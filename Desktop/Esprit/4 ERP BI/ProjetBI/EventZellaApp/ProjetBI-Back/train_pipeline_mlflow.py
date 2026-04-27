import os
import warnings
import joblib
import mlflow
import mlflow.sklearn
import mlflow.statsmodels

import numpy as np
import pandas as pd

from sqlalchemy import create_engine, text

from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder, StandardScaler
from sklearn.metrics import (
    mean_absolute_error,
    mean_squared_error,
    r2_score,
    silhouette_score,
    accuracy_score,
    precision_score,
    recall_score,
    f1_score,
    roc_auc_score,
)
from sklearn.ensemble import RandomForestRegressor, RandomForestClassifier, IsolationForest
from sklearn.cluster import KMeans

from statsmodels.tsa.arima.model import ARIMA
from statsmodels.tsa.holtwinters import ExponentialSmoothing


warnings.filterwarnings("ignore")


# =====================================================
# CONFIGURATION GÉNÉRALE
# =====================================================

ML_MODELS_DIR = "ml_models"
os.makedirs(ML_MODELS_DIR, exist_ok=True)

# Important sur Windows : éviter les problèmes de chemin avec espaces
mlflow.set_tracking_uri("file:./mlruns")
mlflow.set_experiment("EventZella_MLOps_Pipeline")

# Connexion SQL Server
server = "localhost"
database = "event_DWH"

connection_string = (
    f"mssql+pyodbc://@{server}/{database}"
    "?driver=ODBC+Driver+17+for+SQL+Server&trusted_connection=yes"
)


# =====================================================
# 1. CHARGEMENT DES DONNÉES DEPUIS SQL SERVER
# =====================================================

def load_data_from_sql():
    print("Chargement des données depuis SQL Server...")

    engine = create_engine(connection_string)

    with engine.connect() as conn:
        conn.execute(text("SELECT 1"))
        print("Connexion SQL Server réussie")

    query = """
    SELECT 
        f.id_reservation,
        f.price,
        f.nbr_reservations,
        f.nbr_visitors,
        f.marketing_spend,
        f.market_count,
        f.status AS reservation_status,

        e.type AS event_type,
        e.event_date,

        cat.name AS category_name,

        l.city,
        l.country,

        ev.rating,

        t.trend_score,
        t.growth_rate_pct,

        v.capacity_min,
        v.capacity_max,
        v.venue_type

    FROM dbo.FACT_VENTES f
    LEFT JOIN dbo.Dim_Event e 
        ON f.id_event = e.id_event

    LEFT JOIN dbo.Dim_Category cat 
        ON f.id_category = cat.id_category

    LEFT JOIN dbo.Dim_Localisation l 
        ON f.id_localisation = l.id_localisation

    LEFT JOIN dbo.Dim_Evaluation ev 
        ON f.id_evaluation = ev.id_evaluation

    LEFT JOIN dbo.Dim_Trends t 
        ON f.id_trend = t.id_trend

    LEFT JOIN dbo.DimVenue2 v 
        ON f.id_venue = v.id_venue
    """

    df_raw = pd.read_sql(query, engine)
    df_raw.to_csv("event_data.csv", index=False)

    print(f"Données chargées : {df_raw.shape[0]} lignes, {df_raw.shape[1]} colonnes")
    print("Fichier sauvegardé : event_data.csv")

    return df_raw


# =====================================================
# 2. PRÉPARATION DES DONNÉES
# =====================================================

def prepare_data(df_raw):
    print("\nPréparation des données...")

    df = df_raw.copy()

    # Suppression des colonnes ID
    id_columns = ["id_reservation"]
    df = df.drop(columns=[col for col in id_columns if col in df.columns])

    # Traitement des valeurs manquantes numériques
    numeric_cols = df.select_dtypes(include=[np.number]).columns
    for col in numeric_cols:
        if df[col].isnull().sum() > 0:
            df[col] = df[col].fillna(df[col].median())

    # Traitement des valeurs manquantes catégorielles
    categorical_cols = df.select_dtypes(include=["object"]).columns
    for col in categorical_cols:
        if df[col].isnull().sum() > 0:
            mode_val = df[col].mode()
            df[col] = df[col].fillna(mode_val[0] if not mode_val.empty else "Inconnu")

    print("Valeurs manquantes traitées")

    # Encodage des variables catégorielles
    categorical_to_encode = [
        "event_type",
        "category_name",
        "city",
        "country",
        "venue_type",
        "reservation_status",
    ]

    label_encoders = {}

    for col in categorical_to_encode:
        if col in df.columns:
            le = LabelEncoder()
            df[col] = df[col].fillna("Inconnu")
            df[f"{col}_encoded"] = le.fit_transform(df[col].astype(str))
            label_encoders[col] = le
            print(f"Encodage : {col} → {col}_encoded")

    # Feature engineering
    df["marketing_per_visitor"] = df["marketing_spend"] / (df["nbr_visitors"] + 1)
    df["price_per_reservation"] = df["price"] / (df["nbr_reservations"] + 1)
    df["visitors_per_reservation"] = df["nbr_visitors"] / (df["nbr_reservations"] + 1)
    df["conversion_rate"] = df["nbr_reservations"] / (df["nbr_visitors"] + 1) * 100

    # Features temporelles
    df["event_date"] = pd.to_datetime(df["event_date"], errors="coerce")

    df["month"] = df["event_date"].dt.month
    df["quarter"] = df["event_date"].dt.quarter
    df["year"] = df["event_date"].dt.year
    df["day_of_week"] = df["event_date"].dt.dayofweek

    # Saison
    df["season"] = df["month"].apply(
        lambda x: "Hiver" if x in [12, 1, 2]
        else "Printemps" if x in [3, 4, 5]
        else "Été" if x in [6, 7, 8]
        else "Automne"
    )

    df["season_encoded"] = df["season"].map(
        {
            "Hiver": 0,
            "Printemps": 1,
            "Été": 2,
            "Automne": 3,
        }
    )

    # Suppression des colonnes non utilisées
    columns_to_drop = [
        "event_date",
        "season",
        "reservation_status",
        "category_name",
        "city",
        "country",
        "venue_type",
        "comment",
    ]

    columns_to_drop = [col for col in columns_to_drop if col in df.columns]
    df = df.drop(columns=columns_to_drop)

    # Remplacer les éventuelles valeurs manquantes restantes
    df = df.fillna(0)

    df.to_csv("data_cleaned.csv", index=False)

    print(f"Data preparation terminée : {df.shape[0]} lignes, {df.shape[1]} colonnes")
    print("Fichier sauvegardé : data_cleaned.csv")

    return df


# =====================================================
# 3. RÉGRESSION — PRÉDICTION DU PRIX
# =====================================================

def train_regression(df):
    print("\nTraining regression model...")

    features = [
        "market_count",
        "nbr_visitors",
        "nbr_reservations",
        "marketing_spend",
        "rating",
        "trend_score",
        "growth_rate_pct",
        "capacity_min",
        "capacity_max",
        "season_encoded",
        "event_type_encoded",
        "venue_type_encoded",
        "city_encoded",
    ]

    target = "price"

    # Vérification des colonnes
    missing_features = [col for col in features if col not in df.columns]
    if missing_features:
        raise ValueError(f"Colonnes manquantes pour la régression : {missing_features}")

    X = df[features]
    y = df[target]

    X_train, X_test, y_train, y_test = train_test_split(
        X,
        y,
        test_size=0.2,
        random_state=42
    )

    with mlflow.start_run(run_name="Regression_RandomForest_Price"):
        model = RandomForestRegressor(
            n_estimators=100,
            random_state=42
        )

        model.fit(X_train, y_train)

        y_pred = model.predict(X_test)

        mae = mean_absolute_error(y_test, y_pred)
        rmse = np.sqrt(mean_squared_error(y_test, y_pred))
        r2 = r2_score(y_test, y_pred)

        model_path = os.path.join(ML_MODELS_DIR, "rf_regression.pkl")
        joblib.dump(model, model_path)

        mlflow.log_param("model_type", "RandomForestRegressor")
        mlflow.log_param("n_estimators", 100)
        mlflow.log_param("random_state", 42)
        mlflow.log_param("target", target)
        mlflow.log_param("features", ",".join(features))

        mlflow.log_metric("MAE", float(mae))
        mlflow.log_metric("RMSE", float(rmse))
        mlflow.log_metric("R2", float(r2))

        mlflow.log_artifact(model_path)
        mlflow.sklearn.log_model(model, "model")

        print("Regression model saved:", model_path)
        print(f"MAE={mae:.2f}, RMSE={rmse:.2f}, R2={r2:.4f}")


# =====================================================
# 4. CLASSIFICATION — FIDÉLITÉ CLIENT
# =====================================================

def train_classification(df):
    print("\nTraining classification model...")

    # Création de la cible : client fidèle = top 25% des réservations
    threshold = df["nbr_reservations"].quantile(0.75)
    df["target_fidele"] = (df["nbr_reservations"] >= threshold).astype(int)

    features = [
        "price",
        "nbr_visitors",
        "marketing_spend",
        "market_count",
        "rating",
        "trend_score",
        "growth_rate_pct",
        "capacity_min",
        "capacity_max",
        "event_type_encoded",
        "category_name_encoded",
        "city_encoded",
        "venue_type_encoded",
        "season_encoded",
        "conversion_rate",
    ]

    target = "target_fidele"

    missing_features = [col for col in features if col not in df.columns]
    if missing_features:
        raise ValueError(f"Colonnes manquantes pour la classification : {missing_features}")

    X = df[features]
    y = df[target]

    X_train, X_test, y_train, y_test = train_test_split(
        X,
        y,
        test_size=0.2,
        random_state=42,
        stratify=y
    )

    with mlflow.start_run(run_name="Classification_RandomForest_Fidelity"):
        model = RandomForestClassifier(
            n_estimators=100,
            random_state=42,
            class_weight="balanced"
        )

        model.fit(X_train, y_train)

        y_pred = model.predict(X_test)

        if hasattr(model, "predict_proba"):
            y_proba = model.predict_proba(X_test)[:, 1]
            auc = roc_auc_score(y_test, y_proba)
        else:
            auc = 0.0

        accuracy = accuracy_score(y_test, y_pred)
        precision = precision_score(y_test, y_pred, zero_division=0)
        recall = recall_score(y_test, y_pred, zero_division=0)
        f1 = f1_score(y_test, y_pred, zero_division=0)

        model_path = os.path.join(ML_MODELS_DIR, "fidelity_model.pkl")
        joblib.dump(model, model_path)

        mlflow.log_param("model_type", "RandomForestClassifier")
        mlflow.log_param("n_estimators", 100)
        mlflow.log_param("random_state", 42)
        mlflow.log_param("target", target)
        mlflow.log_param("threshold_75_percentile", float(threshold))
        mlflow.log_param("features", ",".join(features))

        mlflow.log_metric("accuracy", float(accuracy))
        mlflow.log_metric("precision", float(precision))
        mlflow.log_metric("recall", float(recall))
        mlflow.log_metric("f1_score", float(f1))
        mlflow.log_metric("auc_roc", float(auc))

        mlflow.log_artifact(model_path)
        mlflow.sklearn.log_model(model, "model")

        print("Classification model saved:", model_path)
        print(f"Accuracy={accuracy:.4f}, Precision={precision:.4f}, Recall={recall:.4f}, F1={f1:.4f}, AUC={auc:.4f}")


# =====================================================
# 5. CLUSTERING — KMEANS
# =====================================================

def train_clustering(df):
    print("\nTraining clustering model...")

    features = [
        "price",
        "nbr_reservations",
        "nbr_visitors",
        "marketing_spend",
        "market_count",
        "rating",
    ]

    missing_features = [col for col in features if col not in df.columns]
    if missing_features:
        raise ValueError(f"Colonnes manquantes pour le clustering : {missing_features}")

    X = df[features].copy()
    X = X.fillna(X.median(numeric_only=True))

    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)

    with mlflow.start_run(run_name="Clustering_KMeans"):
        model = KMeans(
            n_clusters=7,
            random_state=42,
            n_init=10
        )

        clusters = model.fit_predict(X_scaled)
        silhouette = silhouette_score(X_scaled, clusters)

        model_path = os.path.join(ML_MODELS_DIR, "kmeans.pkl")
        joblib.dump(model, model_path)

        mlflow.log_param("model_type", "KMeans")
        mlflow.log_param("n_clusters", 7)
        mlflow.log_param("random_state", 42)
        mlflow.log_param("features", ",".join(features))

        mlflow.log_metric("silhouette_score", float(silhouette))

        mlflow.log_artifact(model_path)
        mlflow.sklearn.log_model(model, "model")

        print("KMeans model saved:", model_path)
        print(f"Silhouette={silhouette:.4f}")


# =====================================================
# 6. ANOMALY DETECTION — ISOLATION FOREST
# =====================================================

def train_anomaly_detection(df):
    print("\nTraining anomaly detection model...")

    features = [
        "price",
        "nbr_reservations",
        "nbr_visitors",
        "marketing_spend",
        "market_count",
        "rating",
    ]

    missing_features = [col for col in features if col not in df.columns]
    if missing_features:
        raise ValueError(f"Colonnes manquantes pour anomaly detection : {missing_features}")

    X = df[features].copy()
    X = X.fillna(X.median(numeric_only=True))

    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)

    with mlflow.start_run(run_name="Anomaly_IsolationForest"):
        model = IsolationForest(
            contamination=0.05,
            random_state=42,
            n_estimators=50
        )

        model.fit(X_scaled)

        anomaly_pred = model.predict(X_scaled)
        anomaly_rate = np.mean(anomaly_pred == -1) * 100

        model_path = os.path.join(ML_MODELS_DIR, "iforest.pkl")
        joblib.dump(model, model_path)

        mlflow.log_param("model_type", "IsolationForest")
        mlflow.log_param("contamination", 0.05)
        mlflow.log_param("n_estimators", 50)
        mlflow.log_param("random_state", 42)
        mlflow.log_param("features", ",".join(features))

        mlflow.log_metric("anomaly_rate_pct", float(anomaly_rate))

        mlflow.log_artifact(model_path)
        mlflow.sklearn.log_model(model, "model")

        print("IsolationForest model saved:", model_path)
        print(f"Anomaly rate={anomaly_rate:.2f}%")


# =====================================================
# 7. TIME SERIES — ARIMA ET ETS
# =====================================================

def safe_mape(y_true, y_pred):
    y_true_arr = np.array(y_true, dtype=float)
    y_pred_arr = np.array(y_pred, dtype=float)

    denominator = np.where(y_true_arr == 0, np.nan, y_true_arr)
    mape = np.nanmean(np.abs((y_true_arr - y_pred_arr) / denominator)) * 100

    if np.isnan(mape):
        return 0.0

    return float(mape)


def train_timeseries():
    print("\nTraining time series models...")

    if not os.path.exists("event_data.csv"):
        print("event_data.csv introuvable, time series ignorée.")
        return

    df = pd.read_csv("event_data.csv")

    if "event_date" not in df.columns or "nbr_reservations" not in df.columns:
        print("Colonnes event_date ou nbr_reservations manquantes, time series ignorée.")
        return

    df["event_date"] = pd.to_datetime(df["event_date"], errors="coerce")
    df = df.dropna(subset=["event_date", "nbr_reservations"])

    monthly = df.groupby(df["event_date"].dt.to_period("M")).agg({
        "nbr_reservations": "sum"
    }).reset_index()

    monthly["date"] = monthly["event_date"].dt.to_timestamp()
    monthly.set_index("date", inplace=True)

    series = monthly["nbr_reservations"].astype(float).sort_index()

    if len(series) < 8:
        print("Pas assez de données mensuelles pour entraîner ARIMA/ETS.")
        return

    train_size = int(len(series) * 0.8)
    train = series.iloc[:train_size]
    test = series.iloc[train_size:]

    if len(test) < 1:
        print("Pas assez de données de test pour time series.")
        return

    # ----------------------------
    # ARIMA
    # ----------------------------
    best_aic = float("inf")
    best_order = (1, 1, 1)

    for p in range(0, 3):
        for d in range(0, 2):
            for q in range(0, 3):
                try:
                    candidate = ARIMA(train, order=(p, d, q)).fit()
                    if candidate.aic < best_aic:
                        best_aic = candidate.aic
                        best_order = (p, d, q)
                except Exception:
                    continue

    with mlflow.start_run(run_name="TimeSeries_ARIMA"):
        arima_fit = ARIMA(train, order=best_order).fit()

        arima_pred = arima_fit.forecast(steps=len(test))
        arima_pred.index = test.index

        mae = mean_absolute_error(test, arima_pred)
        rmse = np.sqrt(mean_squared_error(test, arima_pred))
        mape = safe_mape(test, arima_pred)

        # Réentraîner sur toute la série pour sauvegarder le modèle final
        final_arima_fit = ARIMA(series, order=best_order).fit()

        model_path = os.path.join(ML_MODELS_DIR, "arima.pkl")
        joblib.dump(final_arima_fit, model_path)

        mlflow.log_param("model_type", "ARIMA")
        mlflow.log_param("order", str(best_order))
        mlflow.log_param("best_aic", float(best_aic))

        mlflow.log_metric("MAE", float(mae))
        mlflow.log_metric("RMSE", float(rmse))
        mlflow.log_metric("MAPE", float(mape))

        mlflow.log_artifact(model_path)
        mlflow.statsmodels.log_model(final_arima_fit, "model")

        print("ARIMA model saved:", model_path)
        print(f"ARIMA{best_order}: MAE={mae:.2f}, RMSE={rmse:.2f}, MAPE={mape:.2f}%")

    # ----------------------------
    # ETS
    # ----------------------------
    if len(train) < 24:
        print("Pas assez de données pour ETS saisonnier, ETS ignoré.")
        return

    with mlflow.start_run(run_name="TimeSeries_ETS"):
        ets_model = ExponentialSmoothing(
            train,
            trend="add",
            seasonal="add",
            seasonal_periods=12
        )

        ets_fit = ets_model.fit()
        ets_pred = ets_fit.forecast(len(test))
        ets_pred.index = test.index

        mae = mean_absolute_error(test, ets_pred)
        rmse = np.sqrt(mean_squared_error(test, ets_pred))
        mape = safe_mape(test, ets_pred)

        # Réentraîner sur toute la série
        final_ets_fit = ExponentialSmoothing(
            series,
            trend="add",
            seasonal="add",
            seasonal_periods=12
        ).fit()

        model_path = os.path.join(ML_MODELS_DIR, "ets.pkl")
        joblib.dump(final_ets_fit, model_path)

        mlflow.log_param("model_type", "ExponentialSmoothing")
        mlflow.log_param("trend", "add")
        mlflow.log_param("seasonal", "add")
        mlflow.log_param("seasonal_periods", 12)

        mlflow.log_metric("MAE", float(mae))
        mlflow.log_metric("RMSE", float(rmse))
        mlflow.log_metric("MAPE", float(mape))

        mlflow.log_artifact(model_path)
        mlflow.statsmodels.log_model(final_ets_fit, "model")

        print("ETS model saved:", model_path)
        print(f"ETS: MAE={mae:.2f}, RMSE={rmse:.2f}, MAPE={mape:.2f}%")


# =====================================================
# MAIN PIPELINE
# =====================================================

if __name__ == "__main__":

    print("=" * 70)
    print("EVENTZELLA - AUTOMATED MLOPS TRAINING PIPELINE")
    print("=" * 70)

    # Si event_data.csv existe déjà, on l'utilise.
    # Sinon, on recharge depuis SQL Server.
    if os.path.exists("event_data.csv"):
        print("Chargement depuis event_data.csv")
        df_raw = pd.read_csv("event_data.csv")
    else:
        print("event_data.csv introuvable, chargement depuis SQL Server")
        df_raw = load_data_from_sql()

    df_cleaned = prepare_data(df_raw)

    train_regression(df_cleaned)
    train_classification(df_cleaned)
    train_clustering(df_cleaned)
    train_anomaly_detection(df_cleaned)
    train_timeseries()

    print("\n" + "=" * 70)
    print("Pipeline MLflow terminé avec succès.")
    print("=" * 70)
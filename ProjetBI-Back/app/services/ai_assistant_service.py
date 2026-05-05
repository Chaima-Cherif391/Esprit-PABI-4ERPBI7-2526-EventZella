import os
from groq import Groq
from sqlalchemy import text
from app.database import data_engine

class AIAssistantService:
    def __init__(self):
        self.api_key = os.getenv("GROQ_API_KEY")
        if self.api_key:
            self.client = Groq(api_key=self.api_key)
            # Modèle performant et rapide de Groq
            self.model_name = "llama-3.3-70b-versatile"
        else:
            self.client = None

    def get_schema_context(self):
        return """
        Vous êtes l'assistant IA de EventZella, une plateforme de BI pour l'événementiel.
        Voici le schéma de la base de données :
        - FACT_VENTES (Table centrale) : contient les clés étrangères id_event, id_category, id_beneficiary, id_service, id_localisation, id_evaluation, id_venue, id_entertainer, id_trend, id_weather. Contient aussi price, nbr_reservations, nbr_visitors, marketing_spend.
        - Dim_Event : id_event, title, type, event_date. (Pas de id_category ici)
        - Dim_Category : id_category, name.
        - Dim_Evaluation : id_evaluation, rating, comment.
        - Dim_Weather : id_weather, saison, condition_meteo, temp_max, ville.
        - Dim_Venue : id_venue, venue_name, venue_type, capacity_max, city.
        - Dim_Entertainer : id_entertainer, name, categorie, followers_instagram, average_price.
        - DimDates : Date_PK, Date, Annee, Mois, Lib_Mois, Jour, Trimestre.

        IMPORTANT: Pour lier Dim_Event à Dim_Category (ou toute autre dimension), vous DEVEZ passer par la table FACT_VENTES.

        Règles :
        1. Generate ONLY T-SQL SELECT queries for SQL Server.
        2. Be precise with joins.
        3. Respond in English.
        4. NEVER show the SQL query in the final response to the user.
        5. Use TND (Dinars) as the currency unit.
        6. Provide clear, professional, and data-driven insights.
        """

    def generate_sql(self, user_query):
        if not self.client:
            return None, "Clé API Groq manquante."

        prompt = f"""
        {self.get_schema_context()}
        
        Question de l'utilisateur : "{user_query}"
        
        Générez la requête SQL correspondante. Retournez UNIQUEMENT le code SQL entre balises ```sql ... ```.
        """
        try:
            completion = self.client.chat.completions.create(
                model=self.model_name,
                messages=[{"role": "user", "content": prompt}],
                temperature=0.1
            )
            content = completion.choices[0].message.content
            sql_match = content.split("```sql")[1].split("```")[0].strip()
            return sql_match, None
        except Exception as e:
            return None, f"Erreur de génération SQL (Groq) : {str(e)}"

    def execute_and_interpret(self, user_query, sql_query):
        try:
            with data_engine.connect() as conn:
                result = conn.execute(text(sql_query)).fetchall()
                data_str = str([dict(row._mapping) for row in result])

            prompt = f"""
            {self.get_schema_context()}
            
            The user asked: "{user_query}"
            The SQL results are: {data_str}
            
            Provide a clear and professional answer in English interpreting these results. 
            Remember: Do NOT include the SQL query in your answer. Use TND for currency.
            """
            completion = self.client.chat.completions.create(
                model=self.model_name,
                messages=[{"role": "user", "content": prompt}]
            )
            return completion.choices[0].message.content
        except Exception as e:
            return f"Erreur lors de l'exécution ou de l'interprétation : {str(e)}"

    def chat(self, user_query):
        if not self.client:
            return "Désolé, l'assistant IA n'est pas configuré (Clé API Groq manquante)."
        
        sql, error = self.generate_sql(user_query)
        if error:
            return error
        
        return self.execute_and_interpret(user_query, sql)

    def generate_strategic_report(self):
        if not self.client:
            return "AI Consultant is not configured."
        
        try:
            # Récupération des données clés pour le rapport
            with data_engine.connect() as conn:
                # 1. Performance par catégorie
                cat_data = conn.execute(text("""
                    SELECT DC.name, SUM(FV.price * FV.nbr_reservations) as revenue, AVG(FV.nbr_reservations) as avg_res
                    FROM FACT_VENTES FV JOIN Dim_Category DC ON FV.id_category = DC.id_category
                    GROUP BY DC.name
                """)).fetchall()
                
                # 2. Performance par Ville
                city_data = conn.execute(text("""
                    SELECT DV.city, SUM(FV.price * FV.nbr_reservations) as revenue
                    FROM FACT_VENTES FV JOIN Dim_Venue DV ON FV.id_venue = DV.id_venue
                    GROUP BY DV.city
                """)).fetchall()

                # 3. Satisfaction client
                eval_data = conn.execute(text("SELECT AVG(rating) FROM Dim_Evaluation")).scalar()

            # Construction du prompt pour le consultant
            data_summary = f"""
            - Revenue per category: {str([dict(r._mapping) for r in cat_data])}
            - Revenue per city: {str([dict(r._mapping) for r in city_data])}
            - Average Customer Rating: {eval_data}/5
            """

            prompt = f"""
            You are the Senior Strategic AI Consultant for EventZella. 
            Based on the following Business Intelligence data, write an exhaustive and deep strategic report in English.
            
            Data Summary:
            {data_summary}
            
            Structure of the report:
            1. **Executive Briefing**: A high-level overview of current business health.
            2. **Deep Dive Analysis**: 
               - Analyse the performance of top categories.
               - Evaluate regional performance (Top vs Bottom cities).
               - Correlate ratings with categories.
            3. **Market Opportunities**: Where should the CEO invest next month?
            4. **Actionable Roadmap**: 5 specific, data-backed steps to increase ROI and customer satisfaction.
            
            Formatting Rules:
            - Use professional, consulting-firm level English (like McKinsey/BCG).
            - Use Markdown (bold, lists).
            - Use TND for currency.
            - DO NOT mention SQL queries or technical database terms.
            - Ensure the tone is proactive and visionary.
            """
            
            completion = self.client.chat.completions.create(
                model=self.model_name,
                messages=[{"role": "user", "content": prompt}]
            )
            return completion.choices[0].message.content
        except Exception as e:
            return f"Error generating strategic report: {str(e)}"

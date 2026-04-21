from airflow import DAG
from airflow.operators.bash import BashOperator
from airflow.operators.empty import EmptyOperator
from airflow.operators.email import EmailOperator   # ✅ AJOUT
from datetime import datetime, timedelta

with DAG(
    dag_id="talend_dwh_pipeline",
    start_date=datetime(2026, 3, 1),
    schedule="@daily",
    catchup=False,
    default_args={
        "retries": 1,
        "retry_delay": timedelta(minutes=5),
        "email": ["chouchenemariem505@gmail.com"],
        "email_on_failure": True,
        "email_on_retry": False,
    },
) as dag:

    start = EmptyOperator(task_id="start")

    run_master = BashOperator(
        task_id="run_master_dwh",
        bash_command=(
            "bash /opt/airflow/talend_jobs/MASTER_DWH/MASTER_DWH_run.sh "
            "--context_param dbHost=host.docker.internal "
            "--context_param dbPort=1433 "
            "--context_param dbName=event_DWH "
            "--context_param dbUser=bi "
            "--context_param dbPassword=bi "
            "--context_param dbSchema=dbo "
        ),
    )

    quality_check = BashOperator(
        task_id="quality_check",
        bash_command='echo "Quality check OK"',
    )

    # ✅ EMAIL SUCCESS
    success_email = EmailOperator(
        task_id="success_email",
        to="chouchenemariem505@gmail.com",
        subject="DWH Pipeline Success ✅",
        html_content="""
        <h3>Pipeline exécuté avec succès 🎉</h3>
        <p>Le Data Warehouse a été chargé correctement.</p>
        """,
        trigger_rule="all_success",   # 🔥 seulement si tout est OK
    )

    # ✅ EMAIL FAILURE
    fail_email = EmailOperator(
        task_id="fail_email",
        to="chouchenemariem505@gmail.com",
        subject="DWH Pipeline Failed ❌",
        html_content="""
        <h3>Erreur dans le pipeline ⚠️</h3>
        <p>Vérifiez les logs Airflow.</p>
        """,
        trigger_rule="one_failed",   # 🔥 si une tâche échoue
    )

    end = EmptyOperator(task_id="end")

    # 🔗 FLOW PRINCIPAL (inchangé)
    start >> run_master >> quality_check >> end

    # 🔥 AJOUT EMAIL AUTOMATIQUE
    [run_master, quality_check] >> success_email
    [run_master, quality_check] >> fail_email
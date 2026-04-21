from airflow import DAG
from airflow.operators.email import EmailOperator
from datetime import datetime

with DAG(
    dag_id='test_email',
    start_date=datetime(2024, 1, 1),
    schedule_interval=None,
    catchup=False
) as dag:

    send_email = EmailOperator(
        task_id='send_test_email',
        to='chouchenemariem505@gmail.com',  # ⚠️ mets ton email ici
        subject='Test Airflow Email',
        html_content='<h3>✅ Email fonctionne parfaitement !</h3>',
    )
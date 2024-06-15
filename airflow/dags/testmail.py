from datetime import datetime, timedelta
from airflow import DAG
from airflow.operators.bash import BashOperator
from airflow.operators.email import EmailOperator

default_args = {
    'owner': 'team_a',
    'start_date': datetime(2024, 6, 7),
    'retries': 1,
    'retry_delay': timedelta(seconds=30),
    'email_on_failure': True,
    'email_on_retry': False,
    'email': ['testtest@gmail.com']
}

with DAG('dag_for_team_a', default_args=default_args, schedule_interval='@daily', catchup=False) as dag:

    task = BashOperator(
        task_id="print_hello",
        bash_command="echo 'Hello, Team A!'"
    )

    email = EmailOperator(
        task_id='send_email',
        to='testtest2@gmail.com',
        subject='Airflow DAG Notification for Team A',
        html_content="""<h3>Hello Team A, your DAG has been executed.</h3>"""
    )

    task >> email

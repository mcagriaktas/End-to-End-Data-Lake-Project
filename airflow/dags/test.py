from datetime import datetime, timedelta
from airflow import DAG
from airflow.providers.ssh.operators.ssh import SSHOperator

start_date = datetime(2024, 6, 7)

default_args = {
    'owner': 'admin',
    'start_date': start_date,
    'retry_delay': timedelta(seconds=30)
}

with DAG('test', default_args=default_args, schedule_interval='@daily', catchup=False) as dag:

    t0 = SSHOperator(
        task_id="starting",
        ssh_conn_id='spark_master_ssh',
        command="echo 'Starting the script'",
    )

    t1 = SSHOperator(
        task_id="hello_world",
        ssh_conn_id='spark_master_ssh',
        command="cd /opt/submitfiles && ./scala-spark-submit.sh scala_extarct_mariadb.jar",
    )

    t2 = SSHOperator(
        task_id="the_end",
        ssh_conn_id='spark_master_ssh',
        command="echo 'The script has ended'",
    )

    t0 >> t1 >> t2

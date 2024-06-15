from datetime import datetime, timedelta
from airflow import DAG
from airflow.providers.ssh.operators.ssh import SSHOperator
from airflow.operators.bash import BashOperator
from airflow.models import Variable

start_date = datetime(2024, 6, 7)

default_args = {
    'owner': 'admin',
    'start_date': start_date,
    'retry_delay': timedelta(seconds=30),
}

# Connection Informatino
# MariaDB
maria_url = Variable.get("maria_url")
maria_port = Variable.get("maria_port")
maria_db = Variable.get("maria_db")
maria_user = Variable.get("maria_user")
maria_password = Variable.get("maria_password")

# MongoDB
mongo_url = Variable.get("mongo_url")
mongo_port = Variable.get("mongo_port")
mongo_db = Variable.get("mongo_db")
mongo_user = Variable.get("mongo_user")
mongo_password = Variable.get("mongo_password")

# HDFS
hdfsUrl = Variable.get("hdfsUrl")
hdfsPort = Variable.get("hdfsPort")

# Spark Information
spark_master_url = Variable.get("spark_master_url")
spark_master_port = Variable.get("spark_master_port")


with DAG('ETL', default_args=default_args, schedule_interval='@daily', catchup=False) as dag:

    t0 = SSHOperator(
        task_id="starting",
        ssh_conn_id='spark_master_ssh',
        cmd_timeout=None,
        command="echo 'Starting the script'",
    )

    t1 = SSHOperator(
        task_id="raw_mariadb",
        ssh_conn_id='spark_master_ssh',
        cmd_timeout=None,
        command=f"""
            /opt/spark/bin/spark-submit \
            --class com.example.ETL_raw_mariadb \
            --master spark://{spark_master_url}:{spark_master_port} \
            /opt/submitfiles/ETL_raw_mariadb.jar \
            {maria_url} {maria_port} {maria_db} {maria_user} {maria_password} {hdfsUrl} {hdfsPort} {spark_master_url} {spark_master_port}
        """
    )

    t2 = SSHOperator(
        task_id="raw_mongodb",
        ssh_conn_id='spark_master_ssh',
        cmd_timeout=None,
        command=f"""
            /opt/spark/bin/spark-submit \
            --class com.example.ETL_raw_mongodb \
            --master spark://{spark_master_url}:{spark_master_port} \
            --packages io.delta:delta-core_2.12:2.4.0 \
            /opt/submitfiles/ETL_raw_mongodb.jar \
            {mongo_url} {mongo_port} {mongo_db} {mongo_user} {mongo_password} {hdfsUrl} {hdfsPort} {spark_master_url} {spark_master_port}
        """
    )

    t3 = SSHOperator(
       task_id="clean_mariadb",
        ssh_conn_id='spark_master_ssh',
        cmd_timeout=None,
        command=f"""
            /opt/spark/bin/spark-submit \
            --class com.example.ETL_clean_mariadb \
            --master spark://{spark_master_url}:{spark_master_port} \
            /opt/submitfiles/ETL_clean_mariadb.jar \
            {maria_url} {maria_port} {maria_db} {maria_user} {maria_password} {hdfsUrl} {hdfsPort} {spark_master_url} {spark_master_port}
        """
    )

    t4 = SSHOperator(
        task_id="clean_mongodb",
        ssh_conn_id='spark_master_ssh',
        cmd_timeout=None,
        command=f"""
            /opt/spark/bin/spark-submit \
            --class com.example.ETL_clean_mongodb \
            --master spark://{spark_master_url}:{spark_master_port} \
            --packages io.delta:delta-core_2.12:2.4.0 \
            /opt/submitfiles/ETL_clean_mongodb.jar \
            {mongo_url} {mongo_port} {mongo_db} {mongo_user} {mongo_password} {hdfsUrl} {hdfsPort} {spark_master_url} {spark_master_port}
        """
    )
    
    t5 = SSHOperator(
        task_id="the_end",
        ssh_conn_id='spark_master_ssh',
        cmd_timeout=None,
        command="echo 'The script has ended'",
    )

    t0 >> t1 >> t2 >> t3 >> t4 >> t5








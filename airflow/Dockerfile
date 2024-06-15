FROM bitnami/airflow:latest

USER root
RUN apt-get update && apt-get install -y openssh-client && apt-get clean

RUN useradd -m -u 1001 -s /bin/bash airflow_user
RUN chown -R airflow_user:airflow_user /opt/bitnami/airflow

RUN chmod 777 /opt/bitnami/airflow

RUN airflow db upgrade

USER 1001


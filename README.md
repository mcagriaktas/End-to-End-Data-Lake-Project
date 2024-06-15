# End-to-End-Data-Lake-Project

To set up the desired system in the project, we first created an architecture. This architecture involved collecting raw data from human resources, users within the warehouse, and organizational documents. After cleaning the data, it was written to the relevant databases, making it available for analysis using visualization tools like Tableau or SQL queries. Initially, the raw data was sent to HDFS through an ETL pipeline. As information on the required analysis was received, the data was transferred to clean tables. The analysis was then conducted using Trino, and the relevant reports were prepared.

1. Build containers:

   docker-compose up -d --build

   ![Screenshot from 2024-06-15 20-16-03](https://github.com/mcagriaktas/End-to-End-Data-Lake-Project/assets/52080028/3b2fb4d0-26aa-4718-bf8d-caded9b0d59a)
   
2. Wait 1 minute and check the containers status.

   docker-compose ps -a

   ![Screenshot from 2024-06-15 20-17-01](https://github.com/mcagriaktas/End-to-End-Data-Lake-Project/assets/52080028/2ae93ec6-7a67-47ce-af81-8472879eef0e)

3. Check the SSH connection for Airflow and Spark master.

   ssh -i /opt/bitnami/airflow/.ssh/spark-master-key root@spark-master

   ![Screenshot from 2024-06-15 20-17-45](https://github.com/mcagriaktas/End-to-End-Data-Lake-Project/assets/52080028/12b6b773-c844-4c7a-b09d-ec410b31dca1)

4. Create the /data folder in HDFS and grant permissions.

   ![Screenshot from 2024-06-15 20-18-16](https://github.com/mcagriaktas/End-to-End-Data-Lake-Project/assets/52080028/875cb110-eae2-47b1-89b2-7016c4a3d0a2)

5. Lastly you need to create users and humanresource tables

   You need to create users and humanresource tables. You can use Trino on DBeaver:

   ![Screenshot from 2024-06-15 20-19-07](https://github.com/mcagriaktas/End-to-End-Data-Lake-Project/assets/52080028/6e510c88-5a81-4222-99c2-0e9f39146694)

   Then insert the data. I created a Python script in the create_data folder.

   ![Screenshot from 2024-06-15 20-19-35](https://github.com/mcagriaktas/End-to-End-Data-Lake-Project/assets/52080028/c83bbb3a-62ce-4d5a-ba98-02f49d4f9253)

   Also don't forget to download jar file,

   ![Screenshot from 2024-06-15 20-19-55](https://github.com/mcagriaktas/End-to-End-Data-Lake-Project/assets/52080028/03b752a0-e95a-442f-a132-bb581c15eea6)

6. Add your airflow variables and spark_master_ssh

   Check the (3.1. Scrips Security for Airflow) part. (IN MY MEDIUM PAGE = https://medium.com/@mucagriaktas/end-to-end-data-engineer-data-lake-project-scala-spark-3-5-1-150246b65d1f )

7. Start your airflow dags: localhost:8080

   ![Screenshot from 2024-06-15 20-21-15](https://github.com/mcagriaktas/End-to-End-Data-Lake-Project/assets/52080028/96333554-3037-4f22-850f-bd3badb8cbdd)

   ![Screenshot from 2024-06-15 20-21-45](https://github.com/mcagriaktas/End-to-End-Data-Lake-Project/assets/52080028/d7bbbc29-e993-40ac-b3ed-bb65aeca4198)

8. I’m using DBrave, the database managment tools is really easy and user firently.

   ![Screenshot from 2024-06-15 20-22-10](https://github.com/mcagriaktas/End-to-End-Data-Lake-Project/assets/52080028/79bb67c3-77ae-4217-9cd3-31bb535608cd)

The-End
Thank you for reading all project, if you wish you can send me a mail… :D
cheers in peace… See ya V2…

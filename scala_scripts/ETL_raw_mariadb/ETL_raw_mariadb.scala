package com.example

import org.apache.spark.sql.{DataFrame, SparkSession}

object ETL_raw_mariadb {
  def main(args: Array[String]): Unit = {

    val maria_url = args(0)
    val maria_port = args(1)
    val maria_db = args(2)
    val maria_user = args(3)
    val maria_password = args(4)
    val hdfsUrl = args(5)
    val hdfsPort = args(6)
    val spark_master_url = args(7)
    val spark_master_port = args(8)

    println("Welcome to the Mariadb Users Table Script")
    val spark = initializeSpark(hdfsUrl, hdfsPort, spark_master_url, spark_master_port)
    spark.sparkContext.setLogLevel("INFO")

    println("############### EXTRACT ################")
    val etlExtract = new ETLExtract(maria_url, maria_port, maria_db, maria_user, maria_password)
    val (df_users, df_humanresource) = etlExtract.extract(spark)

    println("############### TRANSFORM ###############")
    val etlTransform = new ETLTransform()
    val df_transformed_users = etlTransform.transform_users(df_users)
    val df_transformed_humanresource = etlTransform.transform_humanresource(df_humanresource)

    println("################### LOAD ##################")
    val etlLoad = new ETLLoad(hdfsUrl, hdfsPort)
    etlLoad.load(df_transformed_users, df_transformed_humanresource)

    spark.stop()
  }

  def initializeSpark(hdfsUrl: String, hdfsPort: String, spark_master_url: String, spark_master_port: String): SparkSession = {
    SparkSession.builder()
      .appName("ETL_Raw_Mariadb")
      .master(s"spark://$spark_master_url:$spark_master_port")
      .config("spark.jars.packages", "io.delta:delta-core_2.12:2.4.0,mariadb-java-client-2.7.3.jar")
      .config("spark.jars", "/opt/spark/jars/mariadb-java-client-2.7.3.jar")
      .config("spark.hadoop.fs.defaultFS", s"hdfs://$hdfsUrl:$hdfsPort")
      .getOrCreate()
  }
}

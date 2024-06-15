package com.example

import org.apache.spark.sql.SparkSession

object ETL_raw_mongodb {
  def main(args: Array[String]): Unit = {

    val mongo_url = args(0)
    val mongo_port = args(1)
    val mongo_db = args(2)
    val mongo_user = args(3)
    val mongo_password = args(4)
    val hdfsUrl = args(5)
    val hdfsPort = args(6)
    val spark_master_url = args(7)
    val spark_master_port = args(8)

    println("Welcome to the Mariadb Users Table Script")
    val spark = initializeSpark(hdfsUrl, hdfsPort, spark_master_url, spark_master_port)
    spark.sparkContext.setLogLevel("INFO")

    println("############### EXTRACT ################")
    val etlExtract = new ETLExtract(mongo_url, mongo_port, mongo_db, mongo_user, mongo_password)
    val (df_texts) = etlExtract.extract(spark)

    println("############### TRANSFORM ###############")
    val etlTransform = new ETLTransform()
    val df_transformed_texts = etlTransform.transform_users(df_texts)

    println("################### LOAD ##################")
    val etlLoad = new ETLLoad(hdfsUrl, hdfsPort)
    etlLoad.load(df_transformed_texts)

    spark.stop()
  }

  def initializeSpark(hdfsUrl: String, hdfsPort: String, spark_master_url: String, spark_master_port: String, mongo_url: String,
                      mongo_port: String, mongo_user: String, mongo_password: String, mongo_db: String): SparkSession = {
    
    SparkSession.builder()
      .appName("ETL_Raw_Mongodb")
      .master(s"spark://$spark_master_url:$spark_master_port")
      .config("spark.jars.packages", "io.delta:delta-core_2.12:2.4.0")
      .config("spark.mongodb.read.connection.uri", s"mongodb://$mongo_user:$mongo_password@$mongo_url:$mongo_port/$mongo_db.texts?authSource=admin")
      .config("spark.mongodb.read.connection.uri", s"mongodb://$mongo_user:$mongo_password@$mongo_url:$mongo_port/$mongo_db.texts?authSource=admin")
      .config("spark.hadoop.fs.defaultFS", s"hdfs://$hdfsUrl:$hdfsPort")
      .getOrCreate()
  }
}

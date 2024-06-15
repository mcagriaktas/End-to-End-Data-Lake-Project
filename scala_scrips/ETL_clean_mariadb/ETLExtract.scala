package com.example

import org.apache.spark.sql.{DataFrame, SparkSession}

class ETLExtract(hdfsUrl: String, hdfsPort: String) {
  def extract(spark: SparkSession): (DataFrame, DataFrame) = {
    println("Reading the users table...")
    val dfusers = spark.read.format("parquet").load(s"hdfs://$hdfsUrl:$hdfsPort/data/raw_users")
    dfusers.show()

    println("Reading the humanresource table...")
    val dfhumanresource = spark.read.format("parquet").load(s"hdfs://$hdfsUrl:$hdfsPort/data/raw_humanresource")
    dfhumanresource.show()

    (dfusers, dfhumanresource)
  }
}

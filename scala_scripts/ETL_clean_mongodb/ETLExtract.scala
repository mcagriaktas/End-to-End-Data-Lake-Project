package com.example

import org.apache.spark.sql.{DataFrame, SparkSession}

class ETLExtract(hdfsUrl: String, hdfsPort: String) {
  def extract(spark: SparkSession): (DataFrame) = {
    spark.read.format("parquet").load(s"hdfs://$hdfsUrl:$hdfsPort/data/raw_texts")
  }
}

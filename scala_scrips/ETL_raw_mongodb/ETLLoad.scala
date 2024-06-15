package com.example

import org.apache.spark.sql.DataFrame

class ETLLoad(hdfsUrl: String, hdfsPort: String) {
  def load(df_transformed_texts: DataFrame): Unit = {
    val dataframe = df_transformed_texts
    val tableFolder = "raw_texts"

    val hdfsBasePath = s"hdfs://$hdfsUrl:$hdfsPort/data/"

    dataframe.write.format("parquet").mode("overwrite").save(s"${hdfsBasePath}${tableFolder}")
    println(s"Successfully saved DataFrame to HDFS path: ${hdfsBasePath}${tableFolder}")
  }
}

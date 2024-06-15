package com.example

import org.apache.spark.sql.{DataFrame, SparkSession}

class ETLLoad (hdfsUrl: String, hdfsPort: String){
  def load(df_users: DataFrame, df_humanresource: DataFrame): Unit = {
    val dataframes = Seq(df_users, df_humanresource)
    val tableFolders = Seq("raw_users", "raw_humanresource")

    val hdfsBasePath = s"hdfs://$hdfsUrl:$hdfsPort/data/"

    dataframes.zip(tableFolders).foreach { case (dataframe, tableFolder) =>
      try {
        println(s"Saving DataFrame to HDFS path: ${hdfsBasePath}${tableFolder}")
        dataframe.write.format("parquet").mode("overwrite").save(s"${hdfsBasePath}${tableFolder}")
        println(s"Successfully saved DataFrame to HDFS path: ${hdfsBasePath}${tableFolder}")
      } catch {
        case e: Exception =>
          println(s"Error while saving DataFrame to HDFS path: ${hdfsBasePath}${tableFolder}")
          e.printStackTrace()
      }
    }
  }
}

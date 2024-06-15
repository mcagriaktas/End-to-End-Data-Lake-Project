package com.example

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

class ETLTransform {
  def transform_users(df_texts: DataFrame): (DataFrame) = {
    val df_filtered_texts = df_texts

    df_filtered_texts
  }
}

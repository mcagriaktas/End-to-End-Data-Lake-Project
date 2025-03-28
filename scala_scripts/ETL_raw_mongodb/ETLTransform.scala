package com.example

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

class ETLTransform {
  def transform_users(df_texts: DataFrame): (DataFrame) = {
    val df_filtered_texts = df_texts

    val df_transformed_texts = df_filtered_texts
      .filter(col("user_id") > 20)
      .withColumnRenamed("timestamp", "date")

    df_transformed_texts
  }
}

package com.example

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

class ETLTransform {
  def transform_users(df_users: DataFrame): DataFrame = {
    // Remember, Spark processes data step by step!
    // 1. Select the DataFrame
    val df_filtered_users = df_users
      // 2. Filter rows where signal_strength is greater than 20
      .filter(col("signal_strength") > 20)
      // 3. Create location_id by extracting the integer part of the location column
      .withColumn("location_id", regexp_extract(col("location"), "\\d+$", 0).cast("int"))
      // 4. Filter rows where location_id is greater than 20
      .filter(col("location_id") > 20)
      // 5. Rename the timestamp column to date
      .withColumnRenamed("timestamp", "date")
      // 6. Select specific columns to include in the final DataFrame
      .select("id", "signal_strength", "frequency", "location", "date")

    val df_transformed_users = df_filtered_users

    df_transformed_users
  }

  def transform_humanresource(df_humanresource: DataFrame): DataFrame = {
    val df_filtered_humanresource = df_humanresource
      .filter(col("salary") > 50000)

    df_filtered_humanresource
  }
}

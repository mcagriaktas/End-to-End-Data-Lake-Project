package com.example

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

class ETLTransform {
  def transform_users(df_users: DataFrame): DataFrame = {

    val df_transformed_users = df_users

    df_transformed_users
  }

  def transform_humanresource(df_humanresource: DataFrame): DataFrame = {
    val df_filtered_humanresource = df_humanresource

    df_filtered_humanresource
  }
}

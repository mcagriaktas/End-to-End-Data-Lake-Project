package com.example

import org.apache.spark.sql.{DataFrame, SparkSession}

class ETLExtract(maria_url: String, maria_port: String, maria_db: String, maria_user: String, maria_password: String) {
  def extract(spark: SparkSession): (DataFrame, DataFrame) = {
    Class.forName("org.mariadb.jdbc.Driver")

    val jdbcConnectionUrl = s"jdbc:mysql://$maria_url:$maria_port/$maria_db?user=$maria_user&password=$maria_password"

    val df_users = spark.read
      .format("jdbc")
      .option("url", jdbcConnectionUrl)
      .option("dbtable", "users")
      .option("driver", "org.mariadb.jdbc.Driver")
      .load()

    val df_humanresource = spark.read
      .format("jdbc")
      .option("url", jdbcConnectionUrl)
      .option("dbtable", "humanresource")
      .option("driver", "org.mariadb.jdbc.Driver")
      .load()

    (df_users, df_humanresource)
  }
}

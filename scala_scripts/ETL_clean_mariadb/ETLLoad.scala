package com.example

import org.apache.spark.sql.{DataFrame, SparkSession}

class ETLLoad (maria_url: String, maria_port: String, maria_db: String, maria_user: String, maria_password: String) {
  def load(df_users: DataFrame, df_humanresource: DataFrame): Unit = {
    Class.forName("org.mariadb.jdbc.Driver")

    val jdbcUrl = s"jdbc:mysql://$maria_url:$maria_port/$maria_db?user=$maria_user&password=$maria_password"

    println("You're clean_users table is writing...")
    df_users.write
      .format("jdbc")
      .mode("overwrite")
      .option("url", jdbcUrl)
      .option("dbtable", "clean_users")
      .option("driver", "org.mariadb.jdbc.Driver")
      .save()

    println("You're clean_humanresource table is writing...")
    df_humanresource.write
      .format("jdbc")
      .mode("overwrite")
      .option("url", jdbcUrl)
      .option("dbtable", "clean_humanresource")
      .option("driver", "org.mariadb.jdbc.Driver")
      .save()
  }
}
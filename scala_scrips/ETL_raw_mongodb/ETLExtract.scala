package com.example

import org.apache.spark.sql.{DataFrame, SparkSession}
import com.mongodb.client.{MongoClients, MongoCollection, MongoDatabase}
import org.bson.Document
import scala.collection.JavaConverters._

class ETLExtract(mongo_url: String, mongo_port: String, mongo_db: String, mongo_user: String, mongo_password: String) {
  def extract(spark: SparkSession): (DataFrame) = {
    val mongoUri = s"mongodb://$mongo_user:$mongo_password@$mongo_url:$mongo_port/?authSource=admin"
    val mongoClient = MongoClients.create(mongoUri)
    val database: MongoDatabase = mongoClient.getDatabase(s"$mongo_db")
    val collection: MongoCollection[Document] = database.getCollection("texts")

    val documents = collection.find().iterator().asScala.toSeq

    import spark.implicits._
    val df_texts = spark.createDataset(documents.map(_.toJson)).toDF("json")

    val df_parsed = spark.read.json(df_texts.rdd.map(r => r.getString(0)))

    df_parsed
  }
}

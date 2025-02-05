package com.example

import org.apache.spark.sql.DataFrame
import com.mongodb.client.{MongoClients, MongoCollection, MongoDatabase}
import org.apache.spark.sql.execution.streaming.FileStreamSource.Timestamp
import org.bson.Document

class ETLLoad (mongo_url: String, mongo_port: String, mongo_db: String, mongo_user: String, mongo_password: String){
  def load(df_transformed_texts: DataFrame): Unit = {
    val mongoUri = s"mongodb://$mongo_user:$mongo_password@$mongo_url:$mongo_port/?authSource=admin"
    val mongoClient = MongoClients.create(mongoUri)
    val database: MongoDatabase = mongoClient.getDatabase(s"$mongo_db")
    val collection: MongoCollection[Document] = database.getCollection("clean_texts")

    df_transformed_texts.collect().foreach(row => {
      val userId = row.getAs[Long]("user_id").toString
      val content = row.getAs[String]("content")
      val date = row.getAs[Timestamp]("timestamp").toString

      val doc = new Document()
        .append("user_id", userId)
        .append("content", content)
        .append("date", date)

      collection.insertOne(doc)
    })
  }
}

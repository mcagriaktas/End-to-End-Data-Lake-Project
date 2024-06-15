ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.14"

lazy val root = (project in file("."))
  .settings(
    name := "pjairflow",
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-core" % "3.5.1",
      "org.apache.spark" %% "spark-sql" % "3.5.1",
      "io.delta" %% "delta-core" % "2.4.0",
      "org.apache.hadoop" % "hadoop-client" % "3.3.1",
      "org.mongodb" % "mongodb-driver-sync" % "4.9.0",
      "org.mariadb.jdbc" % "mariadb-java-client" % "2.7.3",
      "org.jasypt" % "jasypt" % "1.9.3",
      "com.typesafe" % "config" % "1.4.2"
),
    assemblyMergeStrategy in assembly := {
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case "reference.conf" => MergeStrategy.concat
      case "application.conf" => MergeStrategy.concat
      case x => MergeStrategy.first
    }
  )

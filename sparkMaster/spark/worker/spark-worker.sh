#!/bin/bash

# Wait for spark-master to become available
while ! getent hosts spark-master > /dev/null; do
  echo "Waiting for spark-master to start..."
  sleep 5
done

/opt/spark/bin/spark-class org.apache.spark.deploy.worker.Worker spark://spark-master:7077 --webui-port 8081


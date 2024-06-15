#!/bin/bash

echo "History server is starting ...."
/opt/spark/sbin/start-history-server.sh

# Check if SSH service is installed and start if not already running
if ! pgrep -x "sshd" > /dev/null; then
  echo "Starting SSH service..."
  service ssh start
fi

# Set hostname dynamically
hostname=$(hostname -i)
echo "$hostname spark-master" >> /etc/hosts

/opt/spark/bin/spark-class org.apache.spark.deploy.master.Master --ip 0.0.0.0 --port 7077 --webui-port 8080


#!/bin/bash

# WARNING
echo "YOUR CLASS NAME AND JAR FILE NAME MUST BE THE SAME!"

# Extract the base name of the JAR file (remove the directory and .jar extension)
jar_base_name=$(basename "$1" .jar)

# Construct the class name dynamically
class_name="com.example.${jar_base_name}"

# Run the docker exec command with the constructed class name
docker exec -it spark-master /opt/spark/bin/spark-submit --class "${class_name}" --master spark://172.80.0.105:7077 /opt/submitfiles/"$@"


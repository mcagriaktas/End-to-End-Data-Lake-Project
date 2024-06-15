#!/bin/bash
docker exec spark-master spark-submit --master spark://172.80.0.105:7077 /opt/submitfiles/"$@"

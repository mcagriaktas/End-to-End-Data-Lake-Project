#!/bin/bash

URL=$1

docker exec -it spark-master bash -c "cd /opt/spark/jars && wget '$URL'"

echo "Your jar file has been downloaded in the spark-master container at /opt/spark/jars/"


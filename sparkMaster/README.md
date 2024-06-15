## 1. Fix sh files

sed -i 's/\r$//' spark/spark-master.sh
sed -i 's/\r$//' spark/spark-worker.sh

## 2. Docker compose

docker-compose up --build -d 
OR
docker-compose up --scale spark-worker=2 -d

## Check the submitfile for starter .sh
sudo chmod 777 spark-submit.sh
./spark-submit.sh sparktest.py
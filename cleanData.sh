#!/bin/bash
echo 'Cleaning your data folder...'

# Remove data from MariaDB directory
sudo rm -rf data/mariadb_data/*

# Remove data from MongoDB directory
sudo rm -rf data/mongodb_data/*

# Remove data from PostgreSQL directory
sudo rm -rf data/postgresql_data/*

# Remove data from Datanode directory
sudo rm -rf data/hadoop_datanode/*

# Remove data from Namenode directory
sudo rm -rf data/hadoop_namenode/*

# Remove data from Grafana directory
sudo rm -rf data/grafana_data/*

# Check if all specified folders are empty
if [ -z "$(ls -A data/mariadb_data)" ] && \
   [ -z "$(ls -A data/mongodb_data)" ] && \
   [ -z "$(ls -A data/postgresql_data)" ] && \
   [ -z "$(ls -A data/hadoop_datanode)" ] && \
   [ -z "$(ls -A data/grafana_data)" ] && \
   [ -z "$(ls -A data/hadoop_namenode)" ]; then
    echo 'All data has been cleaned.'
else
    echo 'Some data folders are not empty.'
fi

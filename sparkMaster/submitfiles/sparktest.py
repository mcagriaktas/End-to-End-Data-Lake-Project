from pyspark.sql import SparkSession
from pyspark.sql.types import StructType, StructField, StringType, IntegerType

# Initialize SparkSession
spark = SparkSession.builder \
    .appName("InsertParquetToHDFS") \
    .master("local[*]") \
    .config("spark.jars.packages", "io.delta:delta-core_2.12:2.4.0") \
    .getOrCreate()

# Define schema for the DataFrame
schema = StructType([
    StructField("name", StringType(), True),
    StructField("age", IntegerType(), True)
])

# Create data
data = [("Alice", 30), ("Bob", 25), ("Charlie", 35)]

# Create DataFrame
df = spark.createDataFrame(data, schema)

# Show DataFrame content
df.show()

# Write DataFrame to HDFS in Parquet format
try:
    df.write.format("parquet").mode("overwrite").save("hdfs://172.21.0.8:8020/data")
except Exception as e:
    print("ERROR: ", e)

# Stop the Spark session
spark.stop()


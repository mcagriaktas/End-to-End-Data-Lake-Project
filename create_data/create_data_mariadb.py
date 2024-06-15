import mysql.connector
import random
from datetime import datetime, timedelta

# Connect to the MariaDB database
conn = mysql.connector.connect(
    host='127.0.0.1',
    port='3308',
    user='admin',
    password='admin',
    database='test_db'
)
cursor = conn.cursor()

# Function to generate random location
def generate_location():
    return f"Location_{random.randint(1, 100)}"

# Function to generate a random timestamp
def generate_timestamp(start, end):
    delta = end - start
    int_delta = (delta.days * 24 * 60 * 60) + delta.seconds
    random_second = random.randrange(int_delta)
    return start + timedelta(seconds=random_second)

# Define the start and end times for the random timestamp
start_time = datetime.now() - timedelta(days=365)  # 1 year ago
end_time = datetime.now()

# Insert 1000 random entries
for _ in range(100000):  # Adjust the range as needed
    id = random.randint(1, 100)
    signal_strength = random.randint(1, 100)
    frequency = random.randint(2400, 2500)
    location = generate_location()
    timestamp = generate_timestamp(start_time, end_time)
    cursor.execute("""
        INSERT INTO users (id, signal_strength, frequency, location, timestamp)
        VALUES (%s, %s, %s, %s, %s)
    """, (id, signal_strength, frequency, location, timestamp))

# Commit the transaction
conn.commit()

# Close the connection
cursor.close()
conn.close()

print("Inserted 100,000 rows into the 'users' table.")

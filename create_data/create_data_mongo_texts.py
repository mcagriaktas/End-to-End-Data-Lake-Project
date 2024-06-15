import pymongo
import random
from datetime import datetime, timedelta
from bson.objectid import ObjectId

# Connect to the MongoDB server with authentication
client = pymongo.MongoClient("mongodb://admin:admin@172.80.0.101:27017/")
db = client["test_db"]
texts_collection = db["texts"]

# Function to generate random text content
def generate_content():
    words = ["Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit"]
    return " ".join(random.choices(words, k=10))

# Function to generate a random timestamp
def generate_timestamp(start, end):
    delta = end - start
    int_delta = (delta.days * 24 * 60 * 60) + delta.seconds
    random_second = random.randrange(int_delta)
    return start + timedelta(seconds=random_second)

# Define the start and end times for the random timestamp
start_time = datetime.now() - timedelta(days=365)  # 1 year ago
end_time = datetime.now()

# Insert 1,000,000 random documents into the texts collection
documents = []
for _ in range(100000):
    user_id = random.randint(1, 100)
    content = generate_content()
    timestamp = generate_timestamp(start_time, end_time)
    documents.append({
        "user_id": user_id,
        "content": content,
        "timestamp": timestamp
    })

texts_collection.insert_many(documents)

print("Inserted 1,000,000 documents into the 'texts' collection.")

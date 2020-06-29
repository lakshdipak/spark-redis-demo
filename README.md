# spark-redis-demo
Spring Boot Spark Application writing and reading data frame to redis.
It was needed in multiple sparks jobs to save the data frame and use at later stage for aggregations and other joining etc.

Following Components :
1. Spark & Redis Installed locally
2. Spark and redis configured in Spark Session at application start up
3. An endpoint to post a collection of Objects
4. Service class to get Spark Session and transform the collection to data frame and write to redis
3. A Service class to read the stored data frame from redis
4. Get endpoint to transform the loaded data frame and returned in get response.
5. Postman collection to run integration test


Happy Spark and Redis Integration.
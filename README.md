[![CircleCI](https://circleci.com/gh/abalzan/codingexercise.svg?style=svg)](https://circleci.com/gh/abalzan/codingexercise)

# code exercise accela Mid-level Java developer

Endpoints can be checked at: http://localhost:8080/swagger-ui.html

Request samples are present in Postman collection: coding-Exercise.postman_collection.json

In this exercise I am using h2 in memory database, I am loading one user with two addresses, using Bootstrap class.
All the CRUD operations for Person/Address are available on the Postman collection.

I put in place some Integration tests for the CRUD operations.


# Run application using docker

```$ mvn clean install```

Run container:

```$ docker-compose up```
OR
```$ docker-compose up --build --force-recreate```


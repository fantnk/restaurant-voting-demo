# Restaurant Voting Demo

Demo application.

## Task

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

The task is:

Build a voting system for deciding where to have lunch.

- 2 types of users: admin and regular users
- Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
- Menu changes each day (admins do the updates)
- Users can vote on which restaurant they want to have lunch at
- Only one vote counted per user
- If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed
- Each restaurant provides a new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and
couple curl commands to test it (better - link to Swagger).

## Tech Stack

- Java 17
- Spring Boot 2.6 (Security, Data JPA)
- Caffeine
- OpenAPI 3 (springdoc) + Swagger UI
- JUnit 5
- H2
- Maven (wrapper)

## Build

```shell
./mvnw clean package
```

## Run

```shell
./mvnw spring-boot:run
```

## Testing

### Test Users

- admin
    - Login: admin@gmail.com
    - Password: admin

- user
    - Login: user@yandex.ru
    - Password: user

### Swagger UI

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

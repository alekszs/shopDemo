# Springboot Sentia Application


## Requirements

For building and running the application you need:

- [JDK 11+](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Gradle 5+](https://gradle.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.sentia.shopdemo.ShopdemoApplication` class from your IDE.

Alternatively you can use the [Spring Boot Gradle plugin](https://spring.io/guides/gs/gradle/) like so:

```shell
./gradlew bootRun --args='--spring.profiles.active=dev'
```

Production (prod) profile is available as well. Make sure that you have your own database up and running (port: 27017)

## Database

MongoDB

`resources.json.products.json` contains a data set which is being inserted at the start-up by `com.sentia.shopdemo.config.DatabaseChangelog`

changelog.switch in `application.yml` can be used to enable or disable the initial database population


## GraphQL

Provides aditional Schemas and Queries

Available at `/playground`




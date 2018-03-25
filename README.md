# Tech Assignment Stocks

This is a Spring Boot application with Gradle as the build tool. To start the service in the 'dev' profile (meant to indicate dev environment), use the bootRun task:

`./gradlew bootRun -Dspring.profiles.active=dev`

Otherwise production like environment can be started with:

`./gradlew bootRun`

## The 'default' or 'prod' profile

This is the default profile when running the application without specifying active spring profile. In this mode test data WILL NOT be created in the memory DB.

This mode is meant to run with the 'prod' environment of the front end. It listens to port 8080.

## The 'dev' profile

In this Spring profile some test data will be inserted. This is achieved by the "context" attribute on the liquibase script responsible for inserting test data:

`<changeSet id="1" author="keeyeong.tan" context="dev">`

This mode is meant to run with the 'dev' (default) environment of the front end. It listens to port 8484.

## Datasource and persisting data

By default the application is configured with H2 database in memory mode for its datasource. This means that while the application is running, changes are stored in memory; when the application is shut down these data are lost. The next application start up will re-create the initial state again (with test data in dev mode, completely empty for prod/default).

To change this behaviour, configure an appropriate data source in application.yml (or profile specific application.yml):

```
spring:
  datasource:
     url: jdbc:h2:mem:testDB;MODE=MSSQLServer;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1
     username: sa
     password:
     hikari:
       driverClassName: org.h2.Driver
```

## Integration Tests

Integration test of this project covers CRUD operations of the entity by making REST calls to a running server on random port.

The gradle task to execute the tests is `integrationTest`:

`./gradlew integrationTest`

Integration test has its own `application-it.yml` for the intention that it will also execute with test data and will always be using an in memory db, regardless of what was changed for the dev and prod profile.

## Building and unit tests

To build the application:

`./gradlew build`

To run only the unit tests:

`./gradlew test`

## Interfaces

The interfaces of this application is given as part of the assignment and therefore is not duplicated here.
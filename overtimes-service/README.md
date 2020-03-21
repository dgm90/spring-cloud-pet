# overtimes-service app

This application is a micro-service for CRUD operations with overtimes. It allows you to create overtimes for
 employees. It registers itself in Eureka Service Discovery and acts in cooperation with [Bonus-service](https://github.com/dgm90/spring-cloud-pet/blob/master/eureka-server/README.md)

## Requirements

For building and running the application you need:

- [JDK 11](https://jdk.java.net/11/)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.derzhavets.OvertimesServiceApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```
mvn spring-boot:run
```

## Running in docker

You will need Docker installed and running.
1. To build a docker image image run:
```
docker build -t overtimes-service . 
```
2. To run container:
```
docker run --rm --name overtimes-service -d -p 8098:8098 overtimes-service
```
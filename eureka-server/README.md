# eureka-server app

This application acts like service discover for Overtimes and Bonus services.
It uses Eureka Service Discover of Spring Cloud Netflix. 

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.derzhavets.BonusServiceApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:
```
mvn spring-boot:run
```

## Running in docker

You will need Docker installed and running.
1. To build a docker image image run:
```
docker build -t eureka-server . 
```

2. To run container:
```
docker run --rm --name eureka-server -d -p 8761:8761 eureka-server
```
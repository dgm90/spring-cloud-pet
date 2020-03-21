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

#### Before running the app:
Run postgresql:
```
docker run --rm --name pg-docker -d -p 5432:5432 \
	-e POSTGRES_PASSWORD=docker \
	-e POSTGRES_USER=docker \
	-e POSTGRES_DB=overtimes \
	postgres
```
Run rabbitMq:
```
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

#### To run the app:

1. Build a docker image:
```
docker build -t overtimes-service . 
```
2. Run app in container:
```
docker run --rm --name overtimes-service -d -p 8098:8098 overtimes-service
```
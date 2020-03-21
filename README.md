# spring clound pet project

This is a pet project to study spring cloud services.
It consists of 3 Spring Boot applications:
* Eureka-server acting as a Service Discovery 
* 2 microservices:
  * Overtimes-service
  * Bonus-service
  
The main purpose of the project is to manage overtime hours for some employees.
As soon as you create an overtime using overtimes service, a bonus service is invoked to calculate and save a bonus
 for this overtime record.

## Requirements

For building and running the application you need:

- [JDK 11](https://jdk.java.net/11/)
- [Maven 3](https://maven.apache.org)
- [Docker](https://www.docker.com/)

## Running the application

All applications are supposed to be run together and simultaneously using Docker Compose, but you can also run each
 separately.
Please check the readme files for each project:
- [Eureka-server](https://github.com/dgm90/spring-cloud-pet/blob/master/bonus-service/README.md)
- [Overtimes-service](https://github.com/dgm90/spring-cloud-pet/blob/master/overtimes-service/README.md)
- [Bonus-service](https://github.com/dgm90/spring-cloud-pet/blob/master/eureka-server/README.md)

### How to run in Docker compose
You need the following docker images build in your host computer:
1. eureka-server
2. overtimes-service
3. bonus-service
4. postgresql
5. mongodb
6. rabbitmq

Make sure you are in the root folder with a docker-compose.yml.
Run command:
```
docker-compose up
```
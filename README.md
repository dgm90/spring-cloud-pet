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

First you need jar files for each microservice. From root directory run command:
```
mvn clean package
```

After that run:
```
docker-compose up
```

This will build and run images for all three microservices as well as databases and RabbitMq 


### To test the app running:

##### To check Eureka is running:
Open http://localhost:8761/ in browser. Under 'Instances currently registered with Eureka' there should be 2
  services registered: BONUS-SEVICE and OVERTIMES-SERVICE
  
#### To check Overtimes-service is running:
```
curl --location --request GET 'http://localhost:8098/employees/3'
```
An employee should be returned as JSON
{"id":3,"firstName":"Petr","lastName":"Petrov"}

#### To check Bonus-service is running:
```
curl --location --request POST 'http://localhost:9098/bonus/calculateBonusForOvertime' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 5,
    "projectId": 3,
    "hours": 100.0
}'
```
A bonus should be returned as text: 35000.0

#### To test all apps interacting, create an overtime:
```
curl --location --request POST 'http://localhost:8098/overtimes' \
--header 'Content-Type: application/json' \
--data-raw '{
	"employeeId": 3,
	"projectId": 3,
	"startDate": "2011-12-31",
	"hours": 100
}'
```
A created overtime should be returned as JSON
{"id":6,"employeeId":3,"projectId":3,"startDate":"2011-12-31T00:00:00.000+0000","hours":100.0}

##### What is happening:
1. POST request goes to overtimes service
2. Overtimes creates an overtime record in its database (Postgresql)
3. Overtimes service sends a POST request to bonus-service by its domain name via Eureka Server
4. Bonus-service accepts POST request, calculates bonus and creates a record in its database (MongoDB)
5. Bonus-service sends a message to overtimes-service via RabbitMq. The message contains a newly created bonus record
6. Overtimes-services accepts the messages and logs it

To see the message open your terminal where docker-compose is up and look for com.derzhavets.rabbit.RabbitMqListener
 log record like ```New incoming message: [{"message":"A new bonus record is saved with id...```
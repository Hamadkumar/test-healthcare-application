# Healthcare Application

Healthcare Application is an resutful Spring boot application to perform CRUD operations on patient's name, gender, phone number.

## Getting Started
Clone the project to your local repository.

### Prerequisites
Make sure you configure your postgreSQL by using the below configuration. 

```
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
```

## Deployment

After cloning the project, inside the project folder run the below command to generate the JAR file.

```
./mvnw clean package
```

Or you can run the `mvn install` command and run the Healthcare Application.

## Docker

Run the below commands inside the project folder to let the docker take care of everything.

```
docker-compose build

docker-compose up
```

Use this url http://192.168.99.100:8080/ to load the screen with default docker IP.

## Screen URL

```
http://localhost:8080/index

http://localhost:8080/
```

## Screen UI



## Built with

```
* Java 8
* Maven
* Spring Boot
* PostgreSQL
* Lombok
* Docker
```

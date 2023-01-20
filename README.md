# Proof of Concept - Utopia 2.0
## Backend setup

This is the repository for the edge microservice for the **UTOPIA 2.0** project. Here you can find info on the whole backend architecture and how it interacts with the game.

A visual representation of the architecture is shown here:
![Architecture](https://user-images.githubusercontent.com/74854941/212564316-a6d3f4fb-cc44-4343-9acc-cf29e612ec1b.png)

## Description
The application is a POC for a new explorative tool/game that helps students discovering different bachelor degree options.

The application was created in `Unity` and is a 2D isometric game. The repo for the application can be found [here](https://github.com/KevinVandeputte-TM/utopia). Game statistics that are generated when playing the game can be consulted on a qlik sense dashboard. More info on this can be found [here](https://github.com/ValerieBecquart/project4website).

The application will send requests to the `edge-service` which will connect to 3 lower microservices `game-service`, `userservice` and `analytics-service` to request information which it will then process and combine into a single response to the user. Besides the `edge-service`, a separate service dedicated for collecting feedback will be operating independently as this is unrelated to the game itself.

### Repo's (Click the repo name)
- [EDGE-SERVICE](https://github.com/KevinVandeputte-TM/utopia_edgeservice):

  The service is a Spring Boot REST service with `SwaggerUI` implementation. There is a `Github Action` in place to establish a CI/CD pipeline that checks compilation and then builds. Finally, the `Docker Container` is uploaded to `Docker Hub`.


- [GAME-SERVICE](https://github.com/KevinVandeputte-TM/utopia_game-service):

  The service is a Spring Boot REST Microservice which connects to a Dockerized `MariaDB` database. The service is responsible for providing the game with all necessary game information. `Github Actions` are used to establish a CI/CD pipeline for compiling, testing, and building the container. Finally, the `Docker Container` is uploaded to `Docker Hub`.


- [USER-SERVICE](https://github.com/KevinVandeputte-TM/utopia_userservice):

  The service is a Spring Boot REST Microservice which connects to a Dockerized `MongoDB` database. The service is responsible for providing the game with all necessary user information. `Github Actions` are used to establish a CI/CD pipeline for compiling, testing, and building the container. Finally, the `Docker Container` is uploaded to `Docker Hub`.


- [ANALYTIC-SERVICE](https://github.com/KevinVandeputte-TM/utopia_analytic-service):

  The service is a Spring Boot REST Microservice which connects to a Dockerized `MongoDB` database. The service is responsible for providing the game with analytics data of visits per station. `Github Actions` are used to establish a CI/CD pipeline for compiling, testing, and building the container. Finally, the `Docker Container` is uploaded to `Docker Hub`.
  
- [FEEDBACK-SERVICE](https://github.com/KevinVandeputte-TM/utopia_feedback-service):

  The service is a Spring Boot REST service which connects to a Dockerized `MongoDB` database. The service is responsible for collecting feedback from the users after playing the game. `Github Actions` are used to establish a CI/CD pipeline for compiling, testing, and building the container. Finally, the `Docker Container` is uploaded to `Docker Hub`.

- [DEPLOYMENT](https://github.com/KevinVandeputte-TM/utopia_microservices-docker-compose):

  The backend was deployed on `Okteto`. Therefore a seperate repository was created containing a single `Docker compose` file which gives the instructions to pull the necessary images of `docker hub` and set up all microservices and underlying databases.

## Running the example locally

The lower services each connect to a Dockerized database. These databases need to be up and running before the project can be ran locally.

Set up the Docker Container with the MariaDB database:
``` pwsh
docker run --name stations -p 3306:3306 -e MARIADB_ROOT_PASSWORD=abc123 -e MARIADB_USER=user -e MARIADB_PASSWORD=user123 -d mariadb
```

Set up the Docker Container with the MongoDB database:
``` pwsh
docker run --name users-mongodb -p 27017:27017 -d mongo 
```

Set up the Docker container with the second MongoDB database:
``` pwsh
docker run --name analytics-mongodb -p 27018:27018 -d mongo 
```

Services connecting to MongoDB need some additional code in `application.properties` to connect to the dockerized MongoDB database. Below you can find an example for the `user-service`:
``` pwsh
spring.data.mongodb.database=users-mongodb
spring.data.mongodb.uri=mongodb://localhost/users-mongodb
```
Additionaly, in the `application.properties` of the `analytics-service`, it is important to match the port number with its mongodb database. Change the port-number from 27017 to 27018 to achieve this.

Once this is done you can start the `game-service`, `user-service` and `analytics-service` applications. These applications include a method to fill up the databases with dummy information for testing purposes.

Before running the `edge-service` you should know that it, right now, is set up to be deployed. For you to run the project locally you need to go into the `application.properties` file and change following lines of code:
``` pwsh
userservice.baseurl = ${USER_SERVICE_BASEURL:192.168.99.100:8052}
gameservice.baseurl = ${GAME_SERVICE_BASEURL:192.168.99.100:8051}
analytics-service.baseurl = ${ANALYTIC_SERVICE_BASEURL: 192.168.99.100:8054}
```
into:
``` pwsh
userservice.baseurl = ${USER_SERVICE_BASEURL:localhost:8052}
gameservice.baseurl = ${GAME_SERVICE_BASEURL:localhost:8051}
analytic-service.baseurl = ${ANALYTIC_SERVICE_BASEURL: localhost:8054}
```

After this adjustment you can start the `edge-service` application.

Now you should be able to go to:
``` pwsh
http://localhost:8053/highscores
```
And you should see a similar output:

![localhost](https://user-images.githubusercontent.com/58487061/210269210-9888dbc5-2f13-4348-8256-a6a2cd4dbd22.png)

Lastly, the `feedback-service` can be started in a similar way like above. Set up the Docker container with the third MongoDB database:
``` pwsh
docker run --name feedback-mongodb -p 27019:27019 -d mongo
```
Add the connection info and adjust the port number to match the mongodb database in `application.properties` and now you can run the service locally.

## Testing

- [SwaggerUI](https://swagger.io/tools/swagger-ui/)

The service has `SwaggerUI` implemented for interactively testing and exposing the API. After setup the `SwaggerUI` will be running locally on: http://localhost:8053/swagger-ui.html

![swagger](https://user-images.githubusercontent.com/58487061/210269308-b9232468-ade5-4180-bbbd-b2c9717880b1.png)



# Newsletter Subscribtion Service

## Startup 
* Please Checkout The application,  run on your commandline the **_docker-compose.yml_** skript: as _**docker-compose build**_ 
This will create the Docker image of the service. After the image is created you can run _**docker-compose up**_. This
will start the application on `port 8088`.

## Technologies

* [Spring Boot](https://projects.spring.io/spring-boot/) - Microservice framework
* [Maven](https://maven.apache.org/) - Dependency management
* [Swagger IO](http://swagger.io/) -  Swagger API documentation

##API Documentation
* The API-Doc can be seen in this **_`URL: http://localhost:portnumber/apidoc`_**, you will be redirected to the given 
documentation.

## Actuator
* In order to see if the application is running currectly, you can assure this by making a get request on the
given url: **_`http://localhost:{portnumber}/actuator/health`_**, you shoul become a report of **UP**

## DB Entrypoint
 * In order to dumb data to the databes, an scv File with 1000 users are store in the classpath, this data will be 
 dumbed on the fly by the creation of the Database Table called (user). After the application has started you can check 
 the in Memory DB on this URL : **_`http://localhost:portnumber/h2-console/`_**

##DB Acces access
**`username:`** sa 
**`password:`**

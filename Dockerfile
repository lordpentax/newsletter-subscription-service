FROM java:8
FROM maven:alpine

WORKDIR /app

COPY . /app

RUN mvn -v
RUN mvn clean install -DskipTests
EXPOSE 8080

LABEL maintainer="cristian.fernandez@gmx.de"
ADD ./target/newsletter-subscription-service-0.0.1-SNAPSHOT.jar newsletter-subscription-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","newsletter-subscription-service-0.0.1-SNAPSHOT.jar"]

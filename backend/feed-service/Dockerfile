FROM maven:3.9.6-amazoncorretto-17 AS builder

COPY pom.xml /app/pom.xml
COPY ./feed-service/pom.xml /app/feed-service/pom.xml
COPY ./feed-service/src /app/feed-service/src

WORKDIR /app/feed-service

RUN mvn clean package

FROM amazoncorretto:17

COPY --from=builder /app/feed-service/target/*.jar /app/app.jar

EXPOSE 9004

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

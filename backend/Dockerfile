
    FROM maven:3.9.4-eclipse-temurin-17 AS build
    WORKDIR /app
    COPY . .
    RUN mvn clean package -DskipTests
    
    FROM openjdk:17-alpine
    ARG JAR_FILE=target/backend-0.0.1-SNAPSHOT.jar
    WORKDIR /app
    COPY --from=build /app/${JAR_FILE} app.jar
    EXPOSE 8080
    ENTRYPOINT ["java","-jar","/app/app.jar"]
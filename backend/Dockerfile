
FROM maven:3-eclipse-temurin-17-alpine AS deps
WORKDIR /app
COPY pom.xml /app
RUN mvn go-offline:resolve-dependencies
    
FROM maven:3-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY --from=deps /root/.m2/repository /root/.m2/repository
COPY . .
RUN mvn package -DskipTests -o

FROM maven:3-eclipse-temurin-17-alpine AS dev
WORKDIR /app
COPY --from=deps /root/.m2/repository /root/.m2/repository
COPY ./docker-entrypoint.sh /docker-entrypoint.sh
RUN apk add inotify-tools
RUN chmod +x /docker-entrypoint.sh
ENTRYPOINT ["/docker-entrypoint.sh"]

FROM openjdk:17-alpine
ARG JAR_FILE=target/backend-0.0.1-SNAPSHOT.jar
WORKDIR /app
COPY --from=build /app/${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
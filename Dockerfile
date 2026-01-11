# syntax=docker/dockerfile:1

FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml ./
COPY .mvn .mvn
RUN mvn -B dependency:go-offline

COPY src src
RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Render provides PORT env variable dynamically
ENV PORT=8080
EXPOSE ${PORT}

# Use shell form to expand PORT variable
ENTRYPOINT java -Dserver.port=${PORT} -jar /app/app.jar

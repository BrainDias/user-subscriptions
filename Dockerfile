FROM openjdk:17-jdk-alpine
LABEL authors="BrainDias"

WORKDIR /app

COPY build/libs/users-subscriptions-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
FROM maven:3-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /app
COPY --from=build ./app/target/*.jar ./app.jar
ARG RABBITMQ_SERVER=rabbitmqcredito
EXPOSE 8761
ENTRYPOINT java -jar app.jar

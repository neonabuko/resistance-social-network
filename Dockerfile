#FROM maven:3.8 as build
#
#WORKDIR /app/build
#
#COPY pom.xml .
#
#COPY /src/main ./src/main
#
#RUN mvn dependency:go-offline
#
#RUN mvn package -DskipTests

FROM alpine:3 as final

WORKDIR /app

RUN apk --no-cache add openjdk17-jre curl

COPY /target/resistance-social-network-0.0.1.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

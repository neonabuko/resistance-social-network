FROM maven:3.8 as build

WORKDIR /app/build

COPY pom.xml .

COPY /src ./src

RUN mvn package

FROM openjdk:17-alpine3.12 as final

WORKDIR /app

COPY --from=build /app/build/target/resistance-social-network-0.0.1.jar /app/app.jar

RUN apk add curl

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

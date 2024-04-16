FROM maven:3.9.6-amazoncorretto-21 as build

WORKDIR /app/build

COPY pom.xml .

RUN mvn dependency:go-offline

COPY . .

RUN mvn package -DskipTests

FROM alpine:3 as final

WORKDIR /app

RUN apk add openjdk21-jre curl

COPY src/main/resources /app/src/main/resources

ENV CLASSPATH=/app/src/main/resources

COPY --from=build /app/build/target/resistance-social-network-0.0.1.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

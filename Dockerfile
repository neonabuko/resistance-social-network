FROM openjdk:18-alpine

WORKDIR /app

COPY target/resistance-social-network-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

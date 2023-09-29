FROM openjdk:21

WORKDIR /app

COPY target/resistance-social-network-0.0.1-SNAPSHOT.jar /app/resistance-social-network.jar

ENTRYPOINT ["java", "-jar", "resistance-social-network.jar"]
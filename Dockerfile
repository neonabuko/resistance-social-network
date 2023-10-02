FROM maven:3.8 as build

WORKDIR /app/build

COPY pom.xml .

COPY /src ./src

RUN mvn package

FROM alpine:3 as final

WORKDIR /app

RUN apk --no-cache add openjdk17-jre curl

COPY --from=build /app/build/target/resistance-social-network-0.0.1.jar /app/app.jar

RUN rm -rf /var/cache/apk/* \
    && rm -rf /tmp/* \
    && rm -rf /var/tmp/* \
    && rm -rf /var/log/* \
    && rm -rf /usr/share/man/* \
    && rm -rf /usr/share/doc/*

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

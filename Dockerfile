FROM openjdk:11.0.11-9-jdk-slim

COPY target/gateway.jar gateway.jar

RUN mkdir /config
VOLUME /config

EXPOSE 8080

ENTRYPOINT java \
    -jar /gateway.jar \
    --spring.config.location=/config/application.yml \
    --logging.config=/config/logback.xml

FROM openjdk:11.0.11-9-jdk-slim

RUN mkdir /config /templates
VOLUME /config

COPY target/gateway.jar gateway.jar
COPY target/templates/* /templates/

EXPOSE 8080

ENTRYPOINT java \
    -jar /gateway.jar \
    --spring.config.location=/config/application.yml \
    --logging.config=/config/logback.xml

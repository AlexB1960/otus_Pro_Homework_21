FROM maven:3.9.14-eclipse-temurin-21

USER root

ENV PROFILE="ui"

WORKDIR /ui_tests
COPY . .

ENTRYPOINT ["sh", "-c", "mvn test -P $PROFILE"]
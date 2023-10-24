FROM openjdk:17-slim

LABEL authors="jonathan"

ENV JAVA_OPTS " -Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

WORKDIR application

COPY ./../../target/solvedex-blog-api-0.0.1-SNAPSHOT.jar  ./

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "solvedex-blog-api-0.0.1-SNAPSHOT.jar"]
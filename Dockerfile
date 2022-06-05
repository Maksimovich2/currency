FROM openjdk:11-jre-slim
MAINTAINER by.maksimovich
COPY build/libs/currency-0.0.1-SNAPSHOT.jar currency-0.0.1.jar
ENTRYPOINT ["java","-jar","/currency-0.0.1.jar"]
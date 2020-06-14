FROM openjdk:13-alpine
LABEL maintainer = sethuram
WORKDIR /usr/src/gateway
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8081
ENV CORE-SERVER-URL=http://localhost:8084 ADMINISTRATION-SERVER-URL=http://localhost:8085 LOCK-SERVER-URL=http://localhost:8083
ENTRYPOINT ["java","-jar","app.jar"]
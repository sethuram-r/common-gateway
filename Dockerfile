FROM adoptopenjdk/openjdk13:jre-13.0.2_8-alpine
LABEL maintainer = sethuram
WORKDIR /usr/src/gateway
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8081
ENV CORE_SERVER_URL=http://localhost:8084 ADMINISTRATION_SERVER_URL=http://localhost:8085 LOCK_SERVER_URL=http://localhost:8083
ENTRYPOINT ["java","-jar","app.jar"]
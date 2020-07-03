FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} graphql-springboot-mongodb-sample-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/graphql-springboot-mongodb-sample-0.0.1-SNAPSHOT.jar"]
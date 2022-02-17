FROM openjdk:13.0.2-jdk
COPY /target/kafka-0.0.1-SNAPSHOT.jar /app/kafka-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/kafka-0.0.1-SNAPSHOT.jar"]

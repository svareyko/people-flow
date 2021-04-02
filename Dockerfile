FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=target/people-flow-demo.jar
COPY ${JAR_FILE} people-flow-demo.jar
ENTRYPOINT ["java","-jar","/people-flow-demo.jar"]
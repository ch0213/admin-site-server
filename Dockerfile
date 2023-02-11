FROM adoptopenjdk/openjdk11
FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} ./app.jar
ENTRYPOINT ["java","${PROFILE_OPTS}","-jar","/app.jar"]

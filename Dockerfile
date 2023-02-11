FROM adoptopenjdk/openjdk11
ARG JAR_FILE=build/libs/admin-site-server-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "/app.jar"]

FROM amazoncorretto:17

COPY build.gradle /app/build.gradle
COPY settings.gradle /app/settings.gradle

COPY ./api/build.gradle /app/api/build.gradle
COPY ./api/build/libs/*.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

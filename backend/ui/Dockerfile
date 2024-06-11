FROM amazoncorretto:17

COPY build.gradle /app/build.gradle
COPY settings.gradle /app/settings.gradle

COPY ./ui/build.gradle /app/ui/build.gradle
COPY ./ui/build/libs/*.jar /app/app.jar

WORKDIR /app

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

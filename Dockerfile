FROM gradle:jdk16 AS build
COPY --chown=gradle:gradle . /src
WORKDIR /src
RUN gradle build --no-daemon

FROM openjdk:16
WORKDIR /app
COPY --from=build /src/build/libs/*.jar /app/Redirect.jar
CMD [ "java", "-jar", "Redirect.jar" ]
EXPOSE 4567

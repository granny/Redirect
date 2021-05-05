FROM gradle:4.7.0-jdk8-alpine AS build
COPY --chown=gradle:gradle . /src
WORKDIR /src
RUN gradle build --no-daemon


FROM openjdk:8
WORKDIR /app
COPY --from=build /src/build/libs/*.jar /app/Redirect.jar
CMD [ "java", "-jar", "Redirect.jar" ]
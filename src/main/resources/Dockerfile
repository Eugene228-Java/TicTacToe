FROM ubuntu:latest
LABEL authors="user"

ENTRYPOINT ["top", "-b"]

# ===== Build stage =====
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml ./
RUN mvn -q -e -B -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -q -e -B -DskipTests package

# ===== Runtime stage =====
FROM eclipse-temurin:21-jre
WORKDIR /app

RUN apt-get update \
    && apt-get install -y --no-install-recommends xvfb x11vnc \
    && rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/*.jar /app/app.jar

ENV DISPLAY=:99
ENV VNC_PORT=5900
ENV VNC_PASS=1234
ENV SCREEN_GEOMETRY=1280x720x24

EXPOSE 5900

ENTRYPOINT ["sh", "-c", "\
  Xvfb :99 -screen 0 ${SCREEN_GEOMETRY} -ac +extension RANDR +extension GLX & \
  x11vnc -display :99 -forever -shared -rfbport ${VNC_PORT} -passwd ${VNC_PASS} -nopw -noxrecord -noxfixes -noxdamage & \
  java -jar /app/app.jar \
"]

# ===== Build stage =====
FROM gradle:8.6-jdk21 AS build
WORKDIR /app

# копируем всё целиком
COPY . .

# собираем jar
RUN gradle clean build --no-daemon

# ===== Runtime stage =====
FROM eclipse-temurin:21-jre
WORKDIR /app

# копируем jar (Gradle кладёт его сюда)
COPY --from=build /app/build/libs/*.jar /app/app.jar

# для Swing + виртуального дисплея
RUN apt-get update \
    && apt-get install -y --no-install-recommends xvfb x11vnc \
    && rm -rf /var/lib/apt/lists/*

ENV DISPLAY=:99
ENV SCREEN_GEOMETRY=1280x720x24
ENV VNC_PASS=1234

EXPOSE 5900

ENTRYPOINT ["sh", "-c", "\
  Xvfb :99 -screen 0 ${SCREEN_GEOMETRY} & \
  x11vnc -display :99 -forever -shared -rfbport 5900 -passwd ${VNC_PASS} & \
  java -jar /app/app.jar \
"]

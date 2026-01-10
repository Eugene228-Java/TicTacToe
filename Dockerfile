# ===== Build stage =====
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY . .

RUN chmod +x gradlew

# ВАЖНО: wrapper + подробный лог
RUN ./gradlew clean build -x test --no-daemon --stacktrace --info

# ===== Runtime stage =====
FROM eclipse-temurin:21-jre
WORKDIR /app

RUN apt-get update \
    && apt-get install -y --no-install-recommends xvfb x11vnc \
    && rm -rf /var/lib/apt/lists/*

COPY --from=build /app/build/libs/*.jar /app/app.jar

ENV DISPLAY=:99
ENV SCREEN_GEOMETRY=1280x720x24
ENV VNC_PASS=1234

EXPOSE 5900

ENTRYPOINT ["sh", "-c", "\
  Xvfb :99 -screen 0 ${SCREEN_GEOMETRY} & \
  x11vnc -display :99 -forever -shared -rfbport 5900 -passwd ${VNC_PASS} & \
  java -jar /app/app.jar \
"]

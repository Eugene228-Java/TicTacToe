FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean build -x test --no-daemon

FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
# copy your artifact(s) from build stage

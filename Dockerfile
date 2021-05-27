FROM adoptopenjdk:8-jdk-hotspot
# FROM adoptopenjdk:11.0.4_11-jdk-hotspot

WORKDIR /app

RUN apt update && apt upgrade -y && apt install -y \
    git

# Used to cache the download of the gradle distribution zip
COPY gradlew gradlew
COPY gradle gradle
RUN ./gradlew --dry-run --no-daemon

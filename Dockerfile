# ---------- Stage 1: build (JDK + Gradle wrapper) ----------
FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace

# 1) Copy only Gradle files first -> dependencies are cached between builds
COPY gradlew settings.gradle build.gradle ./
COPY gradle gradle
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew && ./gradlew dependencies --no-daemon -q

# 2) Copy source code and build the executable JAR (tests run in Jenkins "Test" stage)
COPY src src
RUN ./gradlew bootJar -x test --no-daemon

# ---------- Stage 2: runtime (only JRE + JAR) ----------
FROM eclipse-temurin:17-jre
LABEL maintainer="Kali Aishah <kaliaishah@gmail.com>" \
      student.id="37830" \
      project="kali-aishah-student-api"
WORKDIR /app
COPY --from=build /workspace/build/libs/kali-aishah-student-api.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]


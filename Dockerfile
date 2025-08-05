# Stage 1: Build
FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/app.jar app.jar

# Exposer le port (Render utilise la variable PORT)
EXPOSE $PORT

# Lancer l'application avec le port dynamique de Render
ENTRYPOINT ["java", "-jar", "-Dserver.port=$PORT", "app.jar"]
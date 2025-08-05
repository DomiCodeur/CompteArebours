FROM openjdk:17-jdk-slim

# Copier le JAR compilé
COPY target/CompteArebours-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port (Render utilise la variable PORT)
EXPOSE $PORT

# Lancer l'application avec le port dynamique de Render
ENTRYPOINT ["java", "-jar", "-Dserver.port=$PORT", "app.jar"]
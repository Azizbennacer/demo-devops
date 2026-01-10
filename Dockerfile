# Image de base avec JDK 17 
FROM openjdk:17-jdk-slim 
# Métadonnées 
LABEL maintainer="votre.email@example.com" 
LABEL version="1.0"
LABEL description="Application Gestion Produits"

# Répertoire de travail
WORKDIR /app

# Copier le JAR
COPY target/gestion-produits-0.0.1-SNAPSHOT.jar app.jar

# Port exposé
EXPOSE 8080

# Commande de démarrage
CMD ["java", "-jar", "app.jar"]
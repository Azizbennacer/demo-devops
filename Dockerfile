# Image de base avec JDK 17 
FROM eclipse-temurin:17-jdk
# Métadonnées 
LABEL maintainer="votre.email@example.com" 
LABEL version="1.0"
LABEL description="Application Gestion Produits"

# Répertoire de travail
WORKDIR /app

# Copier le JAR
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Port exposé
EXPOSE 8080

# Commande de démarrage
CMD ["java", "-jar", "app.jar"]

ENV SPRING_PROFILES_ACTIVE=docker

# Utilise une image Maven pour builder le projet
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copie le fichier pom.xml et télécharge les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline

# Copie le code source du projet
COPY src ./src

# Build le projet Spring Boot
RUN mvn clean package -DskipTests

# Utilise une image JDK pour exécuter l'application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Installer Dockerize pour gérer les dépendances
RUN apt-get update && apt-get install -y wget && \
    wget https://github.com/jwilder/dockerize/releases/download/v0.6.1/dockerize-linux-amd64-v0.6.1.tar.gz && \
    tar -C /usr/local/bin -xzvf dockerize-linux-amd64-v0.6.1.tar.gz && \
    rm dockerize-linux-amd64-v0.6.1.tar.gz

# Copie le fichier JAR de l'application construit précédemment
COPY --from=build /app/target/*.jar app.jar

# Expose le port 3001 pour le back-end
EXPOSE 3001

# Définir le profil Spring à utiliser
ENV SPRING_PROFILES_ACTIVE=prod

# Utiliser java -jar pour démarrer l'application
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Point d'entrée par défaut (modifié via docker-compose)
ENTRYPOINT ["dockerize"]

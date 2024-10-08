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
FROM openjdk:17-jdk-alpine
WORKDIR /app

# Copie le fichier JAR de l'application construit précédemment
COPY --from=build /app/target/*.jar app.jar

# Copie le fichier dev.env dans le conteneur
#COPY dev.env /app/dev.env

# Copie le script de démarrage dans l'image
COPY start.sh /app/start.sh
RUN chmod +x /app/start.sh

# Copie le script wait-for-it.sh pour attendre que MySQL soit prêt
COPY scripts/wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

# Expose le port 3001 pour le back-end
EXPOSE 3001

# Utiliser le script de démarrage comme point d'entrée
ENTRYPOINT ["/app/start.sh"]
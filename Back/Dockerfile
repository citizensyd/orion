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

# Expose le port 8080 pour le back-end
EXPOSE 8080

# Commande pour démarrer l'application en lisant les secrets depuis /run/secrets/
ENTRYPOINT ["java", "-jar", "app.jar", \
  "--spring.datasource.url=$(cat /run/secrets/database_url)", \
  "--spring.datasource.username=$(cat /run/secrets/database_username)", \
  "--spring.datasource.password=$(cat /run/secrets/database_password)", \
  "--jwt.secret=$(cat /run/secrets/jwt_key)", \
  "--cors.allowed-origins=$(cat /run/secrets/cors_allowed_origins)"]

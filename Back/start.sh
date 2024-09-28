#!/bin/sh

# Lire les secrets à partir des fichiers montés par Docker Swarm
SPRING_DATASOURCE_URL=$(cat /run/secrets/database_url)
SPRING_DATASOURCE_USERNAME=$(cat /run/secrets/database_username)
SPRING_DATASOURCE_PASSWORD=$(cat /run/secrets/database_password)
JWT_SECRET=$(cat /run/secrets/jwt_key)
CORS_ALLOWED_ORIGINS=$(cat /run/secrets/cors_allowed_origins)

# Lancer l'application Java avec les secrets en tant que propriétés Spring Boot
exec java -jar app.jar \
  --spring.datasource.url=$SPRING_DATASOURCE_URL \
  --spring.datasource.username=$SPRING_DATASOURCE_USERNAME \
  --spring.datasource.password=$SPRING_DATASOURCE_PASSWORD \
  --jwt.secret=$JWT_KEY \
  --cors.allowed-origins=$CORS_ALLOWED_ORIGINS

#!/bin/sh

# Lire les secrets montés par Docker Swarm en production
if [ "$ENV" = "prod" ]; then
  if [ -f /run/secrets/database_url ] && [ -f /run/secrets/database_username ] && [ -f /run/secrets/database_password ] && [ -f /run/secrets/jwt_key ] && [ -f /run/secrets/cors_allowed_origins ]; then
    SPRING_DATASOURCE_URL=$(cat /run/secrets/database_url)
    SPRING_DATASOURCE_USERNAME=$(cat /run/secrets/database_username)
    SPRING_DATASOURCE_PASSWORD=$(cat /run/secrets/database_password)
    JWT_SECRET=$(cat /run/secrets/jwt_key)
    CORS_ALLOWED_ORIGINS=$(cat /run/secrets/cors_allowed_origins)
  else
    echo "Erreur : Un ou plusieurs secrets requis sont manquants."
    exit 1
  fi

  exec java -jar app.jar \
    --spring.datasource.url=$SPRING_DATASOURCE_URL \
    --spring.datasource.username=$SPRING_DATASOURCE_USERNAME \
    --spring.datasource.password=$SPRING_DATASOURCE_PASSWORD \
    --jwt.secret=$JWT_SECRET \
    --cors.allowed-origins=$CORS_ALLOWED_ORIGINS

else
  # En développement local, charger les variables d'environnement depuis dev.env
#  if [ -f "dev.env" ]; then
#    echo "Chargement des variables d'environnement depuis dev.env..."
#    export $(grep -v '^#' dev.env | xargs)
#  fi

  # Utiliser les variables d'environnement chargées ou définies dans docker-compose.yml
  exec java -jar app.jar \
    --spring.datasource.url=$DATABASE_URL \
    --spring.datasource.username=$DATABASE_USERNAME \
    --spring.datasource.password=$DATABASE_PASSWORD \
    --jwt.secret=$JWT_KEY \
    --cors.allowed-origins=$CORS_ALLOWED_ORIGINS
fi
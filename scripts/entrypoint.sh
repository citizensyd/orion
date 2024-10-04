#!/bin/bash

# Fonction pour vérifier si Nginx est démarré
function wait_for_nginx() {
  echo "Vérification du démarrage de Nginx..."
  while ! nc -z localhost 80; do
    echo "Nginx n'est pas encore démarré, en attente..."
    sleep 2
  done
  echo "Nginx est démarré."
}

# Démarrer Nginx en arrière-plan
echo "Démarrage de Nginx..."
nginx &

# Attendre que Nginx soit complètement démarré avant de procéder
wait_for_nginx

# Fonction pour obtenir le certificat Let's Encrypt
function get_cert() {
  echo "Obtention du certificat Let's Encrypt..."
  certbot certonly --webroot --webroot-path=/var/www/certbot --agree-tos --email guillard.fab@gmail.com -d site1.srv546916.hstgr.cloud --non-interactive

  # Si l'obtention du certificat a échoué, sortir avec une erreur
  if [ $? -ne 0 ]; then
    echo "Certbot n'a pas pu obtenir le certificat. Réessai dans 1 heure."
    return 1
  fi

  echo "Certificat obtenu avec succès."
  return 0
}

# Obtenir le certificat initial
if ! get_cert; then
  exit 1
fi

# Renouveler les certificats automatiquement toutes les 12 heures
while :; do
  echo "Vérification et renouvellement des certificats..."

  # Renouveler les certificats
  certbot renew --quiet

  # Redémarrer Nginx pour charger les nouveaux certificats si le renouvellement réussit
  if [ $? -eq 0 ]; then
    echo "Redémarrage de Nginx avec les nouveaux certificats..."
    nginx -s reload
  else
    echo "Le renouvellement des certificats a échoué. Nouvelle tentative dans 1 heure."
  fi

  # Attendre 12 heures avant la prochaine tentative de renouvellement
  sleep 12h
done

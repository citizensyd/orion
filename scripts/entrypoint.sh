#!/bin/bash

# Démarrer Nginx en arrière-plan
nginx &

# Attendre que Nginx soit complètement démarré avant de procéder
sleep 5

# Obtenir les certificats Let's Encrypt
certbot certonly --webroot --webroot-path=/var/www/certbot --agree-tos --email letsencryptcerbotngi.ntpjm@passmail.net -d site1.srv546916.hstgr.cloud --non-interactive

# Si l'obtention du certificat a échoué, sortir avec une erreur
if [ $? -ne 0 ]; then
  echo "Certbot n'a pas pu obtenir le certificat. Arrêt du script."
  exit 1
fi

# Renouveler les certificats automatiquement toutes les 12 heures
while :; do
  echo "Renouvellement des certificats..."

  # Renouveler les certificats
  certbot renew

  # Redémarrer Nginx pour charger les nouveaux certificats
  echo "Redémarrage de Nginx..."
  nginx -s reload

  # Attendre 12 heures avant la prochaine tentative de renouvellement
  sleep 12h
done

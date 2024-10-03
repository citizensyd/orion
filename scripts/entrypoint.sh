#!/bin/bash

# Démarrer Nginx en arrière-plan
nginx &

# Attendre que Nginx soit complètement démarré avant de procéder
while ! curl -s http://localhost >/dev/null; do
  echo "Attente du démarrage de Nginx..."
  sleep 2
done

# Obtenir les certificats Let's Encrypt
certbot certonly --webroot --webroot-path=/var/www/certbot --agree-tos --email guillard.fab@gmail.com -d site1.srv546916.hstgr.cloud --non-interactive

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
  if [ $? -ne 0 ]; then
    echo "Le renouvellement des certificats a échoué."
  else
    echo "Renouvellement des certificats réussi."
    # Redémarrer Nginx pour charger les nouveaux certificats
    nginx -s reload
  fi

  # Attendre 12 heures avant la prochaine tentative de renouvellement
  sleep 12h
done

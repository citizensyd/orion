#!/bin/bash

# Démarrer Nginx en arrière-plan
nginx &

# Obtenir les certificats Let's Encrypt
certbot certonly --webroot --webroot-path=/var/www/certbot --agree-tos --email letsencryptcerbotngi.ntpjm@passmail.net -d site1.srv546916.hstgr.cloud --non-interactive

# Renouveler les certificats automatiquement toutes les 12 heures
while :; do
  certbot renew
  sleep 12h
done

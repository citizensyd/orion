#!/bin/sh

# Charger et afficher un message pour indiquer le début du chargement des secrets
echo "Chargement des secrets depuis /run/secrets/..."

# Charger les secrets et afficher un message de succès ou d'erreur
if [ -f /run/secrets/dns_cloudflare_api_token ]; then
    export CF_API_TOKEN_SECRET=$(cat /run/secrets/dns_cloudflare_api_token)
    echo "Secret CF_API_TOKEN_SECRET chargé avec succès."
else
    echo "Erreur : Le secret CF_API_TOKEN_SECRET est introuvable dans /run/secrets/."
    exit 1
fi

if [ -f /run/secrets/dns_cloudflare_email ]; then
    export CF_Email_Cloudflare=$(cat /run/secrets/dns_cloudflare_email)
    echo "Secret CF_Email_Cloudflare chargé avec succès."
else
    echo "Erreur : Le secret CF_Email_Cloudflare est introuvable dans /run/secrets/."
    exit 1
fi

# Définir ACMESH_DNS_API_CONFIG dynamiquement avec les variables chargées
export ACMESH_DNS_API_CONFIG="DNS_API: dns_cf
CF_Token: $CF_API_TOKEN_SECRET
CF_Email: $CF_Email_Cloudflare
ACME_CA_URI: https://acme-staging-v02.api.letsencrypt.org/directory"

# Imprimer toutes les variables d'environnement pour vérification
echo "Variables d'environnement actuelles :"
env

# Lancer l'application principale et afficher un message de démarrage
echo "Démarrage de l'application..."
echo "CF_API_TOKEN_SECRET: $CF_API_TOKEN_SECRET"
echo "CF_Email_Cloudflare: $CF_Email_Cloudflare"
exec "$@"
echo "Fin du script d'entrée - cette ligne ne devrait pas s'afficher si exec fonctionne correctement."
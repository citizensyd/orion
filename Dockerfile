# Utiliser l'image de base Nginx officielle
FROM nginx:latest

#Supprime le fichier de configuration par défaut
RUN rm /etc/nginx/conf.d/default.conf

# Installer Certbot pour gérer les certificats Let's Encrypt
#RUN apt-get update && \
#    apt-get install -y certbot python3-certbot-nginx && \
#    rm -rf /var/lib/apt/lists/*

# Copier votre fichier nginx.conf dans le conteneur
COPY nginx.conf /etc/nginx/nginx.conf

# Copier vos fichiers de configuration de site (comme site1.conf)
COPY site1.conf /etc/nginx/conf.d/site1.conf

# Exposer le port 80 et 443 pour HTTP et HTTPS
EXPOSE 80 443

# Ajouter un script pour exécuter certbot et renouveler les certificats automatiquement
#COPY ./scripts/entrypoint.sh /entrypoint.sh
#RUN chmod +x /entrypoint.sh

# Commande par défaut pour démarrer Nginx et Certbot
#CMD ["/entrypoint.sh"]
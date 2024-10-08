# Utiliser l'image de base Nginx officielle
FROM nginx:latest

#Supprime le fichier de configuration par défaut
RUN rm /etc/nginx/conf.d/default.conf

# Installer Certbot pour gérer les certificats Let's Encrypt
#RUN apt-get update && \
#    apt-get install -y certbot python3-certbot-nginx && \
#    rm -rf /var/lib/apt/lists/*

#Copier votre fichier nginx.conf dans le conteneur
COPY nginx.conf /etc/nginx/nginx.conf

#Copier vos fichiers de configuration de site (comme site1.conf)
COPY site1.conf /etc/nginx/conf.d/site1.conf

# Copier le script wait-for-it.sh
#COPY scripts/wait-for-it.sh /wait-for-it.sh
#RUN chmod +x /wait-for-it.sh

#Exposer le port 80
EXPOSE 80

# Exposer le port 443 pour HTTPS
#EXPOSE 443

# Commande par défaut pour démarrer Nginx, en attendant que le backend et frontend soient disponibles
#CMD /wait-for-it.sh backend:3001 -- /wait-for-it.sh frontend:80 -- nginx -g "daemon off;"

# Ajouter un script pour exécuter certbot et renouveler les certificats automatiquement
#COPY ./scripts/entrypoint.sh /entrypoint.sh
#RUN chmod +x /entrypoint.sh

# Commande par défaut pour démarrer Nginx et Certbot
#CMD ["/entrypoint.sh"]

# Commande alternative pour isoler l'utilisation de Certbot et faire fonctionner le backend, le frontend, MySQL et Nginx d'abord
CMD nginx -g "daemon off;"
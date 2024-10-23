# Utiliser l'image de base Nginx officielle
FROM nginx:latest

#Supprime le fichier de configuration par défaut
RUN rm /etc/nginx/conf.d/default.conf

#Copier votre fichier nginx.conf dans le conteneur
#COPY nginx.conf /etc/nginx/nginx.conf

#Copier vos fichiers de configuration de site (comme site1.conf)
#COPY site1.conf /etc/nginx/conf.d/site1.conf

# Copier le script wait-for.sh-it.sh
COPY ../scripts/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

#Exposer le port 80
EXPOSE 80


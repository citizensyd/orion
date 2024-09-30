# Étape 1: Build de l'application Angular
FROM node:18 AS build
WORKDIR /app

# Copier package.json et package-lock.json pour installer les dépendances
COPY package*.json ./
RUN npm install

# Copier le reste du projet Angular
COPY . .

# Build du projet Angular en mode production
RUN npm run build -- --configuration production --project Front

# Étape finale: Utiliser les fichiers buildés dans Nginx
FROM nginx:latest

# Copier les fichiers Angular buildés dans le répertoire nginx/html
COPY --from=build /app/dist/front/browser/* /usr/share/nginx/html
COPY ./nginx.conf  /etc/nginx/conf.d/default.conf
# Exposer le port 80 pour le service HTTP
EXPOSE 80

# Commande par défaut pour démarrer Nginx
CMD ["nginx", "-g", "daemon off;"]

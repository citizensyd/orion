# Étape 1 : Build de l'application Angular
FROM node:18 AS build
WORKDIR /app

# Copier package.json et package-lock.json pour installer les dépendances
COPY package*.json ./
RUN npm install

# Copier le reste du projet Angular
COPY . .

# Build du projet Angular en mode production
RUN npm run build -- --configuration production --project Front

# Étape 2 : Utiliser http-server pour servir les fichiers statiques
FROM node:18 AS runtime
WORKDIR /app

# Installer http-server pour servir les fichiers en runtime
RUN npm install -g http-server

# Copier les fichiers Angular buildés dans le répertoire de travail
COPY --from=build /app/dist/front/browser /app

# Commande par défaut pour démarrer http-server
CMD ["http-server", "/app"]

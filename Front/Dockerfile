# Utilise une image Node.js pour builder le projet Angular
FROM node:18 AS build
WORKDIR /app

# Copie les fichiers package.json et package-lock.json
COPY package*.json ./
RUN npm install

# Copie tout le reste du code source Angular
COPY . .

# Build le projet Angular pour la production
RUN npm run build -- --configuration production --project Front

# Utilise une image Nginx pour servir l'application Angular
FROM nginx:alpine
COPY --from=build /Front/dist/front /usr/share/nginx/html

# Expose le port 80 pour le front-end
EXPOSE 80

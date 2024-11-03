FROM nginxproxy/acme-companion:latest

# Copier le script `entrypoint.sh` dans l'image
COPY entrypoint.sh /app/entrypoint.sh

# Rendre le script exécutable
RUN chmod +x /app/entrypoint.sh

# Définir le script comme point d'entrée
ENTRYPOINT ["/app/entrypoint.sh"]

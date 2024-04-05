-- Création de la table des utilisateurs
CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     email VARCHAR(255) UNIQUE NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
    );

-- Création de la table des thèmes
CREATE TABLE IF NOT EXISTS themes (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(255) UNIQUE NOT NULL,
                                      description TEXT NOT NULL
    );

-- Création de la table des abonnements
CREATE TABLE IF NOT EXISTS subscriptions (
                                             user_id BIGINT NOT NULL,
                                             theme_id BIGINT NOT NULL,
                                             PRIMARY KEY (user_id, theme_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (theme_id) REFERENCES themes(id)
    );

-- Création de la table des articles
CREATE TABLE IF NOT EXISTS articles (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    theme_id BIGINT,
    user_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (theme_id) REFERENCES themes(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
    );

-- Création de la table des commentaires
CREATE TABLE IF NOT EXISTS comments (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        content TEXT NOT NULL,
                                        article_id BIGINT,
                                        user_id BIGINT,
                                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        FOREIGN KEY (article_id) REFERENCES articles(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
    );


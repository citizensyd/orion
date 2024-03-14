-- Insertion d'utilisateurs
INSERT INTO users (email, username, password) VALUES ('user1@example.com', 'user1', 'hashedpassword1');
INSERT INTO users (email, username, password) VALUES ('user2@example.com', 'user2', 'hashedpassword2');

-- Insertion de thèmes informatiques
INSERT INTO themes (name) VALUES ('Programmation');
INSERT INTO themes (name) VALUES ('Sécurité Informatique');
INSERT INTO themes (name) VALUES ('Intelligence Artificielle');
INSERT INTO themes (name) VALUES ('Développement Web');
INSERT INTO themes (name) VALUES ('Base de données');
INSERT INTO themes (name) VALUES ('Systèmes d’exploitation');
INSERT INTO themes (name) VALUES ('Réseaux informatiques');
INSERT INTO themes (name) VALUES ('Cloud Computing');
INSERT INTO themes (name) VALUES ('Data Science');
INSERT INTO themes (name) VALUES ('UI/UX Design');

-- Programmation
INSERT INTO articles (title, content, theme_id, user_id) VALUES
    ('Introduction à Java', 'Dans cet article, nous explorons les bases de la programmation en Java. Java est un langage puissant, utilisé pour construire une variété d''applications, de la conception de jeux à des systèmes complexes d''entreprise. Apprendre Java ouvre de nombreuses portes.', (SELECT id FROM themes WHERE name = 'Programmation'), (SELECT id FROM users WHERE username = 'user1'));

-- Sécurité Informatique
INSERT INTO articles (title, content, theme_id, user_id) VALUES
    ('Fondamentaux de la Cybersécurité', 'La cybersécurité est essentielle dans le monde numérique actuel. Cet article couvre les principes fondamentaux pour protéger vos informations personnelles et professionnelles contre les cyberattaques. Découvrez les meilleures pratiques et comment rester en sécurité en ligne.', (SELECT id FROM themes WHERE name = 'Sécurité Informatique'), (SELECT id FROM users WHERE username = 'user2'));

-- Intelligence Artificielle
INSERT INTO articles (title, content, theme_id, user_id) VALUES
    ('Introduction à l''IA', 'L''intelligence artificielle transforme notre monde, de la santé à l''automobile. Cet article présente une introduction à l''IA, ses applications et son impact sur notre quotidien. Comprendre l''IA est crucial pour naviguer dans le futur technologique qui nous attend.', (SELECT id FROM themes WHERE name = 'Intelligence Artificielle'), (SELECT id FROM users WHERE username = 'user1'));

-- Développement Web
INSERT INTO articles (title, content, theme_id, user_id) VALUES
    ('Les tendances du développement Web en 2024', 'Cet article explore les dernières tendances en matière de développement Web, y compris les frameworks populaires, les meilleures pratiques de conception responsive et l''importance de l''accessibilité.', (SELECT id FROM themes WHERE name = 'Développement Web'), (SELECT id FROM users WHERE username = 'user2'));

-- Base de données
INSERT INTO articles (title, content, theme_id, user_id) VALUES
    ('Conception de bases de données efficaces', 'Apprenez à concevoir des bases de données efficaces. Cet article couvre les principes de normalisation, les modèles relationnels et non relationnels, et comment choisir la bonne base de données pour votre projet.', (SELECT id FROM themes WHERE name = 'Base de données'), (SELECT id FROM users WHERE username = 'user1'));

-- Systèmes d’exploitation
INSERT INTO articles (title, content, theme_id, user_id) VALUES
    ('Comprendre les systèmes d''exploitation modernes', 'Les systèmes d''exploitation sont au cœur de tous les ordinateurs. Cet article détaille leur fonctionnement, les différences entre les principaux OS et les nouvelles technologies émergentes dans ce domaine.', (SELECT id FROM themes WHERE name = 'Systèmes d’exploitation'), (SELECT id FROM users WHERE username = 'user2'));

-- Réseaux informatiques
INSERT INTO articles (title, content, theme_id, user_id) VALUES
    ('Fondamentaux des réseaux informatiques', 'Découvrez les concepts clés des réseaux informatiques, y compris la manière dont les données sont transmises sur Internet, les protocoles réseaux, et les stratégies pour sécuriser les communications.', (SELECT id FROM themes WHERE name = 'Réseaux informatiques'), (SELECT id FROM users WHERE username = 'user1'));

-- Cloud Computing
INSERT INTO articles (title, content, theme_id, user_id) VALUES
    ('Introduction au Cloud Computing', 'Le Cloud Computing révolutionne la manière dont nous stockons et accédons aux données. Cet article introduit les services cloud, leurs avantages, et comment ils transforment les entreprises et l''informatique personnelle.', (SELECT id FROM themes WHERE name = 'Cloud Computing'), (SELECT id FROM users WHERE username = 'user2'));

-- Data Science
INSERT INTO articles (title, content, theme_id, user_id) VALUES
    ('Les bases de la Data Science', 'La Data Science est un champ d''étude fascinant qui combine statistiques, analyse de données et apprentissage automatique. Cet article couvre les compétences fondamentales nécessaires pour démarrer dans ce domaine.', (SELECT id FROM themes WHERE name = 'Data Science'), (SELECT id FROM users WHERE username = 'user1'));

-- UI/UX Design
INSERT INTO articles (title, content, theme_id, user_id) VALUES
    ('Principes de base du UI/UX Design', 'Le design UI/UX est essentiel pour créer des expériences utilisateurs mémorables. Cet article explore les principes du design d''interface et d''expérience utilisateur, et comment ils contribuent au succès d''un produit.', (SELECT id FROM themes WHERE name = 'UI/UX Design'), (SELECT id FROM users WHERE username = 'user2'));
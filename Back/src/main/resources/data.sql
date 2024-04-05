-- Insertion d'utilisateurs
INSERT INTO users (email, username, password)
VALUES ('user1@example.com', 'user1', 'hashedpassword1');
INSERT INTO users (email, username, password)
VALUES ('user2@example.com', 'user2', 'hashedpassword2');

-- Insertion de thèmes informatiques
INSERT INTO themes (name, description)
VALUES ('Programmation',
        'La programmation est la fondation de l''innovation technologique moderne. Elle permet de créer des logiciels et applications variés, couvrant des systèmes d''exploitation, des applications mobiles aux jeux vidéos. Les langages de programmation tels que Python, Java, et C++ sont essentiels pour développer une base solide dans ce domaine.');

INSERT INTO themes (name, description)
VALUES ('Sécurité Informatique',
        'La sécurité informatique est vitale dans le monde numérique d''aujourd''hui. Elle implique la protection des systèmes informatiques contre les intrusions, les logiciels malveillants, et les cyberattaques. Les spécialistes de la sécurité se concentrent sur la création de réseaux sûrs, le chiffrement des données et la sensibilisation à la cybersécurité.');

INSERT INTO themes (name, description)
VALUES ('Intelligence Artificielle',
        'L''intelligence artificielle (IA) transforme notre façon de travailler, d''apprendre et de jouer. Elle englobe des domaines comme l''apprentissage automatique, la robotique, et le traitement du langage naturel. L''IA peut améliorer l''efficacité des décisions et créer des expériences utilisateur personnalisées.');
INSERT INTO themes (name, description)
VALUES ('Développement Web',
        'Le développement Web est un domaine fascinant qui combine la créativité et la technique pour construire des sites web et applications en ligne. Il englobe plusieurs aspects, dont le développement frontal avec HTML, CSS, JavaScript, et des frameworks comme Angular ou React, ainsi que le développement backend avec des langages comme PHP, Python ou Node.js. C''est une compétence essentielle dans le monde numérique pour construire des plateformes allant des blogs personnels aux plateformes e-commerce complexes.');

INSERT INTO themes (name, description)
VALUES ('Base de données',
        'Les bases de données sont le pilier du stockage et de la gestion des données dans le secteur technologique. Elles permettent de structurer, interroger et analyser des données, offrant ainsi le support nécessaire pour des décisions informées et des services interactifs. La compréhension des systèmes de gestion de bases de données relationnelles et non-relationnelles est cruciale pour architecturer des solutions de données robustes.');

INSERT INTO themes (name, description)
VALUES ('Systèmes d’exploitation',
        'Les systèmes d''exploitation sont au cœur des ordinateurs, fournissant une interface essentielle entre le matériel informatique et les logiciels. Ils gèrent les ressources systèmes, coordonnent les entrées et sorties, et fournissent des interfaces utilisateurs. Une bonne compréhension des systèmes d''exploitation est indispensable pour optimiser les performances et la sécurité des applications.');

INSERT INTO themes (name, description)
VALUES ('Réseaux informatiques',
        'Les réseaux informatiques permettent la connexion et la communication entre ordinateurs et périphériques. La connaissance des protocoles de réseau, des équipements de commutation et de routage, et des techniques de sécurisation des données en transit est vitale pour maintenir l''intégrité et la performance des réseaux d''entreprise et d''Internet.');

INSERT INTO themes (name, description)
VALUES ('Cloud Computing',
        'Le Cloud Computing révolutionne la manière dont nous stockons et accédons aux données et aux applications. Grâce à des services comme AWS, Azure et Google Cloud, les développeurs peuvent déployer et gérer des applications à grande échelle avec une plus grande flexibilité et efficacité que jamais.');

INSERT INTO themes (name, description)
VALUES ('Data Science',
        'La data science est un champ interdisciplinaire qui utilise des méthodes scientifiques, des processus et des systèmes pour extraire des connaissances ou un meilleur compréhension des données sous diverses formes, structurées ou non structurées. Cela inclut l''intelligence d''affaires, l''analyse de données, l''apprentissage automatique et de nombreuses autres méthodes analytiques.');

INSERT INTO themes (name, description)
VALUES ('UI/UX Design',
        'Le design UI/UX est un aspect vital du développement de produits numériques, qui englobe la conception de l''interface utilisateur (UI) et l''expérience utilisateur (UX). Un bon design UI/UX est essentiel pour assurer que les applications et les sites web sont intuitifs, accessibles et agréables à utiliser.');

-- Programmation
INSERT INTO articles (title, content, theme_id, user_id)
VALUES ('Introduction à Java',
        'Dans cet article, nous explorons les bases de la programmation en Java. Java est un langage puissant, utilisé pour construire une variété d''applications, de la conception de jeux à des systèmes complexes d''entreprise. Apprendre Java ouvre de nombreuses portes.',
        (SELECT id FROM themes WHERE name = 'Programmation'), (SELECT id FROM users WHERE username = 'user1'));

-- Sécurité Informatique
INSERT INTO articles (title, content, theme_id, user_id)
VALUES ('Fondamentaux de la Cybersécurité',
        'La cybersécurité est essentielle dans le monde numérique actuel. Cet article couvre les principes fondamentaux pour protéger vos informations personnelles et professionnelles contre les cyberattaques. Découvrez les meilleures pratiques et comment rester en sécurité en ligne.',
        (SELECT id FROM themes WHERE name = 'Sécurité Informatique'), (SELECT id FROM users WHERE username = 'user2'));

-- Intelligence Artificielle
INSERT INTO articles (title, content, theme_id, user_id)
VALUES ('Introduction à l''IA',
        'L''intelligence artificielle transforme notre monde, de la santé à l''automobile. Cet article présente une introduction à l''IA, ses applications et son impact sur notre quotidien. Comprendre l''IA est crucial pour naviguer dans le futur technologique qui nous attend.',
        (SELECT id FROM themes WHERE name = 'Intelligence Artificielle'),
        (SELECT id FROM users WHERE username = 'user1'));

-- Développement Web
INSERT INTO articles (title, content, theme_id, user_id)
VALUES ('Les tendances du développement Web en 2024',
        'Cet article explore les dernières tendances en matière de développement Web, y compris les frameworks populaires, les meilleures pratiques de conception responsive et l''importance de l''accessibilité.',
        (SELECT id FROM themes WHERE name = 'Développement Web'), (SELECT id FROM users WHERE username = 'user2'));

-- Base de données
INSERT INTO articles (title, content, theme_id, user_id)
VALUES ('Conception de bases de données efficaces',
        'Apprenez à concevoir des bases de données efficaces. Cet article couvre les principes de normalisation, les modèles relationnels et non relationnels, et comment choisir la bonne base de données pour votre projet.',
        (SELECT id FROM themes WHERE name = 'Base de données'), (SELECT id FROM users WHERE username = 'user1'));

-- Systèmes d’exploitation
INSERT INTO articles (title, content, theme_id, user_id)
VALUES ('Comprendre les systèmes d''exploitation modernes',
        'Les systèmes d''exploitation sont au cœur de tous les ordinateurs. Cet article détaille leur fonctionnement, les différences entre les principaux OS et les nouvelles technologies émergentes dans ce domaine.',
        (SELECT id FROM themes WHERE name = 'Systèmes d’exploitation'),
        (SELECT id FROM users WHERE username = 'user2'));

-- Réseaux informatiques
INSERT INTO articles (title, content, theme_id, user_id)
VALUES ('Fondamentaux des réseaux informatiques',
        'Découvrez les concepts clés des réseaux informatiques, y compris la manière dont les données sont transmises sur Internet, les protocoles réseaux, et les stratégies pour sécuriser les communications.',
        (SELECT id FROM themes WHERE name = 'Réseaux informatiques'), (SELECT id FROM users WHERE username = 'user1'));

-- Cloud Computing
INSERT INTO articles (title, content, theme_id, user_id)
VALUES ('Introduction au Cloud Computing',
        'Le Cloud Computing révolutionne la manière dont nous stockons et accédons aux données. Cet article introduit les services cloud, leurs avantages, et comment ils transforment les entreprises et l''informatique personnelle.',
        (SELECT id FROM themes WHERE name = 'Cloud Computing'), (SELECT id FROM users WHERE username = 'user2'));

-- Data Science
INSERT INTO articles (title, content, theme_id, user_id)
VALUES ('Les bases de la Data Science',
        'La Data Science est un champ d''étude fascinant qui combine statistiques, analyse de données et apprentissage automatique. Cet article couvre les compétences fondamentales nécessaires pour démarrer dans ce domaine.',
        (SELECT id FROM themes WHERE name = 'Data Science'), (SELECT id FROM users WHERE username = 'user1'));

-- UI/UX Design
INSERT INTO articles (title, content, theme_id, user_id)
VALUES ('Principes de base du UI/UX Design',
        'Le design UI/UX est essentiel pour créer des expériences utilisateurs mémorables. Cet article explore les principes du design d''interface et d''expérience utilisateur, et comment ils contribuent au succès d''un produit.',
        (SELECT id FROM themes WHERE name = 'UI/UX Design'), (SELECT id FROM users WHERE username = 'user2'));
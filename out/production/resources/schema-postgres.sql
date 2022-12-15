-- Créer une table pour stocker les données d'utilisateur,
-- y compris les informations d'identification (nom d'utilisateur, mot de passe) et les préférences d'unité de temps (jours, mois, années, dodos)
CREATE TABLE users (
       id SERIAL PRIMARY KEY, -- Identificateur unique pour chaque utilisateur
       username VARCHAR(255) UNIQUE NOT NULL, -- Nom d'utilisateur (doit être unique)
       password VARCHAR(255) NOT NULL, -- Mot de passe
       email VARCHAR(255) NOT NULL -- Adresse email de l'utilisateur
);

-- Créer une table pour stocker les dates sauvegardées par chaque utilisateur, en utilisant l'identificateur unique de l'utilisateur pour créer une relation avec la table users
CREATE TABLE saved_dates (
     id SERIAL PRIMARY KEY,
     user_id INTEGER NOT NULL,
     FOREIGN KEY(user_id) REFERENCES users(id),
     date DATE NOT NULL,
     time_unit VARCHAR(255) NOT NULL, -- Préférences d'unité de temps (jours, mois, années, dodos)
     name VARCHAR(255) NOT NULL
);


-- Ajouter des contraintes pour s'assurer que les données dans les tables sont valides et cohérentes
ALTER TABLE saved_dates ADD CONSTRAINT valid_time_unit CHECK (time_unit IN ('jours', 'mois', 'années', 'dodos'));
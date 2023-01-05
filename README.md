<h1 align="center">Compte à rebours</h1>

<div align="center">
Mon outil de calcul de temps restant
</div>

Ce projet est un outil en ligne qui permet aux utilisateurs de calculer le temps restant jusqu'à une date donnée, en jours, mois, années ou en nombre de dodos. Les utilisateurs peuvent s'enregistrer et sauvegarder leurs dates, et choisir l'unité de temps dans laquelle ils souhaitent afficher le temps restant.

Cet outil est développé avec une combinaison de Java et de React, en utilisant le backend Java pour gérer les calculs de date, les utilisateurs et les dates sauvegardées, et en utilisant React pour afficher l'interface utilisateur et prendre en charge la saisie de la date et les choix d'unité de temps par l'utilisateur. Une base de données PostgreSQL est utilisée pour stocker les dates sauvegardées par les utilisateurs et leurs préférences d'unité de temps.


## Table des matières
- [Stack technique](#stack-technique)
- [Configuration](#Configuration)
- [Déploiement](#Déploiement)
- [Contribution](#Contribution)
- [Licence](#Licence)
- [Contact](#Contact)

## Stack technique
Prérequis
Pour exécuter ce projet, vous aurez besoin de :

- Java 11 ou une version ultérieure
- Maven 3.6.3 ou une version ultérieure
- Un serveur PostgreSQL


## Configuration
1. Créez une base de données PostgreSQL et exécutez les commandes SQL suivantes pour créer les tables nécessaires :
``` sql
CREATE TABLE users (
id SERIAL PRIMARY KEY,
username VARCHAR(255) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL,
time_unit VARCHAR(255) NOT NULL
);

CREATE TABLE saved_dates (
id SERIAL PRIMARY KEY,
user_id INTEGER REFERENCES users(id),
date DATE NOT NULL,
name VARCHAR(255) NOT NULL
);

ALTER TABLE users ADD CONSTRAINT valid_time_unit CHECK (time_unit IN ('jours', 'mois', 'années', 'dodos'));

CREATE INDEX idx_saved_dates_user_id ON saved_dates (user_id);
``` 

2. Modifiez les paramètres de connexion à la base de données dans le fichier src/main/resources/application.properties en indiquant les informations de votre serveur PostgreSQL (nom d'utilisateur, mot de passe, etc.).

3. Buildez et exécutez le projet avec Maven :
``` bash
mvn clean package
java -jar target/CompteArebours-0.0.1-SNAPSHOT.jar
```

Le projet démarrera sur http://localhost:8080.

## Déploiement
Pour déployer ce projet sur un serveur, vous pouvez utiliser un conteneur Docker en créant une image à partir du fichier Dockerfile inclus dans le projet. Assurez-vous que votre serveur PostgreSQL est configuré et accessible depuis le conteneur Docker.

Pour créer l'image Docker, exécutez la commande suivante à partir de la racine du projet :
docker build -t CompteArebours.
Pour démarrer le conteneur, utilisez la commande suivante :
docker run -p 8080:8080 CompteArebours
Le projet sera maintenant accessible sur http://localhost:8080 (ou l'adresse IP de votre serveur, si vous le déployez sur un serveur distant).

## Contribution
Toute contribution est la bienvenue ! Si vous souhaitez contribuer au projet, veuillez ouvrir une pull request sur GitHub. Assurez-vous de suivre les bonnes pratiques de codage et de documenter vos changements.

## Licence
Ce projet est sous licence MIT. Vous pouvez utiliser, modifier et distribuer ce projet selon les termes de cette licence.

## Contact
Si vous avez des questions ou des suggestions, n'hésitez pas à me contacter via Github.





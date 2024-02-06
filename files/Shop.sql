-- ---------------------------------------------------------------------
-- - Reconstruction de la base de données - --
-- ---------------------------------------------------------------------
DROP DATABASE IF EXISTS Shop;
CREATE DATABASE Shop;
Use Shop;


-- -----------------------------------------------------------------------------
-- - Construction de la table des utilisateurs                               ---
-- -----------------------------------------------------------------------------
CREATE TABLE T_Users (
	idUser				int(4)		PRIMARY KEY AUTO_INCREMENT,
	login				varchar(20)	NOT NULL UNIQUE,
	password			varchar(20)	NOT NULL
) ENGINE = InnoDB;

INSERT INTO T_Users (idUser, login, password) VALUES ( 1, 'Ambre' ,	'1234' );
INSERT INTO T_Users (idUser, login, password) VALUES ( 2, 'Arthur',	'azerty' );

SELECT * FROM T_Users;


-- -----------------------------------------------------------------------------
-- - Construction de la table des clients	                                 ---
-- -----------------------------------------------------------------------------

CREATE TABLE T_Customers (
	idCustomer				int(4)		PRIMARY KEY AUTO_INCREMENT,
	name					varchar(30)	NOT NULL,
	firstName				varchar(30)	NOT NULL,
	email 					varchar(30)	NOT NULL unique,
	phone 					varchar(20)	,
	address					varchar(50)	,
	idUser					int(4)		NOT NULL,
	FOREIGN KEY (idUser)	REFERENCES T_Users(idUser)
) ENGINE = InnoDB;


-- ---------------------------------------------------------------------
-- - Construction de la table des Categories d'article - --
-- ---------------------------------------------------------------------
CREATE TABLE T_Categories (
	idCategory 			INT(4)		PRIMARY KEY AUTO_INCREMENT,
	catName				VARCHAR(30)	NOT NULL,
	description			VARCHAR(100)	NOT NULL
) ENGINE = InnoDB;

INSERT INTO T_Categories (idCategory, catName, description) VALUES ( 1, 'Dev Web'	, 'Formation axée sur le développement web');
INSERT INTO T_Categories (idCategory, catName, description) VALUES ( 2, 'CMS'	, 'Formation axée sur le système de gestion de contenu');
INSERT INTO T_Categories (idCategory, catName, description) VALUES ( 3, 'IA'	, 'Formation axée sur l''intelligence artificielle');
INSERT INTO T_Categories (idCategory, catName, description) VALUES ( 4, 'Cyber Sécurité', 'Formation axée sur la cyber sécurité');

SELECT * FROM T_Categories;


-- ---------------------------------------------------------------------
-- - Construction de la table des formations en vente - --
-- ---------------------------------------------------------------------
CREATE TABLE T_Formations (
	idFormation			INT(4)			PRIMARY KEY AUTO_INCREMENT,
	name				VARCHAR(50)		NOT NULL,
	description			VARCHAR(100)	NOT NULL,
	duration 			INT(4)			NOT NULL,
	remote				BOOLEAN			NOT NULL,
	unitaryPrice		FLOAT(8)		NOT NULL,
	idCategory			INT(4)			NOT NULL,
	FOREIGN KEY (idCategory)			REFERENCES T_Categories(idCategory)
) ENGINE = InnoDB;

INSERT INTO T_Formations ( name, description, duration, remote, unitaryPrice, idCategory ) VALUES ( 'Développement Web Full Stack', 'Formation complète sur le développement web, couvrant à la fois frontend et backend', 30, true, 1000, 1);
INSERT INTO T_Formations ( name, description, duration, remote, unitaryPrice, idCategory ) VALUES ( 'Marketing Digital Avancé', 'Approfondissement des stratégies de marketing en ligne, SEO SEM et médias sociaux', 20, false, 850, 2);
INSERT INTO T_Formations ( name, description, duration, remote, unitaryPrice, idCategory ) VALUES ( 'Data Science Fondamental', 'Introduction aux concepts fondamentaux de la science des données', 12, false, 2500, 3);
INSERT INTO T_Formations ( name, description, duration, remote, unitaryPrice, idCategory ) VALUES ( 'Gestion de Projet Agile ', 'Formation pratique sur les méthodologies de gestion de projet Agile', 42, false, 4900, 1);
INSERT INTO T_Formations ( name, description, duration, remote, unitaryPrice, idCategory ) VALUES ( 'Certificat AWS Cloud', 'Préparation de l''examen de certification AWS Cloud', 18, true, 1500, 1);
INSERT INTO T_Formations ( name, description, duration, remote, unitaryPrice, idCategory ) VALUES ( 'LeaderShip et gestion d''équipe', 'Développement des compétences en leadership et en gestion d''équipe', 8, false, 900, 1);
INSERT INTO T_Formations ( name, description, duration, remote, unitaryPrice, idCategory ) VALUES ( 'Développement d''Application Mobiles IOS', 'Apprentissage du développement d''applications IOS ', 24, true, 6000, 1);
INSERT INTO T_Formations ( name, description, duration, remote, unitaryPrice, idCategory ) VALUES ( 'Introduction à l''IA', 'Aperçu des concepts clés de l''intelligence artificielle', 22, false, 3200, 3);
INSERT INTO T_Formations ( name, description, duration, remote, unitaryPrice, idCategory ) VALUES ( 'Sécurité des Réseaux Informatique', 'Formation approfondie sur la sécurité des réseaux informatiques', 40, true, 8300, 4);

SELECT * FROM T_Formations;


-- ---------------------------------------------------------------------
-- - Construction de la table des commandes - --
-- ---------------------------------------------------------------------
CREATE TABLE T_Orders (
	idOrder				int(4)		PRIMARY KEY AUTO_INCREMENT,
	amount				float(4)	NOT NULL DEFAULT 0,
	dateOrder 			DATE		NOT NULL DEFAULT NOW(),
	idCustomer          INT(4)   	NOT NULL,
	FOREIGN KEY(idCustomer) REFERENCES T_Customers(idCustomer)
) ENGINE = InnoDB;



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
	IdUser				int(4)		PRIMARY KEY AUTO_INCREMENT,
	Login				varchar(20)	NOT NULL UNIQUE,
	Password			varchar(20)	NOT NULL
) ENGINE = InnoDB;

INSERT INTO T_Users (IdUser, Login, Password) VALUES ( 1, 'Ambre' ,	'1234' );
INSERT INTO T_Users (IdUser, Login, Password) VALUES ( 2, 'Arthur',	'azerty' );

SELECT * FROM T_Users;


-- -----------------------------------------------------------------------------
-- - Construction de la table des clients	                                 ---
-- -----------------------------------------------------------------------------

CREATE TABLE T_Customers (
	IdCustomer				int(4)		PRIMARY KEY AUTO_INCREMENT,
	Name					varchar(30)	NOT NULL,
	FirstName				varchar(30)	NOT NULL,
	Email 					varchar(30)	NOT NULL unique,
	Phone 					varchar(20)	,
	Address					varchar(50)	,
	IdUser					int(4)		NOT NULL,
	FOREIGN KEY (IdUser)	REFERENCES T_Users(IdUser)
) ENGINE = InnoDB;


-- ---------------------------------------------------------------------
-- - Construction de la table des Categories d'article - --
-- ---------------------------------------------------------------------
CREATE TABLE T_Categories (
	IdCategory 			INT(4)		PRIMARY KEY AUTO_INCREMENT,
	CatName				VARCHAR(30)	NOT NULL,
	Description			VARCHAR(100)	NOT NULL
) ENGINE = InnoDB;

INSERT INTO T_Categories (IdCategory, CatName, Description) VALUES ( 1, 'Dev Web'	, 'Formation axée sur le développement web');
INSERT INTO T_Categories (IdCategory, CatName, Description) VALUES ( 2, 'CMS'	, 'Formation axée sur le système de gestion de contenu');
INSERT INTO T_Categories (IdCategory, CatName, Description) VALUES ( 3, 'IA'	, 'Formation axée sur l''intelligence artificielle');
INSERT INTO T_Categories (IdCategory, CatName, Description) VALUES ( 4, 'Cyber Sécurité', 'Formation axée sur la cyber sécurité');

SELECT * FROM T_Categories;


-- ---------------------------------------------------------------------
-- - Construction de la table des formations en vente - --
-- ---------------------------------------------------------------------
CREATE TABLE T_Formations (
	IdFormation			INT(4)			PRIMARY KEY AUTO_INCREMENT,
	Name				VARCHAR(50)		NOT NULL,
	Description			VARCHAR(100)	NOT NULL,
	Duration 			INT(4)			NOT NULL,
	Remote				BOOLEAN			NOT NULL,
	UnitaryPrice		FLOAT(8)		NOT NULL,
	IdCategory			INT(4)			NOT NULL,
	FOREIGN KEY (IdCategory)			REFERENCES T_Categories(IdCategory)
) ENGINE = InnoDB;

INSERT INTO T_Formations ( Name, Description, Duration, Remote, UnitaryPrice, IdCategory ) VALUES ( 'Développement Web Full Stack', 'Formation complète sur le développement web, couvrant à la fois frontend et backend', 30, true, 1000, 1);
INSERT INTO T_Formations ( Name, Description, Duration, Remote, UnitaryPrice, IdCategory ) VALUES ( 'Marketing Digital Avancé', 'Approfondissement des stratégies de marketing en ligne, SEO SEM et médias sociaux', 20, false, 850, 2);
INSERT INTO T_Formations ( Name, Description, Duration, Remote, UnitaryPrice, IdCategory ) VALUES ( 'Data Science Fondamental', 'Introduction aux concepts fondamentaux de la science des données', 12, false, 2500, 3);
INSERT INTO T_Formations ( Name, Description, Duration, Remote, UnitaryPrice, IdCategory ) VALUES ( 'Gestion de Projet Agile ', 'Formation pratique sur les méthodologies de gestion de projet Agile', 42, false, 4900, 1);
INSERT INTO T_Formations ( Name, Description, Duration, Remote, UnitaryPrice, IdCategory ) VALUES ( 'Certificat AWS Cloud', 'Préparation de l''examen de certification AWS Cloud', 18, true, 1500, 1);
INSERT INTO T_Formations ( Name, Description, Duration, Remote, UnitaryPrice, IdCategory ) VALUES ( 'LeaderShip et gestion d''équipe', 'Développement des compétences en leadership et en gestion d''équipe', 8, false, 900, 1);
INSERT INTO T_Formations ( Name, Description, Duration, Remote, UnitaryPrice, IdCategory ) VALUES ( 'Développement d''Application Mobiles IOS', 'Apprentissage du développement d''applications IOS ', 24, true, 6000, 1);
INSERT INTO T_Formations ( Name, Description, Duration, Remote, UnitaryPrice, IdCategory ) VALUES ( 'Introduction à l''IA', 'Aperçu des concepts clés de l''intelligence artificielle', 22, false, 3200, 3);
INSERT INTO T_Formations ( Name, Description, Duration, Remote, UnitaryPrice, IdCategory ) VALUES ( 'Sécurité des Réseaux Informatique', 'Formation approfondie sur la sécurité des réseaux informatiques', 40, true, 8300, 4);

SELECT * FROM T_Formations;


-- ---------------------------------------------------------------------
-- - Construction de la table des commandes - --
-- ---------------------------------------------------------------------
CREATE TABLE T_Orders (
	IdOrder				int(4)		PRIMARY KEY AUTO_INCREMENT,
	Amount				float(4)	NOT NULL DEFAULT 0,
	DateOrder 			DATE		NOT NULL DEFAULT NOW(),
	IdCustomer          INT(4)   	NOT NULL,
	FOREIGN KEY(idCustomer) REFERENCES T_Customers(idCustomer)
) ENGINE = InnoDB;

-- ---------------------------------------------------------------------
-- - Construction de la table des items de commande - --
-- ---------------------------------------------------------------------
CREATE TABLE T_Order_Items (
	IdOrderItem			int(4)	PRIMARY KEY AUTO_INCREMENT,	
	IdFormation           INT(4)   NOT NULL,
	FOREIGN KEY(IdFormation) REFERENCES T_Formations(IdFormation),	
	Quantity			FLOAT(4) NOT NULL DEFAULT 1,
	UnitaryPrice		FLOAT(4)	NOT NULL DEFAULT 0,	
	IdOrder             INT(4)   NOT NULL,
	FOREIGN KEY(IdOrder) REFERENCES T_Orders(IdOrder)
) ENGINE = InnoDB;


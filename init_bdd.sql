drop table if exists est_emprunte cascade;
drop table if exists a_ecrit cascade;
drop table if exists livre cascade;
drop table if exists list_rouge cascade;
drop table if exists utilisateur cascade;
drop table if exists categorie cascade;
drop table if exists edition cascade;
drop table if exists auteur cascade;
drop table if exists oeuvre cascade;



create table auteur(
	id_auteur serial primary key,
	nom varchar(100),
	prenom varchar(100),
	annee int
);
create table oeuvre(
	id_oeuvre serial primary key,
	titre varchar(100),
	annee int
);


create table edition(
	ISBN bigint unsigned unique not null primary key,
	editeur varchar(100),
	annee int,
	id_oeuvre bigint unsigned not null,
	FOREIGN KEY (id_oeuvre) REFERENCES oeuvre(id_oeuvre)
);

create table categorie(
	id_categorie serial primary key,
	nom varchar(50),
	nombre_emprunt int,
	duree int
);

-- remplissage minimal --
INSERT into categorie(nom,nombre_emprunt,duree) values ("admin", 10, 28);
INSERT into categorie(nom,nombre_emprunt,duree) values ("client", 10, 28);

create table utilisateur(
	id_utilisateur serial primary key,
	nom varchar(100),
	prenom varchar(100),
	mail varchar(320) unique,
	password varchar(500),
	id_categorie bigint unsigned,
	FOREIGN KEY (id_categorie) REFERENCES categorie(id_categorie)
);

create table list_rouge(
	id_utilisateur bigint unsigned,
	date_debut date ,
	date_fin date ,
	PRIMARY KEY (id_utilisateur,date_debut,date_fin),
	FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur)
);

create table livre(
	id_livre serial primary key,
	ISBN bigint unsigned not null,
	id_utilisateur bigint unsigned,
	date_debut date,
	date_fin date,
	FOREIGN KEY (ISBN) REFERENCES edition(ISBN),
	FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur)
);

create table a_ecrit(
	id_oeuvre bigint unsigned not null, 
	id_auteur bigint unsigned not null,
	PRIMARY KEY (id_oeuvre,id_auteur),
	FOREIGN KEY (id_oeuvre) REFERENCES oeuvre(id_oeuvre),
	FOREIGN KEY (id_auteur) REFERENCES auteur(id_auteur)
);

create table est_emprunte(
	id_livre bigint unsigned,
	id_utilisateur bigint unsigned,
	date_emprunt date,
	PRIMARY KEY (ISBN,id_utilisateur,date_emprunt),
	FOREIGN KEY (ISBN) REFERENCES edition(ISBN),
	FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur)
);

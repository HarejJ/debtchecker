CREATE TABLE Oseba(
id INT AUTO_INCREMENT,
ime VARCHAR(50) NULL,
priimek VARCHAR(50) NULL,
e_mail VARCHAR(50) NULL,
vzdevek VARCHAR(50) NULL,
uporabnisko_ime VARCHAR(50) NULL,
uporabnisko_geslo VARCHAR(64) NULL,
CONSTRAINT id_oseba_pk PRIMARY KEY(id)
);

CREATE TABLE Placilo(
id INT AUTO_INCREMENT,
znesek DOUBLE NOT NULL,
datum BIGINT NOT NULL,
idPlacnik INT,
idPrejemnik INT,
CONSTRAINT id_placilo_pk PRIMARY KEY(id),
CONSTRAINT idPlacnik_placilo_fk FOREIGN KEY(idPlacnik) REFERENCES Oseba(id),
CONSTRAINT idPrejemnik_placilo_fk FOREIGN KEY(idPrejemnik) REFERENCES Oseba(id)
);

CREATE TABLE Dolg(
    kolicina DOUBLE NOT NULL,
    idOseba0 INT,
    idOseba1 INT,
	status BOOLEAN,
    CONSTRAINT idOseba0_dolg_fk FOREIGN KEY(idOseba0) REFERENCES Oseba(id),
    CONSTRAINT idOseba1_dolg_fk FOREIGN KEY(idOseba1) REFERENCES Oseba(id)
);
CREATE TABLE modelo (
	idmodelo INT NOT NULL AUTO_INCREMENT
	, idMarca INT NOT NULL
	, descricao VARCHAR(100)
	, CONSTRAINT pk_modelo PRIMARY KEY(idmodelo)
	, CONSTRAINT fk_modelo_marca FOREIGN KEY (idMarca) REFERENCES marca(idMarca)
);
CREATE TABLE fluxo (
	idfluxo INT NOT NULL AUTO_INCREMENT
    , idMovimento INT NOT NULL
    , CONSTRAINT pk_fluxo PRIMARY KEY (idfluxo)
    , constraint fk_fluxo_movimento FOREIGN KEY (idMovimento) REFERENCES movimento (idMovimento)
);
CREATE TABLE endereco
(
    idendereco INT          NOT NULL AUTO_INCREMENT,
    numero     INT          NULL,
    rua        VARCHAR(255) NULL,
    bairro     VARCHAR(100) NULL,
    cidade     VARCHAR(100) NULL,
    CONSTRAINT pk_endereco PRIMARY KEY (idendereco)
);

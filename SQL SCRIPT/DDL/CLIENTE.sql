CREATE TABLE cliente
(
    idcliente  INT          NOT NULL AUTO_INCREMENT,
    idEndereco INT          NOT NULL,
    nome       VARCHAR(255) NOT NULL,
    cpf        VARCHAR(14)  NOT NULL,
    rg         VARCHAR(14),
    email      VARCHAR(255),
    telefone   VARCHAR(45),
    CONSTRAINT pk_cliente PRIMARY KEY (idcliente),
    CONSTRAINT fk_cliente_endereco FOREIGN KEY (idEndereco) REFERENCES endereco (idEndereco)
);

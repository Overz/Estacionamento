CREATE TABLE contrato
(
    idcontrato INT        NOT NULL AUTO_INCREMENT,
    idPlano    INT        NOT NULL,
    idCliente  INT        NOT NULL,
    n_cartao   LONG       NOT NULL,
    dt_entrada DATETIME   NULL DEFAULT CURRENT_TIMESTAMP,
    dt_saida   DATETIME   NULL DEFAULT CURRENT_TIMESTAMP,
    ativo      TINYINT(1) NULL DEFAULT 1,
    valor      DECIMAL    NOT NULL,
    CONSTRAINT pk_contrato PRIMARY KEY (idcontrato),
    CONSTRAINT fk_contrato_plano FOREIGN KEY (idPlano) REFERENCES plano (idPlano),
    CONSTRAINT fk_contrato_cliente FOREIGN KEY (idCliente) REFERENCES cliente (idCliente)
);
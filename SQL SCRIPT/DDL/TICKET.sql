CREATE TABLE ticket
(
    idticket     INT    NOT NULL AUTO_INCREMENT,
    idCliente    INT    NOT NULL,
    n_ticket     LONG   NOT NULL,
    valor        DOUBLE NOT NULL,
    hr_validacao TIMESTAMP,
    CONSTRAINT pk_ticket PRIMARY KEY (idticket),
    CONSTRAINT fk_ticket_cliente FOREIGN KEY (idCliente) REFERENCES cliente (idCliente)
);
CREATE TABLE plano
(
    idplano   INT          NOT NULL AUTO_INCREMENT,
    tipo      ENUM ("MENSAL 30 CORRIDO", "SEMANAL", "PRÉ-PAGO", "ACUMULATIVO"),
    descricao VARCHAR(255) NOT NULL,
    CONSTRAINT fK_plano PRIMARY KEY (idplano)
);
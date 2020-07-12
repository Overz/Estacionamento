DROP DATABASE IF EXISTS dbestacionamento;
CREATE SCHEMA IF NOT EXISTS dbestacionamento DEFAULT CHARACTER SET utf8;
USE dbestacionamento;

CREATE TABLE `dbestacionamento`.`endereco`
(
    `id`     INT          NOT NULL AUTO_INCREMENT,
    `numero` INT          NULL,
    `rua`    VARCHAR(255) NULL,
    `bairro` VARCHAR(100) NULL,
    `cidade` VARCHAR(100) NULL,
    `uf`     VARCHAR(2)   NULL,
    CONSTRAINT `pk_endereco` PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`marca`
(
    `id`   INT          NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(100) NOT NULL,
    CONSTRAINT `pk_marca` PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`modelo`
(
    `id`        INT          NOT NULL AUTO_INCREMENT,
    `idMarca`   INT          NOT NULL,
    `descricao` VARCHAR(100) NOT NULL,
    CONSTRAINT `pk_modelo` PRIMARY KEY (`id`),
    CONSTRAINT `fk_modelo_marca` FOREIGN KEY (`idMarca`) REFERENCES `dbestacionamento`.`marca` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`carro`
(
    `id`       INT         NOT NULL AUTO_INCREMENT,
    `idModelo` INT         NOT NULL,
    `placa`    VARCHAR(7)  NOT NULL,
    `cor`      VARCHAR(45) NULL,
    CONSTRAINT `pk_carro` PRIMARY KEY (`id`),
    CONSTRAINT `fk_carro_modelo` FOREIGN KEY (`idModelo`) REFERENCES `dbestacionamento`.`modelo` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`cliente`
(
    `id`         INT          NOT NULL AUTO_INCREMENT,
    `idEndereco` INT          NOT NULL,
    `idCarro`    INT          NOT NULL,
    `nome`       VARCHAR(255) NOT NULL,
    `cpf`        VARCHAR(11)  NOT NULL UNIQUE,
    `rg`         VARCHAR(10)  NOT NULL,
    `email`      VARCHAR(255) NOT NULL,
    `telefone`   VARCHAR(11)  NOT NULL,
    CONSTRAINT `pk_cliente` PRIMARY KEY (`id`),
    CONSTRAINT `fk_cliente_endereco` FOREIGN KEY (`idEndereco`) REFERENCES `dbestacionamento`.`endereco` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_cliente_carro` FOREIGN KEY (`idCarro`) REFERENCES `dbestacionamento`.`carro` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`plano`
(
    `id`        INT                                                              NOT NULL AUTO_INCREMENT,
    `tipo`      ENUM ('MENSAL 30 CORRIDO', 'SEMANAL', 'PRÉ-PAGO', 'ACUMULATIVO') NOT NULL,
    `descricao` VARCHAR(255)                                                     NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`contrato`
(
    `id`          INT        NOT NULL AUTO_INCREMENT,
    `idPlano`     INT        NOT NULL,
    `idCliente`   INT        NOT NULL,
    `n_cartao`    LONG       NOT NULL,
    `dt_entrada`  DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `dt_validade` DATETIME   NULL     DEFAULT NULL,
    `ativo`       BOOLEAN    NOT NULL DEFAULT TRUE,
    `valor`       DOUBLE     NOT NULL,
    `tipoPgto`    VARCHAR(8) NOT NULL DEFAULT 'DINHEIRO',
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_contrato_plano1` FOREIGN KEY (`idPlano`) REFERENCES `dbestacionamento`.`plano` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_contrato_cliente1` FOREIGN KEY (`idCliente`) REFERENCES `dbestacionamento`.`cliente` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`ticket`
(
    `id`           INT        NOT NULL AUTO_INCREMENT,
    `placa`        VARCHAR(8) NULL     DEFAULT NULL,
    `n_ticket`     LONG       NOT NULL,
    `valor`        DOUBLE     NULL     DEFAULT NULL,
    `tipo`         VARCHAR(8) NOT NULL DEFAULT 'DINHEIRO',
    `hr_entrada`   TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `hr_validacao` TIMESTAMP  NULL     DEFAULT NULL,
    `statusTicket` BOOLEAN    NOT NULL DEFAULT TRUE,
    `validado`     BOOLEAN    NOT NULL DEFAULT FALSE,
    CONSTRAINT `pk_ticket` PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`movimento`
(
    `id`         INT      NOT NULL AUTO_INCREMENT,
    `idTicket`   INT      NULL,
    `idContrato` INT      NULL,
    `hr_entrada` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `hr_saida`   DATETIME NULL     DEFAULT NULL,
    `atual`      BOOLEAN  NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_movimento_ticket` FOREIGN KEY (`idTicket`) REFERENCES `dbestacionamento`.`ticket` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_movimento_contrato` FOREIGN KEY (`idContrato`) REFERENCES `dbestacionamento`.`contrato` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB;

# https://stackoverflow.com/questions/1131116/pdf-to-byte-array-and-vice-versa/1131178#1131178
# https://stackoverflow.com/questions/19970964/how-to-save-generated-pdf-files-to-mysql-database-using-java
CREATE TABLE `dbestacionamento`.`ticketlost`
(
    `id`        INT          NOT NULL AUTO_INCREMENT,
    `nome`      VARCHAR(255) NOT NULL,
    `cpf`       VARCHAR(11)  NOT NULL,
    `placa`     VARCHAR(8)   NOT NULL,
    `renavam`   VARCHAR(11)  NOT NULL,
    `tipoPgo`   VARCHAR(8)   NOT NULL,
    `documento` LONGBLOB     NOT NULL,
    CONSTRAINT `pk_idTicketLost` PRIMARY KEY (`id`)
) ENGINE = InnoDB;
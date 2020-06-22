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
    `placa`    VARCHAR(45) NOT NULL,
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
    `cpf`        VARCHAR(14)  NOT NULL,
    `rg`         VARCHAR(14)  NOT NULL,
    `email`      VARCHAR(255) NOT NULL,
    `telefone`   VARCHAR(45)  NOT NULL,
    CONSTRAINT `pk_cliente` PRIMARY KEY (`id`),
    CONSTRAINT `fk_cliente_endereco` FOREIGN KEY (`idEndereco`) REFERENCES `dbestacionamento`.`endereco` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_cliente_carro` FOREIGN KEY (`idCarro`) REFERENCES `dbestacionamento`.`carro` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`plano`
(
    `id`        INT(11)                                                          NOT NULL AUTO_INCREMENT,
    `tipo`      ENUM ('MENSAL 30 CORRIDO', 'SEMANAL', 'PRÉ-PAGO', 'ACUMULATIVO') NOT NULL,
    `descricao` VARCHAR(255)                                                     NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`contrato`
(
    `id`          INT(11)        NOT NULL AUTO_INCREMENT,
    `idPlano`     INT(11)        NOT NULL,
    `idCliente`   INT(11)        NOT NULL,
    `n_cartao`    MEDIUMTEXT     NOT NULL,
    `dt_entrada`  DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `dt_validade` DATETIME       NULL     DEFAULT NULL,
    `ativo`       TINYINT(1)     NOT NULL DEFAULT '1',
    `valor`       DECIMAL(10, 5) NOT NULL,
    `tipoPgto`    VARCHAR(45)    NOT NULL DEFAULT 'DINHEIRO',
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_contrato_plano1` FOREIGN KEY (`idPlano`) REFERENCES `dbestacionamento`.`plano` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_contrato_cliente1` FOREIGN KEY (`idCliente`) REFERENCES `dbestacionamento`.`cliente` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`ticket`
(
    `id`           INT(11)        NOT NULL AUTO_INCREMENT,
    `n_ticket`     MEDIUMTEXT     NOT NULL,
    `valor`        DECIMAL(10, 5) NULL     DEFAULT NULL,
    `tipo`         VARCHAR(45)    NOT NULL DEFAULT 'DINHEIRO',
    `hr_entrada`   TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `hr_validacao` TIMESTAMP      NULL     DEFAULT NULL,
    `statusTicket` TINYINT(1)     NOT NULL DEFAULT '1',
    `validado`     TINYINT(1)     NOT NULL DEFAULT '0',
    CONSTRAINT `pk_ticket` PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`movimento`
(
    `id`         INT        NOT NULL AUTO_INCREMENT,
    `idTicket`   INT        NULL,
    `idContrato` INT        NULL,
    `hr_entrada` DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `hr_saida`   DATETIME   NULL     DEFAULT NULL,
    `atual`      TINYINT(1) NOT NULL DEFAULT '1',
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
    `cpf`       VARCHAR(45)  NOT NULL,
    `placa`     VARCHAR(45)  NOT NULL,
    `renavam`   VARCHAR(45)  NOT NULL,
    `tipoPgo`   ENUM ('DINHEIRO', 'CARTÃO'),
    `documento` LONGBLOB     NOT NULL,
    CONSTRAINT `pk_idTicketLost` PRIMARY KEY (`id`)
) ENGINE = InnoDB;
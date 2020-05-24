DROP DATABASE IF EXISTS dbestacionamento;
CREATE SCHEMA IF NOT EXISTS dbestacionamento DEFAULT CHARACTER SET utf8;
USE dbestacionamento;

CREATE TABLE `dbestacionamento`.`endereco`
(
    `idendereco` INT          NOT NULL,
    `numero`     INT          NULL,
    `rua`        VARCHAR(255) NULL,
    `bairro`     VARCHAR(100) NULL,
    `cidade`     VARCHAR(100) NULL,
    CONSTRAINT `pk_endereco` PRIMARY KEY (`idendereco`)
) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`marca`
(
    `idmarca` INT          NOT NULL AUTO_INCREMENT,
    `nome`    VARCHAR(100) NOT NULL,
    CONSTRAINT `pk_marca` PRIMARY KEY (`idmarca`)
) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`modelo`
(
    `idmodelo`  INT         NOT NULL AUTO_INCREMENT,
    `idMarca`   INT         NOT NULL,
    `descricao` VARCHAR(45) NULL,
    CONSTRAINT `pk_modelo` PRIMARY KEY (`idmodelo`),
    CONSTRAINT `fk_modelo_marca` FOREIGN KEY (`idMarca`) REFERENCES `dbestacionamento`.`marca` (`idmarca`)
) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`carro`
(
    `idcarro`  INT NULL AUTO_INCREMENT,
    `idModelo` INT NOT NULL,
    `placa`    VARCHAR(45),
    `cor`      VARCHAR(45),
    CONSTRAINT `pk_carro` PRIMARY KEY (`idcarro`),
    CONSTRAINT `fk_carro_modelo` FOREIGN KEY (`idModelo`) REFERENCES `dbestacionamento`.`modelo` (`idmodelo`)
) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`cliente`
(
    `idcliente`  INT          NOT NULL AUTO_INCREMENT,
    `idEndereco` INT          NOT NULL,
    `idCarro`    INT          NOT NULL,
    `nome`       VARCHAR(255) NOT NULL,
    `cpf`        VARCHAR(14)  NOT NULL,
    `rg`         VARCHAR(14)  NULL,
    `email`      VARCHAR(255) NULL,
    `telefone`   VARCHAR(45)  NULL,
    CONSTRAINT `pk_cliente` PRIMARY KEY (`idcliente`),
    CONSTRAINT `fk_cliente_endereco` FOREIGN KEY (`idEndereco`) REFERENCES `dbestacionamento`.`endereco` (`idendereco`),
    CONSTRAINT `fk_cliente_carro` FOREIGN KEY (`idCarro`) REFERENCES `dbestacionamento`.`carro` (`idcarro`)
) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`ticket`
(
    `idticket`     INT                        NOT NULL AUTO_INCREMENT,
    `idCliente`    INT                        NULL,
    `n_ticket`     LONG                       NOT NULL,
    `valor`        DECIMAL                    NOT NULL DEFAULT 0.0,
    `tipo`         ENUM ("DINHEIRO","CARTÃO") NOT NULL DEFAULT "DINHEIRO",
    `hr_entrada`   TIMESTAMP                  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `hr_validacao` TIMESTAMP                  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `statusTicket` TINYINT(1)                 NOT NULL DEFAULT 1, #Ativo
    `validado`     TINYINT(1)                 NOT NULL DEFAULT 0, #Não Validado
    CONSTRAINT `pk_ticket` PRIMARY KEY (`idticket`),
    CONSTRAINT `fk_ticket_cliente` FOREIGN KEY (`idCliente`) REFERENCES `dbestacionamento`.`cliente` (`idcliente`)
) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`contrato`
(
    `idcontrato`  INT        NOT NULL AUTO_INCREMENT,
    `n_cartao`    LONG       NOT NULL,
    `dt_entrada`  DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `dt_validade` DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `ativo`       TINYINT(1) NOT NULL DEFAULT 1,
    `valor`       DECIMAL    NOT NULL,
    CONSTRAINT `pk_contrato` PRIMARY KEY (`idcontrato`)
) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`plano`
(
    `idplano`    INT                                                              NOT NULL AUTO_INCREMENT,
    `idContrato` INT                                                              NOT NULL,
    `idCliente`  INT                                                              NOT NULL,
    `tipo`       ENUM ("MENSAL 30 CORRIDO", "SEMANAL", "PRÉ-PAGO", "ACUMULATIVO") NOT NULL,
    `descricao`  VARCHAR(255)                                                     NOT NULL,
    CONSTRAINT `pk_plano` PRIMARY KEY (`idplano`),
    CONSTRAINT `fk_plano_contrato` FOREIGN KEY (`idContrato`) REFERENCES `dbestacionamento`.`contrato` (`idcontrato`),
    CONSTRAINT `fk_plano_cliente` FOREIGN KEY (`idCliente`) REFERENCES `dbestacionamento`.`cliente` (`idcliente`)
) ENGINE = InnoDB;

CREATE TABLE `dbestacionamento`.`movimento`
(
    `idmovimento` INT        NOT NULL AUTO_INCREMENT,
    `idTicket`    INT        NOT NULL,
    `idPlano`     INT        NULL,
    `hr_entrada`  DATETIME   NULL     DEFAULT CURRENT_TIMESTAMP,
    `hr_saida`    DATETIME   NULL     DEFAULT CURRENT_TIMESTAMP,
    `atual`       TINYINT(1) NOT NULL DEFAULT 1,
    CONSTRAINT `pk_movimento` PRIMARY KEY (`idmovimento`),
    CONSTRAINT `fk_movimento_ticket` FOREIGN KEY (`idTicket`) REFERENCES `dbestacionamento`.`ticket` (`idticket`),
    CONSTRAINT `fk_movimento_plano` FOREIGN KEY (`idPlano`) REFERENCES `dbestacionamento`.`plano` (`idplano`)
) ENGINE = InnoDB;
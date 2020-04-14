DROP DATABASE IF EXISTS dbestacionamento;
CREATE SCHEMA IF NOT EXISTS dbestacionamento DEFAULT CHARACTER SET utf8 ;
USE dbestacionamento;

CREATE TABLE endereco (
	idendereco INT NOT NULL AUTO_INCREMENT
	, numero INT NULL
	, rua VARCHAR(255) NULL
	, bairro VARCHAR(100) NULL
	, cidade VARCHAR(100) NULL
	, CONSTRAINT pk_endereco PRIMARY KEY (idendereco)
);

CREATE TABLE cliente (
	idcliente INT NOT NULL AUTO_INCREMENT
	, idEndereco INT NOT NULL
	, nome VARCHAR(255) NOT NULL
	, cpf VARCHAR(14) NOT NULL
	, rg VARCHAR(14)
	, email VARCHAR(255)
	, telefone VARCHAR (45)
	, CONSTRAINT pk_cliente PRIMARY KEY (idcliente)
	, CONSTRAINT fk_cliente_endereco FOREIGN KEY (idEndereco) REFERENCES endereco (idEndereco)   
);
  
CREATE TABLE marca (
   idmarca INT NOT NULL AUTO_INCREMENT
   , nome VARCHAR(100) NOT NULL
   , CONSTRAINT pk_marca PRIMARY KEY (idmarca)
 );
  
CREATE TABLE modelo (
	idmodelo INT NOT NULL AUTO_INCREMENT
	, idMarca INT NOT NULL
	, descricao VARCHAR(100)
	, CONSTRAINT pk_modelo PRIMARY KEY(idmodelo)
	, CONSTRAINT fk_modelo_marca FOREIGN KEY (idMarca) REFERENCES marca(idMarca)
);
  
CREATE TABLE carro (
  idcarro INT NOT NULL AUTO_INCREMENT
  , idModelo INT NOT NULL
  , idcliente INT NOT NULL
  , placa VARCHAR(10) NOT NULL
  , cor VARCHAR(45) DEFAULT "NÃO INFORMADO"
  , CONSTRAINT pk_carro PRIMARY KEY (idcarro)
  , CONSTRAINT fk_carro_modelo FOREIGN KEY (idModelo) REFERENCES modelo(idModelo)
  , CONSTRAINT fk_carro_cliente FOREIGN KEY (idcliente) REFERENCES cliente(idcliente)
);
  
CREATE TABLE ticket (
  idticket  INT NOT NULL AUTO_INCREMENT
  , idCliente INT NOT NULL
  , n_ticket LONG NOT NULL
  , valor DOUBLE NOT NULL
  , hr_validacao TIMESTAMP
  , CONSTRAINT pk_ticket PRIMARY KEY (idticket)
  , CONSTRAINT fk_ticket_cliente FOREIGN KEY (idCliente) REFERENCES cliente(idCliente)
);
  
 CREATE TABLE plano (
	idplano INT NOT NULL AUTO_INCREMENT
	, tipo ENUM("MENSAL 30 CORRIDO", "SEMANAL", "PRÉ-PAGO", "ACUMULATIVO")
	, descricao VARCHAR(255) NOT NULL
	, CONSTRAINT pK_plano PRIMARY KEY (idplano)
);
  
CREATE TABLE contrato (
  idcontrato INT NOT NULL AUTO_INCREMENT
  , idPlano INT NOT NULL
  , idCliente INT NOT NULL 
  , n_cartao LONG NOT NULL
  , dt_entrada DATETIME NULL DEFAULT CURRENT_TIMESTAMP
  , dt_saida DATETIME NULL DEFAULT CURRENT_TIMESTAMP
  , ativo TINYINT(1) NULL DEFAULT 1
  , valor DECIMAL NOT NULL 
  , CONSTRAINT pk_contrato PRIMARY KEY (idcontrato)
  , CONSTRAINT fk_contrato_plano FOREIGN KEY (idPlano) REFERENCES plano(idPlano)
  , CONSTRAINT fk_contrato_cliente FOREIGN KEY (idCliente) REFERENCES cliente(idCliente)
);
  
CREATE TABLE movimento (
	idmovimento INT NOT NULL AUTO_INCREMENT
	, idTicket INT NOT NULL
	, idPlano INT NOT NULL
	, hr_entrada DATETIME
	, hr_saida DATETIME
	, CONSTRAINT pk_movimento PRIMARY KEY (idmovimento)
	, CONSTRAINT fk_plano_movimento FOREIGN KEY (idPlano) REFERENCES plano (idPlano)
	, CONSTRAINT fk_ticket_movimento FOREIGN KEY (idTicket) REFERENCES ticket (idTicket)
);

CREATE TABLE fluxo (
	idfluxo INT NOT NULL AUTO_INCREMENT
    , idMovimento INT NOT NULL
    , CONSTRAINT pk_fluxo PRIMARY KEY (idfluxo)
    , constraint fk_fluxo_movimento FOREIGN KEY (idMovimento) REFERENCES movimento (idMovimento)
);
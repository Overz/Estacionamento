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
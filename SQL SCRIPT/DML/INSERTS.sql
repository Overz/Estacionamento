-- ENDERECO
INSERT INTO `dbestacionamento`.`endereco` (`idendereco`, `numero`, `rua`, `bairro`, `cidade`) VALUES ('1', '12345', 'SILVA JARDIM', 'PRAINHA', 'FLORIANOPOLIS');
INSERT INTO `dbestacionamento`.`endereco` (`idendereco`, `numero`, `rua`, `bairro`, `cidade`) VALUES ('2', '67890', 'AV. MAURO RAMOS', 'CENTRO', 'FLORIANOPOLIS');

-- Anteriormente Tabela Cliente

-- MARCA
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('1', 'Aston Martin');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('2', 'Audi');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('3', 'Bently');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('4', 'BMW');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('6', 'Caoa Chery');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('7', 'Chrysler');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('8', 'Citroën');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('9', 'Dodge');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('10', 'Ferrari');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('11', 'Fiat');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('12', 'Ford');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('13', 'Honda');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('14', 'Hyundai');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('15', 'Jac Motors');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('16', 'Jaguar');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('17', 'Jeep');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('18', 'Kia');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('19', 'Lamborghini');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('20', 'Land Rover');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('21', 'Lexus');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('22', 'Lifan');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('23', 'Maserati');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('24', 'Mercedes-Benz');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('25', 'Mini');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('26', 'Mitsubishi Motors');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('27', 'Nissan');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('28', 'Peugeot');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('29', 'Porche');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('30', 'Renault');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('31', 'Smart');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('32', 'Subaru');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('33', 'Suzuki');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('34', 'Toyota');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('35', 'Triumph');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('36', 'VolksWagen');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('37', 'Volvo');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`) VALUES ('38', 'Yamaha');

-- MODELO
INSERT INTO `dbestacionamento`.`modelo` (`idmodelo`, `idMarca`, `descricao`) VALUES ('1', '2', 'A5');
INSERT INTO `dbestacionamento`.`modelo` (`idmodelo`, `idMarca`, `descricao`) VALUES ('2', '4', '320i');

-- CARRO
INSERT INTO `dbestacionamento`.`carro` (`idcarro`, `idModelo`, /*`idcliente`,*/ `placa`, `cor`) VALUES ('1', '1', /*'1',*/ 'ABC-1234', 'BRANCO');
INSERT INTO `dbestacionamento`.`carro` (`idcarro`, `idModelo`, /*`idcliente`,*/ `placa`, `cor`) VALUES ('2', '2', /*'2',*/ '1A2B3C4', 'PRETO');

-- PLANO
INSERT INTO `dbestacionamento`.`plano` (`idplano`, `tipo`, `descricao`) VALUES ('1', 'MENSAL 30 CORRIDO', 'R$ 200.00');
INSERT INTO `dbestacionamento`.`plano` (`idplano`, `tipo`, `descricao`) VALUES ('2', 'SEMANAL', 'R$ 50.00');
INSERT INTO `dbestacionamento`.`plano` (`idplano`, `tipo`, `descricao`) VALUES ('3', 'PRÉ-PAGO', 'R$ 25.00');
INSERT INTO `dbestacionamento`.`plano` (`idplano`, `tipo`, `descricao`) VALUES ('4', 'ACUMULATIVO', 'ACUMULA VALORES A SEREM PAGOS AO FINAL DO MÊS');

-- CLIENTE
INSERT INTO `dbestacionamento`.`cliente` (`idcliente`, `idEndereco`, `idCarro`, `nome`, `cpf`, `rg`, `email`, `telefone`) VALUES ('1', '1', '1', 'JOAO', '12345678910', '9888777', 'JOAO@EMAIL.COM', '48988776655');
INSERT INTO `dbestacionamento`.`cliente` (`idcliente`, `idEndereco`, `idCarro`, `nome`, `cpf`, `rg`, `email`, `telefone`) VALUES ('2', '2', '2','CARLOS', '98765432199', '1222333444', 'CARLOS@EMAIL.COM', '11911223344');

-- CONTRATO
INSERT INTO `dbestacionamento`.`contrato` (`idcontrato`, `idPlano`, `idCliente`, `n_cartao`, `dt_entrada`, `dt_saida`, `ativo`, `valor`) VALUES ('1', '1', '1', '11122233344', '2020-01-01 12:00:00', '2020-01-01 17:00:00', '1', '200.00');
INSERT INTO `dbestacionamento`.`contrato` (`idcontrato`, `idPlano`, `idCliente`, `n_cartao`, `dt_entrada`, `dt_saida`, `ativo`, `valor`) VALUES ('2', '3', '2', '99988877766', '2020-01-01 12:00:00', '2020-01-01 17:00:00', '1', '200.00');

-- TICKET
INSERT INTO `dbestacionamento`.`ticket` (`idticket`, `idCliente`, `n_ticket`, `valor`, `hr_validacao`) VALUES ('1', '1', '0101010101', '0', '2020-01-01 13:00:00');
INSERT INTO `dbestacionamento`.`ticket` (`idticket`, `idCliente`, `n_ticket`, `valor`, `hr_validacao`) VALUES ('2', '2', '0202020202', '0', '2020-01-01 14:00:00');

-- MOVIMENTO
INSERT INTO `dbestacionamento`.`movimento` (`idmovimento`, `idTicket`, `idPlano`, `hr_entrada`, `hr_saida`) VALUES ('1', '1', '1', '2020-01-01 12:00:00', '2020-01-01 17:00:00');
INSERT INTO `dbestacionamento`.`movimento` (`idmovimento`, `idTicket`, `idPlano`, `hr_entrada`, `hr_saida`) VALUES ('2', '2', '3', '2020-01-01 12:00:00', '2020-01-01 17:00:00');

-- FLUXO
INSERT INTO `dbestacionamento`.`fluxo` (`idfluxo`, `idMovimento`) VALUES ('1', '1');
INSERT INTO `dbestacionamento`.`fluxo` (`idfluxo`, `idMovimento`) VALUES ('2', '2');

-- ENDERECO
INSERT INTO `dbestacionamento`.`endereco` (`idendereco`, `numero`, `rua`, `bairro`, `cidade`)
VALUES ('1', '10', 'TESTE 01', 'AAAA', 'CIDADE TESTE 1');
INSERT INTO `dbestacionamento`.`endereco` (`idendereco`, `numero`, `rua`, `bairro`, `cidade`)
VALUES ('2', '20', 'TESTE 02', 'BBBB', 'CIDADE TESTE 2');
INSERT INTO `dbestacionamento`.`endereco` (`idendereco`, `numero`, `rua`, `bairro`, `cidade`)
VALUES ('3', '30', 'TESTE 03', 'CCCC', 'CIDADE TESTE 3');
INSERT INTO `dbestacionamento`.`endereco` (`idendereco`, `numero`, `rua`, `bairro`, `cidade`)
VALUES ('4', '40', 'TESTE 04', 'DDDD', 'CIDADE TESTE 4');
INSERT INTO `dbestacionamento`.`endereco` (`idendereco`, `numero`, `rua`, `bairro`, `cidade`)
VALUES ('5', '50', 'TESTE 01', 'EEEE', 'CIDADE TESTE 5');

-- MARCA
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('1', 'Aston Martin');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('2', 'Audi');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('3', 'Bently');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('4', 'BMW');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('6', 'Caoa Chery');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('7', 'Chrysler');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('8', 'Citroën');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('9', 'Dodge');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('10', 'Ferrari');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('11', 'Fiat');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('12', 'Ford');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('13', 'Honda');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('14', 'Hyundai');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('15', 'Jac Motors');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('16', 'Jaguar');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('17', 'Jeep');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('18', 'Kia');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('19', 'Lamborghini');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('20', 'Land Rover');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('21', 'Lexus');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('22', 'Lifan');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('23', 'Maserati');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('24', 'Mercedes-Benz');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('25', 'Mini');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('26', 'Mitsubishi Motors');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('27', 'Nissan');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('28', 'Peugeot');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('29', 'Porche');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('30', 'Renault');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('31', 'Smart');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('32', 'Subaru');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('33', 'Suzuki');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('34', 'Toyota');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('35', 'Triumph');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('36', 'VolksWagen');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('37', 'Volvo');
INSERT INTO `dbestacionamento`.`marca` (`idmarca`, `nome`)
VALUES ('38', 'Yamaha');

-- MODELO
INSERT INTO `dbestacionamento`.`modelo` (`idmodelo`, `idMarca`, `descricao`)
VALUES ('1', '2', 'A5');
INSERT INTO `dbestacionamento`.`modelo` (`idmodelo`, `idMarca`, `descricao`)
VALUES ('2', '4', '320i');
INSERT INTO `dbestacionamento`.`modelo` (`idmodelo`, `idMarca`, `descricao`)
VALUES ('3', '10', 'F40');
INSERT INTO `dbestacionamento`.`modelo` (`idmodelo`, `idMarca`, `descricao`)
VALUES ('4', '13', 'Civic');
INSERT INTO `dbestacionamento`.`modelo` (`idmodelo`, `idMarca`, `descricao`)
VALUES ('5', '32', 'impreza WRX');

-- CARRO
INSERT INTO `dbestacionamento`.`carro` (`idcarro`, `idModelo`, `placa`, `cor`)
VALUES ('1', '1', 'ABC0001', 'BRANCO');
INSERT INTO `dbestacionamento`.`carro` (`idcarro`, `idModelo`, `placa`, `cor`)
VALUES ('2', '2', 'ABC0002', 'PRETO');
INSERT INTO `dbestacionamento`.`carro` (`idcarro`, `idModelo`, `placa`, `cor`)
VALUES ('3', '3', 'A0B0C0D', 'VERMELHO');
INSERT INTO `dbestacionamento`.`carro` (`idcarro`, `idModelo`, `placa`, `cor`)
VALUES ('4', '4', '0A0B0C0', 'VERDE');
INSERT INTO `dbestacionamento`.`carro` (`idcarro`, `idModelo`, `placa`, `cor`)
VALUES ('5', '5', 'ABC-1234', 'AMARELO');

-- CLIENTE
INSERT INTO `dbestacionamento`.`cliente` (`idcliente`, `idEndereco`, `idCarro`, `nome`, `cpf`, `rg`, `email`,
                                          `telefone`)
VALUES ('1', '1', '1', 'JOSE', '00000000010', '1234567', 'teste1@email.com', '48000000001');
INSERT INTO `dbestacionamento`.`cliente` (`idcliente`, `idEndereco`, `idCarro`, `nome`, `cpf`, `rg`, `email`,
                                          `telefone`)
VALUES ('2', '2', '2', 'MARIA', '00000000020', '4567891', 'teste2@email.com', '48000000002');
INSERT INTO `dbestacionamento`.`cliente` (`idcliente`, `idEndereco`, `idCarro`, `nome`, `cpf`, `rg`, `email`,
                                          `telefone`)
VALUES ('3', '3', '3', 'JOAO', '00000000030', '7891234', 'teste3@email.com', '48000000003');
INSERT INTO `dbestacionamento`.`cliente` (`idcliente`, `idEndereco`, `idCarro`, `nome`, `cpf`, `rg`, `email`,
                                          `telefone`)
VALUES ('4', '4', '4', 'CARLOS', '00000000040', '1011123', 'teste4@email.com', '48000000004');
INSERT INTO `dbestacionamento`.`cliente` (`idcliente`, `idEndereco`, `idCarro`, `nome`, `cpf`, `rg`, `email`,
                                          `telefone`)
VALUES ('5', '5', '5', 'ANA', '00000000050', '1213141', 'teste5@email.com', '48000000005');

-- TICKET
INSERT INTO `dbestacionamento`.`ticket` (`idticket`, `idCliente`, `n_ticket`, `valor`, `tipo`, `hr_entrada`,
                                         `statusTicket`, `validado`)
VALUES ('1', '1', '0101010101', '0', 'DINHEIRO', '2020-01-01 13:00:00', '1', '1');
INSERT INTO `dbestacionamento`.`ticket` (`idticket`, `idCliente`, `n_ticket`, `valor`, `tipo`, `hr_entrada`,
                                         `statusTicket`, `validado`)
VALUES ('2', '2', '0202020202', '0', 'CARTÃO', '2020-02-02 14:00:00', '1', '1');
INSERT INTO `dbestacionamento`.`ticket` (`idticket`, `idCliente`, `n_ticket`, `valor`, `tipo`, `hr_entrada`,
                                         `statusTicket`, `validado`)
VALUES ('3', '3', '0303030303', '0', 'DINHEIRO', now(), '1', '1');
INSERT INTO `dbestacionamento`.`ticket` (`idticket`, `idCliente`, `n_ticket`, `valor`, `tipo`, `hr_entrada`,
                                         `statusTicket`, `validado`)
VALUES ('4', '4', '0404040404', '0', 'CARTÃO', now(), '1', '1');
INSERT INTO `dbestacionamento`.`ticket` (`idticket`, `idCliente`, `n_ticket`, `valor`, `tipo`, `hr_entrada`,
                                         `statusTicket`, `validado`)
VALUES ('5', '5', '0505050505', '0', 'DINHEIRO', now(), '1', '1');
INSERT INTO `dbestacionamento`.`ticket` (`idticket`, `n_ticket`, `hr_entrada`, `statusTicket`,
                                         `validado`)
VALUES ('6', '0606060606', now(), '1', '0');

-- CONTRATO
INSERT INTO `dbestacionamento`.`contrato` (`idcontrato`, `n_cartao`, `dt_entrada`, `dt_validade`, `ativo`, `valor`)
VALUES ('1', '10000000001', '2020-01-01 12:00:01', '2020-01-5 17:00:05', '0', '200.00');
INSERT INTO `dbestacionamento`.`contrato` (`idcontrato`, `n_cartao`, `dt_entrada`, `dt_validade`, `ativo`, `valor`)
VALUES ('2', '10000000002', '2020-02-02 12:00:02', '2020-02-10 17:00:10', '0', '50.00');
INSERT INTO `dbestacionamento`.`contrato` (`idcontrato`, `n_cartao`, `dt_entrada`, `dt_validade`, `ativo`, `valor`)
VALUES ('3', '10000000003', '2020-03-03 12:00:03', '2020-03-15 17:00:15', '0', '25.00');
INSERT INTO `dbestacionamento`.`contrato` (`idcontrato`, `n_cartao`, `dt_entrada`, `dt_validade`, `ativo`, `valor`)
VALUES ('4', '10000000004', '2020-04-04 12:00:04', '2020-04-20 17:00:20', '0', '15.00');
INSERT INTO `dbestacionamento`.`contrato` (`idcontrato`, `n_cartao`, `dt_entrada`, `dt_validade`, `ativo`, `valor`)
VALUES ('5', '10000000005', '2020-05-05 12:00:05', '2020-05-25 17:00:25', '0', '200.00');

-- PLANO
INSERT INTO `dbestacionamento`.`plano` (`idplano`, `idContrato`, `idCliente`, `tipo`, `descricao`)
VALUES ('1', '1', '1', 'MENSAL 30 CORRIDO', 'R$ 200.00');
INSERT INTO `dbestacionamento`.`plano` (`idplano`, `idContrato`, `idCliente`, `tipo`, `descricao`)
VALUES ('2', '2', '2', 'SEMANAL', 'R$ 50.00');
INSERT INTO `dbestacionamento`.`plano` (`idplano`, `idContrato`, `idCliente`, `tipo`, `descricao`)
VALUES ('3', '3', '3', 'PRÉ-PAGO', 'R$ 25.00');
INSERT INTO `dbestacionamento`.`plano` (`idplano`, `idContrato`, `idCliente`, `tipo`, `descricao`)
VALUES ('4', '4', '4', 'ACUMULATIVO', 'ACUMULA VALORES A SEREM PAGOS AO FINAL DO MÊS');
INSERT INTO `dbestacionamento`.`plano` (`idplano`, `idContrato`, `idCliente`, `tipo`, `descricao`)
VALUES ('5', '5', '5', 'MENSAL 30 CORRIDO', 'R$ 200.00');

-- MOVIMENTO
INSERT INTO `dbestacionamento`.`movimento` (`idmovimento`, `idTicket`, `idPlano`, `hr_entrada`, `hr_saida`, `atual`)
VALUES ('1', '1', '1', '2020-01-01 12:00:00', now(), '1');
INSERT INTO `dbestacionamento`.`movimento` (`idmovimento`, `idTicket`, `idPlano`, `hr_entrada`, `hr_saida`, `atual`)
VALUES ('2', '2', '3', '2020-01-01 12:00:00', now(), '1');
INSERT INTO `dbestacionamento`.`movimento` (`idmovimento`, `idTicket`, `idPlano`, `hr_entrada`, `hr_saida`, `atual`)
VALUES ('3', '3', '2', '2020-01-01 12:00:00', now(), '1');
INSERT INTO `dbestacionamento`.`movimento` (`idmovimento`, `idTicket`, `idPlano`, `hr_entrada`, `hr_saida`, `atual`)
VALUES ('4', '4', '5', '2020-01-01 12:00:00', now(), '1');
INSERT INTO `dbestacionamento`.`movimento` (`idmovimento`, `idTicket`, `idPlano`, `hr_entrada`, `hr_saida`, `atual`)
VALUES ('5', '5', '4', '2020-01-01 12:00:00', now(), '1');
INSERT INTO `dbestacionamento`.`movimento` (`idmovimento`, `idTicket`, `hr_entrada`, `atual`)
VALUES ('6', '6', now(), '1');

-- FLUXO
#INSERT INTO `dbestacionamento`.`fluxo` (`idfluxo`, `idMovimento`) VALUES ('1', '1');
#INSERT INTO `dbestacionamento`.`fluxo` (`idfluxo`, `idMovimento`) VALUES ('2', '2');
#INSERT INTO `dbestacionamento`.`fluxo` (`idfluxo`, `idMovimento`) VALUES ('3', '3');
#INSERT INTO `dbestacionamento`.`fluxo` (`idfluxo`, `idMovimento`) VALUES ('4', '4');
#INSERT INTO `dbestacionamento`.`fluxo` (`idfluxo`, `idMovimento`) VALUES ('5', '5');


-- ENDERECO
INSERT INTO `dbestacionamento`.`endereco` (`id`, `numero`, `rua`, `bairro`, `cidade`)
VALUES ('1', '10', 'TESTE 01', 'AAAA', 'CIDADE TESTE 1');
INSERT INTO `dbestacionamento`.`endereco` (`id`, `numero`, `rua`, `bairro`, `cidade`)
VALUES ('2', '30', 'TESTE 03', 'CCCC', 'CIDADE TESTE 3');

-- MARCA
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('1', 'Aston Martin');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('2', 'Audi');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('3', 'Bently');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('4', 'BMW');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('6', 'Caoa Chery');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('7', 'Chrysler');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('8', 'Citroën');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('9', 'Dodge');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('10', 'Ferrari');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('11', 'Fiat');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('12', 'Ford');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('13', 'Honda');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('14', 'Hyundai');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('15', 'Jac Motors');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('16', 'Jaguar');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('17', 'Jeep');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('18', 'Kia');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('19', 'Lamborghini');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('20', 'Land Rover');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('21', 'Lexus');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('22', 'Lifan');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('23', 'Maserati');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('24', 'Mercedes-Benz');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('25', 'Mini');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('26', 'Mitsubishi Motors');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('27', 'Nissan');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('28', 'Peugeot');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('29', 'Porche');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('30', 'Renault');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('31', 'Smart');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('32', 'Subaru');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('33', 'Suzuki');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('34', 'Toyota');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('35', 'Triumph');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('36', 'VolksWagen');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('37', 'Volvo');
INSERT INTO `dbestacionamento`.`marca` (`id`, `nome`)
VALUES ('38', 'Yamaha');

-- MODELO
INSERT INTO `dbestacionamento`.`modelo` (`id`, `idMarca`, `descricao`)
VALUES ('1', '2', 'A5');
INSERT INTO `dbestacionamento`.`modelo` (`id`, `idMarca`, `descricao`)
VALUES ('2', '4', '320i');
INSERT INTO `dbestacionamento`.`modelo` (`id`, `idMarca`, `descricao`)
VALUES ('3', '10', 'F40');
INSERT INTO `dbestacionamento`.`modelo` (`id`, `idMarca`, `descricao`)
VALUES ('4', '13', 'Civic');
INSERT INTO `dbestacionamento`.`modelo` (`id`, `idMarca`, `descricao`)
VALUES ('5', '32', 'impreza WRX');

-- CARRO
INSERT INTO `dbestacionamento`.`carro` (`id`, `idModelo`, `placa`, `cor`)
VALUES ('1', '1', 'ABC0001', 'BRANCO');
INSERT INTO `dbestacionamento`.`carro` (`id`, `idModelo`, `placa`, `cor`)
VALUES ('2', '2', 'A0B0C0D', 'VERMELHO');

-- CLIENTE
INSERT INTO `dbestacionamento`.`cliente` (`id`, `idEndereco`, `idCarro`, `nome`, `cpf`, `rg`, `email`, `telefone`)
VALUES ('1', '1', '1', 'JOSE', '00000000010', '1234567', 'teste1@email.com', '48000000001');
INSERT INTO `dbestacionamento`.`cliente` (`id`, `idEndereco`, `idCarro`, `nome`, `cpf`, `rg`, `email`, `telefone`)
VALUES ('2', '2', '2', 'JOAO', '00000000030', '7891234', 'teste3@email.com', '48000000003');

-- TICKET
INSERT INTO `dbestacionamento`.`ticket` (`id`, `n_ticket`, `hr_entrada`, `statusTicket`, `validado`)
VALUES ('1', '0101010101', now(), '1', '0');
INSERT INTO `dbestacionamento`.`ticket` (`id`, `n_ticket`, `hr_entrada`, `statusTicket`, `validado`)
VALUES ('2', '0202020202', now(), '1', '0');

-- CONTRATO
INSERT INTO `dbestacionamento`.`contrato` (`id`, `n_cartao`, `dt_entrada`, `dt_validade`, `ativo`, `valor`, `tipoPgto`)
VALUES ('1', '10000000001', now(), adddate(now(), 365), '1', '200.00', 'DINHEIRO');
INSERT INTO `dbestacionamento`.`contrato` (`id`, `n_cartao`, `dt_entrada`, `dt_validade`, `ativo`, `valor`, `tipoPgto`)
VALUES ('2', '10000000003', now(), adddate(now(), 365), '1', '25.00', 'DINHEIRO');

-- PLANO
INSERT INTO `dbestacionamento`.`plano` (`id`, `idContrato`, `idCliente`, `tipo`, `descricao`)
VALUES ('1', '1', '1', 'MENSAL 30 CORRIDO', 'R$ 200.00');
INSERT INTO `dbestacionamento`.`plano` (`id`, `idContrato`, `idCliente`, `tipo`, `descricao`)
VALUES ('2', '2', '2', 'PRÉ-PAGO', 'R$ 25.00');

-- MOVIMENTO
INSERT INTO `dbestacionamento`.`movimento` (`id`, `idPlano`, `hr_entrada`, `atual`)
VALUES ('1', '1', now(), '1');
INSERT INTO `dbestacionamento`.`movimento` (`id`, `idPlano`, `hr_entrada`, `atual`)
VALUES ('2', '2', now(), '1');
INSERT INTO `dbestacionamento`.`movimento` (`id`, `idTicket`, `hr_entrada`, `atual`)
VALUES ('3', '1', now(), '1');
INSERT INTO `dbestacionamento`.`movimento` (`id`, `idTicket`, `hr_entrada`, `atual`)
VALUES ('4', '2', now(), '1');


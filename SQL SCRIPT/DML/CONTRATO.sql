-- CONTRATO
INSERT INTO `dbestacionamento`.`contrato` (`id`, `n_cartao`, `dt_entrada`, `dt_validade`, `ativo`, `valor`, `tipoPgto`)
VALUES ('1', '10000000001', now(), adddate(now(), 365), '1', '200.00', 'DINHEIRO');
INSERT INTO `dbestacionamento`.`contrato` (`id`, `n_cartao`, `dt_entrada`, `dt_validade`, `ativo`, `valor`, `tipoPgto`)
VALUES ('2', '10000000003', now(), adddate(now(), 365), '1', '25.00', 'DINHEIRO');
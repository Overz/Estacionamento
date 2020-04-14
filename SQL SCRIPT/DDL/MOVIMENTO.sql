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
## [Estacionamento](https://github.com/Overz/Estacionamento)

#### Descrição:
Projeto direcionado para o desenvolvimentode
uma aplicação usando OCR, para leitura de imagens com placas de carros
utilizando Python com Pytesseract e imagens Fixas em um arquivo
['files'](https://github.com/Overz/Estacionamento/tree/master/files) com nomes padrões do tipo
['bp1.png'](https://github.com/Overz/Estacionamento/blob/master/files/bp1.png)


#### Pré-requisitos:
Para utilizar esta aplicação, será necessario ter instalado [Python3.x](https://www.python.org/downloads/)

A Aplicação utiliza de comandos no terminal(Unix) ou CMD(Windows) do genero: `python3 <script_path> <image_path>`,
utilizando um `Buffered Line` no retorno, e adicionando em uma ['lista'](https://github.com/Overz/Estacionamento/blob/5edcd49723b6fb1d3607c47d28174e78c0864f09/src/util/tesseract/OCR.java#L85)
(ArrayList), onde de tempos em tempos com um
[Timer](https://github.com/Overz/Estacionamento/blob/5edcd49723b6fb1d3607c47d28174e78c0864f09/src/util/tesseract/OCR.java#L56),
`simula a entrada` de um novo cliente, verificando se já existe esse cliente, ou não. Caso exista, insere automaticamente para a tabela de exibição da tela principal,
vinculando com a "ultima movimentação" de diarista (Ticket).
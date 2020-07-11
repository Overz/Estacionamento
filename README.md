##[Estacionamento](https://github.com/Overz/Estacionamento)

#### Descrição:
Projeto direcionado para o desenvolvimentode
uma aplicação usando OCR, para leitura de imagens com placas de carros
utilizando Python com Pytesseract e imagens Fixas em um arquivo 'files' com nomes padrões do tipo 'bp1.png'


#### Pré-requisitos:
Para utilizar esta aplicação, será necessario ter instalado [Python3.x](https://www.python.org/downloads/)

A Aplicação utiliza de comandos no terminal(Unix) ou CMD(Windows) do genero: python3 <script_path> <image_path>,
utilizando um Buffered Line, e adicionando em uma lista (ArrayList), onde de tempos em tempos com um Timer, simula a entrada de um novo cliente,
verificando se já existe esse cliente, ou não. Caso exista, insere automaticamente para a tabela de exibição da tela principal,
vinculando com a "ultima movimentação"
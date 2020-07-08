import re
import sys

import pytesseract
from PIL import Image

teste1 = '/home/cris/tess/blackplate/bp1.jpeg'
teste2 = '/home/cris/tess/blueplate/bp1.png'


# https://www.w3schools.com/python/python_regex.asp
# https://nanonets.com/blog/ocr-with-tesseract/
# python3 doOcr.py wp5.png
def main(path):
    if path != "":
        try:
            matches(path)
        except Exception as ex2:
            print("Py: Error trying process the Image")
            print(ex2)
        finally:
            sys.exit(2)
    else:
        print("Py: Erro, can't proccess a empity path!")


def replaces(string):
    return str(string).replace("[", "").replace("]", "").replace("'", "").replace("-", "")


# Leitura da Imagem
def matches(path):  # Utilizar o 'path' no lugar do 'teste'
    regexMerco = '[A-Z0-9]{7}'  # Regex para placas mercosul
    regexCommon = '[A-Z-0-9]{8}'  # Regex para placas comuns
    image_text = pytesseract.image_to_string(Image.open(str(path)))  # Realiza a Leitura
    # print(image_text, '\n original \n')
    firstMatch = str(replaces(re.findall(regexMerco, image_text)))  # Match com o regex mercosul, e replace
    secondMatch = str(replaces(re.findall(regexCommon, image_text)))  # Math com regex comum, e replace
    # print(str(re.findall(regexMerco, image_text)).replace("[", "").replace("]", "").replace("'", "").replace("-", "")) # Leitura realizada anteriormente
    if firstMatch:
        print(firstMatch)
        # print('First Match')
    else:
        print(secondMatch)
        # print('Second Match')


# Main Method
if __name__ == '__main__':
    try:
        main(path=sys.argv[1])  # Remover as aspas ao utilizar no programa
    except Exception as ex1:
        print("Py: How to run: python <script> <args> \n<args> need to be a image .png, .jpeg, .jpg")
        print(ex1)

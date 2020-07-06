import re
import sys

import pytesseract
from PIL import Image

teste = '/home/cris/Área de Trabalho/tesseract/wp3.png'


# https://www.w3schools.com/python/python_regex.asp
# python3 doOcr.py wp5.png
def main(path):
    if path != "":
        try:
            regex = '[A-Z0-9]{7}'
            image_text = pytesseract.image_to_string(Image.open(str(path)))
            print(str(re.findall(regex, image_text)).replace("[", "").replace("]", "").replace("'", ""))
        except Exception as ex2:
            print("Error trying process the Image")
            print(ex2)
        finally:
            sys.exit(2)
    else:
        print("Erro, can't proccess a empity path!")


if __name__ == '__main__':
    try:
        main(path=sys.argv[1])
    except Exception as ex1:
        print("How to run: python <script> <args> \n<args> need to be a image .png, .jpeg, .jpg")
        print(ex1)

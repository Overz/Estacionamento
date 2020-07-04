import os
import re
import sys

import pytesseract
from PIL import Image


def main(path):
    regex = '[A-Z0-9]{7}'
    x = pytesseract.image_to_string(Image.open(str(path)))
    matched = re.search(regex, x)
    print(str(matched).strip()[38:45])


if __name__ == '__main__':
    main(path=sys.argv[1])


def excluir(path):
    if os.path.exists(path):
        os.remove(path)
        print('Removed: ' + path)
    else:
        print('File Not Found: ' + path)


if __name__ == '__excluir__':
    excluir(path=sys.argv[1])

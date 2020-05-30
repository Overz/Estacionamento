package util.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import model.vo.movimentos.MovimentoVO;
import util.helpers.Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class PdfComprovante {

    private static final String EXTENSAO = ".pdf";
    private static final DateTimeFormatter DAY_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter HOUR_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss - a");
    private Font mainFont, fontForAll;
    private Chunk mainChunk, secondChunk, thirdChunk, fourthChunk, fiveChunk;
    private Phrase mainFrase, secondFrase, thirdFrase;
    private Paragraph mainPrgf, secondPrgf, thirdPrgf, prgfCodeBar, finalPrgf;
    private Path path;
    private Image img;
    private MovimentoVO m;
    private String caminho;

    public PdfComprovante(String caminho, MovimentoVO movimento) {
        this.caminho = caminho;
        this.m = movimento;
        this.mainFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLACK);
        this.fontForAll = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.BLACK);
    }

    public PdfComprovante() {
    }

    /**
     * Instancia os atributos usados na criação do PDF
     */
    private void instanciarAtributos() {
        mainChunk = new Chunk();
        secondChunk = new Chunk();
        thirdChunk = new Chunk();
        fourthChunk = new Chunk();
        fiveChunk = new Chunk();

        mainFrase = new Phrase();
        secondFrase = new Phrase();
        thirdFrase = new Phrase();

        mainPrgf = new Paragraph();
        secondPrgf = new Paragraph();
        prgfCodeBar = new Paragraph();
        thirdPrgf = new Paragraph();
        finalPrgf = new Paragraph();
    }

    /**
     * Método principal para gerar um PDF;
     */
    public int gerarPdf() {
        int i = 0;
        try {
            Element e1, e2, e3, e4, e5;
            path = Paths.get(ClassLoader.getSystemResource("img/code-bar-GG-1.png").toURI());

            // Instancia o documento PDF
            Document document = new Document();
            document.setMargins(0, 0, 0, 0);
            // Caminho + Nome do Pdf
            PdfWriter.getInstance(document, new FileOutputStream(caminho + EXTENSAO));

            Rectangle rect = new Rectangle(85, 200);

            document.open(); // Necessario para abrir, e digitar os valores dentro do PDF

            // Inicia os Atributos
            this.instanciarAtributos();

            // Adiciona o Cabeçalho Paragrafo(Header) - Primeiro Paragrafo
            e1 = this.addHeader(mainChunk, secondChunk, mainPrgf);
            if (e1 != null) {
                document.add(e1);
            } else {
                i = 1;
            }

            // Adiciona o Informações descritas após o cabeçalho (Informação Adicional) - Segundo Paragrafo
            e2 = this.addInformation(thirdChunk, mainFrase, secondPrgf);
            if (e2 != null) {
                document.add(e2);
            } else {
                i = 2;
            }

            // Adiciona os Dados do Ticket selecionado ao Entrar (Ticket, Data, Hora) - Terceiro Paragrafo
            e3 = this.addInitialData(fourthChunk, secondFrase, thirdPrgf);
            if (e3 != null) {
                document.add(e3);
            } else {
                i = 3;
            }

            // Adiciona o Codigo de Barras (Code-Bar)
            img = Image.getInstance(path.toAbsolutePath().toString());
            e4 = this.addCodeBar(prgfCodeBar, img);
            if (e4 != null) {
                Paragraph paragraph = new Paragraph("   ");
                document.add(e4);
                document.add(paragraph);
            } else {
                i = 4;
            }

            // Adiciona os Dados do Ticket selecionado ao Saír (Data, Hora, Valor) - Paragrafo final
            e5 = this.addFinalData(fiveChunk, thirdFrase, finalPrgf);
            if (e5 != null) {
                document.add(e5);
            } else {
                i = 5;
            }

            // Cria a Tabela para o PDF
//            PdfPTable table = new PdfPTable(3);
//            document.add(this.addTable(table));

            // Adiciona cripitografia (User/Password)
//            this.encryption();

            document.close(); // Necessario fechar o documento para que os dados sejam salvos

        } catch (DocumentException | URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * Adiciona o Titulo do PDF
     *
     * @param mainChunk Chunk
     * @param mainPrgf  Paragraph
     * @return Element
     */
    private Element addHeader(Chunk mainChunk, Chunk secondChunk, Paragraph mainPrgf) {
        mainChunk.append("Senac - Easy Way\n");
        mainChunk.setFont(mainFont);
        secondChunk.append("Estacionamento\n\n\n");
        secondChunk.setFont(fontForAll);
        mainPrgf.add(mainChunk);
        mainPrgf.add(secondChunk);
        mainPrgf.setAlignment(Element.ALIGN_CENTER);
        return mainPrgf;
    }

    /**
     * Adiciona as informações adicionais a tela
     *
     * @param secondChunk Chunk
     * @param mainFrase   Phrase
     * @param secondPrgf  Paragraph
     * @return Element
     */
    private Element addInformation(Chunk secondChunk, Phrase mainFrase, Paragraph secondPrgf) {
        String msg = "Carencia Rotativa 10 minutos\n" +
                     "Ticket perdido: R$ 20.0\n\n\n";
        secondChunk.append(msg);
        mainFrase.add(secondChunk);
        mainFrase.setFont(fontForAll);
        secondPrgf.add(mainFrase);
        secondPrgf.setAlignment(Element.ALIGN_CENTER);
        return secondPrgf;
    }

    /**
     * Adiciona os Dados de Entrada do Ticket ao PDF
     *
     * @param thirdChunk Chunk
     * @param mainFrase  Phrase
     * @param thirdPrgf  Paragraph
     * @return Element
     */
    private Element addInitialData(Chunk thirdChunk, Phrase mainFrase, Paragraph thirdPrgf) {
        try {
            LocalDateTime dtEntrada = m.getHr_entrada();
            if (dtEntrada != null) {
                String ticket = "Ticket: " + this.m.getTicket().getNumero() + Chunk.NEWLINE;
                String data = "Entrada: " + dtEntrada.toLocalDate().format(DAY_FORMAT) + Chunk.NEWLINE;
                String time = "Hora: " + dtEntrada.toLocalTime().format(HOUR_FORMAT) + Chunk.NEWLINE;

                thirdChunk.append(ticket);
                _util_(thirdChunk, mainFrase, thirdPrgf, data, time);
            }
            return thirdPrgf;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adiciona o Código de Barras ao PDF
     *
     * @param prgfCodeBar Paragraph
     * @param img         Image
     * @return Element
     */
    private Element addCodeBar(Paragraph prgfCodeBar, Image img) {
        img.setAlignment(Element.ALIGN_CENTER);
        prgfCodeBar.setAlignment(Element.ALIGN_CENTER);
        prgfCodeBar.add(img);
        return prgfCodeBar;
    }

    /**
     * Usado para não repetir método, apenas formata os dados
     * com os valores passados e retorna para o lugar correto.
     *
     * @param chunk Chunk
     * @param frase Phrase
     * @param prgf  Paragraph
     * @param time  String
     * @param valor String
     */
    private void _util_(Chunk chunk, Phrase frase, Paragraph prgf, String time, String valor) {
        chunk.append(time);
        chunk.append(valor);

        frase.add(chunk);
        frase.setFont(fontForAll);

        prgf.add(frase);
        prgf.setAlignment(Element.ALIGN_CENTER);
    }

    /**
     * Adiciona os Dados de Saída do ticket ao PDF
     *
     * @param fiveChunk  Chunk
     * @param thirdFrase Phrase
     * @param finalPrgf  Paragraph
     * @return Element
     */
    private Element addFinalData(Chunk fiveChunk, Phrase thirdFrase, Paragraph finalPrgf) {
        try {
            LocalDateTime dtSaida = m.getHr_saida();
            double value = m.getTicket().getValor();
            if (dtSaida != null) {
                if (value > 0.0) {
                    String time = "Hora Saída: " + dtSaida.toLocalTime().format(HOUR_FORMAT) + Chunk.NEWLINE;
                    String valor = "R$: " + Util.formatarValor(value) + Chunk.NEWLINE;

                    _util_(fiveChunk, thirdFrase, finalPrgf, time, valor);
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalPrgf;
    }

    /**
     * Adiciona os Dados Modificados em Tabela ao PDF
     *
     * @param table PdfPTable
     * @return Element
     */
    private Element addTable(PdfPTable table) {
        try {
            this.addTableHeader(table);
            this.addRows(table);
            this.addCustomRows(table);
        } catch (URISyntaxException | BadElementException | IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    /**
     * Primeira Linha - Cabeçalho da Tabela
     *
     * @param table PdfPTable
     */
    private void addTableHeader(PdfPTable table) {
        Stream.of("column header 1", "column header 2", "column header 3")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    /**
     * Segunda Linha - Celulas da Tabela
     * Conteúdo interno das celulas
     *
     * @param table PdfPTable
     */
    private void addRows(PdfPTable table) {
        table.addCell("row 1, col 1");
        table.addCell("row 1, col 2");
        table.addCell("row 1, col 3");
    }

    /**
     * Pode Incluir Textos e Imagens nas celulas
     * Formatadas Individualmente, com:
     * - Alinhamentos Horizontais
     * - Alinhamentos Verticais
     *
     * @param table PdfPTable
     * @throws URISyntaxException  exception
     * @throws BadElementException exception
     * @throws IOException         exception
     */
    private void addCustomRows(PdfPTable table)
            throws URISyntaxException, BadElementException, IOException {

        img = Image.getInstance(path.toAbsolutePath().toString());
        img.scalePercent(10);

        // Imagem dentro da Tabela
        PdfPCell imageCell = new PdfPCell(img);
        table.addCell(imageCell);

        // Alinhamento Horizontal na Celula
        PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
        horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(horizontalAlignCell);

        // Alinhamento Vertical na Celula
        PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col 3"));
        verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(verticalAlignCell);
    }

    /**
     * Criptografia com senha
     * Usuario: userpass
     * Senha: 0
     */
    private void encryption() {
        try {
            PdfReader pdfReader = new PdfReader("Comprovante.pdf");
            PdfStamper pdfStamper = new PdfStamper(pdfReader,
                    new FileOutputStream("EncryptionPDF.pdf"));
            pdfStamper.setEncryption("userpass".getBytes(),
                    "0".getBytes(),
                    0,
                    PdfWriter.ENCRYPTION_AES_256);

            pdfStamper.close();
        } catch (IOException | DocumentException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
    }

}

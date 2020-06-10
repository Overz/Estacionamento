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
import java.util.stream.Stream;


/**
 * Esta classe usa uma API chamada IText para criar um PDF, mas também pode ser usado PDFBox
 * Cria um PDF como comprovante de um Ticket de Estacionamento
 * Com exemplos para implementa os Dados ao PDF
 * <p>
 * Exemplos:
 * <p>
 * 1 - Paragrafos
 * 2 - Imagens
 * 3 - Tabelas (Com 3 tipos diferentes de implementação)
 * 4 - Criptografia
 */
public class PdfComprovante {

    private static final String PASS = "";
    private static final String OWNER_PASS = "";
    private Chunk mainChunk, secondChunk, thirdChunk;
    private Phrase mainFrase;
    private Paragraph mainPrgf, secondPrgf, prgfCodeBar;
    private Path path;
    private Image img;
    private final MovimentoVO m;

    public PdfComprovante(String caminho, MovimentoVO movimento) {
        PdfHelpers.CAMINHO = caminho;
        this.m = movimento;
    }

    /**
     * Instancia os atributos usados na criação do PDF
     */
    private void instanciarAtributos() {
        mainChunk = new Chunk();
        secondChunk = new Chunk();
        thirdChunk = new Chunk();

        mainFrase = new Phrase();

        mainPrgf = new Paragraph();
        secondPrgf = new Paragraph();
        prgfCodeBar = new Paragraph();
    }

    /**
     * Método principal para gerar um PDF;
     * <p>
     * Método estão sendo utilziados Tag's HTML nas mensagens de retorno,
     * pois está sendo retornado para um Label, e utilizado em um JOptionPane,
     * quebra de Linha em HTML com a tag "br" é necessario com essa configuração
     *
     * @return String
     */
    public String gerarPdf() {
        String msg = "<html><body>";
        try {

            // Inicia os Atributos
            this.instanciarAtributos();

            Element header, info, codeBar, tableData;
            path = Paths.get(ClassLoader.getSystemResource("img/code-bar-GG-1.png").toURI());

            // Instancia o documento PDF
            Document document = new Document();
            document.setMargins(180, 180, 50, 0);

            // Caminho + Extensão do Pdf onde ele ira ser gravado
            PdfWriter.getInstance(document, new FileOutputStream(PdfHelpers.CAMINHO + PdfHelpers.EXTENSAO));

            document.open(); // Necessario para abrir, e digitar os valores dentro do PDF

            // Adiciona o Cabeçalho (Header) - Primeiro Paragrafo
            header = this.addHeader(mainChunk, secondChunk, mainPrgf);
            if (header != null) {
                document.add(header);
            } else {
                msg += "Erro ao Adicionar o Cabeçalho ao PDF<br>";
            }

            // Adiciona o Informações descritas após o cabeçalho (Informação Adicional) - Segundo Paragrafo
            info = this.addInformation(thirdChunk, mainFrase, secondPrgf);
            if (info != null) {
                document.add(info);
            } else {
                msg += "Erro ao Adicionar as Informações de Perda ao PDF<br> ";
            }

            // Adiciona o Codigo de Barras (Code-Bar)
            img = Image.getInstance(path.toAbsolutePath().toString());
            codeBar = this.addBarCode(prgfCodeBar, img);
            if (codeBar != null) {
                document.add(codeBar);
            } else {
                msg += "Erro ao Adicionar o Código de Barras ao PDF<br>";
            }

            // Cria a Tabela para o PDF
            tableData = this.addTable();
            document.add(tableData);

            document.close(); // Necessario fechar o documento para que os dados sejam salvos

        } catch (DocumentException e1) {
            System.out.println("Causa: " + e1.getCause());
            System.out.println("Msg: " + e1.getMessage());
            msg += "Erro ao Salvar o Documento<br>";
        } catch (URISyntaxException e2) {
            System.out.println("Reason: " + e2.getReason());
            System.out.println("Input: " + e2.getInput());
            System.out.println("Msg: " + e2.getMessage());
            msg += "Erro ao Especificar o Caminho do Arquivo em: " + PdfHelpers.CAMINHO + "" + PdfHelpers.EXTENSAO + "<br>";
        } catch (IOException e3) {
            System.out.println("Causa: " + e3.getCause());
            System.out.println("Msg: " + e3.getMessage());
            msg += "Erro ao Salvar o Documento em: " + PdfHelpers.CAMINHO + "" + PdfHelpers.EXTENSAO + "<br>Documento se Encontra em Aberto!<br>";
        }

        if (msg.equals("<html><body>")) {
            msg += "Pdf Gerado!";
        }
        return msg + "</body></html>";
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
        mainChunk.setFont(PdfHelpers.MAIN_FONT);
        secondChunk.append("Estacionamento\n\n");
        secondChunk.setFont(PdfHelpers.FONT4ALL);
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
                     "Ticket perdido: R$ 20.0\n\n";
        secondChunk.append(msg);
        mainFrase.add(secondChunk);
        mainFrase.setFont(PdfHelpers.FONT4ALL);
        secondPrgf.add(mainFrase);
        secondPrgf.setAlignment(Element.ALIGN_CENTER);
        return secondPrgf;
    }

    /**
     * Adiciona o Código de Barras ao PDF
     *
     * @param prgfCodeBar Paragraph
     * @param img         Image
     * @return Element
     */
    private Element addBarCode(Paragraph prgfCodeBar, Image img) {
        img.setAlignment(Element.ALIGN_CENTER);
        prgfCodeBar.setAlignment(Element.ALIGN_CENTER);
        prgfCodeBar.add(img);
        return prgfCodeBar;
    }

    /**
     * Adiciona os Dados Modificados em Tabela ao PDF
     *
     * @return Element
     */
    private Element addTable() {
        PdfPTable table = new PdfPTable(2);
        try {
            this.addCustomRowsData(table);

//            this.addTableHeader(table);
//            this.addRowsExample(table);
//            this.addCustomRowsExample(table);
        } catch (Exception e) {
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
        Stream.of("Dados", "Valores")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBorderWidth(0);
                    header.setBorder(Rectangle.NO_BORDER);
                    header.setBackgroundColor(BaseColor.WHITE);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }

    /**
     * Segunda Linha - Celulas da Tabela
     * Conteúdo interno das celulas
     *
     * @param table PdfPTable
     */
    private void addRowsExample(PdfPTable table) {
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
     */
    private void addCustomRowsExample(PdfPTable table) throws IOException, BadElementException {

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
     * Adiciona as Células customizadas
     *
     * @param table PdfPTable
     */
    private void addCustomRowsData(PdfPTable table) {
        try {

            // Coluna 1 - Linha 1
            PdfPCell col1_row1 = new PdfPCell(new Phrase("Ticket"));
            table.addCell(configCell(col1_row1, 0));

            // Coluna 2 - Linha 1
            String ticket = String.valueOf(m.getTicket().getNumero());
            PdfPCell col2_row1 = new PdfPCell(new Phrase(ticket));
            table.addCell(configCell(col2_row1, 1));

            // Coluna 1 - Linha 2
            PdfPCell col1_row2 = new PdfPCell(new Phrase("Entrada"));
            table.addCell(configCell(col1_row2, 0));

            // Coluna 2 - Linha 2
            String dt = m.getTicket().getDataEntrada().toLocalDate().format(PdfHelpers.DAY_FORMAT);
            String entrada = m.getTicket().getDataEntrada().toLocalTime().format(PdfHelpers.HOUR_FORMAT);
            PdfPCell col2_row2 = new PdfPCell(new Phrase(dt + "\n" + entrada));
            table.addCell(configCell(col2_row2, 1));

            // Coluna 1 - Linha 3
            PdfPCell col1_row3 = new PdfPCell(new Phrase("Saída"));
            table.addCell(configCell(col1_row3, 0));


            // Coluna 2 - Linha 3
            String saida = m.getTicket().getDataValidacao().toLocalTime().format(PdfHelpers.HOUR_FORMAT);
            PdfPCell col2_row3 = new PdfPCell(new Phrase(saida));
            table.addCell(configCell(col2_row3, 1));

            // Coluna 1 - Linha 4
            PdfPCell col1_row4 = new PdfPCell(new Phrase("R$"));
            table.addCell(configCell(col1_row4, 0));

            PdfPCell col2_row4 = new PdfPCell(new Phrase(Util.formatarValor(m.getTicket().getValor())));
            table.addCell(configCell(col2_row4, 1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove as bordas da celula/tabela e direciona para esquerda ou direita
     *
     * @param cell    PdfPCell
     * @param direcao int
     * @return PdfPCell
     */
    private PdfPCell configCell(PdfPCell cell, int direcao) {
        cell.setBorderWidth(0);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(direcao == 0 ? Element.ALIGN_LEFT : Element.ALIGN_CENTER);
        cell.setHorizontalAlignment(direcao != 0 ? Element.ALIGN_RIGHT : Element.ALIGN_CENTER);
        return cell;
    }

    /**
     * Criptografia
     * <p>
     * Necessario localizar o Arquivo para gera a Criptografia
     */
    private void encryption(String nomeArquivo, String saidaArquivo) {
        try {
            PdfReader pdfReader = new PdfReader(nomeArquivo);
            PdfStamper pdfStamper = new PdfStamper(pdfReader,
                    new FileOutputStream(saidaArquivo));

            // Password, OwnerPassword, Permissão, Criptografia
            pdfStamper.setEncryption(PASS.getBytes(),
                    OWNER_PASS.getBytes(),
                    0,
                    PdfWriter.ENCRYPTION_AES_256 | PdfWriter.DO_NOT_ENCRYPT_METADATA);

            pdfStamper.close();
        } catch (IOException | DocumentException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
    }

}

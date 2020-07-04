package util.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import model.vo.movimentos.LostTicketVO;
import util.helpers.Util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import static util.pdf.PdfHelpers.*;

public class PdfTermo extends Document {

    private final LostTicketVO vo;

    public PdfTermo(LostTicketVO vo, String caminho) {
        CAMINHO = caminho;
        this.vo = vo;
    }

    public String gerarPDF() {
        String msg = "<html><body>";
        try {

            this.setMargins(80, 80, 30, 80);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            FileOutputStream outputStream = new FileOutputStream(CAMINHO + EXTENSAO);
            PdfWriter.getInstance(this, outputStream);
            this.open();
            this.addMetaData();
            this.add(addHeader());
            this.add(new Paragraph("\n\n"));
            this.add(addInformation());
            this.add(addSignature());
            Paragraph p = new Paragraph("Assinatura do Responsavel");
            p.setAlignment(Element.ALIGN_CENTER);
            this.add(p);
            this.close();

        } catch (DocumentException e1) {
            System.out.println("Causa: " + e1.getCause());
            System.out.println("Msg: " + e1.getMessage());
            msg += "Erro ao Salvar o Documento<br>";
        } catch (IOException e3) {
            System.out.println("Causa: " + e3.getCause());
            System.out.println("Msg: " + e3.getMessage());
            msg += "Erro ao Salvar o Documento em: " + CAMINHO + "" + EXTENSAO + "<br>Documento se Encontra em Aberto!<br>";
        }
        if (msg.equals("<html><body>")) {
            msg += "Pdf Gerado!";
        }
        return msg + "</body></html>";
    }

    /**
     * Adiciona o Header do PDF
     *
     * @return Element
     */
    private Element addHeader() {
        Chunk c = new Chunk("TERMO DE RESPONSABILIDADE\n", MAIN_FONT).setUnderline(1, -1);
        Chunk c1 = new Chunk("\nSenac - Easy Way\n", FONT4ALL);
        Chunk c2 = new Chunk("Estacionamento", FONT4ALL);
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(c);
        paragraph.add(c1);
        paragraph.add(c2);
        return paragraph;
    }

    /**
     * Adiciona as informações do Termo de Reponsabilidade
     *
     * @return Element
     */
    private Element addInformation() {
        LocalDateTime now = LocalDateTime.now();
        String msg = "Eu, " + vo.getNome().toUpperCase() + ", CPF: " + vo.getCPF().toUpperCase()
                     + ", mediante este instrumento de aceitação, me respmsabilizo pela perda do Ticket do Estabelecimento"
                     + " referente ao CNPJ 03.603.739/0001-86,"
                     + " e aceito a multa de R$: " + Util.formatarValor(vo.getValorCobrado()) + "."
                     + "\n\n\n" + now.getDayOfMonth() + " de "
                     + now.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"))
                     + " de " + now.getYear() + "\n\n\n\n";

        Paragraph p = new Paragraph(new Chunk(msg));
        p.setAlignment(Element.ALIGN_CENTER);
        return p;
    }

    /**
     * Assinatura do Responsavel
     *
     * @return Element
     */
    private Element addSignature() {
        Paragraph p = new Paragraph("                      ");
        DottedLineSeparator dotted = new DottedLineSeparator();
        dotted.setOffset(-2);
        dotted.setGap(2f);
        p.add(dotted);
        return p;
    }

    /**
     * Add metaData
     */
    private void addMetaData() {
        this.addAuthor("https://github.com/Overz");
        this.addCreator("https://github.com/Overz");
        this.addHeader("Header?", "Content?");
        this.addKeywords("Termo");
        this.addSubject("Termo de Responsabilidade");
        this.addTitle("Termo de Responsabilidade");
    }
}

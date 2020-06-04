package util.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import model.vo.movimentos.MovimentoVO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class PdfCaixaFinal {

    private ArrayList<MovimentoVO> lista;

    public PdfCaixaFinal(ArrayList<MovimentoVO> lista, String caminho) {
        PdfHelpers.CAMINHO = caminho;
        this.lista = lista;
    }

    private void instanciarAtributos() {
    }

    public String gerarPdf() {
        String msg = "<html><body>";
        try {

            this.instanciarAtributos();

            Element header, data;

            Document document = new Document();
            document.setMargins(50, 50, 50, 50);

            PdfWriter.getInstance(document, new FileOutputStream(PdfHelpers.CAMINHO + PdfHelpers.EXTENSAO));

            document.open();

            header = this.addHeader();
            if (header != null) {
                document.add(header);
            } else {
                msg += "Erro ao Adicionar o Header ao PDF!";
            }

            data = this.addData();
            if (data != null) {
                document.add(data);
            } else {
                msg += "Erro ao Adicionar os Dados ao PDF!";
            }

            document.close();
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

    private Element addData() {
    }

    private Element addHeader() {
    }

    private void addCell() {

    }
}

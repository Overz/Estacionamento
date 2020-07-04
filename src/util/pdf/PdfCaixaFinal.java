package util.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.vo.movimentos.MovimentoVO;
import util.constantes.ConstHelpers;
import util.helpers.Util;

import javax.validation.constraints.NotNull;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Stream;

import static util.pdf.PdfHelpers.CAMINHO;
import static util.pdf.PdfHelpers.EXTENSAO;

public class PdfCaixaFinal extends Document {

    private final ArrayList<MovimentoVO> lista;

    public PdfCaixaFinal(ArrayList<MovimentoVO> lista, String caminho) {
        PdfHelpers.CAMINHO = caminho;
        this.lista = lista;
    }

    /**
     * Main method to create a PDF
     *
     * @return String
     */
    public String gerarPdf() {
        String msg = "<html><body>";
        try {

            this.setMargins(20, 20, 20, 20);
            PdfWriter.getInstance(this, new FileOutputStream(CAMINHO + EXTENSAO));
            this.open();
            this.addMetaData();
            this.add(addHeader());
            this.add(addResume());
            this.add(new Paragraph("\n"));
            this.add(addData());
            this.close();

        } catch (DocumentException e1) {
            System.out.println("Causa: " + e1.getCause());
            System.out.println("Msg: " + e1.getMessage());
            msg += "Erro ao Salvar o Documento<br>";
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
     * Pdf Header
     *
     * @return Element
     */
    private Element addHeader() {
        Chunk c1 = new Chunk("Senac - Easy Way\n", PdfHelpers.MAIN_FONT);
        Chunk c2 = new Chunk("Estacionamento\n\n\n", PdfHelpers.FONT4ALL);
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(c1);
        paragraph.add(c2);
        return paragraph;
    }

    /**
     * Add main data in PDF
     *
     * @return Element
     */
    private Element addData() {
        PdfPTable table = new PdfPTable(5);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        this.addTableHeader(table);
        this.addTableData(table);
        return table;
    }

    /**
     * Add table header
     *
     * @param table PdfPTable
     */
    private void addTableHeader(PdfPTable table) {
        Stream.of("Nº", "Descricao", "Valor", "Entrada", "Saída")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
//                    header.setBorderWidth(1);
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setBorderColor(BaseColor.BLACK);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }

    /**
     * Add all date in Table
     *
     * @param table PdfPTable
     */
    private void addTableData(PdfPTable table) {
        for (MovimentoVO movimentoVO : lista) {
            LocalDateTime entrada = movimentoVO.getHr_entrada();
            LocalDateTime saida = movimentoVO.getHr_saida();
            if (movimentoVO.getTicket() != null) {
                this.addTableTicket(table, movimentoVO, entrada, saida);
            } else if (movimentoVO.getContrato() != null) {
                this.addTableCliente(table, movimentoVO, entrada, saida);
            }
        }
    }

    /**
     * Add data of Ticket's
     *
     * @param table       PdfPTable
     * @param movimentoVO MovimentoVO
     * @param entrada     LocalDateTime
     * @param saida       LocalDateTime
     */
    private void addTableTicket(PdfPTable table, MovimentoVO movimentoVO, LocalDateTime entrada, LocalDateTime saida) {
        // Coluna 1 - Numero
        table.addCell(String.valueOf(movimentoVO.getTicket().getNumero()));
        // Coluna 2 - Descricao
        table.addCell("Ticket");
        // Coluna 3 - Valor
        if (movimentoVO.getTicket().getValor() == 0.0) {
            table.addCell("Não Realizado");
        } else {
            table.addCell(Util.formatarValor(movimentoVO.getTicket().getValor()));
        }
        // Coluna 4 - Entrada
        table.addCell(entrada.format(ConstHelpers.DTF));
        // Coluna 5 - Saída
        if (saida == null) {
            table.addCell("Não Realizado");
        } else {
            table.addCell(saida.format(ConstHelpers.DTF));
        }
    }

    /**
     * Add data of Client's
     *
     * @param table       PdfPTable
     * @param movimentoVO MovimentoVO
     * @param entrada     LocalDateTime
     * @param saida       LocalDateTime
     */
    private void addTableCliente(PdfPTable table, MovimentoVO movimentoVO, LocalDateTime entrada, LocalDateTime saida) {
        // Coluna 1 - Numero
        table.addCell(String.valueOf(movimentoVO.getContrato().getNumeroCartao()));
        // Coluna 2 - Descricao
        table.addCell("Cliente");
        // Coluna 3 - Valor
        table.addCell(Util.formatarValor(movimentoVO.getContrato().getValor()));
        // Coluna 4 - Entrada
        table.addCell(entrada.format(ConstHelpers.DTF));
        // Coluna 5 - Saida
        if (saida == null) {
            table.addCell("Não Realizado");
        } else {
            table.addCell(saida.format(ConstHelpers.DTF));
        }
    }

    /**
     * Add a summary of the day's data;
     *
     * @return Element
     */
    private Element addResume() {
        PdfPTable table = new PdfPTable(3);
        cellOfResume(table);
        return table;
    }

    /**
     * faz um resumo da tela e adiciona os valores a mesma
     *
     * @param table PdfPTable
     */
    private void cellOfResume(PdfPTable table) {
        try {
            int pago = 0, naoPago = 0;
            double totalValor = 0.0;
            for (MovimentoVO movimentoVO : lista) {
                if (movimentoVO.getTicket() != null) {
                    if (movimentoVO.getTicket().getValor() == 0.0) {
                        naoPago++;
                    } else {
                        pago++;
                        totalValor += movimentoVO.getTicket().getValor();
                    }
                } else if (movimentoVO.getContrato() != null) {
                    LocalDateTime entrada = movimentoVO.getHr_entrada();
                    LocalDateTime now = LocalDateTime.now();
                    if (entrada.toLocalDate().equals(now.toLocalDate()) && movimentoVO.getContrato().getValor() != 0.0) {
                        pago++;
                        totalValor += movimentoVO.getContrato().getValor();
                    }
                }
            }
            this.addResumeCells(pago, naoPago, totalValor, table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addResumeCells(int pago, int naoPago, double totalValor, @NotNull PdfPTable table) {
        PdfPCell cell1 = new PdfPCell();
        cell1.setPhrase(new Phrase(new Chunk("Pago: " + pago, PdfHelpers.FONT4ALL)));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setBorder(Rectangle.NO_BORDER);
        cell1.setBorderWidth(0);

        PdfPCell cell2 = new PdfPCell();
        cell2.setPhrase(new Phrase(new Chunk("Não Pago: " + naoPago, PdfHelpers.FONT4ALL)));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setBorder(Rectangle.NO_BORDER);
        cell2.setBorderWidth(0);

        PdfPCell cell3 = new PdfPCell();
        cell3.setPhrase(new Phrase(new Chunk("Total(R$) " + Util.formatarValor(totalValor), PdfHelpers.FONT4ALL)));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setBorder(Rectangle.NO_BORDER);
        cell3.setBorderWidth(0);

        PdfPCell cell4 = new PdfPCell();
        cell4.setPhrase(new Phrase(new Chunk("Nº: " + lista.size(), PdfHelpers.FONT4ALL)));
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setBorder(Rectangle.NO_BORDER);
        cell4.setBorderWidth(0);

        // Empty cell
        PdfPCell cell5 = new PdfPCell(new Phrase(new Chunk("")));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell5.setBorder(Rectangle.NO_BORDER);
        cell5.setBorderWidth(0);

        PdfPCell cell6 = new PdfPCell();
        cell6.setPhrase(new Phrase(new Chunk(LocalDateTime.now().format(ConstHelpers.DTF)
                .replace('-', '\n'), PdfHelpers.FONT4ALL)));
        cell6.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell6.setBorder(Rectangle.NO_BORDER);
        cell6.setBorderWidth(0);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);

    }

    /**
     * Add MetaData in PDF
     */
    private void addMetaData() {
        this.addAuthor("https://github.com/Overz");
        this.addCreator("https://github.com/Overz");
        this.addHeader("Header?", "Content?");
        this.addKeywords("caixa");
        this.addSubject("Relatorio do Dia");
        this.addTitle("Caixa Final");
    }
}

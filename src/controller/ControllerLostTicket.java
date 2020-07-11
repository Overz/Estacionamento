package controller;

import model.banco.BaseDAO;
import model.bo.LostTicketBO;
import model.dao.movientos.LostTicketDAO;
import model.vo.movimentos.LostTicketVO;
import util.helpers.Modificacoes;
import util.helpers.Util;
import util.pdf.PdfHelpers;
import util.pdf.PdfTermo;
import view.panels.LostTicketView;

import javax.swing.*;
import java.io.*;
import java.util.Objects;

public class ControllerLostTicket {
    private final LostTicketView lostTicketView;
    private String msg = "";

    public ControllerLostTicket(LostTicketView lostTicketView) {
        this.lostTicketView = lostTicketView;
    }

    /**
     * limpa os Dados da Tela
     */
    public void limparDados() {
        lostTicketView.getTxtNome().setText("");
        lostTicketView.getTxtCPF().setText("");
        lostTicketView.getTxtPlaca().setText("");
        lostTicketView.getTxtRenavam().setText("");
        lostTicketView.getComboBox().setSelectedIndex(0);
    }

    /**
     * Valida o formulario da tela
     *
     * @param vo LostTicketVO
     * @return true/false
     */
    private boolean validarForm(LostTicketVO vo) {
        msg = "<html><body>";
        if (!LostTicketBO.validarNome(vo)) {
            msg += "Por favor, Digite o NOME Corretamente!<br>";
        }
        if (!LostTicketBO.validarCpf(vo)) {
            msg += "Por favor, Digite o CPF Corretamente!<br>";
        }
        if (!LostTicketBO.validarPlaca(vo)) {
            msg += "Por favor, Digite a PLACA Corretamente!<br>";
        }
        if (!LostTicketBO.validarRenavam(vo)) {
            msg += "Por favor, Digite o RENAVAM Corretamente!<br>";
        }
        if (!LostTicketBO.validarFormaPgto(vo)) {
            msg += "Por favor, Escolha a FORMA DE PAGAMENTO Corretamente!<br>";
        }

        msg += "</body></html>";
        if (!msg.equals("<html><body></body></html>")) {
            JOptionPane.showMessageDialog(lostTicketView, Modificacoes.labelConfig(msg),
                    "Ticket Perdido", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Pega os resultados do Formulario da tela
     *
     * @return LostTicketVO
     */
    private LostTicketVO getResultadoForm() {
        String nome = lostTicketView.getTxtNome().getText();
        String cpf = lostTicketView.getTxtCPF().getText();
        String placa = lostTicketView.getTxtPlaca().getText();
        String renavam = lostTicketView.getTxtRenavam().getText();
        String formPgto = Objects.requireNonNull(lostTicketView.getComboBox().getSelectedItem()).toString();
        return new LostTicketVO(nome, cpf, placa, renavam, formPgto, 56.7);
    }

    /**
     * Pega os Dados da tela, verifica/valida, se true, cadastra, no fim gera um pdf e mostra uma Msg
     * Abre o arquivo, lê ele como array de bytes, escreve ele no Objeto, e salva no Banco
     */
    public void cadastrar() {

        LostTicketVO vo = getResultadoForm();
        if (validarForm(vo)) {
            JFileChooser jFileChooser = new JFileChooser();
            if (escolherCaminhoPDF(vo, jFileChooser)) {
                salvarDocumentoDAO(vo, jFileChooser);
            } else {
                msg = "Termo de Responsabilidade não Gerado!";
                JOptionPane.showMessageDialog(lostTicketView, Modificacoes.labelConfig(msg),
                        "Ticket Perdido", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Cria um diretorio: Path
     * Verifica se existe, se existir pergunta se deseja substituir
     * Cria um Novo Pdf nesse caminho
     *
     * @param vo           LostTicketVO
     * @param jFileChooser JFileChooser
     */
    private boolean escolherCaminhoPDF(LostTicketVO vo, JFileChooser jFileChooser) {

        int i = Util.abrirJFileChooser(lostTicketView, jFileChooser);
        if (i == JFileChooser.APPROVE_OPTION) {

            File file = new File(jFileChooser.getSelectedFile() + ".pdf");
            if (file.exists()) {
                msg = "Arquivo existente Encontrado, Deseja Subistituir?";
                int j = JOptionPane.showConfirmDialog(lostTicketView, Modificacoes.labelConfig(msg), "Sobre-Escrever", JOptionPane.OK_CANCEL_OPTION);
                if (j == JOptionPane.OK_OPTION) {
                    msg = gerarPdf(jFileChooser, vo);
                } else {
                    msg = "Operação Cancelada!";
                }
                JOptionPane.showMessageDialog(lostTicketView, Modificacoes.labelConfig(msg),
                        "Ticket Perdido", JOptionPane.INFORMATION_MESSAGE);
            } else {
                msg = gerarPdf(jFileChooser, vo);
                JOptionPane.showMessageDialog(lostTicketView, Modificacoes.labelConfig(msg),
                        "Ticket Perdido", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        }
        return false;
    }

    /**
     * Gera e Salva o Doc em um lugar especifico
     *
     * @param jFileChooser JFileChooser
     * @param vo           LostTicketVO
     * @return String
     */
    private String gerarPdf(JFileChooser jFileChooser, LostTicketVO vo) {
        String caminho = Util.caminhoFileChooser(jFileChooser.getSelectedFile());
        PdfTermo pdf = new PdfTermo(vo, caminho);
        return pdf.gerarPDF();
    }


    private void salvarDocumentoDAO(LostTicketVO vo, JFileChooser jFileChooser) {
        try {
            //TODO TENTAR SALVAR O DOCUMENTO NO DB
            File file = new File(jFileChooser.getSelectedFile().getAbsolutePath() + PdfHelpers.EXTENSAO);
            byte[] bytesArray = loadFile(file.getAbsolutePath());

            BaseDAO<LostTicketVO> dao = new LostTicketDAO();
            vo.setDocumento(bytesArray);
            vo = dao.cadastrar(vo);
            System.out.println(vo.toString());

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carregar o Arquivo
     *
     * @param sourcePath String
     * @return byte[]
     */
    public byte[] loadFile(String sourcePath) throws IOException {
        try (InputStream inputStream = new FileInputStream(sourcePath)) {
            return readFully(inputStream);
        }
    }

    /**
     * Ler o Arquivo
     *
     * @param stream InputStream
     * @return byte[]
     * @throws IOException IOException
     */
    public byte[] readFully(InputStream stream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int bytesRead;
        byte[] buffer = new byte[1604];
        while ((bytesRead = stream.read(buffer)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }
        stream.close();
        return baos.toByteArray();
    }

}

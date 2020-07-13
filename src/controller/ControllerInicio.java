package controller;

import model.banco.BaseDAO;
import model.bo.CarroBO;
import model.bo.ClienteBO;
import model.bo.InicioBO;
import model.dao.cliente.ContratoDAO;
import model.dao.movientos.MovimentoDAO;
import model.dao.movientos.TicketDAO;
import model.vo.cliente.ClienteVO;
import model.vo.cliente.ContratoVO;
import model.vo.movimentos.MovimentoVO;
import model.vo.movimentos.TicketVO;
import org.apache.commons.math3.random.RandomDataGenerator;
import util.constantes.Colunas;
import util.constantes.ConstCaixa;
import util.constantes.ConstHelpers;
import util.constantes.ConstInicio;
import util.helpers.Modificacoes;
import util.helpers.Util;
import util.tesseract.OCR;
import view.panels.mainView.InicioView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ControllerInicio {
    private final InicioView inicioView;
    private final BaseDAO<MovimentoVO> daoM;
    private final BaseDAO<TicketVO> daoT;
    private final BaseDAO<ContratoVO> daoC;
    private ArrayList<MovimentoVO> lista;
    private MovimentoVO m;
    private TicketVO t;
    private String msg = "";
    private String title = "";
    private long minutes;
    private double valor;
    private Timer timer;

    public ControllerInicio(InicioView inicioView) {
        this.inicioView = inicioView;
        this.daoM = new MovimentoDAO();
        this.daoT = new TicketDAO();
        this.daoC = new ContratoDAO();
        this.m = new MovimentoVO();
        this.t = new TicketVO();
        this.lista = new ArrayList<>();
        this.timerRefreshData();
    }

    // INICIO ### MÉTODS DA TABELA

    /**
     * Atualiza a JTable com Todos os Valores
     */
    public void atualizarTabela() {

        this.limparTabela();

        DefaultTableModel model = (DefaultTableModel) inicioView.getTable().getModel();
        Object[] novaColuna = new Object[5];
        // Flag Inicial
        if (ConstHelpers.FLAG == 0) {
            ConstHelpers.FLAG = 2; // Flag Consultar Todos usado em MovimentoDAO - Consultar
            ConstHelpers.SUB_FLAG = 4; // Subglag para criar o seletor de Data na classe Seletor
            String dt = String.valueOf(LocalDate.now());
            lista = daoM.consultar(dt, dt);
        }

        LocalDateTime now = LocalDateTime.now();
        // Percorre os movimentos para adicionar linha a linha na tabela (JTable)
        for (MovimentoVO movimento : lista) {
            if (movimento.isAtual()) {

                //Ticket
                if (movimento.getTicket() != null) {
                    this.atualizarTabelaTicket(movimento, novaColuna, now);
                    model.addRow(novaColuna);
                }

                //Plano
                if (movimento.getContrato() != null) {
                    this.atualizarTabelaPlano(movimento, novaColuna, now);
                    boolean temLinha = false;
                    for (Object o : novaColuna) {
                        if (o != null) {
                            temLinha = true;
                        } else {
                            temLinha = false;
                            break;
                        }
                    }
                    if (temLinha) {
                        model.addRow(novaColuna);
                    }
                }
            }
        }
        this.addTotalVeiculos(lista, now);
    }

    /**
     * Tabela de Ticket
     *
     * @param movimento  MovimentoVO
     * @param novaColuna Objet[]
     */
    private void atualizarTabelaTicket(MovimentoVO movimento, Object[] novaColuna, LocalDateTime now) {
        LocalDateTime entrada = movimento.getHr_entrada();
        String placa = movimento.getTicket().getPlaca();
        // Impede que o Plano/Cliente seja Preenchido na tabela
        // caso o dia de entrada desse cliente, seja != do dia de hj
//        if (entrada.toLocalDate().equals(now.toLocalDate())) {

        // Coluna 1 (Ticket/Cartao)
        novaColuna[0] = movimento.getTicket().getNumero();
        // Coluna 2 (Carro)
        novaColuna[1] = "";
        // Coluna 3 (Placa)
        if (placa != null && !placa.isEmpty()) {
            novaColuna[2] = placa;
        } else {
            novaColuna[2] = "NENHUM REGISTRO";
        }
        // Coluna 4 (Cliente)
        novaColuna[3] = "";
        // Coluna 5 (Entrada)
        novaColuna[4] = entrada.format(ConstHelpers.DTF);
//        }
    }

    /**
     * Tabela de Cliente
     *
     * @param movimento  MovimentoVO
     * @param novaColuna Object[]
     * @param now        LocalDateTime
     */
    private void atualizarTabelaPlano(MovimentoVO movimento, Object[] novaColuna, LocalDateTime now) {
        LocalDateTime entrada = movimento.getHr_entrada();

        // Impede que o Plano/Cliente seja Preenchido na tabela
        // caso o dia de entrada desse cliente, seja != do dia de hj
//        if (entrada.toLocalDate().equals(now.toLocalDate())) {

        // Coluna 1 (Ticket/Cartao)
        novaColuna[0] = movimento.getContrato().getNumeroCartao();
        // Coluna 2 (Carro)
        novaColuna[1] = movimento.getContrato().getCliente().getCarro().getModelo().getDescricao();
        // Coluna 3 (Placa)
        novaColuna[2] = movimento.getContrato().getCliente().getCarro().getPlaca();
        // Coluna 4 (Cliente)
        novaColuna[3] = movimento.getContrato().getCliente().getNome();
        // Coluna 5 (Dt Entrada)
        novaColuna[4] = entrada.format(ConstHelpers.DTF);
//        }
    }

    /**
     * totaliza a quantidade de veiculos ATUAIS, e adiciona a um Label na tela
     *
     * @param lista ArrayList<MovimentoVO>
     * @param now   LocalDateTime
     */
    private void addTotalVeiculos(ArrayList<MovimentoVO> lista, LocalDateTime now) {
        int i = 0;
        for (int j = 0; j < lista.size(); j++) {
//            if (movimento.getHr_entrada().toLocalDate().equals(now.toLocalDate())) {
            i++;
//            }
        }
        inicioView.getLblTotalDeVeiculos().setText("Total de Veiculos: " + i);
    }

    /**
     * Limpa a tela para revalidar os valores
     */
    private void limparTabela() {
        inicioView.getTable().setModel(new DefaultTableModel(new Object[][]{}, Colunas.COLUNAS_INICIO));
    }
    // FIM ### MÉTODOS DA TABELA

    /**
     * Remover as linhas selecionadas da tablea;
     */
    public void removeSelectedRow() {

        title = "Exclusão";
        int row = inicioView.getTable().getSelectedRow();
        if (row >= 0) {

            DefaultTableModel model = (DefaultTableModel) inicioView.getTable().getModel();

            MovimentoVO m = lista.get(row);
            if (m.getTicket() != null) {
                ConstHelpers.FLAG = 1;
                realizarExclusao(row, model, m);
            } else if (m.getContrato() != null) {
                ConstHelpers.FLAG = 0;
                realizarExclusao(row, model, m);
            } else {
                msg = "Erro ao Tentar Excluir a Linha Selecionada!";
            }

            JOptionPane.showMessageDialog(inicioView, Modificacoes.labelConfig(msg), title,
                    JOptionPane.INFORMATION_MESSAGE);
            System.out.println(m.toString());
        } else {
            msg = "Escolha uma Linha para Remover!";
            JOptionPane.showMessageDialog(inicioView, Modificacoes.labelConfig(msg), title,
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Excluir definitivamente a linha selecionada
     *
     * @param row   int
     * @param model {@link DefaultTableModel}
     * @param m     {@link MovimentoVO}
     */
    private void realizarExclusao(int row, DefaultTableModel model, MovimentoVO m) {
        if (daoM.excluirPorID(m.getId())) {
            msg = "EXCLUSÃO REALIZADA COM SUCESSO!";
            model.removeRow(row);
            lista.remove(row);
            this.atualizarTabela();
        } else {
            msg = "ERRO AO REALIZAR EXCLUSÃO!";
        }
    }

    // INICIO ### MÉTODOS DO TICKET

    /**
     * Valida o ticket por algum tempo, e atualiza o Status no banco
     *
     * @param tipoPgto String
     * @param ticket   String
     */
    public void realizarSaida(String tipoPgto, String ticket) {
        if (InicioBO.validarNumeroTicket(ticket)) {
            if (validarCalculo(tipoPgto, ticket)) {
                if (validarTicket(ticket, tipoPgto)) {
                    msg = "Ticket Valido Por: " + minutes + " minuto's!";
                } else {
                    msg = "Erro ao Validar o Ticket\n";
                }
                JOptionPane.showMessageDialog(inicioView, Modificacoes.labelConfig(msg),
                        title, JOptionPane.INFORMATION_MESSAGE);
            } else if (ConstHelpers.SUB_FLAG == 0) {
                title = "Erro";
                msg = "<html><body>Ação Cancelada!<br>O Ticket não foi validado!</body></html>";
                JOptionPane.showMessageDialog(inicioView, Modificacoes.labelConfig(msg),
                        title, JOptionPane.WARNING_MESSAGE);
            }
        } else {
            msg = "Por Favor, Digite o Ticket Corretamente!";
            JOptionPane.showMessageDialog(inicioView, Modificacoes.labelConfig(msg), title, JOptionPane.WARNING_MESSAGE);
        }
        this.ajustarFocusTxtTicket();
    }

    /**
     * Método para Calcula o valor TOTAL(Desde o Inicio, até o Tempo Atual(Agora))
     * do Valor do Ticket. Utilizando de Valores Iniciais, e Valores de Diferença (Fim - Inicio).
     * <p>
     * Se o TICKET estiver com o Status Flase(Não Validado), o Método permite a Possibilidade
     * de Perguntar se deseja Validar o Ticket quando o evento acontece.
     * <p>
     * Formata e Acumula o valor em uma variavel Constante para a tela CaixaView.
     *
     * @param tipoPgto String
     * @param ticket   String
     * @return true/false
     */
    private boolean validarCalculo(String tipoPgto, String ticket) {
        try {
            if (verificarTicketExistente(ticket)) {
                if (t != null && t.getStatus().equals(true)) {

                    ConstHelpers.FLAG = 1; // Flag para consultar o ID do Ticket na tabela movimento no método Consultar por ID
                    m = daoM.consultarPorId(t.getId());

                    LocalDateTime ldtEntrada = m.getHr_entrada();
                    LocalDateTime now = LocalDateTime.now();
                    Date date1 = Date.from(ldtEntrada.atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
                    Date date2 = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

                    this.calcular(date1, date2);

                    String format = Util.formatarValor(valor);
                    title = "Validação";
                    msg = "<html><body>Total(R$): " + format + "<br>Deseja Validar este Ticket?</body></html>";
                    int i = JOptionPane.showConfirmDialog(inicioView, Modificacoes.labelConfig(msg),
                            title, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                    if (i == JOptionPane.YES_OPTION) {
                        if (tipoPgto.equals(ConstInicio.PGTO_CARTAO)) {
                            ConstCaixa.LBL_VALOR_CAIXA_CARTAO += valor;
                            ConstCaixa.LBL_VALOR_CAIXA_TOTAL += valor;
                        } else if (tipoPgto.equals(ConstInicio.PGTO_DINHEIRO)) {
                            ConstCaixa.LBL_VALOR_CAIXA_DINHEIRO += valor;
                            ConstCaixa.LBL_VALOR_CAIXA_TOTAL += valor;
                        }
                        return true;
                    }
                }

                ConstHelpers.SUB_FLAG = 0;
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void calcular(Date date1, Date date2) {
        // Diferença entre Milisegundos de Agora com a Entrada
        long diff = date2.getTime() - date1.getTime(); // Variavel base para o calculo das demais abaixo
        long diffMinutes = Util.diffMinutes(diff);
        long diffHours = Util.diffHours(diff);
        long diffDays = Util.diffDays(diff);

        // Calculo Final
        double valorDia = 10.0; // Valor da Hora
        double valorMinuto = valorDia / 60.0; // Valor do Minuto
        double minPorDia = 24 * 60; // Minutos totais de 24h(1 Dia)
        double minRestantes = (diffHours * 60.0) + diffMinutes; // Minutos Restantes do Dia atual (Hora + Minuto)
        double total = ((diffDays * minPorDia) + minRestantes); // Diferença de Dias entre o Inicio ate Agora * Minutos Totais de um Dia + Minutos Restantes do Dia Atual
        valor = total * valorMinuto; // Total dos valores Iniciais ate Agora
    }

    /**
     * Valida o Ticket com Status True no banco, com Timer para X tempo,
     * Após esse tempo o status atualiza para false;
     *
     * @param ticket String
     * @return true/false
     */
    private boolean validarTicket(String ticket, String tipoPgto) {
        try {
            if (verificarTicketExistente(ticket)) {
                for (MovimentoVO movimentoVO : lista) {
                    if (m.getId() == movimentoVO.getId()) {
                        ConstHelpers.FLAG = 1;
                        this.atualizarDadosTicketValidado(t, tipoPgto);
                        boolean a = daoT.alterar(t);
                        boolean b = daoM.alterar(m);
                        if (a && b) {
                            this.timerTicket();
                            return true;
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean verificarTicketExistente(String ticket) {
        for (MovimentoVO movimentoVO : lista) {
            if (movimentoVO.getTicket() != null) {
                String numero = String.valueOf(movimentoVO.getTicket().getNumero());
                if (ticket.equals(numero)) {
                    int id = movimentoVO.getTicket().getId();
                    t = daoT.consultarPorId(id);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Preenche o ticket com dados atualizados após
     * o usuario aceitar a validação do mesmo.
     *
     * @param t TicketVO
     */
    private void atualizarDadosTicketValidado(TicketVO t, String tipoPgto) {
        t.setValor(valor);
        t.setTipo(tipoPgto);
        t.setDataValidacao(LocalDateTime.now());
        t.setStatus(false);
        t.setValidado(true);
        m.setHr_entrada(m.getHr_entrada());
        m.setHr_saida(t.getDataValidacao());
    }
    // FIM ### MÉTODOS DO TICKET

    /**
     * Cria o Timer para atualizar o Status do ticket
     */
    private void timerTicket() {
        ActionListener event = e -> {
            // validar o Ticket por X minutos
            ConstHelpers.FLAG = 1;
            TicketVO oldTicket = t;
            oldTicket.setStatus(true);
            daoT.alterar(oldTicket);
        };
        Timer timer;
        if (ConstHelpers.TEMPO_TICKET == 0) {
            timer = new Timer(ConstHelpers.TEMPO_5_MIN, event);
        } else {
            timer = new Timer(ConstHelpers.TEMPO_TICKET, event);
        }
        timer.setRepeats(false);
        timer.start();
        minutes = TimeUnit.MILLISECONDS.toMinutes(timer.getDelay());
    }

    /**
     * Controla os campos da Cancela na tela, atualizando a cor ao clicar
     * e com um timer para retornar ao status original
     * simulando a abertura da cancela
     *
     * @param button JButton
     * @param label  JLabel
     */
    public void controlarCancela(JButton button, JLabel label) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                label.setText("Abrindo Cancela");
                label.setBackground(Color.decode("#35D073"));
                button.setBackground(Color.decode("#35D073"));
                ActionListener event = actionEvent -> {
                    label.setText("Cancela Fechada");
                    label.setBackground(Color.decode("#F85C50"));
                    button.setBackground(Color.WHITE);
                };
                Timer timer = new Timer(10000, event);
                timer.start();
            }
        });
    }

    /**
     * Verifica o Tipo para consultar de acordo com a necessidade do usuário
     *
     * @param tipo  String
     * @param valor String
     */
    public void consultar(String tipo, String valor) {
        ConstHelpers.FLAG = 0;
        title = "Procurar";
        switch (tipo) {
            case ConstInicio.PROCURA:
                this.atualizarTabela();
                break;
            case ConstInicio.PROCURA_CARRO:
                if (CarroBO.validarPlaca(valor)) {
                    ConstHelpers.SUB_FLAG = 1;
                    lista = daoM.consultar(valor);
                }
                break;
            case ConstInicio.PROCURA_CLIENTE:
                ClienteVO c = new ClienteVO();
                c.setNome(valor);
                if (ClienteBO.validarNomeCliente(c)) {
                    ConstHelpers.SUB_FLAG = 2;
                    lista = daoM.consultar(valor);
                }
                break;
            case ConstInicio.PROCURA_TICKET_CARTAO:
                if (InicioBO.validarNumeroTicket(valor)) {
                    ConstHelpers.SUB_FLAG = 3;
                    ConstHelpers.FLAG = 1;
                    lista = daoM.consultar(valor);
                }
                break;
            default:
                msg = "<html><body>Por favor, Digite os Dados Corretamente!" +
                      "<br>- Sem Caracteres Especiais!" +
                      "<br>- Cliente não deve Conter Números!</body></html>";
                break;
        }

        if (lista != null) {
            ConstHelpers.FLAG = 1;
            atualizarTabela();
            msg = "<html><body>Consulta Realizada!</body></html>";
        } else {
            msg = "<html><body>Ocorreu um Problema!<br>Consulta não pode ser Realizada!<br>Movito: Lista retornando Null/Vazio</body></html>";
        }

        this.ajustarFocusTxtProcurar();
        JOptionPane.showMessageDialog(inicioView, Modificacoes.labelConfig(msg),
                title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Gera um Ticket e compara se já existe,
     * Caso exista, ele repete criando um outro diferente,
     * e Cadastra no DB
     */
    public void gerarTicket() {
        title = "Cadastro";

        long leftLimit = 9999L;
        long rightLimit = 999999999L;
        long generatedLong = randomTicketGenerator(leftLimit, rightLimit);

        ConstHelpers.FLAG = 1;
        String num = String.valueOf(generatedLong);
        lista = daoM.consultar(num);

        if (lista != null && lista.size() > 0) {
            for (MovimentoVO movimento : lista) {
                long comparator = movimento.getTicket().getNumero();
                if (comparator == generatedLong) {
                    ConstHelpers.FLAG = 1;
                    generatedLong = randomTicketGenerator(leftLimit, rightLimit);
                }
            }
        }

        if (ConstHelpers.FLAG == 1) {
            t = new TicketVO(generatedLong, LocalDateTime.now(), true, false);
            t = daoT.cadastrar(t);

            if (t != null) {
                ConstHelpers.FLAG = -1;
                m = new MovimentoVO(t.getId(), t.getDataEntrada(), true, t);
                m = daoM.cadastrar(m);

                ConstHelpers.FLAG = 0;
                this.atualizarTabela();

                msg = "Ticket Gerado!";
            } else {
                msg = "<html><body>Ticket Não Cadastrado!" +
                      "<br>Tente Novamente</body></html>";
            }
        }
        JOptionPane.showMessageDialog(inicioView,
                Modificacoes.labelConfig(msg),
                title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Cria um limite minimo e maximo de numeros para gerar um ticket
     *
     * @param leftLimit  long
     * @param rightLimit long
     * @return long
     */
    public long randomTicketGenerator(long leftLimit, long rightLimit) {
        return new RandomDataGenerator().nextLong(leftLimit, rightLimit);
    }

    /**
     * Atualiza o foco do campo TxtTicket na tela após os calculos/validações
     */
    private void ajustarFocusTxtTicket() {
        inicioView.getNumberMask().uninstall();
        inicioView.getNumberMask().install(inicioView.getTxtTicket());
    }

    /**
     * Atualiza o foco do campo TxtProcurar na tela após a consulta
     */
    private void ajustarFocusTxtProcurar() {
        inicioView.getTxtProcurar().setText("Procurar...");
        inicioView.getTxtProcurar().setForeground(Color.BLACK);
    }

    /**
     * Timer que mantém a tabela atualizada a cada X tempo
     */
    private void timerRefreshData() {
        ActionListener event = e -> {
            ConstHelpers.FLAG = 0;
            this.atualizarTabela();
        };
        timer = new Timer(ConstHelpers.TEMPO_1_MIN, event);
        timer.start();
    }

    /**
     * Gambiarra do OCR
     */
    public void runOcr() {
        try {
            OCR ocr = new OCR(this, daoM, daoT, daoC, m, t);
            JOptionPane.showMessageDialog(ocr, Modificacoes.labelConfig("Simulação Iniciada!"));
            ocr.setStart(1);
            ocr.runOcr();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Timer getTimer() {
        return timer;
    }

}

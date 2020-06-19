package controller;

import model.banco.BaseDAO;
import model.bo.CarroBO;
import model.bo.ClienteBO;
import model.bo.ContratoBO;
import model.bo.EnderecoBO;
import model.dao.cliente.ClienteDAO;
import model.dao.cliente.ContratoDAO;
import model.dao.cliente.EnderecoDAO;
import model.dao.cliente.PlanoDAO;
import model.dao.veiculos.CarroDAO;
import model.vo.cliente.ClienteVO;
import model.vo.cliente.ContratoVO;
import model.vo.cliente.EnderecoVO;
import model.vo.cliente.PlanoVO;
import model.vo.veiculo.CarroVO;
import util.constantes.ConstHelpers;
import util.helpers.Modificacoes;
import view.panels.cadastro.ListaClientesView;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroDados;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroEndereco;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroPlano;
import view.panels.mainView.MainView;

import javax.swing.*;
import java.awt.*;

public class ControllerMainCadastro {

    private ListaClientesView clientesView;
    private PanelzinhoCadastroPlano planoView;
    private PanelzinhoCadastroEndereco enderecoView;
    private PanelzinhoCadastroDados dadosView;
    private ControllerListaClientes clientesCtrl;
    private ControllerCadastroDados dadosCtrl;
    private ControllerCadastroEndereco enderecoCtrl;
    private ControllerCadastroPlano planoCtrl;
    private BaseDAO<CarroVO> daoCarro;
    private BaseDAO<ClienteVO> daoCliente;
    private BaseDAO<EnderecoVO> daoEndereco;
    private BaseDAO<ContratoVO> daoContrato;
    private BaseDAO<PlanoVO> daoPlano;
    private String msg;

    public ControllerMainCadastro(JPanel panel) {
        try {
            if (panel instanceof PanelzinhoCadastroPlano) {
                this.planoView = (PanelzinhoCadastroPlano) panel;
                planoCtrl = new ControllerCadastroPlano(planoView);
            }
            if (panel instanceof PanelzinhoCadastroEndereco) {
                enderecoView = (PanelzinhoCadastroEndereco) panel;
                enderecoCtrl = new ControllerCadastroEndereco(enderecoView);
            }
            if (panel instanceof PanelzinhoCadastroDados) {
                dadosView = (PanelzinhoCadastroDados) panel;
                dadosCtrl = new ControllerCadastroDados(dadosView);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * <p>Verifica o Tipo de Inserção:</p>
     * <p>Cadastro: 0</p>
     * <p>Atualização: 1</p>
     * <p>
     * Se for cadastro, tenta cadastrar.
     * Se for alteração, tenta alterar
     *
     * @param tipoCadastro int
     */
    public void salvar(int tipoCadastro) {
        try {
            msg = "";
            daoCarro = new CarroDAO();
            daoCliente = new ClienteDAO();
            daoEndereco = new EnderecoDAO();
            daoContrato = new ContratoDAO();
            daoPlano = new PlanoDAO();

            PlanoVO p = getPlanoForm();
            CarroVO car = getCarroForm();
            ClienteVO c = getClienteForm();
            EnderecoVO e = getEnderecoForm();
            ContratoVO con = getContratoForm();

            if (tipoCadastro == 0) {
                // Cadastrar
                if (corredorPolones(con, car, c, e)) {
                    bigJOptionPane(msg);
                    try {
                        if (cadastrarCliente(con, car, c, e, p)) {

                            msg = "Cliente Cadastrado!";
                        } else {
                            msg = "Erro ao Cadastrar Cliente!";
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                // Alterar
            } else {
                if (corredorPolones(con, car, c, e)) {
                    boolean v = daoCarro.alterar(car);
                    boolean x = daoCliente.alterar(c);
                    boolean w = daoEndereco.alterar(e);
                    boolean y = daoPlano.alterar(p);
                    boolean z = daoContrato.alterar(con);

                    if (v && x && w && y && z) {
                        msg = "Cliente Atualizado!";
                    }
                } else {
                    bigJOptionPane(msg);
                }
            }

            atualizarListaClientes();
            System.out.println(msg + "\n");
            if (msg != null && !msg.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, Modificacoes.labelConfig(msg), "Validação",
                        JOptionPane.PLAIN_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Atualiza a tabela de Clientes em ListaClientesView a cada alteração
     */
    private void atualizarListaClientes() {
        ConstHelpers.FLAG = 1;
        clientesView = MainView.getClienteView();
        clientesCtrl = new ControllerListaClientes(clientesView);
        clientesCtrl.atualizarTabela();
    }

    /**
     * Puta validação
     *
     * @param con ContratoVO
     * @param car CarroVO
     * @param c   ClienteVO
     * @param e   EnderecoVO
     * @return true/false
     */
    private boolean corredorPolones(ContratoVO con, CarroVO car, ClienteVO c, EnderecoVO e) {
        boolean bool = true;

        try {
            // Cliente
            if (!ClienteBO.validarNomeCliente(c)) {
                msg += "- Por favor, Digite o NOME Corretamente\n-NOME: Sem Caracteres Especiais\n\n";
                bool = false;
            }
            if (!ClienteBO.validarCPFcliente(c)) {
                msg += "- Por favor, Digite o CPF Corretamente\n-CPF: 11 Caracteres\n\n";
                bool = false;
            }
            if (!ClienteBO.validarTelefone(c)) {
                msg += "- Por favor, Digite o TELEFONE Corretamente\n-TELEFONE: Somente Numeros\n\n";
                bool = false;
            }
            if (!ClienteBO.validarEmail(c)) {
                msg += "- Por favor, Digite o EMAIL Corretamente\n-EMAIL: Deve ser um Email Real\n\n";
                bool = false;
            }
            if (!ClienteBO.validarRG(c)) {
                msg += "- Por favor, Digite o RG Corretamente\n-RG: Somenete Numeros\n\n";
                bool = false;
            }

            // Carro
            if (!CarroBO.validarPlaca(car)) {
                msg += "- Por favor, Digite a PLACA Corretamente\n-PLACA: >= 0 ou <= 7 Contendo Letras e Numeros\n\n";
                bool = false;
            }
            if (!CarroBO.validarMarca(car)) {
                msg += "- Por favor, Escolha a MARCA\n\n";
                bool = false;
            }
            if (!CarroBO.validarModelo(car)) {
                msg += "- Por favor, Escolha o MODELO\n\n";
                bool = false;
            }
            if (!CarroBO.validarCor(car)) {
                msg += "- Por favor, Escolha a COR\n\n";
                bool = false;
            }

            // Endereco
            if (!EnderecoBO.validarRua(e)) {
                msg += "- Por favor, Digite a RUA Corretamente\n-RUA: Somente Palavras\n\n";
                bool = false;
            }
            if (!EnderecoBO.validarNumero(e)) {
                msg += "- Por favor, Digite o NUMERO Corretamente\n-NUMERO: Somente Numeros\n\n";
                bool = false;
            }
            if (!EnderecoBO.validarBairro(e)) {
                msg += "- Por favor, Digite o BAIRRO Corretamente\n-BAIRRO: Somente Palavras\n\n";
                bool = false;
            }
            if (!EnderecoBO.validarCidade(e)) {
                msg += "- Por favor, Digite a CIDADE Corretamente\n-CIDADE: Somenete Palavras\n\n";
                bool = false;
            }

            // Plano
            if (!ContratoBO.validarNumeroCartao(con)) {
                msg += "- Por favor, Digite o Número do Cartão Corretamente\n\n";
                bool = false;
            }
            System.out.println("Corredor: " + msg);
        } catch (Exception e1) {
            System.out.println(e1.getClass().getSimpleName());
            System.out.println(e1.getMessage());
            e1.printStackTrace();
        }
        return bool;
    }

    private ContratoVO getContratoForm() {
        planoView = MainView.getPlanoCadastroView();
        planoCtrl = new ControllerCadastroPlano(planoView);
        return planoCtrl.getContratoForm();
    }

    /**
     * Pega os valores na tela Sub Endereco
     *
     * @return EnderecoVO
     */
    private EnderecoVO getEnderecoForm() {
        enderecoView = MainView.getEnderecoCadastroView();
        enderecoCtrl = new ControllerCadastroEndereco(enderecoView);
        return enderecoCtrl.getResultadoForm();
    }

    /**
     * Pega os valores na tela Sub Dados - na Tabela de Carros
     *
     * @return CarroVO
     */
    private CarroVO getCarroForm() {
        dadosView = MainView.getDadosCadastroView();
        dadosCtrl = new ControllerCadastroDados(dadosView);
        return dadosCtrl.getFormCarro();
    }

    /**
     * Pega os valores na tela Sub Dados
     *
     * @return ClienteVO
     */
    private ClienteVO getClienteForm() {
        dadosView = MainView.getDadosCadastroView();
        dadosCtrl = new ControllerCadastroDados(dadosView);
        return dadosCtrl.getFormCliente();
    }

    /**
     * Pega os valores na tela Sub Plano
     *
     * @return PlanoVO
     */
    private PlanoVO getPlanoForm() {
        planoView = MainView.getPlanoCadastroView();
        planoCtrl = new ControllerCadastroPlano(planoView);
        return planoCtrl.getPlanoForm();
    }

    /**
     * Limpa todos os formularios de cadastro das Sub telas
     */
    public void limparDados() {
        dadosCtrl.limparForm();
        enderecoCtrl.limparForm();
        planoCtrl.limparForm();
    }

    private boolean cadastrarCliente(ContratoVO con, CarroVO car, ClienteVO c, EnderecoVO e, PlanoVO p) {
        e = daoEndereco.cadastrar(e);
        car = daoCarro.cadastrar(car);

        c.setEndereco(e);
        c.setCarro(car);
        c = daoCliente.cadastrar(c);

        con.setCliente(c);
        con.setPlano(p);
        con = daoContrato.cadastrar(con);

        int idE = e.getId();
        int idCar = car.getId();
        int idCli = c.getId();
        int idCon = con.getId();

        boolean result = false;
        if (idE > 0 && idCli > 0 && idCar > 0 && idCon > 0) {
            if (idE == idCli && idCli == idCar && idCli == idCon) {
                result = true;
            }
        }
        return result;
    }

    private void bigJOptionPane(String msg) {
        JTextArea textArea = new JTextArea(msg);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Dialog", Font.BOLD, 16));
        textArea.setEditable(false);
        scrollPane.setPreferredSize(new Dimension(500, 500));
        if (msg != null && !msg.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, scrollPane, "Validação",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }
}

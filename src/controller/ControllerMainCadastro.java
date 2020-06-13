package controller;

import model.banco.BaseDAO;
import model.bo.CarroBO;
import model.bo.ClienteBO;
import model.bo.EnderecoBO;
import model.bo.PlanoBO;
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
import util.helpers.Modificacoes;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroDados;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroEndereco;
import view.panels.cadastro.subCadastro.PanelzinhoCadastroPlano;

import javax.swing.*;
import java.util.ArrayList;

public class ControllerMainCadastro {

    private ControllerCadastroDados dadosCtrl;
    private ControllerCadastroEndereco enderecoCtrl;
    private ControllerCadastroPlano planoCtrl;
    private BaseDAO<CarroVO> daoCarro;
    private String msg = "<html><body>";

    public ControllerMainCadastro(JPanel panel) {
        if (panel instanceof PanelzinhoCadastroPlano) {
            planoCtrl = new ControllerCadastroPlano(panel);
        }
        if (panel instanceof PanelzinhoCadastroEndereco) {
            enderecoCtrl = new ControllerCadastroEndereco(panel);
        }
        if (panel instanceof PanelzinhoCadastroDados) {
            dadosCtrl = new ControllerCadastroDados(panel);
        }
    }

    /**
     * <p>Verifica o Tipo de Inserção:</p>
     * <p>Cadastro: 0</p>
     * <p>Atualização: 1</p>
     *
     * @param tipo int
     */
    public void salvar(int tipo) {
        try {
            daoCarro = new CarroDAO();
            BaseDAO<ClienteVO> daoCliente = new ClienteDAO();
            BaseDAO<EnderecoVO> daoEndereco = new EnderecoDAO();
            BaseDAO<ContratoVO> daoContrato = new ContratoDAO();
            BaseDAO<PlanoVO> daoPlano = new PlanoDAO();

            ContratoVO con = getContratoForm();
            PlanoVO p = getPlanoForm();
            ClienteVO c = getClienteForm();
            CarroVO car = getCarroForm();
            EnderecoVO e = getEnderecoForm();

            if (tipo == 0) {
                Exception exception = null;
                if (corredorPolones(con, car, c, e)) {
                    try {
                        e = daoEndereco.cadastrar(e);
                        car = daoCarro.cadastrar(car);

                        c.setEndereco(e);
                        c.setCarro(car);
                        c = daoCliente.cadastrar(c);

                        p = daoPlano.cadastrar(p);

                        con = daoContrato.cadastrar(con);
                        con.setCliente(c);
                        con.setPlano(p);
                    } catch (Exception e1) {
                        exception = e1;
                        e1.printStackTrace();
                    }

                    if (exception == null) {
                        msg = "Cliente Cadastrado!<br><br>" + con.toString()
                              + "<br>" + p.toStringDiff()
                              + "<br>" + con.getCliente().toString();
                    }
                }
            } else if (corredorPolones(con, car, c, e)) {
                boolean v = daoCarro.alterar(car);
                boolean x = daoCliente.alterar(c);
                boolean w = daoEndereco.alterar(e);
                boolean y = daoPlano.alterar(p);
                boolean z = daoContrato.alterar(con);

                if (v && x && w && y && z) {
                    msg = "Cliente Atualizado!";
                }
            } else {
                msg += "Ação Cancelada</body></html>";
            }

            JOptionPane.showMessageDialog(null, Modificacoes.labelConfig(msg),
                    "Validação", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ContratoVO getContratoForm() {
        //TODO fazer
        return null;
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
        boolean bool = false;

        try {
            // Cliente
            if (!ClienteBO.validarNomeCliente(c)) {
                msg += "Por favor, Digite o NOME Corretamente<br>";
                bool = true;
            }
            if (!ClienteBO.validarCPFcliente(c)) {
                msg += "Por favor, Digite o CPF Corretamente<br>";
                bool = true;
            }
            if (!ClienteBO.validarTelefone(c)) {
                msg += "Por favor, Digite o TELEFONE Corretamente<br>";
                bool = true;
            }
            if (!ClienteBO.validarEmail(c)) {
                msg += "Por favor, Digite o EMAIL Corretamente<br>";
                bool = true;
            }
            if (!ClienteBO.validarRG(c)) {
                msg += "Por favor, Digite o RG Corretamente<br>";
                bool = true;
            }

            // Carro
            if (!CarroBO.validarPlaca(car)) {
                msg += "Por favor, Digite a PLACA Corretamente<br>";
                bool = true;
            }
            if (!CarroBO.validarMarca(car)) {
                msg += "Por favor, Escolha a MARCA<br> ";
                bool = true;
            }
            if (!CarroBO.validarModelo(car)) {
                msg += "Por favor Escolha o MODELO<br>";
                bool = true;
            }
            if (!CarroBO.validarDescricao(car)) {
                msg += "Por favor, Digite a DESCRIÇÃO Corretamente<br>";
                bool = true;
            }

            // Endereco
            if (!EnderecoBO.validarRua(e)) {
                msg += "Por favor, Digite a RUA Corretamente<br>";
                bool = true;
            }
            if (!EnderecoBO.validarNumero(e)) {
                msg += "Por favor, Digite o NUMERO Corretamente<br>";
                bool = true;
            }
            if (!EnderecoBO.validarBairro(e)) {
                msg += "Por favor, Digite o BAIRRO Corretamente<br>";
                bool = true;
            }
            if (!EnderecoBO.validarCidade(e)) {
                msg += "Por favor, Digite a CIDADE Corretamente<br>";
                bool = true;
            }

            // Plano
//            if (!PlanoBO.validarDadosPlano(con)) {
//                bool = true;
//                msg += "Por favor, Escolha o PLANO Corretamente<br>";
//            }
        } catch (Exception e1) {
            System.out.println(e1.getClass().getSimpleName());
            System.out.println(e1.getMessage());
            e1.printStackTrace();
        }
        return bool;
    }

    /**
     * Pega os valores na tela Sub Endereco
     *
     * @return EnderecoVO
     */
    private EnderecoVO getEnderecoForm() {
        return enderecoCtrl.getResultadoForm();
    }

    /**
     * Pega os valores na tela Sub Dados - na Tabela de Carros
     *
     * @return CarroVO
     */
    private CarroVO getCarroForm() {
        ArrayList<CarroVO> carros = dadosCtrl.getFormListaCarro();
        if (carros.size() > 1) {
            msg = "Deseja Cadastrar mais de um Carro?";
            int i = JOptionPane.showConfirmDialog(null, Modificacoes.labelConfig(msg),
                    "Cadastro", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (i == JOptionPane.OK_OPTION) {
                CarroVO carro = dadosCtrl.getFormCarro();
                return daoCarro.cadastrar(carro);
            }
        }
        return null;
    }

    /**
     * Pega os valores na tela Sub Dados
     *
     * @return ClienteVO
     */
    private ClienteVO getClienteForm() {
        return dadosCtrl.getFormCliente();
    }

    /**
     * Pega os valores na tela Sub Plano
     *
     * @return PlanoVO
     */
    private PlanoVO getPlanoForm() {
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
}

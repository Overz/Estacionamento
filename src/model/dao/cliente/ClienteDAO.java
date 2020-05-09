package model.dao.cliente;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.dao.veiculos.CarroDAO;
import model.seletor.SuperSeletor;
import model.vo.cliente.ClienteVO;
import model.vo.cliente.EnderecoVO;
import model.vo.veiculo.CarroVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDAO implements BaseDAO<ClienteVO> {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet result = null;
    private ArrayList<ClienteVO> list = null;
    private ClienteVO clienteVO = null;

    public ClienteVO criarResultSet(ResultSet result) {
        clienteVO = new ClienteVO();
        try {

            clienteVO.setId(result.getInt("idcliente"));
            clienteVO.setNome(result.getString("nome"));
            clienteVO.setCpf(result.getString("cpf"));
            clienteVO.setRg(result.getString("rg"));
            clienteVO.setEmail(result.getString("email"));
            clienteVO.setTelefone(result.getString("telefone"));

            int idEndereco = result.getInt("idendereco");
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            EnderecoVO enderecoVO = enderecoDAO.consultarPorId(idEndereco);
            clienteVO.setEndereco(enderecoVO);

            int idCarro = result.getInt("idcarro");
            CarroDAO carroDAO = new CarroDAO();
            CarroVO carroVO = carroDAO.consultarPorId(idCarro);
            clienteVO.setCarro(carroVO);

            return clienteVO;
        } catch (SQLException e) {
            String method = "CriarResultSet(ResultSet result)";
            System.out.println("\n" +
                    "Class: " + getClass().getSimpleName() + "\n" +
                    "Method: " + method + "\n" +
                    "Msg: " + e.getMessage() + "\n" +
                    "Cause: " + e.getCause()
            );
        }
        return null;
    } // OK

    @Override
    public ArrayList<ClienteVO> consultarTodos() {
        String qry = "SELECT * FROM CLIENTE";
        list = new ArrayList<>();
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            result = stmt.executeQuery();
            while (result.next()) {
                clienteVO = criarResultSet(result);
                list.add(clienteVO);
            }
        } catch (SQLException e) {
            String method = "ConsultarTodos()";
            System.out.println("\n" +
                    "Class: " + getClass().getSimpleName() + "\n" +
                    "Method: " + method + "\n" +
                    "Msg: " + e.getMessage() + "\n" +
                    "Cause: " + e.getCause()
            );
        } finally {
            Banco.closeResultSet(result);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return null;
    } // OK

    @Override
    public ArrayList<ClienteVO> consultar(SuperSeletor<ClienteVO> seletor) {
        String qry = "SELECT * FROM CLIENTE";
        list = new ArrayList<>();

        if (seletor.temFiltro(clienteVO)) {
            qry += seletor.criarFiltro(qry, clienteVO);
        }

        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            result = stmt.executeQuery();
            while (result.next()) {
                clienteVO = criarResultSet(result);
                list.add(clienteVO);
            }
            return list;
        } catch (SQLException e) {
            String method = "Consultar(SuperSeletor<?> seletor)";
            System.out.println("\n" +
                    "Class: " + getClass().getSimpleName() + "\n" +
                    "Method: " + method + "\n" +
                    "Msg: " + e.getMessage() + "\n" +
                    "Cause: " + e.getCause()
            );
        } finally {
            Banco.closeResultSet(result);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return null;
    } // OK

    @Override
    public ClienteVO consultarPorId(int id) {
        String qry = "SELECT * FROM CLIENTE WHERE IDCLIENTE =?";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setInt(1, id);
            result = stmt.executeQuery();
            while (result.next()) {
                clienteVO = criarResultSet(result);
            }
            return clienteVO;
        } catch (SQLException e) {
            String method = "ConsultarPorID(int id)";
            System.out.println("\n" +
                    "Class: " + getClass().getSimpleName() + "\n" +
                    "Method: " + method + "\n" +
                    "Msg: " + e.getMessage() + "\n" +
                    "Cause: " + e.getCause()
            );
        } finally {
            Banco.closeResultSet(result);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return null;
    } // OK

    @Override
    public ClienteVO cadastrar(ClienteVO newObject) {
        String qry = "INSERT INTO CLIENTE (nome, cpf, rg, email, telefone) VALUES (?,?,?,?,?)";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setString(1, newObject.getNome());
            stmt.setString(2, newObject.getCpf());
            stmt.setString(3, newObject.getRg());
            stmt.setString(4, newObject.getEmail());
            stmt.setString(5, newObject.getTelefone());

            result = stmt.getGeneratedKeys();
            if (result.next()) {
                int id = result.getInt(1);
                newObject.setId(id);
            }
            return newObject;
        } catch (SQLException e) {
            String method = "Cadastrar(T newObject)";
            System.out.println("\n" +
                    "Class: " + getClass().getSimpleName() + "\n" +
                    "Method: " + method + "\n" +
                    "Msg: " + e.getMessage() + "\n" +
                    "Cause: " + e.getCause()
            );
        } finally {
            Banco.closeResultSet(result);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return null;
    } // OK

    @Override
    public boolean alterar(ClienteVO object) {
        String qry = "UPDATE CLIENTE SET NOME=?, CPF=?, RG=?, EMAIL=?, TELEFONE=? WHERE IDCLIENTE=?";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setString(1, object.getNome());
            stmt.setString(2, object.getCpf());
            stmt.setString(3, object.getRg());
            stmt.setString(4, object.getEmail());
            stmt.setString(5, object.getTelefone());
            stmt.setInt(6, object.getId());

            if (stmt.executeUpdate() == Banco.CODIGO_RETORNO_SUCESSO) {
                return true;
            }
        } catch (SQLException e) {
            String method = "Alterar(T object)";
            System.out.println("\n" +
                    "Class: " + getClass().getSimpleName() + "\n" +
                    "Method: " + method + "\n" +
                    "Msg: " + e.getMessage() + "\n" +
                    "Cause: " + e.getCause()
            );
        } finally {
            Banco.closeResultSet(result);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return false;
    } // OK

    @Override
    public boolean excluirPorID(int id) {
        String qry = "DELETE FROM CLIENTE WHERE IDCLIENTE = ?";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setInt(1, id);

            if (stmt.executeUpdate() == Banco.CODIGO_RETORNO_SUCESSO) {
                return true;
            }
        } catch (SQLException e) {
            String method = "excluir(int id)";
            System.out.println("\n" +
                    "Class: " + getClass().getSimpleName() + "\n" +
                    "Method: " + method + "\n" +
                    "Msg: " + e.getMessage() + "\n" +
                    "Cause: " + e.getCause()
            );
        } finally {
            Banco.closeResultSet(result);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return false;
    } // OK
}

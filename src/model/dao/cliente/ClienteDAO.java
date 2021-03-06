package model.dao.cliente;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.dao.veiculos.CarroDAO;
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

            clienteVO.setId(result.getInt("id"));
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
        String qry = "select * from cliente;";
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

    @SuppressWarnings("unchecked")
    @Override
    public ClienteVO consultar(String... values) {
        String qry = "select * from cliente where cpf=?;";
        list = new ArrayList<>();
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);
        try {
            stmt.setString(1, values[0]);
            result = stmt.executeQuery();
            if (result != null && result.next()) {
                return criarResultSet(result);
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
    }

    @Override
    public ClienteVO consultarPorId(int id) {
        String qry = "select * from cliente where id=?;";
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
    public ClienteVO cadastrar(ClienteVO newObject, String... values) {
        String qry = "insert into cliente (idEndereco, idCarro, nome, cpf, rg, email, telefone)" +
                     " values (?,?,?,?,?,?,?);";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setInt(1, newObject.getEndereco().getId());
            stmt.setInt(2, newObject.getCarro().getId());
            stmt.setString(3, newObject.getNome().toUpperCase());
            stmt.setString(4, newObject.getCpf().toUpperCase());
            stmt.setString(5, newObject.getRg().toUpperCase());
            stmt.setString(6, newObject.getEmail().toUpperCase());
            stmt.setString(7, newObject.getTelefone().toUpperCase());

            int i = stmt.executeUpdate();
            result = stmt.getGeneratedKeys();
            if (result != null && result.next()) {
                if (i == Banco.CODIGO_RETORNO_SUCESSO) {
                    int id = result.getInt(1);
                    newObject.setId(id);
                }
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
        String qry = "update cliente set nome=?, cpf=?, rg=?, email=?, telefone=? where id=?;";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setString(1, object.getNome());
            stmt.setString(2, object.getCpf());
            stmt.setString(3, object.getRg());
            stmt.setString(4, object.getEmail());
            stmt.setString(5, object.getTelefone());
            stmt.setInt(6, object.getId());

            int i = stmt.executeUpdate();
            if (i == Banco.CODIGO_RETORNO_SUCESSO) {
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
        String qry = "delete from cliente where id=?;";
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

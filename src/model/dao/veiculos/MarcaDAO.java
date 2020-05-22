package model.dao.veiculos;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.vo.veiculo.MarcaVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MarcaDAO implements BaseDAO<MarcaVO> {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet result = null;
    private ArrayList<MarcaVO> list = null;
    private MarcaVO marcaVO = null;

    private MarcaVO criarResultSet(ResultSet result) {
        try {
            marcaVO = new MarcaVO();
            marcaVO.setId(result.getInt("idmarca"));
            marcaVO.setMarca(result.getString("nome"));

            return marcaVO;
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
    public ArrayList<MarcaVO> consultarTodos() {
        String qry = "SELECT * FROM MARCA";
        list = new ArrayList<>();
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            result = stmt.executeQuery();
            while (result.next()) {
                marcaVO = criarResultSet(result);
                list.add(marcaVO);
            }
            return list;
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
    public <T> T consultar(String values) {
        return null;
    }

    @Override
    public MarcaVO consultarPorId(int id) {
        String qry = "SELECT * FROM MARCA WHERE IDMARCA = ?";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setInt(1, id);
            result = stmt.executeQuery();
            while (result.next()) {
                marcaVO = criarResultSet(result);
            }
            return marcaVO;
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
    public MarcaVO cadastrar(MarcaVO newObject) {
        String qry = "INSERT INTO MARCA (nome) VALUES (?)";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setString(1, newObject.getMarca());

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
    public boolean alterar(MarcaVO object) {
        String qry = "UPDATE MARCA SET NOME=? WHERE IDMARCA=?";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setString(1, object.getMarca());
            stmt.setInt(2, object.getId());

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
        String qry = "DELETE FROM MARCA WHERE IDMARCA = ?";
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

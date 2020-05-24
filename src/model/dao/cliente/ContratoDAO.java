package model.dao.cliente;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.vo.cliente.ContratoVO;

import java.sql.*;
import java.util.ArrayList;

public class ContratoDAO implements BaseDAO<ContratoVO> {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet result = null;
    private ArrayList<ContratoVO> list = null;
    private ContratoVO contratoVO = null;

    public ContratoVO criarResultSet(ResultSet result) {
        contratoVO = new ContratoVO();
        try {
            contratoVO.setId(result.getInt("idcontrato"));
            contratoVO.setNumeroCartao(result.getLong("n_cartao"));
            contratoVO.setDtEntrada(result.getTimestamp("dt_entrada").toLocalDateTime());
            contratoVO.setDtSaida(result.getTimestamp("dt_validade").toLocalDateTime());
            contratoVO.setValor(result.getDouble("valor"));
            contratoVO.setAtivo(result.getBoolean("ativo"));

            return contratoVO;
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
    public ArrayList<ContratoVO> consultarTodos() {
        String qry = "SELECT * FROM CONTRATO";
        list = new ArrayList<>();
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            result = stmt.executeQuery();
            while (result.next()) {
                contratoVO = criarResultSet(result);
                list.add(contratoVO);
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
    public <T> T consultar(String... values) {
        return null;
    }

    @Override
    public ContratoVO consultarPorId(int id) {
        String qry = "SELECT * FROM CONTRATO WHERE IDCONTRATO = ?";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setInt(1, id);
            result = stmt.executeQuery();
            while (result.next()) {
                contratoVO = criarResultSet(result);
            }
            return contratoVO;
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
    public ContratoVO cadastrar(ContratoVO newObject, String... values) {
        String qry = "INSERT INTO CONTRATO (N_CARTAO, DT_ENTRADA, DT_SAIDA, ATIVO, VALOR) VALUES (?,?,?,?,?)";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);
        try {
            stmt.setLong(1, newObject.getNumeroCartao());
            stmt.setTimestamp(2, Timestamp.valueOf(newObject.getDtEntrada()));
            stmt.setTimestamp(3, Timestamp.valueOf(newObject.getDtSaida()));
            stmt.setBoolean(4, newObject.isAtivo());
            stmt.setDouble(5, newObject.getValor());

            result = stmt.getGeneratedKeys();
            while (result.next()) {
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
    public boolean alterar(ContratoVO object) {
        String qry = "UPDATE CONTRATO SET N_CARTAO=?, DT_ENTRADA=?, DT_SAIDA=?, ATIVO=?, VALOR=? WHERE IDCONTRATO =?";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setLong(1, object.getNumeroCartao());
            stmt.setTimestamp(2, Timestamp.valueOf(object.getDtEntrada()));
            stmt.setTimestamp(3, Timestamp.valueOf(object.getDtSaida()));
            stmt.setBoolean(4, object.isAtivo());
            stmt.setDouble(5, object.getValor());

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
        String qry = "DELETE FROM CONTRATO WHERE IDCONTRATO =?";
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

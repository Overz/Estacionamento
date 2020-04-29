package model.dao.movientos;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.seletor.SuperSeletor;
import model.vo.movimentos.FluxoVO;
import model.vo.movimentos.MovimentoVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FluxoDAO implements BaseDAO<FluxoVO> {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet result = null;
    private ArrayList<FluxoVO> list = null;
    private FluxoVO fluxoVO = null;

    public FluxoVO criarResultSet(ResultSet result) {
        fluxoVO = new FluxoVO();

        try {
            fluxoVO.setId(result.getInt("idfluxo"));

            int id = result.getInt("idmovimento");
            MovimentoDAO movimentoDAO = new MovimentoDAO();
            MovimentoVO movimentoVO = movimentoDAO.consultarPorId(id);
            fluxoVO.setMovimento(movimentoVO);

            return fluxoVO;
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
    public ArrayList<?> consultarTodos() {
        String qry = "SELECT * FROM FLUXO";
        list = new ArrayList<>();
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            result = stmt.executeQuery();
            while (result.next()) {
                fluxoVO = criarResultSet(result);
                list.add(fluxoVO);
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
    public ArrayList<?> consultar(SuperSeletor<FluxoVO> seletor) {
        return null;
    } // NOT IMPLEMENTED

    @Override
    public FluxoVO consultarPorId(int id) {
        String qry = "SELECT * FROM FLUXO WHERE IDFLUXO = ?";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setInt(1, id);
            result = stmt.executeQuery();
            while (result.next()) {
                fluxoVO = criarResultSet(result);
            }
            return fluxoVO;
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
    public FluxoVO cadastrar(FluxoVO newObject) {
        return null;
    } // NOT IMPLEMENTED

    @Override
    public boolean alterar(FluxoVO object) {
        return false;
    } // NOT IMPLEMENTED

    @Override
    public boolean excluir(int id) {
        String qry = "DELETE FROM FLUXO WHERE IDFLUXO = ?";
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

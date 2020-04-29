package model.dao.movientos;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.dao.cliente.PlanoDAO;
import model.seletor.SuperSeletor;
import model.vo.cliente.PlanoVO;
import model.vo.movimentos.MovimentoVO;
import model.vo.movimentos.TicketVO;

import java.sql.*;
import java.util.ArrayList;

public class MovimentoDAO implements BaseDAO<MovimentoVO> {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet result = null;
    private ArrayList<MovimentoVO> list = null;
    private MovimentoVO movimentoVO = null;

    public MovimentoVO criarResultSet(ResultSet result) {
        movimentoVO = new MovimentoVO();

        try {
            movimentoVO.setId(result.getInt("idmovimento"));

            int idT = result.getInt("idticket");
            TicketDAO ticketDAO = new TicketDAO();
            TicketVO ticketVO = ticketDAO.consultarPorId(idT);
            movimentoVO.setTicket(ticketVO);

            int idP = result.getInt("idplano");
            PlanoDAO planoDAO = new PlanoDAO();
            PlanoVO planoVO = planoDAO.consultarPorId(idP);
            movimentoVO.setPlano(planoVO);

            movimentoVO.setHr_entrada(result.getTimestamp("hr_entrada").toLocalDateTime());
            movimentoVO.setHr_saida(result.getTimestamp("hr_saida").toLocalDateTime());

            return movimentoVO;
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
        String qry = "SELECT * FROM MOVIMENTO";
        list = new ArrayList<>();
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            result = stmt.executeQuery();
            while (result.next()) {
                movimentoVO = criarResultSet(result);
                list.add(movimentoVO);
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
    public ArrayList<?> consultar(SuperSeletor<MovimentoVO> seletor) {
        String qry = "SELECT * FROM MOVIMENTO";

        if (seletor.temFiltro(movimentoVO)) {
            qry += seletor.criarFiltro(qry, movimentoVO);
        }

        list = new ArrayList<>();
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            result = stmt.executeQuery();
            while (result.next()) {
                movimentoVO = criarResultSet(result);
                list.add(movimentoVO);
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
    public MovimentoVO consultarPorId(int id) {
        String qry = "SELECT * FROM MOVIMENTO WHERE IDMOVIMENTO = ?";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setInt(1, id);
            result = stmt.executeQuery();
            while (result.next()) {
                movimentoVO = criarResultSet(result);
            }
            return movimentoVO;
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
    public MovimentoVO cadastrar(MovimentoVO newObject) {
        String qry = "INSERT INTO MOVIMENTO (hr_entrada, hr_saida) VALUES (?,?)";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setTimestamp(1, Timestamp.valueOf(newObject.getHr_entrada()));
            stmt.setTimestamp(2, Timestamp.valueOf(newObject.getHr_saida()));

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
    public boolean alterar(MovimentoVO object) {
        String qry = "UPDATE MOVIMENTO SET HR_ENTRADA=?, HR_SAIDA=?";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setTimestamp(1, Timestamp.valueOf(object.getHr_entrada()));
            stmt.setTimestamp(2, Timestamp.valueOf(object.getHr_saida()));

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
    public boolean excluir(int id) {
        String qry = "DELETE FROM MOVIMENTO WHERE IDMOVIMENTO = 1";
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

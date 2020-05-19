package model.dao.movientos;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.dao.cliente.ClienteDAO;
import model.seletor.SuperSeletor;
import model.vo.cliente.ClienteVO;
import model.vo.movimentos.TicketVO;
import util.Constantes;

import java.sql.*;
import java.util.ArrayList;

public class TicketDAO implements BaseDAO<TicketVO> {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet result = null;
    private ArrayList<TicketVO> list = null;
    private TicketVO ticketVO = null;

    public TicketVO criarResultSet(ResultSet result) {
        ticketVO = new TicketVO();

        try {
            ticketVO.setId(result.getInt("idticket"));

            int idCliente = result.getInt("idCliente");
            ClienteDAO clienteDAO = new ClienteDAO();
            ClienteVO clienteVO = clienteDAO.consultarPorId(idCliente);
            ticketVO.setCliente(clienteVO);

            ticketVO.setNumero(result.getLong("n_ticket"));
            ticketVO.setValor(result.getDouble("valor"));
            ticketVO.setTipo(result.getString("tipo"));
            ticketVO.setDataValidacao(result.getTimestamp("hr_validacao").toLocalDateTime());
            ticketVO.setStatus(result.getBoolean("statusTicket"));

            return ticketVO;
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
    public ArrayList<TicketVO> consultarTodos() {
        String qry = "SELECT * FROM TICKET";
        list = new ArrayList<>();
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            result = stmt.executeQuery();
            while (result.next()) {
                ticketVO = criarResultSet(result);
                list.add(ticketVO);
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
    public ArrayList<TicketVO> consultar(SuperSeletor<TicketVO> seletor) {
        String qry = "SELECT * FROM TICKET";

        if (seletor.temFiltro(ticketVO)) {
            qry += seletor.criarFiltro(qry, ticketVO);
        }

        list = new ArrayList<>();
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            result = stmt.executeQuery();
            while (result.next()) {
                ticketVO = criarResultSet(result);
                list.add(ticketVO);
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
    public TicketVO consultarObjeto(String... values) {
        return null;
    }

    @Override
    public TicketVO consultarPorId(int id) {
        String qry = "SELECT * FROM TICKET WHERE IDTICKET = ?";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setInt(1, id);
            result = stmt.executeQuery();
            while (result.next()) {
                ticketVO = criarResultSet(result);
            }
            return ticketVO;
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
    public TicketVO cadastrar(TicketVO newObject) {
        String qry = "INSERT INTO TICKET (N_TICKET, VALOR, HR_VALIDACAO) VALUES (?,?,?)";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setLong(1, newObject.getNumero());
            stmt.setDouble(1, newObject.getValor());
            stmt.setTimestamp(1, Timestamp.valueOf(newObject.getDataValidacao()));

            result = stmt.executeQuery();
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
    public boolean alterar(TicketVO object) {
        String qry = null;
        if (Constantes.FLAG == 1) {
            qry = "UPDATE TICKET SET STATUSTICKET = ? WHERE IDTICKET = ?";
        } else {
            qry = "UPDATE TICKET SET N_TICKET=?, VALOR=?, TIPO=?, HR_VALIDACAO=?, STATUSTICKET = ? WHERE IDTICKET=?";
        }
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            if (Constantes.FLAG == 1) {
                stmt.setBoolean(1, object.getStatus());
                stmt.setInt(2, object.getId());
            } else {
                stmt.setLong(1, object.getNumero());
                stmt.setDouble(2, object.getValor());
                stmt.setString(3, object.getTipo());
                stmt.setTimestamp(4, Timestamp.valueOf(object.getDataValidacao()));
                stmt.setBoolean(5, object.getStatus());
                stmt.setInt(6, object.getId());
            }

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
        String qry = "DELETE FROM TICKET WHERE IDTICKET = ?";
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

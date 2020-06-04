package model.dao.movientos;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.vo.movimentos.TicketVO;
import util.constantes.ConstHelpers;

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
            ticketVO.setId(result.getInt("id"));
            ticketVO.setNumero(result.getLong("n_ticket"));
            ticketVO.setValor(result.getDouble("valor"));
            ticketVO.setTipo(result.getString("tipo"));
            ticketVO.setDataEntrada(result.getTimestamp("hr_entrada").toLocalDateTime());
            ticketVO.setDataValidacao(result.getTimestamp("hr_validacao").toLocalDateTime());
            ticketVO.setStatus(result.getBoolean("statusTicket"));
            ticketVO.setValidado(result.getBoolean("validado"));

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
        String qry = "select * from ticket;";
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
    public <T> T consultar(String... values) {
        return null;
    }

    @Override
    public TicketVO consultarPorId(int id) {
        String qry = "select * from ticket where id=?;";
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
    public TicketVO cadastrar(TicketVO newObject, String... values) {
        String qry;
        if (ConstHelpers.FLAG == 1) {
            qry = "insert into ticket (n_ticket, hr_entrada, statusticket, validado)" +
                  "values (?,?,?,?);";
        } else {
            qry = "insert into ticket (n_ticket, valor, tipo, hr_entrada, hr_saida, statusticket, validado" +
                  "values (?,?,?,?,?,?,?);";
        }
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            if (ConstHelpers.FLAG == 1) {
                stmt.setLong(1, newObject.getNumero());
                stmt.setTimestamp(2, Timestamp.valueOf(newObject.getDataEntrada()));
                stmt.setBoolean(3, newObject.getStatus());
                stmt.setBoolean(4, newObject.getValidado());

                stmt.execute();

            } else {
                stmt.setLong(1, newObject.getNumero());
                stmt.setDouble(2, newObject.getValor());
                stmt.setString(3, newObject.getTipo());
                stmt.setTimestamp(4, Timestamp.valueOf(newObject.getDataEntrada()));
                stmt.setTimestamp(5, Timestamp.valueOf(newObject.getDataValidacao()));
                stmt.setBoolean(6, newObject.getStatus());
                stmt.setBoolean(7, newObject.getValidado());
            }

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
    public boolean alterar(TicketVO object) {
        String qry = "update ticket set valor=?, tipo=?, hr_validacao=?, statusticket=?, validado=? where id=?;";

        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setDouble(1, object.getValor());
            stmt.setString(2, object.getTipo());
            stmt.setTimestamp(3, Timestamp.valueOf(object.getDataValidacao()));
            stmt.setBoolean(4, object.getStatus());
            stmt.setBoolean(5, object.getValidado());
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
        String qry = "delete from ticket where id=?;";
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

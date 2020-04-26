package model.dao.movientos;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.dao.cliente.ClienteDAO;
import model.seletor.SuperSeletor;
import model.vo.cliente.ClienteVO;
import model.vo.movimentos.TicketVO;

import java.sql.*;
import java.util.ArrayList;

public class TicketDAO implements BaseDAO<TicketVO> {

    public TicketVO criarResultSet(ResultSet result) {
        TicketVO ticket = new TicketVO();

        try {
            ticket.setId(result.getInt("idticket"));

            int idCliente = result.getInt("idCliente");
            ClienteDAO clienteDAO = new ClienteDAO();
            ClienteVO clienteVO = clienteDAO.consultarPorId(idCliente);
            ticket.setCliente(clienteVO);

            ticket.setNumero(result.getLong("n_ticket"));
            ticket.setValor(result.getDouble("valor"));
            ticket.setDataValidacao(result.getTimestamp("hr_validacao").toLocalDateTime());

        } catch (SQLException e) {
            System.out.println();
            System.out.println("/****************************************************************/");
            System.out.println(this.getClass().getSimpleName());
            System.out.println("Method: criarResultSet()");
            System.out.println("SQL Message:" + e.getMessage());
            System.out.println("SQL Cause:" + e.getCause());
            System.out.println("SQL State:" + e.getSQLState());
            System.out.println("/****************************************************************/");
            System.out.println();
        }

        return ticket;
    }

    @Override
    public ArrayList<TicketVO> consultarTodos() {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet result = null;

        ArrayList<TicketVO> lista = new ArrayList<TicketVO>();
        String qry = " SELECT * FROM TICKET ";

        try {
            result = stmt.executeQuery(qry);
            while (result.next()) {
                TicketVO vo = criarResultSet(result);
                lista.add(vo);
            }
        } catch (SQLException e) {
            System.out.println();
            System.out.println("/****************************************************************/");
            System.out.println(this.getClass().getSimpleName());
            System.out.println("Method: consultarTodos()");
            System.out.println(qry);
            System.out.println("SQL Message:" + e.getMessage());
            System.out.println("SQL Cause:" + e.getCause());
            System.out.println("SQL State:" + e.getSQLState());
            System.out.println("/****************************************************************/");
            System.out.println();
        } finally {
            Banco.closeResultSet(result);
            Banco.closeStatement(stmt);
            Banco.closeConnection(conn);
        }
        return lista;
    }

    @Override
    public ArrayList<?> consultar(SuperSeletor<TicketVO> seletor) {
        return null;
    }

    @Override
    public TicketVO consultarPorId(int id) {
        String qry = " SELECT * FROM TICKET WHERE IDTICKET = ? ";
        TicketVO ticket = null;
        ResultSet result = null;
        PreparedStatement stmt = null;
        Connection conn = Banco.getConnection();

        try {
            stmt = conn.prepareStatement(qry);
            stmt.setInt(1, id);
            result = stmt.executeQuery();

            while (result.next()) {
                ticket = criarResultSet(result);
            }
        } catch (SQLException e) {
            System.out.println();
            System.out.println("/****************************************************************/");
            System.out.println(this.getClass().getSimpleName());
            System.out.println("Method: consultarPorID()");
            System.out.println(qry);
            System.out.println("SQL Message:" + e.getMessage());
            System.out.println("SQL Cause:" + e.getCause());
            System.out.println("SQL State:" + e.getSQLState());
            System.out.println("/****************************************************************/");
            System.out.println();
        } finally {
            Banco.closeResultSet(result);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }

        return ticket;
    }

    @Override
    public TicketVO cadastrar(TicketVO TicketVO) {
        String qry = " INSERT INTO TICKET (N_TICKET, VALOR) VALUES (?,?) ";
        TicketVO ticket = null;
        ResultSet resultSet = null;
        Connection conn = Banco.getConnection();
        PreparedStatement stmt =
                Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setLong(1, ticket.getNumero());
            stmt.setDouble(2, ticket.getValor());

            resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                ticket.setId(id);
            }
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public boolean alterar(TicketVO TicketVO) {
        String qry = " UPDATE TICKET T SET T.N_TICKET = ?, T.VALOR = ? WHERE ID = ?";
        ResultSet resultSet = null;
        TicketVO ticket = null;
        Connection conn = Banco.getConnection();
        PreparedStatement stmt =
                Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setLong(1, ticket.getNumero());
            stmt.setDouble(2, ticket.getValor());
            stmt.setInt(3, ticket.getId());

            resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                ticket.setId(id);
                return true;
            }
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            Banco.closeResultSet(resultSet);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return false;
    }

    @Override
    public boolean excluir(int[] id) {
        // TODO Auto-generated method stub
        return false;
    }

}

package model.dao.movientos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.dao.cliente.ClienteDAO;
import model.seletor.SuperSeletor;
import model.vo.cliente.ClienteVO;
import model.vo.movimentos.TicketVO;

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
//		String qry = " SELECT * FROM TICKET WHERE IDTICKET = ? ";
		String qry = " SELECT * FROM TICKET WHERE IDTICKET = " + id;
		TicketVO ticket = null;

		Connection conn = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatement(conn, qry);
		ResultSet result = null;

		try {

//			stmt.setInt(1, id);
			result = stmt.executeQuery(qry);

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean alterar(TicketVO TicketVO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excluir(int[] id) {
		// TODO Auto-generated method stub
		return false;
	}

}

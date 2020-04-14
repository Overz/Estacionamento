package model.dao.movientos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.dao.cliente.PlanoDAO;
import model.seletor.Seletor;
import model.vo.movimentos.MovimentoVO;
import model.vo.cliente.PlanoVO;
import model.vo.movimentos.TicketVO;

public class MovimentoDAO implements BaseDAO<MovimentoVO> {

	public MovimentoVO criarResultSet(ResultSet result) {
		MovimentoVO movimento = new MovimentoVO();

		try {

			movimento.setId(result.getInt("idmovimento"));

			int idT = result.getInt("idticket");
			TicketDAO ticketDAO = new TicketDAO();
			TicketVO ticketVO = ticketDAO.consultarPorId(idT);
			movimento.setTicket(ticketVO);

			int idP = result.getInt("idplano");
			PlanoDAO planoDAO = new PlanoDAO();
			PlanoVO planoVO = planoDAO.consultarPorId(idP);
			movimento.setPlano(planoVO);

			movimento.setHr_entrada(result.getTimestamp("hr_entrada").toLocalDateTime());
			movimento.setHr_saida(result.getTimestamp("hr_saida").toLocalDateTime());

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

		return movimento;
	}

	@Override
	public ArrayList<MovimentoVO> consultarTodos() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet result = null;

		ArrayList<MovimentoVO> lista = new ArrayList<MovimentoVO>();
		String qry = " SELECT * FROM MOVIMENTO ";

		try {
			result = stmt.executeQuery(qry);
			while (result.next()) {
				MovimentoVO vo = criarResultSet(result);
				lista.add(vo);
			}
		} catch (SQLException e) {
			System.out.println();
			System.out.println("/****************************************************************/");
			System.out.println("Class:" + this.getClass().getSimpleName());
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
	public ArrayList<MovimentoVO> consultar(Seletor seletor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MovimentoVO consultarPorId(int id) {
//		String qry = " SELECT * FROM MOVIMENTO WHERE IDMOVIMENTO = ? ";
		String qry = " SELECT * FROM MOVIMENTO WHERE IDMOVIMENTO = " + id;
		MovimentoVO movimento = null;

		Connection conn = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatement(conn, qry);
		ResultSet result = null;

		try {

//			stmt.setInt(1, id);
			result = stmt.executeQuery(qry);

			while (result.next()) {
				movimento = criarResultSet(result);
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

		return movimento;
	}

	@Override
	public MovimentoVO cadastrar(MovimentoVO MovimentoVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean alterar(MovimentoVO MovimentoVO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excluir(int[] id) {
		String qry = "DELETE FROM MOVIMENTO WHERE IDMOVIMENTO IN (?, ?, ?, ?, ?, ?, ?, ?)";

		Connection conn = Banco.getConnection();
		PreparedStatement prepStmt = Banco.getPreparedStatement(conn, qry);

		try {

			int codigoRetorno = prepStmt.executeUpdate();
			while (codigoRetorno == 1) {
				continue;
			}
			return true;

		} catch (SQLException e) {
			System.out.println();
			System.out.println("/****************************************************************/");
			System.out.println(this.getClass());
			System.out.println("Method: method_name");
			System.out.println(qry);
			System.out.println("SQL Message:" + e.getMessage());
			System.out.println("SQL Cause:" + e.getCause());
			System.out.println("SQL State:" + e.getSQLState());
			System.out.println("/****************************************************************/");
			System.out.println();
		} finally {
			Banco.closePreparedStatement(prepStmt);
			Banco.closeConnection(conn);
		}
		return false;
	}

}

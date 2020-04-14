package model.dao.cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.seletor.Seletor;
import model.vo.cliente.ClienteVO;
import model.vo.cliente.ContratoVO;
import model.vo.cliente.PlanoVO;

public class ContratoDAO implements BaseDAO<ContratoVO> {

	public ContratoVO criarResultSet(ResultSet result) {
		ContratoVO contrato = new ContratoVO();

		try {

			contrato.setId(result.getInt("idcontrato"));

			contrato.setNumeroCartao(result.getLong("n_cartao"));
			contrato.setDtEntrada(result.getTimestamp("dt_entrada").toLocalDateTime());
			contrato.setDtSaida(result.getTimestamp("dt_saida").toLocalDateTime());
			contrato.setValor(result.getDouble("valor"));
			contrato.setAtivo(result.getBoolean("ativo"));

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

		return contrato;
	}

	@Override
	public ArrayList<ContratoVO> consultarTodos() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet result = null;

		ArrayList<ContratoVO> lista = new ArrayList<ContratoVO>();
		String qry = " SELECT * FROM CONTRATO ";

		try {
			result = stmt.executeQuery(qry);
			while (result.next()) {
				ContratoVO vo = criarResultSet(result);
				lista.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("/****************************************************************/");
			System.out.println(this.getClass().getSimpleName());
			System.out.println("Method: consultarTodos()");
			System.out.println(qry);
			System.out.println("SQL Message:" + e.getMessage());
			System.out.println("SQL Cause:" + e.getCause());
			System.out.println("SQL State:" + e.getSQLState());
			System.out.println("/****************************************************************/");
		} finally {
			Banco.closeResultSet(result);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return lista;
	}

	@Override
	public ArrayList<?> consultar(Seletor seletor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContratoVO consultarPorId(int id) {
//		String qry = " SELECT * FROM CONTRATO WHERE IDCONTRATO = ? ";
		String qry = " SELECT * FROM CONTRATO WHERE IDCONTRATO = " + id;
		ContratoVO contrato = null;

		Connection conn = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatement(conn, qry);
		ResultSet result = null;

		try {

//			stmt.setInt(1, id);
			result = stmt.executeQuery(qry);

			while (result.next()) {
				contrato = criarResultSet(result);
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

		return contrato;
	}

	@Override
	public ContratoVO cadastrar(ContratoVO ContratoVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean alterar(ContratoVO obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excluir(int[] id) {
		// TODO Auto-generated method stub
		return false;
	}

}

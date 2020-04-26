package model.dao.cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.seletor.SuperSeletor;
import model.vo.cliente.ClienteVO;
import model.vo.cliente.ContratoVO;
import model.vo.cliente.PlanoVO;

public class PlanoDAO implements BaseDAO<PlanoVO> {

	public PlanoVO criarResultSet(ResultSet result) {
		PlanoVO plano = new PlanoVO();

		try {

			plano.setId(result.getInt("idplano"));
			plano.setTipo(result.getNString("tipo"));
			plano.setDescircao(result.getString("descricao"));

			int idContrato = result.getInt("idContrato");
			ContratoDAO contratoDAO = new ContratoDAO();
			ContratoVO contratoVO = contratoDAO.consultarPorId(idContrato);
			plano.setContrato(contratoVO);

			int idCliente = result.getInt("idcliente");
			ClienteDAO clienteDAO = new ClienteDAO();
			ClienteVO clienteVO = clienteDAO.consultarPorId(idCliente);
			plano.setCliente(clienteVO);

		} catch (SQLException e) {
			System.out.println();
			System.out.println("/****************************************************************/");
			System.out.println(this.getClass().getSimpleName());
			System.out.println("Method: criarResultSet()");
			System.out.println();
			System.out.println("SQL Message:" + e.getMessage());
			System.out.println("SQL Cause:" + e.getCause());
			System.out.println("SQL State:" + e.getSQLState());
			System.out.println("/****************************************************************/");
			System.out.println();
		}

		return plano;
	}

	@Override
	public ArrayList<PlanoVO> consultarTodos() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet result = null;

		ArrayList<PlanoVO> lista = new ArrayList<PlanoVO>();
		String qry = " SELECT * FROM PLANO ";

		try {
			result = stmt.executeQuery(qry);
			while (result.next()) {
				PlanoVO vo = criarResultSet(result);
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
	public ArrayList<?> consultar(SuperSeletor<PlanoVO> seletor) {
		return null;
	}

	@Override
	public PlanoVO consultarPorId(int id) {
//		String qry = " SELECT * FROM PLANO WHERE IDPLANO = ? ";
		String qry = " SELECT * FROM PLANO WHERE IDPLANO = " + id;
		PlanoVO plano = null;

		Connection conexao = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatement(conexao, qry);
		ResultSet result = null;

		try {

//			stmt.setInt(1, id);
			result = stmt.executeQuery(qry);

			while (result.next()) {
				plano = criarResultSet(result);
			}
		} catch (SQLException e) {
			System.out.println();
			System.out.println("/****************************************************************/");
			System.out.println(this.getClass().getSimpleName());
			System.out.println("Method: consultarPorId");
			System.out.println(qry);
			System.out.println("SQL Message:" + e.getMessage());
			System.out.println("SQL Cause:" + e.getCause());
			System.out.println("SQL State:" + e.getSQLState());
			System.out.println("/****************************************************************/");
			System.out.println();
		} finally {
			Banco.closeResultSet(result);
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conexao);
		}

		return plano;
	}

	@Override
	public PlanoVO cadastrar(PlanoVO PlanoVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean alterar(PlanoVO obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excluir(int[] id) {
		// TODO Auto-generated method stub
		return false;
	}

}

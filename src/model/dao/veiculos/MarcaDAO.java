package model.dao.veiculos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.seletor.Seletor;
import model.vo.veiculo.MarcaVO;

public class MarcaDAO implements BaseDAO<MarcaVO> {

	public MarcaVO criarResultSet(ResultSet result) {
		MarcaVO vo = null;

		try {
			vo = new MarcaVO();

			vo.setId(result.getInt("idmarca"));
			vo.setMarca(result.getString("nome"));

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

		return vo;
	}

	@Override
	public ArrayList<MarcaVO> consultarTodos() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet result = null;

		ArrayList<MarcaVO> lista = new ArrayList<MarcaVO>();
		String qry = " SELECT * FROM MARCA ";

		try {
			result = stmt.executeQuery(qry);
			while (result.next()) {
				MarcaVO vo = criarResultSet(result);
				lista.add(vo);
			}
		} catch (SQLException e) {
			System.out.println();
			System.out.println("/*********************************************************/");
			System.out.println(this.getClass().getSimpleName());
			System.out.println("Method: listarTodos()");
			System.out.println(qry);
			System.out.println("SQL Message:" + e.getMessage());
			System.out.println("SQL Cause:" + e.getCause());
			System.out.println("SQL State:" + e.getSQLState());
			System.out.println("/*********************************************************/");
			System.out.println();
		} finally {
			Banco.closeResultSet(result);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}

		return lista;
	}

	@Override
	public ArrayList<MarcaVO> consultar(Seletor seletor) {

		String qry = " SELECT * FROM MARCA WHERE NOME=? ";
		ArrayList<MarcaVO> lista = new ArrayList<MarcaVO>();

		Connection conexao = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatement(conexao, qry);
		ResultSet result = null;

		if (seletor.temFiltro()) {
			qry = seletor.criarFiltroCliente(qry);
		}

		try {
			result = stmt.executeQuery(qry);
			while (result.next()) {
				MarcaVO marca = criarResultSet(result);
				lista.add(marca);
			}
		} catch (SQLException e) {
			System.out.println();
			System.out.println("/*********************************************************/");
			System.out.println(this.getClass().getSimpleName());
			System.out.println("Method: listarTodos()");
			System.out.println(qry);
			System.out.println("SQL Message:" + e.getMessage());
			System.out.println("SQL Cause:" + e.getCause());
			System.out.println("SQL State:" + e.getSQLState());
			System.out.println("/*********************************************************/");
			System.out.println();
		} finally {
			Banco.closeResultSet(result);
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conexao);
		}

		return lista;
	}

	@Override
	public MarcaVO consultarPorId(int id) {
//		String qry = " SELECT * FROM MARCA WHERE IDMARCA = ? ";
		String qry = " SELECT * FROM MARCA WHERE IDMARCA = " + id;
		MarcaVO marca = null;

		Connection conexao = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatement(conexao, qry);
		ResultSet result = null;

		try {

//			stmt.setInt(1, id);
			result = stmt.executeQuery(qry);

			while (result.next()) {
				marca = criarResultSet(result);
			}
		} catch (SQLException e) {
			System.out.println();
			System.out.println("/****************************************************************/");
			System.out.println(this.getClass().getSimpleName());
			System.out.println("Method: consultarPorId()");
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

		return marca;
	}

	@Override
	public MarcaVO cadastrar(MarcaVO MarcaVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean alterar(MarcaVO entidade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excluir(int[] id) {
		// TODO Auto-generated method stub
		return false;
	}

}

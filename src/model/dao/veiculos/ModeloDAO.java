package model.dao.veiculos;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.vo.veiculo.MarcaVO;
import model.vo.veiculo.ModeloVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModeloDAO implements BaseDAO<ModeloVO> {

	public ModeloVO criarResultSet(ResultSet result) {
		ModeloVO vo = null;

		try {
			vo = new ModeloVO();
			vo.setId(result.getInt("idmodelo"));

			int idMarca = result.getInt("idmarca");
			MarcaDAO marcaDAO = new MarcaDAO();
			MarcaVO marcaVO = marcaDAO.consultarPorId(idMarca);

			vo.setMarca(marcaVO);
			vo.setDescricao(result.getString("descricao"));

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
	public ArrayList<ModeloVO> consultarTodos() {
		String qry = " SELECT * FROM MODELO ";
		ResultSet result = null;
		Connection conn = Banco.getConnection();
		PreparedStatement stmt =
				Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);
		ArrayList<ModeloVO> lista = new ArrayList<ModeloVO>();

		try {
			result = stmt.executeQuery(qry);
			while (result.next()) {
				ModeloVO vo = criarResultSet(result);
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
	public ArrayList<?> consultar(ModeloVO seletor) {
		return null;
	}

	@Override
	public ModeloVO consultarPorId(int id) {
		String qry = " SELECT * FROM MODELO WHERE IDMODELO = ? ";
		ModeloVO modelo = null;
		ResultSet result = null;
		PreparedStatement stmt = null;
		Connection conn = Banco.getConnection();

		try {
			stmt = conn.prepareStatement(qry);
			stmt.setInt(1, id);
			result = stmt.executeQuery();

			while (result.next()) {
				modelo = criarResultSet(result);
			}
		} catch (SQLException e) {
			System.out.println();
			System.out.println("/****************************************************************/");
			System.out.println(this.getClass().getSimpleName());
			System.out.println("Method: consultarPorID");
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

		return modelo;
	}

	@Override
	public ModeloVO cadastrar(ModeloVO newObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean alterar(ModeloVO entidade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excluir(int[] id) {
		// TODO Auto-generated method stub
		return false;
	}

}

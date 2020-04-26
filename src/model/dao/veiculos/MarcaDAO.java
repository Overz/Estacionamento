package model.dao.veiculos;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.seletor.SuperSeletor;
import model.vo.veiculo.MarcaVO;

import java.sql.*;
import java.util.ArrayList;

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
    public ArrayList<?> consultar(SuperSeletor<MarcaVO> seletor) {
        return null;
    }

    @Override
    public MarcaVO consultarPorId(int id) {
        String qry = " SELECT * FROM MARCA WHERE IDMARCA = ? ";
        MarcaVO marca = null;
        ResultSet result = null;
        PreparedStatement stmt = null;
        Connection conn = Banco.getConnection();

        try {
            stmt = conn.prepareStatement(qry);
            stmt.setInt(1, id);
            result = stmt.executeQuery();

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
            Banco.closeConnection(conn);
        }

        return marca;
    }

    @Override
    public MarcaVO cadastrar(MarcaVO MarcaVO) {
        String qry = " INSERT INTO MARCA (NOME) VALUES (?) ";
        MarcaVO marca = null;
        ResultSet result = null;
        Connection conn = Banco.getConnection();
        PreparedStatement stmt =
                Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setString(1, marca.getMarca());

            result = stmt.getGeneratedKeys();
            if (result.next()) {
				int id = result.getInt(1);
				marca.setId(id);
            }
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
			System.out.println(e.getMessage());
        } finally {
        	Banco.closeResultSet(result);
        	Banco.closePreparedStatement(stmt);
        	Banco.closeConnection(conn);
		}
        return marca;
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

package model.dao.movientos;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.seletor.SuperSeletor;
import model.vo.movimentos.FluxoVO;
import model.vo.movimentos.MovimentoVO;

import java.sql.*;
import java.util.ArrayList;

public class FluxoDAO implements BaseDAO<FluxoVO> {

    public FluxoVO criarResultSet(ResultSet result) {
        FluxoVO fluxo = new FluxoVO();

        try {

            fluxo.setId(result.getInt("idfluxo"));

            int id = result.getInt("idmovimento");
            MovimentoDAO movimentoDAO = new MovimentoDAO();
            MovimentoVO movimentoVO = movimentoDAO.consultarPorId(id);
            fluxo.setMovimento(movimentoVO);

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

        return fluxo;
    }

    @Override
    public ArrayList<FluxoVO> consultarTodos() {
        String qry = " SELECT * FROM FLUXO ";
        ResultSet result = null;
        ArrayList<FluxoVO> lista = new ArrayList<>();
        Connection conn = Banco.getConnection();
        PreparedStatement stmt = Banco.getPreparedStatement(conn);

        try {
            stmt = conn.prepareStatement(qry);
            result = stmt.executeQuery(qry);
            while (result.next()) {
                FluxoVO vo = criarResultSet(result);
                lista.add(vo);
            }

        } catch (SQLException e) {
            System.out.println();
            System.out.println("/****************************************************************/");
            System.out.println(this.getClass().getSimpleName());
            System.out.println("Method: consultarTodos()");
            //System.out.println(qry);
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
    public ArrayList<?> consultar(SuperSeletor<FluxoVO> seletor) {
        return null;
    }

    @Override
    public FluxoVO consultarPorId(int id) {
		String qry = " SELECT * FROM FLUXO WHERE IDFLUXO = ? ";
        FluxoVO fluxo = null;
        ResultSet result = null;
        PreparedStatement stmt = null;
        Connection conn = Banco.getConnection();

        try {
            stmt = conn.prepareStatement(qry);
			stmt.setInt(1, id);
            result = stmt.executeQuery(qry);

            while (result.next()) {
                fluxo = criarResultSet(result);
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
            Banco.closeConnection(conn);
        }

        return fluxo;
    }

    @Override
    public FluxoVO cadastrar(FluxoVO FluxoVO) {
        String qry = " INSERT INTO FLUXO (idFluxo, idMovimento) VALUES( ?, ? ) ";
        FluxoVO fluxo = null;
        ResultSet result = null;
        Connection conn = Banco.getConnection();
        PreparedStatement stmt =
        Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setInt(1, fluxo.getId());
            stmt.setInt(2, fluxo.getMovimento().getId());

            result = stmt.getGeneratedKeys();
            if (result.next()){
                int id = result.getInt(1);
                fluxo.setId(id);
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
        return fluxo;
    }

    @Override
    public boolean alterar(FluxoVO FluxoVO) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean excluir(int[] id) {
        // TODO Auto-generated method stub
        return false;
    }

}

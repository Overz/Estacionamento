package model.dao.movientos;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.dao.cliente.PlanoDAO;
import model.vo.cliente.PlanoVO;
import model.vo.movimentos.MovimentoVO;
import model.vo.movimentos.TicketVO;

import java.sql.*;
import java.util.ArrayList;

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
        String qry = " SELECT * FROM MOVIMENTO ";
        ResultSet result = null;
        ArrayList<MovimentoVO> lista = new ArrayList<>();
        Connection conn = Banco.getConnection();
        PreparedStatement stmt =
                Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);


        Connection conexao = Banco.getConnection();
        String sql = " SELECT * FROM ENDERECO";
        ResultSet resultadoDaConsulta = null;
        PreparedStatement stmt = Banco.getPreparedStatement(conexao, sql);

        try {
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
    public ArrayList<?> consultar(MovimentoVO seletor) {

        Connection conn = Banco.getConnection();
        PreparedStatement stmt = Banco.getPreparedStatement(conn);
        ResultSet result = null;
        MovimentoVO movimento = null;
        ArrayList<MovimentoVO> lista = new ArrayList<>();

        String qry = " SELECT * MOVIMENTO ";

        try {
            result = stmt.executeQuery(qry);
//            if (seletor.temFiltro(movimento)){
//                qry += (seletor.criarFiltro(qry, movimento));
//
//            }
            while (result.next()) {
                movimento = criarResultSet(result);
                lista.add(movimento);
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
        return null;
    }

    @Override
    public MovimentoVO consultarPorId(int id) {
        String qry = " SELECT * FROM MOVIMENTO WHERE IDMOVIMENTO = ? ";
        ResultSet result = null;
        MovimentoVO movimento = null;
        PreparedStatement stmt = null;
        Connection conn = Banco.getConnection();

        try {
            stmt = conn.prepareStatement(qry);
            stmt.setInt(1, id);
            result = stmt.executeQuery();

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
    public MovimentoVO cadastrar(MovimentoVO newObject) {
        String qry = " INSERT INTO MOVIMENTO (HR_ENTRADA, HR_SAIDA) VALUES (?,?) ";
        ResultSet result = null;
        MovimentoVO movimento = null;
        Connection conn = Banco.getConnection();
        PreparedStatement stmt =
                Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setTimestamp(1, Timestamp.valueOf(movimento.getHr_entrada()));
            stmt.setTimestamp(2, Timestamp.valueOf(movimento.getHr_saida()));

            result = stmt.getGeneratedKeys();
            if (result.next()) {
                int id = result.getInt(1);
                movimento.setId(id);
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

        return null;
    }

    @Override
    public boolean alterar(MovimentoVO MovimentoVO) {
        String qry = " UPDATE MOVIMENTO M SET M.HR_ENTRADA = ?, M.HR_SAIDA = ? WHERE M.ID = ? ";
        ResultSet result = null;
        MovimentoVO movimento = null;
        Connection conn = Banco.getConnection();
        PreparedStatement stmt =
                Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setTimestamp(1, Timestamp.valueOf(movimento.getHr_entrada()));
            stmt.setTimestamp(2, Timestamp.valueOf(movimento.getHr_saida()));
            stmt.setInt(3, movimento.getId());

            result = stmt.getGeneratedKeys();
            if (result.next()) {
                int id = result.getInt(1);
                movimento.setId(id);
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


        return false;
    }

    @Override
    public boolean excluir(int[] id) {
        String qry = "DELETE FROM MOVIMENTO WHERE IDMOVIMENTO IN (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = Banco.getConnection();
        PreparedStatement prepStmt = Banco.getPreparedStatement(conn, qry);

        try {

            int codigoRetorno = prepStmt.executeUpdate();
            while (codigoRetorno == Banco.CODIGO_RETORNO_SUCESSO_EXCLUSAO) {
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

package model.dao.cliente;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.vo.cliente.ContratoVO;

import java.sql.*;
import java.util.ArrayList;

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
    public ArrayList<?> consultar(ContratoVO seletor) {
        return null;
    }

    @Override
    public ContratoVO consultarPorId(int id) {
        String qry = " SELECT * FROM CONTRATO WHERE IDCONTRATO = ? ";
        ContratoVO contrato = null;
        ResultSet result = null;
        Connection conn = Banco.getConnection();
        PreparedStatement stmt =
                Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt = conn.prepareStatement(qry);
            stmt.setInt(1, id);
            result = stmt.executeQuery();

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
    public ContratoVO cadastrar(ContratoVO newContrato) {
        String qry = " INSERT INTO CONTRATO (N_CARTAO, DT_ENTRADA, DT_SAIDA, ATIVO, VALOR) VALUES (?,?,?,?,?)";
        ResultSet resultSet = null;
        Connection conn = Banco.getConnection();
        PreparedStatement stmt =
                Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);


        try {
            stmt.setLong(1, newContrato.getNumeroCartao());
            stmt.setTimestamp(2, Timestamp.valueOf(newContrato.getDtEntrada()));
            stmt.setTimestamp(3, Timestamp.valueOf(newContrato.getDtSaida()));
            stmt.setBoolean(4, newContrato.isAtivo());
            stmt.setDouble(5, newContrato.getValor());

            resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                newContrato.setId(id);
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
        return newContrato;
    }

    @Override
    public boolean alterar(ContratoVO contratoVO) {
        String qry = " UPDATE CONTRATO C SET C.N_CARTAO = ?, C.DT_ENTRADA = ?, C.DT_SAIDA = ?, C.ATIVO = ?, C.VALOR = ?, WHERE C.IDCONTRATO = ? ";
        ResultSet result = null;
        Connection conn = Banco.getConnection();
        PreparedStatement stmt =
                Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setLong(1, contratoVO.getNumeroCartao());
            stmt.setTimestamp(2, Timestamp.valueOf(contratoVO.getDtEntrada()));
            stmt.setTimestamp(3, Timestamp.valueOf(contratoVO.getDtSaida()));
            stmt.setBoolean(4, contratoVO.isAtivo());
            stmt.setDouble(5, contratoVO.getValor());
            stmt.setInt(6, contratoVO.getId());

            result = stmt.getGeneratedKeys();
            if (result.next()) {
                int id = result.getInt(1);
                contratoVO.setId(id);
            }
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean excluir(int[] id) {
        // TODO Auto-generated method stub
        return false;
    }

}

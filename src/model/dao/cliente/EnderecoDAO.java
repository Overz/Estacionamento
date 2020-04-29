package model.dao.cliente;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.vo.cliente.EnderecoVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EnderecoDAO implements BaseDAO<EnderecoVO> {

    public EnderecoVO criarResultSet(ResultSet result) {

        EnderecoVO endereco = null;

        try {
            endereco = new EnderecoVO();

            endereco.setId(result.getInt("idendereco"));
            endereco.setNumero(result.getInt("numero"));
            endereco.setRua(result.getString("rua"));
            endereco.setBairro(result.getString("bairro"));
            endereco.setCidade(result.getString("cidade"));

        } catch (SQLException e) {
            System.out.println();
            System.out.println("/****************************************************************/");
            System.out.println(this.getClass());
            System.out.println("Method: cirarResultSet()");
            System.out.println("SQL Message:" + e.getMessage());
            System.out.println("SQL Cause:" + e.getCause());
            System.out.println("SQL State:" + e.getSQLState());
            System.out.println("/****************************************************************/");
            System.out.println();
        }
        return endereco;
    }

    @Override
    public ArrayList<EnderecoVO> consultarTodos() {

        String qry = " SELECT * FROM ENDERECO ";
        ArrayList<EnderecoVO> lista = new ArrayList<EnderecoVO>();

        Connection conn = Banco.getConnection();
        PreparedStatement stmt = Banco.getPreparedStatement(conn, qry);
        ResultSet result = null;

        try {
            result = stmt.executeQuery(qry);
            while (result.next()) {
                EnderecoVO e = criarResultSet(result);
                lista.add(e);
            }
        } catch (SQLException e) {
            System.out.println();
            System.out.println("/****************************************************************/");
            System.out.println(this.getClass());
            System.out.println("Method: consultarTodos()");
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
        return lista;
    }

    @Override
    public ArrayList<?> consultar(EnderecoVO seletor) {
        return null;
    }

    @Override
    public EnderecoVO consultarPorId(int id) {
        String qry = " SELECT * FROM ENDERECO WHERE IDENDERECO = ? ";
        EnderecoVO endereco = null;
        ResultSet result = null;
        Connection conn = Banco.getConnection();
        PreparedStatement stmt =
                Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt = conn.prepareStatement(qry);
            stmt.setInt(1, id);
            result = stmt.executeQuery();

            while (result.next()) {
                endereco = criarResultSet(result);
            }
        }
        catch (SQLException e) {
            System.out.println();
            System.out.println("/****************************************************************/");
            System.out.println(this.getClass());
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
        return endereco;
    }

    @Override
    public EnderecoVO cadastrar(EnderecoVO newObject) {
        String qry = " INSERT INTO ENDERECO (NUMERO, RUA, BAIRRO, CIDADE) VALUES (?,?,?,?)";
        EnderecoVO endereco = null;
        ResultSet result = null;
        Connection conn = Banco.getConnection();
        PreparedStatement stmt =
                Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setInt(1, endereco.getNumero());
            stmt.setString(2, endereco.getRua());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getCidade());

            result = stmt.getGeneratedKeys();
            if (result.next()) {
                int id = result.getInt(1);
                endereco.setId(id);
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
        return endereco;
    }

    @Override
    public boolean alterar(EnderecoVO entidade) {
        String qry = " UPDATE ENDERECO E SET E.NUMERO = ?, E.RUA = ?, E.BAIRRO = ?, E.CIDADE = ? WHERE E.IDENDERECO = ? ";
        EnderecoVO endereco = null;
        ResultSet result = null;
        PreparedStatement stmt = null;
        Connection conn = Banco.getConnection();

        try {
            stmt.setInt(1, endereco.getNumero());
            stmt.setString(2, endereco.getRua());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getCidade());
            stmt.setInt(5, endereco.getId());

            result = stmt.getGeneratedKeys();
            if (result.next()) {
                int id = result.getInt(1);
                endereco.setId(id);
            }

            stmt.execute(qry);
            return true;

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
        // TODO Auto-generated method stub
        return false;
    }

}

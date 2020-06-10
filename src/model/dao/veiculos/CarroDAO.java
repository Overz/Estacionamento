package model.dao.veiculos;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.vo.veiculo.CarroVO;
import model.vo.veiculo.ModeloVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarroDAO implements BaseDAO<CarroVO> {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet result = null;
    private ArrayList<CarroVO> list = null;
    private CarroVO carroVO = null;

    public CarroVO criarResultSet(ResultSet result) {
        carroVO = new CarroVO();

        try {
            carroVO.setId(result.getInt("id"));

            int idModelo = result.getInt("idmodelo");
            ModeloDAO modeloDAO = new ModeloDAO();
            ModeloVO modelo = modeloDAO.consultarPorId(idModelo);

            carroVO.setModelo(modelo);
            carroVO.setPlaca(result.getString("placa"));
            carroVO.setCor(result.getString("cor"));

            return carroVO;
        } catch (SQLException e) {
            String method = "CriarResultSet(ResultSet result)";
            System.out.println("\n" +
                               "Class: " + getClass().getSimpleName() + "\n" +
                               "Method: " + method + "\n" +
                               "Msg: " + e.getMessage() + "\n" +
                               "Cause: " + e.getCause()
            );

        }
        return null;
    } // OK

    @Override
    public ArrayList<CarroVO> consultarTodos() {
        String qry = "select * from carro;";
        list = new ArrayList<>();
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            result = stmt.executeQuery();
            while (result.next()) {
                carroVO = criarResultSet(result);
                list.add(carroVO);
            }
            return list;
        } catch (SQLException e) {
            String method = "ConsultarTodos()";
            System.out.println("\n" +
                               "Class: " + getClass().getSimpleName() + "\n" +
                               "Method: " + method + "\n" +
                               "Msg: " + e.getMessage() + "\n" +
                               "Cause: " + e.getCause()
            );
        } finally {
            Banco.closeResultSet(result);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return null;
    } // OK

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<CarroVO> consultar(String... values) {
        return null;
    }


    @Override
    public CarroVO consultarPorId(int id) {
        String qry = "select * from carro where id=?;";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setInt(1, id);
            result = stmt.executeQuery();
            while (result.next()) {
                carroVO = criarResultSet(result);
            }
            return carroVO;
        } catch (SQLException e) {
            String method = "ConsultarPorID(int id)";
            System.out.println("\n" +
                               "Class: " + getClass().getSimpleName() + "\n" +
                               "Method: " + method + "\n" +
                               "Msg: " + e.getMessage() + "\n" +
                               "Cause: " + e.getCause()
            );
        } finally {
            Banco.closeResultSet(result);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return null;
    } // OK

    @Override
    public CarroVO cadastrar(CarroVO newObject, String... values) {
        String qry = "insert into carro (placa, cor) values (?,?);";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setString(1, newObject.getPlaca());
            stmt.setString(2, newObject.getPlaca());

            result = stmt.getGeneratedKeys();
            while (result.next()) {
                int id = result.getInt(1);
                newObject.setId(id);
            }
            return newObject;
        } catch (SQLException e) {
            String method = "Cadastrar(T newObject)";
            System.out.println("\n" +
                               "Class: " + getClass().getSimpleName() + "\n" +
                               "Method: " + method + "\n" +
                               "Msg: " + e.getMessage() + "\n" +
                               "Cause: " + e.getCause()
            );
        } finally {
            Banco.closeResultSet(result);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return null;
    } // OK

    @Override
    public boolean alterar(CarroVO object) {
        String qry = "update carro set placa=?, cor=?;";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setString(1, object.getPlaca());
            stmt.setString(2, object.getCor());

            if (stmt.executeUpdate() == Banco.CODIGO_RETORNO_SUCESSO) {
                return true;
            }
        } catch (SQLException e) {
            String method = "Alterar(T object)";
            System.out.println("\n" +
                               "Class: " + getClass().getSimpleName() + "\n" +
                               "Method: " + method + "\n" +
                               "Msg: " + e.getMessage() + "\n" +
                               "Cause: " + e.getCause()
            );
        } finally {
            Banco.closeResultSet(result);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return false;
    } // OK

    @Override
    public boolean excluirPorID(int id) {
        String qry = "delete from carro where id=?;";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setInt(1, id);
            if (stmt.executeUpdate() == Banco.CODIGO_RETORNO_SUCESSO) {
                return true;
            }
        } catch (SQLException e) {
            String method = "excluir(int id)";
            System.out.println("\n" +
                               "Class: " + getClass().getSimpleName() + "\n" +
                               "Method: " + method + "\n" +
                               "Msg: " + e.getMessage() + "\n" +
                               "Cause: " + e.getCause()
            );
        } finally {
            Banco.closeResultSet(result);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return false;
    } // OK
}

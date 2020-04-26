package model.dao.veiculos;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.seletor.SuperSeletor;
import model.vo.veiculo.CarroVO;
import model.vo.veiculo.ModeloVO;

import java.sql.*;
import java.util.ArrayList;

public class CarroDAO implements BaseDAO<CarroVO> {

    public CarroVO criarResultSet(ResultSet result) {
        CarroVO carro = null;

        try {
            carro = new CarroVO();
            carro.setId(result.getInt("idcarro"));

            int idModelo = result.getInt("idmodelo");
            ModeloDAO modeloDAO = new ModeloDAO();
            ModeloVO modelo = modeloDAO.consultarPorId(idModelo);

//			int idCliente = result.getInt("idcliente");
//			ClienteDAO clienteDAO = new ClienteDAO();
//			ClienteVO clienteVO = (ClienteVO) clienteDAO.consultarPorId(idCliente);

            carro.setModelo(modelo);
            carro.setPlaca(result.getString("placa"));
            carro.setCor(result.getString("cor"));

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
        return carro;
    }

    @Override
    public ArrayList<CarroVO> consultarTodos() {
        Connection conn = Banco.getConnection();
        Statement stmt = Banco.getStatement(conn);
        ResultSet result = null;

        ArrayList<CarroVO> lista = new ArrayList<CarroVO>();
        String qry = " SELECT * FROM MODELO ";

        try {
            result = stmt.executeQuery(qry);
            while (result.next()) {
                CarroVO vo = criarResultSet(result);
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
    public ArrayList<?> consultar(SuperSeletor<CarroVO> seletor) {
        return null;
    }

    @Override
    public CarroVO consultarPorId(int id) {
        String qry = " SELECT * FROM CARRO WHERE IDCARRO = ? ";
        CarroVO carro = null;
        ResultSet result = null;
        PreparedStatement stmt = null;
        Connection conn = Banco.getConnection();

        try {
            stmt = conn.prepareStatement(qry);
            stmt.setInt(1, id);
            result = stmt.executeQuery();

            while (result.next()) {
                carro = criarResultSet(result);
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

        return carro;
    }

    @Override
    public CarroVO cadastrar(CarroVO CarroVO) {
        String qry = " INSERT INTO CARRO (PLACA, COR) VALUES (?,?) ";
        CarroVO carro = null;
        ResultSet resultSet = null;
        Connection conn = Banco.getConnection();
        PreparedStatement stmt =
                Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setString(1, carro.getPlaca());
            stmt.setString(2, carro.getCor());

            resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
            	int id = resultSet.getInt(1);
            	carro.setId(id);
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
        return carro;
    }

    @Override
    public boolean alterar(CarroVO obj) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean excluir(int[] id) {
        // TODO Auto-generated method stub
        return false;
    }

}

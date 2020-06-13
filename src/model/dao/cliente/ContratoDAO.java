package model.dao.cliente;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.seletor.SeletorCliente;
import model.vo.cliente.ClienteVO;
import model.vo.cliente.ContratoVO;
import model.vo.cliente.PlanoVO;
import util.constantes.ConstHelpers;

import java.sql.*;
import java.util.ArrayList;

public class ContratoDAO implements BaseDAO<ContratoVO> {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet result = null;
    private ArrayList<ContratoVO> list = null;
    private ContratoVO contratoVO = null;

    public ContratoVO criarResultSet(ResultSet result) {
        contratoVO = new ContratoVO();
        try {
            contratoVO.setId(result.getInt("id"));
            contratoVO.setNumeroCartao(result.getLong("n_cartao"));
            contratoVO.setDtEntrada(result.getTimestamp("dt_entrada").toLocalDateTime());
            Timestamp dtValidade = result.getTimestamp("dt_validade");
            if (dtValidade != null) {
                contratoVO.setDtSaida(dtValidade.toLocalDateTime());
            }
            contratoVO.setValor(result.getDouble("valor"));
            contratoVO.setAtivo(result.getBoolean("ativo"));
            contratoVO.setTipoPgto(result.getString("tipoPgto"));

            int idP = result.getInt("idPlano");
            PlanoDAO planoDAO = new PlanoDAO();
            PlanoVO planoVO = planoDAO.consultarPorId(idP);
            contratoVO.setPlano(planoVO);

            int idC = result.getInt("idCliente");
            ClienteDAO clienteDAO = new ClienteDAO();
            ClienteVO clienteVO = clienteDAO.consultarPorId(idC);
            contratoVO.setCliente(clienteVO);

            return contratoVO;
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
    public ArrayList<ContratoVO> consultarTodos() {
        String qry = "select * from contrato;";
        list = new ArrayList<>();
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            result = stmt.executeQuery();
            while (result.next()) {
                contratoVO = criarResultSet(result);
                list.add(contratoVO);
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

    @Override
    public <T> T consultar(String... values) {
        String qry = " select * from contrato con " +
                     " left join plano p on con.idPlano = p.id " +
                     " left join cliente cli on con.idCliente = cli.id " +
                     " left join carro car on cli.idCarro = car.id " +
                     " left join modelo mdl on car.idModelo = mdl.id " +
                     " left join marca mar on mdl.idMarca = mar.id ";

        SeletorCliente seletor = new SeletorCliente();
        seletor.setValor(values[0]);

        if (seletor.temFiltro()) {
            qry = seletor.criarFiltro(qry);
        }

        list = new ArrayList<>();
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            result = stmt.executeQuery();
            while (result.next()) {
                contratoVO = criarResultSet(result);
                list.add(contratoVO);
            }
            return (T) list;
        } catch (SQLException e) {
            String method = "Consultar()";
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
    }

    @Override
    public ContratoVO consultarPorId(int id) {
        String qry = "select * from contrato where id=?;";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setInt(1, id);
            result = stmt.executeQuery();
            while (result.next()) {
                contratoVO = criarResultSet(result);
            }
            return contratoVO;
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
    public ContratoVO cadastrar(ContratoVO newObject, String... values) {
        String qry = "insert into contrato (n_cartao, dt_entrada, dt_saida, ativo, valor) values (?,?,?,?,?);";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);
        try {
            stmt.setLong(1, newObject.getNumeroCartao());
            stmt.setTimestamp(2, Timestamp.valueOf(newObject.getDtEntrada()));
            stmt.setTimestamp(3, Timestamp.valueOf(newObject.getDtSaida()));
            stmt.setBoolean(4, newObject.isAtivo());
            stmt.setDouble(5, newObject.getValor());

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
    public boolean alterar(ContratoVO object) {
        String qry = "update contrato set n_cartao=?, dt_entrada=?, dt_saida=?, ativo=?, valor=? where id=?;";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setLong(1, object.getNumeroCartao());
            stmt.setTimestamp(2, Timestamp.valueOf(object.getDtEntrada()));
            stmt.setTimestamp(3, Timestamp.valueOf(object.getDtSaida()));
            stmt.setBoolean(4, object.isAtivo());
            stmt.setDouble(5, object.getValor());

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
        String qry = "delete from contrato where id=?;";
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

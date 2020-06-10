package model.dao.movientos;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.dao.cliente.PlanoDAO;
import model.seletor.Seletor;
import model.vo.cliente.PlanoVO;
import model.vo.movimentos.MovimentoVO;
import model.vo.movimentos.TicketVO;
import util.constantes.ConstHelpers;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MovimentoDAO implements BaseDAO<MovimentoVO> {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet result = null;
    private ArrayList<MovimentoVO> list = null;
    private MovimentoVO movimentoVO = null;

    public MovimentoVO criarResultSet(ResultSet result) {
        movimentoVO = new MovimentoVO();

        try {
            movimentoVO.setId(result.getInt("id"));

            int idT = result.getInt("idticket");
            TicketDAO ticketDAO = new TicketDAO();
            TicketVO ticketVO = ticketDAO.consultarPorId(idT);
            movimentoVO.setTicket(ticketVO);

            int idP = result.getInt("idplano");
            PlanoDAO planoDAO = new PlanoDAO();
            PlanoVO planoVO = planoDAO.consultarPorId(idP);
            movimentoVO.setPlano(planoVO);

            movimentoVO.setHr_entrada(result.getTimestamp("hr_entrada").toLocalDateTime());
            movimentoVO.setAtual(result.getBoolean("atual"));

            return movimentoVO;
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
    public ArrayList<MovimentoVO> consultarTodos() {
        String qry = "select * from movimento where atual=1 order by hr_entrada;";

        list = new ArrayList<>();
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            result = stmt.executeQuery();
            while (result.next()) {
                movimentoVO = criarResultSet(result);
                list.add(movimentoVO);
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
            e.printStackTrace();
        } finally {
            Banco.closeResultSet(result);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return null;
    } // OK

    @Override
    public ArrayList<MovimentoVO> consultar(String... values) {
        String qry = "";
        Seletor seletor = new Seletor();
        if (ConstHelpers.FLAG == 0) {
            qry = "select * from movimento movi " +
                  "left join plano p on movi.idPlano = p.id " +
                  "left join ticket t on movi.idTicket = t.id " +
                  "left join contrato con on p.idContrato = con.id " +
                  "left join cliente cli on p.idCliente = cli.id " +
                  "left join carro car on cli.idCarro = car.id " +
                  "left join modelo modl on car.idModelo = modl.id";

            seletor.setValor(values[0].toUpperCase());
            if (seletor.temFiltro()) {
                qry = seletor.criarFiltro(qry);
            }
        } else if (ConstHelpers.FLAG == 1) {
            qry = "select * from movimento movi inner join ticket t on movi.idTicket = t.id " +
                  "where t.n_ticket like '%" + values[0] + "%' ";
        } else if (ConstHelpers.FLAG == 2) {
            qry = "select * from movimento ";

            LocalDate ld1 = LocalDate.parse(values[0]);
            LocalDate ld2 = LocalDate.parse(values[1]);
            seletor.setDt1(ld1);
            seletor.setDt2(ld2);

            if (seletor.temData()) {
                qry = seletor.criarFiltro(qry);
            }
        }

        list = new ArrayList<>();
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            result = stmt.executeQuery();
            while (result.next()) {
                movimentoVO = criarResultSet(result);
                list.add(movimentoVO);
            }
            return list;
        } catch (SQLException e) {
            String method = "Consultar(String values)";
            System.out.println("\n" +
                               "Class: " + getClass().getSimpleName() + "\n" +
                               "Method: " + method + "\n" +
                               "Msg: " + e.getMessage() + "\n" +
                               "Cause: " + e.getCause() + "\n" +
                               "Query:" + qry
            );
        } finally {
            Banco.closeResultSet(result);
            Banco.closePreparedStatement(stmt);
            Banco.closeConnection(conn);
        }
        return null;
    }

    @Override
    public MovimentoVO consultarPorId(int id) {
        String qry = "";
        if (ConstHelpers.FLAG == 0) {
            qry = "select * from movimento where id=?;";
        } else if (ConstHelpers.FLAG == 1) {
            qry = "select * from movimento where idticket=?;";
        } else if (ConstHelpers.FLAG == 2) {
            qry = "select * from movimento wheree idplano=?;";
        }

        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setInt(1, id);
            result = stmt.executeQuery();
            while (result.next()) {
                movimentoVO = criarResultSet(result);
            }
            return movimentoVO;
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
    public MovimentoVO cadastrar(MovimentoVO newObject, String... values) {
        String qry;
        if (ConstHelpers.FLAG == 0) {
            qry = "insert into movimento (hr_entrada, hr_saida, atual) values (?,?,?);";
        } else {
            qry = "insert into movimento (idticket, hr_entrada, atual) values (?,?,?);";
        }
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            if (ConstHelpers.FLAG == 0) {
                stmt.setTimestamp(1, Timestamp.valueOf(newObject.getHr_entrada()));
                stmt.setTimestamp(2, Timestamp.valueOf(newObject.getHr_saida()));
                stmt.setBoolean(3, newObject.isAtual());
            } else {
                stmt.setInt(1, newObject.getId());
                stmt.setTimestamp(2, Timestamp.valueOf(newObject.getHr_entrada()));
                stmt.setBoolean(3, newObject.isAtual());
            }

            stmt.execute();
            result = stmt.getGeneratedKeys();
            if (result.next()) {
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
    public boolean alterar(MovimentoVO object) {
        String qry = "update movimento set hr_entrada=?, hr_saida=?;";
        conn = Banco.getConnection();
        stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stmt.setTimestamp(1, Timestamp.valueOf(object.getHr_entrada()));
            stmt.setTimestamp(2, Timestamp.valueOf(object.getHr_saida()));

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
        String qry = "delete from movimento where id=?;";
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

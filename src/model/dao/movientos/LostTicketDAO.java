package model.dao.movientos;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.vo.movimentos.LostTicketVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LostTicketDAO implements BaseDAO<LostTicketVO> {
    @Override
    public ArrayList<LostTicketVO> consultarTodos() {
        return null;
    }

    @Override
    public <T> T consultar(String... values) {
        return null;
    }

    @Override
    public LostTicketVO consultarPorId(int id) {
        return null;
    }

    @Override
    public LostTicketVO cadastrar(LostTicketVO newObject, String... values) {
        String qry = "insert into ticketlost (nome, cpf, placa, renavam, tipoPgo, documento) " +
                     " values (?,?,?,?,?,?);";
        Connection conn = Banco.getConnection();
        PreparedStatement stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);
        ResultSet result = null;

        try {
            stmt.setString(1, newObject.getNome().toUpperCase());
            stmt.setString(2, newObject.getCPF().toUpperCase());
            stmt.setString(3, newObject.getPlaca().toUpperCase());
            stmt.setString(4, newObject.getRenavam().toUpperCase());
            stmt.setString(5, newObject.getFormaPgto().toUpperCase());
            stmt.setBytes(6, newObject.getDocumento());

            result = stmt.getGeneratedKeys();
            if (result != null && result.next()) {
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
    }

    @Override
    public boolean alterar(LostTicketVO object) {
        return false;
    }

    @Override
    public boolean excluirPorID(int id) {
        return false;
    }
}

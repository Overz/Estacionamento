package model.dao.cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import model.banco.Banco;
import model.banco.BaseDAO;
import model.seletor.SeletorCliente;
import model.vo.cliente.ClienteVO;
import model.vo.cliente.ContratoVO;
import model.vo.cliente.PlanoVO;
import util.constantes.ConstHelpers;

public class ContratoDAO implements BaseDAO<ContratoVO> {

	int resultado = 0;
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
			System.out.println("\n" + "Class: " + getClass().getSimpleName() + "\n" + "Method: " + method + "\n"
					+ "Msg: " + e.getMessage() + "\n" + "Cause: " + e.getCause());
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
			System.out.println("\n" + "Class: " + getClass().getSimpleName() + "\n" + "Method: " + method + "\n"
					+ "Msg: " + e.getMessage() + "\n" + "Cause: " + e.getCause());

		} finally {
			Banco.closeResultSet(result);
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}
		return null;
	} // OK

	@Override
	public <T> T consultar(String... values) {
		String qry = " select * from contrato con " + " left join plano p on con.idPlano = p.id "
				+ " left join cliente cli on con.idCliente = cli.id " + " left join carro car on cli.idCarro = car.id "
				+ " left join modelo mdl on car.idModelo = mdl.id " + " left join marca mar on mdl.idMarca = mar.id ";

		if (ConstHelpers.FLAG == 1) {
			qry = "select * from contrato where n_cartao = ?;";

		}

		if (ConstHelpers.FLAG == 2) {
			qry = "select * from contrato con " + "left join plano p on con.idPlano = p.id "
					+ "left join cliente cli on con.idCliente = cli.id "
					+ "left join carro car on cli.idCarro = car.id " + "left join modelo mdl on car.idModelo = mdl.id "
					+ "left join marca mar on mdl.idMarca = mar.id " + "where car.placa = ?";
		} else {
			SeletorCliente seletor = new SeletorCliente();
			seletor.setValor(values[0]);

			if (seletor.temFiltro()) {
				qry = seletor.criarFiltro(qry);
			}
		}

		list = new ArrayList<>();
		conn = Banco.getConnection();
		stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

		try {
			if (ConstHelpers.FLAG == 1) {
				stmt.setLong(1, Long.parseLong(values[0]));
			}
			if (ConstHelpers.FLAG == 2) {
				stmt.setString(1, values[0]);
			}

			result = stmt.executeQuery();
			while (result != null && result.next()) {
				contratoVO = criarResultSet(result);
				if (ConstHelpers.FLAG == 1 || ConstHelpers.FLAG == 2) {
					return (T) contratoVO;
				}
				list.add(contratoVO);
			}
			if (list.size() == 0) {
				return null;
			} else {
				return (T) list;
			}
		} catch (SQLException e) {
			String method = "Consultar()";
			System.out.println("\n" + "Class: " + getClass().getSimpleName() + "\n" + "Method: " + method + "\n"
					+ "Msg: " + e.getMessage() + "\n" + "Cause: " + e.getCause());
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
			System.out.println("\n" + "Class: " + getClass().getSimpleName() + "\n" + "Method: " + method + "\n"
					+ "Msg: " + e.getMessage() + "\n" + "Cause: " + e.getCause());
		} finally {
			Banco.closeResultSet(result);
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}
		return null;
	} // OK

	@Override
	public ContratoVO cadastrar(ContratoVO newObject, String... values) {
		String qry = "insert into contrato (idPLano, idCliente, n_cartao, dt_validade, ativo, valor, tipoPgto)"
				+ " values (?,?,?,?,?,?,?);";
		conn = Banco.getConnection();
		stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);
		try {
			stmt.setLong(1, newObject.getPlano().getId());
			stmt.setLong(2, newObject.getCliente().getId());
			stmt.setLong(3, newObject.getNumeroCartao());
			stmt.setTimestamp(4, Timestamp.valueOf(newObject.getDtSaida()));
			stmt.setBoolean(5, newObject.isAtivo());
			stmt.setDouble(6, newObject.getValor());
			stmt.setString(7, newObject.getTipoPgto().toUpperCase());

			int i = stmt.executeUpdate();
			result = stmt.getGeneratedKeys();
			if (result != null && result.next()) {
				if (i == Banco.CODIGO_RETORNO_SUCESSO) {
					int id = result.getInt(1);
					newObject.setId(id);
				}
			}
			return newObject;
		} catch (SQLException e) {
			String method = "Cadastrar(T newObject)";
			System.out.println("\n" + "Class: " + getClass().getSimpleName() + "\n" + "Method: " + method + "\n"
					+ "Msg: " + e.getMessage() + "\n" + "Cause: " + e.getCause());

		} finally {
			Banco.closeResultSet(result);
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}
		return null;
	} // OK

	@Override
	public boolean alterar(ContratoVO object) {
		String qry = "update contrato set n_cartao=?, dt_entrada=?, dt_validade =?, ativo=?, valor=?, tipoPgto=? where id=?;";
		conn = Banco.getConnection();
		stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

		try {
			stmt.setLong(1, object.getNumeroCartao());
			stmt.setTimestamp(2, Timestamp.valueOf(object.getDtEntrada()));
			stmt.setTimestamp(3, Timestamp.valueOf(object.getDtSaida()));
			stmt.setBoolean(4, object.isAtivo());
			stmt.setDouble(5, object.getValor());
			stmt.setString(6, object.getTipoPgto());
			stmt.setInt(7, object.getId());

			int i = stmt.executeUpdate();
			if (i == Banco.CODIGO_RETORNO_SUCESSO) {
				return true;
			}
		} catch (SQLException e) {
			String method = "Alterar(T object)";
			System.out.println("\n" + "Class: " + getClass().getSimpleName() + "\n" + "Method: " + method + "\n"
					+ "Msg: " + e.getMessage() + "\n" + "Cause: " + e.getCause());
		} finally {
			Banco.closeResultSet(result);
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}
		return false;
	} // OK

	@Override
	public boolean excluirPorID(int id) {
		String qry;
		if (ConstHelpers.FLAG == 1) {
			qry = "delete con, cli, car, e " + "from contrato con inner join cliente cli on con.idCliente = cli.id "
					+ "inner join carro car on cli.idCarro = car.id "
					+ "inner join endereco e on cli.idEndereco = e.id " + "where e.id = cli.id and car.id = cli.id "
					+ "and cli.id = con.id and con.id = ?;";
		} else if (ConstHelpers.FLAG == 2) {
			qry = "delete movi, con, cli, car, e "
					+ "from movimento movi inner join contrato con on movi.idContrato = con.id "
					+ "inner join cliente cli on con.idCliente = cli.id "
					+ "inner join carro car on cli.idCarro = car.id "
					+ "inner join endereco e on cli.idEndereco = e.id " + "where e.id = cli.id and car.id = cli.id "
					+ "and cli.id = con.id and con.id = movi.id " + "and movi.id = ?;";
		} else {
			qry = "delete from contrato where id=?";
		}

		conn = Banco.getConnection();
		stmt = Banco.getPreparedStatement(conn, qry, PreparedStatement.RETURN_GENERATED_KEYS);

		try {
			stmt.setInt(1, id);

			resultado = stmt.executeUpdate();
			if (resultado == Banco.CODIGO_RETORNO_SUCESSO) {
				return true;
			} else if (resultado == 4 || resultado == 5) {
				return true;
			}
		} catch (SQLException e) {
			String method = "excluir(int id)";
			System.out.println("\n" + "Class: " + getClass().getSimpleName() + "\n" + "Method: " + method + "\n"
					+ "Msg: " + e.getMessage() + "\n" + "Cause: " + e.getCause() + "\n");
		} finally {
			Banco.closeResultSet(result);
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}
		return false;
	} // OK
}

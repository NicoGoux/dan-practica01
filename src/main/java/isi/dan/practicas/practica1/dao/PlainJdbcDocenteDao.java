package isi.dan.practicas.practica1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import isi.dan.practicas.practica1.model.Docente;

public class PlainJdbcDocenteDao implements DocenteDao {
	private static final String INSERT_SQL = "INSERT INTO DOCENTE (nombre, salario) VALUES (?, ?);";
	private static final String UPDATE_SQL = "UPDATE DOCENTE SET nombre=?, salario=? WHERE ID=?";
	private static final String SELECT_ALL_SQL = "SELECT * FROM DOCENTE";
	private static final String SELECT_ONE_SQL = "SELECT * FROM DOCENTE WHERE ID=?";
	private static final String DELETE_SQL = "DELETE FROM DOCENTE WHERE ID=?";

	private final DataSource dataSource;

	public PlainJdbcDocenteDao(DataSource ds) {
		this.dataSource = ds;
	}

	@Override
	public Docente insert(Docente nuevo) {
		try {

			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
			prepareStatement(ps, nuevo);
			int success = ps.executeUpdate();
			if (success == 1) {
				try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int idGenerado = generatedKeys.getInt(1);
						nuevo.setId(idGenerado);
						return nuevo;
					}
				}
			}
			return null;
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("Error al insertar el docente en la base de datos", e);
		}
	}

	@Override
	public List<Docente> insert(Iterable<Docente> nuevos) {
		List<Docente> docentesInsertados = new ArrayList<>();
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
			for (Docente docente : nuevos) {
				prepareStatement(ps, docente);
				int success = ps.executeUpdate();
				if (success == 1) {
					try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
						if (generatedKeys.next()) {
							int idGenerado = generatedKeys.getInt(1);
							docente.setId(idGenerado);
							docentesInsertados.add(docente);
						}
					}
				}
			}
			return docentesInsertados;
		}
		catch (SQLException e) {
			throw new RuntimeException("Error al insertar docentes en la base de datos", e);
		}
	}

	@Override
	public Docente update(Docente actualizar) {
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(UPDATE_SQL);
			prepareStatement(ps, actualizar);
			int success = ps.executeUpdate();
			if (success == 1) {
				return actualizar;
			}
			else {
				return null;
			}
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Boolean delete(Integer id) {
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(DELETE_SQL);
			ps.setInt(1, id);
			int success = ps.executeUpdate();

			return (success != 0);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Docente findById(Integer id) {
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(SELECT_ONE_SQL);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Docente docente = toDocente(rs);
				return docente;
			}
			return null;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Docente> findAll() {
		List<Docente> listaDocentes = new ArrayList<>(0);
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Docente docente = toDocente(rs);
				listaDocentes.add(docente);
			}
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return listaDocentes;
	}

	private Docente toDocente(ResultSet rs) throws SQLException {
		return new Docente(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("salario"));
	}

	private void prepareStatement(PreparedStatement ps, Docente docente)
			throws SQLException {
		ps.setString(1, docente.getNombre());
		ps.setDouble(2, docente.getSalario());
		if (docente.getId() != null) {
			ps.setInt(3, docente.getId());
		}

	}
}

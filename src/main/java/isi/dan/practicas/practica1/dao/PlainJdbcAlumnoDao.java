package isi.dan.practicas.practica1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import isi.dan.practicas.practica1.model.Alumno;

public class PlainJdbcAlumnoDao implements AlumnoDao {

	private static final String INSERT_SQL = "INSERT INTO ALUMNO (nombre, legajo) VALUES (?, ?);";
	private static final String UPDATE_SQL = "UPDATE ALUMNO SET nombre=?,legajo=? WHERE ID=?";
	private static final String SELECT_ALL_SQL = "SELECT * FROM ALUMNO";
	private static final String SELECT_ONE_SQL = "SELECT * FROM ALUMNO WHERE ID=?";
	private static final String DELETE_SQL = "DELETE FROM ALUMNO WHERE ID=?";

	private final DataSource dataSource;

	public PlainJdbcAlumnoDao(DataSource ds) {
		this.dataSource = ds;
	}

	@Override
	public Alumno insert(Alumno nuevo) {
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
			throw new RuntimeException("Error al insertar el alumno en la base de datos", e);
		}
	}

	@Override
	public List<Alumno> insert(Iterable<Alumno> nuevos) {
		List<Alumno> alumnosInsertados = new ArrayList<>();
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
			for (Alumno alumno : nuevos) {
				prepareStatement(ps, alumno);
				int success = ps.executeUpdate();
				if (success == 1) {
					try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
						if (generatedKeys.next()) {
							int idGenerado = generatedKeys.getInt(1);
							alumno.setId(idGenerado);
							alumnosInsertados.add(alumno);
						}
					}
				}
			}
			return alumnosInsertados;
		}
		catch (SQLException e) {
			throw new RuntimeException("Error al insertar alumnos en la base de datos", e);
		}
	}

	@Override
	public Alumno update(Alumno actualizar) {
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
	public Alumno findById(Integer id) {
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(SELECT_ONE_SQL);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Alumno alumno = toAlumno(rs);
				return alumno;
			}
			return null;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Alumno> findAll() {
		List<Alumno> listaAlumnos = new ArrayList<>(0);
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Alumno alumno = toAlumno(rs);
				listaAlumnos.add(alumno);
			}
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return listaAlumnos;
	}

	private Alumno toAlumno(ResultSet rs) throws SQLException {
		return new Alumno(rs.getInt("id"), rs.getString("nombre"), rs.getInt("legajo"));
	}

	private void prepareStatement(PreparedStatement ps, Alumno alumno)
			throws SQLException {
		ps.setString(1, alumno.getNombre());
		ps.setInt(2, alumno.getLegajo());
		if (alumno.getId() != null) {
			ps.setInt(3, alumno.getId());
		}

	}
}

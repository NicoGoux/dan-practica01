package isi.dan.practicas.practica1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import isi.dan.practicas.practica1.model.Curso;

public class PlainJdbcCursoDao implements CursoDao {
	private static final String INSERT_SQL = "INSERT INTO CURSO (nombre, creditos, cupo, docente_asignado) VALUES (?, ?, ?, ?);";
	private static final String UPDATE_SQL = "UPDATE CURSO SET nombre=?, creditos=?, cupo=?, docente_asignado=? WHERE ID=?";
	private static final String SELECT_ALL_SQL = "SELECT * FROM CURSO";
	private static final String SELECT_ONE_SQL = "SELECT * FROM CURSO WHERE ID=?";
	private static final String DELETE_SQL = "DELETE FROM CURSO WHERE ID=?";

	private final DataSource dataSource;

	@Autowired
	private DocenteDao docenteDao;

	public PlainJdbcCursoDao(DataSource ds) {
		this.dataSource = ds;
	}

	@Override
	public Curso insert(Curso nuevo) {
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
			throw new RuntimeException("Error al insertar el curso en la base de datos", e);
		}
	}

	@Override
	public List<Curso> insert(Iterable<Curso> nuevos) {
		List<Curso> cursosInsertados = new ArrayList<>();
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
			for (Curso curso : nuevos) {
				prepareStatement(ps, curso);
				int success = ps.executeUpdate();
				if (success == 1) {
					try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
						if (generatedKeys.next()) {
							int idGenerado = generatedKeys.getInt(1);
							curso.setId(idGenerado);
							cursosInsertados.add(curso);
						}
					}
				}
			}
			return cursosInsertados;
		}
		catch (SQLException e) {
			throw new RuntimeException("Error al insertar cursos en la base de datos", e);
		}
	}

	@Override
	public Curso update(Curso actualizar) {
		try {
			System.out.println(actualizar.toString());
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
	public Curso findById(Integer id) {
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(SELECT_ONE_SQL);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Curso curso = toCurso(rs);
				return curso;
			}
			return null;
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Curso> findAll() {
		List<Curso> listaCursos = new ArrayList<>(0);
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Curso curso = toCurso(rs);
				listaCursos.add(curso);
			}
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return listaCursos;
	}

	@Override
	public Boolean inscribirACurso(Integer idCurso, Integer idAlumno) {
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO curso_inscripto (id_alumno, id_curso) VALUES (?, ?)");

			ps.setInt(1, idAlumno);
			ps.setInt(2, idCurso);

			int success = ps.executeUpdate();

			return (success != 0);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};

	@Override
	public Boolean desinscribirACurso(Integer idCurso, Integer idAlumno) {
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn
					.prepareStatement(
							"DELETE FROM curso_inscripto WHERE id_alumno=? AND id_curso=?");

			ps.setInt(1, idAlumno);
			ps.setInt(2, idCurso);

			int success = ps.executeUpdate();

			return (success != 0);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private List<Integer> getListaInscriptos(int idCurso) {
		List<Integer> listaInscriptos = new ArrayList<>(0);
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT id_alumno FROM curso_inscripto WHERE id_curso=?");
			ps.setInt(1, idCurso);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				listaInscriptos.add(rs.getInt("id_alumno"));
			}
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return listaInscriptos;
	}

	private Curso toCurso(ResultSet rs) throws SQLException {
		Curso curso = new Curso(
				rs.getInt("id"),
				rs.getString("nombre"),
				rs.getInt("creditos"),
				rs.getInt("cupo"),
				docenteDao.findById(rs.getInt("docente_asignado")),
				this.getListaInscriptos(rs.getInt("id")));
		return curso;
	}

	private void prepareStatement(PreparedStatement ps, Curso curso)
			throws SQLException {
		ps.setString(1, curso.getNombre());
		ps.setInt(2, curso.getCreditos());
		ps.setInt(3, curso.getCupo());

		if (curso.getDocenteAsignado() != null) {
			ps.setInt(4, curso.getDocenteAsignado().getId());
		}
		else {
			ps.setNull(4, Types.INTEGER);
		}
		if (curso.getId() != null) {
			ps.setInt(5, curso.getId());
		}
	}
}

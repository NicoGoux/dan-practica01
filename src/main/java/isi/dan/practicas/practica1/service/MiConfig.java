package isi.dan.practicas.practica1.service;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import isi.dan.practicas.practica1.dao.AlumnoDao;
import isi.dan.practicas.practica1.dao.CursoDao;
import isi.dan.practicas.practica1.dao.DocenteDao;
import isi.dan.practicas.practica1.dao.PlainJdbcAlumnoDao;
import isi.dan.practicas.practica1.dao.PlainJdbcCursoDao;
import isi.dan.practicas.practica1.dao.PlainJdbcDocenteDao;

@Configuration
public class MiConfig {

	@Bean
	public MemoryDB memoryDB() {
		return new MemoryDB(0);
	}

	@Bean
	public AlumnoDao alumnoDao() {
		return new PlainJdbcAlumnoDao(dataSource());
	}

	@Bean
	public DocenteDao docenteDao() {
		return new PlainJdbcDocenteDao(dataSource());
	};

	@Bean
	public CursoDao cursoDao() {
		return new PlainJdbcCursoDao(dataSource());
	};

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/practica1");
		ds.setUsername("root");
		ds.setPassword("");
		return ds;
	}
}

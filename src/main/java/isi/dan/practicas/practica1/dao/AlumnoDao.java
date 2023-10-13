package isi.dan.practicas.practica1.dao;

import java.util.List;

import isi.dan.practicas.practica1.model.Alumno;

public interface AlumnoDao {
	Alumno insert(Alumno nuevo);

	List<Alumno> insert(Iterable<Alumno> nuevos);

	Alumno update(Alumno actualizar);

	Boolean delete(Integer id);

	Alumno findById(Integer id);

	List<Alumno> findAll();
}

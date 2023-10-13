package isi.dan.practicas.practica1.dao;

import java.util.List;

import isi.dan.practicas.practica1.model.Curso;

public interface CursoDao {
	Curso insert(Curso nuevo);

	List<Curso> insert(Iterable<Curso> nuevos);

	Curso update(Curso actualizar);

	Boolean delete(Integer id);

	Curso findById(Integer id);

	List<Curso> findAll();

	Boolean inscribirACurso(Integer idCurso, Integer idAlumno);

	Boolean desinscribirACurso(Integer idCurso, Integer idAlumno);
}
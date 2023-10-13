package isi.dan.practicas.practica1.dao;

import java.util.List;

import isi.dan.practicas.practica1.model.Docente;

public interface DocenteDao {
	Docente insert(Docente nuevo);

	List<Docente> insert(Iterable<Docente> nuevos);

	Docente update(Docente actualizar);

	Boolean delete(Integer id);

	Docente findById(Integer id);

	List<Docente> findAll();
}

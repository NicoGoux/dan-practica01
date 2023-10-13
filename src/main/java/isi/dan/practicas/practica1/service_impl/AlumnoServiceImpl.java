package isi.dan.practicas.practica1.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isi.dan.practicas.practica1.dao.AlumnoDao;
import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Alumno;
import isi.dan.practicas.practica1.service.AlumnoService;

@Service
public class AlumnoServiceImpl implements AlumnoService {

	@Autowired
	private AlumnoDao alumnoDao;

	@Override
	public Alumno guardarAlumno(Alumno a) throws RecursoNoEncontradoException {
		if (a.getId() == null) {
			Alumno alumno = alumnoDao.insert(a);
			return alumno;
		}
		else {
			Alumno alumno = alumnoDao.update(a);
			if (alumno == null) {
				throw new RecursoNoEncontradoException("Alumno", a.getId());
			}
		}
		return a;
	}

	@Override
	public Alumno buscarAlumnoPorId(Integer id) throws RecursoNoEncontradoException {
		Alumno alumno = alumnoDao.findById(id);
		if (alumno == null) {
			throw new RecursoNoEncontradoException("Alumno", id);
		}
		return alumno;
	}

	@Override
	public List<Alumno> listarAlumnos() {
		return alumnoDao.findAll();
	}

	@Override
	public void bajaAlumno(Integer id) throws RecursoNoEncontradoException {
		Boolean success = alumnoDao.delete(id);
		if (!success) {
			throw new RecursoNoEncontradoException("Alumno", id);
		}
	}
}

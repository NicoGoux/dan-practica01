package isi.dan.practicas.practica1.service_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Alumno;
import isi.dan.practicas.practica1.repositories.AlumnoRepository;
import isi.dan.practicas.practica1.service.AlumnoService;

@Service
public class AlumnoServiceImpl implements AlumnoService {

	@Autowired
	AlumnoRepository alumnoRepo;

	@Override
	public Alumno guardarAlumno(Alumno a) throws RecursoNoEncontradoException {
		Alumno alumno = alumnoRepo.save(a);
		if (alumno == null) {
			throw new RecursoNoEncontradoException("Alumno", a.getId());
		}
		return alumno;
	}

	@Override
	public Alumno buscarAlumnoPorId(Integer id) throws RecursoNoEncontradoException {
		Optional<Alumno> alumno = alumnoRepo.findById(id);
		if (!alumno.isPresent()) {
			throw new RecursoNoEncontradoException("Alumno", id);
		}
		return alumno.get();
	}

	@Override
	public List<Alumno> listarAlumnos() {
		return alumnoRepo.findAll();
	}

	@Override
	public void bajaAlumno(Integer id) throws RecursoNoEncontradoException {
		alumnoRepo.deleteById(id);
	}
}

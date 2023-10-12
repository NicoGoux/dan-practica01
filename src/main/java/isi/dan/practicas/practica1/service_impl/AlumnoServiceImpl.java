package isi.dan.practicas.practica1.service_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Alumno;
import isi.dan.practicas.practica1.service.AlumnoService;
import isi.dan.practicas.practica1.service.MemoryDB;

@Service
public class AlumnoServiceImpl implements AlumnoService {

	@Autowired
	private MemoryDB memoryDB;

	@Override
	public Alumno guardarAlumno(Alumno a) throws RecursoNoEncontradoException {
		if (a.getId() == null) {
			a.setId(memoryDB.siguienteIdAlumno());
			memoryDB.getListaAlumnos().add(a);
		}
		else {
			Integer idAlumno = a.getId();
			Optional<Alumno> alumnoEncontrado = memoryDB.getListaAlumnos().stream()
					.filter(alumno -> alumno.getId() == idAlumno)
					.findFirst();
			if (alumnoEncontrado.isPresent()) {
				int indexOf = memoryDB.getListaAlumnos().indexOf(alumnoEncontrado.get());
				memoryDB.getListaAlumnos().set(indexOf, a);
			}
			else {
				throw new RecursoNoEncontradoException("Alumno", idAlumno);
			}
		}
		return a;
	}

	@Override
	public Optional<Alumno> buscarAlumnoPorId(Integer id) throws RecursoNoEncontradoException {
		Optional<Alumno> alumnoEncontrado = memoryDB.getListaAlumnos().stream()
				.filter(alumno -> alumno.getId() == id)
				.findFirst();
		if (alumnoEncontrado.isPresent()) {
			return alumnoEncontrado;
		}
		else {
			throw new RecursoNoEncontradoException("Alumno", id);
		}
	}

	@Override
	public List<Alumno> listarAlumnos() {
		return memoryDB.getListaAlumnos();
	}

	@Override
	public void bajaAlumno(Integer id) throws RecursoNoEncontradoException {
		Optional<Alumno> alumnoEncontrado = memoryDB.getListaAlumnos().stream()
				.filter(alumno -> alumno.getId() == id)
				.findFirst();
		if (alumnoEncontrado.isPresent()) {
			int indexOf = memoryDB.getListaAlumnos().indexOf(alumnoEncontrado.get());
			memoryDB.getListaAlumnos().remove(indexOf);
		}
		else {
			throw new RecursoNoEncontradoException("Alumno", id);
		}
	}
}

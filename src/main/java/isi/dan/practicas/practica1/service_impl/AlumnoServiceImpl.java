package isi.dan.practicas.practica1.service_impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Alumno;
import isi.dan.practicas.practica1.service.AlumnoService;

@Service
public class AlumnoServiceImpl implements AlumnoService {

	private static Integer ID = 1;
	private static List<Alumno> listaAlumnos = new ArrayList<>();

	@Override
	public Alumno guardarAlumno(Alumno a) throws RecursoNoEncontradoException {
		if (a.getId() == null) {
			a.setId(ID);
			ID++;
			listaAlumnos.add(a);
		}
		else {
			Integer idAlumno = a.getId();
			Optional<Alumno> alumnoEncontrado = listaAlumnos.stream()
					.filter(alumno -> alumno.getId() == idAlumno)
					.findFirst();
			if (alumnoEncontrado.isPresent()) {
				int indexOf = listaAlumnos.indexOf(alumnoEncontrado.get());
				listaAlumnos.set(indexOf, a);
			}
			else {
				throw new RecursoNoEncontradoException("Alumno", idAlumno);
			}
		}
		return a;
	}

	@Override
	public Optional<Alumno> buscarAlumnoPorId(Integer id) throws RecursoNoEncontradoException {
		Optional<Alumno> alumnoEncontrado = listaAlumnos.stream()
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
		return listaAlumnos;
	}

	@Override
	public void bajaAlumno(Integer id) throws RecursoNoEncontradoException {
		Optional<Alumno> alumnoEncontrado = listaAlumnos.stream()
				.filter(alumno -> alumno.getId() == id)
				.findFirst();
		if (alumnoEncontrado.isPresent()) {
			int indexOf = listaAlumnos.indexOf(alumnoEncontrado.get());
			listaAlumnos.remove(indexOf);
		}
		else {
			throw new RecursoNoEncontradoException("Alumno", id);
		}
	}
}

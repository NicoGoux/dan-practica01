package isi.dan.practicas.practica1.service_impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isi.dan.practicas.practica1.exception.CupoExcedidoException;
import isi.dan.practicas.practica1.exception.DocenteExcedidoException;
import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Alumno;
import isi.dan.practicas.practica1.model.Curso;
import isi.dan.practicas.practica1.model.Docente;
import isi.dan.practicas.practica1.service.AlumnoService;
import isi.dan.practicas.practica1.service.CursoService;
import isi.dan.practicas.practica1.service.DocenteService;

@Service
public class CursoServiceImpl implements CursoService {

	@Autowired
	AlumnoService alumnoService;

	@Autowired
	DocenteService docenteService;

	private static Integer ID = 1;
	private static List<Curso> listaCursos = new ArrayList<>();

	@Override
	public Curso guardarCurso(Curso c) throws RecursoNoEncontradoException {
		if (c.getId() == null) {
			c.setId(ID);
			ID++;
			listaCursos.add(c);
		}
		else {
			Integer idCurso = c.getId();
			Optional<Curso> cursoEncontrado = listaCursos.stream()
					.filter(curso -> curso.getId() == idCurso)
					.findFirst();
			if (cursoEncontrado.isPresent()) {
				int indexOf = listaCursos.indexOf(cursoEncontrado.get());
				listaCursos.add(indexOf, c);
			}
			else {
				throw new RecursoNoEncontradoException("Curso", idCurso);
			}
		}
		return c;
	}

	@Override
	public Optional<Curso> buscarCursoPorId(Integer id) throws RecursoNoEncontradoException {
		Optional<Curso> cursoEncontrado = listaCursos.stream()
				.filter(curso -> curso.getId() == id)
				.findFirst();
		if (cursoEncontrado.isPresent()) {
			return cursoEncontrado;
		}
		else {
			throw new RecursoNoEncontradoException("Curso", id);
		}
	}

	@Override
	public List<Curso> listarCursos() {
		return listaCursos;
	}

	@Override
	public void bajaCurso(Integer id) throws RecursoNoEncontradoException {
		Optional<Curso> cursoEncontrado = listaCursos.stream()
				.filter(curso -> curso.getId() == id)
				.findFirst();
		if (cursoEncontrado.isPresent()) {
			int indexOf = listaCursos.indexOf(cursoEncontrado.get());
			listaCursos.remove(indexOf);
		}
		else {
			throw new RecursoNoEncontradoException("Curso", id);
		}
	}

	@Override
	public void asignarDocente(Integer idCurso, Integer idDocente)
			throws RecursoNoEncontradoException, DocenteExcedidoException {
		Optional<Curso> cursoEncontrado = listaCursos.stream()
				.filter(curso -> curso.getId() == idCurso)
				.findFirst();
		if (cursoEncontrado.isPresent()) {
			Optional<Docente> docente = this.docenteService.buscarDocentePorId(idDocente);
			if (docente.isPresent()) {
				cursoEncontrado.get().asignarDocente(docente.get());
			}
		}
		else {
			throw new RecursoNoEncontradoException("Curso", idCurso);
		}
	}

	@Override
	public void inscribirAlumno(Integer idCurso, Integer idAlumno)
			throws RecursoNoEncontradoException, CupoExcedidoException {
		Optional<Curso> cursoEncontrado = listaCursos.stream()
				.filter(curso -> curso.getId() == idCurso)
				.findFirst();
		if (cursoEncontrado.isPresent()) {
			Optional<Alumno> alumno = this.alumnoService.buscarAlumnoPorId(idAlumno);
			if (alumno.isPresent()) {
				cursoEncontrado.get().inscribirACurso(alumno.get());
			}
		}
		else {
			throw new RecursoNoEncontradoException("Curso", idCurso);
		}
	}
}

package isi.dan.practicas.practica1.service_impl;

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
import isi.dan.practicas.practica1.service.MemoryDB;

@Service
public class CursoServiceImpl implements CursoService {

	@Autowired
	private MemoryDB memoryDB;

	@Autowired
	AlumnoService alumnoService;

	@Autowired
	DocenteService docenteService;

	@Override
	public Curso guardarCurso(Curso c) throws RecursoNoEncontradoException {
		if (c.getId() == null) {
			c.setId(memoryDB.siguienteIdCurso());
			memoryDB.getListaCursos().add(c);
		}
		else {
			Integer idCurso = c.getId();
			Optional<Curso> cursoEncontrado = memoryDB.getListaCursos().stream()
					.filter(curso -> curso.getId() == idCurso)
					.findFirst();
			if (cursoEncontrado.isPresent()) {
				int indexOf = memoryDB.getListaCursos().indexOf(cursoEncontrado.get());
				memoryDB.getListaCursos().add(indexOf, c);
			}
			else {
				throw new RecursoNoEncontradoException("Curso", idCurso);
			}
		}
		return c;
	}

	@Override
	public Optional<Curso> buscarCursoPorId(Integer id) throws RecursoNoEncontradoException {
		Optional<Curso> cursoEncontrado = memoryDB.getListaCursos().stream()
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
		return memoryDB.getListaCursos();
	}

	@Override
	public void bajaCurso(Integer id) throws RecursoNoEncontradoException {
		Optional<Curso> cursoEncontrado = memoryDB.getListaCursos().stream()
				.filter(curso -> curso.getId() == id)
				.findFirst();
		if (cursoEncontrado.isPresent()) {
			int indexOf = memoryDB.getListaCursos().indexOf(cursoEncontrado.get());
			memoryDB.getListaCursos().remove(indexOf);
		}
		else {
			throw new RecursoNoEncontradoException("Curso", id);
		}
	}

	@Override
	public void asignarDocente(Integer idCurso, Integer idDocente)
			throws RecursoNoEncontradoException, DocenteExcedidoException {
		Optional<Curso> cursoEncontrado = memoryDB.getListaCursos().stream()
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
		Optional<Curso> cursoEncontrado = memoryDB.getListaCursos().stream()
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

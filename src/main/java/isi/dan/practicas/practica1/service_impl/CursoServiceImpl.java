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
import isi.dan.practicas.practica1.repositories.CursoRepository;
import isi.dan.practicas.practica1.service.AlumnoService;
import isi.dan.practicas.practica1.service.CursoService;
import isi.dan.practicas.practica1.service.DocenteService;

@Service
public class CursoServiceImpl implements CursoService {

	@Autowired
	CursoRepository cursoRepo;

	@Autowired
	AlumnoService alumnoService;

	@Autowired
	DocenteService docenteService;

	@Override
	public Curso guardarCurso(Curso c) throws RecursoNoEncontradoException {
		Curso curso = cursoRepo.save(c);
		if (curso == null) {
			throw new RecursoNoEncontradoException("Curso", c.getId());
		}
		return curso;
	}

	@Override
	public Curso buscarCursoPorId(Integer id) throws RecursoNoEncontradoException {
		Optional<Curso> curso = cursoRepo.findById(id);
		if (!curso.isPresent()) {
			throw new RecursoNoEncontradoException("Curso", id);
		}
		return curso.get();
	}

	@Override
	public List<Curso> listarCursos() {
		return cursoRepo.findAll();
	}

	@Override
	public void bajaCurso(Integer id) throws RecursoNoEncontradoException {
		cursoRepo.deleteById(id);
	}

	@Override
	public void asignarDocente(Integer idCurso, Integer idDocente)
			throws RecursoNoEncontradoException, DocenteExcedidoException {
		Optional<Curso> cursoEncontrado = cursoRepo.findById(idCurso);
		if (!cursoEncontrado.isPresent()) {
			throw new RecursoNoEncontradoException("Curso", idCurso);
		}

		Docente docenteEncontrado = docenteService.buscarDocentePorId(idDocente);
		if (docenteEncontrado != null) {
			cursoEncontrado.get().asignarDocente(docenteEncontrado);
			cursoRepo.save(cursoEncontrado.get());
		}
	}

	@Override
	public void inscribirAlumno(Integer idCurso, Integer idAlumno)
			throws RecursoNoEncontradoException, CupoExcedidoException {
		Optional<Curso> cursoEncontrado = cursoRepo.findById(idCurso);
		if (!cursoEncontrado.isPresent()) {
			throw new RecursoNoEncontradoException("Curso", idCurso);
		}

		Alumno alumnoEncontrado = alumnoService.buscarAlumnoPorId(idAlumno);
		if (alumnoEncontrado != null) {
			if (cursoEncontrado.get().getListaInscriptos().contains(alumnoEncontrado)) {
				return;
			}
			cursoEncontrado.get().getListaInscriptos().add(alumnoEncontrado);
			cursoRepo.save(cursoEncontrado.get());
		}
	}

	@Override
	public void desinscribirAlumno(Integer idCurso, Integer idAlumno) throws RecursoNoEncontradoException {
		Optional<Curso> cursoEncontrado = cursoRepo.findById(idCurso);
		if (!cursoEncontrado.isPresent()) {
			throw new RecursoNoEncontradoException("Curso", idCurso);
		}

		Alumno alumnoEncontrado = alumnoService.buscarAlumnoPorId(idAlumno);
		if (alumnoEncontrado != null) {
			if (!(cursoEncontrado.get().getListaInscriptos().contains(alumnoEncontrado))) {
				return;
			}
			cursoEncontrado.get().getListaInscriptos().remove(alumnoEncontrado);
			cursoRepo.save(cursoEncontrado.get());
		}
	}
}

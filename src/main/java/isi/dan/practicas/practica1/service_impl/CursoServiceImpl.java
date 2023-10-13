package isi.dan.practicas.practica1.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isi.dan.practicas.practica1.dao.CursoDao;
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
	private CursoDao cursoDao;

	@Autowired
	AlumnoService alumnoService;

	@Autowired
	DocenteService docenteService;

	@Override
	public Curso guardarCurso(Curso c) throws RecursoNoEncontradoException {
		if (c.getId() == null) {
			Curso curso = cursoDao.insert(c);
			return curso;
		}
		else {
			Curso curso = cursoDao.update(c);
			if (curso == null) {
				throw new RecursoNoEncontradoException("Curso", c.getId());
			}
		}
		return c;
	}

	@Override
	public Curso buscarCursoPorId(Integer id) throws RecursoNoEncontradoException {
		Curso curso = cursoDao.findById(id);
		if (curso == null) {
			throw new RecursoNoEncontradoException("Curso", id);
		}
		return curso;
	}

	@Override
	public List<Curso> listarCursos() {
		return cursoDao.findAll();
	}

	@Override
	public void bajaCurso(Integer id) throws RecursoNoEncontradoException {
		Boolean success = cursoDao.delete(id);
		if (!success) {
			throw new RecursoNoEncontradoException("Curso", id);
		}
	}

	@Override
	public void asignarDocente(Integer idCurso, Integer idDocente)
			throws RecursoNoEncontradoException, DocenteExcedidoException {
		Curso cursoEncontrado = cursoDao.findById(idCurso);
		if (cursoEncontrado == null) {
			throw new RecursoNoEncontradoException("Curso", idCurso);
		}

		Docente docenteEncontrado = docenteService.buscarDocentePorId(idDocente);
		if (docenteEncontrado != null) {
			cursoEncontrado.asignarDocente(docenteEncontrado);
			cursoDao.update(cursoEncontrado);
		}
	}

	@Override
	public void inscribirAlumno(Integer idCurso, Integer idAlumno)
			throws RecursoNoEncontradoException, CupoExcedidoException {
		Curso cursoEncontrado = cursoDao.findById(idCurso);
		if (cursoEncontrado == null) {
			throw new RecursoNoEncontradoException("Curso", idCurso);
		}

		Alumno alumnoEncontrado = alumnoService.buscarAlumnoPorId(idAlumno);
		if (alumnoEncontrado != null) {
			if (cursoEncontrado.getListaInscriptos().contains(alumnoEncontrado.getId())) {
				return;
			}
			cursoDao.inscribirACurso(cursoEncontrado.getId(), alumnoEncontrado.getId());
		}
	}

	@Override
	public void desinscribirAlumno(Integer idCurso, Integer idAlumno) throws RecursoNoEncontradoException {
		Curso cursoEncontrado = cursoDao.findById(idCurso);
		if (cursoEncontrado == null) {
			throw new RecursoNoEncontradoException("Curso", idCurso);
		}

		Alumno alumnoEncontrado = alumnoService.buscarAlumnoPorId(idAlumno);
		if (alumnoEncontrado != null) {
			cursoDao.desinscribirACurso(cursoEncontrado.getId(), alumnoEncontrado.getId());
		}
	}
}

package isi.dan.practicas.practica1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import isi.dan.practicas.practica1.exception.CupoExcedidoException;
import isi.dan.practicas.practica1.exception.DocenteExcedidoException;
import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Curso;

@Service
public interface CursoService {
	public Curso guardarCurso(Curso c) throws RecursoNoEncontradoException;

	public Optional<Curso> buscarCursoPorId(Integer id) throws RecursoNoEncontradoException;

	public List<Curso> listarCursos();

	public void bajaCurso(Integer id) throws RecursoNoEncontradoException;

	public void asignarDocente(Integer id, Integer idDocente)
			throws RecursoNoEncontradoException, DocenteExcedidoException;

              public void inscribirAlumno(Integer idCurso, Integer idAlumno) throws RecursoNoEncontradoException, CupoExcedidoException;
}

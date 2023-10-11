package isi.dan.practicas.practica1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Docente;

@Service
public interface DocenteService {
	public Docente guardarDocente(Docente d) throws RecursoNoEncontradoException;

	public Optional<Docente> buscarDocentePorId(Integer id) throws RecursoNoEncontradoException;

	public List<Docente> listarDocentes();

	public void bajaDocente(Integer id) throws RecursoNoEncontradoException;
}

package isi.dan.practicas.practica1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Alumno;

@Service
public interface AlumnoService {
	public Alumno guardarAlumno(Alumno a) throws RecursoNoEncontradoException;

	public Optional<Alumno> buscarAlumnoPorId(Integer id) throws RecursoNoEncontradoException;

	public List<Alumno> listarAlumnos();

	public void bajaAlumno(Integer id) throws RecursoNoEncontradoException;
}

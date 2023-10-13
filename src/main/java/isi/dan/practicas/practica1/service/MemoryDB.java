package isi.dan.practicas.practica1.service;

import java.util.ArrayList;
import java.util.List;

import isi.dan.practicas.practica1.model.Alumno;
import isi.dan.practicas.practica1.model.Curso;
import isi.dan.practicas.practica1.model.Docente;

public class MemoryDB {
	private Integer idDocente;
	private Integer idAlumno;
	private Integer idCurso;

	private List<Docente> listaDocentes;
	private List<Alumno> listaAlumnos;
	private List<Curso> listaCursos;

	/**
	 * @param idDocente
	 * @param idAlumno
	 * @param idCurso
	 * @param listaDocentes
	 * @param listaAlumnos
	 * @param listaCursos
	 */
	public MemoryDB(Integer idInicial) {
		this.idDocente = idInicial;
		this.idAlumno = idInicial;
		this.idCurso = idInicial;
		this.listaDocentes = new ArrayList<>(0);
		this.listaAlumnos = new ArrayList<>(0);
		this.listaCursos = new ArrayList<>(0);
	}

	/**
	 * @return the listaDocentes
	 */
	public List<Docente> getListaDocentes() {
		return listaDocentes;
	}

	/**
	 * @return the listaAlumnos
	 */
	public List<Alumno> getListaAlumnos() {
		return listaAlumnos;
	}

	/**
	 * @return the listaCursos
	 */
	public List<Curso> getListaCursos() {
		return listaCursos;
	}

	public Integer siguienteIdDocente() {
		this.idDocente++;
		return this.idDocente;
	}

	public Integer siguienteIdAlumno() {
		this.idAlumno++;
		return this.idAlumno;
	}

	public Integer siguienteIdCurso() {
		this.idCurso++;
		return this.idCurso;
	}
}

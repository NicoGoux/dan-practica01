package isi.dan.practicas.practica1.model;

import java.util.ArrayList;
import java.util.List;

import isi.dan.practicas.practica1.exception.CupoExcedidoException;
import isi.dan.practicas.practica1.exception.DocenteExcedidoException;

public class Curso {
	private Integer id;
	private String nombre;
	private Integer creditos;
	private Integer cupo;
	private Docente docenteAsignado;
	private List<Integer> listaInscriptos;

	/**
	 * @param id
	 * @param nombre
	 * @param creditos
	 * @param cupo
	 * @param docenteAsignado
	 * @param listaInscriptos
	 */
	public Curso(Integer id, String nombre, Integer creditos, Integer cupo, Docente docenteAsignado,
			List<Integer> listaInscriptos) {
		this.id = id;
		this.nombre = nombre;
		this.creditos = creditos;
		this.cupo = cupo;
		this.docenteAsignado = docenteAsignado;
		this.listaInscriptos = listaInscriptos;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the creditos
	 */
	public Integer getCreditos() {
		return creditos;
	}

	/**
	 * @param creditos
	 *            the creditos to set
	 */
	public void setCreditos(Integer creditos) {
		this.creditos = creditos;
	}

	/**
	 * @return the cupo
	 */
	public Integer getCupo() {
		return cupo;
	}

	/**
	 * @param cupo
	 *            the cupo to set
	 */
	public void setCupo(Integer cupo) {
		this.cupo = cupo;
	}

	/**
	 * @return the docenteAsignado
	 */
	public Docente getDocenteAsignado() {
		return docenteAsignado;
	}

	/**
	 * @param docenteAsignado
	 *            the docenteAsignado to set
	 */
	public void setDocenteAsignado(Docente docenteAsignado) {
		this.docenteAsignado = docenteAsignado;
	}

	/**
	 * @return the listaInscriptos
	 */
	public List<Integer> getListaInscriptos() {
		return listaInscriptos;
	}

	/**
	 * @param listaInscriptos
	 *            the listaInscriptos to set
	 */
	public void setListaInscriptos(List<Integer> listaInscriptos) {
		this.listaInscriptos = listaInscriptos;
	}

	public void asignarDocente(Docente docente) throws DocenteExcedidoException {
		if (!docente.getCursosDictados().contains(this.getId())) {
			if (docente.obtenerCantidadCursosDictados() < 3) {
				this.removerDocente();
				this.setDocenteAsignado(docente);
				this.docenteAsignado.addCursoDictado(this);
			}
			else {
				throw new DocenteExcedidoException();
			}
		}
	}

	public void inscribirACurso(Alumno alumno) throws CupoExcedidoException {
		if (!this.listaInscriptos.contains(alumno.getId())) {
			if (this.listaInscriptos.size() < this.cupo) {
				alumno.addCursoInscripto(this);
				this.listaInscriptos.add(alumno.getId());
			}
			else {
				throw new CupoExcedidoException();
			}
		}
	}

	public void removerDocente() {
		if (this.docenteAsignado != null) {
			this.docenteAsignado.removeCurso(this);
			this.setDocenteAsignado(null);
		}
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", nombre=" + nombre + ", creditos=" + creditos + ", cupo=" + cupo
				+ ", docenteAsignado=" + docenteAsignado + ", listaInscriptos=" + listaInscriptos + "]";
	}
}

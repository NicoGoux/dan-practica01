package isi.dan.practicas.practica1.model;

import java.util.ArrayList;
import java.util.List;

public class Docente {
	private Integer id;
	private String nombre;
	private Double salario;
	private List<Integer> cursosDictados;

	/**
	 * @param id
	 * @param nombre
	 * @param salario
	 * @param cursosDictados
	 */
	public Docente(Integer id, String nombre, Double salario) {
		this.id = id;
		this.nombre = nombre;
		this.salario = salario;
		this.cursosDictados = new ArrayList<>(0);
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
	 * @return the salario
	 */
	public Double getSalario() {
		return salario;
	}

	/**
	 * @param salario
	 *            the salario to set
	 */
	public void setSalario(Double salario) {
		this.salario = salario;
	}

	/**
	 * @return the cursosDictados
	 */
	public List<Integer> getCursosDictados() {
		return cursosDictados;
	}

	/**
	 * @param cursosDictados
	 *            the cursosDictados to set
	 */
	public void setCursosDictados(List<Integer> cursosDictados) {
		this.cursosDictados = cursosDictados;
	}

	/**
	 * @param cursosDictados
	 *            return the quantity of dictated courses
	 */
	public Integer obtenerCantidadCursosDictados() {
		return this.cursosDictados.size();
	}

	public void removeCurso(Curso curso) {
		this.cursosDictados.remove(this.cursosDictados.indexOf(curso.getId()));
	}

	public void addCursoDictado(Curso curso) {
		this.cursosDictados.add(curso.getId());
	}

	@Override
	public String toString() {
		return "Docente [id=" + id + ", nombre=" + nombre + ", salario=" + salario + ", cursosDictados="
				+ cursosDictados + "]";
	}

}

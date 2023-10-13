package isi.dan.practicas.practica1.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "docente")
public class Docente {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "salario", nullable = false)
	private Double salario;

	@OneToMany(mappedBy = "docenteAsignado")
	// @JsonIgnore
	private List<Curso> cursosDictados;

	public Docente() {
		this.cursosDictados = new ArrayList<>();
	}

	/**
	 * @param nombre
	 * @param salario
	 * @param cursosDictados
	 */
	public Docente(String nombre, Double salario) {
		this.nombre = nombre;
		this.salario = salario;
		this.cursosDictados = new ArrayList<>();
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
	 * @return los ids de los cursos dictados, permitiendo que no se entre en un
	 *         bucle al solicitar los objetos
	 */
	public List<Integer> getCursosDictados() {
		List<Integer> cursosDictadosId = new ArrayList<>();
		for (Curso curso : cursosDictados) {
			cursosDictadosId.add(curso.getId());
		}
		return cursosDictadosId;
	}

	/**
	 * @param cursosDictados
	 *            the cursosDictados to set
	 */
	public void setCursosDictados(List<Curso> cursosDictados) {
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
		this.cursosDictados.remove(this.cursosDictados.indexOf(curso));
	}

	public void addCursoDictado(Curso curso) {
		this.cursosDictados.add(curso);
	}

	@Override
	public String toString() {
		return "Docente [id=" + id + ", nombre=" + nombre + ", salario=" + salario + ", cursosDictados="
				+ cursosDictados + "]";
	}
}

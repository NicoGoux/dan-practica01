package isi.dan.practicas.practica1.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "alumno")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "legajo", nullable = false)
    private Integer legajo;

    @ManyToMany(mappedBy = "listaInscriptos")
    private List<Curso> cursosInscriptos;

    public Alumno() {
        this.cursosInscriptos = new ArrayList<>();
    }

    /**
     * @param nombre
     * @param legajo
     * @param cursosInscriptos
     */
    public Alumno(String nombre, Integer legajo) {
        this.nombre = nombre;
        this.legajo = legajo;
        this.cursosInscriptos = new ArrayList<>();
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
     * @return the legajo
     */
    public Integer getLegajo() {
        return legajo;
    }

    /**
     * @param legajo
     *            the legajo to set
     */
    public void setLegajo(Integer legajo) {
        this.legajo = legajo;
    }

    /**
     * @return the cursosInscriptos
     */
    public List<Integer> getCursosInscriptos() {
        List<Integer> cursosInscriptosId = new ArrayList<>();
        for (Curso curso : cursosInscriptos) {
            cursosInscriptosId.add(curso.getId());
        }
        return cursosInscriptosId;
    }

    /**
     * @param cursosInscriptos
     *            the cursosInscriptos to set
     */
    public void setCursosInscriptos(List<Curso> cursosInscriptos) {
        this.cursosInscriptos = cursosInscriptos;
    }

    public void addCursoInscripto(Curso curso) {
        this.cursosInscriptos.add(curso);
    }

    @Override
    public String toString() {
        return "Alumno [id=" + id + ", nombre=" + nombre + ", legajo=" + legajo + ", cursosInscriptos="
                + cursosInscriptos + "]";
    }

}

package isi.dan.practicas.practica1.model;

import java.util.ArrayList;
import java.util.List;

public class Alumno {
    private Integer id;
    private String nombre;
    private Integer legajo;
    List<Curso> cursosInscriptos;

    /**
     * @param id
     * @param nombre
     * @param legajo
     * @param cursosInscriptos
     */
    public Alumno(Integer id, String nombre, Integer legajo) {
        this.id = id;
        this.nombre = nombre;
        this.legajo = legajo;
        this.cursosInscriptos = new ArrayList<>(0);
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
    public List<Curso> getCursosInscriptos() {
        return cursosInscriptos;
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

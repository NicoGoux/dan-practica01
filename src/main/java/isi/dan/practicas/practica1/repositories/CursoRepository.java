package isi.dan.practicas.practica1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import isi.dan.practicas.practica1.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Integer> {

}

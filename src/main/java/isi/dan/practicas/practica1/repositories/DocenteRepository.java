package isi.dan.practicas.practica1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import isi.dan.practicas.practica1.model.Docente;

public interface DocenteRepository extends JpaRepository<Docente, Integer> {

}

package isi.dan.practicas.practica1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isi.dan.practicas.practica1.model.Alumno;
import isi.dan.practicas.practica1.service.AlumnoService;

@RestController
@RequestMapping("/api/alumno")
public class alumnoController {

	@Autowired
	AlumnoService alumnoService;

	@GetMapping()
	public ResponseEntity<List<Alumno>> listarAlumnos() {
		try {
			return ResponseEntity.ok(this.alumnoService.listarAlumnos());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Alumno> obtenerAlumno(@PathVariable Integer id) {
		try {
			return ResponseEntity.ok(this.alumnoService.buscarAlumnoPorId(id));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping
	public ResponseEntity<?> crearAlumno(@RequestBody Alumno nuevo) {
		Alumno creado = null;
		try {
			creado = this.alumnoService.guardarAlumno(nuevo);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		return ResponseEntity.ok(creado);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<?> actualizarAlumno(@RequestBody Alumno nuevo, @PathVariable Integer id) {
		Alumno creado = null;
		try {
			nuevo.setId(id);
			creado = this.alumnoService.guardarAlumno(nuevo);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		return ResponseEntity.ok(creado);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> bajaAlumno(@PathVariable Integer id) {
		try {
			this.alumnoService.bajaAlumno(id);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		return ResponseEntity.ok("Alumno dado de baja");
	}
}

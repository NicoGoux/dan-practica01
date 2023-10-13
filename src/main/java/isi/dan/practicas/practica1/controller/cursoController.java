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

import isi.dan.practicas.practica1.model.Curso;

import isi.dan.practicas.practica1.service.CursoService;

@RestController
@RequestMapping("/api/curso")
public class cursoController {

	@Autowired
	CursoService cursoService;

	@GetMapping()
	public ResponseEntity<List<Curso>> listarCursos() {
		try {
			return ResponseEntity.ok(this.cursoService.listarCursos());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(path = "/{idCurso}/inscribir-alumno/{idAlumno}")
	public ResponseEntity<String> inscribirAlumno(@PathVariable Integer idCurso,
			@PathVariable Integer idAlumno) {
		try {
			this.cursoService.inscribirAlumno(idCurso, idAlumno);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		return ResponseEntity.ok("Alumno asignado");
	}

	@GetMapping(path = "/{idCurso}/desinscribir-alumno/{idAlumno}")
	public ResponseEntity<String> desinscribirAlumno(@PathVariable Integer idCurso,
			@PathVariable Integer idAlumno) {
		try {
			this.cursoService.desinscribirAlumno(idCurso, idAlumno);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		return ResponseEntity.ok("Alumno desasignado");
	}

	@GetMapping(path = "/{idCurso}/asignar-docente/{idDocente}")
	public ResponseEntity<String> asignarDocente(@PathVariable Integer idCurso,
			@PathVariable Integer idDocente) {
		try {
			this.cursoService.asignarDocente(idCurso, idDocente);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		return ResponseEntity.ok("Docente asignado");
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Curso> obtenerCurso(@PathVariable Integer id) {
		try {
			return ResponseEntity.ok(this.cursoService.buscarCursoPorId(id));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping
	public ResponseEntity<?> crearCurso(@RequestBody Curso nuevo) {
		Curso creado = null;
		try {
			creado = this.cursoService.guardarCurso(nuevo);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		return ResponseEntity.ok(creado);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<?> actualizarCurso(@RequestBody Curso nuevo, @PathVariable Integer id) {
		Curso creado = null;
		try {
			nuevo.setId(id);
			creado = this.cursoService.guardarCurso(nuevo);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		return ResponseEntity.ok(creado);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> bajaCurso(@PathVariable Integer id) {
		try {
			this.cursoService.bajaCurso(id);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		return ResponseEntity.ok("Curso dado de baja");
	}
}

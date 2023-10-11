package isi.dan.practicas.practica1.controller;

import java.util.List;
import java.util.Optional;

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

import isi.dan.practicas.practica1.model.Docente;
import isi.dan.practicas.practica1.service.DocenteService;

@RestController
@RequestMapping("/api/docente")
public class docenteController {

	@Autowired
	DocenteService docenteService;

	@GetMapping()
	public ResponseEntity<List<Docente>> listarDocentes() {
		try {
			return ResponseEntity.ok(this.docenteService.listarDocentes());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Optional<Docente>> obtenerDocente(Integer id) {
		try {
			return ResponseEntity.ok(this.docenteService.buscarDocentePorId(id));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping
	public ResponseEntity<?> crearDocente(@RequestBody Docente nuevo) {
		Docente creado = null;
		try {
			creado = this.docenteService.guardarDocente(nuevo);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		return ResponseEntity.ok(creado);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<?> actualizarAlumno(@RequestBody Docente nuevo, @PathVariable Integer id) {
		Docente creado = null;
		try {
			nuevo.setId(id);
			creado = this.docenteService.guardarDocente(nuevo);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		return ResponseEntity.ok(creado);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> bajaDocente(@PathVariable Integer id) {
		try {
			this.docenteService.bajaDocente(id);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		return ResponseEntity.ok("Docente dado de baja");
	}
}

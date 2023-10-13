package isi.dan.practicas.practica1.service_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Docente;
import isi.dan.practicas.practica1.repositories.DocenteRepository;
import isi.dan.practicas.practica1.service.DocenteService;

@Service
public class DocenteServiceImpl implements DocenteService {

	@Autowired
	DocenteRepository docenteRepo;

	@Override
	public Docente guardarDocente(Docente d) throws RecursoNoEncontradoException {
		Docente docente = docenteRepo.save(d);
		if (docente == null) {
			throw new RecursoNoEncontradoException("Docente", d.getId());
		}
		return docente;
	}

	@Override
	public Docente buscarDocentePorId(Integer id) throws RecursoNoEncontradoException {
		Optional<Docente> docente = docenteRepo.findById(id);
		if (!docente.isPresent()) {
			throw new RecursoNoEncontradoException("Docente", id);
		}
		return docente.get();
	}

	@Override
	public List<Docente> listarDocentes() {
		return docenteRepo.findAll();
	}

	@Override
	public void bajaDocente(Integer id) throws RecursoNoEncontradoException {
		docenteRepo.deleteById(id);
	}
}

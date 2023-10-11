package isi.dan.practicas.practica1.service_impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Docente;
import isi.dan.practicas.practica1.service.DocenteService;

@Service
public class DocenteServiceImpl implements DocenteService {

	private static Integer ID = 1;
	private static List<Docente> listaDocentes = new ArrayList<>();

	@Override
	public Docente guardarDocente(Docente d) throws RecursoNoEncontradoException {
		if (d.getId() == null) {
			d.setId(ID);
			ID++;
			listaDocentes.add(d);
		}
		else {
			Integer idDocente = d.getId();
			Optional<Docente> docenteEncontrado = listaDocentes.stream()
					.filter(docente -> docente.getId() == idDocente)
					.findFirst();
			if (docenteEncontrado.isPresent()) {
				int indexOf = listaDocentes.indexOf(docenteEncontrado.get());
				listaDocentes.add(indexOf, d);
			}
			else {
				throw new RecursoNoEncontradoException("Docente", idDocente);
			}
		}

		return d;
	}

	@Override
	public Optional<Docente> buscarDocentePorId(Integer id) throws RecursoNoEncontradoException {
		Optional<Docente> docenteEncontrado = listaDocentes.stream()
				.filter(docente -> docente.getId() == id)
				.findFirst();
		if (docenteEncontrado.isPresent()) {
			return docenteEncontrado;
		}
		else {
			throw new RecursoNoEncontradoException("Docente", id);
		}
	}

	@Override
	public List<Docente> listarDocentes() {
		return listaDocentes;
	}

	@Override
	public void bajaDocente(Integer id) throws RecursoNoEncontradoException {
		Optional<Docente> docenteEncontrado = listaDocentes.stream()
				.filter(docente -> docente.getId() == id)
				.findFirst();
		if (docenteEncontrado.isPresent()) {
			int indexOf = listaDocentes.indexOf(docenteEncontrado.get());
			listaDocentes.remove(indexOf);
		}
		else {
			throw new RecursoNoEncontradoException("Docente", id);
		}
	}

}

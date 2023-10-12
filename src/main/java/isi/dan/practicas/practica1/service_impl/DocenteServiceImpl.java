package isi.dan.practicas.practica1.service_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Docente;
import isi.dan.practicas.practica1.service.DocenteService;
import isi.dan.practicas.practica1.service.MemoryDB;

@Service
public class DocenteServiceImpl implements DocenteService {

	@Autowired
	private static MemoryDB memoryDB;

	@Override
	public Docente guardarDocente(Docente d) throws RecursoNoEncontradoException {
		if (d.getId() == null) {
			d.setId(memoryDB.siguienteIdDocente());
			memoryDB.getListaDocentes().add(d);
		}
		else {
			Integer idDocente = d.getId();
			Optional<Docente> docenteEncontrado = memoryDB.getListaDocentes().stream()
					.filter(docente -> docente.getId() == idDocente)
					.findFirst();
			if (docenteEncontrado.isPresent()) {
				int indexOf = memoryDB.getListaDocentes().indexOf(docenteEncontrado.get());
				memoryDB.getListaDocentes().add(indexOf, d);
			}
			else {
				throw new RecursoNoEncontradoException("Docente", idDocente);
			}
		}

		return d;
	}

	@Override
	public Optional<Docente> buscarDocentePorId(Integer id) throws RecursoNoEncontradoException {
		Optional<Docente> docenteEncontrado = memoryDB.getListaDocentes().stream()
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
		return memoryDB.getListaDocentes();
	}

	@Override
	public void bajaDocente(Integer id) throws RecursoNoEncontradoException {
		Optional<Docente> docenteEncontrado = memoryDB.getListaDocentes().stream()
				.filter(docente -> docente.getId() == id)
				.findFirst();
		if (docenteEncontrado.isPresent()) {
			int indexOf = memoryDB.getListaDocentes().indexOf(docenteEncontrado.get());
			memoryDB.getListaDocentes().remove(indexOf);
		}
		else {
			throw new RecursoNoEncontradoException("Docente", id);
		}
	}

}

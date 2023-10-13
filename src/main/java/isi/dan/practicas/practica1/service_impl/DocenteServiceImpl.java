package isi.dan.practicas.practica1.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isi.dan.practicas.practica1.dao.DocenteDao;
import isi.dan.practicas.practica1.exception.RecursoNoEncontradoException;
import isi.dan.practicas.practica1.model.Docente;
import isi.dan.practicas.practica1.service.DocenteService;

@Service
public class DocenteServiceImpl implements DocenteService {

	@Autowired
	private DocenteDao docenteDao;

	@Override
	public Docente guardarDocente(Docente d) throws RecursoNoEncontradoException {
		if (d.getId() == null) {
			Docente docente = docenteDao.insert(d);
			return docente;
		}
		else {
			Docente docente = docenteDao.update(d);
			if (docente == null) {
				throw new RecursoNoEncontradoException("Docente", d.getId());
			}
		}
		return d;
	}

	@Override
	public Docente buscarDocentePorId(Integer id) throws RecursoNoEncontradoException {
		Docente docente = docenteDao.findById(id);
		if (docente == null) {
			throw new RecursoNoEncontradoException("Docente", id);
		}
		return docente;
	}

	@Override
	public List<Docente> listarDocentes() {
		return docenteDao.findAll();
	}

	@Override
	public void bajaDocente(Integer id) throws RecursoNoEncontradoException {
		Boolean success = docenteDao.delete(id);
		if (!success) {
			throw new RecursoNoEncontradoException("Docente", id);
		}
	}
}

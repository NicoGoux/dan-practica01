package isi.dan.practicas.practica1.exception;

public class RecursoNoEncontradoException extends Exception {
	public RecursoNoEncontradoException(String modelo, Integer id) {
		super("No existe el identificador " + id + " del modelo " + modelo);
	}
}

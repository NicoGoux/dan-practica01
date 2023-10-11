package isi.dan.practicas.practica1.exception;

public class DocenteExcedidoException extends Exception {
	public DocenteExcedidoException() {
		super("El docente ya tiene asignado tres cursos y no puede asignar mas");
	}
}

package es.uned.exceptions;

/**
 * Excepción personalizada para indicar que un DNI es inválido.
 */
public class DniInvalidoException extends RuntimeException {
    public DniInvalidoException(String message) {
        super(message);
    }
}

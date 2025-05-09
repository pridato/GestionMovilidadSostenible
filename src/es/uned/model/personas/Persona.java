package es.uned.model.personas;

import es.uned.exceptions.DniInvalidoException;

import java.util.Objects;

/**
 * Clase que representa una persona con atributos como nombre, apellidos, dni, email y teléfono.
 */
public class Persona {

    private String nombre;
    private String apellidos;
    private String dni;
    private String email;
    private int telefono;

    public Persona(String dni, String nombre, String apellidos, String email, int telefono) throws DniInvalidoException {

        validardni(dni);
        validarEmail(email);
        validarTelefono(telefono);

        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
    }

    /**
     * Método para validar el dni de la persona.
     * @param dni dni a validar.
     * @throws DniInvalidoException Excepción lanzada si el dni es inválido.
     */
    private static void validardni(String dni) throws DniInvalidoException {
        // si el dni está mal landando una excepción
        if (dni.length() != 9) {
            throw new DniInvalidoException("dni inválido, debe tener 9 caracteres");
        }
        // si el dni no es un número
        if (!dni.substring(0, 8).matches("[0-9]+")) {
            throw new DniInvalidoException("dni inválido, no tiene 8 números ");
        }

        // si el dni no es una letra
        if (!dni.substring(8, 9).toUpperCase().matches("[A-Z]")) {
            throw new DniInvalidoException("dni inválido, no tiene 1 letra");
        }


    }

    /**
     * Método para validar el email de la persona.
     * @param email Email a validar.
     * @throws IllegalArgumentException Excepción lanzada si el email es inválido.
     */
    private void validarEmail(String email) throws IllegalArgumentException {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("El email no es válido.");
        }
    }

    /**
     * Método para validar el número de teléfono de una persona
     * @param telefono Número de teléfono a validar.
     * @throws IllegalArgumentException Excepción lanzada si el teléfono es inválido.
     */
    private void validarTelefono(int telefono) throws IllegalArgumentException {
        if (String.valueOf(telefono).length() != 9) {
            throw new IllegalArgumentException("El número de teléfono debe tener 9 dígitos.");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getdni() {
        return dni;
    }

    public void setdni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Persona other)) return false;
        return Objects.equals(this.dni, other.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }


    @Override
    public String toString() {
        final String INDENT = "    ";

        return INDENT + "Nombre completo: " + getNombre() + " " + getApellidos() + "\n" +
                INDENT + "dni: " + getdni() + "\n" +
                INDENT + "Email: " + getEmail() + "\n" +
                INDENT + "Teléfono: " + getTelefono() + "\n";
    }

}

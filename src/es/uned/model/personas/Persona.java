package es.uned.model.personas;

import es.uned.exceptions.DniInvalidoException;

/**
 * Clase que representa una persona con atributos como nombre, apellidos, DNI, email y teléfono.
 */
public class Persona {

    private String nombre;
    private String apellidos;
    private String DNI;
    private String email;
    private int telefono;

    public Persona(String nombre, String apellidos, String DNI, String email, int telefono) throws DniInvalidoException {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.DNI = DNI;
        this.email = email;
        this.telefono = telefono;
    }

    /**
     * Método para validar el DNI de la persona.
     * @param DNI DNI a validar.
     * @throws DniInvalidoException Excepción lanzada si el DNI es inválido.
     */
    public static void validarDNI(String DNI) throws DniInvalidoException {
        // si el dni está mal landando una excepción
        if (DNI.length() != 9) {
            throw new DniInvalidoException("DNI inválido, debe tener 9 caracteres");
        }
        // si el dni no es un número
        if (!DNI.substring(0, 8).matches("[0-9]+")) {
            throw new DniInvalidoException("DNI inválido, no tiene 8 números ");
        }

        // si el dni no es una letra
        if (!DNI.substring(8, 9).matches("[A-Z]")) {
            throw new DniInvalidoException("DNI inválido, no tiene 1 letra");
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

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
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
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", DNI='" + DNI + '\'' +
                ", email='" + email + '\'' +
                ", telefono=" + telefono +
                '}';
    }
}

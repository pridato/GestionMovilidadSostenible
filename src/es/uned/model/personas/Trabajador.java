package es.uned.model.personas;

import java.util.Date;

/**
 * Clase que representa un trabajador, que hereda de la clase Persona.
 */
public class Trabajador extends Persona {
    private final Date fechaContratacion;

    public Trabajador(String DNI, String nombre, String apellidos, String email, int telefono, Date fechaContratacion) {
        super(DNI,nombre, apellidos, email, telefono);
        this.fechaContratacion = fechaContratacion;
    }

    @Override
    public String toString() {
        final String INDENT = "    ";
        return "Trabajador {\n" +
                super.toString() +
                INDENT + "Fecha de contratación: " + fechaContratacion + "\n" +
                "}";
    }

}

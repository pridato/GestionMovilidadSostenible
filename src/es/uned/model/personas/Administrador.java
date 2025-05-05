package es.uned.model.personas;

import java.util.Date;

/**
 * Clase que representa un administrador, que hereda de la clase Trabajador.
 */
public class Administrador extends Trabajador {

    public Administrador(String DNI, String nombre, String apellidos, String email, int telefono, Date fechaContratacion) {
        super(nombre, apellidos, DNI, email, telefono, fechaContratacion);
    }


    @Override
    public String toString() {
        final String INDENT = "    ";
        return "Administrador {\n" +
                super.toString() +
                INDENT + "Permisos asignados:\n" +
                "}";
    }

}

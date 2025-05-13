package es.uned.model.personas;

import es.uned.model.vehiculos.Vehiculo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase que representa un trabajador de mantenimiento, que hereda de la clase Trabajador.
 */
public class Mantenimiento extends Trabajador{



    public Mantenimiento(String DNI, String nombre, String apellidos, String email, int telefono, Date fechaContratacion) {
        super(DNI, nombre, apellidos, email, telefono, fechaContratacion);
    }


    @Override
    public String toString() {
        final String INDENT = "    ";

        // Llama al toString() de la clase padre Trabajador
        return "Mantenimiento {\n" +
                super.toString() +  // Llama al toString() de la clase padre Trabajador
                INDENT + "Bases asignadas:\n" +
                "}";
    }
}

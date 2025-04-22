package es.uned.model.personas;

import java.util.Date;

/**
 * Clase que representa un trabajador, que hereda de la clase Persona.
 */
public class Trabajador extends Persona {
    private Date fechaContratacion;

    public Trabajador(String DNI, String nombre, String apellidos, String email, int telefono, Date fechaContratacion) {
        super(nombre, apellidos, DNI, email, telefono);
        this.fechaContratacion = fechaContratacion;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    @Override
    public String toString() {
        final String INDENT = "    ";
        StringBuilder sb = new StringBuilder();
        sb.append("Trabajador {\n");
        sb.append(super.toString());
        sb.append(INDENT).append("Fecha de contratación: ").append(fechaContratacion).append("\n");
        sb.append("}");
        return sb.toString();
    }

}

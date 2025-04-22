package es.uned.model.personas;

import java.util.Date;

/**
 * Clase que representa un trabajador, que hereda de la clase Persona.
 */
public class Trabajador extends Persona {
    private Date fechaContratacion;

    public Trabajador(String nombre, String apellidos, String DNI, String email, int telefono, Date fechaContratacion) {
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
        return "Trabajador{" +
                "nombre='" + getNombre() + '\'' +
                ", apellidos='" + getApellidos() + '\'' +
                ", DNI='" + getDNI() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", telefono=" + getTelefono() +
                ", fechaContratacion=" + fechaContratacion +
                '}';
    }
}

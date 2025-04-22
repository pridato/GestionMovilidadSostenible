package es.uned.model.personas;

import java.util.Date;
import java.util.List;

/**
 * Clase que representa un administrador, que hereda de la clase Trabajador.
 */
public class Administrador extends Trabajador{
    private List<String> permisos;

    public Administrador(String nombre, String apellidos, String DNI, String email, int telefono, Date fechaContratacion, List<String> permisos) {
        super(nombre, apellidos, DNI, email, telefono, fechaContratacion);
        this.permisos = permisos;
    }

    public List<String> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<String> permisos) {
        this.permisos = permisos;
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "nombre='" + getNombre() + '\'' +
                ", apellidos='" + getApellidos() + '\'' +
                ", DNI='" + getDNI() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", telefono=" + getTelefono() +
                ", fechaContratacion=" + getFechaContratacion() +
                ", permisos=" + permisos +
                '}';
    }
}

package es.uned.model.personas;

import es.uned.model.Base;

import java.util.Date;
import java.util.List;

/**
 * Clase que representa un trabajador de mantenimiento, que hereda de la clase Trabajador.
 */
public class Mantenimiento extends Trabajador{

    private List<Base> basesAsignadas;


    public Mantenimiento(String nombre, String apellidos, String DNI, String email, int telefono, Date fechaContratacion, List<Base> basesAsignadas) {
        super(nombre, apellidos, DNI, email, telefono, fechaContratacion);
        this.basesAsignadas = basesAsignadas;
    }

    public List<Base> getBasesAsignadas() {
        return basesAsignadas;
    }

    public void setBasesAsignadas(List<Base> basesAsignadas) {
        this.basesAsignadas = basesAsignadas;
    }

    @Override
    public String toString() {
        return "Mantenimiento{" +
                "nombre='" + getNombre() + '\'' +
                ", apellidos='" + getApellidos() + '\'' +
                ", DNI='" + getDNI() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", telefono=" + getTelefono() +
                ", fechaContratacion=" + getFechaContratacion() +
                ", basesAsignadas=" + basesAsignadas +
                '}';
    }
}

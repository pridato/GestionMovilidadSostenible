package es.uned.model.personas;

import es.uned.model.Base;

import java.util.Date;
import java.util.List;

/**
 * Clase que representa un trabajador de mantenimiento, que hereda de la clase Trabajador.
 */
public class Mantenimiento extends Trabajador{

    private List<Base> basesAsignadas;


    public Mantenimiento(String DNI, String nombre, String apellidos, String email, int telefono, Date fechaContratacion, List<Base> basesAsignadas) {
        super(DNI, nombre, apellidos, email, telefono, fechaContratacion);
        this.basesAsignadas = basesAsignadas;
    }

    public List<Base> getBasesAsignadas() {
        return basesAsignadas;
    }

    public void setBasesAsignadas(List<Base> basesAsignadas) {
        this.basesAsignadas = basesAsignadas;
    }

    /**
     * Método para asignar una base al trabajador de mantenimiento.
     * @param base base a asignar
     * @return true si se ha asignado correctamente, false en caso contrario
     */
    public boolean asignarBase(Base base) {
        if (base != null) {
            this.basesAsignadas.add(base);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        final String INDENT = "    ";
        StringBuilder sb = new StringBuilder();
        sb.append("Mantenimiento {\n");
        sb.append(super.toString());  // Llama al toString() de la clase padre Trabajador
        sb.append(INDENT).append("Bases asignadas:\n");

        if (basesAsignadas != null && !basesAsignadas.isEmpty()) {
            for (Base base : basesAsignadas) {
                sb.append(INDENT).append(INDENT).append("- ").append(base).append("\n");
            }
        } else {
            sb.append(INDENT).append(INDENT).append("(Sin bases asignadas)\n");
        }

        sb.append("}");
        return sb.toString();
    }

}

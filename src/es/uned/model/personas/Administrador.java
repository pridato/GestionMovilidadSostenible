package es.uned.model.personas;

import java.util.Date;
import java.util.List;

/**
 * Clase que representa un administrador, que hereda de la clase Trabajador.
 */
public class Administrador extends Trabajador {
    private List<String> permisos;

    public Administrador(String DNI, String nombre, String apellidos, String email, int telefono, Date fechaContratacion, List<String> permisos) {
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
        final String INDENT = "    ";
        StringBuilder sb = new StringBuilder();
        sb.append("Administrador {\n");
        sb.append(super.toString());
        sb.append(INDENT).append("Permisos asignados:\n");

        if (permisos != null && !permisos.isEmpty()) {
            for (String permiso : permisos) {
                sb.append(INDENT).append(INDENT).append("- ").append(permiso).append("\n");
            }
        } else {
            sb.append(INDENT).append(INDENT).append("(Sin permisos asignados)\n");
        }

        sb.append("}");
        return sb.toString();
    }

}

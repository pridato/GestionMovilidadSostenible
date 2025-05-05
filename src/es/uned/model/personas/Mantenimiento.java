package es.uned.model.personas;

import es.uned.model.Base;
import es.uned.model.vehiculos.Vehiculo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase que representa un trabajador de mantenimiento, que hereda de la clase Trabajador.
 */
public class Mantenimiento extends Trabajador{

    private List<Vehiculo> vehiculosAsignados;


    public Mantenimiento(String DNI, String nombre, String apellidos, String email, int telefono, Date fechaContratacion) {
        super(DNI, nombre, apellidos, email, telefono, fechaContratacion);
        this.vehiculosAsignados = new ArrayList<>();
    }



    /**
     * Método para asignar un vehículo al trabajador de mantenimiento.
     * @param vehiculo vehículo a asignar
     */
    public void asignarVehiculo(Vehiculo vehiculo) {
        if (vehiculo != null) {
            this.vehiculosAsignados.add(vehiculo);
        }
    }


    @Override
    public String toString() {
        final String INDENT = "    ";
        StringBuilder sb = new StringBuilder();
        sb.append("Mantenimiento {\n");
        sb.append(super.toString());  // Llama al toString() de la clase padre Trabajador
        sb.append(INDENT).append("Bases asignadas:\n");

        if (vehiculosAsignados != null && !vehiculosAsignados.isEmpty()) {
            sb.append(INDENT).append("Vehículos asignados:\n");
            for (Vehiculo vehiculo : vehiculosAsignados) {
                sb.append(INDENT).append(INDENT).append("- ").append(vehiculo).append("\n");
            }
        } else {
            sb.append(INDENT).append("Vehículos asignados: (Sin vehículos asignados)\n");
        }

        sb.append("}");
        return sb.toString();
    }

    public List<Vehiculo> getVehiculosAsignados() {
        return vehiculosAsignados;
    }

    public void setVehiculosAsignados(List<Vehiculo> vehiculosAsignados) {
        this.vehiculosAsignados = vehiculosAsignados;
    }
}

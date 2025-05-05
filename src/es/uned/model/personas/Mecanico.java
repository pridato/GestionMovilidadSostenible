package es.uned.model.personas;

import es.uned.model.Factura;
import es.uned.model.vehiculos.Vehiculo;

import java.util.Date;
import java.util.List;

/**
 * Clase que representa un mecánico, que hereda de la clase Trabajador.
 */
public class Mecanico extends Trabajador{

    private List<Vehiculo> vehiculosAsignados;
    private List<Factura> facturasEmitidas;

    public Mecanico(String DNI, String nombre, String apellidos, String email, int telefono, Date fechaContratacion, List<Vehiculo> vehiculosAsignados, List<Factura> facturasEmitidas) {
        super(DNI, nombre, apellidos, email, telefono, fechaContratacion);
        this.vehiculosAsignados = vehiculosAsignados;
        this.facturasEmitidas = facturasEmitidas;
    }

    public List<Vehiculo> getVehiculosAsignados() {
        return vehiculosAsignados;
    }

    public void setVehiculosAsignados(List<Vehiculo> vehiculosAsignados) {
        this.vehiculosAsignados = vehiculosAsignados;
    }

    /**
     * Método para asignar un vehículo al mecánico.
     * @param vehiculo vehículo a asignar
     * @return true si se ha asignado correctamente, false en caso contrario
     */
    public boolean asignarVehiculo(Vehiculo vehiculo) {
        if (vehiculo != null) {
            this.vehiculosAsignados.add(vehiculo);
            return true;
        }
        return false;
    }

    public List<Factura> getFacturasEmitidas() {
        return facturasEmitidas;
    }

    public void setFacturasEmitidas(List<Factura> facturasEmitidas) {
        this.facturasEmitidas = facturasEmitidas;
    }

    @Override
    public String toString() {
        final String INDENT = "    ";
        StringBuilder sb = new StringBuilder();
        sb.append("Mecanico {\n");
        sb.append(super.toString());  // Llama al toString() de la clase base Trabajador
        sb.append(INDENT).append("Fecha de contratación: ").append(getFechaContratacion()).append("\n");
        sb.append(INDENT).append("Vehículos asignados:\n");

        if (vehiculosAsignados != null && !vehiculosAsignados.isEmpty()) {
            for (Vehiculo vehiculo : vehiculosAsignados) {
                sb.append(INDENT).append(INDENT).append("- ").append(vehiculo).append("\n");
            }
        } else {
            sb.append(INDENT).append(INDENT).append("(Sin vehículos asignados)\n");
        }

        sb.append(INDENT).append("Facturas emitidas:\n");

        if (facturasEmitidas != null && !facturasEmitidas.isEmpty()) {
            for (Factura factura : facturasEmitidas) {
                sb.append(INDENT).append(INDENT).append("- ").append(factura).append("\n");
            }
        } else {
            sb.append(INDENT).append(INDENT).append("(Sin facturas emitidas)\n");
        }

        sb.append("}");
        return sb.toString();
    }

}

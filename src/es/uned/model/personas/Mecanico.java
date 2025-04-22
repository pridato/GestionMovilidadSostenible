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

    public Mecanico(String nombre, String apellidos, String DNI, String email, int telefono, Date fechaContratacion, List<Vehiculo> vehiculosAsignados, List<Factura> facturasEmitidas) {
        super(nombre, apellidos, DNI, email, telefono, fechaContratacion);
        this.vehiculosAsignados = vehiculosAsignados;
        this.facturasEmitidas = facturasEmitidas;
    }

    public List<Vehiculo> getVehiculosAsignados() {
        return vehiculosAsignados;
    }

    public void setVehiculosAsignados(List<Vehiculo> vehiculosAsignados) {
        this.vehiculosAsignados = vehiculosAsignados;
    }

    public List<Factura> getFacturasEmitidas() {
        return facturasEmitidas;
    }

    public void setFacturasEmitidas(List<Factura> facturasEmitidas) {
        this.facturasEmitidas = facturasEmitidas;
    }

    @Override
    public String toString() {
        return "Mecanico{" +
                "nombre='" + getNombre() + '\'' +
                ", apellidos='" + getApellidos() + '\'' +
                ", DNI='" + getDNI() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", telefono=" + getTelefono() +
                ", fechaContratacion=" + getFechaContratacion() +
                ", vehiculosAsignados=" + vehiculosAsignados +
                ", facturasEmitidas=" + facturasEmitidas +
                '}';
    }
}

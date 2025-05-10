package es.uned.model;

import es.uned.model.personas.Mecanico;
import es.uned.model.vehiculos.Vehiculo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que representa una factura, que contiene información sobre el vehículo, el mecánico, el importe, la descripción y la fecha.
 */
public class Factura {

    private final String id;
    private Vehiculo vehiculo;
    private Base base;
    private final Mecanico mecanico;
    private final double importe;
    private final String descripcion;
    private final Date fecha;

    public Factura(double importe, String descripcion, Mecanico mecanico) {
        this.id = String.valueOf(System.currentTimeMillis());
        this.importe = importe;
        this.descripcion = descripcion;
        this.fecha = new Date();
        this.mecanico = mecanico;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getId() {
        return id;
    }

    public Mecanico getMecanico() {
        return mecanico;
    }


    public String getDescripcion() {
        return descripcion;
    }


    public Date getFecha() {
        return fecha;
    }

    public double getImporte() {
        return importe;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    /**
     * Método para mostrar la factura en la consola.
     */
    public void mostrarFactura() {
        System.out.println("ID: " + id + ", Importe: " + importe +
                "€ , Descripción: " + descripcion + ", Fecha: " + fecha);
    }


    @Override
    public String toString() {
        final String INDENT = "    ";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return "Factura {\n" +
                INDENT + "ID: " + id + "\n" +
                INDENT + "Vehículo: " + (vehiculo != null ? vehiculo.toString() : "No asignado") + "\n" +
                INDENT + "Mecánico: " + (mecanico != null ? mecanico.toString() : "No asignado") + "\n" +
                INDENT + "Importe: €" + importe + "\n" +
                INDENT + "Descripción: " + descripcion + "\n" +
                INDENT + "Fecha: " + (fecha != null ? dateFormat.format(fecha) : "No disponible") + "\n" +
                "}";
    }
}

package es.uned.model;

import es.uned.model.personas.Mecanico;
import es.uned.model.vehiculos.Vehiculo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que representa una factura, que contiene información sobre el vehículo, el mecánico, el importe, la descripción y la fecha.
 */
public class Factura {

    private String id;
    private Vehiculo vehiculo;
    private Mecanico mecanico;
    private double importe;
    private String descripcion;
    private Date fecha;

    public Factura(String id, Vehiculo vehiculo, Mecanico mecanico, double importe, String descripcion, Date fecha) {
        this.id = id;
        this.vehiculo = vehiculo;
        this.mecanico = mecanico;
        this.importe = importe;
        this.descripcion = descripcion;
        this.fecha = fecha;
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

    public void setId(String id) {
        this.id = id;
    }

    public Mecanico getMecanico() {
        return mecanico;
    }

    public void setMecanico(Mecanico mecanico) {
        this.mecanico = mecanico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
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

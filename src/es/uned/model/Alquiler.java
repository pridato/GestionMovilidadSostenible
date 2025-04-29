package es.uned.model;

import es.uned.enums.EstadoAlquiler;
import es.uned.model.vehiculos.Vehiculo;

import java.util.Date;
import java.util.UUID;

/**
 * Clase que representa un alquiler de un vehículo.
 */
public class Alquiler {

    private String id;
    private Vehiculo vehiculo;
    private Date fechaInicio;
    private Date fechaFin;
    private EstadoAlquiler estado;
    private Base baseInicio;
    private Base baseFin;
    private double importeFinal;
    private int tiempoDuracion;

    /* Constructor de la clase Alquiler, con todos los datos */
    public Alquiler(Vehiculo vehiculo, Date fechaInicio, Date fechaFin, EstadoAlquiler estado, Base baseInicio, Base baseFin, double importeFinal, int tiempoDuracion) {
        this.id = UUID.randomUUID().toString();
        this.vehiculo = vehiculo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.baseInicio = baseInicio;
        this.baseFin = baseFin;
        this.importeFinal = importeFinal;
        this.tiempoDuracion = tiempoDuracion;
    }

    /* constructor por defecto para inicializar cualquier alquiler*/
    public Alquiler(Vehiculo vehiculo, Date fechaInicio, EstadoAlquiler estado, Base baseInicio) {
        this.id = UUID.randomUUID().toString();
        this.vehiculo = vehiculo;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
        this.baseInicio = baseInicio;
    }

    /* constructor para reservar un vehículo a través de un alquiler */
    public Alquiler(Vehiculo vehiculo, Date fechaInicio, EstadoAlquiler estado) {
        this.id = UUID.randomUUID().toString();
        this.vehiculo = vehiculo;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoAlquiler getEstado() {
        return estado;
    }

    public void setEstado(EstadoAlquiler estado) {
        this.estado = estado;
    }

    public Base getBaseInicio() {
        return baseInicio;
    }

    public void setBaseInicio(Base baseInicio) {
        this.baseInicio = baseInicio;
    }

    public Base getBaseFin() {
        return baseFin;
    }

    public void setBaseFin(Base baseFin) {
        this.baseFin = baseFin;
    }

    public double getImporteFinal() {
        return importeFinal;
    }

    public void setImporteFinal(double importeFinal) {
        this.importeFinal = importeFinal;
    }

    public int getTiempoDuracion() {
        return tiempoDuracion;
    }

    public void setTiempoDuracion(int tiempoDuracion) {
        this.tiempoDuracion = tiempoDuracion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Alquiler {\n");
        sb.append("    ID: ").append(id).append("\n");
        sb.append("    Vehículo: ").append(vehiculo != null ? vehiculo.toString() : "No disponible").append("\n");
        sb.append("    Fecha de Inicio: ").append(fechaInicio != null ? fechaInicio.toString() : "No disponible").append("\n");
        sb.append("    Fecha de Fin: ").append(fechaFin != null ? fechaFin.toString() : "No disponible").append("\n");
        sb.append("    Estado: ").append(estado != null ? estado.toString() : "No disponible").append("\n");
        sb.append("    Base de Inicio: ").append(baseInicio != null ? baseInicio.toString() : "No disponible").append("\n");
        sb.append("    Base de Fin: ").append(baseFin != null ? baseFin.toString() : "No disponible").append("\n");
        sb.append("    Importe Final: ").append(importeFinal).append("\n");
        sb.append("    Duración (en horas): ").append(tiempoDuracion).append("\n");
        sb.append("}");
        return sb.toString();
    }
}

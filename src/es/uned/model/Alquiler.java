package es.uned.model;

import es.uned.enums.EstadoAlquiler;
import es.uned.model.vehiculos.Vehiculo;

import java.util.Date;
import java.util.UUID;

/**
 * Clase que representa un alquiler de un vehículo.
 */
public class Alquiler {

    private final String id;
    private Vehiculo vehiculo;
    private final Date fechaInicio;
    private Date fechaFin;
    private EstadoAlquiler estado;
    private Base baseInicio;
    private Base baseFin;
    private double importeFinal;
    private int tiempoDuracion;
    private Coordenadas coordenadasInicio;
    private Coordenadas coordenadasFin;


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

    public Alquiler(Vehiculo vehiculo, Date fechaInicio, EstadoAlquiler estado, Base baseInicio, Coordenadas coordenadas) {
        this.id = UUID.randomUUID().toString();
        this.vehiculo = vehiculo;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
        this.baseInicio = baseInicio;
        this.coordenadasInicio = coordenadas;
    }

    /* constructor con coordenadas */
    public Alquiler(Vehiculo vehiculo, Date fechaInicio, EstadoAlquiler estado, Coordenadas coordenadasInicio) {
        this.id = UUID.randomUUID().toString();
        this.vehiculo = vehiculo;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
        this.coordenadasInicio = coordenadasInicio;
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

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
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

    public void comprobarAlquilerCurso() {
        if(!estado.equals(EstadoAlquiler.EN_CURSO)) {
            throw new IllegalStateException("El alquiler no está en curso");
        }
    }

    public void setTiempoDuracion(int tiempoDuracion) {
        this.tiempoDuracion = tiempoDuracion;
    }

    public Coordenadas getCoordenadasFin() {
        return coordenadasFin;
    }

    public void setCoordenadasFin(Coordenadas coordenadasFin) {
        this.coordenadasFin = coordenadasFin;
    }

    @Override
    public String toString() {
        return "Alquiler {\n" +
                "    ID: " + id + "\n" +
                "    Vehículo: " + (vehiculo != null ? vehiculo.toString() : "No disponible") + "\n" +
                "    Fecha de Inicio: " + (fechaInicio != null ? fechaInicio.toString() : "No disponible") + "\n" +
                "    Fecha de Fin: " + (fechaFin != null ? fechaFin.toString() : "No disponible") + "\n" +
                "    Estado: " + (estado != null ? estado.toString() : "No disponible") + "\n" +
                "    Base de Inicio: " + (baseInicio != null ? baseInicio.toString() : "No disponible") + "\n" +
                "    Base de Fin: " + (baseFin != null ? baseFin.toString() : "No disponible") + "\n" +
                "    Importe Final: " + importeFinal + "\n" +
                "    Duración (en minutos): " + tiempoDuracion + "\n" +
                "    Coordenadas de Inicio: " + (coordenadasInicio != null ? coordenadasInicio.toString() : "No disponible") + "\n" +
                "    Coordenadas de Fin: " + (coordenadasFin != null ? coordenadasFin.toString() : "No disponible") + "\n" +
                "}";
    }
}

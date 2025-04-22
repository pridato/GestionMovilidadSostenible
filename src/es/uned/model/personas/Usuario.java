package es.uned.model.personas;

import es.uned.model.Alquiler;
import es.uned.model.Coordenadas;

import java.util.Date;
import java.util.List;

/**
 * Clase Usuario que representa a un usuario del sistema.
 */
public class Usuario extends Persona {

    private int sueldo;
    private Boolean esPremium;
    private List<Alquiler> historialViajes;
    private Date fechaCreacion;
    private Coordenadas coordenadas;
    private double descuento;

    public Usuario(String nombre, String apellidos, String dni, String email, int telefono, int sueldo, Boolean esPremium, List<Alquiler> historialViajes, Date fechaCreacion, Coordenadas coordenadas) {
        super(nombre, apellidos, dni, email, telefono);
        this.sueldo = sueldo;
        this.esPremium = esPremium;
        this.historialViajes = historialViajes;
        this.fechaCreacion = fechaCreacion;
        this.coordenadas = coordenadas;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Coordenadas coordenadas) {
        this.coordenadas = coordenadas;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<Alquiler> getHistorialViajes() {
        return historialViajes;
    }

    public void setHistorialViajes(List<Alquiler> historialViajes) {
        this.historialViajes = historialViajes;
    }

    public Boolean getEsPremium() {
        return esPremium;
    }

    public void setEsPremium(Boolean esPremium) {
        this.esPremium = esPremium;
    }

    public int getSueldo() {
        return sueldo;
    }

    public void setSueldo(int sueldo) {
        this.sueldo = sueldo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "sueldo=" + sueldo +
                ", esPremium=" + esPremium +
                ", historialViajes=" + historialViajes +
                ", fechaCreacion=" + fechaCreacion +
                ", coordenadas=" + coordenadas +
                ", descuento=" + descuento +
                '}';
    }
}

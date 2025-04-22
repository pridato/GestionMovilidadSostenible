package es.uned.model.personas;

import es.uned.model.Alquiler;
import es.uned.model.Coordenadas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase Usuario que representa a un usuario del sistema.
 */
public class Usuario extends Persona {

    private double sueldo;
    private Boolean esPremium;
    private List<Alquiler> historialViajes = new ArrayList<>();
    private Date fechaCreacion;
    private Coordenadas coordenadas;
    private double descuento;

    public Usuario(String dni, String nombre, String apellidos, String email, int telefono, Date fechaCreacion, Coordenadas coordenadas) {
        super(nombre, apellidos, dni, email, telefono);
        this.sueldo = 0.0;
        this.esPremium = false;
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

    public double getSueldo() {
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

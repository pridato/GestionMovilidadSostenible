package es.uned.model.personas;

import es.uned.model.Alquiler;
import es.uned.model.Coordenadas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static es.uned.utils.GeolocalizacionPorIP.obtenerCoordenadasDesdeIP;

/**
 * Clase Usuario que representa a un usuario del sistema.
 */
public class Usuario extends Persona {

    private double saldo;
    private Boolean esPremium;
    private List<Alquiler> historialViajes = new ArrayList<>();
    private Date fechaCreacion;
    private Coordenadas coordenadas;
    private double descuento;

    public Usuario(String dni, String nombre, String apellidos, String email, int telefono, Date fechaCreacion, Coordenadas coordenadas) {
        super(nombre, apellidos, dni, email, telefono);
        this.saldo = 0.0;
        this.esPremium = false;
        this.fechaCreacion = fechaCreacion;
        this.coordenadas = coordenadas;
    }

    public Usuario(String dni, String nombre, String apellidos, String email, int telefono, Coordenadas coordenadas) {
        super(dni, nombre, apellidos, email, telefono);
        this.saldo = 0.0;
        this.esPremium = false;
        this.fechaCreacion = new Date();
        this.coordenadas = coordenadas;
    }

    public Usuario(String dni, String nombre, String apellidos, String email, int telefono,Date fechaCreacion, Coordenadas coordenadas, double saldo, Boolean esPremium, List<Alquiler> historialViajes) {
        super(dni, nombre, apellidos, email, telefono);
        this.saldo = saldo;
        this.esPremium = esPremium;
        this.fechaCreacion = fechaCreacion;
        this.coordenadas = coordenadas;
        this.historialViajes = historialViajes;
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

    public double getsaldo() {
        return saldo;
    }

    public void recargarSaldo(double saldo) {
        this.saldo += saldo;
    }

    @Override
    public String toString() {
        final String INDENT = "    ";
        StringBuilder sb = new StringBuilder();

        sb.append("Usuario {\n");
        sb.append(super.toString());
        sb.append(INDENT).append("Fecha de creación: ").append(fechaCreacion).append("\n");
        sb.append(INDENT).append("saldo: ").append(String.format("%.2f", saldo)).append(" €\n");
        sb.append(INDENT).append("¿Premium?: ").append(esPremium != null && esPremium ? "Sí" : "No").append("\n");
        sb.append(INDENT).append("Descuento aplicado: ").append(String.format("%.2f", descuento)).append(" %\n");
        sb.append(INDENT).append("Coordenadas: ").append(coordenadas != null ? coordenadas : "(No especificadas)").append("\n");
        sb.append(INDENT).append("Historial de viajes:\n");

        if (historialViajes != null && !historialViajes.isEmpty()) {
            for (Alquiler alquiler : historialViajes) {
                sb.append(INDENT).append(INDENT).append("- ").append(alquiler.getId()).append(" (")
                        .append(alquiler.getFechaInicio()).append(" → ").append(alquiler.getFechaFin()).append(")\n");
            }
        } else {
            sb.append(INDENT).append(INDENT).append("(Sin viajes registrados)\n");
        }

        sb.append("}");
        return sb.toString();
    }

}

package es.uned.model.personas;

import es.uned.model.Alquiler;
import es.uned.model.Coordenadas;

import java.util.ArrayList;
import java.util.Collections;
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
    private final Date fechaCreacion;
    private Coordenadas coordenadas;
    private double descuento;

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

    public Usuario(String dni, String nombre, String apellidos, String email, int telefono,Date fechaCreacion, Coordenadas coordenadas, double saldo) {
        super(dni, nombre, apellidos, email, telefono);
        this.saldo = saldo;
        this.esPremium = false;
        this.fechaCreacion = fechaCreacion;
        this.coordenadas = coordenadas;
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

    public List<Alquiler> getHistorialViajes() {
        return Collections.unmodifiableList(historialViajes);
    }

    /**
     * Añade un nuevo alquiler al historial de viajes del usuario.
     * @param alquiler Alquiler a añadir.
     */
    public void añadirAlquiler(Alquiler alquiler) {
            historialViajes.add(alquiler);
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

    /**
     * Método para verificar si tiene saldo suficiente para iniciar un alquiler.
     */
    public void verificarSaldo() {
        if (saldo <= 0) {
            throw new IllegalStateException("Saldo insuficiente para iniciar el alquiler.");
        }
    }

    /**
     * Consulta y muestra los alquileres realizados por el usuario.
     */
    public void consultarAlquileresUsuario() {
        if (historialViajes.isEmpty()) {
            System.out.println("No tienes ningún alquiler.");
            return;
        }

        System.out.println("Alquileres del usuario " + getNombre() + ":");
        for (Alquiler alquiler : historialViajes) {
           alquiler.mostrarAlquiler();
        }
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

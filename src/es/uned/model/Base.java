package es.uned.model;

import es.uned.model.vehiculos.Vehiculo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase que representa una base de vehículos.
 */
public class Base {

    private final String id;
    private Coordenadas coordenadas;
    private final int capacidadMaxima;
    private final List<Vehiculo> vehiculos;
    private boolean averiada = false;
    private int tiempoInactividad;

    public Base(String id, Coordenadas coordenadas, int capacidadMaxima) {
        this.id = id;
        this.coordenadas = coordenadas;
        this.capacidadMaxima = capacidadMaxima;
        this.vehiculos = new ArrayList<>();
    }

    public String getId() {
        return id;
    }


    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Coordenadas coordenadas) {
        this.coordenadas = coordenadas;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public List<Vehiculo> getVehiculos() {
        // evitar modificaciones externas
        return Collections.unmodifiableList(vehiculos);
    }

    public int getTiempoInactividad() {
        return tiempoInactividad;
    }

    public void setTiempoInactividad(int tiempoInactividad) {
        this.tiempoInactividad = tiempoInactividad;
    }


    /**
     * Añade un vehículo a la base.
     * @param vehiculo Vehículo a añadir.
     */
    public void añadirVehiculo(Vehiculo vehiculo) {
        if (vehiculos.size() < capacidadMaxima) {
            vehiculos.add(vehiculo);
        } else {
            System.out.println("No se puede añadir el vehículo. Capacidad máxima alcanzada.");
        }
    }

    public boolean isAveriada() {
        return averiada;
    }

    public void setAveriada(boolean averiada) {
        this.averiada = averiada;
    }

    @Override
    public String toString() {
        final String INDENT = "    ";
        StringBuilder sb = new StringBuilder();

        sb.append("Base:\n")
                .append(INDENT).append("ID: ").append(id).append("\n")
                .append(INDENT).append("Coordenadas: ")
                .append(coordenadas != null ? coordenadas.toString() : "No disponibles").append("\n")
                .append(INDENT).append("Capacidad Máxima: ").append(capacidadMaxima).append("\n")
                .append(INDENT).append("Vehículos Alquilados: ").append(vehiculos.size()).append("\n");

        if (tiempoInactividad > 0) {
            sb.append(INDENT).append("Tiempo de Inactividad: ").append(tiempoInactividad).append(" minutos\n");
        }

        return sb.toString();
    }

}

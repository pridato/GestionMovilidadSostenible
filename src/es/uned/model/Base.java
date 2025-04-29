package es.uned.model;

import java.util.List;

/**
 * Clase que representa una base de vehículos.
 */
public class Base {

    private String id;
    private Coordenadas coordenadas;
    private int capacidadMaxima;
    private List<Alquiler> vehiculosAlquilados;
    private boolean averiada = false;

    public Base(String id, Coordenadas coordenadas, int capacidadMaxima) {
        this.id = id;
        this.coordenadas = coordenadas;
        this.capacidadMaxima = capacidadMaxima;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public List<Alquiler> getVehiculosAlquilados() {
        return vehiculosAlquilados;
    }

    public void setVehiculosAlquilados(List<Alquiler> vehiculosAlquilados) {
        this.vehiculosAlquilados = vehiculosAlquilados;
    }

    public boolean isAveriada() {
        return averiada;
    }

    public void setAveriada(boolean averiada) {
        this.averiada = averiada;
    }

    @Override
    public String toString() {
        return "Base {\n" +
                "    ID: " + id + "\n" +
                "    Coordenadas: " + (coordenadas != null ? coordenadas.toString() : "No disponibles") + "\n" +
                "    Capacidad Máxima: " + capacidadMaxima + "\n" +
                "    Vehículos Alquilados: " + (vehiculosAlquilados != null ? vehiculosAlquilados.toString() : "No disponibles") + "\n" +
                "}";
    }
}

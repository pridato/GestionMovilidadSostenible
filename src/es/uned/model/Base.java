package es.uned.model;

import es.uned.model.vehiculos.Vehiculo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una base de vehículos.
 */
public class Base {

    private String id;
    private Coordenadas coordenadas;
    private int capacidadMaxima;
    private List<Vehiculo> vehiculos;
    private boolean averiada = false;

    public Base(String id, Coordenadas coordenadas, int capacidadMaxima) {
        this.id = id;
        this.coordenadas = coordenadas;
        this.capacidadMaxima = capacidadMaxima;
        this.vehiculos = new ArrayList<>();
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

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculosAlquilados(List<Vehiculo> vehiculosAlquilados) {
        this.vehiculos = vehiculosAlquilados;
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
        return "Base {\n" +
                "    ID: " + id + "\n" +
                "    Coordenadas: " + (coordenadas != null ? coordenadas.toString() : "No disponibles") + "\n" +
                "    Capacidad Máxima: " + capacidadMaxima + "\n" +
                "    Vehículos Alquilados: " + (vehiculos != null ? vehiculos.toString() : "No disponibles") + "\n" +
                "}";
    }
}

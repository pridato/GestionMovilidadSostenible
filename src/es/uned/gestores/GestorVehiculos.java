package es.uned.gestores;

import es.uned.enums.EstadoVehiculo;
import es.uned.model.Base;
import es.uned.model.Incidencia;
import es.uned.model.vehiculos.Vehiculo;

import java.util.List;
import java.util.Scanner;

import static es.uned.utils.dto.*;

/**
 * Clase GestorVehiculos que gestiona la lista de vehículos.
 *
 * Métodos:
 *
 * Dar de alta un vehículo
 *
 * Borrar/modificar un vehículo
 *
 * Marcar un vehículo como averiado
 *
 * Marcar un vehículo como alquilado
 *
 * Marcar un vehículo como disponible
 */
public class GestorVehiculos {

    // Atributos
    private final List<Vehiculo> vehiculos;

    // Constructor
    public GestorVehiculos() {
        this.vehiculos = cargarVehiculos();
    }

    /**
     * Método para añadir un vehículo a la lista de vehículos.
     * @param vehiculo Vehiculo a añadir.
     * @return true si se añade correctamente, false si ya existe o es null.
     */
    public boolean añadirVehiculo(Vehiculo vehiculo) {
        if (vehiculo != null && !this.vehiculos.contains(vehiculo)) {
            this.vehiculos.add(vehiculo);
            return true;
        }
        return false;
    }

    /**
     * Método para eliminar un vehículo de la lista de vehículos.
     * @param vehiculo Vehiculo a eliminar.
     * @return true si se elimina correctamente, false si no existe o es null.
     */
    public boolean eliminarVehiculo(Vehiculo vehiculo) {
        if (vehiculo != null && this.vehiculos.contains(vehiculo)) {
            this.vehiculos.remove(vehiculo);
            return true;
        }
        return false;
    }

    /**
     * Método para obtener la lista de vehículos.
     * @param matricula matricula del vehículo a buscar
     * @return Lista de vehículos.
     */
    public void modificarVehiculo(String matricula) {
        //TODO: Implementar la modificación de un vehículo.
    }

    /**
     * Método para marcar un vehículo como averiado.
     * @param matricula matricula del vehículo a buscar
     * @return true si se marca correctamente, false si no existe o es null.
     */
    public boolean marcarVehiculoComoAveriado(String matricula) {
        Vehiculo vehiculo = obtenerVehiculo(matricula);
        if (vehiculo != null) {
            vehiculo.setEstado(EstadoVehiculo.AVERIADO);
            return true;
        }
        return false;
    }

    /**
     * Método para marcar un vehículo como reparado.
     * @param matricula matricula del vehículo a buscar
     * @return true si se marca correctamente, false si no existe o es null.
     */
    public boolean marcarVehiculoComoDisponible(String matricula) {
        Vehiculo vehiculo = obtenerVehiculo(matricula);
        if (vehiculo != null) {
            vehiculo.setEstado(EstadoVehiculo.DISPONIBLE);
            return true;
        }
        return false;
    }

    /**
     * Método para marcar un vehículo como alquilado.
     * @param matricula matricula del vehículo a buscar
     * @return true si se marca correctamente, false si no existe o es null.
     */
    public boolean marcarVehiculoComoAlquilado(String matricula) {
        Vehiculo vehiculo = obtenerVehiculo(matricula);
        if (vehiculo != null) {
            vehiculo.setEstado(EstadoVehiculo.ALQUILADO);
            return true;
        }
        return false;
    }

    /**
     * Método para obtener un vehículo de la lista de vehículos.
     * @param matricula matricula del vehículo a buscar
     * @return El vehículo encontrado o null si no se encuentra.
     */
    public Vehiculo obtenerVehiculo(String matricula) {
        return this.vehiculos.stream()
                .filter(vehiculo -> vehiculo.getMatricula().equals(matricula))
                .findFirst()
                .orElse(null);
    }

}

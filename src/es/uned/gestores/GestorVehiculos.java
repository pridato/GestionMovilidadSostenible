package es.uned.gestores;

import es.uned.model.Base;
import es.uned.model.Incidencia;
import es.uned.model.vehiculos.Vehiculo;

import java.util.List;
import java.util.Scanner;

import static es.uned.utils.dto.*;

/**
 * Clase GestorVehiculos que gestiona la lista de vehículos.
 */
public class GestorVehiculos {

    // Atributos
    private List<Vehiculo> vehiculos;
    private List<Incidencia> incidencias;
    private List<Base> bases;

    // Constructor
    public GestorVehiculos() {
        this.vehiculos = cargarVehiculos();
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

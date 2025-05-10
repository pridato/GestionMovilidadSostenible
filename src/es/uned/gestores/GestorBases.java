package es.uned.gestores;

import es.uned.model.Base;
import es.uned.model.Coordenadas;
import es.uned.model.vehiculos.Vehiculo;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static es.uned.gestores.GestorAlquileres.obtenerBaseParaAlquilar;
import static es.uned.utils.dto.cargarBases;

/**
 * Clase GestorBases.
 * Esta clase se encarga de gestionar las bases de vehículos.
 */
public class GestorBases {

    private static final GestorBases instancia = new GestorBases();
    private final List<Base> bases;


    /* Constructor de la clase GestorBases. */
    public GestorBases() {
        this.bases = cargarBases();
    }

    /**
     * Método para obtener la instancia del gestor de bases.
     *
     * @return instancia del gestor de bases.
     */
    public static GestorBases getInstancia() {
        return instancia;
    }


    /**
     * Método para consultar el estado de las bases.
     */
    public void consultarEstadoBases() {
        System.out.println("Estado de las bases:");
        bases.forEach(Base::mostrarInfoBaseDetallada);
    }

    /**
     * Método para consultar bases por disponibilidad.
     */
    public void consultarBasesDisponibles() {
        System.out.println("Bases disponibles:");
        bases.stream().filter(base -> !base.isAveriada() && base.getVehiculos().size() < base.getCapacidadMaxima())
                .forEach(Base::mostrarInfoBaseDetallada);
    }

    /**
     * Método para consultar bases por ocupación.
     */
    public void consultarBasesPorOcupacion() {
        bases.stream()
                .filter(base -> !base.isAveriada())
                .sorted(Comparator.comparingInt(base -> base.getVehiculos().size()))
                .forEach(base -> System.out.println("ID: " + base.getId() + ", Ocupación: " + base.getVehiculos().size() + "/" + base.getCapacidadMaxima()));
    }

    /**
     * Generar estadisticas de las bases
     */
    public void generarEstadisticasBases() {
        System.out.println("Estadísticas de las bases:");
        bases.stream()
                .filter(base -> !base.isAveriada())
                .sorted(Comparator.comparingInt((Base b) -> b.getVehiculos().size()).reversed())
                .forEach(base -> System.out.println("ID: " + base.getId() + ", Ocupación: " + base.getVehiculos().size() + "/" + base.getCapacidadMaxima()));
    }

    /**
     * Método para obtener una base por su ID.
     *
     * @param id ID de la base a consultar.
     * @return Base correspondiente al ID proporcionado, o null si no se encuentra.
     *
     * @throws IllegalArgumentException si no se encuentra la base con el ID proporcionado.
     */
    public Base consultarBasePorId(String id) throws IllegalArgumentException {
        return bases.stream()
                .filter(base -> Objects.equals(base.getId(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No se ha encontrado la base con ID: " + id));
    }

    /**
     * Método para obtener una base de un vehículo.
     *
     * @param matricula Matrícula del vehículo a consultar.
     * @return Base correspondiente al vehículo, o null si no se encuentra.
     */
    public Base obtenerBaseVehiculo(String matricula) {
        return bases.stream()
                .filter(base -> base.getVehiculos().stream().anyMatch(vehiculo -> Objects.equals(vehiculo.getMatricula(), matricula)))
                .findFirst()
                .orElse(null);
    }

    /**
     * Método para mostrar las bases averiadas.
     */
    public void consultarBasesAveriadas() throws IllegalArgumentException {
        List<Base> averiadas = bases.stream().filter(Base::isAveriada).toList();

        if (averiadas.isEmpty()) {
            System.out.println("No hay bases averiadas.");
            return;
        }

        System.out.println("Bases averiadas:");
        averiadas.forEach(Base::mostrarInfoBaseDetallada);
    }

    /**
     * Método para obtener la base más cercana a unas coordenadas.
     * @param coordenadas Coordenadas a las que se desea encontrar la base más cercana.
     * @return Coordenadas de la base más cercana, o null si no se encuentra ninguna base disponible.
     */
    public Base obtenerBaseMasCercana(Coordenadas coordenadas) {
        return bases.stream()
                .filter(base -> !base.isAveriada() && base.getVehiculos().size() < base.getCapacidadMaxima())
                .min(Comparator.comparingDouble(base -> base.getCoordenadas().Calculardistancia(coordenadas)))
                .orElse(null);
    }

    /**
     * Método para obtener la base con un vehículo disponible, si existe, de lo contrario selecciona una base nueva.
     */
    public Base obtenerBaseConVehiculoDisponible(Vehiculo vehiculo, Scanner scanner) {
        Base base = obtenerBaseVehiculo(vehiculo.getMatricula());
        if (base != null && !base.isAveriada()) {
            System.out.println("Vehículo encontrado en base: " + base.getId());
            return base;
        }
        return obtenerBaseParaAlquilar(scanner);
    }
}

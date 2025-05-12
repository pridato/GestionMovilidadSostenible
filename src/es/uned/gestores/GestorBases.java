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
     * Devuelve la instancia única de la clase GestorBases.
     *
     * @return instancia del gestor de bases.
     */
    public static GestorBases getInstancia() {
        return instancia;
    }


    /**
     * Consulta el estado actual de las bases
     */
    public void consultarEstadoBases() {
        System.out.println("Estado de las bases:");
        bases.forEach(Base::mostrarInfoBaseDetallada);
    }

    /**
     * Consulta las bases disponibles (sin averías y con espacio).
     */
    public void consultarBasesDisponibles() {
        System.out.println("Bases disponibles:");
        bases.stream().filter(base -> !base.isAveriada() && base.getVehiculos().size() < base.getCapacidadMaxima())
                .forEach(Base::mostrarInfoBaseDetallada);
    }

    /**
     * Consulta las bases ordenadas por ocupación (de menor a mayor).
     */
    public void consultarBasesPorOcupacion() {
        bases.stream()
                .filter(base -> !base.isAveriada())
                .sorted(Comparator.comparingInt(base -> base.getVehiculos().size()))
                .forEach(base -> System.out.println("ID: " + base.getId() + ", Ocupación: " + base.getVehiculos().size() + "/" + base.getCapacidadMaxima()));
    }

    /**
     * Genera estadísticas de las bases, mostrando la ocupación de cada una.
     *
     * Realmente es consultarBasesPorOcupacion() pero ordenando de mayor a menor.
     */
    public void generarEstadisticasBases() {
        System.out.println("Estadísticas de las bases:");
        bases.stream()
                .filter(base -> !base.isAveriada())
                .sorted(Comparator.comparingInt((Base b) -> b.getVehiculos().size()).reversed())
                .forEach(base -> System.out.println("ID: " + base.getId() + ", Ocupación: " + base.getVehiculos().size() + "/" + base.getCapacidadMaxima()));
    }

    /**
     * Devuelve una base específica por su ID.
     *
     * @param id  string id de la base a consultar.
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
     * Devuelve una base específica por un vehículo asociado a ella.
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
     * Consultar todas las bases averiadas.
     */
    public void consultarBasesAveriadas() throws IllegalArgumentException {
        // Filtrar las bases averiadas para poder avisar al usuario si no hay ninguna.
        List<Base> averiadas = bases.stream().filter(Base::isAveriada).toList();

        if (averiadas.isEmpty()) {
            System.out.println("No hay bases averiadas.");
            return;
        }

        // mostramos las bases averiadas
        System.out.println("Bases averiadas:");
        averiadas.forEach(Base::mostrarInfoBaseDetallada);
    }

    /**
     * Devuelve la base más cercana a unas coordenadas dadas.
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
     * Devuelve la base asociada a un vehículo específico. Sino se encuentra, hacemos formulario
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

package es.uned.gestores;

import es.uned.model.Base;

import java.util.*;

import static es.uned.utils.dto.cargarBases;

/**
 * Clase GestorBases.
 * Esta clase se encarga de gestionar las bases de vehículos.
 *
 * Métodos:
 *
 * Alta de bases
 *
 * Modificar bases
 *
 * Eliminar bases
 *
 * Marcar bases averiadas
 *
 * Consultar bases disponibles
 *
 * Consultar bases por ocupación
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
     * @return instancia del gestor de bases.
     */
    public static GestorBases getInstancia() {
        return instancia;
    }


    /**
     * Método para consultar el estado de las bases.
     */
    public void consultarEstadoBases(Scanner scanner) {
        System.out.println("Estado de las bases:");
        for (Base base : bases) {
            System.out.println("ID: " + base.getId() + ", Coordenadas: " + base.getCoordenadas().getX() + ", " + base.getCoordenadas().getY() +
                    ", Capacidad máxima: " + base.getCapacidadMaxima() +
                    ", Vehículos alquilados: " + base.getVehiculos().size() +
                    ", Huecos disponibles: " + (base.getCapacidadMaxima() - base.getVehiculos().size()) +
                    ", Averiada: " + (base.isAveriada() ? "Sí" : "No"));
        }

        System.out.println("Presione una tecla para continuar...");
        scanner.nextLine();
    }

    /**
     * Método para consultar bases por disponibilidad.
     */
    public void consultarBasesDisponibles() {
        System.out.println("Bases disponibles:");
        for (Base base : bases) {
            if (!base.isAveriada() && base.getVehiculos().size() < base.getCapacidadMaxima()) {
                System.out.println("ID: " + base.getId() + ", Coordenadas: " + base.getCoordenadas().getX() + ", " + base.getCoordenadas().getY() +
                        ", Capacidad máxima: " + base.getCapacidadMaxima());
            }
        }
    }

    /**
     * Método para consultar bases por ocupación.
     */
    public void consultarBasesPorOcupacion() {
        // consultar bases ordenadas por ocupacion
        this.bases.stream().
                filter(base -> !base.isAveriada()).
                sorted(Comparator.comparingInt(base -> base.getVehiculos().size())).
                forEach(base -> System.out.println("ID: " + base.getId() + ", Ocupación: " + base.getVehiculos().size() + "/" + base.getCapacidadMaxima()));
    }

    /**
     * Generar estadisticas de las bases
     */
    public void generarEstadisticasBases() {
        System.out.println("Estadísticas de las bases:");
        this.bases.stream()
                .filter(base -> !base.isAveriada())
                .sorted(Comparator.comparing((Base base) -> base.getVehiculos().size()).reversed())
                .forEach(base -> System.out.println(
                        "ID: " + base.getId() +
                                ", Ocupación: " + base.getVehiculos().size() +
                                "/" + base.getCapacidadMaxima()
                ));
    }

    /**
     * Método para obtener una base por su ID.
     * @param id ID de la base a consultar.
     * @return Base correspondiente al ID proporcionado, o null si no se encuentra.
     */
    public Base consultarBasePorId(String id) {
        return bases.stream()
                .filter(base -> Objects.equals(base.getId(), id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Método para obtener una base de un vehículo.
     * @param matricula Matrícula del vehículo a consultar.
     * @return Base correspondiente al vehículo, o null si no se encuentra.
     */
    public Base obtenerBaseVehiculo(String matricula) {
        return bases.stream()
                .filter(base -> base.getVehiculos().stream().anyMatch(vehiculo -> Objects.equals(vehiculo.getMatricula(), matricula)))
                .findFirst()
                .orElse(null);
    }



}

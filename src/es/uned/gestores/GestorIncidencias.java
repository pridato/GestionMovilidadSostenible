package es.uned.gestores;

import es.uned.model.Incidencia;
import es.uned.model.personas.Mantenimiento;
import es.uned.model.personas.Mecanico;
import es.uned.model.personas.Trabajador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static es.uned.utils.dto.cargarIncidencias;

public class GestorIncidencias {

    private static final GestorIncidencias instancia = new GestorIncidencias();
    private static final GestorPersonas gp = GestorPersonas.getInstancia();

    private List<Incidencia> incidencias = new ArrayList<>();

    /* constructor */
    private GestorIncidencias() {
        this.incidencias = cargarIncidencias();
    }

    public static GestorIncidencias getInstancia() {
        return instancia;
    }

    /**
     * Método para visualizar los problemas de los vehículos
     */
    public void visualizarProblemasVehículos() {
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getVehiculo() != null) {
                System.out.println("Vehículo: " + incidencia.getVehiculo().getMatricula());
                System.out.println("Problema: " + incidencia.getDescripcion());
                System.out.println("Fecha: " + incidencia.getFechaReporte());
                System.out.println();
            }
        }
    }

    /**
     * Método para visualizar los problemas de las bases
     */
    public void visualizarProblemasBases() {
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getBase() != null) {
                System.out.println("Base: " + incidencia.getBase().getId());
                System.out.println("vehiculos disponibles: " + incidencia.getBase().getCapacidadMaxima());
                System.out.println("Capacidad actual: " + incidencia.getBase().getVehiculosAlquilados().size());
                System.out.println("Problema: " + incidencia.getDescripcion());
                System.out.println("Fecha: " + incidencia.getFechaReporte());
                System.out.println();
            }
        }
    }

    /**
     * Método para buscar una incidencia por su ID
     * @param id ID de la incidencia a buscar
     * @return Incidencia encontrada o null si no se encuentra
     */
    private Incidencia buscarIncidenciaPorId(String id) {
        return incidencias.stream()
                .filter(incidencia -> incidencia.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Método para asignar un vehículo a un trabajador
     * @param scanner Scanner para leer la entrada del usuario
     * @return true si se ha asignado el vehículo, false si no se ha encontrado la incidencia
     */
    public boolean asignarVehiculoTrabajador(Scanner scanner) {
        // primero listar problemas de vehiculos
        visualizarProblemasVehículos();

        // pedir id de la incidencia
        System.out.print("Introduce el ID de la incidencia: ");
        String idIncidencia = scanner.nextLine();

        Incidencia incidencia = buscarIncidenciaPorId(idIncidencia);

        if (incidencia != null) {
            gp.listarPersonas();
            System.out.print("Introduce el DNI del trabajador: ");
            String dni = scanner.nextLine();
            Trabajador trabajador = (Trabajador) gp.buscarPersonaPorDNI(dni);

            // como la incidencia solo puede ser de un mecanico o de mantenimiento comprobamos
            if(trabajador instanceof Mantenimiento || trabajador instanceof Mecanico) {
                incidencia.setEncargado(trabajador);
                System.out.println("Vehículo asignado al trabajador con dni: " + dni + " y nombre " + trabajador.getNombre() + " " + trabajador.getApellidos());
                return true;
            } else {
                System.out.println("El trabajador no es un mecánico o un mantenimiento.");
                return false;
            }

        } else {
            System.out.println("Incidencia no encontrada.");
            return false;
        }

    }

    /**
     * Método para asignar una base a un trabajador
     * @param scanner Scanner para leer la entrada del usuario
     * @return true si se ha asignado la base, false si no se ha encontrado la incidencia
     */
    public boolean asignarBaseTrabajador(Scanner scanner) {
        // primero listar problemas de vehiculos
        visualizarProblemasBases();

        // pedir id de la incidencia
        System.out.print("Introduce el ID de la incidencia: ");
        String idIncidencia = scanner.nextLine();

        Incidencia incidencia = buscarIncidenciaPorId(idIncidencia);

        if (incidencia != null) {
            gp.listarPersonas();
            System.out.print("Introduce el DNI del trabajador: ");
            String dni = scanner.nextLine();
            Trabajador trabajador = (Trabajador) gp.buscarPersonaPorDNI(dni);

            // como la incidencia solo puede ser de un mecanico o de un mantenimiento comprobamos
            if(trabajador instanceof Mecanico) {
                incidencia.setEncargado(trabajador);
                System.out.println("Base asignada al trabajador con dni: " + dni + " y nombre " + trabajador.getNombre() + " " + trabajador.getApellidos());
                return true;
            } else {
                System.out.println("El trabajador no es un mecánico o un mantenimiento.");
                return false;
            }

        } else {
            System.out.println("Incidencia no encontrada.");
            return false;
        }
    }
}

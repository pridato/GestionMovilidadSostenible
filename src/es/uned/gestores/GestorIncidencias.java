package es.uned.gestores;

import es.uned.enums.EstadoVehiculo;
import es.uned.model.Base;
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
    public void visualizarProblemasVehículos(Scanner scanner) {
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getVehiculo() != null) {
                System.out.println("ID: " + incidencia.getId());
                System.out.println("Vehículo: " + incidencia.getVehiculo().getMatricula());
                System.out.println("Problema: " + incidencia.getDescripcion());
                System.out.println("Fecha: " + incidencia.getFechaReporte());
                if(incidencia.getEncargado() != null) {
                    System.out.println("Encargado: " + incidencia.getEncargado().getNombre() + " " + incidencia.getEncargado().getApellidos());
                } else {
                    System.out.println("Encargado: No asignado");
                }
                System.out.println();
            }
        }

        System.out.println("Presione ENTER para continuar...");
        scanner.nextLine();
    }

    /**
     * Método para visualizar los problemas de las bases
     */
    public void visualizarProblemasBases() {
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getBase() != null) {
                System.out.println("ID: " + incidencia.getId());
                System.out.println("Base: " + incidencia.getBase().getId());
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
     *
     * @param scanner Scanner para leer la entrada del usuario
     */
    public void asignarVehiculoTrabajador(Scanner scanner, GestorPersonas gp) {

        System.out.println("Desea consultar los problemas de los vehiculos? (s/n)");

        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("s")) {
            visualizarProblemasVehículos(scanner);
        }

        // pedir id de la incidencia
        System.out.print("Introduce el ID de la incidencia: ");
        String idIncidencia = scanner.nextLine();

        Incidencia incidencia = buscarIncidenciaPorId(idIncidencia);

        if (incidencia == null) {
            System.out.println("Incidencia no encontrada.");
            return;
        }
        incidencia.getVehiculo().setEstado(EstadoVehiculo.AVERIADO);

        String dni = obtenerDniTrabajador(scanner, gp);
        Trabajador trabajador = (Trabajador) gp.buscarPersonaPorDNI(dni);

        // como la incidencia solo puede ser de un mecanico o de mantenimiento comprobamos
        if(trabajador instanceof Mantenimiento || trabajador instanceof Mecanico) {
            incidencia.setEncargado(trabajador);
                if (trabajador instanceof Mecanico mecanico) {
                    mecanico.asignarVehiculo(incidencia.getVehiculo());
                } else {
                    Mantenimiento mantenimiento = (Mantenimiento) trabajador;
                    mantenimiento.asignarVehiculo(incidencia.getVehiculo());
                }
            System.out.println("Vehículo asignado al trabajador con dni: " + dni + " y nombre " + trabajador.getNombre() + " " + trabajador.getApellidos());
        } else {
            System.out.println("El trabajador no es un mecánico o un mantenimiento.");
        }

    }

    /**
     * Método para asignar una base a un trabajador
     *
     * @param scanner Scanner para leer la entrada del usuario
     */
    public void asignarBaseTrabajador(Scanner scanner, GestorPersonas gp) {
        // primero listar problemas de vehiculos
        System.out.println("¿Desea consultar los problemas de las bases? (s/n)");
        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("s")) {
            visualizarProblemasBases();
        }

        // pedir id de la incidencia
        System.out.print("Introduce el ID de la incidencia: ");
        String idIncidencia = scanner.nextLine();

        Incidencia incidencia = buscarIncidenciaPorId(idIncidencia);
        if (incidencia == null) {
            System.out.println("Incidencia no encontrada.");
            return;
        }
        Base base = incidencia.getBase();

        String dni = obtenerDniTrabajador(scanner, gp);
        Trabajador trabajador = (Trabajador) gp.buscarPersonaPorDNI(dni);
        incidencia.setEncargado(trabajador);
        // como la incidencia solo puede ser de un mecanico o de un mantenimiento comprobamos
        if(trabajador instanceof Mecanico mecanico) {
            incidencia.setEncargado(trabajador);
            ((Mecanico)incidencia.getEncargado()).asignarBase(base);
            System.out.println("Base asignada al trabajador con dni: " + dni + " y nombre " + trabajador.getNombre() + " " + trabajador.getApellidos());
        } else {
            System.out.println("El trabajador no es un mecánico.");
        }

    }

    /**
     * Método para obtener el DNI del trabajador
     * @param scanner Scanner para leer la entrada del usuario
     * @param gp Gestor de personas
     * @return DNI del trabajador
     */
    private static String obtenerDniTrabajador(Scanner scanner, GestorPersonas gp) {
        System.out.println("¿Desea consultar los trabajadores? (s/n)");
        String respuesta2 = scanner.nextLine();
        if (respuesta2.equalsIgnoreCase("s")) {
            gp.consultarTrabajadoresDisponibles();
        }

        System.out.print("Introduce el DNI del trabajador: ");
        String dni = scanner.nextLine();
        return dni;
    }
}

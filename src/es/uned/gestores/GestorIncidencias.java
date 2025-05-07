package es.uned.gestores;

import es.uned.enums.EstadoVehiculo;
import es.uned.model.Base;
import es.uned.model.Incidencia;
import es.uned.model.personas.Mantenimiento;
import es.uned.model.personas.Mecanico;
import es.uned.model.personas.Trabajador;
import es.uned.model.personas.Usuario;
import es.uned.model.vehiculos.Vehiculo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static es.uned.menus.MenuAdministrador.gestorBases;
import static es.uned.menus.MenuAdministrador.gestorVehiculos;
import static es.uned.menus.MenuUsuario.gestorAlquiler;
import static es.uned.utils.dto.cargarIncidencias;

public class GestorIncidencias {

    private static final GestorIncidencias instancia = new GestorIncidencias();

    private final List<Incidencia> incidencias;

    /* constructor */
    private GestorIncidencias() {
        this.incidencias = cargarIncidencias();
    }

    public static GestorIncidencias getInstancia() {
        return instancia;
    }

    /**
     * Método para generar una incidencia sobre un vehículo o una base
     * @param usuario Usuario que reporta la incidencia
     * @param scanner Scanner para leer la entrada del usuario
     */
    public void generarIncidencia(Usuario usuario, Scanner scanner) {
        System.out.println("----- Gestión de Incidencias -----");
        System.out.println("1. Crear incidencia sobre un vehículo");
        System.out.println("2. Crear incidencia sobre una base");
        System.out.println("0. Salir");

        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1 -> crearIncidenciaVehiculo(usuario, scanner);
            case 2 -> crearIncidenciaBase(usuario, scanner);
            case 0 -> System.out.println("Saliendo del menú de incidencias...");
            default -> System.out.println("Opción no válida.");
        }
    }

    /**
     * Método para crear una incidencia sobre un vehículo
     * @param usuario Usuario que reporta la incidencia
     * @param scanner Scanner para leer la entrada del usuario
     */
    private void crearIncidenciaVehiculo(Usuario usuario, Scanner scanner) {
        System.out.println("Creando incidencia sobre un vehículo...");
        gestorVehiculos.consultarVehiculosDisponibles();

        System.out.print("Introduce la matrícula del vehículo: ");
        String matricula = scanner.nextLine();
        Vehiculo vehiculo = gestorVehiculos.obtenerVehiculo(matricula);

        if (vehiculo == null) {
            System.out.println("Vehículo no encontrado.");
            return;
        }

        vehiculo.setEstado(EstadoVehiculo.AVERIADO);

        System.out.print("Describe brevemente la incidencia: ");
        String descripcion = scanner.nextLine();

        Incidencia incidencia = new Incidencia(usuario, descripcion, new Date(), null, vehiculo);
        incidencias.add(incidencia);
        mostrarConfirmacion(incidencia);
    }

    /**
     * Método para crear una incidencia sobre una base
     * @param usuario Usuario que reporta la incidencia
     * @param scanner Scanner para leer la entrada del usuario
     */
    private void crearIncidenciaBase(Usuario usuario, Scanner scanner) {
        System.out.println("Creando incidencia sobre una base...");
        gestorBases.consultarBasesDisponibles();

        System.out.print("Introduce el ID de la base: ");
        String idBase = scanner.nextLine();
        Base base = gestorBases.consultarBasePorId(idBase);

        if (base == null) {
            System.out.println("Base no encontrada.");
            return;
        }

        base.setAveriada(true);

        System.out.print("Describe brevemente la incidencia: ");
        String descripcion = scanner.nextLine();

        Incidencia incidencia = new Incidencia(usuario, descripcion, new Date(), base, null);
        incidencias.add(incidencia);
        mostrarConfirmacion(incidencia);
    }

    /**
     * Método para mostrar confirmación de incidencia generada
     * @param incidencia Incidencia generada
     */
    private void mostrarConfirmacion(Incidencia incidencia) {
        System.out.println("Incidencia generada con éxito.");
        System.out.println("ID: " + incidencia.getId());
        System.out.println("Descripción: " + incidencia.getDescripcion());
        System.out.println("Fecha: " + incidencia.getFechaReporte());
    }


}

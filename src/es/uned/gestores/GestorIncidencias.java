package es.uned.gestores;

import es.uned.enums.EstadoVehiculo;
import es.uned.model.Base;
import es.uned.model.Coordenadas;
import es.uned.model.Incidencia;
import es.uned.model.personas.Trabajador;
import es.uned.model.personas.Usuario;
import es.uned.model.vehiculos.Bicicleta;
import es.uned.model.vehiculos.Patinete;
import es.uned.model.vehiculos.Vehiculo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static es.uned.menus.MenuAdministrador.*;
import static es.uned.utils.GeolocalizacionPorIP.leerCoordenadasManualmente;

public class GestorIncidencias {

    private static final GestorIncidencias instancia = new GestorIncidencias();

    private final List<Incidencia> incidencias;

    /* constructor */
    private GestorIncidencias() {
        this.incidencias = new ArrayList<>();
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




    /**
     * Método para asignar una incidencia de una base a un mecánico
     * @param scanner Scanner para leer la entrada del usuario
     * @throws IllegalArgumentException si el trabajador o la base no se encuentran
     */
    public void asignarBaseMecanicos(Scanner scanner) throws IllegalArgumentException {
        gestorPersonas.consultarMecanicosDisponibles();

        System.out.print("Introduce el DNI del trabajador: ");
        String idTrabajador = scanner.nextLine();
        Trabajador trabajador = (Trabajador) gestorPersonas.buscarPersonaPorDNI(idTrabajador);

        if(trabajador == null) {
            throw new IllegalArgumentException("Trabajador no encontrado.");
        }

        gestorBases.consultarBasesAveriadas();
        System.out.print("Introduce el ID de la base: ");
        String idBase = scanner.nextLine();
        Base base = gestorBases.consultarBasePorId(idBase);

        if(base == null) {
            throw new IllegalArgumentException("Base no encontrada.");
        }

        Incidencia incidencia = consultarIncidenciaPorBase(base);
        incidencia.setEncargado(trabajador);

        System.out.println("Asignada la incidencia " + incidencia.getId() + " a " + trabajador.getNombre() + " " + trabajador.getApellidos());

    }

    /**
     * Método para asignar una incidencia de un vehículo a un mecánico
     * @param scanner Scanner para leer la entrada del usuario
     */
    public void asignarVehiculoTrabajador(Scanner scanner) throws IllegalArgumentException {

        System.out.println("----- Asignación de Incidencias -----");
        System.out.println("1. Asignar incidencia de un vehículo a un mecánico");
        System.out.println("2. Asignar incidencia de un vehículo a personal de mantenimiento");

        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();

        scanner.nextLine();
        switch (opcion) {
            case 1 -> {
                System.out.println("Mecánicos disponibles:");
                gestorPersonas.consultarMecanicosDisponibles();
            }
            case 2 -> {
                System.out.println("Personal de mantenimiento disponibles:");
                gestorPersonas.consultarPersonalMantenimientoDisponibles();
            }
            default -> throw new IllegalArgumentException("Opción no válida.");
        }

        System.out.print("Introduce el DNI del trabajador: ");
        String idTrabajador = scanner.nextLine();
        Trabajador trabajador = (Trabajador) gestorPersonas.buscarPersonaPorDNI(idTrabajador);

        if(trabajador == null) {
            throw new IllegalArgumentException("Trabajador no encontrado.");
        }

        gestorVehiculos.consultarVehiculosAveriados();
        System.out.print("Introduce la matrícula del vehículo: ");
        String matricula = scanner.nextLine();
        Vehiculo vehiculo = gestorVehiculos.obtenerVehiculo(matricula);

        if(vehiculo == null) {
            throw new IllegalArgumentException("Vehículo no encontrado.");
        }

        Incidencia incidencia = consultarIncidenciaPorVehiculo(vehiculo);
        incidencia.setEncargado(trabajador);
        System.out.println("Asignando la incidencia " + incidencia.getId() + " a " + trabajador.getNombre() + " " + trabajador.getApellidos());
    }

    /**
     * Método para consultar una incidencia a través de una base
     * @param base Base sobre la que se quiere consultar la incidencia
     * @return Incidencia asociada a la base, o null si no existe
     * @throws IllegalArgumentException si no se encuentra la incidencia
     */
    private Incidencia consultarIncidenciaPorBase(Base base) throws IllegalArgumentException {
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getBase() != null && incidencia.getBase().getId().equals(base.getId())) {
                return incidencia;
            }
        }
        throw new IllegalArgumentException("No se ha encontrado ninguna incidencia asociada a la base " + base.getId());
    }

    /**
     * Método para consultar una incidencia a través de un vehículo
     * @param vehiculo Vehículo sobre el que se quiere consultar la incidencia
     * @return Incidencia asociada al vehículo, o null si no existe
     * @throws IllegalArgumentException si no se encuentra la incidencia
     */
    private Incidencia consultarIncidenciaPorVehiculo(Vehiculo vehiculo) throws IllegalArgumentException {
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getVehiculo() != null && incidencia.getVehiculo().getMatricula().equals(vehiculo.getMatricula())) {
                return incidencia;
            }
        }
        throw new IllegalArgumentException("No se ha encontrado ninguna incidencia asociada al vehículo " + vehiculo.getMatricula());
    }



    /**
     * Método para consultar todas las incidencias actuales
     */
    public void consultarIncidenciasActuales() {
        System.out.println("----- Incidencias actuales -----");
        for (Incidencia incidencia : incidencias) {
            mostrarIncidencia(incidencia);
        }
    }


    /**
     * Método para consultar las asignaciones actuales de incidencias
     */
    public void consultarAsignacionesActuales() {
        System.out.println("----- Asignaciones actuales -----");
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getEncargado() != null) {
                System.out.println("Incidencia " + incidencia.getId() + " asignada a " + incidencia.getEncargado().getNombre() + " " + incidencia.getEncargado().getApellidos());
            }
        }
    }



    /**
     * Método para consultar las incidencias asignadas a un trabajador
     * @param trabajador Trabajador al que se le quieren consultar las incidencias
     */
    public void consultarIncidenciasPorEncargado(Trabajador trabajador) {
        for (int i = 0; i < incidencias.size(); i++) {
            Incidencia incidencia = incidencias.get(i);
            if (incidencia.getEncargado() != null && incidencia.getEncargado().getDNI().equals(trabajador.getDNI())) {
                System.out.println("Incidencia " + (i + 1) + ": ");
                mostrarIncidencia(incidencia);
            }
        }
    }

    /**
     * Método auxiliar para mostrar la información de una incidencia
     * @param incidencia Incidencia a mostrar
     */
    private void mostrarIncidencia(Incidencia incidencia) {
        System.out.println("ID: " + incidencia.getId());
        System.out.println("Descripción: " + incidencia.getDescripcion());
        System.out.println("Fecha de reporte: " + incidencia.getFechaReporte());
        if (incidencia.getBase() != null) {
            System.out.println("Base afectada: " + incidencia.getBase().getId());
            System.out.println("Tiempo de inactividad " + incidencia.getBase().getTiempoInactividad() + " horas");
        }
        if (incidencia.getVehiculo() != null) {
            System.out.println("Vehículo afectado: " + incidencia.getVehiculo().getMatricula());
            System.out.println("Tiempo de inactividad " + incidencia.getVehiculo().getTiempoInactividad() + " horas");

        }
        System.out.println("-------------------------------");
    }

    /**
     * Método para recoger un vehículo para su reparación
     * @return Vehículo recogido
     * @throws IllegalArgumentException si el vehículo no se encuentra
     */
    public Vehiculo recogerVehiculoParaReparar() throws IllegalArgumentException {
        gestorVehiculos.consultarVehiculosAveriados();

        System.out.println("Indique la matrícula del vehículo a recoger:");
        Scanner scanner = new Scanner(System.in);
        String matricula = scanner.nextLine();

        Vehiculo vehiculo = gestorVehiculos.obtenerVehiculo(matricula);

        if(vehiculo == null) {
           throw new IllegalArgumentException("Vehículo no encontrado.");
        } else {
            vehiculo.setEstado(EstadoVehiculo.EN_REPARACION);
            System.out.println("Se procederá a la recogida del vehículo " + vehiculo.getMatricula() + " para su desplazamiento");
            return vehiculo;
        }


    }

    /**
     * Método para desplazamiento a una base para su reparación
     * @param scanner Scanner para leer la entrada del usuario
     */
    public Base desplazamientoBaseReparacion(Scanner scanner) throws IllegalArgumentException {
        gestorBases.consultarBasesAveriadas();
        System.out.print("Introduce el ID de la base: ");
        String idBase = scanner.nextLine();
        Base base = gestorBases.consultarBasePorId(idBase);

        if(base == null) {
            throw new IllegalArgumentException("Base no encontrada.");
        }

        System.out.println("Se ha desplazado a la base " + base.getId() + " para su reparación.");
        System.out.println("Incidencias reportadas: ");
        consultarIncidenciaPorBase(base);

        return base;
    }

    /**
     * Método para definir un tiempo de inactividad para un vehículo
     * @param vehiculo Vehículo al que se le define el tiempo de inactividad
     * @throws IllegalArgumentException Excepción si el vehículo no se encuentra
     */
    public int definirTiempoInactividadVehiculo(Vehiculo vehiculo, Scanner scanner) throws IllegalArgumentException {
        if(vehiculo == null) {
            throw new IllegalArgumentException("Vehículo no encontrado.");
        }
        System.out.println("Definiendo tiempo de inactividad para el vehículo " + vehiculo.getMatricula());
        System.out.print("Introduce el tiempo de inactividad (en horas): ");
        int tiempoInactividad = scanner.nextInt();

        vehiculo.setTiempoInactividad(tiempoInactividad);
        System.out.println("Tiempo de inactividad definido para el vehículo " + vehiculo.getMatricula() + ": " + tiempoInactividad + " horas.");
        return tiempoInactividad;
    }

    /**
     * Método para definir un tiempo de inactividad para una base
     *
     * @param base    Base a la que se le define el tiempo de inactividad
     * @param scanner Scanner para leer la entrada del usuario
     */
    public void definirTiempoInactividadBase(Base base, Scanner scanner) throws IllegalArgumentException {
        if(base == null) {
            throw new IllegalArgumentException("Base no encontrada.");
        }
        System.out.println("Definiendo tiempo de inactividad para la base " + base.getId());
        System.out.print("Introduce el tiempo de inactividad (en horas): ");
        int tiempoInactividad = scanner.nextInt();

        base.setTiempoInactividad(tiempoInactividad);
        System.out.println("Tiempo de inactividad definido para la base " + base.getId() + ": " + tiempoInactividad + " horas.");
    }

    /**
     * Método para devolver un vehículo reparado
     * @param vehiculo Vehículo a devolver
     * @param scanner Scanner para leer la entrada del usuario
     */
    public void devolverVehiculoReparado(Vehiculo vehiculo, Scanner scanner) throws IllegalArgumentException {
        if(vehiculo == null) {
            throw new IllegalArgumentException("Vehiculo no encontrado.");
        }
        if(vehiculo instanceof Patinete || vehiculo instanceof Bicicleta) {
            // devolvemos el vehículo a la base más cercana
            Base base= gestorBases.obtenerBaseMasCercana(vehiculo.getCoordenadas());
            System.out.println("El vehículo se trasladará a la base " + base.getId() + " para su reparación (más cercana a su ubicación)");
            base.añadirVehiculo(vehiculo);
        } else {
            // en caso de ser una moto, devolvemos el vehículo a unas coordenadas especificadas
            Coordenadas coordenadas = leerCoordenadasManualmente(scanner);
            vehiculo.setCoordenadas(coordenadas);
        }

        vehiculo.setEstado(EstadoVehiculo.DISPONIBLE);
        vehiculo.setTiempoInactividad(0);
        System.out.println("Vehículo " + vehiculo.getMatricula() + " devuelto y listo para su uso.");

        Incidencia incidencia = consultarIncidenciaPorVehiculo(vehiculo);
        incidencias.remove(incidencia);
        System.out.println("Incidencia " + incidencia.getId() + " cerrada.");
    }
}

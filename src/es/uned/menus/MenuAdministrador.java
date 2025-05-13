package es.uned.menus;

import es.uned.gestores.*;
import es.uned.model.Coordenadas;

import java.util.Scanner;

import static es.uned.utils.consts.*;
import static es.uned.utils.utils.leerOpcion;

public class MenuAdministrador {

    // variables auxiliares para la definición de límites en las coordenadas
    public static Coordenadas limiteInferior = LIMITES_COORDENADAS_INFERIOR;
    public static Coordenadas limiteSuperior = LIMITES_COORDENADAS_SUPERIOR;

    public static double descuentoPremium = DESCUENTO_PREMIUM;

    public static final GestorVehiculos gestorVehiculos = GestorVehiculos.getInstancia();
    public static final GestorPersonas gestorPersonas = GestorPersonas.getInstancia();
    public static final GestorIncidencias gestorIncidencias = GestorIncidencias.getInstancia();
    public static final GestorBases gestorBases = GestorBases.getInstancia();
    public static final GestorFacturas gestorFacturas = GestorFacturas.getInstancia();
    public static final GestorAlquileres gestorAlquiler = GestorAlquileres.getInstancia();

    /**
     * Gestionar las opciones del menú del administrador.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public static void gestionarOpcionesAdminstrador(Scanner scanner) {
        int opcion;
        do {
            System.out.println();
            System.out.println("----- Menú Administrador -----");
            System.out.println("1. Gestión de Personas");
            System.out.println("2. Gestión de Vehículos");
            System.out.println("3. Gestión de bases");
            System.out.println("4. Gestión de Trabajadores");
            System.out.println("5. Generar Estadísticas");
            System.out.println("6. Establecer límites de coordenadas");
            System.out.println("0. Salir");
            System.out.println();
            System.out.println("------------------------------");
            opcion = leerOpcion(scanner);

            switch (opcion) {
                case 1 -> gestionarMenuUsuarios(scanner);
                case 2 -> gestionarMenuVehiculos(scanner);
                case 3 -> gestionarMenuBases(scanner);
                case 4 -> gestionarMenuTrabajadores(scanner);
                case 5 -> gestionarMenuEstadisticas(scanner);
                case 6 -> establecerLimitesCoordenadas(scanner);
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida.");
            }

            System.out.println("------------------------------");

        } while (opcion != 0);


    }

    // -------------------------------
    // Métodos para gestionar opciones
    // -------------------------------

    /**
     * Gestiona las opciones del menú de gestión de usuarios.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     */
    private static void gestionarMenuUsuarios(Scanner scanner) {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Personas ---");
            System.out.println("1. Alta de personas ");
            System.out.println("2. Baja de personas");
            System.out.println("3. Modificar personas");
            System.out.println("4. Listar personas");
            System.out.println("5. Usuarios que deberían promover a premium");
            System.out.println("6. Visualizar utilización de los vehículos y sus importes");
            System.out.println("7. Promover a premium");
            System.out.println("8. Modificar descuento de usuarios premium");
            System.out.println("0. Volver");
            System.out.println("------------------------------");
            opcion = leerOpcion(scanner);

            switch (opcion) {
                case 1 -> {
                    try {
                        gestorPersonas.crearPersona(scanner);
                    } catch (Exception e) {
                        System.out.println("Error al crear la persona: " + e.getMessage());
                    }
                }
                case 2 -> gestorPersonas.eliminarPersona(scanner);
                case 3 -> gestorPersonas.modificarPersona(scanner);
                case 4 -> gestorPersonas.listarPersonas();
                case 5 -> gestorPersonas.usuariosDeberianSerPremium();
                case 6 -> gestorPersonas.utilizacionVehiculosPorUsuario();
                case 7 -> gestorPersonas.promoverUsuarioAPremium(scanner);
                case 8 -> descuentoPremium = gestorPersonas.modificarDescuentoPremium(scanner);
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }


    /**
     * Gestiona las opciones del menú de gestión de vehículos.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     */
    private static void gestionarMenuVehiculos(Scanner scanner) {
        System.out.println("Gestionando Vehículos...");

        int opcion;
        do {
            System.out.println("\n--- Gestión de Vehículos ---");
            System.out.println("1. Añadir Vehículo");
            System.out.println("2. Modificar Vehículo");
            System.out.println("3. Eliminar Vehículo");
            System.out.println("4. Listar Vehículos");
            System.out.println("5. Establecer tarifas de vehículos");
            System.out.println("6. Visualizar estado de la Batería de los Vehículos");
            System.out.println("0. Volver");
            System.out.println("------------------------------");
            opcion = leerOpcion(scanner);

            switch (opcion) {
                case 1 -> gestorVehiculos.añadirVehiculo(scanner);
                case 2 -> gestorVehiculos.modificarVehiculo(scanner);
                case 3 -> gestorVehiculos.eliminarVehiculo(scanner);
                case 4 -> gestorVehiculos.consultarVehiculos();
                case 5 -> gestorVehiculos.setTarifaMinuto(scanner);
                case 6 -> gestorVehiculos.consultarBaterias(scanner);
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    /**
     * Gestiona las opciones del menú de gestión de bases.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     */
    private static void gestionarMenuBases(Scanner scanner) {
        int opcion;

        do {
            System.out.println("\n--- Gestión de bases ---");
            System.out.println("1. Visualizar estado de las bases");
            System.out.println("2. Consultar bases disponibles");
            System.out.println("3. Consultar bases por ocupación");
            System.out.println("4. Generar estadísticas de bases ordenadas por demanda");
            System.out.println("0. Volver");
            System.out.println("------------------------------");
            opcion = leerOpcion(scanner);

            switch (opcion) {
                case 1 -> gestorBases.consultarEstadoBases();
                case 2 -> gestorBases.consultarBasesDisponibles();
                case 3 -> gestorBases.consultarBasesPorOcupacion();
                case 4 -> gestorBases.generarEstadisticasBases();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }


    /**
     * Gestiona las opciones del menú de gestión de trabajadores.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     */
    private static void gestionarMenuTrabajadores(Scanner scanner) {
        int opcion;

        do {
            System.out.println("\n--- Gestión de Trabajadores ---");
            System.out.println("1. Listar trabajadores");
            System.out.println("2. Asignar bases a mecánicos");
            System.out.println("3. Asignar vehículo a un trabajador");
            System.out.println("4. Ver asignaciones actuales");
            System.out.println("5. Incidencias actuales");
            System.out.println("6. Facturas generadas por mecánicos");
            System.out.println("0. Volver");
            System.out.println("------------------------------");
            opcion = leerOpcion(scanner);

            switch (opcion) {
                case 1 -> {
                    try {
                        gestorPersonas.consultarPersonalMantenimientoDisponibles();
                        gestorPersonas.consultarMecanicosDisponibles();
                    } catch(Exception e) {
                        System.out.println("Error al listar trabajadores: " + e.getMessage());
                    }

                    System.out.println("Presione Enter para continuar...");
                    scanner.nextLine();
                }
                case 2 -> {
                    try {
                        gestorIncidencias.asignarBaseMecanicos(scanner);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error al asignar vehículo a trabajador: " + e.getMessage());
                    }
                }
                case 3 -> {
                    try {
                        gestorIncidencias.asignarVehiculoTrabajador(scanner);
                    } catch(Exception e) {
                        System.out.println("Error al asignar vehículo a trabajador: " + e.getMessage());
                    }
                }
                case 4 -> gestorIncidencias.consultarAsignacionesActuales();
                case 5 -> gestorIncidencias.consultarIncidenciasActuales();
                case 6 -> gestorFacturas.consultarFacturas();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    // ------------------------------
    // Métodos coordenadas
    // ------------------------------


    /**
     * Muestra el menú para establecer límites de coordenadas y gestiona la entrada del usuario.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public static void establecerLimitesCoordenadas(Scanner scanner) {
        System.out.println("\n--- Establecer límites de coordenadas ---");

        System.out.print("Longitud mínima (min x): ");
        double lonMin = scanner.nextDouble();

        System.out.print("Latitud mínima (min y): ");
        double latMin = scanner.nextDouble();

        System.out.print("Longitud máxima (max x): ");
        double lonMax = scanner.nextDouble();

        System.out.print("Latitud máxima (max y): ");
        double latMax = scanner.nextDouble();

        // comprobar que los límites son válidos
        if (latMin >= latMax || lonMin >= lonMax) {
            throw new IllegalArgumentException("Los límites de coordenadas no son válidos.");
        }

        limiteInferior = new Coordenadas(latMin, lonMin);
        limiteSuperior = new Coordenadas(latMax, lonMax);

        System.out.println("Límites establecidos.");
    }


    // ------------------------------
    // Métodos para estadísticas
    // ------------------------------

    /**
     * Genera estadísticas sobre los usuarios y vehículos.
     */
    private static void gestionarMenuEstadisticas(Scanner scanner) {
        int opcion;

        do {
            System.out.println("---------------------");
            System.out.println("1. Listado de bases ordenadas por ocupación");
            System.out.println("2. Listado de vehículos ordenados que han requerido más reparaciones");
            System.out.println("3. Listado de encargados de mantenimiento y mecánicos ordenados por intervenciones");
            System.out.println("4. Listado de vehículos ordenados por tiempo de uso");
            System.out.println("5. Listado de usuarios ordenados por sus gastos de alquiler");
            System.out.println("0. Volver");
            System.out.println("---------------------");
            opcion = leerOpcion(scanner);

            switch (opcion) {
                case 1 -> gestorBases.consultarBasesPorOcupacion();
                case 2 -> gestorIncidencias.consultarVehiculosOrdenadosReparacion();
                case 3 -> gestorIncidencias.consultarTrabajadoresOrdenadosIntervenciones();
                case 4 -> gestorAlquiler.consultarVehiculosOrdenadosTiempoUso();
                case 5 -> gestorAlquiler.consultarUsuariosOrdenadosGastosAlquiler();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }
        } while(opcion != 0);
    }
}

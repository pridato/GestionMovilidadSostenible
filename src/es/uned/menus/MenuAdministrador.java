package es.uned.menus;

import es.uned.gestores.GestorBases;
import es.uned.gestores.GestorIncidencias;
import es.uned.gestores.GestorPersonas;
import es.uned.gestores.GestorVehiculos;
import es.uned.model.Coordenadas;
import java.util.Scanner;

import static es.uned.utils.consts.LIMITES_COORDENADAS_INFERIOR;
import static es.uned.utils.consts.LIMITES_COORDENADAS_SUPERIOR;

public class MenuAdministrador {

    // variables auxiliares para la definición de límites en las coordenadas
    public static Coordenadas limiteInferior = LIMITES_COORDENADAS_INFERIOR;
    public static Coordenadas limiteSuperior = LIMITES_COORDENADAS_SUPERIOR;

    public static final GestorVehiculos gestorVehiculos = GestorVehiculos.getInstancia();
    public static final GestorPersonas gestorPersonas = GestorPersonas.getInstancia();
    public static final GestorIncidencias gestorIncidencias = GestorIncidencias.getInstancia();
    public static final GestorBases gestorBases = GestorBases.getInstancia();

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
            System.out.println("4. Generar Estadísticas");
            System.out.println("5. Establecer límites de coordenadas");
            System.out.println("0. Salir");
            System.out.println();
            System.out.println("------------------------------");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> gestionarMenuUsuarios(scanner);
                case 2 -> gestionarMenuVehiculos(scanner);
                case 3 -> gestionarMenuBases(scanner);
                case 4 -> generarEstadisticas();
                case 5 -> establecerLimitesCoordenadas(scanner);
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
            System.out.println("0. Volver");
            System.out.println("------------------------------");

            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

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
            System.out.println("7. Visualizar problemas mecánicos de los Vehículos");
            System.out.println("8. Asignar vehículo a un trabajador");
            System.out.println("0. Volver");
            System.out.println("------------------------------");
            opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            switch (opcion) {
                case 1 -> {
                    if (gestorVehiculos.añadirVehiculo(scanner)) {
                        System.out.println("Vehículo añadido correctamente.");
                    } else {
                        System.out.println("No se pudo añadir el vehículo.");
                    }
                }
                case 2 -> {
                    // Modificar vehículo
                    System.out.println("Modificando vehículo...");
                    System.out.print("Introduce la matrícula del vehículo a modificar: ");
                    String matricula = scanner.nextLine();
                    gestorVehiculos.modificarVehiculo(matricula, scanner);
                }
                case 3 -> {
                    // Eliminar vehículo
                    System.out.println("Eliminando vehículo...");

                    System.out.print("Introduce la matrícula del vehículo a eliminar: ");
                    String matricula = scanner.nextLine();
                    if (gestorVehiculos.eliminarVehiculo(matricula)) {
                        System.out.println("Vehículo eliminado correctamente.");
                    } else {
                        System.out.println("No se pudo eliminar el vehículo.");
                    }

                }
                case 4 -> gestorVehiculos.consultarVehiculos();
                case 5 -> gestorVehiculos.setTarifaMinuto(scanner);
                case 6 -> gestorVehiculos.consultarBaterias(scanner);
                case 7 -> gestorIncidencias.visualizarProblemasVehículos(scanner);
                case 8 -> gestorIncidencias.asignarVehiculoTrabajador(scanner, gestorPersonas);
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    /**
     * Gestiona las opciones del menú de gestión de bases.
     * @param scanner
     */
    private static void gestionarMenuBases(Scanner scanner) {


        int opcion = 0;

        do {
            System.out.println("\n--- Gestión de bases ---");
            System.out.println("1. Visualizar estado de las bases");
            System.out.println("2. Asignar bases a mecánicos");
            System.out.println("3. Consultar bases disponibles");
            System.out.println("4. Consultar bases por ocupación");
            System.out.println("5. Generar estadísticas de bases ordenadas por demanda");
            System.out.println("0. Volver");
            System.out.println("------------------------------");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> gestorBases.consultarEstadoBases(scanner);
                case 2 -> gestorIncidencias.asignarBaseTrabajador(scanner, gestorPersonas);
                case 3 -> gestorBases.consultarBasesDisponibles();
                case 4 -> gestorBases.consultarBasesPorOcupacion();
                case 5 -> gestorBases.generarEstadisticasBases();
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
    private static void generarEstadisticas() {
        System.out.println("Generando estadísticas...");
        // Implementar lógica para generar estadísticas
    }


}

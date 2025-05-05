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

    private static final GestorVehiculos gv = GestorVehiculos.getInstancia();
    private static final GestorPersonas gp = GestorPersonas.getInstancia();
    private static final GestorIncidencias gi = GestorIncidencias.getInstancia();
    private static final GestorBases gb = GestorBases.getInstancia();

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
            System.out.println("0. Volver");
            System.out.println("------------------------------");

            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> gp.crearPersona(scanner);
                case 2 -> gp.eliminarPersona(scanner);
                case 3 -> gp.modificarPersona(scanner);
                case 4 -> gp.listarPersonas();
                case 5 -> gp.usuariosDeberianSerPremium();
                case 6 -> gp.utilizacionVehiculosPorUsuario();
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
                    if (gv.añadirVehiculo(scanner)) {
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
                    gv.modificarVehiculo(matricula, scanner);
                }
                case 3 -> {
                    // Eliminar vehículo
                    System.out.println("Eliminando vehículo...");

                    System.out.print("Introduce la matrícula del vehículo a eliminar: ");
                    String matricula = scanner.nextLine();
                    if (gv.eliminarVehiculo(matricula)) {
                        System.out.println("Vehículo eliminado correctamente.");
                    } else {
                        System.out.println("No se pudo eliminar el vehículo.");
                    }

                }
                case 4 -> gv.consultarVehiculos();
                case 5 -> gv.setTarifaMinuto(scanner);
                case 6 -> gv.consultarBaterias();
                case 7 -> gi.visualizarProblemasVehículos();
                case 8 -> gi.asignarVehiculoTrabajador(scanner);
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
        System.out.println("\n--- Gestión de bases ---");
        System.out.println("1. Visualizar estado de las bases");
        System.out.println("2. Asignar bases a mecánicos");
        System.out.println("3. Consultar bases disponibles");
        System.out.println("4. Consultar bases por ocupación");
        System.out.println("5. Generar estadísticas de bases ordenadas por demanda");
        System.out.println("0. Volver");
        System.out.println("------------------------------");

        int opcion = 0;

        do {
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> gb.consultarEstadoBases();
                case 2 -> gi.asignarBaseTrabajador(scanner);
                case 3 -> gb.consultarBasesDisponibles();
                case 4 -> gb.consultarBasesPorOcupacion();
                case 5 -> gb.generarEstadisticasBases();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

    }



    // ------------------------------
    // Métodos coordenadas
    // ------------------------------

    /**
     * Lee las coordenadas manualmente desde la entrada del usuario.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     * @return Coordenadas introducidas por el usuario.
     */
    public static Coordenadas leerCoordenadasManualmente(Scanner scanner) {
        System.out.print("Introduce la coordenada X (longitud): ");
        double x = scanner.nextDouble();
        System.out.print("Introduce la coordenada Y (latitud): ");
        double y = scanner.nextDouble();
        scanner.nextLine(); // limpiar buffer
        return new Coordenadas(x, y);
    }


    /**
     * Muestra el menú para establecer límites de coordenadas y gestiona la entrada del usuario.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public static void establecerLimitesCoordenadas(Scanner scanner) {
        System.out.println("\n--- Establecer límites de coordenadas ---");
        System.out.print("Latitud mínima: ");
        double latMin = scanner.nextDouble();
        System.out.print("Longitud mínima: ");
        double lonMin = scanner.nextDouble();
        System.out.print("Latitud máxima: ");
        double latMax = scanner.nextDouble();
        System.out.print("Longitud máxima: ");
        double lonMax = scanner.nextDouble();

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

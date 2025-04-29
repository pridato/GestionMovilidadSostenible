package es.uned.menus;

import es.uned.gestores.GestorUsuarios;
import es.uned.gestores.GestorVehiculos;
import es.uned.model.Coordenadas;
import es.uned.model.personas.Usuario;
import es.uned.utils.GeolocalizacionPorIP;

import java.util.Scanner;

public class MenuAdministrador {

    // variables auxiliares para la definición de límites en las coordenadas
    private static Coordenadas limiteInferior;
    private static Coordenadas limiteSuperior;

    private static final GestorUsuarios gu = GestorUsuarios.getInstancia();
    private static final GestorVehiculos gv = GestorVehiculos.getInstancia();

    /**
     * Gestionar las opciones del menú del administrador.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public static void gestionarOpcionesAdminstrador(Scanner scanner) {
        int opcion;
        do {
            mostrarMenuAdministrador();
            System.out.println("------------------------------");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> gestionarUsuarios(scanner);
                case 2 -> gestionarVehiculos(scanner);
                case 3 -> visualizarBateria();
                case 4 -> gestionarReparaciones(scanner);
                case 5 -> generarEstadisticas();
                case 6 -> establecerLimitesCoordenadas(scanner);
                case 7 -> {
                    System.out.println("Saliendo del programa...");
                    opcion = 0;
                }
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
    private static void gestionarUsuarios(Scanner scanner) {
        int opcion;
        do {
            mostrarMenuUsuarios();

            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> altaUsuario(scanner);
                case 2 -> bajaUsuario(scanner);
                case 3 -> listarUsuarios(scanner);
                case 4 -> promocionarUsuariosAPremium(scanner);
                case 5 -> {
                    System.out.println("Modificando descuentos de usuarios premium...");
                    System.out.print("Introduce el descuento para usuarios premium: (0-100) ");
                    int descuento = scanner.nextInt();
                    gu.setDescuentoPremium(descuento);
                }
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
    private static void gestionarVehiculos(Scanner scanner) {
        System.out.println("Gestionando Vehículos...");


        int opcion;
        do {
            mostrarMenuVehiculos();
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
                case 4 -> {
                    gv.consultarVehiculos();
                }
                case 5 -> {
                    // Volver
                    System.out.println("Volviendo al menú principal...");
                }
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 5);
    }

    /**
     * Gestiona las opciones del menú de gestión de reparaciones.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     */
    private static void gestionarReparaciones(Scanner scanner) {
        mostrarMenuReparaciones();
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1 -> {
                // Asignar reparación a vehículo
                System.out.println("Asignando reparación a vehículo...");
            }
            case 2 -> {
                // Asignar reparación a base
                System.out.println("Asignando reparación a base...");
            }
            case 3 -> {
                // Volver
                System.out.println("Volviendo al menú principal...");
            }
            default -> System.out.println("Opción no válida.");
        }
    }


    // -------------------------------
    // Métodos de mostrar menús
    // -------------------------------

    /**
     * Muestra el menú del administrador.
     */
    private static void mostrarMenuAdministrador() {
        System.out.println();
        System.out.println("----- Menú Administrador -----");
        System.out.println("1. Gestión de Usuarios");
        System.out.println("2. Gestión de Vehículos");
        System.out.println("3. Visualización de Estado de la Batería de los Vehículos");
        System.out.println("4. Reparación de Vehículos y Bases");
        System.out.println("5. Generar Estadísticas");
        System.out.println("6. Establecer límites de coordenadas");
        System.out.println("7. Salir");

        System.out.println();
    }

    /**
     * Muestra el menú de gestión de usuarios.
     */
    private static void mostrarMenuUsuarios() {
        System.out.println("\n--- Gestión de Usuarios ---");
        System.out.println("1. Alta de usuario");
        System.out.println("2. Baja de usuario");
        System.out.println("3. Listar usuarios");
        System.out.println("4. Promoción de Usuarios a Premium");
        System.out.println("5. Modificar descuentos de usuarios premium");
        System.out.println("0. Volver");
        System.out.println("------------------------------");
    }


    /**
     * Muestra el menú de gestión de vehículos.
     */
    private static void mostrarMenuVehiculos() {
        System.out.println("\n--- Gestión de Vehículos ---");
        System.out.println("1. Añadir Vehículo");
        System.out.println("2. Modificar Vehículo");
        System.out.println("3. Eliminar Vehículo");
        System.out.println("4. Listar Vehículos");
        System.out.println("5. Volver");
        System.out.println("------------------------------");
    }


    /**
     * Muestra el menú de gestión de reparaciones.
     */
    private static void mostrarMenuReparaciones() {
        System.out.println("\n--- Gestión de Reparaciones ---");
        System.out.println("1. Asignar reparación a vehículo");
        System.out.println("2. Asignar reparación a base");
        System.out.println("3. Volver");
        System.out.println("------------------------------");
    }


    // ------------------------------
    // Métodos para gestionar usuarios
    // ------------------------------

    /**
     * Muestra el menú de alta de usuario y gestiona la entrada del usuario.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     */
    private static void altaUsuario(Scanner scanner) {
        System.out.println("→ Alta de usuario");

        System.out.print("DNI: ");
        String dni = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();

        System.out.print("Correo electrónico: ");
        String email = scanner.nextLine();

        System.out.print("Telefono: ");
        int telefono = scanner.nextInt();
        scanner.nextLine();

        System.out.println("¿Desea usar localización automática por IP? (S/N): ");
        String opcion = scanner.nextLine().trim().toUpperCase();

        Coordenadas coordenadas;

        if (opcion.equals("S")) {
            coordenadas = GeolocalizacionPorIP.obtenerCoordenadasDesdeIP();
            if (coordenadas == null) {
                System.out.println("No se pudo obtener la ubicación automáticamente. Introduzca manualmente.");
                coordenadas = leerCoordenadasManualmente(scanner);
            }
        } else {
            coordenadas = leerCoordenadasManualmente(scanner);
        }

        Usuario nuevoUsuario = new Usuario(dni, nombre, apellidos, email, telefono, coordenadas);
        boolean resultado = gu.añadirUsuario(nuevoUsuario);

        if (resultado) {
            System.out.println("Usuario añadido correctamente.");
        } else {
            System.out.println("No se pudo añadir el usuario.");
        }
    }

    /**
     * Muestra el menú de baja de usuario y gestiona la entrada del usuario.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     */
    private static void bajaUsuario(Scanner scanner) {
        System.out.println("→ Baja de usuario");
        System.out.print("Introduce el DNI del usuario a eliminar: ");
        String dni = scanner.nextLine();

        boolean resultado = gu.eliminarUsuario(dni);

        if (resultado) {
            System.out.println("Usuario eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el usuario.");
        }
    }

    private static void listarUsuarios(Scanner scanner) {
        System.out.println("→ Listado de usuarios");
        // elegir mostrar todos, solo premium, solo no premium
        int opcion = 0;
        System.out.println("1. Mostrar todos los usuarios");
        System.out.println("2. Mostrar solo usuarios premium");
        System.out.println("3. Mostrar solo usuarios no premium");
        System.out.print("Elige una opción: ");
        opcion = scanner.nextInt();

        scanner.nextLine(); // limpiar buffer

        switch (opcion) {
            case 1 -> gu.consultarUsuarios();
            case 2 -> gu.consultarUsuariosPremium();
            case 3 -> gu.consultarUsuariosNoPremium();
            default -> System.out.println("Opción no válida.");
        }
    }

    /**
     * Promociona a los usuarios a Premium.
     */
    private static void promocionarUsuariosAPremium(Scanner scanner) {
        System.out.println("→ Promoción de usuarios a Premium");

        System.out.println("¿Desea consultar los usuarios no premium? (S/N): ");

        String opcion = scanner.nextLine().trim().toUpperCase();
        if (opcion.equals("S")) {
            gu.consultarUsuariosNoPremium();
        }

        System.out.print("Introduce el DNI del usuario a promocionar: ");
        String dni = scanner.nextLine();

        gu.promocionarUsuarioPremium(dni);

        System.out.println("Usuario promocionado a Premium correctamente.");
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
    private static Coordenadas leerCoordenadasManualmente(Scanner scanner) {
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

    /**
     * Devuelve las coordenadas del límite inferior.
     *
     * @return Coordenadas del límite inferior.
     */
    public static Coordenadas getLimiteInferior() {
        return limiteInferior;
    }

    /**
     * Devuelve las coordenadas del límite superior.
     *
     * @return Coordenadas del límite superior.
     */
    public static Coordenadas getLimiteSuperior() {
        return limiteSuperior;
    }


    // ------------------------------
    // Métodos para baterías
    // ------------------------------

    /**
     * Visualiza el estado de las baterías de los vehículos.
     */
    private static void visualizarBateria() {
        System.out.println("Visualizando Estado de Baterías...");
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

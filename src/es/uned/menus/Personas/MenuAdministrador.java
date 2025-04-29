package es.uned.menus.Personas;

import es.uned.gestores.GestorUsuarios;
import es.uned.model.Coordenadas;
import es.uned.model.personas.Usuario;
import es.uned.utils.GeolocalizacionPorIP;

import java.util.Scanner;

public class MenuAdministrador {

    private static final GestorUsuarios gu = new GestorUsuarios();

    /**
     * Gestionar las opciones del menú del administrador.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public static void gestionarOpcionesAdminstrador(Scanner scanner) {
        int opcion;
        do {
            mostrarMenuAdministrador();
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> gestionarUsuarios(scanner);
                case 2 -> gestionarVehiculos(scanner);
                case 3 -> visualizarBateria();
                case 4 -> gestionarReparaciones(scanner);
                case 5 -> generarEstadisticas();
                case 6 -> establecerLimitesCoordenadas();
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);


    }

    // -------------------------------
    // Métodos para gestionar opciones
    // -------------------------------

    /**
     * Gestiona las opciones del menú de gestión de usuarios.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    private static void gestionarUsuarios(Scanner scanner) {
        mostrarMenuUsuarios();

        int opcion = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer

        switch (opcion) {
            case 1 -> altaUsuario(scanner);
            case 2 -> bajaUsuario(scanner);
            case 3 -> listarUsuarios(scanner);
            case 4 -> promocionarUsuariosAPremium();
            case 0 -> System.out.println("Volviendo...");
            default -> System.out.println("Opción no válida.");
        }

    }


    /**
     * Gestiona las opciones del menú de gestión de vehículos.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    private static void gestionarVehiculos(Scanner scanner) {
        System.out.println("Gestionando Vehículos...");
        mostrarMenuVehiculos();

        int opcion = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer

        switch (opcion) {
            case 1 -> {
                // Añadir vehículo
                System.out.println("Añadiendo vehículo...");
            }
            case 2 -> {
                // Modificar vehículo
                System.out.println("Modificando vehículo...");
            }
            case 3 -> {
                // Eliminar vehículo
                System.out.println("Eliminando vehículo...");
            }
            case 4 -> {
                // Volver
                System.out.println("Volviendo al menú principal...");
            }
            default -> System.out.println("Opción no válida.");
        }
    }

    /**
     * Gestiona las opciones del menú de gestión de reparaciones.
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
        System.out.println("----- Menú Administrador -----");
        System.out.println("1. Gestión de Usuarios");
        System.out.println("2. Gestión de Vehículos");
        System.out.println("3. Visualización de Estado de la Batería de los Vehículos");
        System.out.println("4. Reparación de Vehículos y Bases");
        System.out.println("5. Generar Estadísticas");
        System.out.println("6. Establecer límites de coordenadas");
        System.out.println("7. Salir");
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
        System.out.println("0. Volver");
    }


    /**
     * Muestra el menú de gestión de vehículos.
     */
    private static void mostrarMenuVehiculos() {
        System.out.println("1. Añadir Vehículo");
        System.out.println("2. Modificar Vehículo");
        System.out.println("3. Eliminar Vehículo");
        System.out.println("4. Volver");
    }


    /**
     * Muestra el menú de gestión de reparaciones.
     */
    private static void mostrarMenuReparaciones() {
        System.out.println("1. Asignar reparación a vehículo");
        System.out.println("2. Asignar reparación a base");
        System.out.println("3. Volver");
    }
    // ------------------------------
    // Métodos para gestionar usuarios
    // ------------------------------
    /**
     * Muestra el menú de alta de usuario y gestiona la entrada del usuario.
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

        Usuario nuevoUsuario = new Usuario(dni, nombre,apellidos, email, telefono, coordenadas);
        boolean resultado = gu.añadirUsuario(nuevoUsuario);

        if (resultado) {
            System.out.println("Usuario añadido correctamente.");
        } else {
            System.out.println("No se pudo añadir el usuario.");
        }
    }

    /**
     * Muestra el menú de baja de usuario y gestiona la entrada del usuario.
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
    private static void promocionarUsuariosAPremium() {
        System.out.println("→ Promoción de usuarios a Premium");

    }



    // ------------------------------
    // Métodos para leer coordenadas
    // ------------------------------
    /**
     * Lee las coordenadas manualmente desde la entrada del usuario.
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
     * Establece los límites de coordenadas para la geolocalización.
     */
    private static void establecerLimitesCoordenadas() {
        System.out.println("Estableciendo límites de coordenadas...");
        // Implementar lógica para establecer límites de coordenadas
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

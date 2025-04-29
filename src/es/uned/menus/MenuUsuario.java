package es.uned.menus;

import java.util.Scanner;

/**
 * Clase que representa el menú de usuario.
 */
public class MenuUsuario {

    /**
     * Método para gestionar las opciones del menú de usuario.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public static void gestionarOpcionesUsuario(Scanner scanner) {
        int opcion;
        do {
            mostrarMenuUsuario();
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> consultarEstadoBateria();
                case 2 -> consultarVehiculosDisponibles();
                case 3 -> alquilarVehiculo(scanner);
                case 4 -> devolverVehiculo(scanner);
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    /**
     * Método para mostrar el menú de usuario.
     */
    private static void mostrarMenuUsuario() {
        System.out.println("----- Menú Usuario -----");
        System.out.println("1. Consultar estado de batería de mi vehículo");
        System.out.println("2. Consultar vehículos disponibles");
        System.out.println("3. Alquilar un vehículo");
        System.out.println("4. Devolver vehículo");
        System.out.println("0. Salir");
    }

    /**
     * Método para consultar el estado de batería del vehículo.
     */
    private static void consultarEstadoBateria() {
        System.out.println("Consultando estado de batería...");
        // Lógica real aquí
    }

    /**
     * Método para consultar los vehículos disponibles.
     */
    private static void consultarVehiculosDisponibles() {
        System.out.println("Mostrando vehículos disponibles...");
        // Lógica real aquí
    }


    /**
     * Método para alquilar un vehículo.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    private static void alquilarVehiculo(Scanner scanner) {
        System.out.println("Alquilando vehículo...");
        // Lógica real aquí
    }

    /**
     * Método para devolver un vehículo.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    private static void devolverVehiculo(Scanner scanner) {
        System.out.println("Devolviendo vehículo...");
        // Lógica real aquí
    }

}

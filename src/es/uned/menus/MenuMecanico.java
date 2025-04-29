package es.uned.menus;

import java.util.Scanner;

public class MenuMecanico {

    /**
     * Gestiona las opciones del menú del mecánico.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public static void gestionarOpcionesMecanico(Scanner scanner) {
        int opcion;
        do {
            mostrarMenuMecanico();
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> verVehiculosParaReparar();
                case 2 -> marcarVehiculoReparado(scanner);
                case 3 -> verHistorialReparaciones();
                case 0 -> System.out.println("Volviendo al menú anterior...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    /**
     * Método para mostrar el menú del mecánico.
     */
    private static void mostrarMenuMecanico() {
        System.out.println("\n--- Menú Mecánico ---");
        System.out.println("1. Ver vehículos pendientes de reparación");
        System.out.println("2. Marcar vehículo como reparado");
        System.out.println("3. Ver historial de reparaciones");
        System.out.println("0. Volver");
    }


    /**
     * Método para ver los vehículos pendientes de reparación.
     */
    private static void verVehiculosParaReparar() {
        System.out.println("Mostrando vehículos pendientes de reparación...");
        // Implementar lógica
    }


    /**
     * Método para marcar un vehículo como reparado.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    private static void marcarVehiculoReparado(Scanner scanner) {
        System.out.print("Introduce el ID del vehículo reparado: ");
        String idVehiculo = scanner.nextLine();
        System.out.println("Vehículo " + idVehiculo + " marcado como reparado.");
        // Implementar lógica de actualización
    }


    /**
     * Método para ver el historial de reparaciones.
     */
    private static void verHistorialReparaciones() {
        System.out.println("Mostrando historial de reparaciones...");
        // Implementar lógica
    }
}

package es.uned.menus;

import java.util.Scanner;

public class MenuVehiculos {

    public static void mostrarMenuVehiculos(Scanner scanner) {
        System.out.println("\n--- Gestión de Vehículos ---");
        System.out.println("1. Añadir vehículo");
        System.out.println("2. Eliminar vehículo");
        System.out.println("3. Listar vehículos");
        System.out.println("0. Volver");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1 -> System.out.println("→ Añadir vehículo (por implementar)");
            case 2 -> System.out.println("→ Eliminar vehículo (por implementar)");
            case 3 -> System.out.println("→ Listar vehículos (por implementar)");
            case 0 -> System.out.println("Volviendo...");
            default -> System.out.println("Opción no válida.");
        }
    }
}

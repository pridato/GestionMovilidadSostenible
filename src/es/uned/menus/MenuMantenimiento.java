package es.uned.menus;

import java.util.Scanner;

public class MenuMantenimiento {

    /**
     * Gestiona las opciones del menú de mantenimiento.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public static void gestionarOpcionesMantenimiento(Scanner scanner) {
        int opcion;
        do {
            mostrarMenuMantenimiento();
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> revisarBases();
                case 2 -> reportarIncidenciaBase(scanner);
                case 3 -> listarBases();
                case 0 -> System.out.println("Volviendo al menú anterior...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    /**
     * Método para mostrar el menú de mantenimiento.
     */
    private static void mostrarMenuMantenimiento() {
        System.out.println("\n--- Menú Mantenimiento ---");
        System.out.println("1. Revisar bases");
        System.out.println("2. Reportar incidencia en base");
        System.out.println("3. Listar bases disponibles");
        System.out.println("0. Volver");
    }

    /**
     * Método para revisar el estado de las bases.
     */
    private static void revisarBases() {
        System.out.println("Revisando estado de bases...");
        // Implementar lógica
    }

    /**
     * Método para reportar una incidencia en una base.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    private static void reportarIncidenciaBase(Scanner scanner) {
        System.out.print("Introduce el ID de la base: ");
        String idBase = scanner.nextLine();
        System.out.print("Descripción de la incidencia: ");
        String descripcion = scanner.nextLine();
        System.out.println("Incidencia reportada para la base " + idBase + ": " + descripcion);
        // Implementar lógica de registro
    }

    /**
     * Método para listar todas las bases disponibles.
     */
    private static void listarBases() {
        System.out.println("Listando todas las bases disponibles...");
        // Implementar lógica
    }
}

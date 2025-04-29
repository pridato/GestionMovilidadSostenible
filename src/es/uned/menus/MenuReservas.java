package es.uned.menus;

public class MenuReservas {

    /**
     * Muestra el menú principal de gestión de reservas y gestiona la selección del usuario.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public static void mostrarMenuReservas(java.util.Scanner scanner) {
        System.out.println("\n--- Gestión de Reservas ---");
        System.out.println("1. Crear reserva");
        System.out.println("2. Cancelar reserva");
        System.out.println("3. Listar reservas");
        System.out.println("0. Volver");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer

        switch (opcion) {
            case 1 -> System.out.println("→ Crear reserva (por implementar)");
            case 2 -> System.out.println("→ Cancelar reserva (por implementar)");
            case 3 -> System.out.println("→ Listar reservas (por implementar)");
            case 0 -> System.out.println("Volviendo...");
            default -> System.out.println("Opción no válida.");
        }
    }
}

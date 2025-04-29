package es.uned.menus.Personas;

import java.util.Scanner;

import static es.uned.menus.MenuCoordenadas.establecerLimites;
import static es.uned.menus.MenuPrincipal.mostrarMenu;
import static es.uned.menus.MenuReservas.mostrarMenuReservas;
import static es.uned.menus.MenuVehiculos.mostrarMenuVehiculos;
import static es.uned.menus.Personas.MenuUsuarios.mostrarMenuUsuarios;

public class MenuAdministrador {

    /**
     * Muestra el menú principal de la aplicación y gestiona la selección del usuario.
     */
    public static void mostrarMenu() {
        System.out.println("\n==== MENÚ ADMINISTRADOR ====");
        System.out.println("1. Gestión de usuarios");
        System.out.println("2. Gestión de vehículos");
        System.out.println("3. Gestión de reservas");
        System.out.println("4. Establecer límites de coordenadas");
        System.out.println("0. Salir");
    }

    public static void mostrarMenuAdministrador(Scanner scanner) {
        int opcion;
        do {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> mostrarMenuUsuarios(scanner);
                case 2 -> mostrarMenuVehiculos(scanner);
                case 3 -> mostrarMenuReservas(scanner);
                case 4 -> establecerLimites(scanner);
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);


    }
}

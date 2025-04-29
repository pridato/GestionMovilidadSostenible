import java.util.Scanner;

import static es.uned.menus.MenuAdministrador.gestionarOpcionesAdminstrador;
import static es.uned.menus.MenuMantenimiento.gestionarOpcionesMantenimiento;
import static es.uned.menus.MenuMecanico.gestionarOpcionesMecanico;
import static es.uned.menus.MenuUsuario.gestionarOpcionesUsuario;

public class Main {


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenuRol();
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> gestionarOpcionesAdminstrador(scanner);
                case 2 -> gestionarOpcionesUsuario(scanner);
                case 3 -> gestionarOpcionesMantenimiento(scanner);
                case 4 -> gestionarOpcionesMecanico(scanner);
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);



        scanner.close();
    }

    /**
     * Método para mostrar el menú de selección de rol.
     */
    private static void mostrarMenuRol() {
        System.out.println("===================================");
        System.out.println("Seleccione su rol:");
        System.out.println("1. Administrador");
        System.out.println("2. Usuario");
        System.out.println("3. Encargado de mantenimiento");
        System.out.println("4. Mecánico");
        System.out.println("0. Salir");
        System.out.println("===================================");
    }

}
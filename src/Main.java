import java.util.Scanner;

import static es.uned.menus.MenuCoordenadas.establecerLimites;
import static es.uned.menus.MenuPrincipal.mostrarMenuRol;
import static es.uned.menus.MenuReservas.mostrarMenuReservas;
import static es.uned.menus.Personas.MenuAdministrador.gestionarOpcionesAdminstrador;
import static es.uned.menus.MenuVehiculos.mostrarMenuVehiculos;

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
                case 2 -> mostrarMenuVehiculos(scanner);
                case 3 -> mostrarMenuReservas(scanner);
                case 4 -> establecerLimites(scanner);
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);



        scanner.close();
    }

}
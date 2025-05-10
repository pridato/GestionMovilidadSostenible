package es.uned.utils;

import java.util.Scanner;

public class utils {

    /**
     * Método para mostrar un menú y leer la opción seleccionada por el usuario.
     * @param scanner Scanner para leer la entrada del usuario.
     * @return La opción seleccionada por el usuario.
     */
    public static int leerOpcion(Scanner scanner) {
        while (true) {
            System.out.print("Introduce una opción: ");
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Debes introducir un número. Inténtalo de nuevo.");
            }
        }
    }

    /**
     * Método para mostrar un menú y leer la opción seleccionada por el usuario.
     * @param scanner Scanner para leer la entrada del usuario.
     * @param mensaje Mensaje a mostrar al usuario.
     * @return La opción seleccionada por el usuario.
     */
    public static String obtenerDato(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }
}

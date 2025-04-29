package es.uned.menus;

import es.uned.gestores.GestorUsuarios;
import es.uned.model.Coordenadas;
import es.uned.model.personas.Usuario;
import es.uned.utils.GeolocalizacionPorIP;

import java.util.Scanner;

public class MenuUsuarios {

    private static final GestorUsuarios gu = new GestorUsuarios();

    /**
     * Muestra el menú principal de gestión de usuarios y gestiona la selección del usuario.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public static void mostrarMenuUsuarios(Scanner scanner) {
        System.out.println("\n--- Gestión de Usuarios ---");
        System.out.println("1. Alta de usuario");
        System.out.println("2. Baja de usuario");
        System.out.println("3. Listar usuarios");
        System.out.println("0. Volver");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer

        switch (opcion) {
            case 1 -> altaUsuario(scanner);
            case 2 -> System.out.println("→ Baja de usuario (por implementar)");
            case 3 -> System.out.println("→ Listado de usuarios (por implementar)");
            case 0 -> System.out.println("Volviendo...");
            default -> System.out.println("Opción no válida.");
        }
    }

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
}

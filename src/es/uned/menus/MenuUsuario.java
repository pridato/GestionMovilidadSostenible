package es.uned.menus;

import es.uned.gestores.GestorAlquileres;
import es.uned.gestores.GestorBases;
import es.uned.gestores.GestorUsuarios;
import es.uned.gestores.GestorVehiculos;
import es.uned.model.Base;
import es.uned.model.personas.Usuario;
import es.uned.model.vehiculos.Vehiculo;

import java.util.Scanner;

/**
 * Clase que representa el menú de usuario.
 */
public class MenuUsuario {

    private static GestorAlquileres ga = new GestorAlquileres();
    private static GestorVehiculos gv = new GestorVehiculos();
    private static GestorUsuarios gu = new GestorUsuarios();
    private static GestorBases gb = new GestorBases();
    Usuario usuario;

    /**
     * Método para gestionar el usuario autenticado.
     *
     * Es decir nosotros nos conectamos con un usuario cualquiera
     * @param scanner Scanner para leer la entrada del usuario.
     * @return El usuario autenticado.
     */
    public static Usuario gestionarUsuarioAutenticado(Scanner scanner) {
        System.out.println("Listado de usuarios:");
        gu.listarUsuarios();
        return gu.obtenerUsuarioIndice(scanner.nextInt());
    }

    /**
     * Método para gestionar las opciones del menú de usuario una vez el usuario ha iniciado sesión.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public void gestionarOpcionesUsuario(Scanner scanner) {
        int opcion;

        // usamos un usuario para gestionar los alquileres en su relacion
        this.usuario = gestionarUsuarioAutenticado(scanner);

        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        System.out.println("Bienvenido " + usuario.getNombre() + " " + usuario.getApellidos());
        do {
            mostrarMenuUsuario();
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> consultarEstadoBateria();
                case 2 -> gv.consultarVehiculosDisponibles();
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
     * Método para alquilar un vehículo.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    private void alquilarVehiculo(Scanner scanner) {
        gv.consultarVehiculosDisponibles();
        System.out.println("Introduce la matrícula del vehículo que deseas alquilar:");
        String matricula = scanner.nextLine();


        Vehiculo vehiculo = gv.obtenerVehiculo(matricula);

        gb.consultarBasesDisponiblesPorOcupacion();
        System.out.println("Introduce el ID de la base donde deseas alquilar el vehículo:");
        String idBase = scanner.nextLine();
        Base base = gb.consultarBasePorId(idBase);
        ga.iniciarAlquiler(usuario, vehiculo, base);
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

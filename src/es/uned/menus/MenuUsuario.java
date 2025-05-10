package es.uned.menus;

import es.uned.gestores.GestorAlquileres;
import es.uned.gestores.GestorUsuarios;
import es.uned.model.Alquiler;
import es.uned.model.personas.Usuario;
import es.uned.model.vehiculos.Vehiculo;

import java.util.Scanner;

import static es.uned.menus.MenuAdministrador.*;
import static es.uned.utils.utils.leerOpcion;

/**
 * Clase que representa el menú de usuario.
 */
public class MenuUsuario {

    private static final GestorUsuarios gestorUsuarios = GestorUsuarios.getInstancia();


    Usuario usuario;
    Alquiler alquiler;

    /**
     * Método para gestionar el usuario autenticado.
     * <p>
     * Es decir nosotros nos conectamos con un usuario cualquiera
     *
     * @param scanner Scanner para leer la entrada del usuario.
     * @return El usuario autenticado.
     */
    private static Usuario gestionarUsuarioAutenticado(Scanner scanner) {
        System.out.println("Listado de usuarios:");
        gestorUsuarios.listarUsuarios();
        return gestorUsuarios.obtenerUsuarioIndice(leerOpcion(scanner));
    }

    /**
     * Método para gestionar las opciones del menú de usuario una vez el usuario ha iniciado sesión.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public void gestionarOpcionesUsuario(Scanner scanner) {

        // usamos un usuario para gestionar los alquileres en su relacion
        this.usuario = gestionarUsuarioAutenticado(scanner);

        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        System.out.println("Bienvenido " + usuario.getNombre() + " " + usuario.getApellidos());
        int opcion;
        do {
            System.out.println("-------------------------");
            System.out.println("1. Gestión Personal");
            System.out.println("2. Gestión de Vehículos");
            System.out.println("3. Información General");
            System.out.println("4. Incidencias");
            System.out.println("0. Salir");
            System.out.println("-------------------------");

            opcion = leerOpcion(scanner);

            switch (opcion) {
                case 1 -> gestionarOpcionesUsuarioPersonal(scanner);
                case 2 -> gestionarOpcionesVehiculos(scanner);
                case 3 -> gestionarOpcionesInformacionGeneral(scanner);
                case 4 -> gestorIncidencias.generarIncidencia(usuario, scanner);
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    /**
     * Método para gestionar las opciones de usuario personal.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    private void gestionarOpcionesUsuarioPersonal(Scanner scanner) {
        System.out.println("----- Gestión Personal -----");
        System.out.println("1. Consultar mis datos actuales");
        System.out.println("2. Consultar mis alquileres");
        System.out.println("3. Consultar saldo");
        System.out.println("4. Recargar saldo");
        System.out.println("0. Salir");

        int opcion = leerOpcion(scanner);
        switch (opcion) {
            case 1 -> consultarDatosActualesUsuario(usuario);
            case 2 -> gestorAlquiler.consultarAlquileresUsuario(usuario);
            case 3 -> System.out.println("Saldo actual: " + usuario.getsaldo());
            case 4 -> recargarSaldo(scanner);
            case 0 -> System.out.println("Saliendo...");
            default -> System.out.println("Opción no válida.");
        }
    }

    /**
     * Método para gestionar las opciones de vehiculos
     */
    private void gestionarOpcionesVehiculos(Scanner scanner) {
        try {
            System.out.println("----- Gestión de Vehículos -----");
            System.out.println("1. Consultar vehículos disponibles");
            System.out.println("2. Alquilar un vehículo");
            System.out.println("3. Devolver vehículo");
            System.out.println("4. Reservar vehículo");
            System.out.println("5. Consultar estado de batería");
            System.out.println("0. Salir");

            int opcion = leerOpcion(scanner);
            switch (opcion) {
                case 1 -> gestorVehiculos.consultarVehiculosDisponibles();
                case 2 -> this.alquiler = gestorAlquiler.iniciarAlquiler(usuario, scanner, alquiler);
                case 3 -> {
                    GestorAlquileres.finalizarAlquiler(usuario, alquiler, scanner);
                    this.alquiler = null;
                }
                case 4 -> this.alquiler = gestorAlquiler.reservarVehiculo(usuario, scanner);
                case 5 -> {
                    if (alquiler == null) {
                        System.out.println("No tienes ningún vehículo alquilado.");
                        break;
                    }
                    consultarEstadoBateria(this.alquiler.getVehiculo());
                }
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Método para gestionar las opciones de información general.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    private void gestionarOpcionesInformacionGeneral(Scanner scanner) {
        System.out.println("----- Información General -----");
        System.out.println("1. Consultar límites en las coordenadas");
        System.out.println("2. Consultar tarifas");
        System.out.println("3. Consultar descuento de usuarios premium");
        System.out.println("0. Salir");
        int opcion = leerOpcion(scanner);

        if (opcion == 1) {
            System.out.println("Límites de coordenadas:");
            System.out.println("X: " + limiteInferior);
            System.out.println("Y: " + limiteSuperior);
        } else if (opcion == 2) {
            System.out.println("Tarifas:");
            // tarifas de cada...
        } else if (opcion == 3) {
            System.out.println("Descuento de usuarios premium: " + descuentoPremium*100 + "%");
        }
        else if (opcion == 0) {
            System.out.println("Saliendo...");
        } else {
            System.out.println("Opción no válida.");
        }
    }


    /**
     * Método para recargar el saldo del usuario.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    private void recargarSaldo(Scanner scanner) {
        System.out.print("Introduzca la cantidad a recargar: ");
        double cantidad = scanner.nextDouble();
        scanner.nextLine();
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que 0.");
        } else {
            usuario.recargarSaldo(cantidad);
            System.out.println("Saldo recargado. Nuevo saldo: " + usuario.getsaldo());
        }
    }

    /**
     * Método para consultar el estado de batería del vehículo.
     * @throws IllegalArgumentException si el vehículo es nulo.
     */
    private void consultarEstadoBateria(Vehiculo vehiculo) throws IllegalArgumentException {
        if(vehiculo == null) throw new IllegalArgumentException("El vehículo no puede ser nulo");
        System.out.println("Consultando estado de batería...");
        gestorVehiculos.consultarBateria(vehiculo, alquiler);
    }


    /**
     * Método para consultar los datos actuales del usuario.
     *
     * @param usuario Usuario a consultar.
     */
    private void consultarDatosActualesUsuario(Usuario usuario) {
        System.out.println(usuario);
    }
}

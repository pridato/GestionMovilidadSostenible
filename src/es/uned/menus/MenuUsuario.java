package es.uned.menus;

import es.uned.gestores.GestorAlquileres;
import es.uned.gestores.GestorUsuarios;
import es.uned.model.Alquiler;
import es.uned.model.personas.Usuario;
import es.uned.model.vehiculos.Vehiculo;

import java.util.Scanner;

import static es.uned.menus.MenuAdministrador.*;

/**
 * Clase que representa el menú de usuario.
 */
public class MenuUsuario {

    private static final GestorUsuarios gestorUsuarios = GestorUsuarios.getInstancia();
    private static final GestorAlquileres gestorAlquiler = GestorAlquileres.getInstancia();
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
    public static Usuario gestionarUsuarioAutenticado(Scanner scanner) {
        System.out.println("Listado de usuarios:");
        gestorUsuarios.listarUsuarios();
        return gestorUsuarios.obtenerUsuarioIndice(scanner.nextInt());
    }

    /**
     * Método para gestionar las opciones del menú de usuario una vez el usuario ha iniciado sesión.
     *
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
                case 1 -> {
                    if (alquiler == null) {
                        System.out.println("No tienes ningún vehículo alquilado.");
                        break;
                    }
                    consultarEstadoBateria(this.alquiler.getVehiculo());
                }

                case 2 -> gestorVehiculos.consultarVehiculosDisponibles();
                case 3 -> {
                    try {
                        this.alquiler = gestorAlquiler.iniciarAlquiler(usuario, scanner);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 4 -> GestorAlquileres.finalizarAlquiler(usuario, alquiler, scanner);
                case 5 -> consultarDatosActualesUsuario(usuario);
                case 6 -> gestorAlquiler.consultarAlquileresUsuario(usuario);
                case 7 -> {
                    System.out.println("Límites de coordenadas:");
                    System.out.println("X: " + limiteInferior);
                    System.out.println("Y: " + limiteSuperior);
                }
                case 8 -> System.out.println("Saldo actual: " + usuario.getsaldo());
                case 9 -> recargarSaldo(scanner);
                case 10 -> gestorAlquiler.reservarVehiculo(usuario, scanner);
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
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
     * Método para mostrar el menú de usuario.
     */
    private static void mostrarMenuUsuario() {
        System.out.println("----- Menú Usuario -----");
        System.out.println("1. Consultar estado de batería de mi vehículo");
        System.out.println("2. Consultar vehículos disponibles");
        System.out.println("3. Alquilar un vehículo");
        System.out.println("4. Devolver vehículo");
        System.out.println("5. Consultar mis datos actuales");
        System.out.println("6. Consultar mis alquileres");
        System.out.println("7. Consultar límites en las coordenadas");
        System.out.println("8. Consultar saldo");
        System.out.println("9. Recargar saldo");
        System.out.println("10. Reservar vehículo");
        System.out.println("0. Salir");
    }

    /**
     * Método para consultar el estado de batería del vehículo.
     */
    private void consultarEstadoBateria(Vehiculo vehiculo) {
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

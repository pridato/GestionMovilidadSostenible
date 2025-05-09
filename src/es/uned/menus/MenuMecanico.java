package es.uned.menus;

import es.uned.gestores.GestorFacturas;
import es.uned.model.Base;
import es.uned.model.personas.Mecanico;
import es.uned.model.vehiculos.Vehiculo;

import java.util.Scanner;

import static es.uned.menus.MenuAdministrador.*;

public class MenuMecanico {

    static Mecanico mecanico;
    static Base base;
    static Vehiculo vehiculo;

    /**
     * Gestiona las opciones del menú del mecánico.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public static void gestionarOpcionesMecanico(Scanner scanner) {
        try {
            mecanico = (Mecanico) gestorPersonas.obtenerTrabajadorPorListado(scanner, "mecanico");

            if (mecanico == null) {
                throw new Exception("No se ha encontrado el trabajador mecánico.");
            }

            int opcion;

            do {
                System.out.println("-------------------------");
                System.out.println("1. Visualizar vehículos y bases asignadas");
                System.out.println("2. Recogida de vehículos para su reparación");
                System.out.println("3. Desplazamiento a una base para su reparación");
                System.out.println("4. Definición tiempo inactividad por reparación");
                System.out.println("5. Generar factura de reparación");
                System.out.println("6. Consultar facturas generadas");
                System.out.println("0. Salir");
                System.out.println("-------------------------");

                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> gestorIncidencias.consultarIncidenciasPorEncargado(mecanico);
                    case 2 -> vehiculo = gestorIncidencias.recogerVehiculoParaReparar();
                    case 3 -> base = gestorIncidencias.desplazamientoBaseReparacion(scanner);
                    case 4 -> {
                        System.out.println("¿Desea definir el tiempo de inactividad de un vehículo o de una base?");
                        System.out.println("1. Vehículo");
                        System.out.println("2. Base");
                        System.out.print("Seleccione una opción: ");
                        int opcionTiempo = scanner.nextInt();
                        scanner.nextLine();
                        if (opcionTiempo == 1) {
                            gestorIncidencias.definirTiempoInactividadVehiculo(vehiculo, scanner);
                        } else if (opcionTiempo == 2) {
                            gestorIncidencias.definirTiempoInactividadBase(base, scanner);
                        }
                    }
                    case 5 -> {
                        System.out.println("Desea generar una factura de vehículo o de base?");
                        System.out.println("1. Vehículo");
                        System.out.println("2. Base");
                        System.out.print("Seleccione una opción: ");
                        int opcionFactura = scanner.nextInt();

                        scanner.nextLine();
                        switch (opcionFactura) {
                            case 1 -> {
                                if (vehiculo == null) {
                                    throw new Exception("No se ha recogido ningún vehículo para reparar.");
                                }
                                gestorFacturas.crearFacturaVehiculo(mecanico, vehiculo, scanner);
                            }
                            case 2 -> {
                                if (base == null) {
                                    throw new Exception("No se ha desplazado a ninguna base para reparar.");
                                }
                                gestorFacturas.crearFacturaBase(mecanico, base, scanner);
                            }
                            default -> System.out.println("Opción no válida. Intente de nuevo.");
                        }
                    }
                    case 6 -> gestorFacturas.consultarFacturasPorMecanico(mecanico);
                    case 0 -> System.out.println("Saliendo del menú del mecánico.");
                    default -> System.out.println("Opción no válida. Intente de nuevo.");
                }
            } while (opcion != 0);
        } catch (Exception e) {
            System.out.println("Error al gestionar las opciones del mecánico: " + e.getMessage());
        }
    }

}

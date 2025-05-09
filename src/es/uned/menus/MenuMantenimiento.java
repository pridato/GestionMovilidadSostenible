package es.uned.menus;

import es.uned.model.personas.Mantenimiento;
import es.uned.model.vehiculos.Vehiculo;

import java.util.Scanner;

import static es.uned.menus.MenuAdministrador.gestorIncidencias;
import static es.uned.menus.MenuAdministrador.gestorPersonas;

public class MenuMantenimiento {

    static Mantenimiento mantenimiento;
    static Vehiculo vehiculo;
    static int tiempoInactividad;

    /**
     * Método para gestionar las opciones del menú de mantenimiento.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public static void gestionarOpcionesPersonalMantenimiento(Scanner scanner) {

        try {
            mantenimiento =  (Mantenimiento) gestorPersonas.obtenerTrabajadorPorListado(scanner, "mantenimiento");

            if(mantenimiento == null) {
                throw new Exception("No se ha encontrado el trabajador de mantenimiento.");
            }
            if(vehiculo != null) {
                System.out.println("Reparación en curso del vehículo: " + vehiculo.getMatricula());

            } if (tiempoInactividad != 0) {
                System.out.println("Tiempo de inactividad definido: " + tiempoInactividad + " horas");
            }
            int opcion;

            do {
                System.out.println("-------------------------");
                System.out.println("1. Visualizar vehículos asignados");
                System.out.println("2. Recogida de vehículos para su reparación");
                System.out.println("3. Definición tiempo inactividad por reparación");
                System.out.println("4. Devolución de vehículos reparados");
                System.out.println("0. Salir");

                System.out.println("-------------------------");
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();

                scanner.nextLine();

                switch (opcion) {
                    case 1 -> gestorIncidencias.consultarIncidenciasPorEncargado(mantenimiento);
                    case 2 -> vehiculo = gestorIncidencias.recogerVehiculoParaReparar();
                    case 3 -> tiempoInactividad = gestorIncidencias.definirTiempoInactividadVehiculo(vehiculo, scanner);
                    case 4 -> gestorIncidencias.devolverVehiculoReparado(vehiculo, scanner);
                    case 0 -> System.out.println("Saliendo del menú de mantenimiento...");
                    default -> System.out.println("Opción no válida. Intente de nuevo.");
                }

            } while (opcion != 0);
        } catch (Exception e) {
            System.out.println("Error al gestionar opciones: " + e.getMessage());
        }
    }

}

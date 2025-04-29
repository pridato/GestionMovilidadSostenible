package es.uned.gestores;

import es.uned.enums.EstadoMoto;
import es.uned.enums.EstadoVehiculo;
import es.uned.model.Coordenadas;
import es.uned.model.vehiculos.Bicicleta;
import es.uned.model.vehiculos.Moto;
import es.uned.model.vehiculos.Patinete;
import es.uned.model.vehiculos.Vehiculo;

import java.util.List;
import java.util.Scanner;

import static es.uned.utils.dto.*;

/**
 * Clase GestorVehiculos que gestiona la lista de vehículos.
 *
 * Métodos:
 *
 * Dar de alta un vehículo
 *
 * Borrar/modificar un vehículo
 *
 * Marcar un vehículo como averiado
 *
 * Marcar un vehículo como alquilado
 *
 * Marcar un vehículo como disponible
 */
public class GestorVehiculos {

    // Atributos
    private final List<Vehiculo> vehiculos;

    // Constructor
    public GestorVehiculos() {
        this.vehiculos = cargarVehiculos();
    }


    /**
     * Método para crear nuevo vehiculo
     *
     * @param scanner Scanner para leer la entrada del usuario
     */
    public boolean añadirVehiculo(Scanner scanner) {
        System.out.println("Seleccione el tipo de vehículo:");
        System.out.println("1. Moto");
        System.out.println("2. Patinete");
        System.out.println("3. Bicicleta");
        System.out.print("Opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        Vehiculo vehiculo = null;

        switch (opcion) {
            case 1 -> {
                vehiculo = crearMoto(scanner);
            }
            case 2 -> {
                vehiculo = crearPatinete(scanner);
            }
            case 3 -> {
                vehiculo = crearBicicleta(scanner);
            }
            default -> {
                System.out.println("Opción no válida.");
            }
        }

        if (vehiculo != null && !this.vehiculos.contains(vehiculo)) {
            this.vehiculos.add(vehiculo);
            return true;
        }
        return false;
    }

    /**
     * Ya que los 3 tipos de vehículos tienen atributos comunes, se crea un método para leer los datos comunes.
     * @param scanner Scanner para leer la entrada del usuario
     * @param tipo Tipo de vehículo (moto, patinete, bicicleta)
     * @return Array de objetos con los datos comunes del vehículo
     */
    private static Object[] leerDatosComunesVehiculo(Scanner scanner, String tipo) {
        System.out.print("Introduce la matrícula del " + tipo + ": ");
        String matricula = scanner.nextLine();

        System.out.print("Introduce la coordenada X: ");
        double x = scanner.nextDouble();

        System.out.print("Introduce la coordenada Y: ");
        double y = scanner.nextDouble();
        scanner.nextLine(); // Limpieza de buffer

        Coordenadas coordenadas = new Coordenadas(x, y);

        System.out.println("Selecciona el estado del vehículo (ACTIVO, INACTIVO, MANTENIMIENTO): ");
        EstadoVehiculo estadoVehiculo = EstadoVehiculo.valueOf(scanner.nextLine().toUpperCase());

        System.out.print("Introduce el nivel de batería (0-100): ");
        int bateria = scanner.nextInt();

        if (bateria < 0 || bateria > 100) {
            throw new NumberFormatException("La batería debe estar entre 0 y 100.");
        }

        System.out.print("Introduce la tarifa por minuto (€): ");
        double tarifaMinuto = scanner.nextDouble();

        System.out.print("Introduce la penalización (€): ");
        double penalizacion = scanner.nextDouble();
        scanner.nextLine(); // Limpieza de buffer

        return new Object[]{matricula, coordenadas, estadoVehiculo, bateria, tarifaMinuto, penalizacion};
    }


    /**
     * Método para crear una moto.
     * @param scanner Scanner para leer la entrada del usuario
     * @return Vehiculo creado
     */
    private static Vehiculo crearMoto(Scanner scanner) {
        Object[] datos = leerDatosComunesVehiculo(scanner, "moto");

        System.out.println("Selecciona el estado de la moto (DISPONIBLE, OCUPADA, RESERVADA, AVERIADA): ");
        EstadoMoto estadoMoto = EstadoMoto.valueOf(scanner.nextLine().toUpperCase());

        return new Moto(
                (String) datos[0],
                (Coordenadas) datos[1],
                (EstadoVehiculo) datos[2],
                (int) datos[3],
                (double) datos[4],
                (double) datos[5],
                estadoMoto
        );
    }


    /**
     * Método para crear un patinete.
     * @param scanner Scanner para leer la entrada del usuario
     * @return Vehiculo creado
     */
    private static Vehiculo crearPatinete(Scanner scanner) {
        Object[] datos = leerDatosComunesVehiculo(scanner, "patinete");
        return new Patinete(
                (String) datos[0],
                (Coordenadas) datos[1],
                (EstadoVehiculo) datos[2],
                (int) datos[3],
                (double) datos[4],
                (double) datos[5]
        );
    }


    /**
     * Método para crear una bicicleta.
     * @param scanner Scanner para leer la entrada del usuario
     * @return Vehiculo creado
     */
    private static Vehiculo crearBicicleta(Scanner scanner) {
        Object[] datos = leerDatosComunesVehiculo(scanner, "bicicleta");
        return new Bicicleta(
                (String) datos[0],
                (Coordenadas) datos[1],
                (EstadoVehiculo) datos[2],
                (int) datos[3],
                (double) datos[4],
                (double) datos[5]
        );
    }


    /**
     * Método para eliminar un vehículo de la lista de vehículos.
     * @param vehiculo Vehiculo a eliminar.
     * @return true si se elimina correctamente, false si no existe o es null.
     */
    public boolean eliminarVehiculo(Vehiculo vehiculo) {
        if (vehiculo != null && this.vehiculos.contains(vehiculo)) {
            this.vehiculos.remove(vehiculo);
            return true;
        }
        return false;
    }

    /**
     * Método para obtener la lista de vehículos.
     * @param matricula matricula del vehículo a buscar
     * @return Lista de vehículos.
     */
    public void modificarVehiculo(String matricula) {
        //TODO: Implementar la modificación de un vehículo.
    }

    /**
     * Método para marcar un vehículo como averiado.
     * @param matricula matricula del vehículo a buscar
     * @return true si se marca correctamente, false si no existe o es null.
     */
    public boolean marcarVehiculoComoAveriado(String matricula) {
        Vehiculo vehiculo = obtenerVehiculo(matricula);
        if (vehiculo != null) {
            vehiculo.setEstado(EstadoVehiculo.AVERIADO);
            return true;
        }
        return false;
    }

    /**
     * Método para marcar un vehículo como reparado.
     * @param matricula matricula del vehículo a buscar
     * @return true si se marca correctamente, false si no existe o es null.
     */
    public boolean marcarVehiculoComoDisponible(String matricula) {
        Vehiculo vehiculo = obtenerVehiculo(matricula);
        if (vehiculo != null) {
            vehiculo.setEstado(EstadoVehiculo.DISPONIBLE);
            return true;
        }
        return false;
    }

    /**
     * Método para marcar un vehículo como alquilado.
     * @param matricula matricula del vehículo a buscar
     * @return true si se marca correctamente, false si no existe o es null.
     */
    public boolean marcarVehiculoComoAlquilado(String matricula) {
        Vehiculo vehiculo = obtenerVehiculo(matricula);
        if (vehiculo != null) {
            vehiculo.setEstado(EstadoVehiculo.ALQUILADO);
            return true;
        }
        return false;
    }

    /**
     * Método para obtener un vehículo de la lista de vehículos.
     * @param matricula matricula del vehículo a buscar
     * @return El vehículo encontrado o null si no se encuentra.
     */
    public Vehiculo obtenerVehiculo(String matricula) {
        return this.vehiculos.stream()
                .filter(vehiculo -> vehiculo.getMatricula().equals(matricula))
                .findFirst()
                .orElse(null);
    }

}

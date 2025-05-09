package es.uned.gestores;

import es.uned.model.Base;
import es.uned.model.Factura;
import es.uned.model.personas.Mecanico;
import es.uned.model.vehiculos.Vehiculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestorFacturas {

    private static final GestorFacturas instancia = new GestorFacturas();
    private static final List<Factura> facturas = new ArrayList<>();

    /**
     * Método para obtener la instancia del gestor de facturas.
     *
     * @return instancia del gestor de facturas.
     */
    public static GestorFacturas getInstancia() {
        return instancia;
    }

    /**
     * Método para crear una factura de un vehículo.
     * @param mecanico mecanico que genera la factura
     * @param base base asociada a la factura
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public void crearFacturaBase(Mecanico mecanico, Base base, Scanner scanner) {
        Factura factura = generarFacturaComun(scanner, mecanico);
        factura.setBase(base);
        facturas.add(factura);
        System.out.println("Factura creada con éxito: " + factura.getId());
    }

    /**
     * Método para crear una factura de un vehículo.
     * @param mecanico mecanico que genera la factura
     * @param vehiculo vehículo asociado a la factura
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public void crearFacturaVehiculo(Mecanico mecanico, Vehiculo vehiculo, Scanner scanner) {
        Factura factura = generarFacturaComun(scanner, mecanico);
        factura.setVehiculo(vehiculo);
        facturas.add(factura);
        System.out.println("Factura creada con éxito: " + factura.getId());
    }

    /**
     * Método para generar una factura común solo con importe y descripción.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    private Factura generarFacturaComun(Scanner scanner, Mecanico mecanico) {
        System.out.println("Introduce el importe de la factura:");
        double importe = scanner.nextDouble();
        scanner.nextLine(); // Limpiar el buffer

        System.out.println("Introduce la descripción de la factura:");
        String descripcion = scanner.nextLine();
        return new Factura(importe, descripcion, mecanico);
    }

    /**
     * Método para consultar todas las facturas registradas.
     */
    public void consultarFacturas() {
        if (facturas.isEmpty()) {
            System.out.println("No hay facturas registradas.");
        } else {
            System.out.println("Facturas registradas:");
            for (Factura factura : facturas) {
                System.out.println("ID: " + factura.getId() + ", Importe: " + factura.getImporte() +
                        ", Descripción: " + factura.getDescripcion() + ", Fecha: " + factura.getFecha());
            }
        }
    }

    /**
     * Método para consultar las facturas asociadas a un mecánico específico.
     * @param mecanico mecánico del que se quieren consultar las facturas.
     */
    public void consultarFacturasPorMecanico(Mecanico mecanico) {
        if (facturas.isEmpty()) {
            System.out.println("No hay facturas registradas.");
        } else {
            System.out.println("Facturas registradas para el mecánico " + mecanico.getNombre() + ":");
            for (Factura factura : facturas) {
                if (factura.getMecanico().equals(mecanico)) {
                    System.out.println("ID: " + factura.getId() + ", Importe: " + factura.getImporte() +
                            "€ , Descripción: " + factura.getDescripcion() + ", Fecha: " + factura.getFecha());
                }
            }
        }
    }

}

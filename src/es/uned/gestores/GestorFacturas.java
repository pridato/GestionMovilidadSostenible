package es.uned.gestores;

import es.uned.model.Base;
import es.uned.model.Factura;
import es.uned.model.personas.Mecanico;
import es.uned.model.vehiculos.Vehiculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static es.uned.utils.utils.obtenerDato;

public class GestorFacturas {

    private static final GestorFacturas instancia = new GestorFacturas();
    private static final List<Factura> facturas = new ArrayList<>();

    /**
     * Devuelve la instancia del gestor de facturas.
     *
     * @return instancia del gestor de facturas.
     */
    public static GestorFacturas getInstancia() {
        return instancia;
    }

    /**
     * Crea una factura asociada a un mecánico y una base
     * @param mecanico mecanico que genera la factura
     * @param base base asociada a la factura
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public void crearFacturaBase(Mecanico mecanico, Base base, Scanner scanner) {
        Factura factura = solicitarDatosFactura(scanner, mecanico);
        factura.setBase(base);
        mecanico.añadirFactura(factura);
        registrarFactura(factura);
    }

    /**
     * Crea una factura asociada a un mecánico y un vehículo
     * @param mecanico mecanico que genera la factura
     * @param vehiculo vehículo asociado a la factura
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public void crearFacturaVehiculo(Mecanico mecanico, Vehiculo vehiculo, Scanner scanner) {
        Factura factura = solicitarDatosFactura(scanner, mecanico);
        factura.setVehiculo(vehiculo);
        mecanico.añadirFactura(factura);
        registrarFactura(factura);
    }

    /**
     * Registra la factura en el sistema y muestra su ID.
     */
    private void registrarFactura(Factura factura) {
        facturas.add(factura);
        System.out.println("Factura creada con éxito. ID: " + factura.getId());
    }

    /**
     * Creamos una factura solicitando importe y descripción al usuario.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    private Factura solicitarDatosFactura(Scanner scanner, Mecanico mecanico) {
        try {
            // solicitamos importe y descripción para crear factura
            double importe = Double.parseDouble(obtenerDato(scanner, "Introduce la descripción de la factura:"));
            String descripcion = obtenerDato(scanner, "Introduce el importe de la factura:");

            return new Factura(importe, descripcion, mecanico);
        } catch(Exception e) {
            System.out.println("ERROR: Debes introducir un número. Inténtalo de nuevo.");
            return solicitarDatosFactura(scanner, mecanico); // Llamada recursiva para volver a solicitar los datos

        }
    }

    /**
     * Consulta las facturas registradas
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
     * Consulta las facturas filtrando por mecánico.
     * @param mecanico mecánico del que se quieren consultar las facturas.
     */
    public void consultarFacturasPorMecanico(Mecanico mecanico) {
        List<Factura> facturasDelMecanico = mecanico.getFacturasEmitidas();

        if (facturasDelMecanico.isEmpty()) {
            System.out.println("No hay facturas registradas para el mecánico " + mecanico.getNombre() + ".");
            return;
        }

        System.out.println("Facturas del mecánico " + mecanico.getNombre() + ":");
        facturasDelMecanico.forEach(Factura::mostrarFactura);
    }

}

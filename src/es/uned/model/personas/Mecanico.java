package es.uned.model.personas;

import es.uned.model.Factura;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Clase que representa un mecánico, que hereda de la clase Trabajador.
 */
public class Mecanico extends Trabajador{

    private final List<Factura> facturasEmitidas = new ArrayList<>();

    public Mecanico(String DNI, String nombre, String apellidos, String email, int telefono, Date fechaContratacion) {
        super(DNI, nombre, apellidos, email, telefono, fechaContratacion);
    }

    /**
     * Permite añadir una factura a la lista de facturas emitidas por el mecánico.
     * @param factura Factura a añadir.
     */
    public void añadirFactura(Factura factura) {
        facturasEmitidas.add(factura);
    }

    /**
     * Devuelve una copia de la lista de facturas emitidas por el mecánico.
     * @return Lista de facturas emitidas.
     */
    public List<Factura> getFacturasEmitidas() {
        return Collections.unmodifiableList(facturasEmitidas);
    }


    @Override
    public String toString() {
        final String INDENT = "    ";
        StringBuilder sb = new StringBuilder();
        sb.append("Mecanico {\n");
        sb.append(super.toString());

        sb.append(INDENT).append("Facturas emitidas:\n");

        if (!facturasEmitidas.isEmpty()) {
            for (Factura factura : facturasEmitidas) {
                sb.append(INDENT).append(INDENT).append("- ").append(factura).append("\n");
            }
        } else {
            sb.append(INDENT).append(INDENT).append("(Sin facturas emitidas)\n");
        }

        sb.append("}");
        return sb.toString();
    }

}

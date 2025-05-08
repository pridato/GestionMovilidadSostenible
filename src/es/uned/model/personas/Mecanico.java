package es.uned.model.personas;

import es.uned.model.Factura;

import java.util.Date;
import java.util.List;

/**
 * Clase que representa un mecánico, que hereda de la clase Trabajador.
 */
public class Mecanico extends Trabajador{

    private List<Factura> facturasEmitidas;

    public Mecanico(String DNI, String nombre, String apellidos, String email, int telefono, Date fechaContratacion, List<Factura> facturasEmitidas) {
        super(DNI, nombre, apellidos, email, telefono, fechaContratacion);
        this.facturasEmitidas = facturasEmitidas;
    }



    public List<Factura> getFacturasEmitidas() {
        return facturasEmitidas;
    }

    public void setFacturasEmitidas(List<Factura> facturasEmitidas) {
        this.facturasEmitidas = facturasEmitidas;
    }

    @Override
    public String toString() {
        final String INDENT = "    ";
        StringBuilder sb = new StringBuilder();
        sb.append("Mecanico {\n");
        sb.append(super.toString());

        sb.append(INDENT).append("Facturas emitidas:\n");

        if (facturasEmitidas != null && !facturasEmitidas.isEmpty()) {
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

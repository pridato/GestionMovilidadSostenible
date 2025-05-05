package es.uned.gestores;

import es.uned.model.Incidencia;

import java.util.ArrayList;
import java.util.List;

import static es.uned.utils.dto.cargarIncidencias;

public class GestorIncidencias {

    private static final GestorIncidencias instancia = new GestorIncidencias();

    private List<Incidencia> incidencias = new ArrayList<>();

    /* constructor */
    private GestorIncidencias() {
        this.incidencias = cargarIncidencias();
    }

    public static GestorIncidencias getInstancia() {
        return instancia;
    }

    /**
     * Método para visualizar los problemas de los vehículos
     */
    public void visualizarProblemasVehículos() {
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getVehiculo() != null) {
                System.out.println("Vehículo: " + incidencia.getVehiculo().getMatricula());
                System.out.println("Problema: " + incidencia.getDescripcion());
                System.out.println("Fecha: " + incidencia.getFechaReporte());
                System.out.println();
            }
        }
    }

    /**
     * Método para visualizar los problemas de las bases
     */
    public void visualizarProblemasBases() {
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getBase() != null) {
                System.out.println("Base: " + incidencia.getBase().getId());
                System.out.println("vehiculos disponibles: " + incidencia.getBase().getCapacidadMaxima());
                System.out.println("Capacidad actual: " + incidencia.getBase().getVehiculosAlquilados().size());
                System.out.println("Problema: " + incidencia.getDescripcion());
                System.out.println("Fecha: " + incidencia.getFechaReporte());
                System.out.println();
            }
        }
    }
}

package es.uned.gestores;

import es.uned.model.Base;
import es.uned.model.personas.Mantenimiento;
import es.uned.model.personas.Mecanico;
import es.uned.model.personas.Trabajador;
import es.uned.model.vehiculos.Vehiculo;

import java.util.List;

import static es.uned.utils.dto.cargarTrabajadores;

/**
 * TODO: Consultar tareas pendientes
 *
 * Clase GestorTrabajadores.
 *
 * Métodos principales:
 *
 * Crear trabajadores
 *
 * Asignar vehículos a mecánicos
 *
 * Asignar bases a trabajadores de mantenimiento
 *
 * Consultar tareas pendientes
 */
public class GestorTrabajadores {

    private final List<Trabajador> trabajadores;

    /* constructor */
    public GestorTrabajadores() {
        this.trabajadores = cargarTrabajadores();
    }

    /**
     * Método para crear un nuevo trabajador.
     *
     * @param trabajador trabajador a añadir
     * @return true si se ha añadido correctamente, false en caso contrario
     */
    public boolean añadirTrabajador(Trabajador trabajador) {
        if (trabajador != null) {
            this.trabajadores.add(trabajador);
            return true;
        }
        return false;
    }

    /**
     * Método para asignar un vehículo a un mecánico.
     * @param DNI identificador del mecánico
     * @param vehiculo vehículo a asignar
     * @return true si se ha asignado correctamente, false en caso contrario
     */
    public boolean asignarVehiculoMecanico(String DNI, Vehiculo vehiculo) {
        return this.trabajadores.stream()
                .filter(trabajador -> trabajador.getDNI().equals(DNI) && trabajador instanceof Mecanico)
                .findFirst()
                .map(trabajador -> {
                    ((Mecanico) trabajador).asignarVehiculo(vehiculo);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Método para asignar una base a un trabajador de mantenimiento.
     * @param DNI identificador del trabajador de mantenimiento
     * @param base base a asignar
     * @return true si se ha asignado correctamente, false en caso contrario
     */
    public boolean asignarBaseMantenimiento(String DNI, Base base) {
        return this.trabajadores.stream()
                .filter(trabajador -> trabajador.getDNI().equals(DNI) && trabajador instanceof Mantenimiento)
                .findFirst()
                .map(trabajador -> {
                    ((Mantenimiento) trabajador).asignarBase(base);
                    return true;
                })
                .orElse(false);
    }
}

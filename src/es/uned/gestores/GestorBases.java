package es.uned.gestores;

import es.uned.model.Base;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static es.uned.utils.dto.cargarBases;

/**
 * Clase GestorBases.
 * Esta clase se encarga de gestionar las bases de vehículos.
 *
 * Métodos:
 *
 * Alta de bases
 *
 * Modificar bases
 *
 * Eliminar bases
 *
 * Marcar bases averiadas
 *
 * Consultar bases disponibles
 *
 * Consultar bases por ocupación
 */
public class GestorBases {

    private final List<Base> bases;

    /* Constructor de la clase GestorBases. */
    public GestorBases() {
        this.bases = new ArrayList<>(cargarBases());
    }

    /**
     * Método para añadir una base a la lista de bases.
     * @return true si se ha añadido correctamente, false en caso contrario.
     */
    public boolean altaBase(Base base) {
        if (base != null) {
            this.bases.add(base);
            return true;
        }
        return false;
    }

    /**
     * Método para modificar una base de la lista de bases.
     * @param base Base a buscar.
     * @return true si se ha modificado correctamente, false en caso contrario.
     */
    public boolean modificarBase(Base base) {
        // TODO: modificar la base
        return false;
    }

    /**
     * Método para eliminar una base de la lista de bases.
     * @param base Base a eliminar.
     * @return true si se ha eliminado correctamente, false en caso contrario.
     */
    public boolean eliminarBase(Base base) {
        if (base != null) {
            this.bases.remove(base);
            return true;
        }
        return false;
    }

    /**
     * Método para marcar una base como averiada.
     * @param base Base a marcar como averiada.
     * @return true si se ha marcado correctamente, false en caso contrario.
     */
    public boolean marcarBaseComoAveriada(Base base) {
        if (base != null) {
            base.setAveriada(true);
            return true;
        }
        return false;
    }

    /**
     * Método para obtener una lista de bases disponibles.
     * @return Lista de bases disponibles.
     */
    public List<Base> getBasesDisponibles() {
        return this.bases.stream()
                .filter(base -> !base.isAveriada())
                .toList();
    }


    /**
     * Método para consultar una base por su ID.
     * @param id ID de la base a consultar.
     * @return Base encontrada o null si no se encuentra.
     */
    public Base consultarBasePorId(String id) {
        return this.bases.stream()
                .filter(base -> Objects.equals(base.getId(), id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Método para consultar bases disponibles por ocupación.
     */
    public void consultarBasesDisponiblesPorOcupacion() {
        if (this.bases.isEmpty()) {
            System.out.println("No hay bases disponibles.");
            return;
        }
        this.bases.stream()
                .filter(base -> !base.isAveriada())
                .forEach(b -> System.out.println(b.toString()));
    }
}

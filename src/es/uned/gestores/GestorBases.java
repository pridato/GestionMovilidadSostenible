package es.uned.gestores;

import es.uned.model.Base;

import java.util.List;

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
        this.bases = cargarBases();
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
     * Método para obtener una lista de bases ordenada por ocupación.
     * @return Lista de bases ordenada por ocupación.
     */
    public List<Base> consultarBasesPorOcupacion() {
        return this.bases.stream()
                .sorted((base1, base2) -> {
                    int ocupacionBase1 = base1.getVehiculosAlquilados().size();
                    int ocupacionBase2 = base2.getVehiculosAlquilados().size();
                    return Integer.compare(ocupacionBase1, ocupacionBase2);
                })
                .toList();
    }
}

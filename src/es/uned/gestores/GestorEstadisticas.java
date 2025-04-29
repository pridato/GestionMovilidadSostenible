package es.uned.gestores;

import es.uned.model.Alquiler;
import es.uned.model.Base;
import es.uned.model.personas.Usuario;

import java.util.List;

public class GestorEstadisticas {

    /**
     * Obtiene la lista de bases ordenadas por el número de vehículos alquilados, es decir las más utilizadas
     * @param bases lista de bases a ordenar
     * @return lista de bases ordenadas por el número de vehículos alquilados
     */
    public static List<Base> obtenerBasesMasUtilizadas(List<Base> bases) {
        bases.sort((b1, b2) -> Integer.compare(
                b2.getVehiculosAlquilados() != null ? b2.getVehiculosAlquilados().size() : 0,
                b1.getVehiculosAlquilados() != null ? b1.getVehiculosAlquilados().size() : 0
        ));
        return bases;
    }

    /**
     * Obtiene la lista de usuarios ordenados por el número de viajes realizados, es decir los más frecuentes
     * @param usuarios lista de usuarios a ordenar
     * @return lista de usuarios ordenados por el número de viajes realizados
     */
    public static List<Usuario> obtenerUsuariosFrecuentes(List<Usuario> usuarios) {
        usuarios.sort((u1, u2) -> Integer.compare(
                u2.getHistorialViajes().size(),
                u1.getHistorialViajes().size()
        ));
        return usuarios;
    }

}

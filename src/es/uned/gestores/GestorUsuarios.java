package es.uned.gestores;

import es.uned.model.Alquiler;
import es.uned.model.personas.Usuario;

import java.util.List;

import static es.uned.utils.dto.cargarUsuarios;

/**
 * Clase GestorUsuarios.
 *
 * Métodos principales:
 *
 * Crear usuario
 *
 * Borrar usuario
 *
 * Modificar usuario
 *
 * Promocionar usuario a Premium
 *
 * Recargar saldo
 *
 * Consultar historial de viajes
 */
public class GestorUsuarios {

    List<Usuario> usuarios;

    /* constructor */
    public GestorUsuarios() {
        this.usuarios = cargarUsuarios();
    }

    /**
     * Método para añadir nuevos usuarios
     * @param usuario usuario a añadir
     * @return true si se ha añadido correctamente, false en caso contrario
     */
    public boolean añadirUsuario(Usuario usuario) {
        if (usuario != null) {
            this.usuarios.add(usuario);
            return true;
        }
        return false;
    }

    /**
     * Método para buscar un usuario por su DNI.
     * @param DNI identificador del usuario a buscar.
     * @return El usuario encontrado o null si no se encuentra.
     */
    public boolean eliminarUsuario(String DNI) {
       return this.usuarios.removeIf(usuario -> usuario.getDNI().equals(DNI));
    }

    public void modificarUsuario(String DNI) {
        // TODO: Implementar lógica para modificar un usuario
    }

    /**
     * Método para promocionar un usuario a premium.
     * @param DNI El DNI del usuario a promocionar.
     */
    public void promocionarUsuarioPremium(String DNI) {
        this.usuarios.stream()
                .filter(usuario -> usuario.getDNI().equals(DNI))
                .findFirst()
                .ifPresent(usuario -> usuario.setEsPremium(true));
    }

    /**
     * Método para buscar un usuario por su DNI.
     * @param DNI El DNI del usuario a buscar.
     * @return El usuario encontrado o null si no se encuentra.
     */
    private Usuario buscarUsuario(String DNI) {
        return this.usuarios.stream()
                .filter(usuario -> usuario.getDNI().equals(DNI))
                .findFirst()
                .orElse(null);
    }

    /**
     * Método para recargar el saldo de un usuario.
     * @param DNI identificador del usuario.
     * @param cantidad cantidad a recargar.
     * @return true si se ha recargado correctamente, false en caso contrario.
     */
    public boolean recargarSaldo(String DNI, double cantidad) {
        Usuario usuario = buscarUsuario(DNI);
        if (usuario != null) {
            usuario.recargarSaldo(cantidad);
            return true;
        }
        return false;
    }

    /**
     * Método para consultar el historial de viajes de un usuario.
     * @param DNI identificador del usuario.
     * @return lista de alquileres del usuario o null si no se encuentra.
     */
    public List<Alquiler> consultarHistorialViajes(String DNI) {
        Usuario usuario = buscarUsuario(DNI);
        if (usuario != null) {
            return usuario.getHistorialViajes();
        }
        return null;
    }
}

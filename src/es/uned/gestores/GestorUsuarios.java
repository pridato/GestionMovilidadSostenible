package es.uned.gestores;

import es.uned.model.Alquiler;
import es.uned.model.personas.Usuario;

import java.util.ArrayList;
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

    private static final GestorUsuarios instancia = new GestorUsuarios();

    private List<Usuario> usuarios = new ArrayList<>();

    private int descuentoPremium = 10; // descuento del 10% (defecto) para usuarios premium


    /* constructor */
    public GestorUsuarios() {
        this.usuarios = new ArrayList<>(cargarUsuarios()); // evitamos problema inmutabilidad
    }

    /**
     * Método para obtener la instancia del gestor de usuarios.
     * @return instancia del gestor de usuarios.
     */
    public static GestorUsuarios getInstancia() {
        return instancia;
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

    /**
     * Método para obtener la lista de usuarios.
     * @return lista de usuarios.
     */
    public void consultarUsuarios() {
        this.usuarios.forEach(usuario -> System.out.println(usuario.toString()));
    }

    /**
     * Método para obtener la lista de usuarios premium.
     * @return lista de usuarios premium.
     */
    public void consultarUsuariosPremium() {
        this.usuarios.stream().filter(Usuario::getEsPremium).forEach(usuario -> System.out.println(usuario.toString()));
    }

    /**
     * Método para obtener la lista de usuarios no premium.
     * @return lista de usuarios no premium.
     */
    public void consultarUsuariosNoPremium() {
        this.usuarios.stream().filter(usuario -> !usuario.getEsPremium()).forEach(usuario -> System.out.println(usuario.toString()));
    }

    public int getDescuentoPremium() {
        return descuentoPremium;
    }

    public void setDescuentoPremium(int descuentoPremium) {
        this.descuentoPremium = descuentoPremium;
    }

    /**
     * Método para listar todos los usuarios.
     */
    public void listarUsuarios() {
        for(int i=0; i<usuarios.size(); i++){
            System.out.println(i +" -> " + usuarios.get(i).getDNI() + " " + usuarios.get(i).getNombre() + " " + usuarios.get(i).getApellidos());
        }
    }

    /**
     * Método para obtener un usuario por su índice.
     * @param i índice del usuario en la lista.
     * @return usuario en la posición i.
     */
    public Usuario obtenerUsuarioIndice(int i) {
        return usuarios.get(i);
    }
}

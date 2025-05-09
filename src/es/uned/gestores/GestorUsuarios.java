package es.uned.gestores;

import es.uned.model.personas.Usuario;

import java.util.List;

import static es.uned.menus.MenuAdministrador.gestorPersonas;

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


    /**
     * Funciona como una variable global para almacenar los usuarios.
     * Se usa un get para hacer posible la modificación de la lista de usuarios.
     * 
     * @return lista de usuarios.
     */
    private List<Usuario> getUsuarios() {
        return gestorPersonas.getPersonas().stream()
                .filter(p -> p instanceof Usuario)
                .map(p -> (Usuario) p)
                .toList();
    }

    /**
     * Método para obtener la instancia del gestor de usuarios.
     * @return instancia del gestor de usuarios.
     */
    public static GestorUsuarios getInstancia() {
        return instancia;
    }


    /**
     * Método para listar todos los usuarios.
     */
    public void listarUsuarios() {
        List<Usuario> usuarios = getUsuarios();
        for(int i=0; i<usuarios.size(); i++){
            System.out.println(i +" -> " + usuarios.get(i).getdni() + " " + usuarios.get(i).getNombre() + " " + usuarios.get(i).getApellidos());
        }
    }

    /**
     * Método para obtener un usuario por su índice.
     * @param i índice del usuario en la lista.
     * @return usuario en la posición i.
     */
    public Usuario obtenerUsuarioIndice(int i) {
        return getUsuarios().get(i);
    }
}

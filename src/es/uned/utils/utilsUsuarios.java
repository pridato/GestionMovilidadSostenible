package es.uned.utils;

import es.uned.model.Coordenadas;
import es.uned.model.personas.Persona;
import es.uned.model.personas.Usuario;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static es.uned.model.personas.Persona.validarDNI;

/**
 * Clase de utilidades para la gestión de usuarios.
 */
public class utilsUsuarios {

    /**
     *  Método para mostrar el menú de gestión de usuarios.
     */
    public void mostrarMenuGestionUsuarios(){
        System.out.println();
        System.out.println("----------------------------");
        System.out.println("Gestión de usuarios");
        System.out.println("----------------------------");
        System.out.println();
        System.out.println("1. Añadir usuario");
        System.out.println("2. Modificar usuario");
        System.out.println("3. Eliminar usuario");
        System.out.println("4. Listar usuarios");
    }

    /**
     * Método para crear un nuevo usuario.
     * @param sc Scanner para leer la entrada del usuario.
     * @return Un objeto Usuario con los datos ingresados.
     */
    public Usuario crearUsuario(Scanner sc) {
        System.out.print("Ingrese el DNI del usuario: ");
        String dni = sc.next();

        validarDNI(dni);
        System.out.print("Ingrese el nombre del usuario: ");
        String nombre = sc.next();
        System.out.print("Ingrese los apellidos del usuario: ");
        String apellidos = sc.next();
        sc.nextLine();
        System.out.print("Ingrese el email del usuario: ");
        String email = sc.next();
        System.out.print("Ingrese el teléfono del usuario: ");
        int telefono = sc.nextInt();
        sc.nextLine();

        Date fechaCreacion = new Date();

        Coordenadas coordenadas = new Coordenadas(0.0, 0.0);

        return new Usuario(dni, nombre, apellidos, email, telefono, fechaCreacion, coordenadas);
    }

    /**
     * Método para listar las personas.
     * @param personas Lista de personas a listar.
     */
    public void listarPersonas(List <Persona> personas) {
        if (personas != null && !personas.isEmpty()) {
            System.out.println("Lista de personas:");
            for (Persona persona : personas) {
                System.out.println(persona);
            }
        } else {
            System.out.println("No hay personas para mostrar.");
        }
    }
}

package es.uned.model;

import es.uned.model.personas.Persona;
import es.uned.model.personas.Trabajador;
import es.uned.model.personas.Usuario;
import es.uned.model.vehiculos.Vehiculo;
import es.uned.utils.dto;

import java.util.*;
import java.util.stream.Collectors;

import static es.uned.utils.dto.*;

public class GestorMovilidad {

    private List<Persona> personas;
    private List<Vehiculo> vehiculos;
    private List<Alquiler> alquileres;
    private List<Factura> facturas;
    private List<Incidencia> incidencias;
    private List<Base> bases;
    private dto dto;

    private Map<Vehiculo, Double> tarifas;

    public GestorMovilidad() {

        // Inicializar las listas
        this.personas = cargarPersonas();
        this.vehiculos = cargarVehiculos();
        this.alquileres = cargarAlquileres();
        this.facturas = cargarFacturas();
        this.incidencias = cargarIncidencias();
        this.bases = cargarBases();
    }

    // ----------------------------
    // Gestión de usuarios
    // ----------------------------

    /**
     * Método para añadir un usuario a la lista de usuarios.
     * @param usuario El usuario a añadir.
     * @return true si se ha añadido correctamente, false en caso contrario.
     */
    public boolean añadirUsuario(Usuario usuario) {
        if (usuario != null) {
            this.personas.add(usuario);
            return true;
        }
        return false;
    }

    /**
     * Método para buscar un usuario por su DNI.
     * @param dni DNI del usuario a buscar.
     * @return El usuario encontrado, o null si no se encuentra.
     */
    private Persona buscarPersonaPorDni(String dni) {
        return personas.stream()
                .filter(p -> p.getDNI().equals(dni))
                .findFirst()
                .orElse(null);
    }

    /**
     * Método para actualizar un usuario en la lista de usuarios.
     * @param dni DNI del usuario a actualizar.
     * @return true si se ha actualizado correctamente, false en caso contrario.
     */
    public boolean eliminarUsuario(String dni) {
        Persona persona = buscarPersonaPorDni(dni);
        if (persona != null) {
            personas.remove(persona);
            return true;
        }
        return false;
    }


    /**
     * Método para modificar un usuario en la lista de usuarios.
     * @param DNI DNI del usuario a modificar.
     * @param sc Scanner para leer la entrada del usuario.
     * @return true si se ha modificado correctamente, false en caso contrario.
     */
    public boolean modificarPersona(String DNI, Scanner sc) {

        // Generamos un stream de índices para recorrer la lista de personas
        OptionalInt index = personas.stream()
                .mapToInt(persona -> personas.indexOf(persona))
                .filter(i -> personas.get(i).getDNI().equals(DNI))
                .findFirst();

        // Si el índice es presente, se actualiza la persona
        if (index.isPresent()) {
            int i = index.getAsInt();

            System.out.println("Modificar persona: Completa los campos que quieras modificar (dejar en blanco para no modificar):");

            System.out.print("Nuevo nombre: ");
            String nombre = sc.nextLine();

            if (nombre.isEmpty()) {
                nombre = personas.get(i).getNombre();
            }

            System.out.print("Nuevos apellidos: ");
            String apellidos = sc.nextLine();

            if (apellidos.isEmpty()) {
                apellidos = personas.get(i).getApellidos();
            }

            System.out.print("Nuevo DNI: ");
            String dni = sc.nextLine();

            if (dni.isEmpty()) {
                dni = personas.get(i).getDNI();
            }

            System.out.print("Nuevo email:  ");
            String email = sc.nextLine();

            if (email.isEmpty()) {
                email = personas.get(i).getEmail();
            }

            System.out.print("Nuevo teléfono: (0 dejar sin cambios) ");
            int telefono = Integer.parseInt(sc.nextLine());

            if (telefono == 0) {
                telefono = personas.get(i).getTelefono();
            }

            // Actualizamos la persona
            personas.get(i).setNombre(nombre);
            personas.get(i).setApellidos(apellidos);
            personas.get(i).setDNI(dni);
            personas.get(i).setEmail(email);
            personas.get(i).setTelefono(telefono);
            System.out.println("Persona actualizada correctamente.");
            return true;
        }
        return false;
    }

    /**
     * Método para obtener un usuario de la lista de personas.
     * @return Lista de personas.
     */
    public List<Persona> getPersonas() {
        return personas;
    }

    /**
     * Método para obtener un usuario de la lista de usuarios.
     * @return Lista de usuarios.
     */
    public List<Persona> getTrabajadores() {
        return personas.stream()
                .filter(p -> p instanceof Trabajador)
                .toList();
    }

    /**
     * Método para obtener un usuario de la lista de usuarios que sean premium.
     * @return Lista de usuarios.
     */
    public List<Persona> getUsuariosPremium() {
        return personas.stream()
                .filter(p -> p instanceof Usuario && ((Usuario) p).getEsPremium())
                .collect(Collectors.toList());
    }

    /**
     * Método para obtener un usuario de la lista de usuarios que no sean premium.
     * @return Lista de usuarios.
     */
    public List<Persona> getUsuariosNoPremium() {
        return personas.stream()
                .filter(p -> p instanceof Usuario && !((Usuario) p).getEsPremium())
                .collect(Collectors.toList());
    }


    // ----------------------------
    // Gestión de vehículos
    // ----------------------------

    /**
     * Método para añadir un vehículo a la lista de vehículos.
     *
     * @param vehiculo El vehículo a añadir.
     */
    public void añadirVehiculo(Vehiculo vehiculo) {
        this.vehiculos.add(vehiculo);
    }

    /**
     * Método para eliminar un vehículo de la lista de vehículos.
     * @param matricula Matrícula del vehículo a eliminar.
     * @param vehiculoActualizado Vehículo actualizado.
     * @return true si se ha eliminado correctamente, false en caso contrario.
     */
    public boolean actualizarVehiculo(String matricula, Vehiculo vehiculoActualizado) {
        for (int i = 0; i < vehiculos.size(); i++) {
            if (vehiculos.get(i).getMatricula().equals(matricula)) {
                vehiculos.set(i, vehiculoActualizado);
                return true;
            }
        }
        return false;
    }

    /**
     * Método para eliminar un vehículo de la lista de vehículos.
     * @param matricula Matrícula del vehículo a eliminar.
     * @return true si se ha eliminado correctamente, false en caso contrario.
     */
    public boolean eliminarVehiculo(String matricula) {
        for (int i = 0; i < vehiculos.size(); i++) {
            if (vehiculos.get(i).getMatricula().equals(matricula)) {
                vehiculos.remove(i);
                return true;
            }
        }
        return false;
    }

    // ----------------------------
    // Gestión de tarifas
    // ----------------------------

    /**
     * Método para obtener la tarifa de un vehículo.
     * @param vehiculo Vehículo del que se quiere obtener la tarifa.
     * @param tarifa Tarifa del vehículo.
     */
    public void añadirTarifa(Vehiculo vehiculo, double tarifa) {
        if (vehiculo != null && tarifa >= 0) {
            tarifas.put(vehiculo, tarifa);
        } else {
            System.out.println("Error: Vehículo o tarifa no válida.");
        }
    }

    /**
     * Método para obtener la tarifa de un vehículo.
     * @param vehiculo Vehículo del que se quiere obtener la tarifa.
     * @param tarifa Tarifa del vehículo.
     * @return true si se ha actualizado correctamente, false en caso contrario.
     */
    public boolean actualizarTarifa(Vehiculo vehiculo, double tarifa) {
        if (tarifas.containsKey(vehiculo)) {
            tarifas.put(vehiculo, tarifa);
            return true;
        }
        return false;
    }

    /**
     * Método para obtener la tarifa de un vehículo.
     * @param vehiculo Vehículo del que se quiere obtener la tarifa.
     * @return La tarifa del vehículo, o 0.0 si no se encuentra.
     */
    public double obtenerTarifa(Vehiculo vehiculo) {
        return tarifas.getOrDefault(vehiculo, 0.0);
    }

    // ----------------------------
    // Gestión de descuentos para usuarios premium
    // ----------------------------
}

package es.uned.gestores;

import es.uned.model.Alquiler;
import es.uned.model.Coordenadas;
import es.uned.model.personas.*;
import es.uned.utils.GeolocalizacionPorIP;

import java.text.SimpleDateFormat;
import java.util.*;

import static es.uned.menus.MenuAdministrador.leerCoordenadasManualmente;
import static es.uned.utils.dto.cargarPersonas;
import static es.uned.utils.dto.cargarTrabajadores;

public class GestorPersonas {

    private static final GestorPersonas instancia = new GestorPersonas();

    private final Set<Persona> personas = new HashSet<>();


    /* constructor */
    public GestorPersonas() {
        this.personas.addAll(cargarPersonas());
        this.personas.addAll(cargarTrabajadores());
    }

    /**
     * Método para obtener la instancia del gestor de personas.
     *
     * @return instancia del gestor de personas.
     */
    public static GestorPersonas getInstancia() {
        return instancia;
    }

    /**
     * Método para crear nuevas personas (Usuarios, Administradores, Mecánicos y Mantenimiento).
     *
     * @param scanner scanner para leer la entrada del usuario
     */
    public void crearPersona(Scanner scanner) {
        System.out.print("DNI: ");
        String dni = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();

        System.out.print("Correo electrónico: ");
        String email = scanner.nextLine();

        System.out.print("Telefono: ");
        int telefono = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Elija el tipo de Persona a crear:");
        System.out.println("1. Usuario");
        System.out.println("2. Administrador");
        System.out.println("3. Mecanico");
        System.out.println("4. Mantenimiento");

        int opcionTipo = scanner.nextInt();
        scanner.nextLine();

        Date fecha = new Date();

        if (opcionTipo != 1 && opcionTipo < 5) {
            System.out.println("Fecha de contratación (dd/MM/yyyy): ");
            String fechaContratacion = scanner.nextLine();
            // Parsear la fecha
            fecha = new Date();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fecha = sdf.parse(fechaContratacion);
            } catch (Exception e) {
                System.out.println("Error al parsear la fecha. Usando fecha actual.");
            }
        }
        Persona persona = null;


        switch (opcionTipo) {
            case 1 -> {
                System.out.println("¿Desea usar localización automática por IP? (S/N): ");
                String opcion = scanner.nextLine().trim().toUpperCase();

                Coordenadas coordenadas;
                if (opcion.equals("S")) {
                    coordenadas = GeolocalizacionPorIP.obtenerCoordenadasDesdeIP();
                    if (coordenadas == null) {
                        System.out.println("No se pudo obtener la ubicación automáticamente. Introduzca manualmente.");
                        coordenadas = leerCoordenadasManualmente(scanner);
                    }
                } else {
                    coordenadas = leerCoordenadasManualmente(scanner);
                }

                persona = new Usuario(dni, nombre, apellidos, email, telefono, coordenadas);
            }
            case 2 -> persona = new Administrador(dni, nombre, apellidos, email, telefono, fecha);
            case 3 -> persona = new Mecanico(dni, nombre, apellidos, email, telefono, fecha, null, null);
            case 4 -> persona = new Mantenimiento(dni, nombre, apellidos, email, telefono, fecha);
            default -> {
            }
        }


        if (persona != null) {
            if (!personas.add(persona)) {
                throw new IllegalArgumentException("Ya existe una persona con ese DNI.");
            }
            System.out.println("Persona creada: " + persona);
        } else {
            System.out.println("Error al crear la persona. ");
        }

    }


    /**
     * Método para eliminar personas del sistema
     *
     * @param scanner scanner para leer la entrada del usuario
     */
    public void eliminarPersona(Scanner scanner) {
        System.out.println("Introduzca el DNI de la persona a eliminar: ");
        String dni = scanner.nextLine();

        personas.removeIf(persona -> persona.getDNI().equals(dni));

    }

    /**
     * Método para modificar los datos de una persona.
     * @param scanner scanner para leer la entrada del usuario
     */
    public void modificarPersona(Scanner scanner) {
        System.out.println("Introduzca el DNI de la persona a modificar: ");
        String dni = scanner.nextLine();
        Persona persona = buscarPersonaPorDNI(dni);

        if (persona == null) {
            System.out.println("Persona no encontrada.");
            return;
        }

        // hacer un switch case de todos los atributos de la persona a modificar
        int opcion = 0;
        do {
            System.out.println("¿Qué atributo desea modificar?");
            System.out.println("1. Nombre");
            System.out.println("2. Apellidos");
            System.out.println("3. DNI");
            System.out.println("4. Email");
            System.out.println("5. Telefono");
            System.out.println("6. Salir");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch(opcion) {
                case 1 -> {
                    System.out.println("Introduzca el nuevo nombre: ");
                    String nuevoNombre = scanner.nextLine();
                    persona.setNombre(nuevoNombre);
                }
                case 2 -> {
                    System.out.println("Introduzca los nuevos apellidos: ");
                    String nuevosApellidos = scanner.nextLine();
                    persona.setApellidos(nuevosApellidos);
                }
                case 3 -> {
                    System.out.println("Introduzca el nuevo DNI: ");
                    String nuevoDNI = scanner.nextLine();
                    persona.setDNI(nuevoDNI);
                }
                case 4 -> {
                    System.out.println("Introduzca el nuevo email: ");
                    String nuevoEmail = scanner.nextLine();
                    persona.setEmail(nuevoEmail);
                }
                case 5 -> {
                    System.out.println("Introduzca el nuevo telefono: ");
                    int nuevoTelefono = scanner.nextInt();
                    persona.setTelefono(nuevoTelefono);
                }
                case 6 -> {
                    System.out.println("Saliendo...");
                }
                default -> {
                    System.out.println("Opción no válida.");
                }
            }
        } while(opcion != 6);

    }


    /**
     * Método para listar todas las personas del sistema.
     */
    public void listarPersonas() {
        System.out.println("Lista de personas:");
        for (Persona persona : personas) {
            System.out.println(persona);
        }
    }



    /**
     * Método para buscar una persona por su DNI.
     * @param dni DNI de la persona a buscar.
     * @return Persona encontrada o null si no se encuentra.
     */
    public Persona buscarPersonaPorDNI(String dni) {
        return personas.stream()
                .filter(persona -> persona.getDNI().equals(dni))
                .findFirst()
                .orElse(null);
    }


    /**
     * Método para obtener la lista de usuarios que deben ser premium.
     */
    public void usuariosDeberianSerPremium() {
        for (Persona persona : personas) {
            if (persona instanceof Usuario usuario && usuario.getHistorialViajes().size() > 4) {
                System.out.println("El usuario " + usuario.getNombre() + " " + usuario.getApellidos() + " debería ser premium.");
            }
        }
    }

    /**
     * Método para obtener la lista de trabajadores disponibles.
     */
    public void consultarTrabajadoresDisponibles() {
        // mostrar solo mecanicos y mantenimiento
        for (Persona persona : personas) {
            if (persona instanceof Mecanico mecanico) {
                System.out.println("El mecánico " + mecanico.getNombre() + " " + mecanico.getApellidos() + "con dni "+ mecanico.getDNI() + " está disponible.");
            } else if (persona instanceof Mantenimiento mantenimiento) {
                System.out.println("El mantenimiento " + mantenimiento.getNombre() + " " + mantenimiento.getApellidos()  + "con dni "+ mantenimiento.getDNI() + " está disponible.");
            }
        }
    }

    /**
     * Método para obtener la lista de usuarios que han utilizado vehículos.
     */
    public void utilizacionVehiculosPorUsuario() {
        for (Persona persona : personas) {
            if (persona instanceof Usuario usuario) {
                System.out.println("El usuario " + usuario.getNombre() + " " + usuario.getApellidos() + " ha utilizado los siguientes vehículos:");
                for (Alquiler alquiler : usuario.getHistorialViajes()) {
                    System.out.println(alquiler.getVehiculo());
                    System.out.println("Fecha de alquiler: " + alquiler.getFechaInicio() + " - " + alquiler.getFechaFin());
                }
            }
        }
    }

}



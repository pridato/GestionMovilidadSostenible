package es.uned.gestores;

import es.uned.model.Coordenadas;
import es.uned.model.personas.*;
import es.uned.utils.GeolocalizacionPorIP;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static es.uned.menus.MenuAdministrador.leerCoordenadasManualmente;
import static es.uned.utils.dto.cargarPersonas;
import static es.uned.utils.dto.cargarTrabajadores;

public class GestorPersonas {

    private static final GestorPersonas instancia = new GestorPersonas();

    private List<Persona> personas = new ArrayList<>();

    /* constructor */
    public GestorPersonas() {
        this.personas.addAll(cargarPersonas());
        this.personas.addAll(cargarTrabajadores());
    }

    /**
     * Método para obtener la instancia del gestor de personas.
     * @return instancia del gestor de personas.
     */
    public static GestorPersonas getInstancia() {
        return instancia;
    }

    /**
     * Método para crear nuevas personas (Usuarios, Administradores, Mecánicos y Mantenimiento).
     * @param scanner
     * @return
     */
    public boolean añadirPersonas(Scanner scanner) {

        Persona persona = crearPersonaGenerica(scanner);

        // Mostrar las opciones de tipos de usuario
        System.out.println("Elija el tipo de Persona a crear:");
        System.out.println("1. Usuario");
        System.out.println("2. Administrador");
        System.out.println("3. Mecanico");
        System.out.println("4. Mantenimiento");

        int opcionTipo = scanner.nextInt();
        scanner.nextLine();

        Date fecha = new Date();

        // sí son trabajadores obtener la fecha de contratación para evitar código duplicado
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
        switch (opcionTipo) {
            case 1 ->{
                // Usuario con su localización
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

                Usuario usuario = (Usuario) persona;
                usuario.setCoordenadas(coordenadas);
                personas.add(usuario);

                return true;
            }
            case 2 -> {
                Administrador administrador = (Administrador) persona;
                administrador.setFechaContratacion(fecha);
                personas.add(administrador);

                return true;
            }
            case 3 -> {
                Mecanico mecanico = (Mecanico) persona;
                mecanico.setFechaContratacion(fecha);
                personas.add(mecanico);

                return true;
            }

            case 4 -> {
                Mantenimiento mantenimiento = (Mantenimiento) persona;
                mantenimiento.setFechaContratacion(fecha);
                personas.add(mantenimiento);

                return true;
            }
        }
        return false;
    }

    /**
     * Método para eliminar personas del sistema
     * @param scanner scanner para leer la entrada del usuario
     * @return true si se ha eliminado una persona, false si no se ha eliminado
     */
    public boolean eliminarPersona(Scanner scanner) {
        System.out.println("Introduzca el DNI de la persona a eliminar: ");
        String dni = scanner.nextLine();
        int tamaño = personas.size();

        personas.removeIf(persona -> persona.getDNI().equals(dni));

        return personas.size() < tamaño;
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
     * Método para obtener la lista de personas.
     * @return lista de personas.
     */
    public List<Persona> getPersonas() {
        return personas;
    }

    /**
     * Método para buscar una persona por su DNI.
     * @param dni DNI de la persona a buscar.
     * @return Persona encontrada o null si no se encuentra.
     */
    private Persona buscarPersonaPorDNI(String dni) {
        return personas.stream()
                .filter(persona -> persona.getDNI().equals(dni))
                .findFirst()
                .orElse(null);
    }

    /**
     * Método para crear una persona genérica.
     * @param scanner scanner para leer la entrada del usuario
     * @return Persona creada
     */
    private Persona crearPersonaGenerica(Scanner scanner) {
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



        return new Persona(nombre, apellidos, dni, email, telefono);
    }

}



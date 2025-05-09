package es.uned.gestores;

import es.uned.model.Alquiler;
import es.uned.model.Coordenadas;
import es.uned.model.personas.*;
import es.uned.utils.GeolocalizacionPorIP;

import java.text.SimpleDateFormat;
import java.util.*;

import static es.uned.utils.GeolocalizacionPorIP.leerCoordenadasManualmente;
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

    public Set<Persona> getPersonas() {
        return personas;
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

        // Formulario de creación de persona
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

        // Elección de tipo de persona a crear

        System.out.println("Elija el tipo de Persona a crear:");
        System.out.println("1. Usuario");
        System.out.println("2. Administrador");
        System.out.println("3. Mecanico");
        System.out.println("4. Mantenimiento");

        int opcionTipo = scanner.nextInt();
        scanner.nextLine();

        Date fecha = obtenerFechaDeContratacion(scanner, opcionTipo);
        Persona persona = crearPersonaSegunTipo(dni, nombre, apellidos, email, telefono, fecha, opcionTipo, scanner);

        if (persona != null) {
            personas.add(persona);
            System.out.println("Persona creada: " + persona.getdni() + " " + persona.getNombre() + " " + persona.getApellidos());
        } else {
            System.out.println("Error al crear la persona.");
        }

    }


    /**
     * Método para obtener las coordenadas del usuario.
     * @param scanner scanner para leer la entrada del usuario
     * @return coordenadas del usuario
     */
    private static Coordenadas obtenerCoordenadasUsuario(Scanner scanner) {
        Coordenadas coordenadas = null;

        while (coordenadas == null) {
            System.out.println("¿Desea usar localización automática por IP? (S/N): ");
            String opcion = scanner.nextLine().trim().toUpperCase();

            if (opcion.equals("S")) {
                // Intentar obtener las coordenadas por IP
                coordenadas = GeolocalizacionPorIP.obtenerCoordenadasDesdeIP();

                // Si no se pudo obtener las coordenadas por IP, pedir al usuario que ingrese las coordenadas manualmente
                if (coordenadas == null) {
                    System.out.println("No se pudo obtener la ubicación automáticamente. Intentando ingresar coordenadas manualmente.");
                    coordenadas = leerCoordenadasManualmente(scanner);
                }
            } else if (opcion.equals("N")) {
                // Si el usuario no quiere usar la localización automática, se piden las coordenadas manualmente
                coordenadas = leerCoordenadasManualmente(scanner);
            } else {
                // Si la respuesta no es válida, solicitamos la opción nuevamente
                System.out.println("Opción no válida. Por favor, ingrese 'S' para sí o 'N' para no.");
            }
        }
        return coordenadas;
    }


    /**
     * Método para obtener la fecha de contratación de un trabajador.
     * @param scanner scanner para leer la entrada del usuario
     * @param opcionTipo tipo de persona
     * @return fecha de contratación
     */
    private Date obtenerFechaDeContratacion(Scanner scanner, int opcionTipo) {
        if (opcionTipo != 1 && opcionTipo < 5) {
            System.out.println("Fecha de contratación (dd/MM/yyyy): ");
            String fechaContratacion = scanner.nextLine();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                return sdf.parse(fechaContratacion);
            } catch (Exception e) {
                System.out.println("Error al parsear la fecha. Usando fecha actual.");
                return new Date();
            }
        }
        return new Date();
    }


    /**
     * Método para crear una persona según el tipo seleccionado.
     * @param dni DNI de la persona
     * @param nombre Nombre de la persona
     * @param apellidos Apellidos de la persona
     * @param email Email de la persona
     * @param telefono Teléfono de la persona
     * @param fecha Fecha de contratación
     * @param opcionTipo Tipo de persona
     * @param scanner Scanner para leer la entrada del usuario
     * @return Persona creada
     */
    private Persona crearPersonaSegunTipo(String dni, String nombre, String apellidos, String email, int telefono, Date fecha, int opcionTipo, Scanner scanner) {
        return switch (opcionTipo) {
            case 1 -> crearUsuario(dni, nombre, apellidos, email, telefono, scanner);
            case 2 -> new Administrador(dni, nombre, apellidos, email, telefono, fecha);
            case 3 -> new Mecanico(dni, nombre, apellidos, email, telefono, fecha, null);
            case 4 -> new Mantenimiento(dni, nombre, apellidos, email, telefono, fecha);
            default -> null;
        };
    }

    /**
     * Método para crear un usuario.
     * @param dni DNI del usuario
     * @param nombre Nombre del usuario
     * @param apellidos Apellidos del usuario
     * @param email Email del usuario
     * @param telefono Teléfono del usuario
     * @param scanner Scanner para leer la entrada del usuario
     * @return Usuario creado
     */
    private Persona crearUsuario(String dni, String nombre, String apellidos, String email, int telefono, Scanner scanner) {
        Coordenadas coordenadas = obtenerCoordenadasUsuario(scanner);
        return new Usuario(dni, nombre, apellidos, email, telefono, coordenadas);
    }

    /**
     * Método para eliminar personas del sistema
     *
     * @param scanner scanner para leer la entrada del usuario
     */
    public void eliminarPersona(Scanner scanner) {
        System.out.println("Introduzca el DNI de la persona a eliminar: ");
        String dni = scanner.nextLine();

        personas.removeIf(persona -> persona.getdni().equals(dni));

    }

    /**
     * Método para modificar los datos de una persona.
     *
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

        // hacer un "switch case" de todos los atributos de la persona a modificar
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

            switch (opcion) {
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
                    persona.setdni(nuevoDNI);
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
        } while (opcion != 6);

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
     *
     * @param dni DNI de la persona a buscar.
     * @return Persona encontrada o null si no se encuentra.
     */
    public Persona buscarPersonaPorDNI(String dni) {
        return personas.stream()
                .filter(persona -> persona.getdni().equals(dni))
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
     *
     * @throws IllegalArgumentException si no hay trabajadores disponibles.
     */
    public void consultarPersonalMantenimientoDisponibles() throws IllegalArgumentException {
        List<Mantenimiento> mantenimientos = personas.stream()
                .filter(p -> p instanceof Mantenimiento)
                .map(p -> (Mantenimiento) p)
                .toList();

        if (mantenimientos.isEmpty()) {
            throw new IllegalArgumentException("No hay personal de mantenimiento disponible.");
        }
        // mostrar solo mecanicos y mantenimiento
        for (Mantenimiento mantenimiento : mantenimientos) {
            System.out.println("El personal de mentenimiento " + mantenimiento.getNombre() + " " + mantenimiento.getApellidos() + " con dni " + mantenimiento.getdni() + " está disponible.");
        }
    }

    /**
     * Método para obtener la lista de mecánicos disponibles.
     *
     * @throws IllegalArgumentException si no hay mecánicos disponibles.
     */
    public void consultarMecanicosDisponibles() throws IllegalArgumentException {
        List<Mecanico> mecanicos = personas.stream()
                .filter(p -> p instanceof Mecanico)
                .map(p -> (Mecanico) p)
                .toList();

        if (mecanicos.isEmpty()) {
            throw new IllegalArgumentException("No hay mecánicos disponible.");
        }

        for (Mecanico mecanico : mecanicos) {
            System.out.println("El mecánico " + mecanico.getNombre() + " " + mecanico.getApellidos() + " con dni " + mecanico.getdni() + " está disponible.");
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

    /**
     * Método para promover a un usuario a premium.
     */
    public void promoverUsuarioAPremium(Scanner scanner) {

        System.out.println("Lista de usuarios:");
        consultarUsuarios();
        System.out.println("Introduzca el DNI del usuario a promover: ");
        String dni = scanner.nextLine();
        Persona persona = buscarPersonaPorDNI(dni);

        if (persona == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        if (persona instanceof Usuario usuario) {
            usuario.setEsPremium(true);
            System.out.println("El usuario " + usuario.getNombre() + " " + usuario.getApellidos() + " ha sido promovido a premium.");
        } else {
            System.out.println("La persona no es un usuario.");
        }

    }

    public void consultarUsuarios() {
        for (Persona persona : personas) {
            if (persona instanceof Usuario usuario) {
                System.out.println("El usuario " + usuario.getNombre() + " " + usuario.getApellidos() + " con dni " + usuario.getdni() + " tiene " + usuario.getHistorialViajes().size() + " viajes.");
            }
        }
    }

    /**
     * Método para modificar el descuento para usuarios premium.
     *
     * @param scanner scanner para leer la entrada del usuario
     */
    public double modificarDescuentoPremium(Scanner scanner) {
        System.out.println("Introduzca el nuevo descuento para usuarios premium (0-100): ");
        double nuevoDescuento = scanner.nextDouble() / 100;
        scanner.nextLine();

        if (nuevoDescuento < 0 || nuevoDescuento > 1) {
            System.out.println("El descuento debe estar entre 0 y 100.");
            modificarDescuentoPremium(scanner);
        } else {
            for (Persona persona : personas) {
                if (persona instanceof Usuario usuario) {
                    usuario.setDescuento(nuevoDescuento);
                }
            }
            System.out.println("El descuento para usuarios premium ha sido modificado a: " + nuevoDescuento);
        }
        return nuevoDescuento;
    }

    public Trabajador obtenerTrabajadorPorListado(Scanner scanner, String encargado) throws IllegalArgumentException {

        if(encargado.equals("mantenimiento")){
            System.out.println("Seleccione un personal de mantenimiento para loguearte: ");
            consultarPersonalMantenimientoDisponibles();
        } else if(encargado.equals("mecanico")){
            consultarMecanicosDisponibles();
        } else {
            throw new IllegalArgumentException("El encargado no es correcto.");
        }

        System.out.println("Indique el DNI del trabajador:");
        String dni = scanner.nextLine();

        return (Trabajador) buscarPersonaPorDNI(dni);
    }

    /**
     * Método para obtener la lista de usuarios.
     * @return Lista de usuarios.
     */
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        for (Persona persona : personas) {
            if (persona instanceof Usuario usuario) {
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }
}



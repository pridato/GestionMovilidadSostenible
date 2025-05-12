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
import static es.uned.utils.utils.leerOpcion;
import static es.uned.utils.utils.obtenerDato;

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
     * Devuelve la instancia del gestor de personas.
     *
     * @return instancia del gestor de personas.
     */
    public static GestorPersonas getInstancia() {
        return instancia;
    }

    /**
     * Permite la creación de una nueva persona en el sistema.
     *
     * Las personas pueden ser de diferentes tipos: Usuario, Administrador, Mecanico o Mantenimiento.
     *
     * @param scanner scanner para leer la entrada del usuario
     */
    public void crearPersona(Scanner scanner) {

        String dni = obtenerDato(scanner, "DNI: ");
        String nombre = obtenerDato(scanner, "Nombre: ");
        String apellidos = obtenerDato(scanner, "Apellidos: ");
        String email = obtenerDato(scanner, "Correo electrónico: ");
        try {
            int telefono = Integer.parseInt(obtenerDato(scanner, "Telefono: "));

            // tipo de persona a crear
            System.out.println("Elija el tipo de Persona a crear:");
            System.out.println("1. Usuario");
            System.out.println("2. Administrador");
            System.out.println("3. Mecanico");
            System.out.println("4. Mantenimiento");

            int opcionTipo = leerOpcion(scanner);

            Date fecha = obtenerFechaDeContratacion(scanner, opcionTipo);

            // con el formulario relleno, creamos la persona segun el tipo (1-4)
            Persona persona = crearPersonaSegunTipo(dni, nombre, apellidos, email, telefono, fecha, opcionTipo, scanner);

            // mensajes de confirmación
            if (persona != null) {
                personas.add(persona);
                System.out.println("Persona creada: " + persona.getdni() + " " + persona.getNombre() + " " + persona.getApellidos());
            } else {
                System.out.println("Error al crear la persona.");
            }



        } catch (NumberFormatException e) {
            System.out.println("El teléfono debe ser un número.");
            crearPersona(scanner); // recursividad para volver a pedir el teléfono
        }
    }


    /**
     * Devuelve las coordenadas del usuario.
     * @param scanner scanner para leer la entrada del usuario
     * @return coordenadas del usuario
     */
    private static Coordenadas obtenerCoordenadasUsuario(Scanner scanner) {
        while (true) {
            // pedimos las coordenadas al usuario por autómatica o de manera manual
            String opcion = obtenerDato(scanner, "¿Desea usar localización automática por IP? (S/N): ").trim().toUpperCase();

            switch (opcion) {
                case "S" -> {
                    // Obtenemos las coorrdenadas desde la ip, si existen devolvemos si no case N
                    Coordenadas coordenadasIP = GeolocalizacionPorIP.obtenerCoordenadasDesdeIP();
                    if (coordenadasIP != null) {
                        return coordenadasIP;
                    }
                    System.out.println("No se pudo obtener la ubicación automáticamente. Introduzca las coordenadas manualmente.");
                    return leerCoordenadasManualmente(scanner);
                }
                case "N" -> {
                    return leerCoordenadasManualmente(scanner);
                }
                default -> System.out.println("Opción no válida. Por favor, ingrese 'S' para sí o 'N' para no.");
            }
        }
    }


    /**
     * Devuelve la fecha de contratación de la persona.
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
     * Función auxiliar de crear persona, en esta a través de todos los datos se crea una persona
     * según su tipo
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
            case 3 -> new Mecanico(dni, nombre, apellidos, email, telefono, fecha);
            case 4 -> new Mantenimiento(dni, nombre, apellidos, email, telefono, fecha);
            default -> null;
        };
    }

    /**
     * Creación de usuarios añadiendo coordenadas
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
     * Elimina a una persona de cualquier tipo del sistema
     *
     * @param scanner scanner para leer la entrada del usuario
     */
    public void eliminarPersona(Scanner scanner) {
        String dni = obtenerDato(scanner, "Introduzca el DNI de la persona a eliminar: ");
        personas.removeIf(persona -> persona.getdni().equals(dni));

    }

    /**
     * Permite la modificación de cualquier atributo de una persona en el sistema.
     *
     * @param scanner scanner para leer la entrada del usuario
     */
    public void modificarPersona(Scanner scanner) {
        // primero a través del dni buscamos la persona
        String dni = obtenerDato(scanner, "Introduzca el DNI de la persona a modificar: ");
        Persona persona = buscarPersonaPorDNI(dni);

        // opciones de modificación
        int opcion;
        do {
            System.out.println("¿Qué atributo desea modificar?");
            System.out.println("1. Nombre");
            System.out.println("2. Apellidos");
            System.out.println("3. DNI");
            System.out.println("4. Email");
            System.out.println("5. Telefono");
            System.out.println("6. Salir");

            opcion = leerOpcion(scanner);

            switch (opcion) {
                case 1 -> persona.setNombre(obtenerDato(scanner, "Introduzca el nuevo nombre: "));
                case 2 -> persona.setApellidos(obtenerDato(scanner, "Introduzca los nuevos apellidos: "));
                case 3 -> persona.setdni(obtenerDato(scanner, "Introduzca el nuevo DNI: "));
                case 4 -> persona.setEmail(obtenerDato(scanner, "Introduzca el nuevo email: "));
                case 5 -> {
                    try {
                        persona.setTelefono(Integer.parseInt(obtenerDato(scanner, "El teléfono debe ser un número.")));
                    } catch (InputMismatchException e) {
                        System.out.println("El teléfono debe ser un número. No se puede modificar.");
                    }
                }
                case 6 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 6);
    }


    /**
     * Lista todas las personas del sistema.
     */
    public void listarPersonas() {
        System.out.println("Lista de personas:");
        for (Persona persona : personas) {
            persona.mostrarInformacionDetallada();
            if(persona instanceof Usuario usuario) {
                System.out.println("Coordenadas: " + usuario.getCoordenadas());
                usuario.consultarAlquileresUsuario();
            }
        }
    }


    /**
     * Devuelve una persona a partir de su DNI.
     *
     * @param dni DNI de la persona a buscar.
     * @return Persona encontrada o null si no se encuentra.
     *
     * @throws IllegalArgumentException si no se encuentra la persona.
     */
    public Persona buscarPersonaPorDNI(String dni) throws IllegalArgumentException{
        return personas.stream()
                .filter(persona -> persona.getdni().equalsIgnoreCase(dni.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada."));
    }


    /**
     * muestra los usuarios que deberían ser promovidos a premium
     */
    public void usuariosDeberianSerPremium() {
        for (Persona persona : personas) {
            if (persona instanceof Usuario usuario && usuario.getHistorialViajes().size() > 4) {
                System.out.println("El usuario " + usuario.getNombre() + " " + usuario.getApellidos() + " debería ser premium.");
            }
        }
    }

    /**
     * Consulta y muestra el personal de mantenimiento disponibles.
     *
     * @throws IllegalArgumentException si no hay trabajadores disponibles.
     */
    public void consultarPersonalMantenimientoDisponibles() throws IllegalArgumentException {
        System.out.println("Personal de mantenimiento disponibles:");
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
     * Consulta y muestra los mecánicos disponibles.
     *
     * @throws IllegalArgumentException si no hay mecánicos disponibles.
     */
    public void consultarMecanicosDisponibles() throws IllegalArgumentException {
        System.out.println("Mecánicos disponibles:");
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
     * Consulta la utilización de vehículos por parte de los usuarios.
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
     * Permite la promoción de un usuario a premium.
     *
     * @throws IllegalArgumentException si el usuario no es válido.
     */
    public void promoverUsuarioAPremium(Scanner scanner) throws IllegalArgumentException {

        System.out.println("Lista de usuarios:");
        consultarUsuarios();
        System.out.println("Introduzca el DNI del usuario a promover: ");
        String dni = scanner.nextLine();
        Persona persona = buscarPersonaPorDNI(dni);

        if (persona instanceof Usuario usuario) {
            usuario.setEsPremium(true);
            System.out.println("El usuario " + usuario.getNombre() + " " + usuario.getApellidos() + " ha sido promovido a premium.");
        } else {
            System.out.println("La persona no es un usuario.");
        }

    }

    /**
     * Consulta y muestra los usuarios del sistema.
     */
    public void consultarUsuarios() {
        for (Persona persona : personas) {
            if (persona instanceof Usuario usuario) {
                System.out.println("Coordenadas: " + usuario.getCoordenadas());
                usuario.consultarAlquileresUsuario();
            }
        }
    }

    /**
     * Modifica el descuento global para usuarios premium.
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


    /**
     * Devuelve un trabajador a partir de un listado de trabajadores.
     * @param scanner scanner para leer la entrada del usuario
     * @param encargado tipo de trabajador a buscar (mantenimiento o mecánico)
     * @return Trabajador encontrado o null si no se encuentra.
     * @throws IllegalArgumentException si el encargado no es correcto.
     */
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
     * Obtiene una lista de usuarios del sistema.
     * @return Lista de usuarios.
     */
    public List<Usuario> obtenerUsuarios() {
        return personas.stream()
                .filter(persona -> persona instanceof Usuario)
                .map(persona -> (Usuario) persona)
                .toList();
    }
}



package es.uned.gestores;

import es.uned.enums.EstadoVehiculo;
import es.uned.model.Alquiler;
import es.uned.model.Base;
import es.uned.model.Coordenadas;
import es.uned.model.Incidencia;
import es.uned.model.personas.Trabajador;
import es.uned.model.personas.Usuario;
import es.uned.model.vehiculos.Bicicleta;
import es.uned.model.vehiculos.Patinete;
import es.uned.model.vehiculos.Vehiculo;

import java.util.*;

import static es.uned.menus.MenuAdministrador.*;
import static es.uned.utils.GeolocalizacionPorIP.leerCoordenadasManualmente;
import static es.uned.utils.utils.leerOpcion;
import static es.uned.utils.utils.obtenerDato;

public class GestorIncidencias {

    private static final GestorIncidencias instancia = new GestorIncidencias();
    private final List<Incidencia> incidencias;

    /* constructor */
    private GestorIncidencias() {
        this.incidencias = new ArrayList<>();
    }

    public static GestorIncidencias getInstancia() {
        return instancia;
    }

    /**
     * Genera una incidencia sobre un vehículo o una base
     *
     * @param usuario Usuario que reporta la incidencia
     * @param scanner Scanner para leer la entrada del usuario
     */
    public void generarIncidencia(Usuario usuario, Alquiler alquiler, Scanner scanner) {
        System.out.println("----- Gestión de Incidencias -----");
        System.out.println("1. Crear incidencia sobre un vehículo");
        System.out.println("2. Crear incidencia sobre una base");
        System.out.println("0. Salir");

        int opcion = leerOpcion(scanner);

        switch (opcion) {
            case 1 -> crearIncidenciaVehiculo(usuario,alquiler, scanner);
            case 2 -> crearIncidenciaBase(usuario, scanner);
            case 0 -> System.out.println("Saliendo del menú de incidencias...");
            default -> System.out.println("Opción no válida.");
        }
    }

    /**
     * Crea una incidencia sobre un vehículo
     *
     * @param usuario Usuario que reporta la incidencia
     * @param scanner Scanner para leer la entrada del usuario
     */
    private void crearIncidenciaVehiculo(Usuario usuario,Alquiler alquiler, Scanner scanner) {
        // consultamos los vehículos disponibles
        System.out.println("Creando incidencia sobre un vehículo...");
        gestorVehiculos.consultarVehiculos();

        // pedimos al usuario que introduzca la matrícula del vehículo para obtener el objeto Vehiculo
        String matricula = obtenerDato(scanner, "Introduce la matrícula del vehículo: ");
        Vehiculo vehiculo = gestorVehiculos.obtenerVehiculo(matricula);

        // para trabajar sobre el objeto Vehiculo, usamos un Optional para evitar NullPointerException
        // si existe, procesamos el vehículo averiado sino seguir...
        Optional.ofNullable(vehiculo)
                .ifPresentOrElse(
                        v -> procesarVehiculoAveriado(usuario,alquiler, scanner, v),
                        () -> System.out.println("Vehículo no encontrado.")
                );
    }

    /**
     * Método auxiliar de `crearIncidenciaVehiculo` para procesar un vehículo averiado
     *
     * @param usuario  Usuario que reporta la incidencia
     * @param scanner  Scanner para leer la entrada del usuario
     * @param vehiculo Vehículo averiado
     */
    private void procesarVehiculoAveriado(Usuario usuario,Alquiler alquiler, Scanner scanner, Vehiculo vehiculo) {
        vehiculo.setEstado(EstadoVehiculo.AVERIADO);
        String descripcion = obtenerDato(scanner, "Describe brevemente la incidencia: ");

        Incidencia incidencia = new Incidencia(usuario, descripcion, new Date(), null, vehiculo);
        incidencias.add(incidencia);
        incidencia.mostrarConfirmacion();
    }

    /**
     * Método para crear una incidencia sobre una base
     *
     * @param usuario Usuario que reporta la incidencia
     * @param scanner Scanner para leer la entrada del usuario
     */
    private void crearIncidenciaBase(Usuario usuario, Scanner scanner) {
        // mismo proceso que crear incidencia sobre un vehículo
        System.out.println("Creando incidencia sobre una base...");
        gestorBases.consultarBasesDisponibles();

        String idBase = obtenerDato(scanner, "Introduce el ID de la base: ");
        Base base = gestorBases.consultarBasePorId(idBase);

        Optional.ofNullable(base)
                .ifPresentOrElse(
                        b -> procesarBaseAveriada(usuario, scanner, b),
                        () -> System.out.println("Base no encontrada.")
                );
    }

    /**
     * Método auxiliar de `crearIncidenciaBase` para procesar una base averiada
     *
     * @param usuario Usuario que reporta la incidencia
     * @param scanner Scanner para leer la entrada del usuario
     * @param base    Base averiada
     */
    private void procesarBaseAveriada(Usuario usuario, Scanner scanner, Base base) {
        base.setAveriada(true);
        String descripcion = obtenerDato(scanner, "Describe brevemente la incidencia: ");

        Incidencia incidencia = new Incidencia(usuario, descripcion, new Date(), base, null);
        incidencias.add(incidencia);
        incidencia.mostrarConfirmacion();
    }


    /**
     * Método para asignar una incidencia de una base a un mecánico
     *
     * @param scanner Scanner para leer la entrada del usuario
     * @throws IllegalArgumentException si el trabajador o la base no se encuentran
     */
    public void asignarBaseMecanicos(Scanner scanner) throws IllegalArgumentException {
        // consultamos los mecánicos disponibles y obtenemos el trabajador
        gestorPersonas.consultarMecanicosDisponibles();
        Trabajador trabajador = (Trabajador) gestorPersonas.
                buscarPersonaPorDNI(obtenerDato(scanner, "Introduce el DNI del trabajador: "));

        // consultamos las bases averiadas y obtenemos la base
        gestorBases.consultarBasesAveriadas();
        Base base = gestorBases.consultarBasePorId(obtenerDato(scanner, "Introduce el ID de la base: "));

        Incidencia incidencia = consultarIncidenciaPorBase(base);
        incidencia.setEncargado(trabajador);

        System.out.println("Asignada la incidencia " + incidencia.getId() + " a " + trabajador.getNombre() + " " + trabajador.getApellidos());

    }

    /**
     * Método para asignar una incidencia de un vehículo a un mecánico
     *
     * @param scanner Scanner para leer la entrada del usuario
     */
    public void asignarVehiculoTrabajador(Scanner scanner) throws IllegalArgumentException {
        System.out.println("----- Asignación de Incidencias -----");
        System.out.println("1. Asignar incidencia de un vehículo a un mecánico");
        System.out.println("2. Asignar incidencia de un vehículo a personal de mantenimiento");

        int opcion = leerOpcion(scanner);

        switch (opcion) {
            case 1 -> gestorPersonas.consultarMecanicosDisponibles();
            case 2 -> gestorPersonas.consultarPersonalMantenimientoDisponibles();
            default -> throw new IllegalArgumentException("Opción no válida.");
        }

        Trabajador trabajador = (Trabajador) gestorPersonas.buscarPersonaPorDNI(obtenerDato(scanner, "Introduce el DNI: "));

        gestorVehiculos.consultarVehiculosAveriados();
        Vehiculo vehiculo = gestorVehiculos.obtenerVehiculo(obtenerDato(scanner, "Introduce la matrícula del vehículo: "));

        Incidencia incidencia = consultarIncidenciaPorVehiculo(vehiculo);
        incidencia.setEncargado(trabajador);

        System.out.println("Asignando la incidencia " + incidencia.getId() + " a " + trabajador.getNombre() + " " + trabajador.getApellidos());
    }

    /**
     * Método para consultar una incidencia a través de una base
     *
     * @param base Base sobre la que se quiere consultar la incidencia
     * @return Incidencia asociada a la base, o null si no existe
     * @throws IllegalArgumentException si no se encuentra la incidencia
     */
    private Incidencia consultarIncidenciaPorBase(Base base) throws IllegalArgumentException {
        return incidencias.stream()
                .filter(i -> i.getBase() != null && i.getBase().equals(base))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No hay incidencias para la base " + base.getId()));
    }

    /**
     * Método para consultar una incidencia a través de un vehículo
     *
     * @param vehiculo Vehículo sobre el que se quiere consultar la incidencia
     * @return Incidencia asociada al vehículo, o null si no existe
     * @throws IllegalArgumentException si no se encuentra la incidencia
     */
    private Incidencia consultarIncidenciaPorVehiculo(Vehiculo vehiculo) throws IllegalArgumentException {
        return incidencias.stream()
                .filter(i -> i.getVehiculo() != null && i.getVehiculo().equals(vehiculo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No hay incidencias para el vehículo " + vehiculo.getMatricula()));
    }


    /**
     * Método para consultar todas las incidencias actuales
     */
    public void consultarIncidenciasActuales() {
        System.out.println("----- Incidencias actuales -----");

        // usamos un optional para evitar NullPointerException
        // primero filtramos las incidencias que no están cerradas
        // si existen mostramos la información detallada de cada incidencia
        Optional.of(incidencias)
                .filter(list -> !list.isEmpty())
                .ifPresentOrElse(
                        list -> list.forEach(Incidencia::mostrarInformacionDetalladaIncidencia),
                        () -> System.out.println("No hay incidencias registradas.")
                );
    }


    /**
     * Método para consultar las asignaciones actuales de incidencias
     */
    public void consultarAsignacionesActuales() {
        System.out.println("----- Asignaciones actuales -----");
        boolean hayAsignaciones = incidencias.stream().anyMatch(Incidencia::mostrarAsignacionEncargado);

        if (!hayAsignaciones) {
            System.out.println("No hay incidencias asignadas actualmente.");
        }
    }


    /**
     * Método para consultar las incidencias asignadas a un trabajador
     *
     * @param trabajador Trabajador al que se le quieren consultar las incidencias
     */
    public void consultarIncidenciasPorEncargado(Trabajador trabajador) {
        incidencias.stream()
                .filter(incidencia -> incidencia.getEncargado() != null &&
                        incidencia.getEncargado().getdni().equals(trabajador.getdni()))  // Filtra por encargado
                .forEach(incidencia -> {
                    System.out.println("Incidencia " + (incidencias.indexOf(incidencia) + 1) + ": ");
                    incidencia.mostrarInformacionDetalladaIncidencia();
                });
    }


    /**
     * Método para recoger un vehículo para su reparación
     *
     * @return Vehículo recogido
     * @throws IllegalArgumentException si el vehículo no se encuentra
     */
    public Vehiculo recogerVehiculoParaReparar(Scanner scanner) throws IllegalArgumentException {
        // consultamos los vehículos averiados
        gestorVehiculos.consultarVehiculosAveriados();

        // pedimos al usuario que introduzca la matrícula del vehículo a recoger
        System.out.println("Indique la matrícula del vehículo a recoger:");
        String matricula = scanner.nextLine();

        // buscamos el vehículo en la lista de vehículos y usamos un Optional para evitar NullPointerException
        Optional<Vehiculo> vehiculoOptional = Optional.ofNullable(gestorVehiculos.obtenerVehiculo(matricula));

        // si el vehículo no se encuentra, lanzamos una excepción y usamos un map para devolver el vehículo modif.
        return vehiculoOptional.map(vehiculo -> {
            vehiculo.setEstado(EstadoVehiculo.EN_REPARACION); // Establece el estado
            System.out.println("Se procederá a la recogida del vehículo " + vehiculo.getMatricula() + " para su desplazamiento");
            return vehiculo;
        }).orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado."));
    }

    /**
     * Método para desplazamiento a una base para su reparación
     *
     * @param scanner Scanner para leer la entrada del usuario
     */
    public Base desplazamientoBaseReparacion(Scanner scanner) throws IllegalArgumentException {
        gestorBases.consultarBasesAveriadas();
        System.out.print("Introduce el ID de la base: ");
        String idBase = scanner.nextLine();
        Base base = gestorBases.consultarBasePorId(idBase);

        if (base == null) {
            throw new IllegalArgumentException("Base no encontrada.");
        }

        System.out.println("Se ha desplazado a la base " + base.getId() + " para su reparación.");
        System.out.println("Incidencias reportadas: ");
        consultarIncidenciaPorBase(base);

        return base;
    }

    /**
     * Método para definir un tiempo de inactividad para un vehículo
     *
     * @param vehiculo Vehículo al que se le define el tiempo de inactividad
     * @throws IllegalArgumentException Excepción si el vehículo no se encuentra
     */
    public int definirTiempoInactividadVehiculo(Vehiculo vehiculo, Scanner scanner) throws IllegalArgumentException {
        if (vehiculo == null) {
            throw new IllegalArgumentException("Vehículo no encontrado.");
        }
        System.out.println("Definiendo tiempo de inactividad para el vehículo " + vehiculo.getMatricula());
        System.out.print("Introduce el tiempo de inactividad (en horas): ");
        int tiempoInactividad = scanner.nextInt();

        vehiculo.setTiempoInactividad(tiempoInactividad);
        System.out.println("Tiempo de inactividad definido para el vehículo " + vehiculo.getMatricula() + ": " + tiempoInactividad + " horas.");
        return tiempoInactividad;
    }

    /**
     * Método para definir un tiempo de inactividad para una base
     *
     * @param base    Base a la que se le define el tiempo de inactividad
     * @param scanner Scanner para leer la entrada del usuario
     */
    public void definirTiempoInactividadBase(Base base, Scanner scanner) throws IllegalArgumentException {
        if (base == null) {
            throw new IllegalArgumentException("Base no encontrada.");
        }
        System.out.println("Definiendo tiempo de inactividad para la base " + base.getId());
        System.out.print("Introduce el tiempo de inactividad (en horas): ");
        int tiempoInactividad = scanner.nextInt();

        base.setTiempoInactividad(tiempoInactividad);
        System.out.println("Tiempo de inactividad definido para la base " + base.getId() + ": " + tiempoInactividad + " horas.");
    }

    /**
     * Método para devolver un vehículo reparado
     *
     * @param vehiculo Vehículo a devolver
     * @param scanner  Scanner para leer la entrada del usuario
     */
    public void devolverVehiculoReparado(Vehiculo vehiculo, Scanner scanner) throws IllegalArgumentException {
        if (vehiculo == null) {
            throw new IllegalArgumentException("Vehiculo no encontrado.");
        }
        if (vehiculo instanceof Patinete || vehiculo instanceof Bicicleta) {
            // devolvemos el vehículo a la base más cercana
            Base base = gestorBases.obtenerBaseMasCercana(vehiculo.getCoordenadas());
            System.out.println("El vehículo se trasladará a la base " + base.getId() + " para su reparación (más cercana a su ubicación)");
            base.añadirVehiculo(vehiculo);
        } else {
            // en caso de ser una moto, devolvemos el vehículo a unas coordenadas especificadas
            Coordenadas coordenadas = leerCoordenadasManualmente(scanner);
            vehiculo.setCoordenadas(coordenadas);
        }

        vehiculo.setEstado(EstadoVehiculo.DISPONIBLE);
        vehiculo.setTiempoInactividad(0);
        System.out.println("Vehículo " + vehiculo.getMatricula() + " devuelto y listo para su uso.");

        Incidencia incidencia = consultarIncidenciaPorVehiculo(vehiculo);
        incidencias.remove(incidencia);
        System.out.println("Incidencia " + incidencia.getId() + " cerrada.");
    }


    /**
     * Método para consultar los vehículos ordenados por el número de reparaciones
     */
    public void consultarVehiculosOrdenadosReparacion() {
        System.out.println("----- Vehículos ordenados por número de reparaciones -----");

        // creamos un map para contar el número de reparaciones por vehículo
        Map<Vehiculo, Integer> vehiculosReparaciones = new HashMap<>();
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getVehiculo() != null) {
                vehiculosReparaciones.put(incidencia.getVehiculo(), vehiculosReparaciones.getOrDefault(incidencia.getVehiculo(), 0) + 1);
            }
        }

        // ordenamos el map por el número de reparaciones
        List<Map.Entry<Vehiculo, Integer>> listaOrdenada = new ArrayList<>(vehiculosReparaciones.entrySet());
        listaOrdenada.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // mostramos los vehículos ordenados
        for (Map.Entry<Vehiculo, Integer> entry : listaOrdenada) {
            Vehiculo vehiculo = entry.getKey();
            int numReparaciones = entry.getValue();
            System.out.println("Vehículo: " + vehiculo.getMatricula() + ", Número de reparaciones: " + numReparaciones);
        }
    }

    /**
     * Método para consultar los trabajadores ordenados por el número de intervenciones
     */
    public void consultarTrabajadoresOrdenadosIntervenciones() {
        System.out.println("----- Trabajadores ordenados por número de intervenciones -----");

        // creamos un map para contar el número de intervenciones por trabajador
        Map<Trabajador, Integer> trabajadoresIntervenciones = new HashMap<>();
        for (Incidencia incidencia : incidencias) {
            if (incidencia.getEncargado() != null) {
                trabajadoresIntervenciones.put(incidencia.getEncargado(), trabajadoresIntervenciones.getOrDefault(incidencia.getEncargado(), 0) + 1);
            }
        }

        // ordenamos el map por el número de intervenciones
        List<Map.Entry<Trabajador, Integer>> listaOrdenada = new ArrayList<>(trabajadoresIntervenciones.entrySet());
        listaOrdenada.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // mostramos los trabajadores ordenados
        for (Map.Entry<Trabajador, Integer> entry : listaOrdenada) {
            Trabajador trabajador = entry.getKey();
            int numIntervenciones = entry.getValue();
            System.out.println("Trabajador: " + trabajador.getNombre() + " " + trabajador.getApellidos() + ", Número de intervenciones: " + numIntervenciones);
        }
    }
}

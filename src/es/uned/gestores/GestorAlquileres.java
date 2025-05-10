package es.uned.gestores;

import es.uned.enums.EstadoAlquiler;
import es.uned.enums.EstadoVehiculo;
import es.uned.model.Alquiler;
import es.uned.model.Base;
import es.uned.model.Coordenadas;
import es.uned.model.personas.Usuario;
import es.uned.model.vehiculos.Moto;
import es.uned.model.vehiculos.Vehiculo;
import es.uned.utils.GeolocalizacionPorIP;

import java.util.*;

import static es.uned.menus.MenuAdministrador.*;
import static es.uned.utils.GeolocalizacionPorIP.leerCoordenadasManualmente;
import static es.uned.utils.utils.obtenerDato;

/**
 * Clase GestorAlquileres.
 * <p>
 * Métodos:
 * <p>
 * Iniciar alquiler
 * <p>
 * Finalizar alquiler
 * <p>
 * Reservar vehículo (alquiler)
 * <p>
 * Consultar alquileres
 */
public class GestorAlquileres {

    private static final double minX = limiteInferior.getX();
    private static final double maxX = limiteSuperior.getX();
    private static final double minY = limiteInferior.getY();
    private static final double maxY = limiteSuperior.getY();

    private static final GestorAlquileres instancia = new GestorAlquileres();
    private final List<Alquiler> alquileres;

    /* Constructor de la clase GestorAlquileres. */
    public GestorAlquileres() {
        this.alquileres = new ArrayList<>();
    }

    /**
     * Método para obtener la instancia del gestor de alquileres.
     *
     * @return instancia del gestor de alquileres.
     */
    public static GestorAlquileres getInstancia() {
        return instancia;
    }


    /**
     * Método para iniciar un alquiler.
     *
     * @param usuario usuario que alquila el vehículo
     * @throws IllegalStateException si el usuario no tiene saldo suficiente
     * @throws IllegalStateException si el vehículo no está disponible
     * @throws IllegalStateException si el vehículo no tiene batería suficiente
     */
    public Alquiler iniciarAlquiler(Usuario usuario, Scanner scanner, Alquiler alquilerExistente) throws IllegalArgumentException {

        // verificamos si el usuario tiene saldo suficiente
        comprobarTiempoReserva(usuario, alquilerExistente);
        usuario.verificarSaldo();

        // seleccionamos un vehiculo, o lo creamos o lo sacamos de una reserva
        Vehiculo vehiculo = (alquilerExistente != null && alquilerExistente.getEstado() == EstadoAlquiler.RESERVADO)
                ? alquilerExistente.getVehiculo()
                : seleccionarVehiculo(usuario, scanner);

        // verificamos si el vehiculo tiene bateria suficiente y si está disponible
        vehiculo.verificarDisponibilidad(usuario);
        vehiculo.setEstado(EstadoVehiculo.ALQUILADO);

        // si el vehiculo es una moto, no requiere base en caso contrario, si el vehículo está en una base cogerlo automáticamente sino elegirlo
        Base baseInicio = (vehiculo instanceof Moto) ? null : gestorBases.obtenerBaseConVehiculoDisponible(vehiculo, scanner);
        Coordenadas coordenadas = (vehiculo instanceof Moto && alquilerExistente == null) ? vehiculo.getCoordenadas() : null;

        // creamos el alquiler y lo añadimos a la lista de alquileres
        Alquiler nuevoAlquiler = new Alquiler(vehiculo, new Date(), EstadoAlquiler.EN_CURSO, baseInicio, coordenadas);
        alquileres.add(nuevoAlquiler);
        usuario.añadirAlquiler(nuevoAlquiler);

        System.out.println("Alquiler iniciado: " + nuevoAlquiler.getId());
        return nuevoAlquiler;
    }

    /**
     * Verifica si han transcurrido más de 20 minutos desde la reserva.
     * Si excede el tiempo, lanza excepción. Si no, procede con el alquiler.
     *
     * @param usuario usuario que alquila el vehículo
     * @param alquilerExistente alquiler existente
     * Si excede el tiempo, lanza excepción. Si no, procede con el alquiler.
     */
    private static void comprobarTiempoReserva(Usuario usuario, Alquiler alquilerExistente) throws IllegalStateException {
        if (alquilerExistente != null) {
            long tiempoTranscurrido = (new Date().getTime() - alquilerExistente.getFechaInicio().getTime()) / 60000;
            if (tiempoTranscurrido >= 20) {
                throw new IllegalStateException("Han pasado más de 20 minutos desde que se realizó la reserva por lo que no puedes alquilar el vehículo.");
            } else {
                System.out.println("Han pasado " + tiempoTranscurrido + " minutos desde que se realizó la reserva.");
                System.out.println("Se alquilará el vehículo con matrícula " + alquilerExistente.getVehiculo().getMatricula() + " en la base " + alquilerExistente.getVehiculo().getMatricula() + ".");
                usuario.getHistorialViajes().removeIf(a -> a.getId().equals(alquilerExistente.getId()));
            }
        }
    }


    /**
     * Método para finalizar un alquiler.
     *
     * @param usuario  usuario que finaliza el alquiler
     * @param alquiler alquiler a finalizar
     * @param scanner  scanner para leer la entrada del usuario
     */
    public static void finalizarAlquiler(Usuario usuario, Alquiler alquiler, Scanner scanner) throws IllegalStateException {
        // verificamos si el alquiler está en curso
        alquiler.comprobarAlquilerCurso();

        Vehiculo vehiculo = alquiler.getVehiculo();

        if (vehiculo == null) {
            throw new IllegalStateException("El vehículo no está disponible.");
        }

        // obtener coordenadas o base de finalización
        Coordenadas coordenadasFin = (vehiculo instanceof Moto) ? obtenerCoordenadasFin(scanner) : null;
        Base baseFin = (vehiculo instanceof Moto) ? null : obtenerBaseParaAlquilar(scanner);

        if (baseFin != null) {
            baseFin.añadirVehiculo(vehiculo);
            alquiler.setBaseFin(baseFin);
        }

        // calculamos el tiempo de duración del alquiler en minutos
        int tiempoDuracion = (int) ((alquiler.getFechaFin().getTime() - alquiler.getFechaInicio().getTime()) / 60000);
        alquiler.setTiempoDuracion(tiempoDuracion);

        // calculamos el importe total del alquiler más las penalizaciones
        double importeTotal = vehiculo.calcularImporte(alquiler.getTiempoDuracion());
        importeTotal += aplicarPenalizaciones(vehiculo, alquiler);

        // aplicamos el descuento premium si el usuario es premium
        if (usuario.getEsPremium()) {
            importeTotal -= importeTotal * descuentoPremium;
            System.out.println("El usuario es premium, se aplicará un descuento del " + descuentoPremium * 100 + "%");
        }

        modificarEstadosAlquiler(usuario, alquiler, coordenadasFin, importeTotal, vehiculo);

        alquiler.mostrarPrecio(usuario);
    }

    /**
     * Modifica los estados del alquiler, usuario y vehículo al finalizar el alquiler.
     * @param usuario usuario que finaliza el alquiler
     * @param alquiler alquiler a finalizar
     * @param coordenadasFin coordenadas de finalización
     * @param importeTotal importe total del alquiler
     * @param vehiculo vehículo alquilado
     */
    private static void modificarEstadosAlquiler(Usuario usuario, Alquiler alquiler, Coordenadas coordenadasFin, double importeTotal, Vehiculo vehiculo) {
        alquiler.setCoordenadasFin(coordenadasFin);
        alquiler.setEstado(EstadoAlquiler.FINALIZADO);
        alquiler.setFechaFin(new Date());
        alquiler.setImporteFinal(importeTotal);
        usuario.recargarSaldo(-importeTotal);
        vehiculo.setEstado(EstadoVehiculo.DISPONIBLE);
    }

    /**
     * Método para reservar un vehículo
     *
     * @param usuario usuario que reserva el alquiler
     * @param scanner scanner para leer la entrada del usuario
     */
    public Alquiler reservarVehiculo(Usuario usuario, Scanner scanner) throws IllegalArgumentException {
        if (!usuario.getEsPremium()) {
            throw new IllegalStateException("Solo los usuarios premium pueden reservar vehículos.");
        }

        Vehiculo vehiculo = obtenerVehiculoParaAlquilar(scanner);

        if (vehiculo.getEstado() != EstadoVehiculo.DISPONIBLE) {
            throw new IllegalStateException("El vehículo no está disponible para reservar.");
        }

        Base baseInicio = gestorBases.obtenerBaseVehiculo(vehiculo.getMatricula());
        Alquiler alquiler;

        if (vehiculo instanceof Moto) {
            System.out.println("Va a alquilar el vehículo " + vehiculo.getMatricula() + " en las coordenadas " + vehiculo.getCoordenadas().getX() + ", " + vehiculo.getCoordenadas().getY());
            alquiler = new Alquiler(vehiculo, new Date(), EstadoAlquiler.EN_CURSO, vehiculo.getCoordenadas());
        } else {
            if (baseInicio == null) {
                baseInicio = obtenerBaseParaAlquilar(scanner);
                System.out.println("Va a alquilar el vehículo " + vehiculo.getMatricula() + " en la base " + baseInicio.getId());
            }
            alquiler = new Alquiler(vehiculo, new Date(), EstadoAlquiler.RESERVADO);
        }

        this.alquileres.add(alquiler);
        return alquiler;
    }


    /**
     * Método para obtener una base para alquilar.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     * @return Base a alquilar
     */
    public static Base obtenerBaseParaAlquilar(Scanner scanner) {
        Base base;
        while (true) {
            System.out.println("Al ser una bicicleta o patinete, debes alquilarlo en una base.");
            gestorBases.consultarBasesDisponibles();
            base = gestorBases.consultarBasePorId(obtenerDato(scanner, "Porfavor introduce la base de inicio donde deseas alquilar el vehículo:"));
            if (base != null) {
                return base;
            } else {
                System.out.println("Base no encontrada.");
            }
        }
    }

    /**
     * Método para obtener un vehículo para alquilar.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     * @return Vehiculo a alquilar
     */
    private static Vehiculo obtenerVehiculoParaAlquilar(Scanner scanner) throws IllegalArgumentException {
        do {
            try {
                gestorVehiculos.consultarVehiculosDisponibles();
                return gestorVehiculos.obtenerVehiculo(obtenerDato(scanner, "Introduce la matrícula del vehículo que deseas alquilar:"));
            } catch (IllegalArgumentException e) {
                System.out.println("Vehículo no encontrado.");
            }
        } while (true);
    }



    /**
     * Método para consultar la moto más cercana del usuario.
     *
     * @param usuario Usuario a consultar.
     * @return Vehículo más cercano.
     */
    private Vehiculo motoMasCercana(Usuario usuario) {
        List<Vehiculo> vehiculos = gestorVehiculos.getVehiculos();
        Vehiculo vehiculoMasCercano = null;
        double distanciaMinima = Double.MAX_VALUE;

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo instanceof Moto && vehiculo.getEstado() == EstadoVehiculo.DISPONIBLE) {
                double distancia = vehiculo.getCoordenadas().Calculardistancia(usuario.getCoordenadas());
                if (distancia < distanciaMinima) {
                    distanciaMinima = distancia;
                    vehiculoMasCercano = vehiculo;
                }
            }
        }

        return vehiculoMasCercano;
    }

    /**
     * Método para seleccionar un vehículo para alquilar.
     *
     * @param usuario usuario que alquila el vehículo
     * @param scanner Scanner para leer la entrada del usuario
     * @return Vehículo seleccionado
     */
    public Vehiculo seleccionarVehiculo(Usuario usuario, Scanner scanner) {
        System.out.println("¿Deseas alquilar la moto más cercana? (s/n)");
        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("s")) {
            Vehiculo moto = motoMasCercana(usuario);
            if (moto == null) {
                throw new IllegalStateException("No hay motos disponibles.");
            }
            return moto;
        } else {
            return obtenerVehiculoParaAlquilar(scanner);
        }
    }

    /**
     * Método para obtener las coordenadas de finalización del alquiler.
     *
     * @param scanner Scanner para leer la entrada del usuario
     * @return Coordenadas de finalización
     */
    private static Coordenadas obtenerCoordenadasFin(Scanner scanner) {
        System.out.println("¿Quieres introducir las coordenadas manualmente? (s/n)");
        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("s")) {
            return leerCoordenadasManualmente(scanner);
        } else {
            return GeolocalizacionPorIP.obtenerCoordenadasDesdeIP();
        }
    }

    /**
     * Método para aplicar penalizaciones al usuario.
     *
     * @param vehiculo vehículo alquilado
     * @param alquiler alquiler a finalizar
     */
    private static double aplicarPenalizaciones(Vehiculo vehiculo, Alquiler alquiler) {
        double penalizaciones = 0;
        if (vehiculo.getBateria() == 0) {
            penalizaciones += vehiculo.getPenalizacion();
            System.out.println("Penalización por batería: " + penalizaciones + "€");
        }

        if (vehiculo instanceof Moto &&
                !alquiler.getCoordenadasFin().estaDentroDeLimites(minX, maxX, minY, maxY)) {
            penalizaciones += vehiculo.getPenalizacion();
            System.out.println("Penalización por salir de límites: " + penalizaciones + "€");
        }

        return penalizaciones;
    }


    /**
     * Método para consultar los vehículos ordenados por tiempo de uso.
     */
    public void consultarVehiculosOrdenadosTiempoUso() {
        System.out.println("Vehículos ordenados por tiempo de uso:");

        // filtrar los alquileres finalizados y añadirlos en un mapa
        Map<Vehiculo, Integer> tiemposUso = new HashMap<>();
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getEstado() == EstadoAlquiler.FINALIZADO) {
                tiemposUso.put(alquiler.getVehiculo(), alquiler.getTiempoDuracion());
            }
        }

        // ordenar el mapa por tiempo de uso
        List<Map.Entry<Vehiculo, Integer>> listaOrdenada = new ArrayList<>(tiemposUso.entrySet());
        listaOrdenada.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));


        // imprimir los vehículos ordenados
        for (Map.Entry<Vehiculo, Integer> entry : listaOrdenada) {
            System.out.println("Vehículo: " + entry.getKey().getMatricula() + " - Tiempo de uso: " + entry.getValue() + " minutos");
        }
    }


    /**
     * Método para consultar los usuarios ordenados por gastos de alquiler.
     */
    public void consultarUsuariosOrdenadosGastosAlquiler() {
        System.out.println("Usuarios ordenados por gastos de alquiler:");

        // filtrar los alquileres finalizados y añadirlos en un mapa
        Map<Usuario, Double> gastosAlquiler = new HashMap<>();

        for (Usuario usuario : gestorPersonas.obtenerUsuarios()) {
            double totalGastos = 0;
            for (Alquiler alquiler : usuario.getHistorialViajes()) {
                if (alquiler.getEstado() == EstadoAlquiler.FINALIZADO) {
                    totalGastos += alquiler.getImporteFinal();
                }
            }
            gastosAlquiler.put(usuario, totalGastos);
        }

        // ordenar el mapa por gastos
        List<Map.Entry<Usuario, Double>> listaOrdenada = new ArrayList<>(gastosAlquiler.entrySet());
        listaOrdenada.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // imprimir los usuarios ordenados
        for (Map.Entry<Usuario, Double> entry : listaOrdenada) {
            System.out.println("Usuario: " + entry.getKey().getNombre() + " - Gastos de alquiler: " + entry.getValue() + "€");
        }
    }
}

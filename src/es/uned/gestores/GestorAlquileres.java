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
    List<Alquiler> alquileres;

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
     *
     * @exception IllegalStateException si el usuario no tiene saldo suficiente
     * @exception IllegalStateException si el vehículo no está disponible
     * @exception IllegalStateException si el vehículo no tiene batería suficiente
     */
    public Alquiler iniciarAlquiler(Usuario usuario, Scanner scanner, Alquiler alquilerExistente) throws IllegalArgumentException {

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
        Base baseInicio = (vehiculo instanceof Moto)
                ? null
                : Optional.ofNullable(gestorBases.obtenerBaseVehiculo(vehiculo.getMatricula()))
                .orElse(obtenerBaseParaAlquilar(scanner));

        Coordenadas coordenadas = (vehiculo instanceof Moto && alquilerExistente == null)
                ? vehiculo.getCoordenadas()
                : null;

        Alquiler nuevoAlquiler = new Alquiler(vehiculo, new Date(), EstadoAlquiler.EN_CURSO, baseInicio, coordenadas);
        alquileres.add(nuevoAlquiler);

        usuario.getHistorialViajes().add(nuevoAlquiler);

        System.out.println("Alquiler iniciado: " + nuevoAlquiler.getId());
        return nuevoAlquiler;
    }

    /**
     * Método para comprobar si han pasado más de 20 minutos desde la reserva. Si es así, se lanza una excepción.
     * @param usuario usuario que alquila el vehículo
     * @param alquilerExistente alquiler existente
     */
    private static void comprobarTiempoReserva(Usuario usuario, Alquiler alquilerExistente) {
        if(alquilerExistente != null) {
            {
                long tiempoTranscurrido = (new Date().getTime() - alquilerExistente.getFechaInicio().getTime()) / 60000;
                if (tiempoTranscurrido >=  20) {
                    throw new IllegalStateException("Han pasado más de 20 minutos desde que se realizó la reserva por lo que no puedes alquilar el vehículo.");
                } else {
                    System.out.println("Han pasado " + tiempoTranscurrido + " minutos desde que se realizó la reserva.");
                    System.out.println("Se alquilará el vehículo con matrícula " + alquilerExistente.getVehiculo().getMatricula() + " en la base " + alquilerExistente.getVehiculo().getMatricula()  + ".");

                    usuario.getHistorialViajes().removeIf(a -> a.getId().equals(alquilerExistente.getId()));
                }
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
    public static void finalizarAlquiler(Usuario usuario, Alquiler alquiler, Scanner scanner) {

        // comprobar si el alquiler está en curso
        alquiler.comprobarAlquilerCurso();

        Vehiculo vehiculo = alquiler.getVehiculo();

        // obtener coordenadas o base de finalización
        Coordenadas coordenadasFin = (vehiculo instanceof Moto) ? obtenerCoordenadasFin(scanner) : null;
        Base baseFin = (vehiculo instanceof Moto) ? null : obtenerBaseParaAlquilar(scanner);

        // seteamos bases, coordenadas y finalizar el alquiler
        alquiler.setBaseFin(baseFin);
        alquiler.setCoordenadasFin(coordenadasFin);
        alquiler.setEstado(EstadoAlquiler.FINALIZADO);
        alquiler.setFechaFin(new Date());

        int tiempoDuracion = (int) ((alquiler.getFechaFin().getTime() - alquiler.getFechaInicio().getTime()) / 60000);
        alquiler.setTiempoDuracion(tiempoDuracion);


        // calculamos el tiempo de duración y el importe final
        double importeTotal = vehiculo.calcularImporte(alquiler.getTiempoDuracion());
        importeTotal -= importeTotal * descuentoPremium;

        if(usuario.getEsPremium()) {
            System.out.println("El usuario es premium, se aplicará un descuento del " + descuentoPremium * 100 + "%");
            alquiler.setImporteFinal(importeTotal);
        }

        // aplicamos penalizaciones y recargamos saldo
        aplicarPenalizaciones(usuario, vehiculo, alquiler);
        importeTotal +=  vehiculo.getPenalizacion(); // añadir penalización al importe total
        usuario.recargarSaldo(-importeTotal);
        System.out.println("Alquiler finalizado con éxito.");

        // Actualizar estado del vehículo
        vehiculo.setEstado(EstadoVehiculo.DISPONIBLE);


        System.out.println("Alquiler finalizado: " + alquiler.getId());
        System.out.println("Importe final: " + alquiler.getImporteFinal() + "€");
        System.out.println("Penalización aplicada: " + vehiculo.getPenalizacion() + "€");
        System.out.println("Duración: " + alquiler.getTiempoDuracion() + " minutos");
        System.out.println("Saldo actual del usuario: " + usuario.getsaldo() + "€");
    }

    /**
     * Método para reservar un vehículo
     *
     * @param usuario usuario que reserva el alquiler
     * @param scanner scanner para leer la entrada del usuario
     */
    public Alquiler reservarVehiculo(Usuario usuario, Scanner scanner) throws IllegalArgumentException {
        // si el usuario no es premium abortar...
        if (!usuario.getEsPremium()) {
            throw new IllegalStateException("Solo los usuarios premium pueden reservar vehículos.");
        }

        Vehiculo vehiculo = null;
        try {
            // obtenemos un vehiculo para alquilar
            vehiculo = obtenerVehiculoParaAlquilar(scanner);

            // si el vehículo no está disponible abortar...
            if (vehiculo.getEstado() != EstadoVehiculo.DISPONIBLE) {
                throw new IllegalStateException("El vehículo no está disponible para reservar.");
            }

            Base baseInicio = gestorBases.obtenerBaseVehiculo(vehiculo.getMatricula());
            Alquiler alquiler;

            if (vehiculo instanceof Moto) {
                System.out.println("Va a alquilar el vehículo " + vehiculo.getMatricula() + " en las coordenadas " + vehiculo.getCoordenadas().getX() + ", " + vehiculo.getCoordenadas().getY());
                alquiler = new Alquiler(
                        vehiculo,
                        new Date(),
                        EstadoAlquiler.EN_CURSO,
                        vehiculo.getCoordenadas()
                );
            } else if (baseInicio == null) {
                baseInicio = obtenerBaseParaAlquilar(scanner);
            }

            alquiler = new Alquiler(
                    vehiculo,
                    new Date(),
                    EstadoAlquiler.RESERVADO
            );

            this.alquileres.add(alquiler);

            return alquiler;

        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    /**
     * Método para obtener una base para alquilar.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     * @return Base a alquilar
     */
    public static Base obtenerBaseParaAlquilar(Scanner scanner) {
        Base base;
        String idBase = "";
        while (true) {
            System.out.println("Al ser una bicicleta o patinete, debes alquilarlo en una base.");
            gestorBases.consultarBasesDisponibles();
            System.out.println("Porfavor introduce la base de inicio donde deseas alquilar el vehículo:");
            idBase = scanner.nextLine();
            base = gestorBases.consultarBasePorId(idBase);
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
        Vehiculo vehiculo;
        String matricula;
        do {
            gestorVehiculos.consultarVehiculosParaAlquilar();
            System.out.println("Introduce la matrícula del vehículo que deseas alquilar:");
            matricula = scanner.nextLine();

            vehiculo = gestorVehiculos.obtenerVehiculo(matricula);
            if (vehiculo == null) {
                System.out.println("Vehículo no encontrado.");
                matricula = "";
            } else if (vehiculo.getEstado() != EstadoVehiculo.DISPONIBLE) {
                throw new IllegalStateException("El vehículo no está disponible para alquilar");
            }

        } while (matricula.isEmpty());
        return vehiculo;
    }


    /**
     * Método para consultar los alquileres del usuario.
     *
     * @param usuario Usuario a consultar.
     */
    public void consultarAlquileresUsuario(Usuario usuario) {
        if (usuario.getHistorialViajes().isEmpty()) {
            System.out.println("No tienes ningún alquiler.");
            return;
        }

        System.out.println("Alquileres del usuario " + usuario.getNombre() + ":");
        for (Alquiler alquiler : usuario.getHistorialViajes()) {
            System.out.println(alquiler.getId() + " - " + alquiler.getVehiculo().getMatricula() + " - " + alquiler.getEstado() +
                    " - " + alquiler.getFechaInicio() + " - " + alquiler.getFechaFin() + " - " + alquiler.getImporteFinal());
        }
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
     * @param usuario usuario que alquila el vehículo
     * @param vehiculo vehículo alquilado
     * @param alquiler alquiler a finalizar
     */
    private static void aplicarPenalizaciones(Usuario usuario, Vehiculo vehiculo, Alquiler alquiler) {
        if (vehiculo.getBateria() == 0) {
            double penalizacion = vehiculo.getPenalizacion();
            usuario.recargarSaldo(-penalizacion);
            System.out.println("Penalización por batería: " + penalizacion + "€");
        }

        if (vehiculo instanceof Moto &&
                !alquiler.getCoordenadasFin().estaDentroDeLimites(minX, maxX, minY, maxY)) {
            double penalizacion = vehiculo.getPenalizacion();
            usuario.recargarSaldo(-penalizacion);
            System.out.println("Penalización por salir de límites: " + penalizacion + "€");
        }
    }
}

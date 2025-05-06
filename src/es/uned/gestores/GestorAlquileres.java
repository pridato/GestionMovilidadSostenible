package es.uned.gestores;

import es.uned.enums.EstadoAlquiler;
import es.uned.enums.EstadoVehiculo;
import es.uned.model.Alquiler;
import es.uned.model.Base;
import es.uned.model.personas.Usuario;
import es.uned.model.vehiculos.Bicicleta;
import es.uned.model.vehiculos.Moto;
import es.uned.model.vehiculos.Patinete;
import es.uned.model.vehiculos.Vehiculo;
import es.uned.utils.GeolocalizacionPorIP;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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
     * @return instancia del gestor de alquileres.
     */
    public static GestorAlquileres getInstancia() {
        return instancia;
    }

    /**
     * Método para iniciar un alquiler.
     *
     * @param usuario usuario que alquila el vehículo
     */
    public Alquiler iniciarAlquiler(Usuario usuario, Scanner scanner) throws Exception {

        if(usuario.getsaldo() < 0){
            throw new IllegalStateException("El usuario no tiene saldo suficiente para alquilar.");
        }
        Vehiculo vehiculo = null;
        try {
            // obtenemos un vehiculo para alquilar
            vehiculo = obtenerVehiculoParaAlquilar(scanner);

        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return null;
        }

        vehiculo.setEstado(EstadoVehiculo.ALQUILADO);

        Alquiler alquiler;

        // si es bici o patinete requiere base
        if (vehiculo instanceof Bicicleta || vehiculo instanceof Patinete) {
            Base baseInicio = obtenerBaseParaAlquilar(scanner);
            alquiler = new Alquiler(
                    vehiculo,
                    new Date(),
                    EstadoAlquiler.EN_CURSO,
                    baseInicio
            );
        } else {
            System.out.println("Va a alquilar el vehículo " + vehiculo.getMatricula() + " en las coordenadas " + vehiculo.getCoordenadas().getX() + ", " + vehiculo.getCoordenadas().getY());
            alquiler = new Alquiler(
                    vehiculo,
                    new Date(),
                    EstadoAlquiler.EN_CURSO,
                    vehiculo.getCoordenadas()
            );
        }

        this.alquileres.add(alquiler);
        System.out.println("Alquiler iniciado: " + alquiler.getId());
        usuario.getHistorialViajes().add(alquiler);

        return alquiler;
    }

    /**
     * Método para obtener una base para alquilar.
     * @param scanner Scanner para leer la entrada del usuario.
     * @return Base a alquilar
     */
    private static Base obtenerBaseParaAlquilar(Scanner scanner) {
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
     * Método para finalizar un alquiler.
     *
     * @param usuario  usuario que finaliza el alquiler
     * @param alquiler alquiler a finalizar
     * @param scanner scanner para leer la entrada del usuario
     */
    public static void finalizarAlquiler(Usuario usuario, Alquiler alquiler, Scanner scanner) {
        if (alquiler.getEstado() != EstadoAlquiler.EN_CURSO) {
            throw new IllegalStateException("El alquiler no está en curso");
        }

        Vehiculo vehiculo = alquiler.getVehiculo();
        Base baseFin = null;

        // si es bici o patinete requiere base
        if (vehiculo instanceof Bicicleta || vehiculo instanceof Patinete) {
            baseFin = obtenerBaseParaAlquilar(scanner);
            baseFin.añadirVehiculo(vehiculo);
        }

        // obtener coordenadas manualmente o por GPS
        else {
            System.out.println("¿Quieres introducir las coordenadas manualmente? (s/n)");
            String respuesta = scanner.nextLine();
            if (respuesta.equalsIgnoreCase("s")) {
                System.out.println("Introduce las coordenadas manualmente:");
                alquiler.setCoordenadasFin(leerCoordenadasManualmente(scanner));
            } else {
                // si no se introducen manualmente, se obtienen por GPS
                alquiler.setCoordenadasFin(GeolocalizacionPorIP.obtenerCoordenadasDesdeIP());
            }
        }

        // establecer la fecha de fin
        Date fechaFin = new Date();
        alquiler.setFechaFin(fechaFin);

        // Calcular minutos de duración
        long duracionMillis = fechaFin.getTime() - alquiler.getFechaInicio().getTime();
        int duracionMinutos = (int) (duracionMillis / (60 * 1000));
        if (duracionMinutos <= 0) duracionMinutos = 1;  // Mínimo 1 minuto

        alquiler.setTiempoDuracion(duracionMinutos);


        double importe = vehiculo.calcularImporte(duracionMinutos);

        // si el usuario es premium, aplicar descuento
        if (usuario.getEsPremium()) {
            importe *= (1 - usuario.getDescuento() / 100);
        }


        alquiler.setImporteFinal(importe);
        alquiler.setBaseFin(baseFin);
        alquiler.setEstado(EstadoAlquiler.FINALIZADO);

        // Descontar saldo al usuario
        usuario.recargarSaldo(-importe);

        // Actualizar estado del vehículo
        vehiculo.setEstado(EstadoVehiculo.DISPONIBLE);


        // Penalización si batería llega a 0
        if (vehiculo.getBateria() == 0) {
            double penalizacion = vehiculo.getPenalizacion();
            usuario.recargarSaldo(-penalizacion);
            System.out.println("Penalización aplicada al usuario por agotar batería: " + penalizacion + "€");
        }



        if(vehiculo instanceof Moto) {
            if (!alquiler.getCoordenadasFin().estaDentroDeLimites(minX, maxX, minY, maxY)) {
                System.out.println("El vehículo ha salido de los límites de la base. Penalización aplicada del " + vehiculo.getPenalizacion() + "€");
                double penalizacion = vehiculo.getPenalizacion();
                usuario.recargarSaldo(-penalizacion);
            }
        }


        System.out.println("Alquiler finalizado: " + alquiler.getId());
        System.out.println("Importe final: " + alquiler.getImporteFinal() + "€");
        System.out.println("Penalización aplicada: " + vehiculo.getPenalizacion() + "€");
        System.out.println("Duración: " + alquiler.getTiempoDuracion() + " minutos");
        System.out.println("Saldo actual del usuario: " + usuario.getsaldo() + "€");
    }

    /**
     * Método para reservar un vehículo
     *
     * @param usuario  usuario que reserva el alquiler
     * @param vehiculo vehiculo a reservar
     */
    public void reservarVehiculo(Usuario usuario, Vehiculo vehiculo) {
        // si el usuario no es premium abortar...
        if (!usuario.getEsPremium()) {
            throw new IllegalStateException("Solo los usuarios premium pueden reservar vehículos.");
        }

        // si el vehículo no está disponible abortar...
        if (vehiculo.getEstado() != EstadoVehiculo.DISPONIBLE) {
            throw new IllegalStateException("El vehículo no está disponible para reservar.");
        }

        Alquiler alquiler = new Alquiler(
                vehiculo,
                new Date(),
                EstadoAlquiler.RESERVADO
        );

        this.alquileres.add(alquiler);
    }

    /**
     * Método para consultar los alquileres.
     *
     * @return lista de alquileres
     */
    public List<Alquiler> consultarAlquileres() {
        return this.alquileres;
    }


    /**
     * Método para consultar los alquileres del usuario.
     * @param usuario Usuario a consultar.
     */
    public void consultarAlquileresUsuario(Usuario usuario) {
        if (usuario.getHistorialViajes().isEmpty()) {
            System.out.println("No tienes ningún alquiler.");
            return;
        }

        System.out.println("Alquileres del usuario " + usuario.getNombre() + ":");
        for (Alquiler alquiler : usuario.getHistorialViajes()) {
            System.out.println(alquiler);
        }
    }
}

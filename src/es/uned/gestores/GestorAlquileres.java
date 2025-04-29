package es.uned.gestores;

import es.uned.enums.EstadoAlquiler;
import es.uned.enums.EstadoVehiculo;
import es.uned.model.Alquiler;
import es.uned.model.Base;
import es.uned.model.personas.Usuario;
import es.uned.model.vehiculos.Vehiculo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase GestorAlquileres.
 *
 * Métodos:
 *
 * Iniciar alquiler
 *
 * Finalizar alquiler
 *
 * Reservar vehículo (alquiler)
 *
 * Consultar alquileres
 */
public class GestorAlquileres {

    List<Alquiler> alquileres;

    /* Constructor de la clase GestorAlquileres. */
    public GestorAlquileres() {
        this.alquileres = new ArrayList<>();
    }

    /**
     * Método para iniciar un alquiler.
     * @param usuario usuario que alquila el vehículo
     * @param vehiculo vehículo a alquilar
     * @param baseInicio base de inicio del alquiler
     */
    public Alquiler iniciarAlquiler(Usuario usuario, Vehiculo vehiculo, Base baseInicio) {

        if(vehiculo.getEstado() != EstadoVehiculo.DISPONIBLE) {
            throw new IllegalStateException("El vehículo no está disponible para alquilar");
        }

        vehiculo.setEstado(EstadoVehiculo.ALQUILADO);

        Alquiler alquiler = new Alquiler(
                vehiculo,
                new Date(),
                EstadoAlquiler.EN_CURSO,
                baseInicio
        );

        this.alquileres.add(alquiler);
        System.out.println("Alquiler iniciado: " + alquiler.getId());
        usuario.getHistorialViajes().add(alquiler);

        return alquiler;
    }

    /**
     * Método para finalizar un alquiler.
     * @param usuario usuario que finaliza el alquiler
     * @param alquiler alquiler a finalizar
     * @param baseFin base de finalización del alquiler
     */
    public void finalizarAlquiler(Usuario usuario, Alquiler alquiler, Base baseFin) {
        if(alquiler.getEstado() != EstadoAlquiler.EN_CURSO) {
            throw new IllegalStateException("El alquiler no está en curso");
        }

        // establecer la fecha de fin
        Date fechaFin = new Date();
        alquiler.setFechaFin(fechaFin);

        // Calcular minutos de duración
        long duracionMillis = fechaFin.getTime() - alquiler.getFechaInicio().getTime();
        int duracionMinutos = (int) (duracionMillis / (60 * 1000));
        if (duracionMinutos <= 0) duracionMinutos = 1;  // Mínimo 1 minuto

        alquiler.setTiempoDuracion(duracionMinutos);

        // Calcular el importe a partir de la duración

        Vehiculo vehiculo = alquiler.getVehiculo();

        double importe = vehiculo.calcularImporte(duracionMinutos);

        // si el usuario es premium, aplicar descuento
        if (usuario.getEsPremium()) {
            importe *= (1 - usuario.getDescuento() / 100);
        }

        // si el usuario no tiene saldo suficiente, no se puede finalizar el alquiler
        if (usuario.getsaldo() < importe) {
            throw new IllegalStateException("Saldo insuficiente para finalizar el alquiler.");
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
    }

    /**
     * Método para reservar un vehículo
     * @param usuario usuario que reserva el alquiler
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
     * @return lista de alquileres
     */
    public List<Alquiler> consultarAlquileres() {
        return this.alquileres;
    }


}

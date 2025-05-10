package es.uned.model;

import es.uned.model.personas.Trabajador;
import es.uned.model.personas.Usuario;
import es.uned.model.vehiculos.Vehiculo;

import java.util.Date;

/**
 * Representa una incidencia registrada por un usuario, relacionada con un vehículo o una base.
 */
public class Incidencia {

    private final String id;
    private Usuario usuario;
    private Vehiculo vehiculo;
    private final String descripcion;
    private final Date fechaReporte;
    private Trabajador encargado;
    private Base base;


    /**
     * Constructor general sin vehículo/base.
     */
    public Incidencia(Usuario usuario, String descripcion, Date fechaReporte, Base base, Vehiculo vehiculo) {
        this.id = String.valueOf(System.currentTimeMillis()); // valor aleatorio
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.fechaReporte = fechaReporte;
        this.base = base;
        this.vehiculo = vehiculo;
    }

    public String getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getDescripcion() {
        return descripcion;
    }


    public Date getFechaReporte() {
        return fechaReporte;
    }


    public Trabajador getEncargado() {
        return encargado;
    }

    public void setEncargado(Trabajador encargado) {
        this.encargado = encargado;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }


    /**
     * Método para mostrar la información de la incidencia en la consola.
     */
    public void mostrarInformacionDetalladaIncidencia() {
        System.out.println("ID: " + id);
        System.out.println("Descripción: " + descripcion);
        System.out.println("Fecha de reporte: " + fechaReporte);
        if (base != null) {
            System.out.println("Base afectada: " + base.getId());
            System.out.println("Tiempo de inactividad " + base.getTiempoInactividad() + " horas");
        }
        if (vehiculo != null) {
            System.out.println("Vehículo afectado: " + vehiculo.getMatricula());
            System.out.println("Tiempo de inactividad " + vehiculo.getTiempoInactividad() + " horas");

        }
        System.out.println("-------------------------------");
    }


    /**
     * Método para mostrar la información de la incidencia en la consola.
     * @return true si se ha asignado un encargado, false en caso contrario.
     */
    public Boolean mostrarAsignacionEncargado() {
        if (encargado != null) {
            System.out.println("ID Incidencia: " + id);
            System.out.println("Asignada a: " + encargado.getNombre() + " " + encargado.getApellidos());
            System.out.println("----------------------------------");
            return true;
        }
        return false;
    }

    /**
     * Método para mostrar la información de la incidencia en la consola.
     */
    public void mostrarConfirmacion() {
        System.out.println("Incidencia generada con éxito.");
        System.out.println("ID: " + id);
        System.out.println("Descripción: " + descripcion);
        System.out.println("Fecha: " + fechaReporte);
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "id='" + id + '\'' +
                ", usuario=" + usuario +
                ", vehiculo=" + vehiculo +
                ", descripcion='" + descripcion + '\'' +
                ", fechaReporte=" + fechaReporte +
                ", encargado=" + encargado +
                '}';
    }
}

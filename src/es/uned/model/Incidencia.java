package es.uned.model;

import es.uned.model.personas.Trabajador;
import es.uned.model.personas.Usuario;
import es.uned.model.vehiculos.Vehiculo;

import java.util.Date;

/**
 * Clase Incidencia que representa una incidencia reportada por un usuario.
 */
public class Incidencia {

    private String id;
    private Usuario usuario;
    private Vehiculo vehiculo;
    private String descripcion;
    private Date fechaReporte;
    private Trabajador encargado;

    public Incidencia(String id, Usuario usuario, Vehiculo vehiculo, String descripcion, Date fechaReporte, Trabajador encargado) {
        this.id = id;
        this.usuario = usuario;
        this.vehiculo = vehiculo;
        this.descripcion = descripcion;
        this.fechaReporte = fechaReporte;
        this.encargado = encargado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(Date fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public Trabajador getEncargado() {
        return encargado;
    }

    public void setEncargado(Trabajador encargado) {
        this.encargado = encargado;
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

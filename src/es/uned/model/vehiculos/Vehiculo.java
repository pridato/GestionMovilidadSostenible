package es.uned.model.vehiculos;

import es.uned.enums.EstadoMoto;
import es.uned.enums.EstadoVehiculo;
import es.uned.model.Coordenadas;

/**
 * Clase abstracta Vehiculo que representa un vehículo con atributos como matrícula, coordenadas, estado, batería, tarifa por minuto y penalización.
 */
public abstract class Vehiculo {

    private String matricula;
    private Coordenadas coordenadas;
    private EstadoVehiculo estado;
    private int bateria;
    private double tarifaMinuto;
    private double penalizacion;

    public Vehiculo(String matricula, Coordenadas coordenadas, EstadoVehiculo estado, int bateria, double tarifaMinuto, double penalizacion) {
        this.matricula = matricula;
        this.coordenadas = coordenadas;
        this.estado = estado;
        this.bateria = bateria;
        this.tarifaMinuto = tarifaMinuto;
        this.penalizacion = penalizacion;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Coordenadas coordenadas) {
        this.coordenadas = coordenadas;
    }

    public EstadoVehiculo getEstado() {
        return estado;
    }

    public void setEstado(EstadoVehiculo estado) {
        this.estado = estado;
    }

    public int getBateria() {
        return bateria;
    }

    public void setBateria(int bateria) {
        this.bateria = bateria;
    }

    public double getTarifaMinuto() {
        return tarifaMinuto;
    }

    public void setTarifaMinuto(double tarifaMinuto) {
        this.tarifaMinuto = tarifaMinuto;
    }

    public double getPenalizacion() {
        return penalizacion;
    }

    public void setPenalizacion(double penalizacion) {
        this.penalizacion = penalizacion;
    }

    public abstract void setConsumoMinuto(double consumoMinuto);

    public abstract double calcularImporte(int minutos);


    @Override
    public String toString() {
        return "Vehiculo{" +
                "matricula='" + matricula + '\'' +
                ", coordenadas=" + coordenadas +
                ", estado=" + estado +
                ", bateria=" + bateria +
                ", tarifaMinuto=" + tarifaMinuto +
                ", penalizacion=" + penalizacion +
                '}';
    }
}

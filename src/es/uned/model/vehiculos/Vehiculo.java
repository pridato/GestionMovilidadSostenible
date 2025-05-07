package es.uned.model.vehiculos;

import es.uned.enums.EstadoVehiculo;
import es.uned.model.Coordenadas;
import es.uned.model.personas.Usuario;

/**
 * Clase abstracta Vehiculo que representa un vehículo con atributos como matrícula, coordenadas, estado, batería, tarifa por minuto y penalización.
 */
public abstract class Vehiculo {

    private String matricula;
    private Coordenadas coordenadas;
    private EstadoVehiculo estado;
    protected double bateria;
    private double tarifaMinuto;
    private double penalizacion;

    public Vehiculo(String matricula, Coordenadas coordenadas, EstadoVehiculo estado, double bateria, double tarifaMinuto, double penalizacion) {
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

    public double getBateria() {
        return bateria;
    }

    public void setBateria(double bateria) {
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

    public abstract void calcularBateriaRestante(int minutos);

    public abstract double calcularImporte(int minutos);

    /**
     * Comprueba si el vehículo tiene batería suficiente y si no está reservado o averiado.
     */
    public void verificarDisponibilidad(Usuario usuario) throws IllegalArgumentException {
        int umbralMinimo = usuario.getEsPremium() ? 10 : 20;

        if (bateria < umbralMinimo) {
            throw new IllegalStateException("El vehículo no tiene batería suficiente"
                    + (usuario.getEsPremium() ? " para usuarios premium." : "."));
        }

        if (estado == EstadoVehiculo.AVERIADO) {
            throw new IllegalStateException("El vehículo está averiado.");
        }

        if (estado == EstadoVehiculo.RESERVADO && usuario.getEsPremium()) {
            throw new IllegalStateException("El vehículo está reservado.");
        }
    }

    public String toString() {
        final String INDENT = "    ";
        return getClass().getSimpleName() + " {\n" +  // Nombre de la clase (Bicicleta, Moto, Patinete, etc.)
                INDENT + "Matrícula: " + getMatricula() + "\n" +
                INDENT + "Coordenadas: " + getCoordenadas().toString() + "\n" +
                INDENT + "Estado: " + getEstado().toString() + "\n" +
                INDENT + "Batería: " + getBateria() + "%\n" +
                INDENT + "Tarifa por minuto: €" + String.format("%.2f", getTarifaMinuto()) + "\n" +
                INDENT + "Penalización: €" + String.format("%.2f", getPenalizacion()) + "\n" +
                "}";
    }
}

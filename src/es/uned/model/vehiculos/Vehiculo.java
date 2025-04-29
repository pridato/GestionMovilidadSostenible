package es.uned.model.vehiculos;

import es.uned.enums.EstadoVehiculo;
import es.uned.model.Coordenadas;

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

    public abstract void calcularBateriaRestante(int minutos);

    public abstract double calcularImporte(int minutos);

    public void setAveriado() {
        this.estado = EstadoVehiculo.AVERIADO;
    }

    /**
     * Método para procesar el consumo de batería y calcular el importe a pagar.
     * @param minutos minutos de uso del vehículo
     * @param consumoMinuto consumo por minuto
     * @return importe a pagar
     */
    protected double procesarConsumo(int minutos, double consumoMinuto) {
        double bateriaConsumida = consumoMinuto * minutos;
        int nuevaBateria = (int) (getBateria() - (int) bateriaConsumida);
        setBateria(Math.max(nuevaBateria, 0));

        double penalizacionAplicada = 0;
        if (nuevaBateria <= 0) {
            penalizacionAplicada = 1.0;
            setPenalizacion(getPenalizacion() + penalizacionAplicada);
        }

        return minutos * getTarifaMinuto() + penalizacionAplicada;
    }


    @Override
    public String toString() {
        final String INDENT = "    ";
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append(" {\n");  // Nombre de la clase (Bicicleta, Moto, Patinete, etc.)
        sb.append(INDENT).append("Matrícula: ").append(getMatricula()).append("\n");
        sb.append(INDENT).append("Coordenadas: ").append(getCoordenadas().toString()).append("\n");
        sb.append(INDENT).append("Estado: ").append(getEstado().toString()).append("\n");
        sb.append(INDENT).append("Batería: ").append(getBateria()).append("%\n");
        sb.append(INDENT).append("Tarifa por minuto: €").append(String.format("%.2f", getTarifaMinuto())).append("\n");
        sb.append(INDENT).append("Penalización: €").append(String.format("%.2f", getPenalizacion())).append("\n");
        sb.append("}");
        return sb.toString();
    }
}

package es.uned.model.vehiculos;

import es.uned.enums.EstadoVehiculo;
import es.uned.model.Coordenadas;

/**
 * Clase Bicicleta que hereda de la clase Vehiculo.
 */
public class Bicicleta extends Vehiculo{

    public Bicicleta(String matricula, Coordenadas coordenadas, EstadoVehiculo estado, int bateria, double tarifaMinuto, double penalizacion) {
        super(matricula, coordenadas, estado, bateria, tarifaMinuto, penalizacion);
    }

    @Override
    public void setConsumoMinuto(double consumoMinuto) {

    }

    @Override
    public double calcularImporte(int minutos) {
        return 0;
    }

    @Override
    public String toString() {
        return "Bicicleta{" +
                "matricula='" + getMatricula() + '\'' +
                ", coordenadas=" + getCoordenadas() +
                ", estado=" + getEstado() +
                ", bateria=" + getBateria() +
                ", tarifaMinuto=" + getTarifaMinuto() +
                ", penalizacion=" + getPenalizacion() +
                '}';
    }
}

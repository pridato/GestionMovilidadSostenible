package es.uned.model.vehiculos;

import es.uned.enums.EstadoVehiculo;
import es.uned.model.Coordenadas;

/**
 * Clase Bicicleta que hereda de la clase Vehiculo.
 */
public class Bicicleta extends Vehiculo{

    private final double consumoMinuto = 1.0;

    public Bicicleta(String matricula, Coordenadas coordenadas, EstadoVehiculo estado, int bateria, double tarifaMinuto, double penalizacion) {
        super(matricula, coordenadas, estado, bateria, tarifaMinuto, penalizacion);
    }

    @Override
    public void calcularBateriaRestante(int minutos) {
        bateria -= (int) procesarConsumo(minutos, consumoMinuto);
    }

    @Override
    public double calcularImporte(int minutos) {
        return procesarConsumo(minutos, consumoMinuto);
    }

    @Override
    public String toString() {
        return super.toString() + "consumoMinuto=" + consumoMinuto;
    }

}

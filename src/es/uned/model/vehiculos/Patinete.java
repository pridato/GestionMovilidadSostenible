package es.uned.model.vehiculos;

import es.uned.enums.EstadoVehiculo;
import es.uned.model.Coordenadas;

import static es.uned.utils.consts.TARIFA_MINUTO_PATINETE;

/**
 * Clase Patinete que hereda de la clase Vehiculo.
 */
public class Patinete extends Vehiculo {

    private final double consumoMinuto = 0.5;

    public Patinete(String matricula, Coordenadas coordenadas, EstadoVehiculo estado, int bateria, double tarifaMinuto, double penalizacion) {
        super(matricula, coordenadas, estado, bateria, tarifaMinuto, penalizacion);
    }

    public Patinete(String matricula, Coordenadas coordenadas, EstadoVehiculo estado,  double penalizacion) {
        super(matricula, coordenadas, estado, 100, TARIFA_MINUTO_PATINETE, penalizacion);
    }

    @Override
    public void calcularBateriaRestante(int minutos) {
        // Se calcula el consumo de bateria restando la bateria menos la bateria consumida
        double bateriaConsumida = minutos * consumoMinuto;
        if (bateriaConsumida > getBateria()) {
            System.out.println("No hay suficiente bateria para realizar el alquiler");
            return;
        }
        setBateria((bateria - bateriaConsumida));
    }


    @Override
    public double calcularImporte(int minutos) {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString() + "consumoMinuto=" + consumoMinuto;
    }

}

package es.uned.model.vehiculos;

import es.uned.enums.EstadoMoto;
import es.uned.enums.EstadoVehiculo;
import es.uned.model.Coordenadas;

import static es.uned.utils.consts.TARIFA_MINUTO_MOTO;

public class Moto extends Vehiculo{
    private EstadoMoto estado;
    private double consumoMinuto;

    public Moto(String matricula, Coordenadas coordenadas, EstadoVehiculo estado, int bateria, double tarifaMinuto, double penalizacion, EstadoMoto estadoMoto) {
        super(matricula, coordenadas, estado, bateria, tarifaMinuto, penalizacion);
        this.estado = estadoMoto;
        if (estadoMoto == EstadoMoto.PEQUEÑA) {
            consumoMinuto = 0.4;
        } else if (estadoMoto == EstadoMoto.GRANDE) {
            consumoMinuto = 0.25;
        }
    }

    public Moto(String matricula, Coordenadas coordenadas, EstadoVehiculo estado, double penalizacion, EstadoMoto estadoMoto) {
        super(matricula, coordenadas, estado, 100, TARIFA_MINUTO_MOTO, penalizacion);
        this.estado = estadoMoto;
        if (estadoMoto == EstadoMoto.PEQUEÑA) {
            consumoMinuto = 0.4;
        } else if (estadoMoto == EstadoMoto.GRANDE) {
            consumoMinuto = 0.25;
        }
    }


    public void setEstado(EstadoMoto estado) {
        this.estado = estado;
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
        return getTarifaMinuto() * minutos;
    }


    @Override
    public String toString() {
        return super.toString() + "\nEstado Moto: " + estado.toString() + "\n" +
                "Consumo por minuto: " + consumoMinuto + "\n";
    }
}


package es.uned.model.vehiculos;

import es.uned.enums.EstadoMoto;
import es.uned.enums.EstadoVehiculo;
import es.uned.model.Coordenadas;

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

    public EstadoMoto getEstadoMoto() {
        return estado;
    }

    public void setEstado(EstadoMoto estado) {
        this.estado = estado;
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
        return super.toString() + "\nEstado Moto: " + estado.toString() + "\n" +
                "Consumo por minuto: " + consumoMinuto + "\n";
    }
}


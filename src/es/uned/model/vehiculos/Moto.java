package es.uned.model.vehiculos;

import es.uned.enums.EstadoMoto;
import es.uned.enums.EstadoVehiculo;
import es.uned.model.Coordenadas;

public class Moto extends Vehiculo{
    private EstadoMoto estado;

    public Moto(String matricula, Coordenadas coordenadas, EstadoVehiculo estado, int bateria, double tarifaMinuto, double penalizacion, EstadoMoto estadoMoto) {
        super(matricula, coordenadas, estado, bateria, tarifaMinuto, penalizacion);
        this.estado = estadoMoto;
    }

    public EstadoMoto getEstadoMoto() {
        return estado;
    }

    public void setEstado(EstadoMoto estado) {
        this.estado = estado;
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
        return super.toString() + "\nEstado Moto: " + estado.toString() + "\n";
    }
}

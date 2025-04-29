package es.uned.gestores;

import es.uned.model.vehiculos.Vehiculo;

public class GestorBateria {

    /**
     * Comprueba si la batería de un vehículo es suficiente para realizar un trayecto
     * @param bateria nivel de batería del vehículo
     * @param umbralMinimo nivel mínimo de batería necesario para realizar el trayecto
     * @return true si la batería es suficiente, false en caso contrario
     */
    public static boolean tieneBateriaSuficiente(int bateria, int umbralMinimo) {
        return bateria >= umbralMinimo;
    }

    /**
     * Aplica el consumo de batería a un vehículo
     * @param v vehículo al que se le aplica el consumo
     * @param minutos tiempo de consumo en minutos
     * @param consumoPorMinuto consumo de batería por minuto
     */
    public static void aplicarConsumo(Vehiculo v, int minutos, double consumoPorMinuto) {
        int bateriaActual = v.getBateria();
        int bateriaConsumida = (int) Math.round(minutos * consumoPorMinuto);
        int nuevaBateria = Math.max(0, bateriaActual - bateriaConsumida);
        v.setBateria(nuevaBateria);
    }
}

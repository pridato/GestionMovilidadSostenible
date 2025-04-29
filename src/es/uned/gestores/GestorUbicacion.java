package es.uned.gestores;

import es.uned.enums.EstadoVehiculo;
import es.uned.model.Coordenadas;
import es.uned.model.vehiculos.Vehiculo;

import java.util.List;

/**
 * Clase GestorUbicacion
 *
 * Métodos:
 *
 * estaDentroLimites: Comprueba si una coordenada está dentro de los límites definidos por dos coordenadas
 *
 * buscarVehiculoMasCercano: Busca el vehículo más cercano a unas coordenadas dadas
 */
public class GestorUbicacion {

    /**
     * Comprueba si una coordenada está dentro de los límites definidos por dos coordenadas
     * @param coordenada Coordenada a comprobar
     * @param esquinaInferiorIzquierda límite inferior izquierdo
     * @param esquinaSuperiorDerecha límite superior derecho
     * @return true si la coordenada está dentro de los límites, false en caso contrario
     */
    public static boolean estaDentroLimites(Coordenadas coordenada, Coordenadas esquinaInferiorIzquierda, Coordenadas esquinaSuperiorDerecha) {
        return coordenada.getX() >= esquinaInferiorIzquierda.getX() &&
                coordenada.getX() <= esquinaSuperiorDerecha.getX() &&
                coordenada.getY() >= esquinaInferiorIzquierda.getY() &&
                coordenada.getY() <= esquinaSuperiorDerecha.getY();
    }

    /**
     * Busca el vehículo más cercano a unas coordenadas dadas
     *
     * <p><strong>Advertencia:</strong> La lista de vehículos debe contener únicamente vehículos disponibles.
     * @param coordenadasUsuario coordenadas del usuario
     * @param vehiculos lista de vehículos disponibles
     * @return el vehículo más cercano a las coordenadas del usuario
     *
     * @throws IllegalArgumentException si el vehículo no está disponible
     */
    public static Vehiculo buscarVehiculoMasCercano(Coordenadas coordenadasUsuario, List<Vehiculo> vehiculos) {
        Vehiculo masCercano = null;
        double menorDistancia = Double.MAX_VALUE;

        for (Vehiculo v : vehiculos) {
            if(v.getEstado() != EstadoVehiculo.DISPONIBLE) {
                throw new IllegalArgumentException("El vehículo no está disponible");
            }

            double distancia = coordenadasUsuario.Calculardistancia(v.getCoordenadas());
            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                masCercano = v;
            }
        }

        return masCercano;
    }
}

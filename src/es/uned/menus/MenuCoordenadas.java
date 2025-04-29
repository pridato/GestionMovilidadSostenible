package es.uned.menus;

import es.uned.model.Coordenadas;

import java.util.Scanner;

public class MenuCoordenadas {

    private static Coordenadas limiteInferior;
    private static Coordenadas limiteSuperior;

    /**
     * Muestra el menú para establecer límites de coordenadas y gestiona la entrada del usuario.
     * @param scanner Scanner para leer la entrada del usuario.
     */
    public static void establecerLimites(Scanner scanner) {
        System.out.println("\n--- Establecer límites de coordenadas ---");
        System.out.print("Latitud mínima: ");
        double latMin = scanner.nextDouble();
        System.out.print("Longitud mínima: ");
        double lonMin = scanner.nextDouble();
        System.out.print("Latitud máxima: ");
        double latMax = scanner.nextDouble();
        System.out.print("Longitud máxima: ");
        double lonMax = scanner.nextDouble();

        limiteInferior = new Coordenadas(latMin, lonMin);
        limiteSuperior = new Coordenadas(latMax, lonMax);

        System.out.println("Límites establecidos.");
    }

    /**
     * Devuelve las coordenadas del límite inferior.
     * @return Coordenadas del límite inferior.
     */
    public static Coordenadas getLimiteInferior() {
        return limiteInferior;
    }

    /**
     * Devuelve las coordenadas del límite superior.
     * @return Coordenadas del límite superior.
     */
    public static Coordenadas getLimiteSuperior() {
        return limiteSuperior;
    }
}

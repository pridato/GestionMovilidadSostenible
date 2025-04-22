package es.uned.model;

/**
 * Clase que representa las coordenadas de un punto en un plano cartesiano.
 */
public class Coordenadas {
    private double x;
    private double y;

    public Coordenadas(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "Coordenadas{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

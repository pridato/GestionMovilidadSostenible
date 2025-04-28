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

    /**
     * Calcula la distancia entre dos puntos en un plano cartesiano.
     * @param c coordenadas del segundo punto
     * @return distancia entre los dos puntos
     */
    public double Calculardistancia(Coordenadas c) {
        return Math.sqrt(Math.pow(c.getX() - this.x, 2) + Math.pow(c.getY() - this.y, 2));
    }

    @Override
    public String toString() {
        final String INDENT = "    ";
        return "Coordenadas {\n" +
                INDENT + "X: " + x + "\n" +
                INDENT + "Y: " + y + "\n" +
                "}";
    }

}

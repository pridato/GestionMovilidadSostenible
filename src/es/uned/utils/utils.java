package es.uned.utils;

import es.uned.model.Coordenadas;
import es.uned.model.personas.Usuario;

import java.util.Date;
import java.util.Scanner;

public class utils {
    /**
     * Método para mostrar el menú principal del sistema de gestión de movilidad.
     */
    public static void mostrarMenuPrincipal() {
        System.out.println("Bienvenido al sistema de gestión de movilidad");
        System.out.println("1. Gestión de usuarios");
        System.out.println("2. Gestión de vehículos");
        System.out.println("3. Gestión de viajes");
        System.out.println("4. Gestión de incidencias");
        System.out.println("5. Gestión de bases");
        System.out.println("6. Gestión de estadísticas");
        System.out.println("7. Gestión de informes");
    }

    /**
     * Método para limpiar la consola.
     */
    public static void limpiarPantalla() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

}

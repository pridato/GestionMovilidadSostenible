package es.uned.utils;

import es.uned.enums.EstadoMoto;
import es.uned.enums.EstadoVehiculo;
import es.uned.model.*;
import es.uned.model.personas.*;
import es.uned.model.vehiculos.Bicicleta;
import es.uned.model.vehiculos.Moto;
import es.uned.model.vehiculos.Patinete;
import es.uned.model.vehiculos.Vehiculo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class dto {


    /**
     * Método para crear una lista de personas de ejemplo.
     *
     * @return Lista de personas.
     */
    public static List<Persona> cargarPersonas() {
        List<Persona> personas = new ArrayList<>();

        personas.add(
                new Usuario(
                        "09323452A",
                        "Laura",
                        "Sánchez",
                        "laura@gmail.com",
                        612345678,
                        new Date(),
                        new Coordenadas(9.5, 7.9),
                        2000.0
        ));

        personas.add(new Usuario(
                        "06428545X",
                        "Paco",
                        "Ruiz",
                        "paco@gmail.com",
                        575324644,
                        new Date(),
                        new Coordenadas(14.5, 8.9),
                        7500.00
                )
        );

        personas.add(new Usuario(
                        "09248632X",
                        "David",
                        "Arroyo Esquinas",
                        "david@gmail.com",
                        637604644,
                        new Date(),
                        new Coordenadas(10, 12.9),
                        10000.00
                )
        );

        personas.add(
                new Trabajador(
                        "06428542X",
                        "Paco",
                        "Ruiz",
                        "paco@gmail.com",
                        575324644,
                        new Date()

                )
        );

        personas.add(
                new Mantenimiento(
                        "06428544X",
                        "Sara",
                        "Mrtínez",
                        "sara@gmail.com",
                        575324644,
                        new Date()

                )
        );

        personas.add(
                new Mecanico(
                        "09247521X",
                        "David",
                        "Mecanico",
                        "mecanico@gmail.com",
                        575324644,
                        new Date()
                )
        );

        personas.add(
                new Administrador(
                        "06428543X",
                        "Sara",
                        "Mrtínez",
                        "sara@gmail.com",
                        575324644,
                        new Date()
                )
        );

        return personas;
    }

    /**
     * Método para crear una lista de trabajadores filtrada de cargarPersonas
     *
     * @return Lista de trabajadores.
     */
    public static List<Trabajador> cargarTrabajadores() {
        return cargarPersonas().stream().filter(persona -> persona instanceof Trabajador)
                .map(persona -> (Trabajador) persona)
                .toList();
    }



    /**
     * Método para crear una lista de bases de ejemplo.
     *
     * @return Lista de bases.
     */
    public static List<Base> cargarBases() {
        List<Base> bases = new ArrayList<>();
        Base base1 = new Base("Base1", new Coordenadas(9.5, 7.9), 100);
        base1.añadirVehiculo(cargarVehiculos().get(1));

        Base base2 = new Base("Base2", new Coordenadas(14.5, 8.9), 200);
        base2.añadirVehiculo(cargarVehiculos().get(2));

        Base base3 = new Base("Base3", new Coordenadas(12.5, 6.9), 300);
        base3.añadirVehiculo(cargarVehiculos().get(3));

        bases.add(base1);
        bases.add(base2);
        bases.add(base3);

        return bases;
    }

    /**
     * Método para crear una lista de vehículos de ejemplo.
     *
     * @return Lista de vehículos.
     */
    public static List<Vehiculo> cargarVehiculos() {
        List<Vehiculo> vehiculos = new ArrayList<>();

        vehiculos.add(new Moto("MOT123", new Coordenadas(9.5, 7.9), EstadoVehiculo.DISPONIBLE,  50.0, EstadoMoto.PEQUEÑA));
        vehiculos.add(new Moto("MOT124", new Coordenadas(14.5, 8.9), EstadoVehiculo.DISPONIBLE,  50.0, EstadoMoto.PEQUEÑA));
        vehiculos.add(new Moto("MOT125", new Coordenadas(12.5, 6.9), EstadoVehiculo.DISPONIBLE, 50.0, EstadoMoto.PEQUEÑA));
        vehiculos.add(new Bicicleta("BIC123", new Coordenadas(9.5, 7.9), EstadoVehiculo.DISPONIBLE, 50.0));
        vehiculos.add(new Bicicleta("BIC124", new Coordenadas(14.5, 8.9), EstadoVehiculo.DISPONIBLE, 50.0));
        vehiculos.add(new Patinete("PAT123", new Coordenadas(12.5, 6.9), EstadoVehiculo.DISPONIBLE, 50.0));

        return vehiculos;
    }
}


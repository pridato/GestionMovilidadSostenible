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
                        "0932345A",
                        "Laura",
                        "Sánchez",
                        "laura@gmail.com",
                        612345678,
                        new Date(),
                        new Coordenadas(9.5, 7.9),
                        2000.0,
                        false,
                        new ArrayList<>()
                )
        );

        personas.add(new Usuario(
                        "06428542X",
                        "Paco",
                        "Ruiz",
                        "paco@gmail.com",
                        575324644,
                        new Date(),
                        new Coordenadas(14.5, 8.9),
                        7500.00,
                        true,
                        new ArrayList<>(List.of(new Alquiler(
                                new Moto(
                                        "MOT123",
                                        new Coordenadas(9.5, 7.9),
                                        EstadoVehiculo.DISPONIBLE,
                                        80,
                                        10000,
                                        50.0,
                                        EstadoMoto.PEQUEÑA
                                ),
                                null,
                                new Date(),
                                null,
                                null,
                                null,
                                0.0,
                                0
                        )))
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
                        "06428542X",
                        "Sara",
                        "Mrtínez",
                        "sara@gmail.com",
                        575324644,
                        new Date(),
                        new ArrayList<>(
                                List.of(new Base("Base1", new Coordenadas(9.5, 7.9), 100))
                        )
                )
        );

        personas.add(
                new Administrador(
                        "06428542X",
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
     * Método para crear una lista de usuarios filtrada de cargarPersonas
     * @return Lista de usuarios.
     */
    public static List<Usuario> cargarUsuarios() {
        return cargarPersonas().stream().filter(persona -> persona instanceof Usuario)
                .map(persona -> (Usuario) persona)
                .toList();
    }



    /**
     * Método para crear una lista de bases de ejemplo.
     *
     * @return Lista de bases.
     */
    public static List<Base> cargarBases() {
        List<Base> bases = new ArrayList<>();

        bases.add(new Base("Base1", new Coordenadas(9.5, 7.9), 100));
        bases.add(new Base("Base2", new Coordenadas(14.5, 8.9), 200));
        bases.add(new Base("Base3", new Coordenadas(12.5, 6.9), 300));

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

    /**
     * Método para crear una lista de facturas de ejemplo.
     * @return Lista de facturas.
     */
    public static List<Factura> cargarFacturas() {
        List<Factura> facturas = new ArrayList<>();

        facturas.add(
                new Factura(
                        "FAC123",
                        cargarVehiculos().getFirst(),
                        new Mecanico(
                                "Pedro",
                                "Sánchez",
                                "56789012E",
                                "pedro@example.com",
                                644556677,
                                new Date(), // Fecha de contratación actual
                                new ArrayList<>(),
                                new ArrayList<>()
                        ),
                        100.0,
                        "Cambio de aceite y revisión",
                        new Date()
                )
        );

        facturas.add(
                new Factura(
                        "FAC124",
                        cargarVehiculos().get(1),
                        new Mecanico(
                                "Raúl",
                                "Perez",
                                "86528632X",
                                "raul@gmail.com",
                                863265411,
                                new Date(), // Fecha de contratación actual
                                new ArrayList<>(),
                                new ArrayList<>()
                        ),
                        100.0,
                        "Ajuste de frenos",
                        new Date()
                )
        );

        facturas.add(
                new Factura(
                        "FAC125",
                        cargarVehiculos().getFirst(),
                        new Mecanico(
                                "David",
                                "Arroyo",
                                "09757321X",
                                "david@gmail.com",
                                644556677,
                                new Date(), // Fecha de contratación actual
                                new ArrayList<>(),
                                new ArrayList<>()
                        ),
                        100.0,
                        "Embrague nuevo",
                        new Date()
                )
        );

        return facturas;
    }

    /**
     * Método para crear una lista de incidencias de ejemplo.
     * @return Lista de incidencias.
     */
    public static List<Incidencia> cargarIncidencias() {
        List<Incidencia> incidencias = new ArrayList<>();

        incidencias.add(new Incidencia("INC123", (Usuario)cargarPersonas().getFirst(), cargarVehiculos().get(1), "En reparación", new Date(), (Trabajador)cargarPersonas().get(2)));
        incidencias.add(new Incidencia("INC124", (Usuario)cargarPersonas().getFirst(),cargarVehiculos().getFirst(), "En reparación", new Date(), (Trabajador)cargarPersonas().get(2)));
        incidencias.add(new Incidencia("INC125",(Usuario)cargarPersonas().getFirst(), cargarVehiculos().getLast(), "En reparación", new Date(), (Trabajador)cargarPersonas().get(2)));

        return incidencias;
    }
}


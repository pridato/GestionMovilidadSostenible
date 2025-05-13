package es.uned.gestores;

import es.uned.enums.EstadoMoto;
import es.uned.enums.EstadoVehiculo;
import es.uned.model.Alquiler;
import es.uned.model.Base;
import es.uned.model.Coordenadas;
import es.uned.model.personas.Usuario;
import es.uned.model.vehiculos.Bicicleta;
import es.uned.model.vehiculos.Moto;
import es.uned.model.vehiculos.Patinete;
import es.uned.model.vehiculos.Vehiculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static es.uned.menus.MenuAdministrador.gestorBases;
import static es.uned.menus.MenuAdministrador.gestorPersonas;
import static es.uned.utils.dto.cargarVehiculos;
import static es.uned.utils.utils.leerOpcion;

/**
 * Clase GestorVehiculos que gestiona la lista de vehículos.
 */
public class GestorVehiculos {

    // Atributos
    private static final GestorVehiculos instancia = new GestorVehiculos();

    private final List<Vehiculo> vehiculos;


    // Constructor
    public GestorVehiculos() {
        this.vehiculos = new ArrayList<>(cargarVehiculos());

    }

    /**
     * Método para devolver la instancia de la clase GestorVehiculos.
     * <p>
     * Así evitamos crear una nueva instancia cada vez que se necesite.
     *
     * @return
     */
    public static GestorVehiculos getInstancia() {
        return instancia;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }


    /**
     * Método para crear nuevo vehiculo
     *
     * @param scanner Scanner para leer la entrada del usuario
     */
    public void añadirVehiculo(Scanner scanner) {
        System.out.println("Seleccione el tipo de vehículo:");
        System.out.println("1. Moto");
        System.out.println("2. Patinete");
        System.out.println("3. Bicicleta");
        System.out.print("Opción: ");
        int opcion = leerOpcion(scanner);

        Vehiculo vehiculo = null;

        switch (opcion) {
            case 1 -> vehiculo = crearMoto(scanner);
            case 2 -> vehiculo = crearPatinete(scanner);
            case 3 -> vehiculo = crearBicicleta(scanner);
            default -> System.out.println("Opción no válida.");
        }

        if (vehiculo != null && !this.vehiculos.contains(vehiculo)) {
            if (!(vehiculo instanceof Moto)) {
                gestorBases.consultarBasesDisponibles();
                System.out.println("Seleccione la base a la que desea añadir el vehículo:");
                String idBase = scanner.nextLine();
                Base base = gestorBases.consultarBasePorId(idBase);
                base.añadirVehiculo(vehiculo);
            }

            this.vehiculos.add(vehiculo);
            System.out.println("Vehículo añadido correctamente.");
            return;
        }
        System.out.println("No se pudo añadir el vehículo.");
    }

    /**
     * Ya que los 3 tipos de vehículos tienen atributos comunes, se crea un método para leer los datos comunes.
     *
     * @param scanner Scanner para leer la entrada del usuario
     * @param tipo    Tipo de vehículo (moto, patinete, bicicleta)
     * @return Array de objetos con los datos comunes del vehículo
     */
    private static Object[] leerDatosComunesVehiculo(Scanner scanner, String tipo) {
        System.out.print("Introduce la matrícula del " + tipo + ": ");
        String matricula = scanner.nextLine();

        System.out.print("Introduce la coordenada X: ");
        double x = scanner.nextDouble();

        System.out.print("Introduce la coordenada Y: ");
        double y = scanner.nextDouble();
        scanner.nextLine(); // Limpieza de buffer

        Coordenadas coordenadas = new Coordenadas(x, y);
        EstadoVehiculo estadoVehiculo = EstadoVehiculo.DISPONIBLE;
        ;
        try {
            System.out.println("Selecciona el tipo de moto (DISPONIBLE, ALQUILADO, AVERIADO o RESERVADO): ");
            estadoVehiculo = EstadoVehiculo.valueOf(scanner.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Estado de vehículo no válido. Se asignará DISPONIBLE por defecto.");
        }

        System.out.print("Introduce la penalización (€): ");
        double penalizacion = scanner.nextDouble();
        scanner.nextLine(); // Limpieza de buffer

        return new Object[]{matricula, coordenadas, estadoVehiculo, penalizacion};
    }


    /**
     * Método para crear una moto.
     *
     * @param scanner Scanner para leer la entrada del usuario
     * @return Vehiculo creado
     */
    private static Vehiculo crearMoto(Scanner scanner) {
        Object[] datos = leerDatosComunesVehiculo(scanner, "moto");

        EstadoMoto estadoMoto = EstadoMoto.PEQUEÑA;
        try {
            System.out.print("Selecciona el tamaño de la moto (PEQUEÑA o GRANDE): ");
            estadoMoto = EstadoMoto.valueOf(scanner.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Tamaño no válido. Se asignará PEQUEÑA por defecto.");
        }

        return new Moto(
                (String) datos[0],
                (Coordenadas) datos[1],
                (EstadoVehiculo) datos[2],
                (double) datos[3],
                estadoMoto
        );
    }


    /**
     * Método para crear un patinete.
     *
     * @param scanner Scanner para leer la entrada del usuario
     * @return Vehiculo creado
     */
    private static Vehiculo crearPatinete(Scanner scanner) {
        Object[] datos = leerDatosComunesVehiculo(scanner, "patinete");
        return new Patinete(
                (String) datos[0],
                (Coordenadas) datos[1],
                (EstadoVehiculo) datos[2],
                (double) datos[3]
        );
    }


    /**
     * Método para crear una bicicleta.
     *
     * @param scanner Scanner para leer la entrada del usuario
     * @return Vehiculo creado
     */
    private static Vehiculo crearBicicleta(Scanner scanner) {
        Object[] datos = leerDatosComunesVehiculo(scanner, "bicicleta");
        return new Bicicleta(
                (String) datos[0],
                (Coordenadas) datos[1],
                (EstadoVehiculo) datos[2],
                (double) datos[3]
        );
    }


    /**
     * Método para eliminar un vehículo de la lista de vehículos.
     *
     * @throws IllegalArgumentException si el vehículo no se encuentra
     */
    public void eliminarVehiculo(Scanner scanner) throws IllegalArgumentException {
        System.out.println("Eliminando vehículo...");

        System.out.print("Introduce la matrícula del vehículo a eliminar: ");
        String matricula = scanner.nextLine();
        Vehiculo vehiculo = obtenerVehiculo(matricula);
        if (vehiculo != null && this.vehiculos.contains(vehiculo)) {
            this.vehiculos.remove(vehiculo);
            System.out.println("Vehículo eliminado correctamente.");
            return;
        }
        System.out.println("No se pudo eliminar el vehículo.");
    }

    /**
     * Método para obtener la lista de vehículos.
     *
     * @return Lista de vehículos.
     * @throws IllegalArgumentException si no hay vehículos disponibles
     */
    public void modificarVehiculo(Scanner scanner) throws IllegalArgumentException {

        System.out.println("Modificando vehículo...");
        System.out.print("Introduce la matrícula del vehículo a modificar: ");
        String matricula = scanner.nextLine();
        Vehiculo vehiculo = obtenerVehiculo(matricula);

        int opcion;
        do {
            menuModificacion(vehiculo);
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpieza de buffer

            switch (opcion) {
                case 1 -> {
                    System.out.print("Introduce la nueva matrícula: ");
                    String nuevaMatricula = scanner.nextLine();
                    vehiculo.setMatricula(nuevaMatricula);
                }
                case 2 -> {
                    System.out.print("Introduce la nueva coordenada X: ");
                    double x = scanner.nextDouble();
                    System.out.print("Introduce la nueva coordenada Y: ");
                    double y = scanner.nextDouble();
                    vehiculo.setCoordenadas(new Coordenadas(x, y));
                }

                case 3 -> {
                    System.out.print("Introduce el nuevo estado (DISPONIBLE, ALQUILADO, AVERIADO o RESERVADO): ");
                    EstadoVehiculo nuevoEstado = EstadoVehiculo.valueOf(scanner.nextLine().toUpperCase());
                    vehiculo.setEstado(nuevoEstado);
                }

                case 4 -> {
                    System.out.print("Introduce el nuevo nivel de batería (0-100): ");
                    int bateria = scanner.nextInt();
                    if (bateria < 0 || bateria > 100) {
                        throw new NumberFormatException("La batería debe estar entre 0 y 100.");
                    }
                    vehiculo.setBateria(bateria);
                }

                case 5 -> {
                    System.out.print("Introduce la nueva tarifa por minuto (€): ");
                    double tarifa = scanner.nextDouble();
                    vehiculo.setTarifaMinuto(tarifa);
                }

                case 6 -> {
                    System.out.print("Introduce la nueva penalización (€): ");
                    double penalizacion = scanner.nextDouble();
                    vehiculo.setPenalizacion(penalizacion);
                }

                case 7 -> {
                    if (vehiculo instanceof Moto) {
                        System.out.print("Introduce el nuevo estado de la moto (PEQUEÑA o GRANDE): ");
                        EstadoMoto nuevoEstadoMoto = EstadoMoto.valueOf(scanner.nextLine().toUpperCase());
                        ((Moto) vehiculo).setEstado(nuevoEstadoMoto);
                    } else {
                        System.out.println("Este vehículo no es una moto.");
                    }
                }

                case 0 -> System.out.println("Saliendo del menú de modificación.");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);


    }

    /**
     * Método para mostrar el menú de modificación de un vehículo.
     *
     * @param vehiculo Vehículo a modificar.
     */
    private void menuModificacion(Vehiculo vehiculo) {
        System.out.println("Seleccione el campo a modificar:");
        System.out.println("1. Matrícula");
        System.out.println("2. Coordenadas");
        System.out.println("3. Estado");
        System.out.println("4. Batería");
        System.out.println("5. Tarifa por minuto");
        System.out.println("6. Penalización");
        if (vehiculo instanceof Moto) {
            System.out.println("7. Estado de la moto");
        }
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    /**
     * Método para obtener un vehículo de la lista de vehículos.
     *
     * @param matricula matricula del vehículo a buscar
     * @return El vehículo encontrado o null si no se encuentra.
     * @throws IllegalArgumentException si el vehículo no se encuentra
     */
    public Vehiculo obtenerVehiculo(String matricula) {
        return this.vehiculos.stream()
                .filter(vehiculo -> vehiculo.getMatricula().equals(matricula))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado."));
    }

    /**
     * Consulta todos los vehículos y su relación con los usuarios.
     */
    public void consultarVehiculos() {
        // sacamos todos los usuarios de nuestro sistema
        List<Usuario> usuarios = gestorPersonas.getPersonas().stream()
                .filter(p -> p instanceof Usuario)
                .map(p -> (Usuario) p)
                .toList();

        // Recorremos cada vehículo registrado en el sistema
        for (Vehiculo vehiculo : vehiculos) {
            boolean asignado = false; // comprobar si el vehículo está asignado a un usuario

            // Recorremos cada usuario del sistema
            for (Usuario usuario : usuarios) {
                // Recorremos el historial de alquileres de ese usuario
                for (Alquiler alquiler : usuario.getHistorialViajes()) {
                    Vehiculo vAlquiler = alquiler.getVehiculo();

                    // si el vehículo del alquiler coincide con el actual imprimimos el vehículo
                    if (vAlquiler != null && vAlquiler.getMatricula().equals(vehiculo.getMatricula())) {
                        System.out.println("Vehículo: " + vehiculo.getMatricula() +
                                ", Batería: " + vehiculo.getBateria() + "%" +
                                ", Usuario: " + usuario.getNombre() + " " + usuario.getApellidos() +
                                " (DNI: " + usuario.getdni() + ")");
                        asignado = true;
                        break;
                    }
                }
                if (asignado) break;
            }

            // mostramos no asignados
            if (!asignado) {
                System.out.println("Vehículo: " + vehiculo.getMatricula() +
                        ", Batería: " + vehiculo.getBateria() + "%" +
                        ", Sin alquiler");
            }
        }
    }


    /**
     * Método para consultar el estado de batería de un vehículo.
     */
    public void consultarVehiculosDisponibles() {
        // mostrar vehiculos disponibles y sus bases
        System.out.println("Vehículos disponibles:");
        StringBuilder mensaje;

        for (Vehiculo vehiculo : this.vehiculos) {
            mensaje = new StringBuilder("Vehículo: " + vehiculo.getMatricula() + ", " +
                    "Batería: " + vehiculo.getBateria() + "%, " +
                    "Tarifa por minuto: " + vehiculo.getTarifaMinuto() + "€, penalización: " + vehiculo.getPenalizacion() + "€" +
                    "Penalización: " + vehiculo.getPenalizacion() + "€");
            if (vehiculo.getEstado() == EstadoVehiculo.DISPONIBLE) {
                if (vehiculo instanceof Moto) {
                    mensaje.append(", Coordenadas: (").append(vehiculo.getCoordenadas().getX()).append(", ").append(vehiculo.getCoordenadas().getY()).append("), ");
                } else {
                    Base base = gestorBases.obtenerBaseVehiculo(vehiculo.getMatricula());
                    if (base != null) {
                        mensaje.append(", Base: ").append(base.getId()).append(", Coordenadas: (").append(base.getCoordenadas().getX()).append(", ").append(base.getCoordenadas().getY()).append(")");
                    } else {
                        mensaje.append(", Sin base asignada");
                    }
                }
                System.out.println(mensaje);
            }
        }
    }


    /**
     * Método para consultar el estado de batería de un vehículo.
     *
     * @param vehiculo Vehículo a consultar
     * @param alquiler Alquiler asociado al vehículo
     */
    public void consultarBateria(Vehiculo vehiculo, Alquiler alquiler) {
        if (vehiculo.getEstado() != EstadoVehiculo.ALQUILADO) {
            throw new IllegalStateException("El vehículo no está alquilado.");
        }

        // simulamos que ya ha pasado el tiempo de alquiler
        long tiempoAlquiler = System.currentTimeMillis() - alquiler.getFechaInicio().getTime();
        int minutosAlquiler = (int) (tiempoAlquiler / (60 * 1000));

        System.out.println("Tiempo transcurrido desde el inicio del alquiler: " + minutosAlquiler + " minutos");
        alquiler.setTiempoDuracion(minutosAlquiler);
        vehiculo.calcularBateriaRestante(alquiler.getTiempoDuracion());
        System.out.println("Estado de batería del vehículo: " + vehiculo.getBateria() + "%");
    }

    /**
     * Método para establecer tarifas por defecto en los vehículos.
     *
     * @param scanner Scanner para leer la entrada del usuario
     */
    public void setTarifaMinuto(Scanner scanner) {
        int opcion = 0;

        System.out.println("Seleccione el tipo de vehículo:");
        System.out.println("1. Moto");
        System.out.println("2. Patinete");
        System.out.println("3. Bicicleta");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
        opcion = scanner.nextInt();
        scanner.nextLine(); // Limpieza de buffer

        System.out.println("Estableciendo tarifas de vehículos...");
        System.out.print("Introduce la tarifa por minuto: ");
        double tarifa = scanner.nextDouble();

        switch (opcion) {
            case 1 -> {
                for (Vehiculo vehiculo : this.vehiculos) {
                    if (vehiculo instanceof Moto) {
                        vehiculo.setTarifaMinuto(tarifa);
                    }
                }
            }
            case 2 -> {
                for (Vehiculo vehiculo : this.vehiculos) {
                    if (vehiculo instanceof Patinete) {
                        vehiculo.setTarifaMinuto(tarifa);
                    }
                }
            }
            case 3 -> {
                for (Vehiculo vehiculo : this.vehiculos) {
                    if (vehiculo instanceof Bicicleta) {
                        vehiculo.setTarifaMinuto(tarifa);
                    }
                }
            }
            case 0 -> System.out.println("Saliendo del menú de modificación.");
            default -> System.out.println("Opción no válida.");
        }

    }


    /**
     * Método para consultar el estado de batería de todos los vehículos.
     */
    public void consultarBaterias(Scanner scanner) {
        for (Vehiculo vehiculo : this.vehiculos) {
            if (vehiculo.getEstado() == EstadoVehiculo.DISPONIBLE) {
                System.out.println("Vehículo: " + vehiculo.getMatricula() + ", Batería: " + vehiculo.getBateria() + "%");
            }
        }

        System.out.println("Presione ENTER para continuar...");
        scanner.nextLine();
    }

    /**
     * Método para consultar los vehículos averiados.
     *
     * @throws IllegalStateException si no hay vehículos disponibles
     */
    public void consultarVehiculosAveriados() throws IllegalStateException {
        List<Vehiculo> vehiculosAveriados = vehiculos.stream().filter(v -> v.getEstado() == EstadoVehiculo.AVERIADO).toList();
        System.out.println("Vehículos averiados:");

        if (vehiculosAveriados.isEmpty()) {
            throw new IllegalStateException("No hay vehículos averiados.");
        }

        for (Vehiculo vehiculo : vehiculosAveriados) {
            String tipoVehiculo =
                    vehiculo instanceof Moto ? "Moto" : vehiculo instanceof Patinete ? "Patinete" :
                            vehiculo instanceof Bicicleta ? "Bicicleta" : "Desconocido";
            System.out.println(tipoVehiculo + ", Matricula: " + vehiculo.getMatricula() + ", Batería: " + vehiculo.getBateria() + "%, Tarifa por minuto: " + vehiculo.getTarifaMinuto() + "€, penalización: " + vehiculo.getPenalizacion() + "€");
        }
    }


}

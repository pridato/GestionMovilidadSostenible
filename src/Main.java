import es.uned.model.GestorMovilidad;
import es.uned.model.personas.Persona;
import es.uned.model.personas.Usuario;
import es.uned.utils.utils;
import es.uned.utils.utilsUsuarios;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // inicialización de variables
        Scanner sc = new Scanner(System.in);
        GestorMovilidad gm = new GestorMovilidad();
        utilsUsuarios utilsUsuarios = new utilsUsuarios();



        int opcion = 0;
        do {
            // Mostrar el menú principal
            utils.mostrarMenuPrincipal();
            System.out.print("Seleccione una opción: ");
            try {
                opcion = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
                sc.next();
                continue;
            }

            switch (opcion) {
                case 1:
                    // Gestión de usuarios
                    utilsUsuarios.mostrarMenuGestionUsuarios();
                    int opcionUsuario = sc.nextInt();

                    switch (opcionUsuario) {
                        case 1:
                            // Añadir usuario
                            try {
                                Usuario usuario = utilsUsuarios.crearUsuario(sc);
                                if (gm.añadirUsuario(usuario)) System.out.println("Usuario añadido correctamente.");
                                else System.out.println("Error al añadir el usuario.");
                            } catch (Exception e) {
                                System.out.println("Error al crear el usuario: " + e.getMessage());
                                System.out.println("Saliendo al menú principal...");
                                break;
                            }
                            break;
                        case 2:
                            // Modificar usuario
                            System.out.print("Ingrese el DNI del usuario a modificar: ");
                            String dniModificar = sc.next();

                            if(gm.modificarPersona(dniModificar, sc)) {
                                System.out.println("Usuario modificado correctamente.");
                            } else {
                                System.out.println("Error al modificar el usuario.");
                            }
                            break;
                        case 3:
                            // Eliminar usuario
                            System.out.println("Ingrese el DNI del usuario a eliminar: ");
                            String dniEliminar = sc.next();
                            if(gm.eliminarUsuario(dniEliminar)) {
                                System.out.println("Usuario eliminado correctamente.");
                            } else {
                                System.out.println("Error al eliminar el usuario.");
                            }
                            break;
                        case 4:
                            // Opción listar usuarios, trabajadores o usuarios
                            System.out.println("Seleccione el tipo de usuario a listar:");
                            System.out.println("1. Listar personas");
                            System.out.println("2. Listar trabajadores");
                            System.out.println("3. Listar usuarios premium");
                            System.out.println("4. Listar usuarios no premium");

                            int opcionListar = sc.nextInt();
                            List<Persona> personas = null;

                            switch (opcionListar) {
                                case 1:
                                    // Listar personas
                                    personas = gm.getPersonas();
                                    break;
                                case 2:
                                    // Listar trabajadores
                                    // stream cast a trabajadores
                                    personas = gm.getTrabajadores();
                                    break;
                                case 3:
                                    // Listar usuarios premium
                                    personas = gm.getUsuariosPremium();
                                    break;
                                case 4:
                                    // Listar usuarios no premium
                                    personas = gm.getUsuariosNoPremium();
                                    break;
                                default:
                                    System.out.println("Opción no válida. Intente nuevamente.");
                            }

                            // Mostrar la lista de personas
                            utilsUsuarios.listarPersonas(personas);

                            break;
                        default:
                            System.out.println("Opción no válida. Intente nuevamente.");
                    }
                    break;
                case 2:
                    // Gestión de vehículos
                    break;
                case 3:
                    // Gestión de viajes
                    break;
                case 4:
                    // Gestión de incidencias
                    break;
                case 5:
                    // Gestión de bases
                    break;
                case 6:
                    // Gestión de estadísticas
                    break;
                case 7:
                    // Gestión de informes
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }


}
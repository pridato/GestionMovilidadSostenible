package es.uned.model.personas;

/**
 * Clase que representa una persona con atributos como nombre, apellidos, DNI, email y teléfono.
 */
public class Persona {

    private String nombre;
    private String apellidos;
    private String DNI;
    private String email;
    private int telefono;

    public Persona(String nombre, String apellidos, String DNI, String email, int telefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.DNI = DNI;
        this.email = email;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", DNI='" + DNI + '\'' +
                ", email='" + email + '\'' +
                ", telefono=" + telefono +
                '}';
    }
}

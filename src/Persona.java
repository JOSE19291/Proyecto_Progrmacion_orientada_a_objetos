public abstract class Persona {
    private String id;
    private String nombre;

    public Persona() {

    }

    public Persona(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getID() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}

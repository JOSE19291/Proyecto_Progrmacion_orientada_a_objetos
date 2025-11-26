public class Tecnico extends Persona {
    private boolean disponible;

    public Tecnico(String id, String nombre) {
        super(id, nombre);
        this.disponible = true;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}

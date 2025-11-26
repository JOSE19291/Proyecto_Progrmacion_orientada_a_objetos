import java.util.Date;
import java.util.UUID;

public class Incidencia {
    private String id;
    private String descripcion;
    private String equipoAfectado;
    private TipoFalla tipo;
    private Prioridad prioridad;
    private Estado estado;

    private Usuario reportadoPor;
    private Tecnico asignadoA;

    private Date fechaReporte;
    private Date fechaResolucion;

    public Incidencia(String descripcion, TipoFalla tipo, String equipoAfectado, Usuario reportadoPor) {
        this.id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.descripcion = descripcion;
        this.tipo = tipo; // Clasificar incidencias por tipo
        this.equipoAfectado = equipoAfectado;
        this.reportadoPor = reportadoPor;
        this.fechaReporte = new Date();

        this.estado = Estado.Pendiente;
        this.prioridad = Prioridad.Media;
    }

    public void setPrioridad(Prioridad nuevaPrioridad) {
        this.prioridad = nuevaPrioridad;
    }

    // Asignar técnicos responsables
    public void asignarTecnico(Tecnico tecnico) {
        this.asignadoA = tecnico;
    }

    // Gestionar el ciclo de vida completo
    public void actualizarEstado(Estado nuevoEstado) {
        this.estado = nuevoEstado;
        if (nuevoEstado == Estado.Resuelta) {
            this.fechaResolucion = new Date();
        }
    }

    public String getId() {
        return id;
    }

    public Estado getEstado() {
        return estado;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public String getEquipoAfectado() {
        return equipoAfectado;
    }

    public Usuario getReportadoPor() {
        return reportadoPor;
    }

    public String getReportadoPorID() {
        return reportadoPor.getID();
    }

    @Override
    public String toString() {
        String tecnicoNombre = (asignadoA != null) ? asignadoA.getNombre() : "Sin asignar";
        String resolucion = (fechaResolucion != null) ? fechaResolucion.toString() : "N/A";

        return String.format(
                "ID: %s | Equipo: %s | Tipo: %s | Prioridad: %s\n" +
                        "Estado: %s | Reportado por: %s | Asignado a: %s\n" +
                        "Descripción: %s\n" +
                        "Reporte: %s | Resuelto: %s",
                id, equipoAfectado, tipo, prioridad,
                estado, reportadoPor.getNombre(), tecnicoNombre,
                descripcion, fechaReporte, resolucion);
    }
}
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SistemaTicketing {

    private List<Incidencia> listaIncidencias;
    private List<Tecnico> listaTecnicos;
    private List<Usuario> listaUsuarios;

    public SistemaTicketing() {
        this.listaIncidencias = new ArrayList<>();
        this.listaTecnicos = new ArrayList<>();
        this.listaUsuarios = new ArrayList<>();
        inicializarDatos();
    }

    // Inicialización de datos de prueba
    private void inicializarDatos() {
        // Tecnicos
        listaTecnicos.add(new Tecnico("T001", "Pedro Perez"));
        listaTecnicos.add(new Tecnico("T002", "Ana Lopez"));
        // Usuarios
        listaUsuarios.add(new Usuario("U001", "Estudiante Juan"));
        listaUsuarios.add(new Usuario("U002", "Profesor García"));
    }

    // Métodos utilitarios de busqueda
    public Incidencia buscarIncidencia(String idInc) { // Devuelve incidencia
        return listaIncidencias.stream()
                .filter(i -> i.getId().equals(idInc))
                .findFirst()
                .orElse(null);
    }

    public Tecnico buscarTecnico(String idTec) { // Devuelve tecnico
        return listaTecnicos.stream()
                .filter(t -> t.getID().equals(idTec))
                .findFirst()
                .orElse(null);
    }

    // 1. Registrar nuevas incidencias
    public void registrarIncidencia(String desc, TipoFalla tipo, String equipo, Usuario reporta) {
        Incidencia nuevaIncidencia = new Incidencia(desc, tipo, equipo, reporta);
        this.listaIncidencias.add(nuevaIncidencia);
        System.out.printf("\n Incidencia %s registrada, prioridad: %s, estado: %s.\n",
                nuevaIncidencia.getId(), nuevaIncidencia.getPrioridad(), nuevaIncidencia.getEstado());
    }

    // 2. Asignar prioridades según la criticidad
    public void cambiarPrioridad(String idInc, Prioridad nuevaP) { // Recibe String, Prioridad
        Incidencia inc = buscarIncidencia(idInc);
        if (inc != null) {
            inc.setPrioridad(nuevaP);
            System.out.printf(" Incidencia %s: prioridad cambiada a %s.\n", idInc, nuevaP);
        } else {
            System.out.println(" Incidencia no encontrada.");
        }
    }

    // 3. Asignar técnicos responsables
    public void asignarTecnico(String idInc, String idTec) {
        Incidencia inc = buscarIncidencia(idInc);
        Tecnico tec = buscarTecnico(idTec);

        if (inc != null && tec != null) {
            inc.asignarTecnico(tec);
            System.out.printf(" Incidencia %s asignada a %s.\n", idInc, tec.getNombre());
        } else {
            System.out.println(" ID de incidencia o tecnico no encontrado.");
        }
    }

    // 4. Gestionar el ciclo de vida
    public void actualizarEstadoIncidencia(String idInc, Estado nuevoE) {
        Incidencia inc = buscarIncidencia(idInc);

        if (inc != null) {
            inc.actualizarEstado(nuevoE);
            System.out.printf(" Estado de incidencia %s actualizado a %s. %s\n",
                    idInc, nuevoE, (nuevoE == Estado.Resuelta ? "(Fecha de resolución registrada)" : ""));
        } else {
            System.out.println(" Incidencia no encontrada.");
        }
    }

    // 5. Consultar y filtrar incidencias por diversos criterios
    public List<Incidencia> filtrarIncidencias(Prioridad prioridad, Estado estado) {
        System.out.printf("\n--- Filtrando: Estado %s y Prioridad %s ---\n", estado, prioridad);
        return listaIncidencias.stream()
                .filter(i -> i.getPrioridad() == prioridad && i.getEstado() == estado)
                .collect(Collectors.toList());
    }

    // Consultar por equipo
    public List<Incidencia> consultarPorEquipo(String equipo) {
        System.out.printf("\n--- Filtrando: equipo %s ---\n", equipo);
        return listaIncidencias.stream()
                .filter(i -> i.getEquipoAfectado().equalsIgnoreCase(equipo))
                .collect(Collectors.toList());
    }

    // Consultar por usuario
    public List<Incidencia> consultarPorUsuario(String idUsuario) {
        System.out.printf("\n--- Filtrando: Usuario %s ---\n", idUsuario);
        return listaIncidencias.stream()
                .filter(i -> i.getReportadoPorID().equals(idUsuario))
                .collect(Collectors.toList());
    }

    // 6. Generar reportes básicos
    public Map<Estado, Long> generarReportePorEstado() {
        // Agrupa y cuenta las incidencias por su estado
        return listaIncidencias.stream()
                .collect(Collectors.groupingBy(Incidencia::getEstado, Collectors.counting()));
    }

    // Método para mostrar lista
    public void mostrarIncidencias(List<Incidencia> lista) {
        if (lista.isEmpty()) {
            System.out.println("No se encontraron incidencias que coincidan con el filtro.");
            return;
        }
        for (Incidencia inc : lista) {
            System.out.println("-------------------------");
            System.out.println(inc);
        }
        System.out.println("-------------------------");
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public List<Tecnico> getListaTecnicos() {
        return listaTecnicos;
    }

    public List<Incidencia> getListaIncidencias() {
        return listaIncidencias;
    }
}
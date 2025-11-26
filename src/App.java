import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        SistemaTicketing sistema = new SistemaTicketing();

        Usuario uJuan = sistema.getListaUsuarios().get(0); // U001
        Usuario uProf = sistema.getListaUsuarios().get(1); // U002
        Tecnico tPedro = sistema.getListaTecnicos().get(0); // T001

        System.out.println("--- Inicio de pruebas del sistema de ticketing LAB-LIS ---");

        // 1. Registrar nuevas incidencias (Historia de usuario)
        System.out.println("\n 1. Registro de incidencias");
        sistema.registrarIncidencia("El monitor no enciende.", TipoFalla.Hardware, "LAB-PC15", uJuan);
        sistema.registrarIncidencia("Error al iniciar sesión.", TipoFalla.Software, "LAB-PC05", uJuan);
        sistema.registrarIncidencia("No hay conexión de red.", TipoFalla.Red, "LAB-PC02", uProf);

        String idIncidenciaHardware = sistema.getListaIncidencias().get(0).getId();
        String idIncidenciaSoftware = sistema.getListaIncidencias().get(1).getId();

        // 2. Clasificar prioridad (Historia de usuario)
        System.out.println("\n 2. Clasificacion y asignacion de prioridad");
        sistema.cambiarPrioridad(idIncidenciaHardware, Prioridad.Alta);
        sistema.cambiarPrioridad(idIncidenciaSoftware, Prioridad.Baja);

        // 3. Asignar responsable (Historia de usuario)
        System.out.println("\n 3. Asignacion de tecnico");
        sistema.asignarTecnico(idIncidenciaHardware, tPedro.getID());

        // 4. Consultar y filtrar incidencias (Filtro por Prioridad/Estado)
        System.out.println("\n 4. Consulta y filtrado de incidencias");

        // Filtro por Estado y Prioridad
        List<Incidencia> filtradasPrioridad = sistema.filtrarIncidencias(Prioridad.Alta, Estado.Pendiente);
        sistema.mostrarIncidencias(filtradasPrioridad);

        // Filtro por Usuario
        List<Incidencia> reportadasPorJuan = sistema.consultarPorUsuario(uJuan.getID());
        sistema.mostrarIncidencias(reportadasPorJuan);

        // Filtro por Equipo
        List<Incidencia> incidenciasPC05 = sistema.consultarPorEquipo("LAB-PC05");
        sistema.mostrarIncidencias(incidenciasPC05);

        // 5. Iniciar y cerrar tarea (gestión de ciclo de vida)
        System.out.println("\n 5. Gestion del ciclo de vida");

        sistema.actualizarEstadoIncidencia(idIncidenciaHardware, Estado.En_progreso);
        sistema.actualizarEstadoIncidencia(idIncidenciaHardware, Estado.Resuelta);

        // 6. Generar reportes
        System.out.println("\n 6. Generacion de reportes");
        Map<Estado, Long> reporte = sistema.generarReportePorEstado();

        System.out.println("--- Reporte por estado ---");
        for (Map.Entry<Estado, Long> entry : reporte.entrySet()) {
            System.out.printf("%s: %d\n", entry.getKey(), entry.getValue());
        }
        System.out.println("---------------------------------");
    }
}

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReporteManager {

    private EventoManager eventoManager;

    public ReporteManager(EventoManager eventoManager) {
        this.eventoManager = eventoManager;
    }

    public void generarReporte() {
        List<Evento> eventos = eventoManager.consultarEventos();

        // Crear nombre de archivo con timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filePath = "files/reporte_eventos_" + timestamp + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            if (eventos.isEmpty()) {
                writer.write("⚠️ No hay eventos registrados.");
                System.out.println("✅ Reporte generado: " + filePath);
                return;
            }

            int totalEventos = eventos.size();
            int sumaCuposDisponibles = 0;
            StringBuilder eventosAgotados = new StringBuilder();

            for (Evento e : eventos) {
                sumaCuposDisponibles += e.getCuposDisponibles();
                if (e.getCuposDisponibles() == 0) {
                    eventosAgotados.append("- ").append(e.getNombre()).append("\n");
                }
            }

            // Escribir reporte en archivo
            writer.write("===== REPORTE DE EVENTOS =====\n");
            writer.write("Total de eventos registrados: " + totalEventos + "\n");
            writer.write("Suma de cupos disponibles: " + sumaCuposDisponibles + "\n");

            if (eventosAgotados.length() > 0) {
                writer.write("Eventos agotados:\n");
                writer.write(eventosAgotados.toString());
            } else {
                writer.write("No hay eventos agotados.\n");
            }

            writer.write("==============================\n");

            System.out.println("✅ Reporte generado: " + filePath);

        } catch (IOException e) {
            System.out.println("❌ Error al generar el reporte: " + e.getMessage());
        }
    }
}


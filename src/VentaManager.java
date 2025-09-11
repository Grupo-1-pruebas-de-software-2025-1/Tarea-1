import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VentaManager {

    private static final String FILE_PATH_VENTAS = "files/ventas.txt";
    private static final Logger logger = LogManager.getLogger(VentaManager.class);

    private EventoManager eventoManager;

    public VentaManager(EventoManager eventoManager) {
        this.eventoManager = eventoManager;
    }

    // ------------------ Registrar Venta ------------------
    public boolean registrarVenta(String nombreEvento, int cantidad) {
        var eventos = eventoManager.consultarEventos();

        for (Evento e : eventos) {
            if (e.getNombre().equalsIgnoreCase(nombreEvento)) {
                if (e.getCuposDisponibles() >= cantidad) {
                    e.setCuposDisponibles(e.getCuposDisponibles() - cantidad);
                    eventoManager.editarEvento(nombreEvento, "cuposDisponibles", String.valueOf(e.getCuposDisponibles()));

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_VENTAS, true))) {
                        writer.write("Evento: " + e.getNombre() +
                                     " | Cantidad: " + cantidad +
                                     " | Fecha: " + LocalDateTime.now());
                        writer.newLine();
                    } catch (IOException ex) {
                        logger.error("Error al registrar la venta en archivo: {}", ex.getMessage());
                    }

                    logger.info("Venta registrada - Evento: {}, Cantidad: {}, Fecha: {}",
                                e.getNombre(), cantidad, LocalDateTime.now());
                    return true;
                } else {
                    logger.warn("Venta rechazada - Evento: {}, Cantidad solicitada: {}, Cupos disponibles: {}",
                                e.getNombre(), cantidad, e.getCuposDisponibles());
                    System.out.println("🔴 No hay cupos suficientes.");
                    return false;
                }
            }
        }

        logger.warn("Venta rechazada - Evento no encontrado: {}, Cantidad solicitada: {}",
                    nombreEvento, cantidad);
        System.out.println("🔴 Evento no encontrado.");
        return false;
    }

    public boolean registrarDevolucion(String nombreEvento, int cantidad) {
    var eventos = eventoManager.consultarEventos();

    for (Evento e : eventos) {
        if (e.getNombre().equalsIgnoreCase(nombreEvento)) {
            int cuposMax = e.getCuposMaximos();
            int cuposDispo = e.getCuposDisponibles();
            int vendidas = cuposMax - cuposDispo;

            if (cantidad <= vendidas) {
                e.setCuposDisponibles(e.getCuposDisponibles() + cantidad);
                eventoManager.editarEvento(
                    nombreEvento,
                    "cuposDisponibles",
                    String.valueOf(e.getCuposDisponibles())
                );

                return true;
            } else {
                System.out.println("❌ La devolución supera la cantidad de entradas vendidas.");
                return false;
            }
        }
    }
    System.out.println("❌ Evento no encontrado.");
    return false;
}



}
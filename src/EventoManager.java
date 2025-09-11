import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventoManager {

    private static final Logger logger = LogManager.getLogger(EventoManager.class);
    private static final String FILE_PATH = "files/eventos.txt";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void crearEvento(Evento evento) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String linea = evento.getNombre() + "|" +
                           evento.getDescripcion() + "|" +
                           evento.getFecha().format(DATE_FORMAT) + "|" +
                           evento.getCategoria() + "|" +
                           evento.getPrecioEntrada() + "|" +
                           evento.getCuposDisponibles() + "|" +
                           evento.getCuposMaximos(); 

            writer.write(linea);
            writer.newLine();
            logger.info("Evento creado: {}", evento.getNombre());
        } catch (IOException e) {
            logger.error("Error al guardar el evento '{}': {}", evento.getNombre(), e.getMessage());
            System.out.println("Error al guardar el evento: " + e.getMessage());
        }
    }
    
    public List<Evento> consultarEventos() {
        List<Evento> eventos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Separar los campos por "|"
                String[] campos = linea.split("\\|");

                if (campos.length == 7) { // aseguramos que tenga todos los atributos
                    String nombre = campos[0];
                    String descripcion = campos[1];
                    LocalDate fecha = LocalDate.parse(campos[2]);
                    String categoria = campos[3];
                    int precioEntrada = Integer.parseInt(campos[4]);
                    int cuposDisponibles = Integer.parseInt(campos[5]);
                    int cuposMaximos = Integer.parseInt(campos[6]);

                    Evento evento = new Evento(nombre, descripcion, fecha, categoria, precioEntrada, cuposDisponibles);
                    evento.setCuposMaximos(cuposMaximos);
                    eventos.add(evento);
                }
            }
            logger.info("Consulta de eventos realizada. Total: {}", eventos.size());
        } catch (IOException e) {
            logger.error("Error al leer los eventos: {}", e.getMessage());
            System.out.println("Error al leer los eventos: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error al procesar un evento: {}", e.getMessage());
            System.out.println("Error al procesar un evento: " + e.getMessage());
        }

        return eventos;
    }

    public boolean editarEvento(String nombreEvento, String campo, String nuevoValor) {
        List<Evento> eventos = consultarEventos();
        boolean encontrado = false;

        for (Evento e : eventos) {
            if (e.getNombre().equalsIgnoreCase(nombreEvento)) {
                encontrado = true;
                try {
                    switch (campo) {
                        case "nombre" -> e.setNombre(nuevoValor);
                        case "descripcion" -> e.setDescripcion(nuevoValor);
                        case "fecha" -> e.setFecha(LocalDate.parse(nuevoValor));
                        case "categoria" -> e.setCategoria(nuevoValor);
                        case "precioEntrada" -> e.setPrecioEntrada(Integer.parseInt(nuevoValor));
                        case "cuposDisponibles" -> e.setCuposDisponibles(Integer.parseInt(nuevoValor));
                        case "cuposMaximos" -> {
                            int nuevoMax = Integer.parseInt(nuevoValor);
                            if (nuevoMax < e.getCuposDisponibles()) {
                                System.out.println("No se puede establecer cupos máximos menores a los cupos disponibles actuales.");
                                return false;
                            }
                            e.setCuposMaximos(nuevoMax);
                        }
                    }
                    logger.info("Evento '{}' editado. Campo '{}' actualizado.", nombreEvento, campo);
                } catch (Exception ex) {
                    logger.error("Valor inválido para el campo '{}' en evento '{}': {}", campo, nombreEvento, ex.getMessage());
                    System.out.println("Valor inválido para el campo " + campo + ": " + ex.getMessage());
                    return false;
                }
                break;
            }
        }

        if (encontrado) {
            // Reescribir el archivo con la lista actualizada
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Evento e : eventos) {
                    String linea = e.getNombre() + "|" +
                                e.getDescripcion() + "|" +
                                e.getFecha().format(DATE_FORMAT) + "|" +
                                e.getCategoria() + "|" +
                                e.getPrecioEntrada() + "|" +
                                e.getCuposDisponibles() + "|" +
                                e.getCuposMaximos();
                    writer.write(linea);
                    writer.newLine();
                }
                logger.info("Archivo de eventos actualizado tras edición.");
            } catch (IOException e) {
                logger.error("Error al actualizar el archivo de eventos: {}", e.getMessage());
                System.out.println("Error al actualizar el archivo: " + e.getMessage());
                return false;
            }
        } else {
            logger.warn("Intento de editar evento no encontrado: '{}'", nombreEvento);
        }

        return encontrado;
    }

    public boolean eliminarEvento(String nombreEvento) {
        List<Evento> eventos = consultarEventos();
        boolean encontrado = false;

        // Filtrar eventos que no coincidan con el nombre
        List<Evento> eventosFiltrados = new ArrayList<>();
        for (Evento e : eventos) {
            if (e.getNombre().equalsIgnoreCase(nombreEvento)) {
                encontrado = true;
                // Evento eliminado, no lo agregamos a la lista filtrada
            } else {
                eventosFiltrados.add(e);
            }
        }

        if (encontrado) {
            // Reescribir el archivo con los eventos restantes
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Evento e : eventosFiltrados) {
                    String linea = e.getNombre() + "|" +
                                e.getDescripcion() + "|" +
                                e.getFecha().format(DATE_FORMAT) + "|" +
                                e.getCategoria() + "|" +
                                e.getPrecioEntrada() + "|" +
                                e.getCuposDisponibles() + "|" +
                                e.getCuposMaximos();
                    writer.write(linea);
                    writer.newLine();
                }
                logger.info("Evento eliminado: '{}'. Archivo de eventos actualizado.", nombreEvento);
            } catch (IOException e) {
                logger.error("Error al actualizar el archivo tras eliminar evento '{}': {}", nombreEvento, e.getMessage());
                System.out.println("Error al actualizar el archivo: " + e.getMessage());
                return false;
            }
        } else {
            logger.warn("Intento de eliminar evento no encontrado: '{}'", nombreEvento);
        }

        return encontrado;
    }

    public Evento buscarEvento(String nombreEvento) {
    List<Evento> eventos = consultarEventos(); 
    for (Evento e : eventos) {
        if (e.getNombre().equalsIgnoreCase(nombreEvento)) {
            return e;
        }
    }
    logger.info("Evento no encontrado en búsqueda: '{}'", nombreEvento);
    return null;
}
}


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Menu {
    private static final Logger logger = LogManager.getLogger(Menu.class);
    private EventoManager eventoManager;
    private VentaManager ventaManager;

    public Menu() {
        this.eventoManager = new EventoManager();
        this.ventaManager = new VentaManager(eventoManager);
    }

    public void iniciar() {

        // TODO: Manejo de autenticacion de usuario

        System.out.println("----------------------------\n");
        System.out.println("Bienvenido(a)\n");
        System.out.println("----------------------------\n");

        boolean salir = false;
        Scanner scanner = new Scanner(System.in);


        while (!salir) {
            mostrarOpciones();
            int opcion = -1;
            try {
                opcion = scanner.nextInt();
            } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor ingrese un número.");
            scanner.nextLine();
            continue;
    }

            // TODO: Implementar manejo de las distintas opciones
            switch (opcion) {
                case 1 -> manejarEventos(scanner);
                case 3 -> manejarVenta(scanner);
                case 0 -> {
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                    salir = true;
                    System.exit(0);
                }
                default -> {
                    System.out.println("Opción no válida");
                    salir = true;
                }
            }
        }
        scanner.close();
    }

    private void manejarEventos(Scanner scanner) {
        boolean volver = false;

        while (!volver) {
            mostrarOpcionesEventos();
            int opcionEvento = -1;
            try {
                opcionEvento = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor ingrese un número.");
                scanner.nextLine();
                continue;
            }

            switch (opcionEvento) {
                case 1 -> manejarCrearEvento(scanner);
                case 2 -> manejarMostrarEventos(scanner);
                case 3 -> manejarEditarEvento(scanner);
                case 4 -> manejarEliminarEvento(scanner);
                case 0 -> volver = true; // Regresa al menú principal
                default -> System.out.println("Opción no válida, intente de nuevo.");
            }
        }
    }

    private void manejarVenta(Scanner scanner) {
        scanner.nextLine(); // limpiar buffer
        
        String nombreEvento;
        Evento evento = null;
        do {
            System.out.print("Ingrese el nombre del evento: ");
            nombreEvento = scanner.nextLine().trim();

            if (nombreEvento.isEmpty()) {
                System.out.println("⚠️ El nombre no puede estar vacío. Intente nuevamente.");
                continue;
            }

            evento = eventoManager.buscarEvento(nombreEvento);
            if (evento == null) {
                System.out.println("❌ El evento \"" + nombreEvento + "\" no existe. Intente nuevamente.");
            }
        } while (evento == null);

        int cantidad = -1;
        while (cantidad <= 0) {
            System.out.print("Ingrese la cantidad de entradas: ");
            if (scanner.hasNextInt()) {
                cantidad = scanner.nextInt();
                if (cantidad <= 0) {
                    System.out.println("⚠️ La cantidad debe ser mayor a 0.");
                }
            } else {
                System.out.println("❌ Debe ingresar un número válido.");
                scanner.nextLine(); // limpiar entrada inválida
            }
        }

        boolean exito = ventaManager.registrarVenta(nombreEvento, cantidad);

        if (exito) {
            System.out.println("🟢 Venta registrada con éxito.");
        } else {
            System.out.println("🔴 Venta no realizada.");
        }
    }
    


    private void manejarCrearEvento(Scanner scanner) {
        try {
            scanner.nextLine(); // limpiar buffer antes de leer inputs

            //Leer campos para el evento

            System.out.print("Ingrese el nombre del evento: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese la descripción: ");
            String descripcion = scanner.nextLine();

            // Validar fecha
            LocalDate fecha = null;
            while (fecha == null) {
                System.out.print("Ingrese la fecha (YYYY-MM-DD): ");
                String fechaStr = scanner.nextLine();
                try {
                    fecha = LocalDate.parse(fechaStr);
                } catch (Exception e) {
                    System.out.println("Formato de fecha inválido. Intente de nuevo.");
                }
            }

            // Validar categoría
            String categoria = "";
            while (true) {
                System.out.print("Ingrese la categoría (Charla, Taller, Show): ");
                categoria = scanner.nextLine();
                if (categoria.equalsIgnoreCase("Charla") ||
                    categoria.equalsIgnoreCase("Taller") ||
                    categoria.equalsIgnoreCase("Show")) {
                    // Normalizar la categoría
                    categoria = categoria.substring(0, 1).toUpperCase() + categoria.substring(1).toLowerCase();
                    break;
                } else {
                    System.out.println("Categoría inválida. Debe ser 'Charla', 'Taller' o 'Show'.");
                }
            }

            // Validar precio de entrada
            int precioEntrada = -1;
            while (precioEntrada <= 0) {
                System.out.print("Ingrese el precio de la entrada (> 0): ");
                try {
                    precioEntrada = scanner.nextInt();
                    if (precioEntrada <= 0) {
                        System.out.println("El precio debe ser mayor a 0.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Debe ser un número entero.");
                scanner.nextLine();
                }
            }

            // Validar cupos disponibles
            int cuposDisponibles = -1;
            while (cuposDisponibles <= 0) {
                System.out.print("Ingrese la cantidad de cupos disponibles (> 0): ");
                try {
                    cuposDisponibles = scanner.nextInt();
                    if (cuposDisponibles <= 0) {
                        System.out.println("Los cupos deben ser mayores a 0.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Debe ser un número entero.");
                    scanner.nextLine();
                }
            }
            scanner.nextLine();
            // Crear el evento
            Evento evento = new Evento(nombre, descripcion, fecha, categoria, precioEntrada, cuposDisponibles);

            // Guardar el evento
            eventoManager.crearEvento(evento);

            System.out.println("Evento creado con éxito.");

        } catch (Exception e) {
            System.out.println("Error al crear el evento: " + e.getMessage());
            scanner.nextLine(); // limpiar buffer si hubo error
        }
    }

    private void manejarMostrarEventos(Scanner scanner) {
        boolean volver = false;
        while (!volver) {
            // Menú de acciones
            System.out.println("\n--- Menú de eventos ---");
            System.out.println("1. Ver todos los eventos");
            System.out.println("2. Filtrar eventos");
            System.out.println("3. Buscar evento por nombre");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> {
                    List<Evento> eventos = eventoManager.consultarEventos();
                    if (eventos.isEmpty()) {
                        System.out.println("No hay eventos registrados.");
                        logger.info("No hay eventos registrados al mostrar todos los eventos.");
                    } else {
                        imprimirTablaEventos(eventos);
                        logger.info("Se mostraron {} eventos en formato tabla.", eventos.size());
                    }
                }
                case "2" -> {
                    logger.info("Usuario seleccionó filtrar eventos.");
                    manejarFiltrarEventos(scanner);
                }
                case "3" -> {
                    logger.info("Usuario seleccionó buscar evento por nombre.");
                    manejarBuscarEventoPorNombre(scanner);
                }
                case "0" -> {
                    logger.info("Usuario volvió al menú anterior desde mostrar eventos.");
                    volver = true;
                }
                default -> {
                    System.out.println("Opción no válida. Intente de nuevo.");
                    logger.warn("Opción inválida en mostrar eventos: {}", opcion);
                }
            }
        }
    }
    

    private void manejarEditarEvento(Scanner scanner) {
        try {
            System.out.print("Ingrese el nombre del evento a editar: ");
            scanner.nextLine(); // limpiar buffer
            String nombreEvento = scanner.nextLine();

            System.out.println("Seleccione el campo a editar:");
            System.out.println("1. Nombre");
            System.out.println("2. Descripcion");
            System.out.println("3. Fecha");
            System.out.println("4. Categoria");
            System.out.println("5. Precio de entrada");
            System.out.println("6. Cupos disponibles");

            int opcion = -1;
            while (opcion < 1 || opcion > 6) {
                try {
                    opcion = scanner.nextInt();
                    scanner.nextLine(); // limpiar buffer
                    if (opcion < 1 || opcion > 6) System.out.println("Opción no válida. Intente de nuevo.");
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor ingrese un número.");
                    scanner.nextLine();
                }
            }

            String nuevoValor = "";
            boolean valorValido = false;
            while (!valorValido) {
                switch (opcion) {
                    case 1 -> {
                        System.out.print("Ingrese el nuevo nombre: ");
                        nuevoValor = scanner.nextLine();
                        valorValido = !nuevoValor.trim().isEmpty();
                        if (!valorValido) System.out.println("El nombre no puede estar vacío.");
                    }
                    case 2 -> {
                        System.out.print("Ingrese la nueva descripción: ");
                        nuevoValor = scanner.nextLine();
                        valorValido = !nuevoValor.trim().isEmpty();
                        if (!valorValido) System.out.println("La descripción no puede estar vacía.");
                    }
                    case 3 -> {
                        System.out.print("Ingrese la nueva fecha (YYYY-MM-DD): ");
                        nuevoValor = scanner.nextLine();
                        try {
                            LocalDate.parse(nuevoValor);
                            valorValido = true;
                        } catch (Exception e) {
                            System.out.println("Formato de fecha inválido.");
                        }
                    }
                    case 4 -> {
                        System.out.print("Ingrese la nueva categoría (Charla, Taller, Show): ");
                        nuevoValor = scanner.nextLine();
                        if (nuevoValor.equalsIgnoreCase("Charla") || 
                            nuevoValor.equalsIgnoreCase("Taller") || 
                            nuevoValor.equalsIgnoreCase("Show")) {
                            nuevoValor = nuevoValor.substring(0, 1).toUpperCase() + nuevoValor.substring(1).toLowerCase();
                            valorValido = true;
                        } else {
                            System.out.println("Categoría inválida. Debe ser 'Charla', 'Taller' o 'Show'.");
                        }
                    }
                    case 5 -> {
                        System.out.print("Ingrese el nuevo precio de entrada (>0): ");
                        try {
                            int precio = Integer.parseInt(scanner.nextLine());
                            if (precio > 0) {
                                nuevoValor = String.valueOf(precio);
                                valorValido = true;
                            } else {
                                System.out.println("El precio debe ser mayor a 0.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida. Debe ser un número entero.");
                        }
                    }
                    case 6 -> {
                        System.out.print("Ingrese la nueva cantidad de cupos disponibles (>0): ");
                        try {
                            int cupos = Integer.parseInt(scanner.nextLine());
                            if (cupos > 0) {
                                nuevoValor = String.valueOf(cupos);
                                valorValido = true;
                            } else {
                                System.out.println("Los cupos deben ser mayores a 0.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida. Debe ser un número entero.");
                        }
                    }
                }
            }

            boolean exito = eventoManager.editarEvento(nombreEvento, switch (opcion) {
                case 1 -> "nombre";
                case 2 -> "descripcion";
                case 3 -> "fecha";
                case 4 -> "categoria";
                case 5 -> "precioEntrada";
                case 6 -> "cuposDisponibles";
                default -> "";
            }, nuevoValor);

            System.out.println(exito ? "Evento editado con éxito." : "No se encontró el evento o no se pudo editar.");

        } catch (Exception e) {
            System.out.println("Error al editar el evento: " + e.getMessage());
            scanner.nextLine(); // limpiar buffer en caso de error
        }
    }

    private void manejarEliminarEvento(Scanner scanner) {
        try {
            System.out.print("Ingrese el nombre del evento a eliminar: ");
            scanner.nextLine(); // limpiar buffer
            String nombreEvento = scanner.nextLine();

            boolean exito = eventoManager.eliminarEvento(nombreEvento);

            if (exito) {
                System.out.println("Evento eliminado con éxito.");
            } else {
                System.out.println("No se encontró el evento con ese nombre.");
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar el evento: " + e.getMessage());
            scanner.nextLine(); // limpiar buffer en caso de error
        }
    }

    private void mostrarOpciones() {
        System.out.println("Seleccione la opción que desea realizar");
        System.out.println("1. Administrar eventos");
        System.out.println("2. Generar reportes");
        System.out.println("3. Añadir venta");
        System.out.println("4. Eliminar venta (Devolucion)");
        System.out.println("0. Salir del programa");   
    }

    private void mostrarOpcionesEventos() {
        System.out.println("Seleccione la opción que desea realizar");
        System.out.println("1. Registrar evento");
        System.out.println("2. Ver listado de eventos");
        System.out.println("3. Editar evento");
        System.out.println("4. Eliminar evento");
        System.out.println("0. Volver al menú principal");

    }

    private void manejarFiltrarEventos(Scanner scanner) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Filtrar eventos ---");
            System.out.println("1. Por categoría");
            System.out.println("2. Por fecha");
            System.out.println("3. Por precio");
            System.out.println("4. Por cupos");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> {
                    // Filtrar por categoría
                    String categoria = "";
                    while (true) {
                        System.out.print("Ingrese la categoría (Charla, Taller, Show): ");
                        categoria = scanner.nextLine();
                        if (categoria.equalsIgnoreCase("Charla") ||
                            categoria.equalsIgnoreCase("Taller") ||
                            categoria.equalsIgnoreCase("Show")) {
                            break;
                        } else {
                            System.out.println("Categoría inválida. Debe ser 'Charla', 'Taller' o 'Show'.");
                            logger.warn("Categoría inválida ingresada en filtro: {}", categoria);
                        }
                    }
                    final String categoriaFiltro = categoria;
                    List<Evento> eventos = eventoManager.consultarEventos();
                    List<Evento> filtrados = eventos.stream()
                            .filter(e -> e.getCategoria().equalsIgnoreCase(categoriaFiltro))
                            .toList();
                    System.out.println("Eventos en la categoría " + categoria + ":");
                    if (filtrados.isEmpty()) {
                        System.out.println("No hay eventos para esa categoría.");
                        logger.info("No se encontraron eventos para la categoría '{}'.", categoria);
                    } else {
                        imprimirTablaEventos(filtrados);
                        logger.info("Se filtraron {} eventos por categoría '{}'.", filtrados.size(), categoria);
                    }
                }
                case "2" -> {
                    // Filtrar por fecha
                    LocalDate desde = null, hasta = null;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    while (desde == null) {
                        System.out.print("Ingrese la fecha de inicio (YYYY-MM-DD): ");
                        String desdeStr = scanner.nextLine();
                        try {
                            desde = LocalDate.parse(desdeStr, formatter);
                        } catch (Exception e) {
                            System.out.println("Formato de fecha inválido.");
                            logger.warn("Fecha de inicio inválida: {}", desdeStr);
                        }
                    }
                    while (hasta == null) {
                        System.out.print("Ingrese la fecha de fin (YYYY-MM-DD): ");
                        String hastaStr = scanner.nextLine();
                        try {
                            hasta = LocalDate.parse(hastaStr, formatter);
                            if (hasta.isBefore(desde)) {
                                System.out.println("La fecha de fin no puede ser anterior a la de inicio.");
                                logger.warn("Fecha de fin anterior a la de inicio: {} < {}", hasta, desde);
                                hasta = null;
                            }
                        } catch (Exception e) {
                            System.out.println("Formato de fecha inválido.");
                            logger.warn("Fecha de fin inválida: {}", hastaStr);
                        }
                    }
                    final LocalDate desdeFinal = desde;
                    final LocalDate hastaFinal = hasta;
                    List<Evento> eventos = eventoManager.consultarEventos();
                    List<Evento> filtrados = eventos.stream()
                            .filter(e -> !e.getFecha().isBefore(desdeFinal) && !e.getFecha().isAfter(hastaFinal))
                            .toList();
                    System.out.println("Eventos entre " + desde + " y " + hasta + ":");
                    if (filtrados.isEmpty()) {
                        System.out.println("No hay eventos en ese rango de fechas.");
                        logger.info("No se encontraron eventos entre {} y {}.", desde, hasta);
                    } else {
                        imprimirTablaEventos(filtrados);
                        logger.info("Se filtraron {} eventos por fecha entre {} y {}.", filtrados.size(), desde, hasta);
                    }
                }
                case "3" -> {
                    // Filtrar por precio
                    int precio = -1;
                    while (precio < 0) {
                        System.out.print("Ingrese el precio máximo: ");
                        String precioStr = scanner.nextLine();
                        try {
                            precio = Integer.parseInt(precioStr);
                            if (precio < 0) {
                                System.out.println("El precio debe ser mayor o igual a 0.");
                                logger.warn("Precio negativo ingresado: {}", precioStr);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida. Debe ser un número entero.");
                            logger.warn("Precio inválido ingresado: {}", precioStr);
                        }
                    }
                    final int precioFinal = precio;
                    List<Evento> eventos = eventoManager.consultarEventos();
                    List<Evento> filtrados = eventos.stream()
                            .filter(e -> e.getPrecioEntrada() <= precioFinal)
                            .toList();
                    System.out.println("Eventos con precio de entrada menor o igual a " + precio + ":");
                    if (filtrados.isEmpty()) {
                        System.out.println("No hay eventos con ese precio.");
                        logger.info("No se encontraron eventos con precio <= {}.", precio);
                    } else {
                        imprimirTablaEventos(filtrados);
                        logger.info("Se filtraron {} eventos por precio <= {}.", filtrados.size(), precio);
                    }
                }
                case "4" -> {
                    // Filtrar por cupos
                    int cupos = -1;
                    while (cupos < 0) {
                        System.out.print("Ingrese la cantidad mínima de cupos disponibles: ");
                        String cuposStr = scanner.nextLine();
                        try {
                            cupos = Integer.parseInt(cuposStr);
                            if (cupos < 0) {
                                System.out.println("La cantidad debe ser mayor o igual a 0.");
                                logger.warn("Cupos negativos ingresados: {}", cuposStr);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida. Debe ser un número entero.");
                            logger.warn("Cupos inválidos ingresados: {}", cuposStr);
                        }
                    }
                    final int cuposFinal = cupos;
                    List<Evento> eventos = eventoManager.consultarEventos();
                    List<Evento> filtrados = eventos.stream()
                            .filter(e -> e.getCuposDisponibles() >= cuposFinal)
                            .toList();
                    System.out.println("Eventos con al menos " + cupos + " cupos disponibles:");
                    if (filtrados.isEmpty()) {
                        System.out.println("No hay eventos con esa cantidad de cupos.");
                        logger.info("No se encontraron eventos con cupos >= {}.", cupos);
                    } else {
                        imprimirTablaEventos(filtrados);
                        logger.info("Se filtraron {} eventos por cupos >= {}.", filtrados.size(), cupos);
                    }
                }
                case "0" -> {
                    logger.info("Usuario volvió al menú anterior desde filtrar eventos.");
                    volver = true;
                }
                default -> {
                    System.out.println("Opción no válida. Intente de nuevo.");
                    logger.warn("Opción inválida en filtrar eventos: {}", opcion);
                }
            }
        }
    }

    private void manejarBuscarEventoPorNombre(Scanner scanner) {
        System.out.print("Ingrese el nombre o parte del nombre del evento: ");
        String nombre = scanner.nextLine();
        List<Evento> eventos = eventoManager.consultarEventos();
        List<Evento> encontrados = eventos.stream()
                .filter(e -> e.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .toList();
        if (encontrados.isEmpty()) {
            System.out.println("No se encontraron eventos con ese nombre.");
            logger.info("Búsqueda de evento por nombre '{}' no arrojó resultados.", nombre);
        } else {
            System.out.println("Resultados de búsqueda:");
            imprimirTablaEventos(encontrados);
            logger.info("Se encontraron {} eventos buscando por nombre '{}'.", encontrados.size(), nombre);
        }
    }

    private void imprimirTablaEventos(List<Evento> eventos) {
        System.out.printf("%-20s %-30s %-12s %-10s %-10s %-10s%n",
                "Nombre", "Descripción", "Fecha", "Categoría", "Precio", "Cupos");
        System.out.println("---------------------------------------------------------------------------------------------");
        for (Evento e : eventos) {
            String desc = e.getDescripcion();
            if (desc.length() > 28) desc = desc.substring(0, 27) + "...";
            System.out.printf("%-20s %-30s %-12s %-10s %-10d %-10d%n",
                    e.getNombre(), desc, e.getFecha(), e.getCategoria(),
                    e.getPrecioEntrada(), e.getCuposDisponibles());
        }
    }

    private void imprimirEvento(Evento e) {
        System.out.println("Nombre: " + e.getNombre() +
                ", Descripción: " + e.getDescripcion() +
                ", Fecha: " + e.getFecha() +
                ", Categoría: " + e.getCategoria() +
                ", Precio: " + e.getPrecioEntrada() +
                ", Cupos: " + e.getCuposDisponibles());
    }
    
}

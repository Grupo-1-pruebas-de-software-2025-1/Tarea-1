package src;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private EventoManager eventoManager;

    public Menu() {
        this.eventoManager = new EventoManager();
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
                case 2 -> manejarMostrarEventos();
                case 3 -> manejarEditarEvento(scanner);
                case 4 -> manejarEliminarEvento(scanner);
                case 0 -> volver = true; // Regresa al menú principal
                default -> System.out.println("Opción no válida, intente de nuevo.");
            }
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

    private void manejarMostrarEventos() {
        List<Evento> eventos = eventoManager.consultarEventos();
        if (eventos.isEmpty()) {
            System.out.println("No hay eventos registrados.");
        } else {
            // Encabezado de la tabla
            System.out.println("--------------------------------------------------------------------------------");
            System.out.printf("%-20s %-30s %-12s %-10s %-10s %-10s%n",
            "Nombre", "Descripcion", "Fecha", "Categoria", "Precio", "Cupos");
            System.out.println("--------------------------------------------------------------------------------");

            // Recorrer eventos
            for (Evento e : eventos) {
                String desc = e.getDescripcion();
                if (desc.length() > 30) {
                    desc = desc.substring(0, 27) + "...";
                }

                System.out.printf("%-20s %-30s %-12s %-10s %-10d %-10d%n",
                    e.getNombre(),
                    desc,
                    e.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    e.getCategoria(),
                    e.getPrecioEntrada(),
                    e.getCuposDisponibles()
                );
            }
            System.out.println("--------------------------------------------------------------------------------");
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
    }

    private void mostrarOpcionesEventos() {
        System.out.println("Seleccione la opción que desea realizar");
        System.out.println("1. Registrar evento");
        System.out.println("2. Ver listado de eventos");
        System.out.println("3. Editar evento");
        System.out.println("4. Eliminar evento");
        System.out.println("0. Volver al menú principal");

    }
    
}

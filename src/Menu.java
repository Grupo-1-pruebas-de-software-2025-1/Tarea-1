package src;

import java.util.Scanner;

public class Menu {

    public void iniciar() {

        // TODO: Manejo de autenticacion de usuario

        System.out.println("----------------------------\n");
        System.out.println("Bienvenido(a)\n");
        System.out.println("----------------------------\n");

        boolean salir = false;
        Scanner scanner = new Scanner(System.in);


        while (!salir) {
            mostrarOpciones();
            int opcion = scanner.nextInt();

            // TODO: Implementar manejo de las distintas opciones
            switch (opcion) {
                default -> {
                    System.out.println("Opción no válida");
                    salir = true;
                }
            }
        }
        scanner.close();
    }

    public void mostrarOpciones() {
        System.out.println("Seleccione la opción que desea realizar");
        System.out.println("1. Administrar eventos");
        System.out.println("2. Generar reportes");
        System.out.println("3. Añadir venta");
        System.out.println("4. Eliminar venta (Devolucion)");   
    }
    
}

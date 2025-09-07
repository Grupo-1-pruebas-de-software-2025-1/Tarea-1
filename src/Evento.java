package src;

import java.time.LocalDate;

public class Evento {
    
    private String nombre;
    private String descripcion;
    private LocalDate fecha;
    private String categoria;
    private int precioEntrada;
    private int cuposDisponibles;

    public Evento() {}

    public Evento(String nombre, String descripcion, LocalDate fecha, String categoria, int precioEntrada, int cuposDisponibles) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.categoria = categoria;
        this.precioEntrada = precioEntrada;
        this.cuposDisponibles = cuposDisponibles;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(int precioEntrada) {
        this.precioEntrada = precioEntrada;
    }

    public int getCuposDisponibles() {
        return cuposDisponibles;
    }

    public void setCuposDisponibles(int cuposDisponibles) {
        this.cuposDisponibles = cuposDisponibles;
    }
    
}
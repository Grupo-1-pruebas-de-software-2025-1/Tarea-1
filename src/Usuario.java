package src;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Usuario {
    private String nombre;
    private String clave;

    public Usuario(String nombre, char[] clave) {
        this.nombre = nombre;
        this.clave = generarHash(clave);
        // Limpieza de memoria de la clave original
        Arrays.fill(clave, ' ');
    }

    // Constructor alternativo para cargar desde archivo
    public Usuario(String nombre, String passwordHash, boolean hashed) {
        this.nombre = nombre;
        this.clave = passwordHash;
    }

    public String getNombre() {
        return nombre;
    }

    public String getClave() {
        return clave;
    }

    private String generarHash(char[] clave) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = new String(clave).getBytes(); // convertir char[] → byte[]
            byte[] hashBytes = md.digest(bytes);

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo de hash no disponible.", e);
        }
    }

    public boolean verificarClave(char[] clave) {
        String hashIntento = generarHash(clave);
        Arrays.fill(clave, ' ');
        return this.clave.equals(hashIntento);
    }
}
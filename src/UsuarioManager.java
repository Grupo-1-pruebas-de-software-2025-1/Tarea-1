package src;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UsuarioManager {
    private static final Logger logger = LogManager.getLogger(UsuarioManager.class);
    private static final String FILE_PATH = "files/usuarios.txt";
    private List<Usuario> usuarios;

    public UsuarioManager() {
        this.usuarios = new ArrayList<>();
        cargarUsuarios();
    }

    public boolean registrarUsuario(String nombre, char[] clave) {
        for (Usuario u : usuarios) {
            if (u.getNombre().equals(nombre)) {
                logger.warn("Intento de registro con nombre de usuario ya existente: {}", nombre);
                return false; // ya existe
            }
        }
        Usuario nuevo = new Usuario(nombre, clave);
        usuarios.add(nuevo);
        guardarUsuario(nuevo);
        logger.info("Usuario registrado exitosamente: {}", nombre);
        return true;
    }

    public Usuario autenticar(String nombre, char[] clave) {
        for (Usuario u : usuarios) {
            if (u.getNombre().equals(nombre) && u.verificarClave(clave)) {
                logger.info("Usuario autenticado correctamente: {}", nombre);
                return u;
            }
        }
        logger.warn("Fallo de autenticación para usuario: {}", nombre);
        return null;
    }

    private void cargarUsuarios() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            logger.info("Archivo de usuarios no existe, se creará al registrar el primer usuario.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 2) {
                    String nombre = partes[0];
                    String hash = partes[1];
                    usuarios.add(new Usuario(nombre, hash, true));
                }
            }
            logger.info("Usuarios cargados correctamente desde archivo.");
        } catch (IOException e) {
            logger.error("Error al cargar usuarios: {}", e.getMessage());
        }
    }

    private void guardarUsuario(Usuario usuario) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String linea = usuario.getNombre() + "|" + usuario.getClave();
            writer.write(linea);
            writer.newLine();
        } catch (IOException e) {
            logger.error("Error al guardar el usuario {}: {}", usuario.getNombre(), e.getMessage());
        }
    }
}
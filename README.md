# Tarea-1: Gestión de Micro-Eventos

Autores:  
- Bruno Flores Peñaloza  
- Francisca Figueroa Loyola  

---

## Descripción

Aplicación de consola en Java para la gestión de entradas de micro-eventos (charlas, talleres y shows pequeños) para una productora local. Permite crear, consultar, actualizar y eliminar eventos, registrar ventas y devoluciones de entradas, filtrar y buscar eventos, y generar reportes. El sistema está protegido por autenticación de usuario y contraseña, y almacena los datos en archivos de texto.

---

## Requisitos

- Java 21 (JDK)
- Log4j 2 (incluido en la carpeta `lib`)

---

## Guía de instalación y ejecución

### 1. Instalar Java 21

Descarga e instala Java 21 desde alguno de estos enlaces:

- [Oracle JDK 21](https://www.oracle.com/java/technologies/downloads/#jdk21-windows)
- [Adoptium Temurin JDK 21](https://adoptium.net/temurin/releases/?version=21)

Asegúrate de agregar Java al PATH del sistema.

### 2. Clonar el repositorio

```sh
git clone <URL_DEL_REPOSITORIO>
cd Tarea-1
```

### 3. Compilar el proyecto

```sh
javac -cp "lib\log4j-api-2.25.1.jar;lib\log4j-core-2.25.1.jar;src" -d bin src\*.java
```

### 4. Ejecutar la aplicación

```sh
java -cp "bin;lib\log4j-api-2.25.1.jar;lib\log4j-core-2.25.1.jar" App
```

Asegúrate de que las carpetas `lib`, `src`, y `files` estén en la raíz del proyecto y que los archivos `.jar` de Log4j estén en la carpeta `lib`.

---

## Estructura del proyecto

```
Tarea-1/
│
├── src/                # Código fuente Java
├── lib/                # Bibliotecas externas (Log4j)
├── files/              # Archivos de datos y logs
│   ├── eventos.txt
│   ├── usuarios.txt
│   ├── ventas.txt
│   └── logs/
│       └── app.log
├── README.md
└── ...
```

---

## Funcionalidades principales

- **Gestión de eventos (CRUD):** Crear, consultar, actualizar y eliminar eventos.
- **Filtrado y búsqueda:** Filtrar eventos por fecha, precio, cupos y categoría. Buscar eventos por nombre.
- **Gestión de ventas y devoluciones:** Registrar ventas y devoluciones de entradas.
- **Reportes:** Total de eventos, suma de cupos disponibles y eventos agotados.
- **Seguridad:** Autenticación de usuario y contraseña (contraseñas encriptadas con SHA-256).
- **Logs:** Todas las acciones relevantes quedan registradas en `files/logs/app.log` usando Log4j.

---

## Licencia

Este proyecto se distribuye bajo la licencia MIT, que permite su uso, copia, modificación y distribución con pocas restricciones.


## Guía de instalación y ejecución

### 1. Instalar Java 21

Descarga e instala Java 21 desde el sitio oficial de Oracle o una distribución OpenJDK:

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
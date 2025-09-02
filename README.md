# Tarea-1

## Integrantes
- Bruno Flores
- Francisca Figueroa

# Especificación de Requerimiento

Se solicita crear una aplicación para gestionar entradas de **micro-eventos**.

## Funcionalidades principales

- Crear, consultar, actualizar y eliminar eventos (**CRUD de eventos**).
- Filtrar eventos por:
  - Rango de fechas
  - Rango de precios de entrada
  - Cupos disponibles / agotados
  - Categoría
- Buscar eventos por **Nombre**.
- Generar reportes con:
  - Total de eventos
  - Suma de cupos disponibles en todos los eventos
  - Eventos agotados

## Detalle de un evento

Cada evento cuenta con los siguientes atributos:

- **Nombre**: `string`, largo máximo 128 caracteres  
- **Descripción**: `string`, largo máximo 526 caracteres  
- **Fecha**: `Date`  
- **Categoría**: Tipos posibles: `'Charla'`, `'Taller'`, `'Show'`, etc.  
- **Precio de entrada**: `int`, número positivo  
- **Cupos disponibles**: `int`, número positivo  

## Seguridad

- La aplicación debe proteger el acceso con **usuario y contraseña**.  
- Solo usuarios autenticados pueden utilizar las distintas funcionalidades.  
- (SUPUESTO) Se distinguen niveles de acceso: tipos de usuario con diferentes permisos (ej: solo ver eventos).

## Observaciones

- Falta especificación sobre **registro de ventas/devoluciones**.  
  - Una idea sería establecer un **plazo** en el cual se pueden realizar las devoluciones.

 ## Flujo de administración del código fuente 
 Se utilizará el GitHub Flow, por ser un modelo simple, flexible y adecuado para equipos pequeños.
 
 ### Ramas:
 - main: Contiene siempre la última versión estable y funcional del proyecto. Estará protegida (no se puede hacer push directo).
 - Ramas de funcionalidad (feature/...): Cada nueva funcionalidad o módulo se desarrollará en una rama propia.

 ### Pull Request (PR)
 - Todo cambio deberá ser propuesto mediante un Pull Request (PR) hacia la rama main.
 - Cada integrante deberá revisar y aprobar el PR del otro antes de poder hacer merge.
 - Se fomentará el uso de comentarios en PRs para mejorar la calidad del código.

## Estrategía de Pruebas
### ¿Cómo vamos a probarlo?
- Cada integrante prueba el módulo que desarrolló (prueba individual).
- Posteriormente, se realizarán pruebas cruzadas: cada uno probará el módulo de su compañero.

### ¿Quién prueba qué?
Se realizarán pruebas cruzadas: cada uno probará el módulo de su compañero. De esta manera, podemos detectar sesgos del desarrollador. 

## Ciclos de prueba
### Ciclo 1: Pruebas individuales
- Cada integrante prepara y ejecuta su propio conjunto de pruebas sobre el programa.

### Ciclo 2: Pruebas Cruzadas
- Ahora se unifica toda esa información en un solo documento de pruebas.
- Cada integrante prueba los módulos de sus compañeros usando el documento consolidado.

### Ciclo 3: Pruebas Extra (si es necesario)
- Si en el ciclo 2 aparecen fallos graves, se prepara un tercer ciclo para validar nuevamente después de las correcciones.


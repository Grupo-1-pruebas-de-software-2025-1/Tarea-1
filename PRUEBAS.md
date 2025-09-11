## Informe de Pruebas
En este proyecto se han implementado distintos tipos de pruebas para asegurar la correcta funcionalidad del sistema de gestión de eventos. Las pruebas realizadas se dividen principalmente en pruebas unitarias y pruebas cruzadas, y abarcan tanto los módulos desarrollados por Francisca como los desarrollados por Bruno.

La estructura de la sección es la siguiente:
1. **Pruebas Unitarias Francisca**
    - Pruebas unitarias sobre la generación de reportes.
    - Pruebas unitarias sobre la gestión de ventas.
    - Pruebas unitarias sobre la devolución de entradas.

2. **Pruebas Unitarias Bruno**
    - Pruebas unitarias sobre autenticación de usuarios (login).
    - Pruebas unitarias sobre filtrado y búsqueda de eventos.

**Pruebas Cruzadas:**
Para realizar las pruebas cruzadas se integraron todas las funcionalidad y cada integrante revisó el código del otro miembro del equipo.

Cada una de las pruebas está documentada con:
- ID de prueba
- Entrada
- Resultado esperado
- Resultado obtenido
- Éxito/Fallo
- Comentario adicional

### Pruebas unitarias Francisca
1. Añadir venta
| ID | Entrada                                              | Resultado Esperado                               | Resultado Obtenido                                         | Éxito/Fallo | Comentario Adicional                           |
| -- | ---------------------------------------------------- | ------------------------------------------------ | ---------------------------------------------------------- | ----------- | ---------------------------------------------- |
| 1  | Concierto A, cuposDisponibles: 50, cantidadVenta: 10 | Cupos disponibles disminuyen a 40 y log de éxito | Cantidad actualizada a 40, log INFO mostrado               | Éxito       | Venta registrada correctamente                 |
| 2  | Concierto A, cuposDisponibles: 50, cantidadVenta: 60 | Log de error, excede stock                       | No se descuenta stock, log WARN mostrado                   | Éxito       | Venta rechazada correctamente                  |
| 3  | Concierto Z (inexistente)                            | Log de error, volver a pedir nombre              | Sale log WARN, flujo principal continúa sin pedir de nuevo | Fallo       | Debe solicitar nuevamente el nombre del evento |
| 4  | Concierto A, cuposDisponibles: 50, cantidadVenta: z  | Log de error, volver a pedir cantidad            | Se rompe el programa                                       | Fallo       | Entrada inválida no manejada correctamente     |

2. Devolución  de entradas
| ID | Entrada                                                                      | Resultado Esperado                                         | Resultado Obtenido                      | Éxito/Fallo | Comentario Adicional                       |
| -- | ---------------------------------------------------------------------------- | ---------------------------------------------------------- | --------------------------------------- | ----------- | ------------------------------------------ |
| 5  | Concierto Z (inexistente)                                                    | Log de error, volver a pedir nombre, no actualizar stock   | Sale log WARN, flujo principal continúa | Fallo       | Debe pedir nuevamente el nombre del evento |
| 6  | Concierto A, cantidadADevolver: z                                            | Log de error, volver a pedir cantidad, no actualizar stock | Se rompe el programa                    | Fallo       | Entrada inválida no manejada correctamente |
| 7  | Concierto A, cuposDisponibles: 50, cantidadMaxima: 70, cantidadADevolver: 10 | Cupos aumentan, log de éxito                               | Cupos actualizados y log INFO mostrado  | Éxito       | Devolución registrada correctamente        |
| 8  | Concierto A, cuposDisponibles: 50, cantidadMaxima: 70, cantidadADevolver: 30 | Si excede máximo, log de error, no actualizar stock        | Log WARN, error mostrado                | Éxito       | Validación de límite máxima correcta       |


3. Generación de reporte
| ID | Entrada                                          | Resultado Esperado                                                                                       | Resultado Obtenido                                            | Éxito/Fallo | Comentario Adicional                                      |
| -- | ------------------------------------------------ | -------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------- | ----------- | --------------------------------------------------------- |
| 9  | Solicitar archivo con registros existentes       | Se crea un archivo .txt con total de eventos, suma de cupos y listado de eventos agotados. Log de éxito. | Se creó el archivo con los datos correctos.                   | Éxito       | Reporte generado correctamente                            |
| 10 | Solicitar archivo sin registros                  | Notificar que no hay registros. No crear archivo. Crear log correspondiente.                             | Genera un archivo de todas formas.                            | Fallo       | Debe validar existencia de eventos antes de crear archivo |
| 11 | Solicitar archivo sin eventos agotados           | Mostrar en el registro “No hay eventos agotados”                                                         | Sale correctamente el mensaje “No hay eventos agotados”       | Éxito       | Registro correcto sin eventos agotados                    |
| 12 | Solicitar archivo con algunos eventos agotados   | Listado de eventos agotados                                                                              | Listado correcto de eventos agotados                          | Éxito       | Todos los eventos agotados listados correctamente         |
| 13 | Solicitar archivo con todos los eventos agotados | Todos los eventos se muestran en agotados, suma de cupos = 0                                             | Cupos disponibles = 0 y listado de todos los eventos agotados | Éxito       | Correcto cálculo de cupos y listado de agotados           |

### Pruebas unitarias Bruno
1. Autenticación de usuarios
| ID | Entrada                        | Resultado Esperado                                 | Resultado Obtenido                               | Éxito/Fallo | Comentario                       |
| -- | ------------------------------ | -------------------------------------------------- | ------------------------------------------------ | ----------- | -------------------------------- |
| 28 | Contraseña incorrecta          | No permitir login, solicitar nuevamente, log error | Mensaje usuario/contraseña incorrectos, log WARN | Éxito       | Solicita reingreso correctamente |
| 29 | Usuario incorrecto             | No permitir login, solicitar nuevamente, log error | Mensaje usuario/contraseña incorrectos, log WARN | Éxito       | Solicita reingreso correctamente |
| 30 | Usuario y contraseña correctos | Permitir login, log éxito                          | Login permitido, log INFO                        | Éxito       | Login exitoso                    |
| 31 | Registrar usuario existente    | Log WARN, no completar registro                    | Mensaje de error, no registra                    | Éxito       | Registro bloqueado correctamente |
| 32 | Registrar usuario nuevo        | Log éxito, completar registro                      | Usuario registrado, log INFO                     | Éxito       | Registro exitoso                 |


2. Filtro y Buscar Evento
| ID | Entrada                          | Resultado Esperado                   | Resultado Obtenido                   | Éxito/Fallo | Comentario                              |
| -- | -------------------------------- | ------------------------------------ | ------------------------------------ | ----------- | --------------------------------------- |
| 14 | Filtrar por categoría válida     | Mostrar solo eventos de la categoría | Correctamente filtrado               | Éxito       | Filtro funcional                        |
| 15 | Filtrar por categoría inválida   | Solicitar nuevamente, mensaje error  | Mensaje mostrado, solicita reingreso | Éxito       | Entrada inválida correctamente manejada |
| 16 | Selección filtro válida          | Menú para seleccionar filtro deseado | Correctamente mostrado               | Éxito       | Funciona la selección de filtros        |
| 17 | Selección filtro inválida        | Solicitar nuevamente, mensaje error  | Mensaje mostrado, solicita reingreso | Éxito       | Entrada inválida correctamente manejada |
| 18 | Filtrar por fecha válida         | Mostrar eventos en rango de fechas   | Correctamente filtrados              | Éxito       | Filtro funcional                        |
| 19 | Filtrar por fecha inválida       | Solicitar nuevamente                 | Mensaje mostrado, solicita reingreso | Éxito       | Entrada inválida correctamente manejada |
| 20 | Filtrar por precio inválido      | Solicitar nuevamente                 | Mensaje mostrado, solicita reingreso | Éxito       | Entrada inválida correctamente manejada |
| 21 | Filtrar por precio válido        | Filtrar eventos por precio           | Correctamente filtrado               | Éxito       | Filtro funcional                        |
| 22 | Filtrar por cupos inválido       | Solicitar nuevamente                 | Mensaje mostrado, solicita reingreso | Éxito       | Entrada inválida correctamente manejada |
| 23 | Filtrar por cupos válido         | Filtrar eventos por cupos            | Correctamente filtrado               | Éxito       | Filtro funcional                        |
| 24 | Buscar evento nombre exacto      | Mostrar evento                       | Correctamente mostrado               | Éxito       | Búsqueda exacta correcta                |
| 25 | Buscar evento en minúsculas      | Mostrar evento                       | Correctamente mostrado               | Éxito       | Búsqueda insensible a mayúsculas        |
| 26 | Buscar evento en mayúsculas      | Mostrar evento                       | Correctamente mostrado               | Éxito       | Búsqueda insensible a mayúsculas        |
| 27 | Buscar evento por nombre parcial | Mostrar coincidencias                | Correctamente mostrado               | Éxito       | Búsqueda parcial correcta               |

### Pruebas cruzadas 

1. Autenticación de usuarios
| ID | Entrada                        | Resultado Esperado                                    | Resultado Obtenido                                                   | Éxito/Fallo | Comentario                                      |
| -- | ------------------------------ | ----------------------------------------------------- | -------------------------------------------------------------------- | ----------- | ----------------------------------------------- |
| 28 | Contraseña incorrecta          | No permitir login, solicitar nuevamente, log de error | Mensaje usuario/contraseña incorrectos, solicita reingreso, log WARN | Éxito       | Validación de contraseña funciona correctamente |
| 29 | Usuario incorrecto             | No permitir login, solicitar nuevamente, log de error | Mensaje usuario/contraseña incorrectos, solicita reingreso, log WARN | Éxito       | Validación de usuario funciona correctamente    |
| 30 | Usuario y contraseña correctos | Permitir login, log de éxito                          | Login permitido, log INFO                                            | Éxito       | Login exitoso correctamente                     |
| 31 | Registrar usuario existente    | Log de advertencia, no completar registro             | Mensaje de error, no se registra usuario, log WARN                   | Éxito       | Evita registro duplicado correctamente          |
| 32 | Registrar usuario nuevo        | Log de éxito, completar registro                      | Usuario agregado a archivo, log INFO                                 | Éxito       | Registro exitoso y archivo actualizado          |

2. Filtro y buscar eventos
| ID | Entrada                          | Resultado Esperado                                | Resultado Obtenido                   | Éxito/Fallo | Comentario                                  |
| -- | -------------------------------- | ------------------------------------------------- | ------------------------------------ | ----------- | ------------------------------------------- |
| 14 | Filtrar por categoría válida     | Mostrar solo eventos de la categoría seleccionada | Correctamente filtrado               | Éxito       | Filtrado por categoría funcional            |
| 15 | Filtrar por categoría inválida   | Solicitar nuevamente, mostrar mensaje de error    | Mensaje mostrado, solicita reingreso | Éxito       | Manejo de entrada inválida correcto         |
| 16 | Selección filtro válida          | Mostrar menú para seleccionar filtro              | Correctamente mostrado               | Éxito       | Menú de filtros funciona                    |
| 17 | Selección filtro inválida        | Solicitar nuevamente, mostrar mensaje de error    | Mensaje mostrado, solicita reingreso | Éxito       | Manejo de selección inválida correcto       |
| 18 | Filtrar por fecha válida         | Mostrar eventos en el rango de fechas             | Correctamente filtrado               | Éxito       | Filtro por fecha funcional                  |
| 19 | Filtrar por fecha inválida       | Solicitar nueva entrada, mostrar mensaje error    | Mensaje mostrado, solicita reingreso | Éxito       | Manejo de fecha inválida correcto           |
| 20 | Filtrar por precio inválido      | Solicitar nueva entrada, mostrar mensaje error    | Mensaje mostrado, solicita reingreso | Éxito       | Manejo de precio inválido correcto          |
| 21 | Filtrar por precio válido        | Mostrar eventos según precio                      | Correctamente filtrado               | Éxito       | Filtro por precio funcional                 |
| 22 | Filtrar por cupos inválido       | Solicitar nueva entrada, mostrar mensaje error    | Mensaje mostrado, solicita reingreso | Éxito       | Manejo de cupos inválido correcto           |
| 23 | Filtrar por cupos válido         | Mostrar eventos según cantidad de cupos           | Correctamente filtrado               | Éxito       | Filtro por cupos funcional                  |
| 24 | Buscar evento con nombre exacto  | Mostrar evento correspondiente                    | Correctamente mostrado               | Éxito       | Búsqueda exacta correcta                    |
| 25 | Buscar evento en minúsculas      | Mostrar evento correspondiente                    | Correctamente mostrado               | Éxito       | Búsqueda insensible a mayúsculas/minúsculas |
| 26 | Buscar evento en mayúsculas      | Mostrar evento correspondiente                    | Correctamente mostrado               | Éxito       | Búsqueda insensible a mayúsculas/minúsculas |
| 27 | Buscar evento por nombre parcial | Mostrar eventos que coincidan                     | Correctamente mostrado               | Éxito       | Búsqueda parcial funcional                  |


3. Generación de reporte
| ID | Entrada                                                | Resultado Esperado                                                                        | Resultado Obtenido                        | Éxito/Fallo | Comentario                                         |
| -- | ------------------------------------------------------ | ----------------------------------------------------------------------------------------- | ----------------------------------------- | ----------- | -------------------------------------------------- |
| 9  | Se solicita el archivo y hay registros                 | Crear archivo `.txt` con total de eventos, suma de cupos y eventos agotados. Log de éxito | Se crea archivo con los datos mencionados | Éxito       | Generación de reporte correcta                     |
| 10 | Se solicita el archivo y no hay registros              | Notificar que no hay registros, no crear archivo, crear log                               | Genera archivo de todas formas            | Fallo       | No maneja correctamente la ausencia de eventos     |
| 11 | Se solicita archivo y no hay eventos agotados          | Registrar “No hay eventos agotados”                                                       | Registro correcto                         | Éxito       | Correcta notificación de eventos agotados          |
| 12 | Se solicita archivo y hay algunos eventos agotados     | Listado de eventos agotados                                                               | Listado generado correctamente            | Éxito       | Eventos agotados listados correctamente            |
| 13 | Se solicita archivo y todos los eventos están agotados | Mostrar todos los eventos en agotados, suma de stock disponible = 0                       | Listado generado correctamente, stock = 0 | Éxito       | Todos los eventos agotados mostrados correctamente |

4. Ventas
| ID | Entrada                                | Resultado Esperado                    | Resultado Obtenido                        | Éxito/Fallo | Comentario                                    |
| -- | -------------------------------------- | ------------------------------------- | ----------------------------------------- | ----------- | --------------------------------------------- |
| 1  | Evento=Concierto A, cupos=50, venta=10 | Disminuir cupos a 40, log de éxito    | Cupos disminuidos a 40, log INFO          | Éxito       | Venta registrada correctamente                |
| 2  | Evento=Concierto A, cupos=50, venta=60 | Mensaje de error, no exceder stock    | No se descuenta, log WARN                 | Éxito       | Manejo de stock excedido correcto             |
| 3  | Evento inexistente                     | Log de error, volver a pedir nombre   | Sale log WARN, se sale al flujo principal | Fallo       | No vuelve a pedir nombre, necesita corrección |
| 4  | Venta inválida (cantidad no numérica)  | Log de error, volver a pedir cantidad | Programa se rompe                         | Fallo       | Entrada inválida no manejada correctamente    |

5. Devolución de entradas
| ID | Entrada                                                                  | Resultado Esperado                                       | Resultado Obtenido                        | Éxito/Fallo | Comentario                                    |
| -- | ------------------------------------------------------------------------ | -------------------------------------------------------- | ----------------------------------------- | ----------- | --------------------------------------------- |
| 5  | Evento inexistente                                                       | Log de error, volver a pedir nombre, no actualizar stock | Sale log WARN, se sale al flujo principal | Fallo       | No vuelve a pedir nombre, necesita corrección |
| 6  | Cantidad a devolver inválida (no numérica)                               | Log de error, volver a pedir cantidad                    | Programa se rompe                         | Fallo       | Entrada inválida no manejada correctamente    |
| 7  | Evento=Concierto A, cupos=50, cantidad devolver=10                       | Sumar entradas a cupos, log de éxito                     | Stock actualizado, log INFO               | Éxito       | Devolución realizada correctamente            |
| 8  | Evento=Concierto A, cupos=50, cantidad devolver=30 (excede stock máximo) | Mensaje de error, no actualizar stock                    | Log WARN, error                           | Éxito       | Validación de stock máximo correcta           |



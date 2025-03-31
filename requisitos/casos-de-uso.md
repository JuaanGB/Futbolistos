# Tabla de Actores y Casos de Uso

| Actor Principal | Caso de Uso |
|---------------|------------|
| **Usuario**   | - Registrarse en el sistema <br> - Iniciar sesión en el sistema |
| **Alumno**   | - [Seleccionar curso <sup>*1</sup>](#8) <br> - [Realizar curso](#1) <br> - Guardar progreso del curso <br> - [Mostrar estadísticas de usuario](#3) |
| **Creador**   | - Crear curso <br> - Compartir curso |
| **Sistema**   | - [Actualizar estadísticas de usuario](#9) <br> - Cargar cursos disponibles |

## Aclaraciones
<sup>*1</sup> Dentro de **Seleccionar curso** iría la estrategia concreta de estudio: **secuencial**, **aleatoria** o **espaciada** (flujos alternativos). Al igual que el sistema avisará de si hay algún progreso guardado para el curso, ofreciéndoselo al usuario para **reanudarlo**.
La funcionalidad extra que hemos decidido implementar son medallas, que formarían parte del caso de uso de **Mostrar estadísticas de usuario** y un sistema de pistas en las preguntas, presente en el caso de uso **Realizar curso**

---

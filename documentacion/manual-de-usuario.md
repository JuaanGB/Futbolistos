# Manual de usuario.

## Contenido

- [Login de usuarios](#login-de-usuarios)
- [Registro de usuarios](#registro-de-usuarios)
- [Ventana principal](#ventana-principal)
- [Importar curso](#importar-curso)
- [Realizar un curso / Reanudar progreso](#realizar-un-curso--reanudar-progreso)
- [Mostrar estadísticas](#mostrar-estadísticas)
- [Formato de importación de cursos](#formato-de-importacion-de-cursos)

---

## Login de usuarios.

<img title="" src="img/login.png" alt="" width="236" data-align="center">

Inicialmente no dispones de una cuenta de usuario para utilizar la aplicación. Así que deberás pulsar el botón de **Registrar***.

## Registro de usuarios.

<img title="" src="img/registro.png" alt="" width="338" data-align="center">

Introduciremos nuestros credenciales deseados para la cuenta. Podemos observar la contraseña introducida clicando el botón con el icono del ojo. 

El sistema notificará si ya existe un usuario con dicho nombre o la contraseña y su confirmación no coincide.

## Ventana principal.

Tras el registro, deberemos introducir de nuevo nuestras credenciales de acceso en la ventana de *login*.

<img title="" src="img/ventana-principal.png" alt="" width="398" data-align="center">

Desde aquí podemos cargar cursos desde ficheros o ver las estadísticas. Aunque de poco sirve ver las estadísticas cuando no hemos realizado ningún curso todavía.

## Importar curso.

Clicaremos el botón **Importar curso** y seleccionaremos el fichero que contiene el curso completo. Se muestran los formatos válidos en el selector de fichero.

Los ficheros de cursos deben seguir un formato minucioso, tal y como se explica [aquí](#formato-de-importacion-de-cursos).

<img title="" src="img/ventana-principal-tras-importar.png" alt="" width="398" data-align="center">

Hemos importado correctamente el curso *Jugadores de Fútbol Legendarios*. En ese panel podemos observar el título, una foto, un botón de información que muestra la descripción del curso y un botón para **Comenzar el curso**.

## Realizar un curso / Reanudar progreso.

Si no tenemos una sesión guardada para el curso (lo cual es lo más normal si lo acabamos de importar), se preguntará acerca de la estrategia de aprendizaje que el usuario desea emplear:

<img title="" src="img/seleccionar-estrategia.png" alt="" width="257" data-align="center">

Tras esto, se mostrará la primera pregunta, donde todas ellas tienen un tiempo límite para ser respondidas.

<img title="" src="img/realizar-curso.png" alt="" width="234" data-align="center">

En cualquier momento del curso, podemos **guardar su estado** para retomarlo más adelante. Clicaremos el botón **Guardar estado** del margen inferior.

Cuando volvamos a retomarlo, no se sugerirá una estrategia de aprendizaje, sino que se le indicará al alumno que se va a retomar la sesión previamente guardada:

<img title="" src="img/reanudar-curso.png" alt="" width="341" data-align="center">

## Mostrar estadísticas.

Las estadísticas relacionadas con las preguntas respondidas se actualizan tras acabar el curso al completo.

Otras, como el número de cursos creados o la racha, se actualizan cuando se lleva a cabo la acción necesaria: importar un curso o iniciar sesión en el sistema, respectivamente.

Todas las estadísticas son visibles para el usuario en la ventana asociada al botón **Mostrar estadísticas** de la ventana principal.

<img src="img/estadisticas.png" title="" alt="" data-align="center">

## Formato de importación de cursos

### Atributos necesarios para cada entidad

Si estás considerando poner algún atributo como `null`, **no lo incluyas**.

---

**Curso**

- `nombre`: No puede ser blanco (es decir, no sólo formado por espacios).
- `descripcion`: No puede ser blanca.
- `imagen_url` (opcional): Si se incluye, no puede ser blanca.
- `bloques_de_contenido`: Debe contener al menos un bloque de contenido.

---

**Bloque de contenido**

- `preguntas`: Debe contener al menos una pregunta.

---

**Pregunta (reglas aplicables a todos los subtipos)**

- `tipo_pregunta`: Debe ser uno de los valores reconocidos (`flashcard`, `test`, `completar`, etc.).

- `segundos`: Debe ser mayor que 0.

- `pista` (opcional): Si se incluye, no puede ser blanca.

- `imagen_url` (opcional): Si se incluye, no puede ser blanca.

- Si `tipo_pregunta` es `flashcard`:
  
  - `anverso`: No puede ser blanco.
  
  - `reverso`: No puede ser blanco.
  
  - No debe incluir `enunciado`.

- Si `tipo_pregunta` es `completar`:
  
  - `enunciado`: No puede ser blanco.
  
  - `respuesta_correcta`: No puede ser blanca.

- Si `tipo_pregunta` es `test`:
  
  - `enunciado`: No puede ser blanco.   
  
  - `respuestas`: Debe ser una lista con al menos dos elementos, y ninguno puede ser blanco o `null`.
  
  - `respuesta_correcta`: Debe estar incluida dentro de `respuestas`.

---

Si has seguido correctamente esta guía, el curso se importará correctamente. Si no, mostrará un mensaje de error de que hay errores en el formato.

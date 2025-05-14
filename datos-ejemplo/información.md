# Información acerca de los ficheros de ejemplo.

## Cursos de ejemplo.

- `curso-jugadores`: Curso en formato JSON acerca de cuestiones de jugadores de fútbol.

- `curso-tacticas`: Curso en formato YAML acerca de cuestiones de tácticas en el fútbol.

- `curso-formato`: Curso con únicamente tres preguntas que utilicé para comprobar el método `checkParsing()` de la clase `Curso`.

Cada curso (excepto el último) contiene un directorio local con el mismo nombre en el que almacenan la foto de cada pregunta.

## Fichero de base de datos de ejemplo.

Es el fichero `futbolistos.db` y contiene los siguientes datos persistidos:

- Dos usuarios (con mismo nombre de usuario y contraseña): `juan` y `jesus`.

- Usuario `juan`: 
  
  - Tiene importado `curso-jugadores` y una sesión activa para ese curso siguiendo una estrategia aleatoria.
    
    - Falta una única pregunta para comprobar que las estadísticas se actualizan tras terminarlo.
  
  - No ha completado todavía ningún curso, así que sus estadísticas están a 0 excepto la de la racha y la de cursos creados (importados).

- Usuario `jesus`: 
  
  - Ha importado ambos cursos (tácticas y jugadores).
  
  - Ha completado una vez el curso de tácticas. Por ello, tiene estadísticas asociadas a ese curso.

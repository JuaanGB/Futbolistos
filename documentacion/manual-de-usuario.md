# Formato de importación de cursos

## Atributos necesarios para cada entidad

Si estás considerando poner algún atributo como `null`, **no lo incluyas**.

---

### Curso

- `nombre`: No puede ser blanco (es decir, no sólo formado por espacios).
- `descripcion`: No puede ser blanca.
- `imagen_url` (opcional): Si se incluye, no puede ser blanca.
- `bloques_de_contenido`: Debe contener al menos un bloque de contenido.

---

### Bloques de contenido

- `preguntas`: Debe contener al menos una pregunta.

---

### Pregunta (reglas aplicables a todos los subtipos)

- `tipo_pregunta`: Debe ser uno de los valores reconocidos (`flashcard`, `test`, `completar`, etc.).
- `segundos`: Debe ser mayor que 0.
- `pista` (opcional): Si se incluye, no puede ser blanca.
- `imagen_url` (opcional): Si se incluye, no puede ser blanca.

- Si `tipo_pregunta` es `flashcard`:
  
  - `anverso`: No puede ser blanco.
  
  - `reverso`: No puede ser blanco.
  
  - No debe incluir `enunciado`.

-  Si `tipo_pregunta` es `completar`:
  
  - `enunciado`: No puede ser blanco.
  
  - `respuesta_correcta`: No puede ser blanca.

- Si `tipo_pregunta` es `test`:
  
  - `enunciado`: No puede ser blanco.   
  
  - `respuestas`: Debe ser una lista con al menos dos elementos, y ninguno puede ser blanco o `null`.
  
  - `respuesta_correcta`: Debe estar incluida dentro de `respuestas`.

---

Si has seguido correctamente esta guía, el curso se importará correctamente. Si no, mostrará un mensaje de error de que hay errores en el formato.

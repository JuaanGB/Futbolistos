# Información para desarrolladores.

## Contenido

- [¿Cómo añadir nuevos tipos de pregunta?](#cómo-añadir-nuevos-tipos-de-pregunta)
  - [1. Jerarquía de `PanelPregunta`](#1-jerarquía-de-panelpregunta)
  - [2. Clase Java que hereda de `Pregunta`](#2-clase-java-que-hereda-de-pregunta)
- [¿Cómo añadir nuevas estrategias de aprendizaje?](#cómo-añadir-nuevas-estrategias-de-aprendizaje)
  - [1. Crear nueva clase que implemente la interfaz `EstrategiaAprendizaje`](#1-crear-nueva-clase-que-implemente-la-interfaz-estrategiaaprendizajejava)
  - [2. Añadir la nueva estrategia a `FactoriaEstrategiasAprendizaje.java`](#2-añadir-la-nueva-estrategia-a-factoriaestrategiasaprendizajejava)
- [¿Cómo agregar nuevos formatos de serialización?](#cómo-agregar-nuevos-formatos-de-serialización)
  - [1. Añadir nuevo `ObjectMapper` en clase `FactoriaObjectMapper.java`](#1-añadir-nuevo-objectmapper-en-clase-factoriaobjectmapperjava)

---

## ¿Cómo añadir nuevos tipos de pregunta?

### 1. Jerarquía de `PanelPregunta`.

- Clase principal: `PanelPregunta`
  - Contiene todos los elementos comunes de un panel de pregunta: enunciado, foto (opcional), botón de pista, tiempo restante...
  - Contiene un panel `panelRespuestas` que es un atributo `protected` para que cada subtipo concreto de pregunta añada los componentes que sean necesarios.
  - Esta clase es la que se encarga de gestionar el tiempo restante de la pregunta.
    - Sin embargo, el método `gestionarPreguntaRespondida(respondida)` es implementado por cada subclase de `PanelPregunta`. ¿Por qué? No es lo mismo que se acabe el tiempo en una `FlashCard` que en una `TipoTest`.
  - Cualquier duda acerca de ésto, en principio funciona bien la subclase `PanelPreguntaTest`.

Ejemplo de plantilla para los PanelPreguntaTemplate:

```java
public class PanelPreguntaTemplate extends PanelPregunta {

    // Nuevos componentes

    public PanelPreguntaTemplate(PreguntaNueva p) {
        super(p);
        // Configuración adicional
    }

    @Override
    protected void gestionarPreguntaRespondida(boolean respondida) {
        ...
    }
```

### 2. Clase Java que hereda de Pregunta.

Al igual que con `PanelPregunta`, hay una clase que contiene toda la información de una pregunta. Es la clase `Pregunta`.

Para crear un nuevo tipo de pregunta, hay que crear una clase que herede de ésta e implementar obligatoriamente los métodos `isRespuestaValida(...)` y `getPanel()`. 

Este segundo método abstracto es el que dota de extensibilidad a la aplicación. A la hora de mostrar un panel u otro en función del tipo de pregunta, se le pregunta directamente a ella que, por ligadura dinámica, llamará al método `getPanel()` de su tipo dinámico.

También es recomendable añadir funcionalidad extra al método `checkParsing()`, pues cada pregunta tendrá sus restricciones como número de respuestas o formato.

## ¿Cómo añadir nuevas estrategias de aprendizaje?

### 1. Crear nueva clase que implemente la interfaz `EstrategiaAprendizaje.java`.

Dicha interfaz podría ser considerada funcional pues únicamente tiene un método a ser implementado por las clases:

```java
public interface EstrategiaAprendizaje {
    List<Pregunta> calcularOrden(Curso c);
}
```

Se deberá obtener las preguntas del curso (antes obteniendo los bloques de contenido) y realizar los cálculos necesarios características de la estrategia de aprendizaje a implementar.

### 2. Añadir la nueva estrategia a `FactoriaEstrategiasAprendizaje.java`.

Contiene un mapa que almacena un nombre y una instancia de cada estrategia. El mapa es un `LinkedHashMap` para poder personalizar el orden de aparición de las estrategias en el selector.

Para añadir una nueva estrategia, simplementar agregar nueva directiva

```java
estrategias.put("Nueva estrategia", new NuevaEstrategia());
```

en el método `inicializarEstrategias()`.

## ¿Cómo agregar nuevos formatos de serialización?

### 1. Añadir nuevo `ObjectMapper` en clase `FactoriaObjectMapper.java`.

Al igual que la factoría anterior, ésta también contiene un mapa que relaciona la extensión con el `ObjectMapper` a emplear.

Por ejemplo, si quisiéramos añadir soporte para el formato `XML`, deberíamos añadir la línea

```java
mappers.put("xml", new XMLMapper());
```

en el método `inicializarMapa()` (además de las dependencias pertinentes en el fichero `pom.xml`).

La extensibilidad se logra nada más añadiendo esa línea, ya que desde las ventanas se llama al método `getExtensionesValidas()`, que interactúa con el mapa de la factoría.

# Información para desarrolladores.

## ¿Cómo añadir nuevos tipos de Pregunta?

### 1. Jerarquía de PanelPregunta.

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

    public PanelPreguntaTest(Pregunta p) {
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

estrategias.put("Estrategia nueva", new EstrategiaNueva());``

en el método `inicializarEstrategias()`.

## ¿Cómo agregar nuevos formatos de serialización

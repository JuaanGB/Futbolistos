## Información para desarrolladores.

### Jerarquía de PanelPregunta.

- Clase principal: `PanelPregunta`
  - Contiene todos los elementos comunes de un panel de pregunta: enunciado, foto (opcional), botón de pista, tiempo restante...
  - Contiene un panel `panelRespuestas` que es un atributo `protected` para que cada subtipo concreto de pregunta añada los componentes que sean necesarios.
  - Esta clase es la que se encarga de gestionar el tiempo restante de la pregunta.
    - Sin embargo, el método `gestionarPreguntaRespondida(respondida)` es implementado por cada subclase de `PanelPregunta`. ¿Por qué? No es lo mismo que se acabe el tiempo en una `FlashCard` que en una `TipoTest`.
  - Los métodos `personalizarDisplay(pregunta)` y `inicializarComponentes()` son `protected` para que cada subtipo concreto de pregunta añada la funcionalidad adicional requerida. Por ejemplo: añadir botones en `PreguntaTest` y ponerles el texto.
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
    protected void personalizarDisplay(Pregunta p) {
        super.personalizarDisplay(p);
        // Personalizar display de nuevos componentes
    }

    @Override
    protected void inicializarComponentes() {
        super.inicializarComponentes();

        // Configuración de nuevos componentes
    }

    @Override
    protected void gestionarPreguntaRespondida(boolean respondida) {
    	...
    }
```

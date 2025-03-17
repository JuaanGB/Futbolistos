## Información para desarrolladores.

### Jerarquía de PanelPregunta.

- Clase principal: `PanelPregunta`
  - Contiene todos los elementos comunes de un panel de pregunta: enunciado, foto (opcional), botón de pista, tiempo restante...
  - Contiene un panel `panelRespuestas` que es un atributo `protected` para que cada subtipo concreto de pregunta añada los componentes que sean necesarios.
  - Esta clase es la que se encarga de gestionar el tiempo restante de la pregunta.
    - Sin embargo, el método `manejarTiempoTerminado(respondida)` es implementado por cada subclase de `PanelPregunta`. ¿Por qué? No es lo mismo que se acabe el tiempo en una `FlashCard` que en una `TipoTest`.
  - Los métodos `personalizarDisplay(pregunta)` y `inicializarComponentes()` son `protected` para que cada subtipo concreto de pregunta añada la funcionalidad adicional requerida. Por ejemplo: añadir botones en `PreguntaTest` y ponerles el texto.
  - Cualquier duda acerca de ésto, en principio funciona bien la subclase `PanelPreguntaTest`.

Ejemplo de plantilla para los PanelPreguntaConcreto:
```java
public class PanelPreguntaTemplate extends PanelPregunta {

	// Nuevos componentes y atributos

	public PanelPreguntaTest(Pregunta p) {
		super(p);
	}

	@Override
	protected void personalizarDisplay(Pregunta p) {

		// Personalización global (enunciado, foto, pista)
		super.personalizarDisplay(p);
		PreguntaTest pt = (PreguntaTest) p;
		// Personalización de los nuevos componentes
		// ...
		}
	}

	@Override
	protected void inicializarComponentes() {

		// Inicializamos lo global a cada pregunta
		super.inicializarComponentes();

		// Inicializamos los nuevos componentes
		// ...
	}

	@Override
	protected void manejarTiempoTerminado(boolean respondida) {
        
        // Manejar qué ocurre cuando se acaba el temporizador (respondida=false)
        // o cuando se responde (respondida=true).
        // Debe incluir siempre la siguiente llamada.
        Controlador.getInstancia().pasarASiguientePregunta();
    }
}
```
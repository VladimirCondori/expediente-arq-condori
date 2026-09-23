# Recorrido de defensa — 2.5 minutos

**Estudiante:** Vladimir Condori Huanca
**Variante:** Sistema de Inventarios

1. **Qué es.** Sistema de inventarios de una tienda: qué hay, cuánto, en qué almacén y por qué cambió. Atributos: integridad y trazabilidad.
2. **El problema.** `Existencia` cambiaba el saldo y no avisaba a nadie: cada interesado nuevo obligaba a abrir la clase. OCP roto.
3. **La decisión.** Observer para *cuándo* avisar, Strategy para *cuánto* reponer. Observer y no llamada directa; Strategy y no herencia, porque la política cambia según el proveedor, no según el tipo de movimiento.
4. **Lo que descarté.** Decorator: apilar capas cambia el total según el orden, y en la reposición no hay nada que apilar.
5. **Dónde y qué falta.** `h3/final/` y `h4/adr-001.md`. Deuda: el tipo de movimiento sigue siendo un String con un `when`, y mi diagrama muestra subclases.

# Recorrido de defensa — 2.5 minutos

**Estudiante:** Vladimir Condori Huanca
**Variante:** Sistema de Inventarios

## El guion en 5 líneas

1. **Qué es.** Sistema de inventarios de una tienda: qué hay, cuánto, en qué almacén y por qué cambió. Atributos de calidad: integridad y trazabilidad.
2. **El problema.** `Existencia` cambiaba el saldo y no avisaba a nadie: cada interesado nuevo obligaba a abrir la clase. OCP roto.
3. **La decisión.** Observer para *cuándo* avisar, Strategy para *cuánto* reponer. Observer y no llamada directa; Strategy y no herencia, porque la política cambia según el proveedor, no según el tipo de movimiento.
4. **Lo que descarté.** Decorator: apilar capas cambia el total según el orden, y en la reposición no hay nada que apilar.
5. **Dónde y qué falta.** `h3/final/` y `h4/adr-001.md`. Deuda: el tipo de movimiento sigue siendo un String con un `when`, y el diagrama del H1 muestra subclases.

## Desarrollado, con los tiempos

**0:00 – 0:25 · Qué construí y para quién**

Sistema de inventarios para una tienda de comercio: controla qué productos hay, cuántos, en qué
almacén y por qué cambió esa cantidad. Las ventas las registra el POS; acá entra solo la salida de
stock que esa venta genera. Los dos atributos de calidad fijados en el H1 son integridad de los
datos y trazabilidad.

**0:25 – 1:10 · El problema crítico**

En el H1 aparecieron dos. `Usuario` hacía dos trabajos —decir quién es la persona y decidir qué
puede hacer—, lo que rompe SRP y hacía que Recursos Humanos y Gerencia pidieran cambios sobre la
misma clase. El que más pesó fue el otro: `Existencia` actualizaba el saldo y no avisaba a nadie.
Para que Compras se enterara de un stock bajo había que llamarla por nombre dentro de `aplicar()`,
y abrir esa clase con cada interesado nuevo. Eso es OCP roto.

**1:10 – 1:55 · La decisión y por qué esa**

Dos decisiones, fusionadas en `h3/final/`. La primera: `Existencia` publica el cambio de stock y los
interesados se suscriben — Observer. Observer y no una llamada directa inyectada, porque con la
llamada directa `Existencia` sigue conociendo a un solo interesado y sumar la vitrina del POS obliga
a tocarla. La segunda: cuánto reponer salió a una estrategia, con `HastaElMinimo`,
`PorLoteDelProveedor` y `ConStockDeSeguridad` detrás de una interfaz. Strategy y no herencia porque
la política cambia según el proveedor en tiempo de ejecución, no según el tipo de movimiento.
Observer decide cuándo avisar; Strategy, cuánto pedir. Decorator quedó descartado: apilar capas
cambia el total según el orden, y en la reposición no hay nada que apilar. Las dos decisiones están
en `h4/adr-001.md` con sus alternativas.

**1:55 – 2:30 · Dónde se ve y qué haría distinto**

En el repositorio vive en `h3/final/`, en `Observadores.kt` y `Reposicion.kt`. El ADR está en `h4/`,
y el diagrama de contenedores del C4 muestra el servicio de avisos separado de la lógica de negocio.
Con una semana más arreglaría el tipo de movimiento: hoy es un String con un `when` adentro, y el
diagrama de clases del H1 muestra `Entrada`, `Salida` y `Ajuste` como subclases. Ese es el OCP que
quedó a medias.

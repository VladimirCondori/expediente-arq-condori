# Singleton — por qué mi caso NO lo pide

**Variante:** Sistema de Inventarios

## No hay ningún candidato genuino

Todas las clases de la base son entidades con muchas instancias: muchos productos,
almacenes, existencias y movimientos.
Lo único que sí es único es su identidad — el SKU, el código de almacén, la pareja
producto-almacén — y eso lo garantiza la base de datos con `UNIQUE`, no una instancia
única en memoria.

## Candidatos revisados y descartados

- **Correlativo de movimientos.** Un objeto en memoria solo garantiza la unicidad
  dentro de un proceso: con dos servidores se emiten números repetidos. Es una
  garantía falsa, peor que no dar ninguna.
- **Conteo físico abierto.** Único por almacén, no global. Es unicidad de alcance, y
  Singleton solo sabe ser único por proceso.
- **Conexión a la base de datos.** Se usa un *pool*, que por definición no es una sola.
- **Configuración global.** La base no tiene parámetros globales: el único umbral que
  existe es `Producto.stockMinimo`, y es por producto.

## Lo más cercano, si el caso creciera

Al agregar valorización aparecería el **período contable abierto**: solo puede haber
uno abierto a la vez, y todo movimiento se registra contra él. Aun así no sería
Singleton, sino una entidad con un índice único sobre `estado = ABIERTO`, porque es un
dato que vive en la base de datos y cambia cada mes.

## Regla aplicada

Antes de declarar algo Singleton: ¿único respecto de qué? Si es del negocio, va a la
base de datos con una restricción. Si es de esta ejecución, va como instancia
inyectada. Si es "de todo el programa y accesible por cualquiera", es acceso global
disfrazado de arquitectura.

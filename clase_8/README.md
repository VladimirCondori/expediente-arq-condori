# Clase 8 — Práctica: Singleton

**Estudiante:** Vladimir Condori Huanca
**Variante:** Sistema de Inventarios

---

## ¿Qué es genuinamente único en el dominio?

**El período contable abierto** (el mes en curso, el que se cierra en el "cierre de mes").

### Justificación

> Solo puede haber un período abierto a la vez, y todo movimiento se registra contra
> ese período.
> Si hubiera dos abiertos, un mismo movimiento podría caer en cualquiera de los dos y
> la valorización del inventario dejaría de ser reproducible.

Es único **globalmente**, no por almacén ni por usuario: el cierre de mes es uno solo
para toda la empresa.

> **Nota:** el diagrama de clases actual no incluye la clase `PeriodoContable`. Es el
> concepto que el diseño incorporaría al agregar valorización de inventario.

---

## Candidatos descartados

| Candidato | Por qué NO |
|---|---|
| **Correlativo de movimientos** | Un objeto en memoria solo garantiza la unicidad dentro de **un** proceso: con dos servidores se emiten números repetidos. Es una garantía falsa |
| **Conteo físico abierto** | Único **por almacén**, no global. Es unicidad de alcance, y Singleton solo sabe ser único por proceso |
| **Conexión a la base de datos** | Se usa un *pool*; una conexión única sería cuello de botella y rompería las transacciones concurrentes |
| **Clases del dominio** (`Producto`, `Almacen`, `Existencia`, `Usuario`) | Todas tienen muchas instancias. Su unicidad es de **identidad** (SKU, código, la pareja producto-almacén) y la garantizan las restricciones `UNIQUE` |

---

## Aun siendo único, no se implementa como Singleton

El período contable es un **dato**: vive en la base de datos y cambia cada mes. La
unicidad se garantiza con una entidad `PeriodoContable` y la regla *"solo uno con
estado ABIERTO"*, aplicada por un índice único.

Un Singleton en memoria solo lo cachearía, y con dos servidores cada uno creería tener
el suyo.

---

## Cuándo Singleton se vuelve antipatrón

| Señal | Cómo se vería en este sistema |
|---|---|
| Se usa por **acceso global**, no por unicidad | La unicidad es la excusa; la comodidad de llamarlo desde cualquier parte es el motivo real |
| Guarda **estado mutable** | Si el período abierto se pudiera cambiar en caliente, dos movimientos simultáneos se registrarían en meses distintos |
| La unicidad es de **alcance menor** | El conteo físico es único por almacén: forzarlo a global impediría contar dos sucursales el mismo día |
| La unicidad debe **sobrevivir al proceso** | El correlativo con dos servidores: garantía falsa, peor que ninguna |
| **Oculta la dependencia** | No aparece en el constructor, así que nadie sabe de qué depende la clase. Mata las pruebas y viola la inversión de dependencias |
| Se vuelve **god object** | Accesible desde todas partes, se le cuelgan responsabilidades hasta que cambia por seis razones distintas |

---

## Regla de decisión

Antes de declarar algo Singleton, preguntar: **¿único respecto de qué?**

- Único **del negocio** → restricción en la base de datos
- Único **de esta ejecución** → instancia inyectada por constructor
- Único **de todo el programa y accesible por cualquiera** → casi siempre es comodidad
  disfrazada de arquitectura

# Sistema de Inventarios

**Nombre:** Vladimir Condori Huanca

**Variante:** Variante 4 · Comercio — "Tienda con inventario"

Controla qué productos hay, cuántos, en qué almacén y por qué cambió esa cantidad.
Las ventas las registra el POS; acá entra solamente la salida de stock que esa venta genera.

**Problema crítico:** que el saldo del sistema no coincida con lo que hay en el estante, y no
poder explicar de dónde salió la diferencia. Por eso el stock no se edita a mano: solo cambia
con un movimiento registrado —entrada, salida o ajuste— y cada movimiento lleva fecha, motivo
y responsable.

Me tocó esta variante porque trabajé con sistemas de inventarios y conozco de cerca los
problemas de control de stock y disponibilidad que plantea el caso.

## Contenido

| Carpeta | Qué hay |
|---|---|
| `clase-01-expediente-h1/` | H1 — diagrama de clases |
| `clase-02` … `clase_10/` | Prácticas por clase: SOLID, Singleton, Builder, Adapter |
| `h3/` | Laboratorio de patrones: `base/`, una carpeta por patrón y `final/` con la fusión |
| `h4/` | C4 niveles 1 y 2, y ADR-001 |
| `parcial2/` `integradora/` `examen/` | Evaluaciones |

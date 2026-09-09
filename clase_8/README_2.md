# Clase 8 — Práctica: Singleton

**Estudiante:** Vladimir Condori Huanca
**Variante:** Sistema de Inventarios

## ¿Qué es genuinamente único en el dominio?

**El período contable abierto** (el mes en curso, el que se cierra en el cierre de mes).

### Justificación

Solo puede haber un período abierto a la vez, y todo movimiento se registra contra ese período.
Si hubiera dos abiertos, un mismo movimiento podría caer en cualquiera de los dos y la valorización del inventario dejaría de ser reproducible.

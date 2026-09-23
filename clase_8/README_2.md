# Clase 8 — Práctica: Singleton

**Estudiante:** Vladimir Condori Huanca
**Variante:** Sistema de Inventarios

## El candidato

**El período contable abierto.** Solo puede haber uno a la vez y todo movimiento se registra
contra él. Si hubiera dos abiertos, un mismo movimiento podría caer en cualquiera de los dos y
la valorización del inventario dejaría de ser reproducible.

## La práctica

`codigo/PeriodoContable.kt` lo implementa como `object` de Kotlin, y `codigo/PeriodoContableTest.kt`
lo prueba.

## Dónde se vuelve antipatrón

En `reiniciarParaPruebas()`. Ese método no debería existir: está ahí solo porque el Singleton
guarda estado global y las pruebas se contaminan entre sí. Tener que agregar un "reiniciar"
para poder testear es la señal de que el patrón empezó a estorbar.

## Qué queda en el diseño real

No lo dejo como Singleton. El período contable es un dato del negocio que vive en la base de
datos y cambia cada mes: se resuelve con un índice único sobre `estado = ABIERTO`, no con una
instancia única en memoria.

El análisis completo, con todos los candidatos descartados, está en `h3/con-singleton/singleton.md`.

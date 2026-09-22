# Parte 2 — Detecciones SOLID (esqueleto-A.cs)

Autor: Vladimir Condori

## 1. SRP — Responsabilidad Única
**Dónde:** clase `GestorDePedidos`, método `ProcesarPedido`.
**Por qué:** calcula el precio, persiste, imprime el vale y envía el correo; tiene cuatro razones para cambiar en una sola clase.

## 2. OCP — Abierto/Cerrado
**Dónde:** el `switch (tipoMenu)` dentro de `ProcesarPedido`.
**Por qué:** agregar un tipo de menú obliga a modificar el método; además los precios están fijos en el código aunque el administrador debe poder ajustarlos.

## 3. DIP — Inversión de Dependencias
**Dónde:** `new BaseDeDatosComedor()` y `new CorreoUniversitario()` dentro de `ProcesarPedido`.
**Por qué:** la lógica de negocio depende de clases concretas de bajo nivel; no se puede cambiar el canal de aviso ni probar el gestor sin tocar su código.

**Curada en refactor.cs:** DIP.

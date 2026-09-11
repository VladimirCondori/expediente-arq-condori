# H3 · Patrón Builder

**Estudiante:** Vladimir Condori Huanca
**Variante:** Sistema de Inventarios

## Mi objeto complejo: la Orden de Compra

Es proveedor + N líneas (producto, cantidad, precio) + fecha de entrega + descuento +
observación. Tiene partes obligatorias, partes opcionales y una cantidad variable de
líneas: armarla con un constructor de cinco parámetros obliga a pasar nulos y deja
objetos a medio llenar.

El constructor de `OrdenCompra` es **privado**. La única forma de obtener una es pasar
por el guardián.

## Los 5 pasos del armador

| Paso | Método | Obligatorio |
|---|---|---|
| 1 | `paraProveedor(nombre)` | Sí |
| 2 | `conEntregaEl(fecha)` | No — por defecto "por confirmar" |
| 3 | `agregarLinea(producto, cantidad, precio)` | Sí, al menos una. Repetible |
| 4 | `conDescuento(porcentaje)` | No — por defecto 0 |
| 5 | `conObservacion(texto)` | No |

```kotlin
val orden = OrdenCompra.Builder()
    .paraProveedor("Ferreteria Central")
    .conEntregaEl("2026-09-20")
    .agregarLinea("Martillo", 20, 35.0)
    .agregarLinea("Taladro", 5, 400.0)
    .conDescuento(10.0)
    .conObservacion("Entregar en almacen central")
    .construir()
```

## Las 2 validaciones del guardián — `construir()`

1. **Sin proveedor no hay orden.** No se le puede comprar a nadie.
2. **Al menos una línea con cantidad mayor a cero.** Una orden sin nada que pedir no es
   una orden.

Si alguna falla, lanza `IllegalArgumentException` y **no se crea el objeto**. Nunca
existe una `OrdenCompra` inválida.

## Archivos

- `OrdenCompra.kt` — el objeto y su `Builder`
- `OrdenCompraBuilderTest.kt` — 5 pruebas (clic derecho → Run, no necesita emulador)

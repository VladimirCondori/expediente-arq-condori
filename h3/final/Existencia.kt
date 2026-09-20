package com.applimarket.practicas_uab.h3.fusion

class Existencia(
    val producto: Producto,
    val almacen: Almacen,
    cantidadInicial: Int = 0
) {
    var cantidad: Int = cantidadInicial
        private set

    private val observadores = mutableListOf<ObservadorStock>()

    fun suscribir(observador: ObservadorStock) {
        observadores.add(observador)
    }

    fun hayStock(pedida: Int): Boolean = cantidad >= pedida

    fun aplicar(movimiento: MovimientoInventario) {
        val nueva = cantidad + movimiento.cantidadEfectiva()
        check(nueva >= 0) {
            "El movimiento dejaria el stock en $nueva para ${producto.sku}"
        }
        cantidad = nueva

        val cambio = CambioDeStock(producto.sku, cantidad, producto.stockMinimo, movimiento.motivo)
        observadores.forEach { it.alCambiarStock(cambio) }
    }
}

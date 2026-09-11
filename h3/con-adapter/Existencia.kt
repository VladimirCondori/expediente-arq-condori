package com.applimarket.practicas_uab.h3.conadapter

class Existencia(
    val producto: Producto,
    val almacen: Almacen,
    cantidadInicial: Int = 0
) {
    var cantidad: Int = cantidadInicial
        private set

    fun hayStock(pedida: Int): Boolean = cantidad >= pedida

    fun aplicar(movimiento: MovimientoInventario) {
        val nueva = cantidad + movimiento.cantidadEfectiva()
        check(nueva >= 0) {
            "El movimiento dejaria el stock en $nueva para ${producto.sku}"
        }
        cantidad = nueva
    }
}

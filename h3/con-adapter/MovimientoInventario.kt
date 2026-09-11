package com.applimarket.practicas_uab.h3.conadapter

class MovimientoInventario(
    val tipo: String,
    val producto: Producto,
    val cantidad: Int,
    val motivo: String,
    val usuario: String
) {
    fun cantidadEfectiva(): Int = when (tipo) {
        "ENTRADA" -> cantidad
        "SALIDA" -> -cantidad
        "AJUSTE" -> cantidad
        else -> error("Tipo de movimiento desconocido: $tipo")
    }
}

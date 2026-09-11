package com.applimarket.practicas_uab.h3.confactory

abstract class MovimientoInventario(
    val producto: Producto,
    val cantidad: Int,
    val motivo: String,
    val usuario: String
) {
    abstract val tipo: String
    abstract fun cantidadEfectiva(): Int
}

class Entrada(producto: Producto, cantidad: Int, motivo: String, usuario: String) :
    MovimientoInventario(producto, cantidad, motivo, usuario) {
    override val tipo = "ENTRADA"
    override fun cantidadEfectiva() = cantidad
}

class Salida(producto: Producto, cantidad: Int, motivo: String, usuario: String) :
    MovimientoInventario(producto, cantidad, motivo, usuario) {
    override val tipo = "SALIDA"
    override fun cantidadEfectiva() = -cantidad
}

class Ajuste(producto: Producto, cantidad: Int, motivo: String, usuario: String) :
    MovimientoInventario(producto, cantidad, motivo, usuario) {
    override val tipo = "AJUSTE"
    override fun cantidadEfectiva() = cantidad
}

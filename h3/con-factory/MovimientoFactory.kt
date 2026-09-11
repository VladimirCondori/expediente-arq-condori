package com.applimarket.practicas_uab.h3.confactory

object MovimientoFactory {

    fun crear(
        tipo: String,
        producto: Producto,
        cantidad: Int,
        motivo: String,
        usuario: String
    ): MovimientoInventario {

        require(cantidad > 0) { "La cantidad debe ser mayor a cero" }
        require(motivo.isNotBlank()) { "Todo movimiento necesita un motivo" }

        return when (tipo.uppercase()) {
            "ENTRADA" -> Entrada(producto, cantidad, motivo, usuario)
            "SALIDA"  -> Salida(producto, cantidad, motivo, usuario)
            "AJUSTE"  -> Ajuste(producto, cantidad, motivo, usuario)
            else -> error("Tipo de movimiento desconocido: $tipo")
        }
    }
}

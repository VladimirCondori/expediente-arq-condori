package com.applimarket.practicas_uab.h3.confactory

data class LineaOrdenCompra(
    val producto: String,
    val cantidad: Int,
    val precioUnitario: Double
) {
    val subtotal: Double get() = cantidad * precioUnitario
}

class OrdenCompra(
    val proveedor: String,
    val fechaEntrega: String,
    val lineas: List<LineaOrdenCompra>,
    val descuentoPorcentaje: Double,
    val observacion: String?
) {
    val subtotal: Double get() = lineas.sumOf { it.subtotal }
    val total: Double get() = subtotal * (1 - descuentoPorcentaje / 100)

    override fun toString(): String =
        "OrdenCompra(proveedor=$proveedor, lineas=${lineas.size}, total=${"%.2f".format(total)})"
}

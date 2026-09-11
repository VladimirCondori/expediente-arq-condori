package com.applimarket.practicas_uab.h3.conbuilder

data class LineaOrdenCompra(
    val producto: String,
    val cantidad: Int,
    val precioUnitario: Double
) {
    val subtotal: Double get() = cantidad * precioUnitario
}

class OrdenCompra private constructor(
    val proveedor: String,
    val fechaEntrega: String,
    val lineas: List<LineaOrdenCompra>,
    val descuentoPorcentaje: Double,
    val observacion: String?
) {
    val subtotal: Double get() = lineas.sumOf { it.subtotal }
    val total: Double get() = subtotal * (1 - descuentoPorcentaje / 100)

    override fun toString(): String =
        "OrdenCompra(proveedor=$proveedor, entrega=$fechaEntrega, " +
            "lineas=${lineas.size}, total=${"%.2f".format(total)})"

    class Builder {
        private var proveedor: String? = null
        private var fechaEntrega: String = "por confirmar"
        private val lineas = mutableListOf<LineaOrdenCompra>()
        private var descuentoPorcentaje: Double = 0.0
        private var observacion: String? = null

        fun paraProveedor(nombre: String) = apply { proveedor = nombre }

        fun conEntregaEl(fecha: String) = apply { fechaEntrega = fecha }

        fun agregarLinea(producto: String, cantidad: Int, precioUnitario: Double) =
            apply { lineas.add(LineaOrdenCompra(producto, cantidad, precioUnitario)) }

        fun conDescuento(porcentaje: Double) = apply { descuentoPorcentaje = porcentaje }

        fun conObservacion(texto: String) = apply { observacion = texto }

        fun construir(): OrdenCompra {

            val prov = proveedor
            require(!prov.isNullOrBlank()) {
                "Una orden de compra necesita un proveedor"
            }

            require(lineas.any { it.cantidad > 0 }) {
                "Una orden de compra necesita al menos una linea con cantidad mayor a cero"
            }

            return OrdenCompra(prov, fechaEntrega, lineas.toList(), descuentoPorcentaje, observacion)
        }
    }
}

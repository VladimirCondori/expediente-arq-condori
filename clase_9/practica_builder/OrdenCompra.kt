package com.applimarket.practicas_uab.h3

/**
 * H3 — Patron Builder
 * Variante: Sistema de Inventarios
 *
 * MI OBJETO COMPLEJO: la ORDEN DE COMPRA.
 * Es proveedor + varias lineas (producto, cantidad, precio) + fecha de entrega +
 * descuento + observacion. Tiene partes obligatorias, partes opcionales y una
 * cantidad variable de lineas: armarla con un constructor de cinco parametros
 * obliga a pasar nulos y deja objetos a medio llenar.
 *
 * El constructor es PRIVADO: la unica forma de obtener una OrdenCompra es pasar
 * por el guardian construir(), que valida antes de crearla.
 *
 * Nota: la fecha se maneja como String ISO ("2026-09-20") porque el proyecto
 * tiene minSdk 24 y java.time necesitaria desugaring.
 */

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

    /**
     * EL ARMADOR: 5 pasos con nombre + el guardian.
     * Cada paso devuelve el propio Builder (apply), asi se encadenan.
     */
    class Builder {

        private var proveedor: String? = null
        private var fechaEntrega: String = "por confirmar"
        private val lineas = mutableListOf<LineaOrdenCompra>()
        private var descuentoPorcentaje: Double = 0.0
        private var observacion: String? = null

        /** Paso 1 — a quien se le compra (obligatorio). */
        fun paraProveedor(nombre: String) = apply { proveedor = nombre }

        /** Paso 2 — cuando se espera la mercaderia (opcional). */
        fun conEntregaEl(fecha: String) = apply { fechaEntrega = fecha }

        /** Paso 3 — que se pide. Se puede llamar muchas veces. */
        fun agregarLinea(producto: String, cantidad: Int, precioUnitario: Double) =
            apply { lineas.add(LineaOrdenCompra(producto, cantidad, precioUnitario)) }

        /** Paso 4 — descuento negociado con el proveedor (opcional). */
        fun conDescuento(porcentaje: Double) = apply { descuentoPorcentaje = porcentaje }

        /** Paso 5 — nota para el almacenero que recibe (opcional). */
        fun conObservacion(texto: String) = apply { observacion = texto }

        /**
         * EL GUARDIAN. Nada sale de aqui a medio armar.
         */
        fun construir(): OrdenCompra {

            // Validacion 1: sin proveedor no hay a quien comprarle.
            val prov = proveedor
            require(!prov.isNullOrBlank()) {
                "Una orden de compra necesita un proveedor"
            }

            // Validacion 2: una orden sin nada que pedir no es una orden.
            require(lineas.any { it.cantidad > 0 }) {
                "Una orden de compra necesita al menos una linea con cantidad mayor a cero"
            }

            return OrdenCompra(
                proveedor = prov,
                fechaEntrega = fechaEntrega,
                lineas = lineas.toList(),
                descuentoPorcentaje = descuentoPorcentaje,
                observacion = observacion
            )
        }
    }
}

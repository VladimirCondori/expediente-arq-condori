package com.applimarket.practicas_uab.adapter

/**
 * NUESTRO CONTRATO, escrito en NUESTRO idioma.
 *
 * Sistema externo que toca el dominio: el SISTEMA DE VENTAS (POS).
 * Cuando el POS confirma una venta, esa venta tiene que convertirse en una
 * salida de inventario. Pero el POS habla de "transacciones", "item_code" y
 * "store"; nosotros hablamos de SKU, cantidad y almacen.
 *
 * El dominio solo conoce esta interfaz. Nunca ve una clase del POS.
 */

data class VentaConfirmada(
    val sku: String,
    val cantidad: Int,
    val almacen: String,
    val documentoRespaldo: String
)

interface VentasExternas {
    fun ventasPendientes(): List<VentaConfirmada>
}

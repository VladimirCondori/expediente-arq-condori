package com.applimarket.practicas_uab.h3.conadapter

data class VentaConfirmada(
    val sku: String,
    val cantidad: Int,
    val almacen: String,
    val documentoRespaldo: String
)

interface VentasExternas {
    fun ventasPendientes(): List<VentaConfirmada>
}

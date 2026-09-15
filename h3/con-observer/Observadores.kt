package com.applimarket.practicas_uab.h3.conobserver

data class CambioDeStock(
    val sku: String,
    val cantidad: Int,
    val stockMinimo: Int,
    val motivo: String
)

interface ObservadorStock {
    fun alCambiarStock(cambio: CambioDeStock)
}

class VitrinaPos : ObservadorStock {

    private val agotados = mutableSetOf<String>()

    override fun alCambiarStock(cambio: CambioDeStock) {
        if (cambio.cantidad == 0) agotados.add(cambio.sku) else agotados.remove(cambio.sku)
    }

    fun estaAgotado(sku: String) = sku in agotados
}

class AvisoCompras : ObservadorStock {

    private val sugerencias = mutableListOf<String>()

    override fun alCambiarStock(cambio: CambioDeStock) {
        if (cambio.cantidad >= cambio.stockMinimo) return
        val faltante = cambio.stockMinimo - cambio.cantidad
        sugerencias.add("Reponer ${cambio.sku}: faltan $faltante (${cambio.motivo})")
    }

    fun pendientes(): List<String> = sugerencias.toList()
}

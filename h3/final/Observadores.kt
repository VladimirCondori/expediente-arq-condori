package com.applimarket.practicas_uab.h3.fusion

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

class AvisoCompras(private val politica: PoliticaReposicion) : ObservadorStock {

    private val sugerencias = mutableListOf<String>()

    override fun alCambiarStock(cambio: CambioDeStock) {
        val aPedir = politica.cantidadAPedir(cambio.cantidad, cambio.stockMinimo)
        if (aPedir == 0) return
        sugerencias.add("Reponer ${cambio.sku}: $aPedir unidades (${cambio.motivo})")
    }

    fun pendientes(): List<String> = sugerencias.toList()
}

package com.applimarket.practicas_uab.h3.constrategy

interface PoliticaReposicion {
    fun cantidadAPedir(stockActual: Int, stockMinimo: Int): Int
}

class HastaElMinimo : PoliticaReposicion {
    override fun cantidadAPedir(stockActual: Int, stockMinimo: Int): Int =
        maxOf(0, stockMinimo - stockActual)
}

class PorLoteDelProveedor(private val lote: Int) : PoliticaReposicion {
    override fun cantidadAPedir(stockActual: Int, stockMinimo: Int): Int {
        val faltante = maxOf(0, stockMinimo - stockActual)
        if (faltante == 0) return 0
        val lotes = (faltante + lote - 1) / lote
        return lotes * lote
    }
}

class ConStockDeSeguridad : PoliticaReposicion {
    override fun cantidadAPedir(stockActual: Int, stockMinimo: Int): Int =
        maxOf(0, stockMinimo * 2 - stockActual)
}

class PlanificadorCompras(private var politica: PoliticaReposicion) {

    fun usarPolitica(nueva: PoliticaReposicion) {
        politica = nueva
    }

    fun sugerir(existencia: Existencia): Int =
        politica.cantidadAPedir(existencia.cantidad, existencia.producto.stockMinimo)
}

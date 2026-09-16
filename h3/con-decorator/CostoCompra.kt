package com.applimarket.practicas_uab.h3.condecorator

interface CostoCompra {
    fun total(): Double
    fun detalle(): String
}

class MercaderiaRecibida(private val subtotal: Double) : CostoCompra {
    override fun total(): Double = subtotal
    override fun detalle(): String = "mercaderia"
}

class ConFlete(private val costo: CostoCompra, private val monto: Double) : CostoCompra {
    override fun total(): Double = costo.total() + monto
    override fun detalle(): String = costo.detalle() + " + flete"
}

class ConSeguro(private val costo: CostoCompra, private val porcentaje: Double) : CostoCompra {
    override fun total(): Double = costo.total() * (1 + porcentaje / 100)
    override fun detalle(): String = costo.detalle() + " + seguro"
}

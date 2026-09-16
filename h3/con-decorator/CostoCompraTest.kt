package com.applimarket.practicas_uab.h3.condecorator

import org.junit.Assert.assertEquals
import org.junit.Test

class CostoCompraTest {

    private val mercaderia = MercaderiaRecibida(2700.0)

    @Test
    fun `la mercaderia sola no lleva agregados`() {
        assertEquals(2700.0, mercaderia.total(), 0.001)
        assertEquals("mercaderia", mercaderia.detalle())
    }

    @Test
    fun `compra local lleva flete`() {
        val compraLocal = ConFlete(mercaderia, 150.0)

        assertEquals(2850.0, compraLocal.total(), 0.001)
        assertEquals("mercaderia + flete", compraLocal.detalle())
        println("${compraLocal.detalle()} = Bs ${compraLocal.total()}")
    }

    @Test
    fun `compra importada lleva flete y seguro`() {
        val compraImportada = ConSeguro(ConFlete(mercaderia, 400.0), 2.0)

        assertEquals(3162.0, compraImportada.total(), 0.001)
        assertEquals("mercaderia + flete + seguro", compraImportada.detalle())
        println("${compraImportada.detalle()} = Bs ${compraImportada.total()}")
    }

    @Test
    fun `el orden de las capas cambia el total`() {
        val seguroSobreFlete = ConSeguro(ConFlete(mercaderia, 400.0), 2.0)
        val fleteSobreSeguro = ConFlete(ConSeguro(mercaderia, 2.0), 400.0)

        assertEquals(3162.0, seguroSobreFlete.total(), 0.001)
        assertEquals(3154.0, fleteSobreSeguro.total(), 0.001)
    }

    @Test
    fun `se puede repetir una capa sin crear clases`() {
        val dosFletes = ConFlete(ConFlete(mercaderia, 150.0), 80.0)

        assertEquals(2930.0, dosFletes.total(), 0.001)
        assertEquals("mercaderia + flete + flete", dosFletes.detalle())
    }
}

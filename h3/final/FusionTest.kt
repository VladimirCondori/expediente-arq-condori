package com.applimarket.practicas_uab.h3.fusion

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FusionTest {

    private val martillo = Producto("000123", "Martillo", "unidad", 20)
    private val central = Almacen("ALM-CENTRAL", "Almacen Central")

    private fun salida(cantidad: Int, motivo: String) =
        MovimientoInventario("SALIDA", martillo, cantidad, motivo, "almacenero")

    private fun entrada(cantidad: Int, motivo: String) =
        MovimientoInventario("ENTRADA", martillo, cantidad, motivo, "almacenero")

    @Test
    fun `el evento dispara el aviso y la politica calcula la cantidad`() {
        val existencia = Existencia(martillo, central, 30)
        val compras = AvisoCompras(HastaElMinimo())
        existencia.suscribir(compras)

        existencia.aplicar(salida(22, "venta mayorista"))

        assertEquals(1, compras.pendientes().size)
        assertTrue(compras.pendientes().first().contains("12 unidades"))
        println(compras.pendientes().first())
    }

    @Test
    fun `el mismo evento sugiere otra cantidad con otra politica`() {
        val existencia = Existencia(martillo, central, 30)
        val compras = AvisoCompras(PorLoteDelProveedor(25))
        existencia.suscribir(compras)

        existencia.aplicar(salida(22, "venta mayorista"))

        assertTrue(compras.pendientes().first().contains("25 unidades"))
        println(compras.pendientes().first())
    }

    @Test
    fun `si la politica pide cero no se genera aviso`() {
        val existencia = Existencia(martillo, central, 30)
        val compras = AvisoCompras(HastaElMinimo())
        existencia.suscribir(compras)

        existencia.aplicar(salida(5, "venta mostrador"))

        assertTrue(compras.pendientes().isEmpty())
    }

    @Test
    fun `los dos observadores reaccionan al mismo evento sin conocerse`() {
        val existencia = Existencia(martillo, central, 4)
        val compras = AvisoCompras(ConStockDeSeguridad())
        val vitrina = VitrinaPos()
        existencia.suscribir(compras)
        existencia.suscribir(vitrina)

        existencia.aplicar(salida(4, "venta mostrador"))

        assertTrue(vitrina.estaAgotado("000123"))
        assertTrue(compras.pendientes().first().contains("40 unidades"))

        existencia.aplicar(entrada(50, "recepcion orden 12"))
        assertFalse(vitrina.estaAgotado("000123"))
    }
}

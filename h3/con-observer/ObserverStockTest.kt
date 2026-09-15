package com.applimarket.practicas_uab.h3.conobserver

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ObserverStockTest {

    private val martillo = Producto("000123", "Martillo", "unidad", 10)
    private val central = Almacen("ALM-CENTRAL", "Almacen Central")

    private fun salida(cantidad: Int, motivo: String) =
        MovimientoInventario("SALIDA", martillo, cantidad, motivo, "almacenero")

    private fun entrada(cantidad: Int, motivo: String) =
        MovimientoInventario("ENTRADA", martillo, cantidad, motivo, "almacenero")

    @Test
    fun `compras avisa solo cuando el stock baja del minimo`() {
        val existencia = Existencia(martillo, central, 30)
        val compras = AvisoCompras()
        existencia.suscribir(compras)

        existencia.aplicar(salida(15, "venta mayorista"))
        assertTrue(compras.pendientes().isEmpty())

        existencia.aplicar(salida(8, "venta mostrador"))
        assertEquals(1, compras.pendientes().size)
        println(compras.pendientes().first())
    }

    @Test
    fun `la vitrina marca agotado y lo libera al reponer`() {
        val existencia = Existencia(martillo, central, 5)
        val vitrina = VitrinaPos()
        existencia.suscribir(vitrina)

        existencia.aplicar(salida(5, "venta mayorista"))
        assertTrue(vitrina.estaAgotado("000123"))

        existencia.aplicar(entrada(20, "recepcion orden 12"))
        assertFalse(vitrina.estaAgotado("000123"))
    }

    @Test
    fun `un mismo movimiento llega a los dos observadores`() {
        val existencia = Existencia(martillo, central, 4)
        val compras = AvisoCompras()
        val vitrina = VitrinaPos()
        existencia.suscribir(compras)
        existencia.suscribir(vitrina)

        existencia.aplicar(salida(4, "venta mostrador"))

        assertEquals(0, existencia.cantidad)
        assertTrue(vitrina.estaAgotado("000123"))
        assertEquals(1, compras.pendientes().size)
    }
}

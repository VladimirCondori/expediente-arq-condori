package com.applimarket.practicas_uab.h3.conbuilder

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class OrdenCompraBuilderTest {

    @Test
    fun `arma una orden completa encadenando los pasos`() {
        val orden = OrdenCompra.Builder()
            .paraProveedor("Ferreteria Central")
            .conEntregaEl("2026-09-20")
            .agregarLinea("Martillo", 20, 35.0)
            .agregarLinea("Taladro", 5, 400.0)
            .conDescuento(10.0)
            .conObservacion("Entregar en almacen central")
            .construir()

        assertEquals(2, orden.lineas.size)
        assertEquals(2700.0, orden.subtotal, 0.001)
        assertEquals(2430.0, orden.total, 0.001)
        println(orden)
    }

    @Test
    fun `los pasos opcionales se pueden omitir`() {
        val orden = OrdenCompra.Builder()
            .paraProveedor("Distribuidora Sur")
            .agregarLinea("Clavos 2 pulgadas", 100, 1.5)
            .construir()

        assertEquals("por confirmar", orden.fechaEntrega)
        assertEquals(150.0, orden.total, 0.001)
        println(orden)
    }

    @Test
    fun `el guardian rechaza una orden sin proveedor`() {
        val e = runCatching {
            OrdenCompra.Builder().agregarLinea("Martillo", 20, 35.0).construir()
        }.exceptionOrNull()
        assertTrue(e is IllegalArgumentException)
        println("Rechazado: ${e?.message}")
    }

    @Test
    fun `el guardian rechaza una orden sin lineas con cantidad`() {
        val e = runCatching {
            OrdenCompra.Builder()
                .paraProveedor("Ferreteria Central")
                .agregarLinea("Martillo", 0, 35.0)
                .construir()
        }.exceptionOrNull()
        assertTrue(e is IllegalArgumentException)
        println("Rechazado: ${e?.message}")
    }
}

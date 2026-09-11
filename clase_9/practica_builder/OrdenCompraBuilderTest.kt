package com.applimarket.practicas_uab.h3

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Demostracion del Builder de OrdenCompra.
 * Click derecho sobre la clase -> Run (no necesita emulador).
 */
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
        assertEquals(2700.0, orden.subtotal, 0.001)   // 20*35 + 5*400
        assertEquals(2430.0, orden.total, 0.001)      // menos 10%
        println(orden)
    }

    @Test
    fun `los pasos opcionales se pueden omitir`() {
        val orden = OrdenCompra.Builder()
            .paraProveedor("Distribuidora Sur")
            .agregarLinea("Clavos 2 pulgadas", 100, 1.5)
            .construir()

        assertEquals("por confirmar", orden.fechaEntrega)
        assertEquals(0.0, orden.descuentoPorcentaje, 0.001)
        assertEquals(150.0, orden.total, 0.001)
        println(orden)
    }

    @Test
    fun `el guardian rechaza una orden sin proveedor`() {
        val error = runCatching {
            OrdenCompra.Builder()
                .agregarLinea("Martillo", 20, 35.0)
                .construir()
        }.exceptionOrNull()

        assertTrue(error is IllegalArgumentException)
        println("Rechazado: ${error?.message}")
    }

    @Test
    fun `el guardian rechaza una orden sin lineas con cantidad`() {
        val error = runCatching {
            OrdenCompra.Builder()
                .paraProveedor("Ferreteria Central")
                .agregarLinea("Martillo", 0, 35.0)
                .construir()
        }.exceptionOrNull()

        assertTrue(error is IllegalArgumentException)
        println("Rechazado: ${error?.message}")
    }

    @Test
    fun `un builder arma varias ordenes distintas`() {
        val builder = OrdenCompra.Builder().paraProveedor("Ferreteria Central")

        val primera = builder.agregarLinea("Martillo", 10, 35.0).construir()
        val segunda = builder.agregarLinea("Taladro", 2, 400.0).construir()

        // La primera no se modifica: construir() copia las lineas con toList().
        assertEquals(1, primera.lineas.size)
        assertEquals(2, segunda.lineas.size)
        println("primera=$primera / segunda=$segunda")
    }
}

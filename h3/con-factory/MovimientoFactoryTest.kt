package com.applimarket.practicas_uab.h3.confactory

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MovimientoFactoryTest {

    private val martillo = Producto("000123", "Martillo", "unidad", 10)
    private val central = Almacen("ALM-CENTRAL", "Almacen Central")

    @Test
    fun `la fabrica devuelve el tipo correcto y la entrada suma`() {
        val m = MovimientoFactory.crear("ENTRADA", martillo, 20, "compra", "almacenero")
        assertTrue(m is Entrada)
        assertEquals(20, m.cantidadEfectiva())
    }

    @Test
    fun `la salida resta`() {
        val m = MovimientoFactory.crear("SALIDA", martillo, 5, "venta", "almacenero")
        assertTrue(m is Salida)
        assertEquals(-5, m.cantidadEfectiva())
    }

    @Test
    fun `un tipo desconocido no se fabrica`() {
        val e = runCatching {
            MovimientoFactory.crear("REGALO", martillo, 1, "x", "almacenero")
        }.exceptionOrNull()
        assertTrue(e is IllegalStateException)
        println("Rechazado: ${e?.message}")
    }

    @Test
    fun `la fabrica valida cantidad y motivo`() {
        val e1 = runCatching {
            MovimientoFactory.crear("ENTRADA", martillo, 0, "compra", "almacenero")
        }.exceptionOrNull()
        val e2 = runCatching {
            MovimientoFactory.crear("ENTRADA", martillo, 5, "", "almacenero")
        }.exceptionOrNull()
        assertTrue(e1 is IllegalArgumentException)
        assertTrue(e2 is IllegalArgumentException)
    }

    @Test
    fun `el stock se mueve con lo que fabrica la factory`() {
        val ex = Existencia(martillo, central, 0)
        ex.aplicar(MovimientoFactory.crear("ENTRADA", martillo, 20, "compra", "almacenero"))
        ex.aplicar(MovimientoFactory.crear("SALIDA", martillo, 5, "venta", "almacenero"))
        assertEquals(15, ex.cantidad)
        println("stock final: ${ex.cantidad}")
    }
}

package com.applimarket.practicas_uab.periodo

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Demostracion del Singleton `PeriodoContable`.
 * Click derecho sobre la clase -> Run para ejecutar (no necesita emulador).
 */
class PeriodoContableTest {

    @Before
    fun antesDeCadaPrueba() {
        // Necesario porque el Singleton conserva su estado entre pruebas.
        PeriodoContable.reiniciarParaPruebas("2026-09")
    }

    @Test
    fun `todo movimiento cae en el unico periodo abierto`() {
        PeriodoContable.registrar(Movimiento("Martillo", 20))
        PeriodoContable.registrar(Movimiento("Taladro", 5))

        assertEquals("2026-09", PeriodoContable.mes)
        assertEquals(2, PeriodoContable.cantidadMovimientos())
        println("Los 2 movimientos quedaron en el periodo ${PeriodoContable.mes}")
    }

    @Test
    fun `no se puede abrir un segundo periodo sin cerrar el primero`() {
        val error = runCatching { PeriodoContable.abrir("2026-10") }.exceptionOrNull()

        assertTrue(error is IllegalStateException)
        assertEquals("2026-09", PeriodoContable.mes)
        println("Rechazado: ${error?.message}")
    }

    @Test
    fun `el cierre de mes cierra uno y abre el siguiente`() {
        PeriodoContable.registrar(Movimiento("Martillo", 20))

        PeriodoContable.cerrar()
        PeriodoContable.abrir("2026-10")

        assertEquals("2026-10", PeriodoContable.mes)
        assertEquals(0, PeriodoContable.cantidadMovimientos())
        println("Periodo 2026-09 cerrado, 2026-10 abierto")
    }

    @Test
    fun `un periodo cerrado no acepta movimientos`() {
        PeriodoContable.cerrar()

        val error = runCatching {
            PeriodoContable.registrar(Movimiento("Taladro", 5))
        }.exceptionOrNull()

        assertTrue(error is IllegalStateException)
        println("Rechazado: ${error?.message}")
    }

    /**
     * Donde empieza el antipatron: sin el reinicio del @Before, esta prueba
     * heredaria los movimientos de las anteriores. El Singleton guarda estado
     * global, y el estado global hace que las pruebas dependan del orden en que
     * se ejecutan.
     */
    @Test
    fun `el singleton obliga a reiniciar el estado en cada prueba`() {
        assertEquals(0, PeriodoContable.cantidadMovimientos())
        assertTrue(PeriodoContable.abierto)
    }
}

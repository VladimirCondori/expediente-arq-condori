package com.applimarket.practicas_uab.adapter

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Demostracion del Adapter.
 * Click derecho sobre la clase -> Run (no necesita emulador).
 */
class PosVentasAdapterTest {

    private fun adaptador(vararg tx: PosTransaction) =
        PosVentasAdapter(PosVentasApi(tx.toList()))

    @Test
    fun `traduce el idioma del POS al nuestro`() {
        val ventas = adaptador(
            PosTransaction("TXN-001", "P-000123", "3", 1, "COMPLETED")
        ).ventasPendientes()

        val venta = ventas.first()
        assertEquals("000123", venta.sku)            // item_code -> sku
        assertEquals(3, venta.cantidad)              // texto -> numero
        assertEquals("ALM-CENTRAL", venta.almacen)   // tienda 1 -> almacen
        assertEquals("TXN-001", venta.documentoRespaldo)
        println(venta)
    }

    @Test
    fun `las ventas anuladas no llegan al inventario`() {
        val ventas = adaptador(
            PosTransaction("TXN-001", "P-000123", "3", 1, "COMPLETED"),
            PosTransaction("TXN-002", "P-000456", "5", 1, "VOID")
        ).ventasPendientes()

        assertEquals(1, ventas.size)
        assertEquals("TXN-001", ventas.first().documentoRespaldo)
    }

    @Test
    fun `cada tienda del POS se traduce a su almacen`() {
        val ventas = adaptador(
            PosTransaction("TXN-001", "P-000123", "1", 1, "COMPLETED"),
            PosTransaction("TXN-002", "P-000123", "2", 2, "COMPLETED")
        ).ventasPendientes()

        assertEquals("ALM-CENTRAL", ventas[0].almacen)
        assertEquals("ALM-SUR", ventas[1].almacen)
    }

    @Test
    fun `una tienda desconocida no entra al inventario`() {
        val error = runCatching {
            adaptador(
                PosTransaction("TXN-009", "P-000123", "3", 99, "COMPLETED")
            ).ventasPendientes()
        }.exceptionOrNull()

        assertTrue(error is IllegalStateException)
        println("Rechazado: ${error?.message}")
    }

    @Test
    fun `una cantidad que no es numero no entra al inventario`() {
        val error = runCatching {
            adaptador(
                PosTransaction("TXN-010", "P-000123", "tres", 1, "COMPLETED")
            ).ventasPendientes()
        }.exceptionOrNull()

        assertTrue(error is IllegalStateException)
        println("Rechazado: ${error?.message}")
    }
}

package com.applimarket.practicas_uab.h3.conadapter

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PosVentasAdapterTest {

    private fun adaptador(vararg tx: PosTransaction) =
        PosVentasAdapter(PosVentasApi(tx.toList()))

    @Test
    fun `traduce el idioma del POS al nuestro`() {
        val venta = adaptador(
            PosTransaction("TXN-001", "P-000123", "3", 1, "COMPLETED")
        ).ventasPendientes().first()

        assertEquals("000123", venta.sku)
        assertEquals(3, venta.cantidad)
        assertEquals("ALM-CENTRAL", venta.almacen)
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
        val e = runCatching {
            adaptador(PosTransaction("TXN-009", "P-000123", "3", 99, "COMPLETED")).ventasPendientes()
        }.exceptionOrNull()
        assertTrue(e is IllegalStateException)
        println("Rechazado: ${e?.message}")
    }

    @Test
    fun `una cantidad que no es numero no entra al inventario`() {
        val e = runCatching {
            adaptador(PosTransaction("TXN-010", "P-000123", "tres", 1, "COMPLETED")).ventasPendientes()
        }.exceptionOrNull()
        assertTrue(e is IllegalStateException)
        println("Rechazado: ${e?.message}")
    }
}

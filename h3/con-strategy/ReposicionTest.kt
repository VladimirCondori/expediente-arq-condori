package com.applimarket.practicas_uab.h3.constrategy

import org.junit.Assert.assertEquals
import org.junit.Test

class ReposicionTest {

    private val martillo = Producto("000123", "Martillo", "unidad", 20)
    private val central = Almacen("ALM-CENTRAL", "Almacen Central")
    private val existencia = Existencia(martillo, central, 8)

    @Test
    fun `hasta el minimo pide solo lo que falta`() {
        val planificador = PlanificadorCompras(HastaElMinimo())
        assertEquals(12, planificador.sugerir(existencia))
    }

    @Test
    fun `por lote redondea al lote del proveedor`() {
        val planificador = PlanificadorCompras(PorLoteDelProveedor(25))
        assertEquals(25, planificador.sugerir(existencia))
    }

    @Test
    fun `con stock de seguridad pide el doble del minimo`() {
        val planificador = PlanificadorCompras(ConStockDeSeguridad())
        assertEquals(32, planificador.sugerir(existencia))
    }

    @Test
    fun `cambiar la politica cambia la sugerencia sin tocar el resto`() {
        val planificador = PlanificadorCompras(HastaElMinimo())
        assertEquals(12, planificador.sugerir(existencia))

        planificador.usarPolitica(PorLoteDelProveedor(25))
        assertEquals(25, planificador.sugerir(existencia))
    }

    @Test
    fun `si hay stock suficiente ninguna politica pide nada`() {
        val lleno = Existencia(martillo, central, 40)
        assertEquals(0, PlanificadorCompras(HastaElMinimo()).sugerir(lleno))
        assertEquals(0, PlanificadorCompras(PorLoteDelProveedor(25)).sugerir(lleno))
    }
}

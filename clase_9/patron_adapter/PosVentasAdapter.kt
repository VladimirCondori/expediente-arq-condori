package com.applimarket.practicas_uab.adapter

/**
 * EL TRADUCTOR.
 *
 * Es la unica clase del sistema que entiende el idioma del POS. Si el POS
 * cambia sus nombres, o si manana se reemplaza por otro proveedor, solo se
 * toca este archivo: el dominio ni se entera.
 *
 * Ademas hace de aduana: lo que no se puede traducir no entra al inventario.
 */
class PosVentasAdapter(
    private val api: PosVentasApi,
    private val almacenPorTienda: Map<Int, String> = mapOf(
        1 to "ALM-CENTRAL",
        2 to "ALM-SUR"
    )
) : VentasExternas {

    override fun ventasPendientes(): List<VentaConfirmada> =
        api.fetchTransactions()
            .filter { it.status == "COMPLETED" }   // las anuladas no mueven stock
            .map { traducir(it) }

    private fun traducir(tx: PosTransaction): VentaConfirmada {

        val cantidad = tx.qty_sold.toIntOrNull()
            ?: error("El POS envio una cantidad no numerica en ${tx.txn_id}: '${tx.qty_sold}'")

        val almacen = almacenPorTienda[tx.store]
            ?: error("El POS envio la tienda ${tx.store}, que no corresponde a ningun almacen")

        return VentaConfirmada(
            sku = tx.item_code.removePrefix("P-"),   // su codigo -> nuestro SKU
            cantidad = cantidad,
            almacen = almacen,
            documentoRespaldo = tx.txn_id            // respaldo para la trazabilidad
        )
    }
}

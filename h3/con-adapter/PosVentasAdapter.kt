package com.applimarket.practicas_uab.h3.conadapter

class PosVentasAdapter(
    private val api: PosVentasApi,
    private val almacenPorTienda: Map<Int, String> = mapOf(
        1 to "ALM-CENTRAL",
        2 to "ALM-SUR"
    )
) : VentasExternas {

    override fun ventasPendientes(): List<VentaConfirmada> =
        api.fetchTransactions()
            .filter { it.status == "COMPLETED" }
            .map { traducir(it) }

    private fun traducir(tx: PosTransaction): VentaConfirmada {

        val cantidad = tx.qty_sold.toIntOrNull()
            ?: error("El POS envio una cantidad no numerica en ${tx.txn_id}: '${tx.qty_sold}'")

        val almacen = almacenPorTienda[tx.store]
            ?: error("El POS envio la tienda ${tx.store}, que no corresponde a ningun almacen")

        return VentaConfirmada(
            sku = tx.item_code.removePrefix("P-"),
            cantidad = cantidad,
            almacen = almacen,
            documentoRespaldo = tx.txn_id
        )
    }
}

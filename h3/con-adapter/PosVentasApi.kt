package com.applimarket.practicas_uab.h3.conadapter

data class PosTransaction(
    val txn_id: String,
    val item_code: String,
    val qty_sold: String,
    val store: Int,
    val status: String
)

class PosVentasApi(private val transacciones: List<PosTransaction>) {
    fun fetchTransactions(): List<PosTransaction> = transacciones
}

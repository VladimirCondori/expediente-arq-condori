package com.applimarket.practicas_uab.adapter

/**
 * CLASE FALSA que simula el sistema externo.
 *
 * Representa codigo ajeno que NO podemos modificar: nombres en ingles, con
 * guion bajo, cantidades como texto y la tienda como numero. Asi es como llega
 * la informacion desde afuera, nos guste o no.
 */

data class PosTransaction(
    val txn_id: String,
    val item_code: String,   // "P-000123"
    val qty_sold: String,    // "3" — viene como texto
    val store: Int,          // 1, 2 ...
    val status: String       // "COMPLETED" | "VOID"
)

class PosVentasApi(private val transacciones: List<PosTransaction>) {
    fun fetchTransactions(): List<PosTransaction> = transacciones
}

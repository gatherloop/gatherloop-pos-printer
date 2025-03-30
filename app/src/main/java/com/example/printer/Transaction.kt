package com.example.printer

data class Transaction(
    val createdAt: String,
    val paidAt: String?,
    val name: String,
    val items: Array<TransactionItem>
) {
    fun getTotal(): Int {
        var total = 0
        items.forEach { item -> total += item.price * item.amount - item.discountAmount }
        return total
    }
}
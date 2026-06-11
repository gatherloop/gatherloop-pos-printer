package com.example.printer

enum class PrintType {
    INVOICE,
    ORDER_SLIP,
    PURCHASE_LIST
}

data class PrintRequest(
    val type: PrintType,
    val transaction: Transaction,
    val purchaseList: PurchaseList
)
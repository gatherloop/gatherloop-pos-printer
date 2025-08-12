package com.example.printer

enum class PrintType {
    INVOICE,
    ORDER_SLIP
}

data class PrintRequest(
    val type: PrintType,
    val transaction: Transaction
)
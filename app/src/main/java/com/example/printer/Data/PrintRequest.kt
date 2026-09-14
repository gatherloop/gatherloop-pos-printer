package com.example.printer.Data

enum class PrintType {
    INVOICE,
    ORDER_SLIP,
    PURCHASE_LIST,
    CHECKIN_SLIP
}

data class PrintRequest(
    val type: PrintType,
    val transaction: Transaction,
    val orderSlip: OrderSlipPayload,
    val purchaseList: PurchaseList,
    val checkin: Checkin
)
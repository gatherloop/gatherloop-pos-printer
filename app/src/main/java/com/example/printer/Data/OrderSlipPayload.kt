package com.example.printer.Data

data class OrderSlipPayloadItemsData(
    val name: String,
    val amount: Int,
    val note: String
){

}

data class OrderSlipPayloadItems(
    val bars: Array<OrderSlipPayloadItemsData>,
    val kitchens:Array<OrderSlipPayloadItemsData>
){

}

data class OrderSlipPayload(
    val createdAt: String,
    val paidAt: String?,
    val name: String,
    val transactionNumber: Int,
    val pagerNumber: Int,
    val items: OrderSlipPayloadItems,
) {

}
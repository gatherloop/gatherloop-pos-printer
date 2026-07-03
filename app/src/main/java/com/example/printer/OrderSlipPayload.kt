package com.example.printer

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
    val orderNumber: Int,
    val items: OrderSlipPayloadItems,
) {

}
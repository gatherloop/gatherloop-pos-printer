package com.example.printer.Data

data class PurchaseList (
    val stockCheckDate: String,
    val totalEstimatedCost: Int,
    val supplierNames: Array<String>,
    val items: Array<PurchaseListItem>
)
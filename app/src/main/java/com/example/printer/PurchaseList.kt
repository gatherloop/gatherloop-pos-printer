package com.example.printer

data class PurchaseList (
    val stockCheckDate: String,
    val totalEstimatedCost: Int,
    val supplierNames: Array<String>,
    val items: Array<PurchaseListItem>
)
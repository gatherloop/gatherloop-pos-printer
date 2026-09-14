package com.example.printer.Data

data class PurchaseListItem (
    val materialName: String,
    val purchaseQuantity: Int,
    val purchaseUnit: String,
    val estimatedCost: Int,
    val supplierName: String,
)
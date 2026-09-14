package com.example.printer.View

import com.example.printer.Align
import com.example.printer.Data.PurchaseList
import com.example.printer.Printer

class PurchaseListNote(val printer: Printer, val purchaseList: PurchaseList) {
    fun print() {
        printer.feed()
        printer.feed()

        printer.printTextTwoColumn("Tanggal", purchaseList.stockCheckDate)
        printer.printTextTwoColumn("Total", (purchaseList.totalEstimatedCost).toString())
        printer.printTextLine("------------------------------", Align.CENTER)

        purchaseList.supplierNames.forEach { supplierName ->
            printer.printTextLine("${supplierName} :", Align.LEFT, true)
            printer.feed()
            purchaseList.items.forEach { item ->
                if (item.supplierName == supplierName) {
                    printer.printTextLine("${item.materialName} x ${item.purchaseQuantity} ${item.purchaseUnit}", Align.LEFT)
                    printer.feed()
                }
            }
        }

        printer.feed()
        printer.feed()
    }
}
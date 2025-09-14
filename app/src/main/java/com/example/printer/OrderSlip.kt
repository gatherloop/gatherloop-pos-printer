package com.example.printer

class OrderSlip(val printer: Printer, val transaction: Transaction) {
    fun print() {
        printer.feed()
        printer.feed()

        printer.printTextTwoColumn("Nama", transaction.name)
        printer.printTextTwoColumn("No. Order", transaction.orderNumber.toString())
        printer.printTextTwoColumn("Waktu Transaksi", transaction.createdAt)
        printer.printTextLine("------------------------------", Align.CENTER)

        transaction.items.forEach { item ->
            printer.printTextLine("${item.name} x ${item.amount}", Align.LEFT, true)
            printer.feed()
        }

        printer.feed()
        printer.feed()
    }
}
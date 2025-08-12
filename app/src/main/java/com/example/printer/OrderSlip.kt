package com.example.printer

class OrderSlip(val printer: Printer, val transaction: Transaction) {
    fun print() {
        printer.printTextLine("Nama", Align.CENTER, true)
        printer.printTextLine(transaction.name, Align.CENTER)
        printer.printTextLine("Waktu Transaksi", Align.CENTER, true)
        printer.printTextLine(transaction.createdAt, Align.CENTER)
        printer.printTextLine("------------------------------", Align.CENTER)

        transaction.items.forEach { item ->
            printer.printTextLine(item.name, Align.LEFT, true)
            printer.feed()
        }

        printer.feed()
        printer.feed()
        printer.feed()
    }
}
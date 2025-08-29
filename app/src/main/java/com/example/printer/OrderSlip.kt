package com.example.printer

class OrderSlip(val printer: Printer, val transaction: Transaction) {
    fun print() {
        printer.feed()
        printer.feed()

        printer.printTextLine("Nama", Align.CENTER, )
        printer.printTextLine(transaction.name, Align.CENTER, true)
        printer.printTextLine("Waktu Transaksi", Align.CENTER, )
        printer.printTextLine(transaction.createdAt, Align.CENTER, true)
        printer.printTextLine("------------------------------", Align.CENTER)

        transaction.items.forEach { item ->
            printer.printTextLine("${item.name} x ${item.amount}", Align.LEFT, true)
            printer.feed()
        }

        printer.feed()
        printer.feed()
    }
}
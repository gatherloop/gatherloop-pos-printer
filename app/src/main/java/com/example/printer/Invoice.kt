package com.example.printer

class Invoice(val printer: Printer, val transaction: Transaction) {
    fun print() {
        printer.printTextLine("Gatherloop Cafe & Community", Align.CENTER, true)
        printer.printTextLine("New Kraksaan Land, Blok G16", Align.CENTER)
        printer.printTextLine("Kraksaan, Probolinggo", Align.CENTER)
        printer.printTextLine("Instagram @gatherloop", Align.CENTER)

        printer.printTextLine("------------------------------", Align.CENTER)
        printer.printTextLine("Waktu Transaksi", Align.CENTER, true)
        printer.printTextLine(transaction.createdAt, Align.CENTER)

        if (transaction.paidAt != null) {
            printer.printTextLine("Waktu Pembayaran", Align.CENTER, true)
            printer.printTextLine(transaction.paidAt, Align.CENTER)
        }
        printer.printTextLine("------------------------------", Align.CENTER)


        transaction.items.forEach { item ->
            val subtotal = item.price * item.amount
            printer.printTextLine(item.name, Align.LEFT, true)
            printer.printTextTwoColumn("Rp. ${item.price} x ${item.amount}", "Rp. ${subtotal}")


            if (item.discountAmount > 0) {
                printer.printTextTwoColumn("Diskon", "- Rp. ${item.discountAmount}")
                printer.printTextTwoColumn("", "Rp. ${subtotal - item.discountAmount}")
            }
            printer.feed()
        }

        printer.printTextTwoColumn("Total", "Rp. ${transaction.getTotal()}")
        printer.feed()

        printer.printTextLine("------------------------------", Align.CENTER)
        printer.printTextLine("Terimakasih Kak ${transaction.name}", Align.CENTER)
        printer.feed()
        printer.feed()
        printer.feed()
    }
}
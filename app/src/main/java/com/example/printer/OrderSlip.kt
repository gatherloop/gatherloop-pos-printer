package com.example.printer

class OrderSlip(val printer: Printer, val orderSlip: OrderSlipPayload) {
    fun print() {
        printer.feed()
        printer.feed()

        printer.printTextTwoColumn("Nama", orderSlip.name)
        printer.printTextTwoColumn("No. Order", orderSlip.orderNumber.toString())
        printer.printTextTwoColumn("Waktu Transaksi", orderSlip.createdAt)
        printer.printTextLine("------------------------------", Align.CENTER)


        if (orderSlip.items.bars.isNotEmpty()) {
            printer.feed()
            printer.printTextLine("BAR", Align.CENTER)
            orderSlip.items.bars.forEach { item ->
                printer.printTextLine("${item.name} x ${item.amount}", Align.LEFT, true)

                if (item.note != "") {
                    printer.printTextLine(item.note, Align.LEFT, true)
                }

                printer.feed()
            }
        }


        if (orderSlip.items.kitchens.isNotEmpty()) {
            printer.feed()
            printer.printTextLine("KITCHEN", Align.CENTER)
            orderSlip.items.kitchens.forEach { item ->
                printer.printTextLine("${item.name} x ${item.amount}", Align.LEFT, true)

                if (item.note != "") {
                    printer.printTextLine(item.note, Align.LEFT, true)
                }

                printer.feed()
            }
        }

        printer.feed()
        printer.feed()
    }
}
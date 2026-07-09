package com.example.printer

class CheckinSlip(val printer: Printer, val checkin: Checkin) {
    fun print() {
        printer.feed()
        printer.feed()

        printer.printTextLine("Gatherloop Board Game Cafe", Align.CENTER, true)
        printer.printTextLine("New Kraksaan Land, Blok G16", Align.CENTER)
        printer.printTextLine("Kraksaan, Probolinggo", Align.CENTER)
        printer.printTextLine("Instagram & Tiktok @gatherloop", Align.CENTER)

        printer.printTextLine("------------------------------", Align.CENTER)
        printer.printTextTwoColumn("Nama", checkin.name)
        printer.printTextTwoColumn("Waktu Checkin", checkin.createdAt)
        printer.printTextTwoColumn("Jumlah Pemain", checkin.tickets.size.toString())
        printer.printTextLine("------------------------------", Align.CENTER)

        checkin.tickets.forEach { ticket ->
            printer.printTextLine("${ticket.name} - ${ticket.variant}", Align.LEFT, true)
            printer.feed()
        }

        printer.feed()
        printer.feed()
    }
}
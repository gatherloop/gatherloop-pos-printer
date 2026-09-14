package com.example.printer.View

import com.example.printer.Align
import com.example.printer.Data.Checkin
import com.example.printer.Printer

class CheckinSlip(val printer: Printer, val checkin: Checkin) {
    fun print() {
        printer.feed()
        printer.feed()

        printer.printTextLine("Gatherloop Board Game Cafe", Align.CENTER, true)
        printer.printTextLine("New Kraksaan Land, Blok G16", Align.CENTER)
        printer.printTextLine("Instagram & Tiktok @gatherloop", Align.CENTER)

        printer.printTextLine("------------------------------", Align.CENTER)
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
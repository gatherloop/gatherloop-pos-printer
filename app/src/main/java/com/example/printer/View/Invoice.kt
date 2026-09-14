package com.example.printer.View

import com.example.printer.Align
import com.example.printer.Data.Transaction
import com.example.printer.Printer

class Invoice(val printer: Printer, val transaction: Transaction) {
    fun print() {
        printer.printTextLine("Gatherloop Board Game Cafe", Align.CENTER, true)
        printer.printTextLine("New Kraksaan Land, Blok G16", Align.CENTER)
        printer.printTextLine("Instagram & Tiktok @gatherloop", Align.CENTER)

        printer.printTextLine("------------------------------", Align.CENTER)
        printer.printTextTwoColumn("Nomor", "#${transaction.transactionNumber}")
        printer.printTextTwoColumn("Waktu", transaction.createdAt)
        printer.printTextTwoColumn("Nama", transaction.name)
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

        if (transaction.coupons.isNotEmpty()) {
            printer.printTextTwoColumn("Subtotal", "Rp. ${transaction.getTotal()}")
            printer.feed()
            printer.printTextLine("Diskon")
            transaction.coupons.forEachIndexed { index, coupon ->
                printer.printTextTwoColumn(coupon.code, "- Rp. ${transaction.getDiscountAmountAtIndex(index)}")
            }
            printer.feed()
        }

        printer.printTextTwoColumn("Total", "Rp. ${transaction.getTotalWithDiscount()}")

        if (!transaction.isCashless) {
            printer.printTextTwoColumn("Uang", "Rp. ${transaction.paidAmount}")
            printer.printTextTwoColumn("Kembalian", "Rp. ${transaction.getChangeMoney()}")
        }

        printer.feed()
        printer.feed()
    }
}
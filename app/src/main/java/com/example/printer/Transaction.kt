package com.example.printer

data class Transaction(
    val createdAt: String,
    val paidAt: String?,
    val name: String,
    val items: Array<TransactionItem>,
    val coupons: Array<TransactionCoupon>,
    val isCashless: Boolean,
    val paidAmount: Int,
) {
    fun getTotal(): Int {
        var total = 0
        items.forEach { item -> total += item.price * item.amount - item.discountAmount }
        return total
    }

    fun getTotalWithDiscount(): Int {
        var total = getTotal()
        coupons.forEach { coupon -> total -= coupon.getDiscountAmount(total) }
        return total
    }

    fun getDiscountAmountAtIndex(index: Int): Int {
        val targetCoupon = coupons[index]

        var total = getTotal()
        for (i in 0..<index) {
            val currentCoupon = coupons[i]
            total -= currentCoupon.getDiscountAmount(total)
        }

        return targetCoupon.getDiscountAmount(total)
    }

    fun getChangeMoney(): Int {
        return paidAmount - getTotalWithDiscount()
    }
}
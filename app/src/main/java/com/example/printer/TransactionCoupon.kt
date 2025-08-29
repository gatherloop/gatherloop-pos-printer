package com.example.printer

import kotlin.math.round

enum class CouponType {
    FIXED,
    PERCENTAGE
}

data class TransactionCoupon (
    var code: String,
    var type: CouponType,
    var amount: Int
) {
    fun getDiscountAmount(transactionTotal: Int): Int {
        if (type == CouponType.FIXED) {
            return amount
        } else if (type == CouponType.PERCENTAGE) {
            val discountAmount = transactionTotal * amount / 100
            return (round(discountAmount / 500.0) * 500).toInt()
        } else {
            return 0
        }
    }
}
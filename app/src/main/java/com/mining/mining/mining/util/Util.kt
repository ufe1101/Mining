package com.mining.mining.mining.util

import java.util.*

fun getRandomAmount(random: Random): String {
    var randomAmount = random.nextFloat() * 13.33f
    if (randomAmount < 1) {
        randomAmount = 1f
    }

    var amount = randomAmount.toString()

    if (amount.length > 10) {
        amount = amount.substring(0, 9)
    }

    return amount
}
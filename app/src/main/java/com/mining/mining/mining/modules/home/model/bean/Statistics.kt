package com.mining.mining.mining.modules.home.model.bean

data class Statistics(
        var date: Long = 0,
        var ticker: Ticker = Ticker()
)

data class Ticker(
        var buy: String = "",
        var buy_amount: String = "",
        var open: String = "",
        var high: String = "",
        var last: String = "",
        var low: String = "",
        var sell: String = "",
        var sell_amount: String = "",
        var vol: String = ""
)
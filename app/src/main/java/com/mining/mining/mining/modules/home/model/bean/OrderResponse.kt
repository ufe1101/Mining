package com.mining.mining.mining.modules.home.model.bean

data class OrderResponse(
        var amount: String = "",
        var avg_price: String = "",
        var create_time: Int = 0,
        var deal_amount: String = "",
        var deal_fee: String = "",
        var deal_money: String = "",
        var id: Int = 0,
        var left: String = "",
        var maker_fee_rate: String = "",
        var market: String = "",
        var order_type: String = "",
        var price: String = "",
        var source_id: String = "",
        var status: String = "",
        var taker_fee_rate: String = "",
        var type: String = ""
)
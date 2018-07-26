package com.mining.mining.mining.modules.home.model.bean

data class OrderBody(
        var access_id: String = "",
        var amount: String = "",
        var price: String = "",
        var type: String = "",
        var market: String = "",
        var tonce: Long = 0,
        var source_id: String = ""
)
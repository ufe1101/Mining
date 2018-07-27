package com.mining.mining.mining.modules.home.model.bean

import com.mining.mining.mining.util.ACCESS_ID

data class OrderBody(
        var access_id: String = ACCESS_ID,
        var amount: String = "2",
        var price: String = "",
        var type: String = "buy",
        var market: String = "",
        var tonce: Long = System.currentTimeMillis(),
        var source_id: String = "123"
)
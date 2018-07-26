package com.mining.mining.mining.modules.home.model.bean

data class Mining(
        var detail: List<Any> = listOf(),
        var total_mining: TotalMining = TotalMining()
)

data class TotalMining(
        var amount: String = "",
        var coin_type: String = ""
)
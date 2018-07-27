package com.mining.mining.mining.modules.home.model.bean

data class Mining(
        var detail: List<MiningDetail> = listOf(),
        var total_mining: TotalMining = TotalMining()
)

data class TotalMining(
        var amount: String = "",
        var coin_type: String = ""
)


data class MiningDetail(
    var end_time: Int = 0,
    var mining_amount: MiningAmount = MiningAmount(),
    var mining_diffculty: MiningDiffculty = MiningDiffculty(),
    var my_mining_diffculty: MyMiningDiffculty = MyMiningDiffculty(),
    var start_time: Int = 0
)

data class MiningDiffculty(
    var amount: String = "",
    var coin_type: String = ""
)

data class MiningAmount(
    var amount: String = "",
    var coin_type: String = ""
)

data class MyMiningDiffculty(
    var amount: String = "",
    var coin_type: String = ""
)
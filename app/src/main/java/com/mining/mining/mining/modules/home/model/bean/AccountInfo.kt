package com.mining.mining.mining.modules.home.model.bean

data class AccountInfo(
        var BCH: BCH = BCH(),
        var BTC: BTC = BTC(),
        var ETH: ETH = ETH(),
        var CET: CET = CET(),
        var USDT: USDT = USDT()
)

data class BCH(
        var available: String = "",
        var frozen: String = ""
)

data class BTC(
        var available: String = "",
        var frozen: String = ""
)

data class ETH(
        var available: String = "",
        var frozen: String = ""
)

data class USDT(
        var available: String = "",
        var frozen: String = ""
)

data class CET(
        var available: String = "",
        var frozen: String = ""
)
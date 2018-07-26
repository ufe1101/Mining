package com.mining.mining.mining.modules.home.model.bean

data class Depth(
        var last: String = "",
        var asks: List<List<String>> = listOf(),
        var bids: List<List<String>> = listOf()
)
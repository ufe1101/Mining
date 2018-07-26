package com.mining.mining.mining.modules.home.model.bean

data class Reponse<T> (
        var code: Int = 0,
        var message: String = "",
        var data: T
)

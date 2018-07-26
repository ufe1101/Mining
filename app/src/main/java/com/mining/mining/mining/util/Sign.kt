package com.mining.mining.mining.util

import android.net.Uri
import com.coinex.trade.base.extension.md5
import okhttp3.Request

fun genSign(request: Request): String{

    val map: MutableMap<String, String> = mutableMapOf()

    when(request.method()) {
        "GET" -> {
            val query = request.url().query()
            val array = query?.split("&")
            array?.forEach {
                val pair = it.split("=")
                map[pair[0]] = pair[1]
            }
        }
        "POST" -> {

        }
        else -> {
            return ""
        }

    }

    val sortedKeys = map.keys.sorted()
    val builder = Uri.Builder()
            .scheme(SCHEME)
            .authority(HOST)

    sortedKeys.forEach {
        builder.appendQueryParameter(it, map[it])
    }
    builder.appendQueryParameter("secret_key", SECRET_KEY)

    val uri = builder.build()
    return uri.query.md5().toUpperCase()
}
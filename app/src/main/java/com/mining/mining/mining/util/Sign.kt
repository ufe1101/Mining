package com.mining.mining.mining.util

import android.net.Uri
import com.coinex.trade.base.extension.md5
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Request

fun genSign(request: Request): String{

    val map: MutableMap<String, String> = mutableMapOf()
    map.clear()
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
            val jsonObj: JsonObject = Gson().fromJson<JsonObject>(request.body().toString(),
                    JsonObject::class.java)
            jsonObj.keySet().forEach {
                map[it] = jsonObj[it].asString
            }
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
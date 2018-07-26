package com.coinex.trade.base.extension

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import java.io.Serializable
import java.math.BigInteger
import java.security.MessageDigest

fun String?.isEmpty(): Boolean = this.isNullOrEmpty() || this?.isBlank() ?: true
        || this?.equals("null", true) ?: true

fun Any.log(message: String) {
    Log.d(this.javaClass.simpleName, message)
}

fun Any.loge(message: String) {
    Log.e(this.javaClass.simpleName, message)
}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun ImageView.loadUrl(url: String) {
    Glide.with(this.context)
            .load(url)
            .into(this)
}

fun Map<String, Any>.toBundle(): Bundle {
    val bundle = Bundle()
    this.forEach { entry ->
        when (entry.value) {
            is Int -> bundle.putInt(entry.key, entry.value as Int)
            is String -> bundle.putString(entry.key, entry.value as String)
            is Long -> bundle.putLong(entry.key, entry.value as Long)
            is Short -> bundle.putShort(entry.key, entry.value as Short)
            is Float -> bundle.putFloat(entry.key, entry.value as Float)
            is Double -> bundle.putDouble(entry.key, entry.value as Double)
            is Byte -> bundle.putByte(entry.key, entry.value as Byte)
            is Parcelable -> bundle.putParcelable(entry.key, entry.value as Parcelable)
            is Serializable -> bundle.putSerializable(entry.key, entry.value as Serializable)
        }
    }
    return bundle
}

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}


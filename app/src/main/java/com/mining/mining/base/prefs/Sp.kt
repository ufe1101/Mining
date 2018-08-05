package com.mining.mining.base.prefs

import android.content.Context
import android.content.SharedPreferences
import com.mining.mining.base.extension.isEmpty
import java.lang.ref.WeakReference
import java.util.*

/**
 * Created by zhaolin on 18/7/25.
 */
class Sp private constructor(context: Context, spName: String) {

    private var mSpName: String = if (spName.isEmpty()) COINEX_SP else spName
    private var mSp: SharedPreferences = context.getSharedPreferences(mSpName, Context.MODE_PRIVATE)
    private var mSpEditor: SharedPreferences.Editor = mSp.edit()

    fun read(): SharedPreferences{
        return mSp
    }

    fun get(key: String, default: Any): Any {
        when (default) {
            is Int ->  return mSp.getInt(key, default)
            is String -> return mSp.getString(key, default)
            is Long ->  return mSp.getLong(key, default)
            is Boolean -> return mSp.getBoolean(key, default)
            is Float -> return mSp.getFloat(key, default)
        }
        return default
    }

    fun get(key: String, default: Set<String>): Set<String> {
        return mSp.getStringSet(key, default)
    }

    fun put(key: String, value: Any) {
        when (value) {
            is Int -> mSpEditor.putInt(key, value).apply()
            is String -> mSpEditor.putString(key, value).apply()
            is Long -> mSpEditor.putLong(key, value).apply()
            is Boolean -> mSpEditor.putBoolean(key, value).apply()
            is Float -> mSpEditor.putFloat(key, value).apply()
        }
    }

    fun put(key: String, value: Set<String>) {
        mSpEditor.putStringSet(key, value).apply()
    }

    companion object {

        private val COINEX_SP = "coinexSp"
        private val spCache = LinkedHashMap<String, WeakReference<Sp>>()

        @JvmOverloads
        fun from(context: Context, name: String = ""): Sp {

            val cacheKey = if (name.isEmpty()) "default" else name

            var cacheSpRef: WeakReference<Sp>? = spCache[cacheKey]
            var targetSp: Sp? = cacheSpRef?.get()
            if (targetSp == null) {
                targetSp = Sp(context, name)
                cacheSpRef = WeakReference(targetSp)
                spCache[cacheKey] = cacheSpRef
            }
            return targetSp
        }
    }

}

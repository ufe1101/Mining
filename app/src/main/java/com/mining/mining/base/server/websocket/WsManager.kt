package com.mining.mining.base.server.websocket

import com.mining.mining.base.util.AppModule
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.retry.LinearBackoffStrategy
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object WsManager {

    private val DURATION_MILLIS = 5000L
    private const val BASE_URL = "wss://socket.coinex.com/"
    private var scarlet : Scarlet

    init {

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

        val backoffStrategy = LinearBackoffStrategy(DURATION_MILLIS)

        scarlet = Scarlet.Builder()
                .webSocketFactory(okHttpClient.newWebSocketFactory(BASE_URL))
                .addMessageAdapterFactory(GsonMessageAdapter.Factory())
                .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
                .backoffStrategy(backoffStrategy)
                .lifecycle(AndroidLifecycle.ofApplicationForeground(AppModule.provideApplication()))
                .build()
    }

    fun <T> getApi(tClass: Class<T>): T {
        return scarlet.create(tClass)
    }

}
package com.mining.mining.base.server.websocket

object WsManager {

   /* private val DURATION_MILLIS = 5000L
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
    }*/

}
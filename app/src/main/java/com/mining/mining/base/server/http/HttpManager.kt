package com.mining.mining.base.server.http

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.mining.mining.mining.util.signHttp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object HttpManager {

    private val DEFAULT_TIMEOUT = 10L
    private val BASE_URL = "https://api.coinex.com/v1/"
    private var retrofit : Retrofit

    init {
        val httpClientBuilder = OkHttpClient.Builder()
                .addInterceptor{
                    val request = it.request().newBuilder()
                            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36")
                            .addHeader("authorization", signHttp(it.request()))
                            .build()

       it.proceed(request)
                }
                .addNetworkInterceptor(StethoInterceptor())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)

        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClientBuilder.build())
                .build()
    }

    fun <T> getApi(tClass: Class<T>): T {
        return retrofit.create(tClass)
    }

}
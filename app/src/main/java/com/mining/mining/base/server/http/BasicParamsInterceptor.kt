package com.mining.mining.base.server.http

import com.mining.mining.mining.util.ACCESS_ID
import okhttp3.Interceptor
import okhttp3.Response

class BasicParamsInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()

        val wrapperBuilder = originRequest.url()
                .newBuilder()
                .scheme(originRequest.url().scheme())
                .host(originRequest.url().host())
                .addQueryParameter(PARAM_ACCESS_ID, ACCESS_ID)
                .addQueryParameter(PARAM_TONCE, System.currentTimeMillis().toString())

        val processedRequest = originRequest.newBuilder()
                .method(originRequest.method(), originRequest.body())
                .url(wrapperBuilder.build())
                .build()
        return chain.proceed(processedRequest)

    }

    companion object {
        val PARAM_ACCESS_ID = "access_id"
        val PARAM_TONCE = "tonce"
    }
}
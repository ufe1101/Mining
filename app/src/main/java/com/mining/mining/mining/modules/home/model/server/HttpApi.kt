package com.mining.mining.mining.modules.home.model.server

import com.mining.mining.mining.modules.home.model.bean.*
import io.reactivex.Observable
import retrofit2.http.*

interface HttpApi {

    /**
     * Inquire Account Info
     */
    @GET("balance/info")
    fun requestAccountInfo(@Query("access_id") access_id: String,
                           @Query("tonce") tonce: String): Observable<Reponse<AccountInfo>>

    /**
     * Acquire Mining Difficulty
     */
    @GET("order/mining/difficulty")
    fun requestDifficulty(@Query("access_id") access_id: String,
                          @Query("tonce") tonce: String): Observable<Reponse<MiningDifficulty>>

    /**
     * Acquire Market Depth
     */
    @GET("market/depth")
    fun requestMarketDepth(@Query("access_id") access_id: String,
                           @Query("tonce") tonce: String,
                           @Query("market") market: String,
                           @Query("merge") merge: String,
                           @Query("limit") limit: Int = 5): Observable<Reponse<Depth>>

    /**
     * Acquire Market List
     */
    @GET("market/list")
    fun requestMarketList(@Query("access_id") access_id: String,
                          @Query("tonce") tonce: String): Observable<Reponse<List<String>>>


    /**
     * Place Limit Order
     */
    @POST("order/limit")
    fun requestLimitOrder(@Body orderBody: OrderBody): Observable<Reponse<OrderResponse>>


    /**
     * Cancel Order
     */
    @DELETE("order/pending")
    fun requestCancelOrder(@Query("access_id") access_id: String,
                           @Query("tonce") tonce: String,
                           @Query("id") id: Int,
                           @Query("market") market: String): Observable<Reponse<OrderResponse>>

    /**
     * Acquire Market Statistics
     */
    @GET("market/ticker")
    fun requestMarketStatistics(@Query("access_id") access_id: String,
                                @Query("tonce") tonce: String,
                                @Query("market") market: String): Observable<Reponse<Statistics>>

}
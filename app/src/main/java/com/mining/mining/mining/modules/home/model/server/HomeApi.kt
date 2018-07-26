package com.mining.mining.mining.modules.home.model.server

import com.mining.mining.mining.modules.home.model.bean.AccountInfo
import com.mining.mining.mining.modules.home.model.bean.Depth
import com.mining.mining.mining.modules.home.model.bean.MiningDifficulty
import com.mining.mining.mining.modules.home.model.bean.Reponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

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



}
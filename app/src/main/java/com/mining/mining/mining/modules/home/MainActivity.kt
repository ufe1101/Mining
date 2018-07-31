package com.mining.mining.mining.modules.home

import android.os.Bundle
import com.coinex.trade.base.extension.log
import com.coinex.trade.base.extension.loge
import com.coinex.trade.base.extension.toast
import com.mining.mining.R
import com.mining.mining.base.server.RequestManager
import com.mining.mining.mining.modules.home.model.bean.OrderBody
import com.mining.mining.mining.modules.home.model.server.HomeApi
import com.mining.mining.mining.util.ACCESS_ID
import com.mining.mining.mining.util.SUCCESS
import com.mining.mining.mining.util.getRandomAmount
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : RxAppCompatActivity() {

    private val api = RequestManager.getApi(HomeApi::class.java)
    private val random = Random(1)
    private var factor: Float = 12.39f
    private lateinit var dispose: Disposable
    private val history = mutableListOf<Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Observable.interval(0,15, TimeUnit.MINUTES)
                .bindUntilEvent(this, ActivityEvent.DESTROY)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe{
                    requestDifficulty()
                }

        bt1.setOnClickListener {
            Observable.interval(5, TimeUnit.SECONDS)
                    .doOnDispose { log("Unsubscribing subscription") }
                    .bindUntilEvent(this, ActivityEvent.DESTROY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({
                        requestMarketStatistics("CETUSDT")
                    }, {

                    }, {

                    }, {
                        dispose = it
                    })
        }

        bt2.setOnClickListener {
            if (!dispose.isDisposed) {
                dispose.dispose()
            }
        }
    }

    private fun requestMarketStatistics(market: String) {
        api.requestMarketStatistics(ACCESS_ID, System.currentTimeMillis().toString(), market)
                .bindUntilEvent(this, ActivityEvent.DESTROY)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    log(it.toString())
                    if (it.code == SUCCESS) {
                        val buy1price = it.data.ticker.buy
                        val sell1price = it.data.ticker.sell
                        val meanPrice = (buy1price.toDouble() + sell1price.toDouble()) / 2

                        val amount = getRandomAmount(random, factor)
                        log(amount)

                        val average = history.average()
                        val diff = meanPrice - average
                        val normal = Math.abs(diff) / average < 0.005

                        if(history.size > 5) {
                            history.removeAt(0)
                            history.add(meanPrice)
                        } else {
                            history.add(meanPrice)
                        }

                        if (normal) {
                            requestLimitOrder(OrderBody(price = meanPrice.toString(), type = "buy", market = market, amount = amount))
                            requestLimitOrder(OrderBody(price = meanPrice.toString(), type = "sell", market = market, amount = amount))

                        } else {
                            log("anomaly meanPrice = $meanPrice" )
//                            if(diff > 0) {
//                                requestLimitOrder(OrderBody(price = meanPrice.toString(), type = "sell", market = market, amount = amount))
//                            } else {
//                                requestLimitOrder(OrderBody(price = meanPrice.toString(), type = "buy", market = market, amount = amount))
//                            }
                        }

                    } else {
                        loge(it?.message ?: "error")
                    }

                }, {
                    loge(it.toString())
                })
    }

    private fun requestCancelOrder(id: Int, market: String) {
        api.requestCancelOrder(ACCESS_ID, System.currentTimeMillis().toString(), id, market)
                .bindUntilEvent(this, ActivityEvent.DESTROY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    log(it.toString())
                    tv1.text = it.toString()
                    if (it.code == SUCCESS) {

                    } else {
                        toast(it?.message ?: "error")
                    }

                }, {
                    toast(it?.message ?: "error")
                    loge(it.toString())
                })
    }

    private fun requestLimitOrder(orderBody: OrderBody) {
        api.requestLimitOrder(orderBody)
                .bindUntilEvent(this, ActivityEvent.DESTROY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    log(it.toString())
                    tv1.text = it.toString()
                    if (it.code == SUCCESS) {

                    } else {
                        loge(it.toString())
                    }

                }, {
                    loge(it.toString())
                })
    }

    private fun requestMarketList() {
        api.requestMarketList(ACCESS_ID, System.currentTimeMillis().toString())
                .bindUntilEvent(this, ActivityEvent.DESTROY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    log(it.toString())
                    tv1.text = it.toString()
                    if (it.code == SUCCESS) {

                    } else {
                        toast(it?.message ?: "error")
                    }

                }, {
                    toast(it?.message ?: "error")
                    loge(it.toString())
                })
    }

    private fun requestDepth() {
        api.requestMarketDepth(ACCESS_ID, System.currentTimeMillis().toString(), "CETUSDT", "0.0000001")
                .bindUntilEvent(this, ActivityEvent.DESTROY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    log(it.toString())
                    tv1.text = it.toString()
                    if (it.code == SUCCESS) {

                    } else {
                        toast(it?.message ?: "error")
                    }

                }, {
                    toast(it?.message ?: "error")
                    loge(it.toString())
                })
    }

    private fun requestDifficulty() {
        api.requestDifficulty(ACCESS_ID, System.currentTimeMillis().toString())
                .bindUntilEvent(this, ActivityEvent.DESTROY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    log(it.toString())
                    if (it.code == SUCCESS) {

                        factor = it.data.difficulty.toFloat() / 9.25f * 12.39f

                        tv1.text = it.toString()
                    } else {
                        loge(it?.message ?: "error")
                    }

                }, {
                    loge(it.toString())
                })
    }

    private fun requestAccountInfo() {
        api.requestAccountInfo(ACCESS_ID, System.currentTimeMillis().toString())
                .bindUntilEvent(this, ActivityEvent.DESTROY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    log(it.toString())
                    tv1.text = it.toString()

                    if (it.code == SUCCESS) {

                    } else {
                        toast(it?.message ?: "error")
                    }

                }, {
                    toast(it?.message ?: "error")
                    loge(it.toString())
                })
    }



}

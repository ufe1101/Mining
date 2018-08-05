package com.mining.mining.mining.modules.home

import android.os.Bundle
import com.mining.mining.R
import com.mining.mining.base.extension.isEmpty
import com.mining.mining.base.extension.log
import com.mining.mining.base.extension.loge
import com.mining.mining.base.extension.toast
import com.mining.mining.base.prefs.Sp
import com.mining.mining.base.server.http.HttpManager
import com.mining.mining.base.util.AppModule
import com.mining.mining.mining.modules.home.model.bean.OrderBody
import com.mining.mining.mining.modules.home.model.server.HttpApi
import com.mining.mining.mining.util.ACCESS_ID
import com.mining.mining.mining.util.SUCCESS
import com.mining.mining.mining.util.getRandomAmount
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : RxAppCompatActivity() {

    private val DEFAULT_X = 1.319331f
    private val httpApi = HttpManager.getApi(HttpApi::class.java)
    private val random = Random(1)
    private var y: Float = 0f
    private val history = mutableListOf<Double>()
    private var x = DEFAULT_X

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        x = Sp.from(AppModule.provideContext()).read().getFloat("x", DEFAULT_X)

        tv_factor.text = x.toString()
        et1.setText(x.toString())

        bt1.setOnClickListener {
            placeOrder("CETUSDT")
        }

        bt_ok.setOnClickListener {
            val inputText = et1.text.toString()
            if(inputText.isEmpty()) {
                toast("Please input y!")
            } else {
                tv_factor.text = inputText
                x = inputText.toFloat()
                Sp.from(this).put("x", x)

                requestDifficulty()
            }
        }
    }

    private fun placeOrder(market: String) {
        Observable.interval(5, TimeUnit.SECONDS)
                .flatMap { httpApi.requestMarketStatistics(ACCESS_ID, System.currentTimeMillis().toString(), market) }
                .bindUntilEvent(this, ActivityEvent.DESTROY)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    log(it.toString())
                    if (it.code == SUCCESS) {
                        val buy1price = it.data.ticker.buy
                        val sell1price = it.data.ticker.sell
                        val meanPrice = (buy1price.toDouble() + sell1price.toDouble()) / 2

                        val amount = getRandomAmount(random, y)
                        log(amount)

                        val average = history.average()
                        val diff = meanPrice - average
                        val normal = Math.abs(diff) / average < 0.003

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
                        }

                    } else {
                        loge(it?.message ?: "error")
                    }

                }, {
                    loge(it.toString())
                })
    }

    private fun requestCancelOrder(id: Int, market: String) {
        httpApi.requestCancelOrder(ACCESS_ID, System.currentTimeMillis().toString(), id, market)
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
        httpApi.requestLimitOrder(orderBody)
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
        httpApi.requestMarketList(ACCESS_ID, System.currentTimeMillis().toString())
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
        httpApi.requestMarketDepth(ACCESS_ID, System.currentTimeMillis().toString(), "CETUSDT", "0.0000001")
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
        Observable.interval(0,15, TimeUnit.MINUTES)
                .flatMap { httpApi.requestDifficulty(ACCESS_ID, System.currentTimeMillis().toString()) }
                .bindUntilEvent(this, ActivityEvent.DESTROY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    log(it.toString())
                    if (it.code == SUCCESS) {
                        y = it.data.difficulty.toFloat() * x
                        tv1.text = it.toString()
                    } else {
                        loge(it?.message ?: "error")
                    }

                }, {
                    loge(it.toString())
                })
    }

    private fun requestAccountInfo() {
        httpApi.requestAccountInfo(ACCESS_ID, System.currentTimeMillis().toString())
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

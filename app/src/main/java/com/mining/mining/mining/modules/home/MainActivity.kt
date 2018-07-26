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
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : RxAppCompatActivity() {

    private var api = RequestManager.getApi(HomeApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //requestMarketStatistics("CETBCH")
    }

    private fun requestMarketStatistics(market: String) {
        api.requestMarketStatistics(ACCESS_ID, System.currentTimeMillis().toString(), market)
                .bindToLifecycle(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    log(it.toString())
                    tv1.text = it.toString()
                    if (it.code == SUCCESS) {
                        val buy1price = it.data.ticker.buy
                        val sell1price = it.data.ticker.sell

                        requestLimitOrder(OrderBody(price = buy1price))

                    } else {
                        toast(it?.message ?: "error")
                    }

                }, {
                    toast(it?.message ?: "error")
                    loge(it.toString())
                })
    }

    private fun requestCancelOrder(id: Int, market: String) {
        api.requestCancelOrder(ACCESS_ID, System.currentTimeMillis().toString(), id, market)
                .bindToLifecycle(this)
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
                .bindToLifecycle(this)
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

    private fun requestMarketList() {
        api.requestMarketList(ACCESS_ID, System.currentTimeMillis().toString())
                .bindToLifecycle(this)
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
                .bindToLifecycle(this)
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
                .bindToLifecycle(this)
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

    private fun requestAccountInfo() {
        api.requestAccountInfo(ACCESS_ID, System.currentTimeMillis().toString())
                .bindToLifecycle(this)
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

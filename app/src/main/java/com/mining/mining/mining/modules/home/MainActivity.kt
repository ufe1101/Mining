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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestDifficulty()
    }

    private fun requestLimitOrder(orderBody: OrderBody) {
        val api = RequestManager.getApi(HomeApi::class.java)
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
        val api = RequestManager.getApi(HomeApi::class.java)
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
        val api = RequestManager.getApi(HomeApi::class.java)
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
        val api = RequestManager.getApi(HomeApi::class.java)
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
        val api = RequestManager.getApi(HomeApi::class.java)
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

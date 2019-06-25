package iam.lfc.myretrofitcache

import android.support.multidex.MultiDexApplication
import iam.lfc.myretrofitcache.utils.network.NetStateReceiver

/**
 * Created by LFC
on 2019/6/18.
 */
class MyAPP : MultiDexApplication() {

    companion object MyAPP {
        lateinit var instance: iam.lfc.myretrofitcache.MyAPP
            internal set
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        initConfig()
    }

    private fun initConfig() {
        NetStateReceiver.registerNetworkStateReceiver(this)

    }


}
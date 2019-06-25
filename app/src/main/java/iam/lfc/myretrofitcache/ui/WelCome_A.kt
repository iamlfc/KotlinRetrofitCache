package iam.lfc.myretrofitcache.ui

import android.os.Bundle
import android.view.animation.AnimationUtils
import com.gyf.immersionbar.ImmersionBar
import iam.lfc.myretrofitcache.R
import iam.lfc.myretrofitcache.base.BaseMain
import iam.lfc.myretrofitcache.utils.LgU
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_wel_come.*
import java.util.concurrent.TimeUnit


class WelCome_A : BaseMain() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wel_come)
        SetToolBarVisiabale(false)
        ImmersionBar.with(this).transparentBar().init()
        Countdown()
    }

    /**
     * 60 s 轮询 一次
     */
    var disposable: Disposable? = null  //   全局变量

    /**
     * 倒计时
     */
    private fun Countdown() {
//        第一次发送延迟，间隔时间，时间单位
        disposable = Observable.interval(0, 1, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())//切换到主线程修改UI
                .subscribe {
                    LgU.d("rxTime", it.toString())
                    if (it == 3000L) {
                        disposable!!.dispose()
                        MainActivity.EnterThis(this)
                        overridePendingTransition(R.anim.enter_animation, R.anim.exit_animation)
                        finish()
                        return@subscribe //使用标记跳出方法
                    } else if (it == 1500L) {
                        img_welcom.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation))

                    }
                }

    }
}


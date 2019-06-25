package iam.lfc.myretrofitcache.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import iam.lfc.myretrofitcache.R
import iam.lfc.myretrofitcache.base.BaseMain
import iam.lfc.myretrofitcache.share.Params
import iam.lfc.myretrofitcache.share.eventmessage.MessageEvent
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus


class MainActivity : BaseMain() {
    companion object {
        fun EnterThis(context: Context, string: String = "", int: Int = 0) {
            var intent = Intent(context, MainActivity().javaClass)
            intent.putExtra("Title", string)
            intent.putExtra("Time", int)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        SetToolBarVisiabale(false)
//        mImmersionBar?.titleBar(toolbar_main)?.init()
        mImmersionBar?.titleBar(toolbar_main)
                ?.statusBarDarkFont(false, 0.2f)
                ?.init()
        btn_1.setOnClickListener(this)
        btn_2.setOnClickListener(this)
        btn_3.setOnClickListener(this)
        btn_4.setOnClickListener(this)
        tv_5.setOnClickListener(this)
        tv_6.setOnClickListener(this)
        tv_7.setOnClickListener(this)
        tv_8.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v!!.id) {
            R.id.btn_1 -> {
                ListDemo_A.EnterThis(baseContext)
            }
            R.id.btn_2 -> {

//                Main2Activity.EnterThis(baseContext)
                RetrofitDemo_A.EnterThis(baseContext)

            }
            R.id.btn_3 -> showToast("按钮3")
            R.id.btn_4 -> showToast("按钮4")
            R.id.tv_5 -> showToast("按钮5")
            R.id.tv_6 -> showToast("按钮6")
            R.id.tv_7 -> showToast("按钮7")
            R.id.tv_8 -> showToast("按钮8")

        }
    }

    override fun finish() {
        super.finish()
//        overridePendingTransition(R.anim.enter_animation, R.anim.exit_animation)

    }

    override fun onEventBus(message: MessageEvent) {
        super.onEventBus(message)
        when (message.name) {
            Params.EB_NetFailed2Main -> {
                if (message.type == 1) ShowTopAlaret() else showToast("我也收到消息了~")
                EventBus.getDefault().removeStickyEvent(message)

            }
        }

    }

    /*  @Subscribe(sticky = true)
      open fun onStickyEventBus(message: MessageEvent) {
          // 在这里已经接收到消息 我加判断是为了实现群发单实现的效果
          val str_name = message.name
          if (TextUtils.isEmpty(str_name)) {
              return
          }
          LgU.d("onStickyEventBus " + str_name)
          when (str_name) {
              Params.EB_NetFailed2Main -> if (message.type == 1) ShowTopAlaret() else showToast("我也收到消息了~")
          }


      }*/
}

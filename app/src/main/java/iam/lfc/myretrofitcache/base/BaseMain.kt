package iam.lfc.myretrofitcache.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.provider.Settings
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.flyco.animation.BounceEnter.BounceEnter
import com.flyco.animation.SlideExit.SlideBottomExit
import com.gyf.immersionbar.ImmersionBar
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import iam.lfc.myretrofitcache.R
import iam.lfc.myretrofitcache.share.Params
import iam.lfc.myretrofitcache.share.eventmessage.MessageEvent
import iam.lfc.myretrofitcache.ui.WelCome_A
import iam.lfc.myretrofitcache.utils.ActivityStack
import iam.lfc.myretrofitcache.utils.DynamicTimeFormat
import iam.lfc.myretrofitcache.utils.LUtils
import iam.lfc.myretrofitcache.utils.LgU
import iam.lfc.myretrofitcache.utils.network.NetChangeObserver
import iam.lfc.myretrofitcache.utils.network.NetStateReceiver
import iam.lfc.myretrofitcache.utils.network.NetUtils
import iam.lfc.myretrofitcache.widget.WaitDialog
import iam.lfc.myretrofitcache.widget.alerterTop.Alerter
import kotlinx.android.synthetic.main.activity_base_main.*
import kotlinx.android.synthetic.main.content_base_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.text.SimpleDateFormat
import java.util.*

open class BaseMain : AppCompatActivity(), View.OnClickListener {
    /**
     * 上下文context
     */
    var baseContext: Activity = Activity()
    var mImmersionBar: ImmersionBar? = null

    /**
     * 分页加载页数
     */
    var pageNum = 1
    /**
     * Dialog
     */
    var mWaitDialog: WaitDialog? = null
    /**
     *  网络是否可用
     *
     */
    var ISNetWork = false
    var mClassicsHeader: ClassicsHeader? = null
    //    public ClassicsFooter mClassicsFooter;
    var mClassicsFooter: BallPulseFooter? = null

    var DialogIn = BounceEnter()
    var DialogOut = SlideBottomExit()

    private var imm: InputMethodManager? = null


    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.img_base_back, R.id.tv_base_back ->
                onBackPressed()
        }
    }

    override fun setContentView(view: View) {
        if (rootLayout == null)
            return
        rootLayout.addView(view, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))


    }

    override fun setContentView(layoutId: Int) {
        setContentView(View.inflate(this, layoutId, null))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base_main)
        baseContext = this
        initView()
        initConfig()
        /* fab.setOnClickListener { view ->
             Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                     .setAction("Action", null).show()
         }*/
    }


    private fun initView() {
//        setSupportActionBar(toolbar)

        initToolBar()
        initImmerBar()
        initSmartRefresh()
    }

    /**
     *  初始化 状态栏设置
     *  isdark  true  设置状态栏 字体黑色  false  白色
     */
    private fun initImmerBar(isDark: Boolean = true) {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.titleBar(toolbar)
                ?.statusBarDarkFont(isDark, 0.2f)
                ?.init()
    }

    private fun initSmartRefresh() {

        mClassicsHeader = ClassicsHeader(this)
        mClassicsFooter = BallPulseFooter(this)

        val deta = Random().nextInt(7 * 24 * 60 * 60 * 1000)
        mClassicsHeader?.setLastUpdateTime(Date(System.currentTimeMillis() - deta))
        mClassicsHeader?.setTimeFormat(SimpleDateFormat("更新于 MM-dd HH:mm", Locale.CHINA))
        mClassicsHeader?.setTimeFormat(DynamicTimeFormat("更新于 %s"))
        mClassicsHeader?.setEnableLastTime(true)
        mClassicsHeader?.setSpinnerStyle(SpinnerStyle.Translate)
//        mClassicsHeader.setAccentColor(getResources().getColor(R.color.base_text));//设置强调颜色
        mClassicsHeader?.setPrimaryColor(resources.getColor(R.color.white))//设置主题颜色

        mClassicsFooter?.setAnimatingColor(resources.getColor(R.color.main))
        mClassicsFooter?.setNormalColor(resources.getColor(R.color.blue))
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if (actionBar != null)
            actionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        tv_base_back.setOnClickListener(this)
        img_base_back.setOnClickListener(this)

    }

    fun SetToolBarVisiabale(isShow: Boolean) {
        lay_appbar.visibility = View.VISIBLE
        lay_appbar.visibility = View.GONE
        lay_appbar.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    fun ChangeTitle(strTitle: String?, strRight1: String? = "") {
        tv_base_title.text = strTitle
        if (!TextUtils.isEmpty(strRight1)) {
            tv_base_right.visibility = View.VISIBLE
            tv_base_right.setText(strRight1)
        } else
            tv_base_right.visibility = View.GONE
    }

    private fun initConfig() {
        ActivityStack.getScreenManager().pushActivity(this)

        //订阅EventBus这个必须写
        EventBus.getDefault().register(this)
        initNetWork()
    }

    private fun initNetWork() {

        //开启广播去监听 网络 改变事件
        NetStateReceiver.registerObserver(object : NetChangeObserver {
            override fun onNetConnected(type: NetUtils.NetType?) {
                if (type != null) {
                    onNetworkConnected(type)
                }
            }

            override fun onNetDisConnect() {
                onNetworkDisConnected()
            }
        })

    }

    protected fun onNetworkConnected(type: NetUtils.NetType) {
        LgU.e("有网了！ " + type)
        if (!Alerter.isShowing()) {
            Alerter.hide()
        }
    }

    protected fun onNetworkDisConnected() {
        // TODO: 2017/8/5  这个地方 需要修改一下 一些页面不需要网络监听
        LgU.e("断网了！ ")
        if (ActivityStack.getScreenManager().currentActivity().javaClass.equals(WelCome_A::class.java)) {
            EventBus.getDefault().postSticky(MessageEvent(name = Params.EB_NetFailed2Main, typeValue = 1))
            return
        }
//        LgU.d("onNetworkDisConnected" + ActivityStack.getScreenManager().currentActivity().javaClass.toString())
        ShowTopAlaret()
    }

    open fun ShowTopAlaret() {
        Alerter.create(ActivityStack.getScreenManager().currentActivity())
                .setTitle("提示")
                .setText("当前网络异常，请您检查网络设置")
                .setIcon(R.drawable.alerter_ic_notifications)
                .setBackgroundColorRes(R.color.prompt_warning)
                .setDuration(5 * 1000)
                .enableSwipeToDismiss()
                .setOnClickListener {
                    val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                    startActivity(intent)
                    Alerter.hide()
                }
                .setOnHideListener {
                    //    TODO("这个地方需要注意,enableSwipeToDismiss 不触发这回调！ ")
                    LgU.d("弹框 藏起来了")
                    EventBus.getDefault().post(MessageEvent(name = Params.EB_NetFailed2Main, typeValue = 2))

                }
                ?.show()
    }

    /**
     * 扩展 吐司
     */
    fun Context.showToast(strMsg: String?) {

        if (TextUtils.isEmpty(strMsg)) {
            return
        } else {
            Toast.makeText(this@BaseMain, strMsg, Toast.LENGTH_SHORT).show()
        }
    }

    fun showDialog(isCancle: Boolean = true, strMsg: String? = resources.getString(R.string.loadingmess)) {
        if (mWaitDialog == null) {
            mWaitDialog = WaitDialog.setDialog(baseContext
                    , if (TextUtils.isEmpty(strMsg)) resources.getString(R.string.loadingmess) else strMsg
                    , null)
        }
        mWaitDialog?.setCancelable(isCancle)
        mWaitDialog?.show()
    }

    fun hideDialog() {
        if (mWaitDialog != null && mWaitDialog?.isShowing()!!) {
            mWaitDialog?.dismiss()
        }
    }

    private var mActivityJumpTag: String? = null
    private var mActivityJumpTime: Long = 0

    /**
     * 检查当前 Activity 是否重复跳转了，不需要检查则重写此方法并返回 true 即可
     *
     * @param intent 用于跳转的 Intent 对象
     * @return 检查通过返回true, 检查不通过返回false
     */
    protected fun startActivitySelfCheck(intent: Intent): Boolean {
        // 默认检查通过
        var result = true
        // 标记对象
        val tag: String?
        if (intent.getComponent() != null) { // 显式跳转
            tag = intent.getComponent()!!.getClassName()
        } else if (intent.getAction() != null) { // 隐式跳转
            tag = intent.getAction()
        } else {
            return result
        }

        if (tag == mActivityJumpTag && mActivityJumpTime >= SystemClock.uptimeMillis() - 1000) {
            // 检查不通过
            result = false
        }

        // 记录启动标记和时间
        mActivityJumpTag = tag
        mActivityJumpTime = SystemClock.uptimeMillis()
        return result
    }

    override fun finish() {
        super.finish()
        hideSoftKeyBoard()
    }

    fun hideSoftKeyBoard() {
        val localView = currentFocus
        if (this.imm == null) {
            this.imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        }
        if (localView != null && this.imm != null) {
            this.imm?.hideSoftInputFromWindow(localView.windowToken, 2)
        }
    }

    /**
     * 动态隐藏软键盘
     *
     * @param context 上下文
     * @param edit    输入框
     */
    fun hideSoftInput(context: Context, edit: EditText) {
        edit.clearFocus()
        val inputmanger = context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputmanger.hideSoftInputFromWindow(edit.windowToken, 0)
    }

    @Subscribe(sticky = true)
    //    @Subscribe(threadMode = ThreadMode.MAIN)
    //    @Subscribe(threadMode = ThreadMode.POSTING)
    open fun onEventBus(message: MessageEvent) {
        // 在这里已经接收到消息 我加判断是为了实现群发单实现的效果
        val str_name = message.name
        LgU.d("收到 回调消息" + str_name + "  " + message.type)
        if (TextUtils.isEmpty(str_name)) {
            return
        }
        /* */
        /**
         * 我真他妈的是一个小精灵鬼！
         *//*
        if (TextUtils.equals(str_name, EB_ExitLogin)) {
            val cla = javaClass
            LgU.d("当前activity ：" + cla.toString())
            if (cla == MainActivity::class.java || cla == Login_A::class.java) {
            } else {
                finish()
            }
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        ActivityStack.getScreenManager().popActivity(this)
        LUtils.hideToask()

    }


}

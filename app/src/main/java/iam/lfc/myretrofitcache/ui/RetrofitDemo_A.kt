package iam.lfc.myretrofitcache.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import iam.lfc.myretrofitcache.Beans.HttpResult
import iam.lfc.myretrofitcache.Beans.LoginM
import iam.lfc.myretrofitcache.Beans.ProjectFirstM
import iam.lfc.myretrofitcache.R
import iam.lfc.myretrofitcache.RetrofitUtils.RetrofitUtils
import iam.lfc.myretrofitcache.RetrofitUtils.cache.EnhancedCall
import iam.lfc.myretrofitcache.RetrofitUtils.cache.MyCallBack
import iam.lfc.myretrofitcache.base.BaseMain
import iam.lfc.myretrofitcache.share.Params
import iam.lfc.myretrofitcache.utils.LgU
import kotlinx.android.synthetic.main.activity_retrofit_demo.*
import okhttp3.ResponseBody
import java.util.*

class RetrofitDemo_A : BaseMain() {
    //  private var StrValue = ""
    //  private var Type = 0
    companion object {
        fun EnterThis(context: Context, string: String = "", int: Int = 0) {
            var intent = Intent(context, RetrofitDemo_A().javaClass)
            intent.putExtra("Title", string)
            intent.putExtra("Time", int)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_demo)

        //title = intent!!.getStringExtra("Title")
        // Type = intent!!.getIntExtra("Time", 0)
        initView();
        //initData();
        getData();

    }

    private fun initView() {
        ChangeTitle("网络请求测试")
        /* btnLogin.setOnClickListener(this)
         btn_01.setOnClickListener(this)
         btn_02.setOnClickListener(this)
         btn_03.setOnClickListener(this)
         btn_04.setOnClickListener(this)*/
    }

    /* override fun onClick(v: View?) {
         super.onClick(v)
         when (v?.id) {
             R.id.btnLogin -> Login_Request()
             R.id.btn_01 -> Request1()
             R.id.btn_02 -> Request2()
             R.id.btn_03 -> Request3()
             R.id.btn_04 -> Request4()
             else -> {
             }
         }


     }*/

    fun OnClickCallback(v: View) {
        when (v?.id) {
            R.id.btnLogin -> Login_Request()
            R.id.btn_01 -> Request1()
            R.id.btn_02 -> Request2()
            R.id.btn_03 -> Request3()
            R.id.btn_04 -> Request4()
            else -> {
            }
        }
    }

    /**
     * 登录操作
     */
    private fun Login_Request() {
        val loginMap = HashMap<String, String>()
        loginMap.put("username", et_account.text.toString())
        loginMap.put("user_type", 2.toString())//2项目端 3运输端
        loginMap.put("password", et_pw.text.toString())
        loginMap.put("device_type", "android")

        val call = RetrofitUtils.getApiService(baseContext).login(loginMap)
        val enhancedCall: EnhancedCall<HttpResult<LoginM>> = EnhancedCall(call)

        enhancedCall
//                .useCache(false)
                .dataClassName(LoginM::class.java).enqueue(baseContext, object : MyCallBack<HttpResult<LoginM>> {
            override fun onResponse(dataM: HttpResult<LoginM>?, strJson: String?, isCache: Boolean) {
                var sb: StringBuffer = StringBuffer()
                sb.append("token:" + dataM?.data?.token)
                sb.append("\n user_nickname:" + dataM?.data?.user?.user_nickname)
                sb.append("\n app_user_status:" + dataM?.data?.user?.app_user_status)
                sb.append("\n mobile:" + dataM?.data?.user?.mobile)
                sb.append("\n avatar: " + dataM?.data?.user?.avatar)
                tv_log.text = sb.toString()
                Params.UserToken = dataM?.data?.token!!
            }


            override fun onFinish(msg: String?, Code: Int, isSuccess: Boolean) {
                LgU.d(msg)
            }


        })

    }


    private fun Request1() {
        val call = RetrofitUtils.getApiService(baseContext, true).projectFirst(2.toString())
        val enhancedCall: EnhancedCall<HttpResult<ProjectFirstM>> = EnhancedCall(call)

        enhancedCall
                .useCache(true)
                .dataClassName(ProjectFirstM::class.java)
                .enqueue(baseContext, object : MyCallBack<HttpResult<ProjectFirstM>> {
                    override fun onResponse(dataM: HttpResult<ProjectFirstM>?, strJson: String?, isCache: Boolean) {
                        var sb: StringBuffer = StringBuffer()
                        sb.append("Cache: " + (if (isCache) "缓存获取" else "网络获取"))
                        sb.append("newGame:" + dataM?.data?.newGame)
                        sb.append("\n sowing:" + dataM?.data?.sowing)
                        sb.append("\n additionalFunctions: " + dataM?.data?.additionalFunctions)
                        tv_log.text = sb.toString()
                    }


                    override fun onFinish(msg: String?, Code: Int, isSuccess: Boolean) {
                        LgU.d(msg)

                    }


                })
    }

    private fun Request2() {
        val loginMap = HashMap<String, String>()
        loginMap.put("username", et_account.text.toString())
        loginMap.put("user_type", 2.toString())//2项目端 3运输端
        loginMap.put("password", et_pw.text.toString())
        loginMap.put("device_type", "android")
        val call = RetrofitUtils.getApiService(baseContext, true).login_string(loginMap)
        val enhancedCall: EnhancedCall<ResponseBody> = EnhancedCall(call)
        enhancedCall
                .useGson(false)
                .useCache(true)
                .dataClassName(String.javaClass).enqueue(baseContext, object : MyCallBack<ResponseBody> {
            override fun onResponse(dataM: ResponseBody?, strJson: String?, isCache: Boolean) {
                var sb: StringBuffer = StringBuffer()
                sb.append("Cache: " + (if (isCache) "缓存获取" else "网络获取"))
                sb.append("result : " + strJson)
                tv_log.text = sb.toString()
            }


            override fun onFinish(msg: String?, Code: Int, isSuccess: Boolean) {
                LgU.d(msg)
            }


        })
/*
        val loginMap = HashMap<String, String>()
        loginMap.put("username", et_account.text.toString())
        loginMap.put("user_type", 2.toString())//2项目端 3运输端
        loginMap.put("password", et_pw.text.toString())
        loginMap.put("device_type", "android")

        val call = RetrofitUtils.getApiService(baseContext).login_string(loginMap)
        val enhancedCall: EnhancedCall<ResponseBody> = EnhancedCall(call)

        enhancedCall
                .useGson(false)
                .dataClassName(String.javaClass).enqueue(baseContext, object : MyCallBack<ResponseBody> {
            override fun onResponse(data: ResponseBody?, isCache: Boolean) {

                var sb: StringBuffer = StringBuffer()

                sb.append("token:" + data?.string())
//                sb.append("token:" + data?.data?.token)
//                sb.append("\n user_nickname:" + data?.data?.user?.user_nickname)
//                sb.append("\n app_user_status:" + data?.data?.user?.app_user_status)
//                sb.append("\n mobile:" + data?.data?.user?.mobile)
//                sb.append("\n avatar: " + data?.data?.user?.avatar)
                tv_log.text = sb.toString()
            }

            override fun onFinish(msg: String?, Code: Int, isSuccess: Boolean) {
                LgU.d(msg)
            }


        })
*/

    }

    private fun Request3() {


    }

    private fun Request4() {


    }

    private fun getData() {

    }


}








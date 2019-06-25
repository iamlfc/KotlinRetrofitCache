package iam.lfc.myretrofitcache.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import iam.lfc.myretrofitcache.Beans.HttpResult
import iam.lfc.myretrofitcache.Beans.OrderListM
import iam.lfc.myretrofitcache.CallBack.OnItemClickCallback
import iam.lfc.myretrofitcache.R
import iam.lfc.myretrofitcache.RetrofitUtils.RetrofitUtils
import iam.lfc.myretrofitcache.RetrofitUtils.cache.EnhancedCall
import iam.lfc.myretrofitcache.RetrofitUtils.cache.MyCallBack
import iam.lfc.myretrofitcache.base.BaseMain
import iam.lfc.myretrofitcache.ui.adapter.MyAdapter
import iam.lfc.myretrofitcache.utils.LgU
import kotlinx.android.synthetic.main.activity_list_demo.*
import java.util.*

class ListDemo_A : BaseMain() {
    companion object {
        fun EnterThis(context: Context, string: String = "", int: Int = 0) {
            var intent = Intent(context, ListDemo_A().javaClass)
            intent.putExtra("Title", string)
            intent.putExtra("Time", int)
            context.startActivity(intent)
        }
    }

    private val str_page = ""//   是	[string]
    private val str_size = ""//   是	[string]
    private val str_pid = ""//   项目ID	是	[string]		查看
    private val str_status = ""//   状态 1未进场 2已进场 3待确认离场 4已离场 5已完成 6异地完成 7异常反馈 8已取消 9已关闭	是	[string]
    private val str_paystatus = ""//   支付状态 1未支付 2已支付	是	[string]
    private val str_fwf = ""//   服务费结算 1 是 司机已确认同意线下结款	是	[string]
    private val str_trucknumber = ""//   车牌号	是	[string]
    private val selectIndex = 0


    var list_data = mutableListOf<OrderListM.DataBean>()
    var testAdapter: MyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_demo)
        initView()
        //initData();
        getData()
    }

    private fun initView() {
        ChangeTitle("订单列表")
        layt_refresh.apply {
            setOnRefreshListener {
                object : OnRefreshLoadMoreListener {
                    override fun onRefresh(refreshLayout: RefreshLayout) {
                        pageNum = 1
                        getData()
                    }

                    override fun onLoadMore(refreshLayout: RefreshLayout) {
                        pageNum++
                        getData()
                    }

                }
            }
        }
        rlv.apply {
            testAdapter = MyAdapter(list_data, baseContext)
            testAdapter?.addEmptyView(R.layout.lay_empty)
            testAdapter?.addHeaderView(R.layout.item_header)
            testAdapter?.addFooterView(R.layout.item_footer)

            /*  testAdapter!!.onItemClickCallback.apply {
                  object : OnItemClickCallback {
                      override fun OnItemClickCallbackListener(t: Int, value: String) {
                          showToast("回调ing " + value)
                      }
                  }
              }*/
            testAdapter?.setItemClickCallBack(object : OnItemClickCallback {
                override fun OnItemClickCallbackListener(t: Int, value: String) {
                    showToast("回调ing " + value)
                }
            })
            rlv.layoutManager = LinearLayoutManager(baseContext)
            rlv.adapter = testAdapter
        }
    }

    private fun getData() {
        val service = RetrofitUtils.getApiService(this)
        val requestMap = HashMap<String, String>()
        //todo 添加请求body
        requestMap.put("pid", str_pid)
        requestMap.put("status", str_status)//状态 1未进场 2已进场 3待确认离场 4已离场 5已完成 6异地完成 7异常反馈 8已取消 9已关闭
        requestMap.put("paystatus", str_paystatus)//支付状态 1未支付 2已支付
        requestMap.put("fwf", str_fwf)//服务费结算 1 是 司机已确认同意线下结款
        requestMap.put("page", pageNum.toString())

        val call = service.Order_getList21(requestMap)
        val enhancedCall = EnhancedCall(call)

        enhancedCall.dataClassName(OrderListM::class.java).enqueue(baseContext, object : MyCallBack<HttpResult<OrderListM>> {
            override fun onResponse(dataM: HttpResult<OrderListM>?, strJson: String?, isCache: Boolean) {
                if (dataM != null) {
                    LgU.d("onResponse->" + dataM!!.getData().toString())
                    showToast((if (isCache) "来自缓存数据：" else "来自接口数据：") + dataM!!.getData().getData().get(0).getName())
                }

            }


            override fun onFinish(msg: String?, Code: Int, isSuccess: Boolean) {
            }


        })
    }
}

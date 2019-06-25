package iam.lfc.myretrofitcache.ui.adapter

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import iam.lfc.myretrofitcache.Beans.OrderListM
import iam.lfc.myretrofitcache.CallBack.OnItemClickCallback
import iam.lfc.myretrofitcache.R
import iam.lfc.myretrofitcache.share.Params.Companion.TESTIMG_URL
import iam.lfc.myretrofitcache.utils.LUtils
import iam.lfc.myretrofitcache.widget.adapter.AdvancedRecyclerViewAdapter
import iam.lfc.myretrofitcache.widget.adapter.AdvancedRecyclerViewHolder

/**
 * Created by LFC
on 2019/6/21.
 */
class MyAdapter(listData: MutableList<OrderListM.DataBean>, baseContext: Context?, type: Int = 2) : AdvancedRecyclerViewAdapter<OrderListM.DataBean>(baseContext, listData) {


    private var listData = mutableListOf<OrderListM.DataBean>()
    private var baseContext: Context? = null
    private var showType = 0// 0 正常 1 控制判断
    var onItemClickCallback: OnItemClickCallback? = null

    init {
        showType = type
        this.baseContext = baseContext
    }

    fun setItemClickCallBack(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    override fun onBindHeaderViewHolder(holder: AdvancedRecyclerViewHolder?, positon: Int) {
        (holder)!!.setText(R.id.tv_top_head, positon.toString() + "唉呀妈呀脑瓜疼")
    }

    override fun onBindContentViewHolder(holder: AdvancedRecyclerViewHolder?, positon: Int) {
        LUtils.ShowImgHead(baseContext, holder?.get(R.id.img_head), TESTIMG_URL)
        holder!!.setText(R.id.item_tv_title, positon.toString() + "号 ")
        holder!!.setText(R.id.item_tv_title2, positon.toString())
        holder!!.setText(R.id.item_tv_content1, "第" + positon.toString() + "个小宝贝")
        holder!!.setText(R.id.item_tv_content2, "第" + positon.toString() + "号  号内容信息 ")
        holder!!.itemView.setOnClickListener {
            //            Toast.makeText(baseContext, positon.toString() + "body ", Toast.LENGTH_SHORT).show()
            onItemClickCallback!!.OnItemClickCallbackListener(positon, positon.toString())

        }
    }

    override fun onBindEmptyViewHolder(holder: AdvancedRecyclerViewHolder?, positon: Int) {
        val tv = holder?.get<TextView>(R.id.tv_empty) as TextView
        tv.text = positon.toString() + " 苍也空井也空~~!"
        val laytotem = holder?.get<LinearLayout>(R.id.lay_total_empty) as LinearLayout
        if (heaD_COUNT > 0) {
            laytotem.layoutParams.height = LUtils.dp2px(baseContext, 300F)
        } else {
            laytotem.layoutParams.height = LUtils.getPhoneHeight(baseContext)
        }

    }

    override fun onBindFooterViewHolder(holder: AdvancedRecyclerViewHolder?, positon: Int) {
        val tv = holder!!.get<TextView>(R.id.tv_info_foot)
        tv.text = positon.toString() + "jio 真好看"
        tv.setOnClickListener {
            LUtils.showToask(baseContext, positon.toString() + "jio 疼")
        }

    }

    override fun setContentLayout(): Int {
        return R.layout.item_myrlv
    }
}
package iam.lfc.myretrofitcache.api


import iam.lfc.myretrofitcache.Beans.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    companion object {
        const val BASE_Header = "api/work/"
    }

    //    @Headers("Cache-Control:public ,max-age=3600")
    @get:POST(BASE_Header + "public/getSystemSet")
    val systemSet: Call<HttpResult<SystemSetM>>//获取系统参数


    @FormUrlEncoded
    @POST(BASE_Header + "public/projectFirst")
    fun projectFirst(@Field("user_type") type: String): Call<HttpResult<ProjectFirstM>> //首页数据


    @FormUrlEncoded
    @POST(BASE_Header + "public/login")
    fun login(@FieldMap map: Map<String, String>): Call<HttpResult<LoginM>> //用户登录

    @FormUrlEncoded
    @POST(BASE_Header + "public/login")
    fun login_string(@FieldMap map: Map<String, String>): Call<ResponseBody> //用户登录

    /**
     * 订单列表（项目端）
     */
    @FormUrlEncoded
    @POST(BASE_Header + "Order/getList")
    fun Order_getList21(@FieldMap map: Map<String, String>): Call<HttpResult<OrderListM>>

    @FormUrlEncoded
    @POST(BASE_Header + "user_manage/eidtNickname")
    fun eidtNickname(@FieldMap map: Map<String, String>): Observable<ResponseBody> //修改用户昵称


    @FormUrlEncoded
    @POST(BASE_Header + "public/login")
    fun login2(@FieldMap map: Map<String, String>): Observable<HttpResult<LoginM>> //用户登录


}

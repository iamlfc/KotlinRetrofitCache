package iam.lfc.myretrofitcache.RetrofitUtils

import android.content.Context
import android.text.TextUtils
import iam.lfc.myretrofitcache.RetrofitUtils.GsonConverter.ResponseConverterFactory
import iam.lfc.myretrofitcache.RetrofitUtils.cache.EnhancedCacheInterceptor
import iam.lfc.myretrofitcache.api.ApiService
import iam.lfc.myretrofitcache.share.HttpIp
import iam.lfc.myretrofitcache.share.Params.Companion.UserToken
import iam.lfc.myretrofitcache.utils.LgU
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by LFC
on 2019/6/21.
 */
open class RetrofitUtils {

    companion object {

        fun getApiService(baseContext: Context, isCache: Boolean = false): ApiService {
//            val builder = OkHttpClient.Builder()
            val retrofit = Retrofit.Builder()
                    .client(getOkHttpClient(baseContext, isCache))
                    .baseUrl(HttpIp.BASE_URL)
                    .addConverterFactory(ResponseConverterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return retrofit.create<ApiService>(ApiService::class.java!!)
        }

        private var okHttpClient: OkHttpClient? = null

        fun getOkHttpClient(context: Context, isCache: Boolean): OkHttpClient {
            if (okHttpClient == null) {

                okHttpClient = OkHttpClient.Builder()
                        .connectTimeout(90, TimeUnit.SECONDS)
                        .writeTimeout(90, TimeUnit.SECONDS)
                        .readTimeout(90, TimeUnit.SECONDS)
                        .addInterceptor { chain ->
                            val str_token = UserToken
                            var request: Request? = null
                            if (TextUtils.isEmpty(str_token)) {
                                request = chain.request().newBuilder()
                                        .build()
                            } else {
                                request = chain.request().newBuilder()
                                        .addHeader("XX-Device-Type", "android") //这里就是添加一个请求头
                                        .addHeader("XX-Token", str_token) //这里就是添加一个请求头
                                        .build()
                            }

                            chain.proceed(request!!)
                        }
                        .addInterceptor(HttpLoggingInterceptor(HttpLogger()).setLevel(HttpLoggingInterceptor.Level.BODY))
                        .addInterceptor(EnhancedCacheInterceptor(isCache))
                        .build()

            }
            return okHttpClient!!
        }

        class HttpLogger : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                LgU.more("apiData", "\n" + message)
            }
        }


    }


}
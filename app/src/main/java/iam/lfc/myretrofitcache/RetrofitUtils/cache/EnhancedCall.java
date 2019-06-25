package iam.lfc.myretrofitcache.RetrofitUtils.cache;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import iam.lfc.myretrofitcache.Beans.HttpResult;
import iam.lfc.myretrofitcache.MyAPP;
import iam.lfc.myretrofitcache.R;
import iam.lfc.myretrofitcache.RetrofitUtils.GsonConverter.ApiException;
import iam.lfc.myretrofitcache.RetrofitUtils.NetWorkUtil;
import iam.lfc.myretrofitcache.utils.LUtils;
import iam.lfc.myretrofitcache.utils.LgU;
import iam.lfc.myretrofitcache.widget.WaitDialog;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 2016/11/30.
 *
 * @author WangYi
 */

public class EnhancedCall<T> {
    private Call<T> mCall;
    private Class dataClassName;
    // 是否使用缓存 默认开启
    private boolean mUseCache = false;
    //    是否显示失败信息
    private boolean isShowMsg = true;
    //    是否显示加载框
    private boolean isShowLoading = true;
    //    加载框是否可取消
    private boolean isCancle = true;
    private int isGson = 1;//  1 是  2 否  gson 格式数据
    private WaitDialog mWaitDialog;
    private Context baseContext;

    public EnhancedCall(Call<T> call) {
        this.mCall = call;
    }

    /**
     * Gson反序列化缓存时 需要获取到泛型的class类型
     */
    public EnhancedCall<T> dataClassName(Class className) {
        dataClassName = className;
        return this;
    }

    /**
     * 是否使用缓存 默认使用
     */
    public EnhancedCall<T> useCache(boolean useCache) {
        mUseCache = useCache;
        return this;
    }

    /**
     * 是否使用缓存 默认使用
     */
    public EnhancedCall<T> useGson(boolean isGson) {
        this.isGson = isGson ? 1 : 2;
        return this;
    }

    /**
     * 是否显示失败信息
     *
     * @param showMsg
     */
    public void setShowMsg(boolean showMsg) {
        isShowMsg = showMsg;
    }

    /**
     * 是否 可取消加载框
     *
     * @param iscancle
     */
    public void setCancle(boolean iscancle) {
        this.isCancle = iscancle;
    }

    /**
     * 是否显示加载框
     *
     * @param showLoading
     */
    public void setShowLoading(boolean showLoading) {
        isShowLoading = showLoading;
    }


    public void enqueue(final Context baseContext, final MyCallBack<T> enhancedCallback) {
        this.baseContext = baseContext;
        if (isShowLoading) {
            showCallDialog();
        }
        mCall.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, final Response<T> response) {
                String strMsg = "";
                int requestCode = 0;
                String str_body = "";
                try {
                    if (response.body() != null) {
                        if (isGson != 1) {
                            str_body = ((ResponseBody) response.body()).string();
                            LgU.d(str_body);

                            JSONObject object = new JSONObject(str_body);
                            strMsg = object.getString("msg");
                            requestCode = object.getInt("code");
                        } else {
                            strMsg = ((HttpResult) response.body()).getMsg();
                            requestCode = ((HttpResult) response.body()).getCode();
                        }


// TODO: 2019/6/22  判断返回值code
                        if (response.body() != null && requestCode == 1)
//                            enhancedCallback.onResponse(response.body(), false);
                            enhancedCallback.onResponse(response.body(), str_body, false);
                        else {
                            enhancedCallback.onFinish(strMsg, requestCode, false);
                            if (isShowMsg && !TextUtils.isEmpty(strMsg)) {
                                LUtils.showToask(baseContext, strMsg);
                            }
                        }

                    } else if (response.errorBody() != null) {
                        LgU.more("errorBody : " + response.errorBody().string());
                    } else
                        onFailure(call, new Throwable());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hideCallDialog();
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                String strMsg = "服务器跑丢了`请稍后重试！";
                int errorCode = 0;
                if (t instanceof ApiException) {

                    strMsg = ((ApiException) t).getStrMsg();
                    if (TextUtils.isEmpty(strMsg)) {
                        strMsg = "服务器跑丢了`请稍后重试！！";
                    }
                    errorCode = ((ApiException) t).getCode();
                }

                if (!mUseCache || NetWorkUtil.isNetAvailable(MyAPP.instance)) {
                    //不使用缓存 或者网络可用 的情况下直接回调onFailure  或者解析失败
                    enhancedCallback.onFinish(strMsg, 0, false);
                    if (isShowMsg)
                        LUtils.showToask(baseContext, strMsg);
                    hideCallDialog();
                    return;
                }
//                读取缓存数据
                try {
                    Request request = call.request();
                    String url = request.url().toString();
                    RequestBody requestBody = request.body();
                    Charset charset = Charset.forName("UTF-8");
                    StringBuilder sb = new StringBuilder();
                    sb.append(url + "/");
                    if (request.method().equals("POST")) {
                        MediaType contentType = requestBody.contentType();
                        if (contentType != null) {
                            charset = contentType.charset(Charset.forName("UTF-8"));
                        }
                        Buffer buffer = new Buffer();
                        try {
                            requestBody.writeTo(buffer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        sb.append(buffer.readString(charset));
                        buffer.close();
                    }

                    String cache = CacheManager.getInstance().getCache(sb.toString());
                    Log.d(CacheManager.TAG, "get cache->" + cache);
// TODO: 2019/6/25  判断是否是字符串获取
                    if (!TextUtils.isEmpty(cache) && dataClassName != null) {
                        if (isGson == 1) {
                            Object obj = new Gson().fromJson(cache, dataClassName);
//                        Object obj = JSON.parseObject(cache, new TypeReference<HttpResult<dataClassName>>());
                            HttpResult httpResult = new HttpResult();
                            httpResult.setCode(1);
                            httpResult.setMsg("成功!");
                            httpResult.setData(obj);
                            if (obj != null) {
//                        enhancedCallback.onGetCache();
                                enhancedCallback.onResponse((T) httpResult, cache, true);
                                hideCallDialog();
                                return;
                            }
                        } else {
                            enhancedCallback.onResponse(null, cache, true);
                            hideCallDialog();
                            return;
                        }

                    }
                    enhancedCallback.onFinish(strMsg, errorCode, false);
                    if (isShowMsg)
                        LUtils.showToask(baseContext, strMsg);
//                enhancedCallback.onFailure(call, t);
                    Log.d(CacheManager.TAG, "onFailure->" + t.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    enhancedCallback.onFinish(strMsg, errorCode, false);
                    if (isShowMsg)
                        LUtils.showToask(baseContext, strMsg);
                }
                hideCallDialog();

            }
        });
    }


    public void showCallDialog() {
        showCallDialog(baseContext.getResources().getString(R.string.loadingmess));
    }

    public void showCallDialog(String str_msg) {

        if (mWaitDialog == null) {
            if (TextUtils.isEmpty(str_msg))
                str_msg = baseContext.getResources().getString(R.string.loadingmess);
            mWaitDialog = WaitDialog.setDialog(baseContext, str_msg, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
//                    disposable.dispose();
                }
            });
        }
        mWaitDialog.setCancelable(isCancle);
        mWaitDialog.show();
    }

    public void hideCallDialog() {
        if (mWaitDialog != null && mWaitDialog.isShowing()) {
            mWaitDialog.dismiss();
        }
    }

    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }


}

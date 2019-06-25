package iam.lfc.myretrofitcache.RetrofitUtils.cache;

/**
 * Created by LFC
 * on 2019/6/14.
 * 自定义回调接口类
 */

public interface MyCallBack<T> {


    //    void onResponse(Call<T> call, Response<T> response, T t, boolean isCache);
//    void onFinish(Call<T> call, Throwable t, String msg, int Code, boolean isSuccess);

    void onResponse(T dataM, String strJson, boolean isCache);

    void onFinish(String msg, int Code, boolean isSuccess);


}

package iam.lfc.myretrofitcache.RetrofitUtils.GsonConverter;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import iam.lfc.myretrofitcache.Beans.HttpResult;
import okhttp3.ResponseBody;
import retrofit2.Converter;

public class CustomResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    public CustomResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        //先将返回的json数据解析到Response中，如果code==SUCCESS，则解析到我们的实体基类中，否则抛异常
        String response = value.string();
        try {
            HttpResult statusVo = gson.fromJson(response, HttpResult.class);
            if (statusVo != null && statusVo.getCode() == 1) {
                //成功的时候就直接解析，不可能出现解析异常。因为我们实体基类中传入的泛型，就是数据成功时候的格式
                return gson.fromJson(response, type);
            } else {
                //抛一个自定义ResultException 传入失败时候的状态码，和信息
                throw new ApiException(statusVo.getCode(), statusVo.getMsg());
            }
        } finally {
            value.close();
        }
    }
}


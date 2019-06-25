package iam.lfc.myretrofitcache.RetrofitUtils.cache;

import com.alibaba.fastjson.JSON;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import iam.lfc.myretrofitcache.utils.LgU;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created on 2016/11/30.
 *
 * @author WangYi
 */

public class EnhancedCacheInterceptor implements Interceptor {
    private boolean isCache = false;

    public EnhancedCacheInterceptor(boolean cache) {
        this.isCache = cache;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (!isCache) {
            return chain.proceed(request);
        }

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
        LgU.d("EnhancedCacheInterceptor -> key:" + sb.toString());

        ResponseBody responseBody = response.body();
        MediaType contentType = responseBody.contentType();

        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();

        if (contentType != null) {
            charset = contentType.charset(Charset.forName("UTF-8"));
        }
        try {
            String key = sb.toString();
            String json = buffer.clone().readString(charset);
            com.alibaba.fastjson.JSONObject object = JSON.parseObject(json);
            String str_jsonData = object.getString("data");


            CacheManager.getInstance().putCache(key, str_jsonData);
            LgU.more("put cache-> key:" + key + "-> json: \n" + json + " \n str_jsonData" + str_jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chain.proceed(request);
    }

    public static boolean checkForError(String jsonResponse) {
        boolean status = false;
        try {

            JSONObject json = new JSONObject(jsonResponse);

            if (json instanceof JSONObject) {
                status = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

}

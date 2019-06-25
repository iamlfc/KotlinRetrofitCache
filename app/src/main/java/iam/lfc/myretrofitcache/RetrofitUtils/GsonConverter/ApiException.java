package iam.lfc.myretrofitcache.RetrofitUtils.GsonConverter;

import java.io.IOException;

/**
 * Created by LFC
 * on 2019/6/25.
 */

public class ApiException extends IOException {

    private int code = 0;
    private String strMsg = "";

    public ApiException(int code, String strMsg) {
        this.code = code;
        this.strMsg = strMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStrMsg() {
        return strMsg == null ? "" : strMsg;
    }

    public void setStrMsg(String strMsg) {
        this.strMsg = strMsg;
    }
}



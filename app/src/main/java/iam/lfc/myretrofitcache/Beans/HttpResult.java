package iam.lfc.myretrofitcache.Beans;


import java.io.Serializable;

public class HttpResult<T> implements Serializable {


    /**
     * msgcode : 1
     * msg : 获取成功!
     * data : T
     */

    public int code;
    public String msg;
    public T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }


    public boolean SUCCESS() {
        return this.code == 1;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "msgcode=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }




}

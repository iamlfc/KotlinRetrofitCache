package iam.lfc.myretrofitcache.Beans;

/**
 * Created by LFC
 * on 2019/1/28.
 */

public class LoginM {


    /**
     * token : 01d0d5fa0f32cd75ef30bc30b8ddfd8101d0d5fa0f32cd75ef30bc30b8ddfd81
     * user : {"id":31,"user_type":2,"sex":0,"birthday":0,"last_login_time":0,"score":0,"coin":0,"balance":"0.00","create_time":1547260911,"user_status":1,"user_login":"","user_pass":"###e21cff032f6b18a1cb10e40cd37acdfc","user_nickname":"","user_email":"","user_url":"","avatar":"","signature":"","last_login_ip":"","user_activation_key":"","mobile":"17797752813","more":null,"user_pass_pay":"","truckid":0,"bupid":0,"upid":0,"cid":"0","lat":"22.5428600","lng":"114.0595600","driverid":0,"checktype":0,"rytoken":"K05c9utz+o0PuUXxNcitH2MMRF4TG4I979yDz5fsv7o1xCl6BXieGJzj+SLRvs1qYLiQ79snEe511frDqATGQA==","user_alias":"user_id_31","user_tag":"user_tag_2_0"}
     */

    private String token;
    private UserBeanM user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserBeanM getUser() {
        return user;
    }

    public void setUser(UserBeanM user) {
        this.user = user;
    }


}

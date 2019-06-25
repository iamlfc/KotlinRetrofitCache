package iam.lfc.myretrofitcache.share

/**
 * Created by LFC
on 2019/6/20.
 */
class Params {
    companion object {
        const val TESTIMG_URL = "http://img1.3lian.com/2015/a1/105/d/40.jpg"   // 跳转 web
        var UserToken = "20325e88eaff94499f35e36f3b2612345be628fb52e39fef34fe06c6d9c647eb"

        const val OtherHttp = "http://"   //  域名头像
        const val OtherHttpS = "https://"   //  域名头像

        /**
         * Evenbus   信息
         */
        const val EB_NetFailed2Main = "EB_NetFailed2Main"   //   在欢迎页 或者启动页监听到网络之后 暂不显示网络异常信息 发送粘性通知到 main中显示


        //以下为 退出登录  需要清除的 内容
        const val UserInfo = "UserInfo"   // 登录用户信息
        const val UserPW = "UserPW"   // 用户 登录密码
        const val UserTel = "UserTel"   // 用户电话
        const val Token = "Token"   // 用户 token
        const val UserID = "UserID"   // 用户id

    }

    //evenbus
}
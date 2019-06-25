package iam.lfc.myretrofitcache.Beans;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LFC
 * on 2019/2/19.
 */

public class OrderListM implements Serializable {


    /**
     * total : 2
     * per_page : 5
     * current_page : 1
     * last_page : 1
     * data : [{"id":22,"upid":52,"pid":85,"did":68,"uid":68,"bupid":36,"truckid":15,"driverid":41,"isauto":2,"beginprovince":410000,"begincity":410100,"begindistrict":410105,"beginaliasname":"文化路街道","beginaddress":"河南省郑州市金水区 文化路街道","beginlng":"113.6815180","beginlat":"34.7866240","endprovince":440000,"endcity":440300,"enddistrict":440303,"endaliasname":"罗湖地方2","endaddress":"南湖街道","endlng":"114.1208640","endlat":"22.5414770","device":"还喝酒","number":8996,"phone":"18348085585","vehicle_type":1,"transport_category":4,"valuation_method":1,"transportation_price":"568.00","overnumber":1,"isblm":2,"price":"568.00","oldprice":"0.00","newprice":"0.00","fwfprice":"5.68","enterprisename":"垃圾总站公司","trucknumber":"豫a7f31y","type":5,"xszimg":"/upload/app/2019212/1549967041_818286.jpg","img":"/upload/app/2019212/1549967042_508581.jpg","yszimg":"/upload/app/2019212/1549967042_722924.jpg","dahpimg":"/upload/app/2019212/1549967042_787579.jpg","bxdimg":"/upload/app/2019212/1549967043_530416.jpg","bxdyxq":"2021-02-12","txzimg":"/upload/app/2019212/1549967043_262377.jpg","name":"刘强","driverphone":"18300703053","sfzh":"410105199203140279","jsz":"/upload/app/2019213/1550039075_965750.jpg","cyz":"/upload/app/2019213/1550039076_167950.jpg","hgz":"/upload/app/2019213/1550039076_535195.jpg","status":1,"ifoffline":0,"abnormal":"","abnormalimg":"","strange":"","strangelng":"0.0000000","strangelat":"0.0000000","yccl_flag":0,"yccl_cljg":0,"yccl_address":"","yccl_lng":"0.0000000","yccl_lat":"0.0000000","yccl_aliasname":"","yccl_time":"","yccl_priceflag":0,"paystatus":1,"uppaystatus":0,"buppaystatus":0,"updatetime":"0000-00-00 00:00:00","isinvoice":2,"isinvoiceb":2,"weight":"8.00","volume":"16.00","distance":1363794,"createtime":"2019-02-19 09:25:41","orderno":"2019021909254100000092940","isdelete":0,"ucharger":62,"auth":"1,2,3,5","begin_merger_name":" 河南省,郑州市,金水区","end_merger_name":" 广东省,深圳市,罗湖区","vehicle_type_title":"小金刚4方","valuation_method_title":"固定金额","transport_category_title":"烂泥","memberauth ":"1,2,5"},{"id":21,"upid":52,"pid":85,"did":68,"uid":36,"bupid":36,"truckid":15,"driverid":41,"isauto":2,"beginprovince":410000,"begincity":410100,"begindistrict":410105,"beginaliasname":"文化路街道","beginaddress":"河南省郑州市金水区 文化路街道","beginlng":"113.6815180","beginlat":"34.7866240","endprovince":440000,"endcity":440300,"enddistrict":440303,"endaliasname":"罗湖地方2","endaddress":"南湖街道","endlng":"114.1208640","endlat":"22.5414770","device":"还喝酒","number":8996,"phone":"18348085585","vehicle_type":1,"transport_category":4,"valuation_method":1,"transportation_price":"568.00","overnumber":0,"isblm":2,"price":"568.00","oldprice":"0.00","newprice":"0.00","fwfprice":"5.68","enterprisename":"垃圾总站公司","trucknumber":"豫a7f31y","type":5,"xszimg":"/upload/app/2019212/1549967041_818286.jpg","img":"/upload/app/2019212/1549967042_508581.jpg","yszimg":"/upload/app/2019212/1549967042_722924.jpg","dahpimg":"/upload/app/2019212/1549967042_787579.jpg","bxdimg":"/upload/app/2019212/1549967043_530416.jpg","bxdyxq":"2021-02-12","txzimg":"/upload/app/2019212/1549967043_262377.jpg","name":"刘强","driverphone":"18300703053","sfzh":"410105199203140279","jsz":"/upload/app/2019213/1550039075_965750.jpg","cyz":"/upload/app/2019213/1550039076_167950.jpg","hgz":"/upload/app/2019213/1550039076_535195.jpg","status":1,"ifoffline":0,"abnormal":"","abnormalimg":"","strange":"","strangelng":"0.0000000","strangelat":"0.0000000","yccl_flag":0,"yccl_cljg":0,"yccl_address":"","yccl_lng":"0.0000000","yccl_lat":"0.0000000","yccl_aliasname":"","yccl_time":"","yccl_priceflag":0,"paystatus":1,"uppaystatus":0,"buppaystatus":0,"updatetime":"0000-00-00 00:00:00","isinvoice":2,"isinvoiceb":2,"weight":"8.00","volume":"16.00","distance":1363794,"createtime":"2019-02-18 18:42:09","orderno":"2019021818420900000061267","isdelete":0,"ucharger":62,"auth":"1,2,3,5","begin_merger_name":" 河南省,郑州市,金水区","end_merger_name":" 广东省,深圳市,罗湖区","vehicle_type_title":"小金刚4方","valuation_method_title":"固定金额","transport_category_title":"烂泥","memberauth ":"1,2,5"}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 22
         * upid : 52
         * pid : 85
         * did : 68
         * uid : 68
         * bupid : 36
         * truckid : 15
         * driverid : 41
         * isauto : 2
         * beginprovince : 410000
         * begincity : 410100
         * begindistrict : 410105
         * beginaliasname : 文化路街道
         * beginaddress : 河南省郑州市金水区 文化路街道
         * beginlng : 113.6815180
         * beginlat : 34.7866240
         * endprovince : 440000
         * endcity : 440300
         * enddistrict : 440303
         * endaliasname : 罗湖地方2
         * endaddress : 南湖街道
         * endlng : 114.1208640
         * endlat : 22.5414770
         * device : 还喝酒
         * number : 8996
         * phone : 18348085585
         * vehicle_type : 1
         * transport_category : 4
         * valuation_method : 1
         * transportation_price : 568.00
         * overnumber : 1
         * isblm : 2
         * price : 568.00
         * oldprice : 0.00
         * newprice : 0.00
         * fwfprice : 5.68
         * enterprisename : 垃圾总站公司
         * trucknumber : 豫a7f31y
         * type : 5
         * xszimg : /upload/app/2019212/1549967041_818286.jpg
         * img : /upload/app/2019212/1549967042_508581.jpg
         * yszimg : /upload/app/2019212/1549967042_722924.jpg
         * dahpimg : /upload/app/2019212/1549967042_787579.jpg
         * bxdimg : /upload/app/2019212/1549967043_530416.jpg
         * bxdyxq : 2021-02-12
         * txzimg : /upload/app/2019212/1549967043_262377.jpg
         * name : 刘强
         * driverphone : 18300703053
         * sfzh : 410105199203140279
         * jsz : /upload/app/2019213/1550039075_965750.jpg
         * cyz : /upload/app/2019213/1550039076_167950.jpg
         * hgz : /upload/app/2019213/1550039076_535195.jpg
         * status : 1
         * ifoffline : 0
         * abnormal :
         * abnormalimg :
         * strange :
         * strangelng : 0.0000000
         * strangelat : 0.0000000
         * yccl_flag : 0
         * yccl_cljg : 0
         * yccl_address :
         * yccl_lng : 0.0000000
         * yccl_lat : 0.0000000
         * yccl_aliasname :
         * yccl_time :
         * yccl_priceflag : 0
         * paystatus : 1
         * uppaystatus : 0
         * buppaystatus : 0
         * updatetime : 0000-00-00 00:00:00
         * isinvoice : 2
         * isinvoiceb : 2
         * weight : 8.00
         * volume : 16.00
         * distance : 1363794
         * createtime : 2019-02-19 09:25:41
         * orderno : 2019021909254100000092940
         * isdelete : 0
         * ucharger : 62
         * auth : 1,2,3,5
         * begin_merger_name :  河南省,郑州市,金水区
         * end_merger_name :  广东省,深圳市,罗湖区
         * vehicle_type_title : 小金刚4方
         * valuation_method_title : 固定金额
         * transport_category_title : 烂泥
         * memberauth  : 1,2,5
         */

        private String id;
        private String upid;
        private String pid;
        private String did;
        private String uid;
        private String bupid;
        private String truckid;
        private String driverid;
        private String isauto;
        private String beginprovince;
        private String begincity;
        private String begindistrict;
        private String beginaliasname;
        private String beginaddress;
        private String beginlng;
        private String beginlat;
        private String endprovince;
        private String endcity;
        private String enddistrict;
        private String endaliasname;
        private String endaddress;
        private String endlng;
        private String endlat;
        private String device;
        private String number;
        private String phone;
        private String vehicle_type;
        private String transport_category;
        private String valuation_method;
        private String transportation_price;
        private String overnumber;
        private String isblm;
        private String price;
        private String oldprice;
        private String newprice;
        private String fwfprice;
        private String enterprisename;
        private String trucknumber;
        private String type;
        private String xszimg;
        private String img;
        private String yszimg;
        private String dahpimg;
        private String bxdimg;
        private String bxdyxq;
        private String txzimg;
        private String name;
        private String driverphone;
        private String sfzh;
        private String jsz;
        private String cyz;
        private String hgz;
        private String status;
        private String ifoffline;
        private String abnormal;
        private String abnormalimg;
        private String strange;
        private String strangelng;
        private String strangelat;
        private String yccl_flag;
        private String yccl_cljg;
        private String yccl_address;
        private String yccl_lng;
        private String yccl_lat;
        private String yccl_aliasname;
        private String yccl_time;
        private String yccl_priceflag;
        private String paystatus;
        private String uppaystatus;
        private String buppaystatus;
        private String updatetime;
        private String isinvoice;
        private String isinvoiceb;
        private String weight;
        private String volume;
        private String distance;
        private String createtime;
        private String orderno;
        private String isdelete;
        private String ucharger;
        private String auth;
        private String begin_merger_name;
        private String end_merger_name;
        private String vehicle_type_title;
        private String valuation_method_title;
        private String transport_category_title;
        private String memberauth;
        private String newtransportation_price;

        private String arrive_price;
        private String pay_price;
        private String fwfpricenew;

        public String getArrive_price() {
            return arrive_price == null ? "" : arrive_price;
        }

        public void setArrive_price(String arrive_price) {
            this.arrive_price = arrive_price;
        }

        public String getPay_price() {
            return pay_price == null ? "" : pay_price;
        }

        public void setPay_price(String pay_price) {
            this.pay_price = pay_price;
        }

        public String getFwfpricenew() {
            return fwfpricenew == null ? "" : fwfpricenew;
        }

        public void setFwfpricenew(String fwfpricenew) {
            this.fwfpricenew = fwfpricenew;
        }

        public String getNewtransportation_price() {
            return newtransportation_price == null ? "" : newtransportation_price;
        }

        public void setNewtransportation_price(String newtransportation_price) {
            this.newtransportation_price = newtransportation_price;
        }

        private boolean isselect = false;

        public boolean isselect() {
            return isselect;
        }

        public void setIsselect(boolean isselect) {
            this.isselect = isselect;
        }

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUpid() {
            return upid == null ? "" : upid;
        }

        public void setUpid(String upid) {
            this.upid = upid;
        }

        public String getPid() {
            return pid == null ? "" : pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getDid() {
            return did == null ? "" : did;
        }

        public void setDid(String did) {
            this.did = did;
        }

        public String getUid() {
            return uid == null ? "" : uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getBupid() {
            return bupid == null ? "" : bupid;
        }

        public void setBupid(String bupid) {
            this.bupid = bupid;
        }

        public String getTruckid() {
            return truckid == null ? "" : truckid;
        }

        public void setTruckid(String truckid) {
            this.truckid = truckid;
        }

        public String getDriverid() {
            return driverid == null ? "" : driverid;
        }

        public void setDriverid(String driverid) {
            this.driverid = driverid;
        }

        public String getIsauto() {
            return isauto == null ? "" : isauto;
        }

        public void setIsauto(String isauto) {
            this.isauto = isauto;
        }

        public String getBeginprovince() {
            return beginprovince == null ? "" : beginprovince;
        }

        public void setBeginprovince(String beginprovince) {
            this.beginprovince = beginprovince;
        }

        public String getBegincity() {
            return begincity == null ? "" : begincity;
        }

        public void setBegincity(String begincity) {
            this.begincity = begincity;
        }

        public String getBegindistrict() {
            return begindistrict == null ? "" : begindistrict;
        }

        public void setBegindistrict(String begindistrict) {
            this.begindistrict = begindistrict;
        }

        public String getBeginaliasname() {
            return TextUtils.isEmpty(beginaliasname) ? getBeginaddress() : beginaliasname;
//            return beginaliasname == null ? "" : beginaliasname;
        }

        public void setBeginaliasname(String beginaliasname) {
            this.beginaliasname = beginaliasname;
        }

        public String getBeginaddress() {
            return beginaddress == null ? "" : beginaddress;
        }

        public void setBeginaddress(String beginaddress) {
            this.beginaddress = beginaddress;
        }

        public String getBeginlng() {
            return beginlng == null ? "" : beginlng;
        }

        public void setBeginlng(String beginlng) {
            this.beginlng = beginlng;
        }

        public String getBeginlat() {
            return beginlat == null ? "" : beginlat;
        }

        public void setBeginlat(String beginlat) {
            this.beginlat = beginlat;
        }

        public String getEndprovince() {
            return endprovince == null ? "" : endprovince;
        }

        public void setEndprovince(String endprovince) {
            this.endprovince = endprovince;
        }

        public String getEndcity() {
            return endcity == null ? "" : endcity;
        }

        public void setEndcity(String endcity) {
            this.endcity = endcity;
        }

        public String getEnddistrict() {
            return enddistrict == null ? "" : enddistrict;
        }

        public void setEnddistrict(String enddistrict) {
            this.enddistrict = enddistrict;
        }

        public String getEndaliasname() {
//            return endaliasname == null ? "" : endaliasname;
            if (!TextUtils.isEmpty(isauto) && TextUtils.equals(isauto, 1 + "")) {
                return "自倒";
            }
            if (!TextUtils.isEmpty(getYccl_cljg()) && TextUtils.equals(getYccl_cljg(), 1 + "")) {
                return TextUtils.isEmpty(getYccl_aliasname()) ? getYccl_address() : getYccl_aliasname();
            } else
                return TextUtils.isEmpty(endaliasname) ? getEndaddress() : endaliasname;

        }

        public void setEndaliasname(String endaliasname) {
            this.endaliasname = endaliasname;
        }

        public String getEndaddress() {
            return endaddress == null ? "" : endaddress;
        }

        public void setEndaddress(String endaddress) {
            this.endaddress = endaddress;
        }

        public String getEndlng() {
            return endlng == null ? "" : endlng;
        }

        public void setEndlng(String endlng) {
            this.endlng = endlng;
        }

        public String getEndlat() {
            return endlat == null ? "" : endlat;
        }

        public void setEndlat(String endlat) {
            this.endlat = endlat;
        }

        public String getDevice() {
            return device == null ? "" : device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getNumber() {
            return number == null ? "" : number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPhone() {
            return phone == null ? "" : phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getVehicle_type() {
            return vehicle_type == null ? "" : vehicle_type;
        }

        public void setVehicle_type(String vehicle_type) {
            this.vehicle_type = vehicle_type;
        }

        public String getTransport_category() {
            return transport_category == null ? "" : transport_category;
        }

        public void setTransport_category(String transport_category) {
            this.transport_category = transport_category;
        }

        public String getValuation_method() {
            return valuation_method == null ? "" : valuation_method;
        }

        public void setValuation_method(String valuation_method) {
            this.valuation_method = valuation_method;
        }

        public String getTransportation_price() {
            return transportation_price == null ? "" : transportation_price;
        }

        public void setTransportation_price(String transportation_price) {
            this.transportation_price = transportation_price;
        }

        public String getOvernumber() {
            return overnumber == null ? "" : overnumber;
        }

        public void setOvernumber(String overnumber) {
            this.overnumber = overnumber;
        }

        public String getIsblm() {
            return isblm == null ? "" : isblm;
        }

        public void setIsblm(String isblm) {
            this.isblm = isblm;
        }

        public String getPrice() {
            return price == null ? "" : price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOldprice() {
            return oldprice == null ? "" : oldprice;
        }

        public void setOldprice(String oldprice) {
            this.oldprice = oldprice;
        }

        public String getNewprice() {
            return newprice == null ? "" : newprice;
        }

        public void setNewprice(String newprice) {
            this.newprice = newprice;
        }

        public String getFwfprice() {
            return fwfprice == null ? "" : fwfprice;
        }

        public void setFwfprice(String fwfprice) {
            this.fwfprice = fwfprice;
        }

        public String getEnterprisename() {
            return enterprisename == null ? "" : enterprisename;
        }

        public void setEnterprisename(String enterprisename) {
            this.enterprisename = enterprisename;
        }

        public String getTrucknumber() {
            return trucknumber == null ? "" : trucknumber;
        }

        public void setTrucknumber(String trucknumber) {
            this.trucknumber = trucknumber;
        }

        public String getType() {
            return type == null ? "" : type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getXszimg() {
            return xszimg == null ? "" : xszimg;
        }

        public void setXszimg(String xszimg) {
            this.xszimg = xszimg;
        }

        public String getImg() {
            return img == null ? "" : img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getYszimg() {
            return yszimg == null ? "" : yszimg;
        }

        public void setYszimg(String yszimg) {
            this.yszimg = yszimg;
        }

        public String getDahpimg() {
            return dahpimg == null ? "" : dahpimg;
        }

        public void setDahpimg(String dahpimg) {
            this.dahpimg = dahpimg;
        }

        public String getBxdimg() {
            return bxdimg == null ? "" : bxdimg;
        }

        public void setBxdimg(String bxdimg) {
            this.bxdimg = bxdimg;
        }

        public String getBxdyxq() {
            return bxdyxq == null ? "" : bxdyxq;
        }

        public void setBxdyxq(String bxdyxq) {
            this.bxdyxq = bxdyxq;
        }

        public String getTxzimg() {
            return txzimg == null ? "" : txzimg;
        }

        public void setTxzimg(String txzimg) {
            this.txzimg = txzimg;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDriverphone() {
            return driverphone == null ? "" : driverphone;
        }

        public void setDriverphone(String driverphone) {
            this.driverphone = driverphone;
        }

        public String getSfzh() {
            return sfzh == null ? "" : sfzh;
        }

        public void setSfzh(String sfzh) {
            this.sfzh = sfzh;
        }

        public String getJsz() {
            return jsz == null ? "" : jsz;
        }

        public void setJsz(String jsz) {
            this.jsz = jsz;
        }

        public String getCyz() {
            return cyz == null ? "" : cyz;
        }

        public void setCyz(String cyz) {
            this.cyz = cyz;
        }

        public String getHgz() {
            return hgz == null ? "" : hgz;
        }

        public void setHgz(String hgz) {
            this.hgz = hgz;
        }

        public String getStatus() {
            return status == null ? "" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIfoffline() {
            return ifoffline == null ? "" : ifoffline;
        }

        public void setIfoffline(String ifoffline) {
            this.ifoffline = ifoffline;
        }

        public String getAbnormal() {
            return abnormal == null ? "" : abnormal;
        }

        public void setAbnormal(String abnormal) {
            this.abnormal = abnormal;
        }

        public String getAbnormalimg() {
            return abnormalimg == null ? "" : abnormalimg;
        }

        public void setAbnormalimg(String abnormalimg) {
            this.abnormalimg = abnormalimg;
        }

        public String getStrange() {
            return strange == null ? "" : strange;
        }

        public void setStrange(String strange) {
            this.strange = strange;
        }

        public String getStrangelng() {
            return strangelng == null ? "" : strangelng;
        }

        public void setStrangelng(String strangelng) {
            this.strangelng = strangelng;
        }

        public String getStrangelat() {
            return strangelat == null ? "" : strangelat;
        }

        public void setStrangelat(String strangelat) {
            this.strangelat = strangelat;
        }

        public String getYccl_flag() {
            return yccl_flag == null ? "" : yccl_flag;
        }

        public void setYccl_flag(String yccl_flag) {
            this.yccl_flag = yccl_flag;
        }

        public String getYccl_cljg() {
            return yccl_cljg == null ? "" : yccl_cljg;
        }

        public void setYccl_cljg(String yccl_cljg) {
            this.yccl_cljg = yccl_cljg;
        }

        public String getYccl_address() {
            return yccl_address == null ? "" : yccl_address;
        }

        public void setYccl_address(String yccl_address) {
            this.yccl_address = yccl_address;
        }

        public String getYccl_lng() {
            return yccl_lng == null ? "" : yccl_lng;
        }

        public void setYccl_lng(String yccl_lng) {
            this.yccl_lng = yccl_lng;
        }

        public String getYccl_lat() {
            return yccl_lat == null ? "" : yccl_lat;
        }

        public void setYccl_lat(String yccl_lat) {
            this.yccl_lat = yccl_lat;
        }

        public String getYccl_aliasname() {
            return yccl_aliasname == null ? "" : yccl_aliasname;
        }

        public void setYccl_aliasname(String yccl_aliasname) {
            this.yccl_aliasname = yccl_aliasname;
        }

        public String getYccl_time() {
            return yccl_time == null ? "" : yccl_time;
        }

        public void setYccl_time(String yccl_time) {
            this.yccl_time = yccl_time;
        }

        public String getYccl_priceflag() {
            return yccl_priceflag == null ? "" : yccl_priceflag;
        }

        public void setYccl_priceflag(String yccl_priceflag) {
            this.yccl_priceflag = yccl_priceflag;
        }

        public String getPaystatus() {
            return paystatus == null ? "" : paystatus;
        }

        public void setPaystatus(String paystatus) {
            this.paystatus = paystatus;
        }

        public String getUppaystatus() {
            return uppaystatus == null ? "" : uppaystatus;
        }

        public void setUppaystatus(String uppaystatus) {
            this.uppaystatus = uppaystatus;
        }

        public String getBuppaystatus() {
            return buppaystatus == null ? "" : buppaystatus;
        }

        public void setBuppaystatus(String buppaystatus) {
            this.buppaystatus = buppaystatus;
        }

        public String getUpdatetime() {
            return updatetime == null ? "" : updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getIsinvoice() {
            return isinvoice == null ? "" : isinvoice;
        }

        public void setIsinvoice(String isinvoice) {
            this.isinvoice = isinvoice;
        }

        public String getIsinvoiceb() {
            return isinvoiceb == null ? "" : isinvoiceb;
        }

        public void setIsinvoiceb(String isinvoiceb) {
            this.isinvoiceb = isinvoiceb;
        }

        public String getWeight() {
            return weight == null ? "" : weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getVolume() {
            return volume == null ? "" : volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getDistance() {
            return distance == null ? "" : distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getCreatetime() {
            return createtime == null ? "" : createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getOrderno() {
            return orderno == null ? "" : orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getIsdelete() {
            return isdelete == null ? "" : isdelete;
        }

        public void setIsdelete(String isdelete) {
            this.isdelete = isdelete;
        }

        public String getUcharger() {
            return ucharger == null ? "" : ucharger;
        }

        public void setUcharger(String ucharger) {
            this.ucharger = ucharger;
        }

        public String getAuth() {
            return auth == null ? "" : auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public String getBegin_merger_name() {
            return begin_merger_name == null ? "" : begin_merger_name;
        }

        public void setBegin_merger_name(String begin_merger_name) {
            this.begin_merger_name = begin_merger_name;
        }

        public String getEnd_merger_name() {
            return end_merger_name == null ? "" : end_merger_name;
        }

        public void setEnd_merger_name(String end_merger_name) {
            this.end_merger_name = end_merger_name;
        }

        public String getVehicle_type_title() {
            return vehicle_type_title == null ? "" : vehicle_type_title;
        }

        public void setVehicle_type_title(String vehicle_type_title) {
            this.vehicle_type_title = vehicle_type_title;
        }

        public String getValuation_method_title() {
            return valuation_method_title == null ? "" : valuation_method_title;
        }

        public void setValuation_method_title(String valuation_method_title) {
            this.valuation_method_title = valuation_method_title;
        }

        public String getTransport_category_title() {
            return transport_category_title == null ? "" : transport_category_title;
        }

        public void setTransport_category_title(String transport_category_title) {
            this.transport_category_title = transport_category_title;
        }

        public String getMemberauth() {
            return memberauth == null ? "" : memberauth;
        }

        public void setMemberauth(String memberauth) {
            this.memberauth = memberauth;
        }
    }
}

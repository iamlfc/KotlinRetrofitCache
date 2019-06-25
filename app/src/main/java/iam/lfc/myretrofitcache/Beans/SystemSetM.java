package iam.lfc.myretrofitcache.Beans;

import java.io.Serializable;

/**
 * Created by LFC
 * on 2019/1/25.
 */
public class SystemSetM implements Serializable {

    /**
     * SERVICEPHONE : null
     * WEBSITE : WWW.BAIDU.COM
     * BONDLOW : null
     * BOND : null
     * LEASE : null
     * RESOURCE : null
     * WebSite : WWW.BAIDU.COM
     * ServiceRate : null
     */

    private String SERVICEPHONE;
    private String WEBSITE;
    private String BONDLOW;
    private String BOND;
    private String LEASE;
    private String RESOURCE;
    private String WebSite;
    private String ServiceRate;
    private String SCORE_RATE;
    private String CashRatePerson;
    private String CashRateEnterprise;


    public String getSCORE_RATE() {
        return SCORE_RATE == null ? "" : SCORE_RATE;
    }

    public void setSCORE_RATE(String SCORE_RATE) {
        this.SCORE_RATE = SCORE_RATE;
    }

    public String getCashRatePerson() {
        return CashRatePerson == null ? "" : CashRatePerson;
    }

    public void setCashRatePerson(String cashRatePerson) {
        CashRatePerson = cashRatePerson;
    }

    public String getCashRateEnterprise() {
        return CashRateEnterprise == null ? "" : CashRateEnterprise;
    }

    public void setCashRateEnterprise(String cashRateEnterprise) {
        CashRateEnterprise = cashRateEnterprise;
    }

    public String getSERVICEPHONE() {
        return SERVICEPHONE == null ? "" : SERVICEPHONE;
    }

    public void setSERVICEPHONE(String SERVICEPHONE) {
        this.SERVICEPHONE = SERVICEPHONE;
    }

    public String getWEBSITE() {
        return WEBSITE == null ? "" : WEBSITE;
    }

    public void setWEBSITE(String WEBSITE) {
        this.WEBSITE = WEBSITE;
    }

    public String getBONDLOW() {
        return BONDLOW == null ? "" : BONDLOW;
    }

    public void setBONDLOW(String BONDLOW) {
        this.BONDLOW = BONDLOW;
    }

    public String getBOND() {
        return BOND == null ? "" : BOND;
    }

    public void setBOND(String BOND) {
        this.BOND = BOND;
    }

    public String getLEASE() {
        return LEASE == null ? "" : LEASE;
    }

    public void setLEASE(String LEASE) {
        this.LEASE = LEASE;
    }

    public String getRESOURCE() {
        return RESOURCE == null ? "" : RESOURCE;
    }

    public void setRESOURCE(String RESOURCE) {
        this.RESOURCE = RESOURCE;
    }

    public String getWebSite() {
        return WebSite == null ? "" : WebSite;
    }

    public void setWebSite(String webSite) {
        WebSite = webSite;
    }

    public String getServiceRate() {
        return ServiceRate == null ? "" : ServiceRate;
    }

    public void setServiceRate(String serviceRate) {
        ServiceRate = serviceRate;
    }
}

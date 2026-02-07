package com.ftk.pg.responsevo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response") // This must match the root element of your XML
public class HdfcResponse {

    private String result;
    private String auth;
    private String ref;
    private String avr;
    private String postdate;
    private String tranid;
    private String trackid;
    private String payid;
    private String udf1;
    private String udf2;
    private String udf3;
    private String udf4;
    private String udf5;
    private String amt;
    private String authRespCode;

    @XmlElement(name = "result")
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @XmlElement(name = "auth")
    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    @XmlElement(name = "ref")
    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @XmlElement(name = "avr")
    public String getAvr() {
        return avr;
    }

    public void setAvr(String avr) {
        this.avr = avr;
    }

    @XmlElement(name = "postdate")
    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    @XmlElement(name = "tranid")
    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    @XmlElement(name = "trackid")
    public String getTrackid() {
        return trackid;
    }

    public void setTrackid(String trackid) {
        this.trackid = trackid;
    }

    @XmlElement(name = "payid")
    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    @XmlElement(name = "udf1")
    public String getUdf1() {
        return udf1;
    }

    public void setUdf1(String udf1) {
        this.udf1 = udf1;
    }

    @XmlElement(name = "udf2")
    public String getUdf2() {
        return udf2;
    }

    public void setUdf2(String udf2) {
        this.udf2 = udf2;
    }

    @XmlElement(name = "udf3")
    public String getUdf3() {
        return udf3;
    }

    public void setUdf3(String udf3) {
        this.udf3 = udf3;
    }

    @XmlElement(name = "udf4")
    public String getUdf4() {
        return udf4;
    }

    public void setUdf4(String udf4) {
        this.udf4 = udf4;
    }

    @XmlElement(name = "udf5")
    public String getUdf5() {
        return udf5;
    }

    public void setUdf5(String udf5) {
        this.udf5 = udf5;
    }

    @XmlElement(name = "amt")
    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    @XmlElement(name = "authRespCode")
    public String getAuthRespCode() {
        return authRespCode;
    }

    public void setAuthRespCode(String authRespCode) {
        this.authRespCode = authRespCode;
    }

    @Override
    public String toString() {
        return "HdfcResponse{" +
                "result='" + result + '\'' +
                ", auth='" + auth + '\'' +
                ", ref='" + ref + '\'' +
                ", avr='" + avr + '\'' +
                ", postdate='" + postdate + '\'' +
                ", tranid='" + tranid + '\'' +
                ", trackid='" + trackid + '\'' +
                ", payid='" + payid + '\'' +
                ", udf1='" + udf1 + '\'' +
                ", udf2='" + udf2 + '\'' +
                ", udf3='" + udf3 + '\'' +
                ", udf4='" + udf4 + '\'' +
                ", udf5='" + udf5 + '\'' +
                ", amt='" + amt + '\'' +
                ", authRespCode='" + authRespCode + '\'' +
                '}';
    }
}

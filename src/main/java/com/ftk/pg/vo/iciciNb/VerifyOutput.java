package com.ftk.pg.vo.iciciNb;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VerifyOutput")
public class VerifyOutput {
    private String BID;
    private String ITC;
    private String AMT;
    private String VERIFIED;

    @XmlAttribute(name = "BID")
    public String getBID() {
        return BID;
    }

    public void setBID(String BID) {
        this.BID = BID;
    }

    @XmlAttribute(name = "ITC")
    public String getITC() {
        return ITC;
    }

    public void setITC(String ITC) {
        this.ITC = ITC;
    }

    @XmlAttribute(name = "AMT")
    public String getAMT() {
        return AMT;
    }

    public void setAMT(String AMT) {
        this.AMT = AMT;
    }

    @XmlAttribute(name = "VERIFIED")
    public String getVERIFIED() {
        return VERIFIED;
    }

    public void setVERIFIED(String VERIFIED) {
        this.VERIFIED = VERIFIED;
    }

    @Override
    public String toString() {
        return "VerifyOutput{" +
                "BID='" + BID + '\'' +
                ", ITC='" + ITC + '\'' +
                ", AMT='" + AMT + '\'' +
                ", VERIFIED='" + VERIFIED + '\'' +
                '}';
    }
}
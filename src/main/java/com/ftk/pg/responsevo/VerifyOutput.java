package com.ftk.pg.responsevo;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VerifyOutput")
public class VerifyOutput {

    private String ITC;
    private String PRN;
    private String BID;
    private String CURRENCY;
    private String PMTDATE;
    private String AMT;
    private String STATUS;

    @XmlAttribute(name = "ITC")
    public String getITC() {
        return ITC;
    }

    public void setITC(String ITC) {
        this.ITC = ITC;
    }

    @XmlAttribute(name = "PRN")
    public String getPRN() {
        return PRN;
    }

    public void setPRN(String PRN) {
        this.PRN = PRN;
    }

    @XmlAttribute(name = "BID")
    public String getBID() {
        return BID;
    }

    public void setBID(String BID) {
        this.BID = BID;
    }

    @XmlAttribute(name = "CURRENCY")
    public String getCURRENCY() {
        return CURRENCY;
    }

    public void setCURRENCY(String CURRENCY) {
        this.CURRENCY = CURRENCY;
    }

    @XmlAttribute(name = "PMTDATE")
    public String getPMTDATE() {
        return PMTDATE;
    }

    public void setPMTDATE(String PMTDATE) {
        this.PMTDATE = PMTDATE;
    }

    @XmlAttribute(name = "AMT")
    public String getAMT() {
        return AMT;
    }

    public void setAMT(String AMT) {
        this.AMT = AMT;
    }

    @XmlAttribute(name = "STATUS")
    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    @Override
    public String toString() {
        return "VerifyOutput{" +
                "ITC='" + ITC + '\'' +
                ", PRN='" + PRN + '\'' +
                ", BID='" + BID + '\'' +
                ", CURRENCY='" + CURRENCY + '\'' +
                ", PMTDATE='" + PMTDATE + '\'' +
                ", AMT='" + AMT + '\'' +
                ", STATUS='" + STATUS + '\'' +
                '}';
    }
}

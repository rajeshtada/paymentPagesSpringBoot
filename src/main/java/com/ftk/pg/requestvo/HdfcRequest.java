package com.ftk.pg.requestvo;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class HdfcRequest {

    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "password")
    private String password;

    @XmlElement(name = "action")
    private String action;

    @XmlElement(name = "amt")
    private String amt;

    @XmlElement(name = "trackId")
    private String trackId;

    @XmlElement(name = "member")
    private String member;

    @XmlElement(name = "transid")
    private String transid;

    @XmlElement(name = "udf5")
    private String udf5;

    // Constructors
    public HdfcRequest() {}

    public HdfcRequest(String id, String password, String action, String amt, String trackId, String member, String transid, String udf5) {
        this.id = id;
        this.password = password;
        this.action = action;
        this.amt = amt;
        this.trackId = trackId;
        this.member = member;
        this.transid = transid;
        this.udf5 = udf5;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    public String getUdf5() {
        return udf5;
    }

    public void setUdf5(String udf5) {
        this.udf5 = udf5;
    }

    // Method to convert object to XML string
    public String toXml() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(HdfcRequest.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        java.io.StringWriter writer = new java.io.StringWriter();
        marshaller.marshal(this, writer);
        return writer.toString();
    }
}


package com.ftk.pg.vo.generateInvoice;

public class MqttDynamicQrRequest {
	public String QR;
    public String message;
    public String messageType;
    public String orderNum;
    public String price;
    public String type;
	public String getQR() {
		return QR;
	}
	public void setQR(String qR) {
		QR = qR;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
    
    
}

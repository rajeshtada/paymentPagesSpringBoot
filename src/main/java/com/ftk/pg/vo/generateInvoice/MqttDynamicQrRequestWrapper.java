package com.ftk.pg.vo.generateInvoice;

public class MqttDynamicQrRequestWrapper {

	private String serialNo;

	private MqttDynamicQrRequest mqttModal;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public MqttDynamicQrRequest getMqttModal() {
		return mqttModal;
	}

	public void setMqttModal(MqttDynamicQrRequest mqttModal) {
		this.mqttModal = mqttModal;
	}

	@Override
	public String toString() {
		return "MqttDynamicQrRequestWrapper [serialNo=" + serialNo + ", mqttModal=" + mqttModal + "]";
	}

}

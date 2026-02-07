package com.ftk.pg.vo.generateInvoice;

public class InvoiceStatusResponseWrapper {
    
    private String status;

    private String message;

    private String mid;

    private String terminalId;

    private String response;

    private String ru;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

	public String getRu() {
		return ru;
	}

	public void setRu(String ru) {
		this.ru = ru;
	}

	@Override
	public String toString() {
		return "PgResponse [status=" + status + ", message=" + message + ", mid=" + mid + ", terminalId=" + terminalId
				+ ", response=" + response + ", ru=" + ru + "]";
	}
}

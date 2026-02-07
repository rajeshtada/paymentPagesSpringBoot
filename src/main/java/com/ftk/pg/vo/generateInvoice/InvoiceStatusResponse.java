package com.ftk.pg.vo.generateInvoice;

import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.pi.modal.IntermediateTransaction;
import com.ftk.pg.pi.modal.MerchantInvoice;


public class InvoiceStatusResponse {
    


	static Logger logger = LogManager.getLogger(InvoiceStatusResponse.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 8815429335198310672L;
	private String getepayTxnId = "";
	private String mid = "";
	private String txnAmount = "";
	private String txnStatus = "";
	private String merchantOrderNo = "";
	private String udf1 = "";
	private String udf2 = "";
	private String udf3 = "";
	private String udf4 = "";
	private String udf5 = "";
	private String udf6 = "";
	private String udf7 = "";
	private String udf8 = "";
	private String udf9 = "";
	private String udf10 = "";
	private String udf41 = "";

	private String custRefNo = "";

	private String paymentMode = "";
	private String discriminator = "";

	private String message = "";

	private String paymentStatus;
	private String txnDate = "";
	private String surcharge = "";
	private String totalAmount = "";
	private String settlementAmount = "";
	private String settlementRefNo = "";
	private String settlementDate = "";
	private String settlementStatus = "";
	private String txnNote = "";
	private String refundStatus;
	private String refundAmount;
	private String bankError;

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getTxnNote() {
		return txnNote;
	}

	public void setTxnNote(String txnNote) {
		this.txnNote = txnNote;
	}

	public String getGetepayTxnId() {
		return getepayTxnId;
	}

	public void setGetepayTxnId(String getepayTxnId) {
		this.getepayTxnId = getepayTxnId;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getTxnStatus() {
		return txnStatus;
	}

	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public String getUdf1() {
		return udf1;
	}

	public void setUdf1(String udf1) {
		this.udf1 = udf1;
	}

	public String getUdf2() {
		return udf2;
	}

	public void setUdf2(String udf2) {
		this.udf2 = udf2;
	}

	public String getUdf3() {
		return udf3;
	}

	public void setUdf3(String udf3) {
		this.udf3 = udf3;
	}

	public String getUdf4() {
		return udf4;
	}

	public void setUdf4(String udf4) {
		this.udf4 = udf4;
	}

	public String getUdf5() {
		return udf5;
	}

	public void setUdf5(String udf5) {
		this.udf5 = udf5;
	}

	public String getUdf6() {
		return udf6;
	}

	public void setUdf6(String udf6) {
		this.udf6 = udf6;
	}

	public String getUdf7() {
		return udf7;
	}

	public void setUdf7(String udf7) {
		this.udf7 = udf7;
	}

	public String getUdf8() {
		return udf8;
	}

	public void setUdf8(String udf8) {
		this.udf8 = udf8;
	}

	public String getUdf9() {
		return udf9;
	}

	public void setUdf9(String udf9) {
		this.udf9 = udf9;
	}

	public String getUdf10() {
		return udf10;
	}

	public void setUdf10(String udf10) {
		this.udf10 = udf10;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getDiscriminator() {
		return discriminator;
	}

	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
	}

	public static InvoiceStatusResponse fromIntermediateTransaction2(IntermediateTransaction intermedateTransaction,
			MerchantInvoice invoice) {
                InvoiceStatusResponse response = null;
		try {
			response = new InvoiceStatusResponse();
			response.setDiscriminator(intermedateTransaction.getUdf43());
			response.setGetepayTxnId(String.valueOf(intermedateTransaction.getTransactionId()));
			response.setMerchantOrderNo(intermedateTransaction.getOrderNumber());
			response.setMid(String.valueOf(intermedateTransaction.getMid()));
			response.setPaymentMode(intermedateTransaction.getPaymentType());
			response.setTxnAmount(String.valueOf(intermedateTransaction.getTxnAmount()));
			response.setTxnStatus(intermedateTransaction.getStatus());
			if (intermedateTransaction.getUdf1() != null) {
				response.setUdf1(intermedateTransaction.getUdf1());
			}
			if (intermedateTransaction.getUdf2() != null) {
				response.setUdf2(intermedateTransaction.getUdf2());
			}
			if (intermedateTransaction.getUdf3() != null) {
				response.setUdf3(intermedateTransaction.getUdf3());
			}
			if (intermedateTransaction.getUdf4() != null) {
				response.setUdf4(intermedateTransaction.getUdf4());
			}
			if (intermedateTransaction.getUdf5() != null) {
				response.setUdf5(intermedateTransaction.getUdf5());
			}
			if (invoice.getUdf8() != null) {
				String[] arr = invoice.getUdf8().split("\\|");
				if (arr.length > 1) {
					response.setUdf6(arr[1]);
				}
			}
			if (intermedateTransaction.getUdf12() != null) {
				response.setUdf7(intermedateTransaction.getUdf12());
			}
			if (intermedateTransaction.getUdf13() != null) {
				response.setUdf8(intermedateTransaction.getUdf13());
			}
			if (intermedateTransaction.getUdf14() != null) {
				response.setUdf9(intermedateTransaction.getUdf14());
			}
			if (intermedateTransaction.getUdf15() != null) {
				response.setUdf10(intermedateTransaction.getUdf15());
			}
			if (intermedateTransaction.getUdf41() != null) {
				response.setUdf41(intermedateTransaction.getUdf41());
			}
			if (intermedateTransaction.getUdf7() != null) {
				response.setCustRefNo(intermedateTransaction.getUdf7());
			}
			if (intermedateTransaction.getTxnDatetime() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				response.setTxnDate(sdf.format(intermedateTransaction.getTxnDatetime()));
			}
			if (intermedateTransaction.getSurcharge() != 0) {
				response.setSurcharge(String.valueOf(intermedateTransaction.getSurcharge()));
			}
			response.setTxnNote(String.valueOf(intermedateTransaction.getRemark()));
			response.setPaymentStatus(intermedateTransaction.getStatus());
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return response;

	}

	public static InvoiceStatusResponse fromIntermediateTransaction(IntermediateTransaction intermedateTransaction) {
		InvoiceStatusResponse response = null;
		try {
			response = new InvoiceStatusResponse();
			response.setDiscriminator(intermedateTransaction.getUdf43());
			response.setGetepayTxnId(String.valueOf(intermedateTransaction.getTransactionId()));
			response.setMerchantOrderNo(intermedateTransaction.getOrderNumber());
			response.setMid(String.valueOf(intermedateTransaction.getMid()));
			response.setPaymentMode(intermedateTransaction.getPaymentType());
			response.setTxnAmount(String.valueOf(intermedateTransaction.getTxnAmount()));
			response.setTotalAmount(String.valueOf(intermedateTransaction.getTxnAmount()));
			response.setTxnStatus(intermedateTransaction.getStatus());
			response.setRefundStatus(null);
			if (intermedateTransaction.getUdf1() != null) {
				response.setUdf1(intermedateTransaction.getUdf1());
			}
			if (intermedateTransaction.getUdf2() != null) {
				response.setUdf2(intermedateTransaction.getUdf2());
			}
			if (intermedateTransaction.getUdf3() != null) {
				response.setUdf3(intermedateTransaction.getUdf3());
			}
			if (intermedateTransaction.getUdf4() != null) {
				response.setUdf4(intermedateTransaction.getUdf4());
			}
			if (intermedateTransaction.getUdf5() != null) {
				response.setUdf5(intermedateTransaction.getUdf5());
			}
			if (intermedateTransaction.getUdf11() != null) {
				response.setUdf6(intermedateTransaction.getUdf11());
			}
			if (intermedateTransaction.getUdf12() != null) {
				response.setUdf7(intermedateTransaction.getUdf12());
			}
			if (intermedateTransaction.getUdf13() != null) {
				response.setUdf8(intermedateTransaction.getUdf13());
			}
			if (intermedateTransaction.getUdf14() != null) {
				response.setUdf9(intermedateTransaction.getUdf14());
			}
			if (intermedateTransaction.getUdf15() != null) {
				response.setUdf10(intermedateTransaction.getUdf15());
			}
			if (intermedateTransaction.getUdf41() != null) {
				response.setUdf41(intermedateTransaction.getUdf41());
			}
			if (intermedateTransaction.getUdf7() != null) {
				response.setCustRefNo(intermedateTransaction.getUdf7());
			}
			if (intermedateTransaction.getTxnDatetime() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				response.setTxnDate(sdf.format(intermedateTransaction.getTxnDatetime()));

				response.setTxnDate(sdf.format(intermedateTransaction.getTxnDatetime()));
			}
			if (intermedateTransaction.getSurcharge() != 0) {
				response.setSurcharge(String.valueOf(intermedateTransaction.getSurcharge()));
				response.setTotalAmount(String.valueOf(intermedateTransaction.getSurcharge() + intermedateTransaction.getTxnAmount()));
			}

			response.setPaymentStatus(intermedateTransaction.getStatus());
			if (intermedateTransaction.getSettlementAmount() != null)
				response.setSettlementAmount(intermedateTransaction.getSettlementAmount().toString());
			else
				response.setSettlementAmount("");
			if (intermedateTransaction.getSettlementStatus() != null)
				response.setSettlementStatus(intermedateTransaction.getSettlementStatus());
			else
				response.setSettlementStatus("");
			if (intermedateTransaction.getSettlementDate() != null)
				response.setSettlementDate(intermedateTransaction.getSettlementDate().toString());
			else
				response.setSettlementDate("");
			if (intermedateTransaction.getUdf40() != null)
				response.setSettlementRefNo(intermedateTransaction.getUdf40());
			else
				response.setSettlementRefNo("");
			if (intermedateTransaction.getRemark() != null)
				response.setTxnNote(intermedateTransaction.getRemark());
			else
				response.setTxnNote("");
			
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			// e.printStackTrace();
		}
		return response;

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getUdf41() {
		return udf41;
	}

	public void setUdf41(String udf41) {
		this.udf41 = udf41;
	}

	public String getCustRefNo() {
		return custRefNo;
	}

	public void setCustRefNo(String custRefNo) {
		this.custRefNo = custRefNo;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public String getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(String surcharge) {
		this.surcharge = surcharge;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(String settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getSettlementRefNo() {
		return settlementRefNo;
	}

	public void setSettlementRefNo(String settlementRefNo) {
		this.settlementRefNo = settlementRefNo;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	
	public String getBankError() {
		return bankError;
	}

	public void setBankError(String bankError) {
		this.bankError = bankError;
	}

	@Override
	public String toString() {
		return "PaymentResponse [getepayTxnId=" + getepayTxnId + ", mid=" + mid + ", txnAmount=" + txnAmount
				+ ", txnStatus=" + txnStatus + ", merchantOrderNo=" + merchantOrderNo + ", udf1=" + udf1 + ", udf2="
				+ udf2 + ", udf3=" + udf3 + ", udf4=" + udf4 + ", udf5=" + udf5 + ", udf6=" + udf6 + ", udf7=" + udf7
				+ ", udf8=" + udf8 + ", udf9=" + udf9 + ", udf10=" + udf10 + ", udf41=" + udf41 + ", custRefNo="
				+ custRefNo + ", paymentMode=" + paymentMode + ", discriminator=" + discriminator + ", message="
				+ message + ", paymentStatus=" + paymentStatus + ", txnDate=" + txnDate + ", surcharge=" + surcharge
				+ ", totalAmount=" + totalAmount + ", settlementAmount=" + settlementAmount + ", settlementRefNo="
				+ settlementRefNo + ", settlementDate=" + settlementDate + ", settlementStatus=" + settlementStatus
				+ ", txnNote=" + txnNote + ", refundStatus=" + refundStatus + ", refundAmount=" + refundAmount
				+ ", bankError=" + bankError + "]";
	}
}

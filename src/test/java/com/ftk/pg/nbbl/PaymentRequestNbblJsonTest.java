package com.ftk.pg.nbbl;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.ftk.pg.util.NbblUtil;
import com.ftk.pg.vo.nbbl.reqTxnInit.AdditionalInfo;
import com.ftk.pg.vo.nbbl.reqTxnInit.Amount;
import com.ftk.pg.vo.nbbl.reqTxnInit.AmountBreakUp;
import com.ftk.pg.vo.nbbl.reqTxnInit.Beneficiary;
import com.ftk.pg.vo.nbbl.reqTxnInit.Creds;
import com.ftk.pg.vo.nbbl.reqTxnInit.Details;
import com.ftk.pg.vo.nbbl.reqTxnInit.Head;
import com.ftk.pg.vo.nbbl.reqTxnInit.Pa;
import com.ftk.pg.vo.nbbl.reqTxnInit.Payer;
import com.ftk.pg.vo.nbbl.reqTxnInit.PaymentRequestNBBL;
import com.ftk.pg.vo.nbbl.reqTxnInit.ReturnUrl;
import com.ftk.pg.vo.nbbl.reqTxnInit.TPVDetail;
import com.ftk.pg.vo.nbbl.reqTxnInit.Tag;
import com.ftk.pg.vo.nbbl.reqTxnInit.Txn;
import com.google.gson.Gson;

public class PaymentRequestNbblJsonTest {

	public static void main(String[] args) throws JSONException {
		PaymentRequestNBBL nbblRequest = generateNbblRequest();
		Gson gson = new Gson();
		String json = gson.toJson(nbblRequest);
		System.out.println(json);
	}
	
	public static PaymentRequestNBBL generateNbblRequest() throws JSONException {
		
		String timenow = NbblUtil.generateTimeStamp(ZonedDateTime.now());
		LocalDateTime now = LocalDateTime.now();
		PaymentRequestNBBL paymentRequestnbbl = new PaymentRequestNBBL();
		String paId = "PGP17";
		String refId = NbblUtil.generateRefId(paId, String.valueOf("112233445"),now);
		String messageId = NbblUtil.generateMessageId(String.valueOf("112233445"),now);

		Head head = new Head();
		head.setVer("1.0");
		head.setTs(timenow);
		head.setMsgID(messageId);
		head.setBankID("BHC01");
//		head.setBankAppId("BXY0123");
		head.setOrgID(paId);
		head.setCorrelationKey("e936e32db159c025df4ab7f6368b73ca");

		paymentRequestnbbl.setHead(head);
		paymentRequestnbbl.setTxn(Txn.builder().refID(refId).ts(timenow).expiry(300).initiationMode("REDIRECTION").build());

		Pa pa = new Pa();
		pa.setPaID(paId);
		pa.setPaName("Getepay");
//		pa.setCreds(Creds.builder().type("GSTN").value("27BBBBB0000A1Z6").build());
//		pa.setBeneficiary(Beneficiary.builder().bankId("BBA09").accNumber("6810353469612760").build());
        String jsonString = "{\"type\":\"GSTN\",\"value\":\"27BBBBB0000A1Z6\"}";
        JSONObject jsonObject = new JSONObject(jsonString);
        
        HashMap<String, Object> yourHashMap = new Gson().fromJson(jsonString, HashMap.class);

		pa.setCreds(yourHashMap);
		paymentRequestnbbl.setPa(pa);

		com.ftk.pg.vo.nbbl.reqTxnInit.Merchant merchantnbbl = new com.ftk.pg.vo.nbbl.reqTxnInit.Merchant();
		merchantnbbl.setMcc("5411");
		merchantnbbl.setMid("PGP17KRSHNMRT05");
		merchantnbbl.setmName("Shree Krishna Retail Pvt Ltd");
		merchantnbbl.setBeneficiary(Beneficiary.builder().bankId("BHC01").accNumber("8027624710272780").build());

		ReturnUrl returnUrl = new ReturnUrl();
		returnUrl.setFailure("https://portal.getepay.in:8443/getePaymentPages/nbblResponse/refID");
		returnUrl.setSuccess("https://portal.getepay.in:8443/getePaymentPages/nbblResponse/refID");
		merchantnbbl.setReturnUrl(returnUrl);

		paymentRequestnbbl.setMerchant(merchantnbbl);

		Payer payer = new Payer();

		Amount amount = new Amount();
		amount.setValue(300);
		amount.setCurr("INR");
		
		AmountBreakUp amountBreakUp = new AmountBreakUp();
		List<Tag> tagListAmountBreakup = List.of(Tag.builder().name("Convenience Charges").value("12").build());
		amountBreakUp.setTag(tagListAmountBreakup);
		
		amount.setAmountBreakUp(amountBreakUp);
		payer.setAmount(amount);

		com.ftk.pg.vo.nbbl.reqTxnInit.Device device = new com.ftk.pg.vo.nbbl.reqTxnInit.Device();
		device.setMobile("919876543210");
		List<Tag> taglistdevice = List.of(Tag.builder().name("GEOCODE").value("").build(),
				Tag.builder().name("LOCATION").value("").build(), 
				Tag.builder().name("OS").value("IOS").build(),
				Tag.builder().name("APP").value("").build(), 
				Tag.builder().name("BROWSER").value("").build());
		device.setTag(taglistdevice);
		payer.setDevice(device);

		List<TPVDetail> tpvDetailList = List.of(TPVDetail.builder().name("CUSTNAME").value("customer").build(),
				TPVDetail.builder().name("ACNUM").value("5183209016332230").build(),
				TPVDetail.builder().name("IFSC").value("BAC0007452").build());
		payer.setTPV(tpvDetailList);

		List<Details> detailsList = List.of(Details.builder().name("Customer Name").value("Customer Name").build());
		payer.setDetails(detailsList);

		paymentRequestnbbl.setPayer(payer);

		List<AdditionalInfo> additionalInfoList = List
				.of(AdditionalInfo.builder().name("SampleadditionalInfo").value("sampleAddInfoValue").build());
		paymentRequestnbbl.setAdditionalInfo(additionalInfoList);
		
		return paymentRequestnbbl;
		
	}
}

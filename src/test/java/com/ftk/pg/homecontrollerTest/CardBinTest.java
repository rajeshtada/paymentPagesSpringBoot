package com.ftk.pg.homecontrollerTest;

import com.ftk.pg.requestvo.CardBinRequest;
import com.ftk.pg.requestvo.CardBinRequestWrapper;
import com.ftk.pg.responsevo.CardBinResponse;
import com.ftk.pg.responsevo.CardBinResponseWrapper;
import com.ftk.pg.util.CardBinUtils;
import com.ftk.pg.util.FrmUtils;
import com.google.gson.Gson;

public class CardBinTest {

	public static final String CARD_BIN_MASTER_KEY = "RtwK8yN9zZNjt1PU0V0ON1EwvLF/EtHqde46zNhHmB4=";
	public static final String CARD_BIN_URL = "https://sandboxapi.getepay.in/billpayment/getepayapi/api";

	public static void main(String[] args) throws Exception {

		Gson gson = new Gson();

		CardBinRequest cardBinRequest = new CardBinRequest();
		cardBinRequest.setBin(substring("401704"));

		System.out.println("CardBinRequest=====================>" + gson.toJson(cardBinRequest));
		String encrytpedData = CardBinUtils.encrypt(gson.toJson(cardBinRequest), CARD_BIN_MASTER_KEY);

		CardBinRequestWrapper cardBinRequestWrapper = new CardBinRequestWrapper();
		cardBinRequestWrapper.setMid("1");
		cardBinRequestWrapper.setMethod("binfetch");
		cardBinRequestWrapper.setRequest(encrytpedData);
		cardBinRequestWrapper.setSignature(CardBinUtils.signatureGenarator(cardBinRequestWrapper));

		System.out.println("Request=====================>" + gson.toJson(cardBinRequestWrapper));
		String response = FrmUtils.frmRiskApiCall2(CARD_BIN_URL, gson.toJson(cardBinRequestWrapper));

		System.out.println("Response=====>" + response);

		CardBinResponseWrapper cardbeanResponseWrapper = gson.fromJson(response, CardBinResponseWrapper.class);

		String decryptedData = CardBinUtils.decrypt(cardbeanResponseWrapper.getResponse(), CARD_BIN_MASTER_KEY);

		System.out.println("Decrypted Data====================>" + decryptedData);
		CardBinResponse cardbeanResponse = gson.fromJson(decryptedData, CardBinResponse.class);
		
		cardbeanResponse.setCategory("corporate t&e");
		if(cardbeanResponse != null && cardbeanResponse.getCategory()!=null && cardbeanResponse.getCategory().contains("corporate")) {
			System.out.println("YES");
		}

		System.out.println(gson.toJson(cardbeanResponse));

	}
	
	
	
	
	public static String substring(String cardno) {
		try {
			return cardno.substring(0, 6);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}

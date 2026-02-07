package com.ftk.pg;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.ftk.pg.util.CustomExceptionLoggerError;

public class AtomRequery {

	public static void main(String[] args) {

//		String merchantTxnId = args[0].trim();
//		String date = args[1].trim();
//		String amount = args[2].trim();

		String mid = "96581";

		String merchantTxnId = "522636706";
		String date = "2025-03-11";
		String amount = "112723.6";

		BigDecimal am = new BigDecimal(112700);
		am = am.add(new BigDecimal(23.6)); // Commission
		am = am.add(new BigDecimal(0)); // Total service charge
		BigDecimal mAmount = am.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		amount = mAmount.toString();

		fetchAtomStatus(merchantTxnId, date, mid, amount);
	}

	public static int fetchAtomStatus(String merchantTxnId, String date, String mid, String amount) {
		String strUrl = "https://payment.atomtech.in/paynetz/vfts?merchantid=" + mid + "&merchanttxnid=" + merchantTxnId
				+ "&amt=" + amount.trim() + "&tdate=" + date.trim();

		System.out.println(strUrl);

//			EshUtil.writeRequeryLogs(strUrl);
		String response = "";
		try {
			URL url = new URL(strUrl);
			URLConnection urlCon = url.openConnection();
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			InputStream inputStream = httpCon.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line = "";
			while ((line = reader.readLine()) != null) {
				response = response + line;
			}
		} catch (Exception e) {
			e.printStackTrace();
//				EshUtil.writeRequeryLogs("unable to call requery Api");
		}

		System.out.println(response);
		return 1;
//			if (parseData(response, "VERIFIED").toLowerCase().startsWith("succe")) {
////				EshUtil.writeRequeryLogs("Returning success..for" + merchantTxnId);
//				return 1;
//			}
//			return 0;
	}
}

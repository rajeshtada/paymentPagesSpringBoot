package com.ftk.pg.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.dto.PaymentRequest;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.modal.MerchantSetting;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.requestvo.PropertiesVo;
import com.ftk.pg.requestvo.RblChnlPrtnrLoginReqVo;
import com.ftk.pg.requestvo.RblChnlPrtnrLoginResVo;
import com.ftk.pg.requestvo.RequestProductsVo;
import com.ftk.pg.responsevo.PaymentResponse;
import com.pgcomponent.security.SecureCardData;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class PGUtility {

	static Logger logger = LogManager.getLogger(PGUtility.class);

	public static String settlement_signature_key = "$getepayPG_@2020";
	public static String AWS_ARN_KEY = "AWS_ARN_KEY";
	public static String RBL_CORPORATION_ID = "RBL_CORPORATION_ID";
	public static String RBL_TRANSACTION_PARTICULARS = "RBL_TRANSACTION_PARTICULARS";
	public static String RBL_ISSUED_BRANCH_CODE = "RBL_ISSUED_BRANCH_CODE";
	public static String RBL_BANK_CODE = "RBL_BANK_CODE";
	public static String RBL_REGISTRATION_URL = "RBL_REGISTRATION_URL";
	public static String RBL_UPI_URL = "RBL_UPI_URL";
	public static String RBL_BC_AGENT = "RBL_BC_AGENT";
	public static String RBL_USERNAME = "RBL_USERNAME";
	public static String RBL_PASSWORD = "RBL_PASSWORD";
	public static String RBL_HMAC_KEY = "RBL_HMAC_KEY";
//	public static String portal_url = "http://164.52.194.21:8080/getepayPortal/apis/upi_response";
//	public static String decrypted_key = "1f551a44525697df72990c82a5398db2389163e5a92429b5bac6471546f4cc02";
	public static String RBL_CHNL_PTNR_USERNAME = "RBL_CHNL_PTNR_USERNAME";
	public static String RBL_CHNL_PTNR_PASSWORD = "RBL_CHNL_PTNR_PASSWORD";
	public static String ACK_FAILED = "ACK Failed";
	public static String ACK_SUCCESS = "ACK Success";
	public static Integer ACK_SUCCESS_STATUS = 0;
	public static Integer ACK_FAILED_STATUS = 1;
	public static String PAYMENT_MODE = "UPIQR";
	public static String TXNLOG_SUCCESS_STATUS = "200";
	public static String TXNLOG_FAILED_STATUS = "400";
	public static String STATIC_RBL = "Getepay.static.";
	public static String DYNAMIC_RBL = "getp.d.";
	public static String PORTAL_RESPONSE = "PORTAL_RESPONSE";
	public static String RBL_DATA_DECRYPTKEY = "RBL_DATA_DECRYPTKEY";

	public static String VIRTUAL_QR = "gvi.";

	public static String AU_CALLBACK_DEC_KEY = "AU_CALLBACK_KEY";
	public static String AU_CALLBACK_DEC_IV = "AU_CALLBACK_IV";

	// public static String GETEPAY_SSL_PATH =
	// "E:\\\\Installed\\\\keystore\\\\getepay.keystore";
	// public static String GETEPAY_SSL_PASS="Getepay@2019";
	public static String GETEPAY_SSL_PATH_KEY = "GETEPAY_SSL_PATH";
	public static String GETEPAY_SSL_PASS_KEY = "GETEPAY_SSL_PASS";

	public static String RBL_USERNAME_KEY = "RBL_USERNAME";
	public static String RBL_PASS_KEY = "RBL_PASSWORD";
	public static String RBL_AGENT_KEY = "RBL_AGENT";

	public static String BOB_PROPERTIES_PATH_KEY = "BOB_PROPERTIES_PATH_KEY";
	public static String BOB_ALIAS_KEY = "BOB_ALIAS_KEY"; // BOBTESTME;
	public static String BOB_RU_KEY = "BOB_RU_KEY";
	public static String BOB_RU_KEY_V2 = "BOB_RU_KEY_V2";

	public static String NEFT_CHALLAN_URL = "NEFT_CHALLAN_URL";
	public static String ICICI_COLLECT_URL = "ICICI_COLLECT_URL";
	// release txn Update in getepayPortal
	public static String GETEPAY_URL = "GETEPAY_URL";

	public static String PAYU_PROPERTIES_PATH_KEY = "PAYU_PROPERTIES_PATH_KEY";
	public static String PAYU_ALIAS_KEY = "PAYU_ALIAS_KEY";
	public static String PAYU_RU_KEY = "PAYU_RU_KEY";
	public static String PAYU_RU_KEY_V2 = "PAYU_RU_KEY_V2";

	public static final String PAYMENT_MODE_DYNAMIC_QR_REFID = "gptn.";
	public static final String REQUEST_DYNAMIC_QR_REFID = "gpdr.";

	public static final String VIRTUAL_VPA_HANDLER = "gvi.";
	// static response for au bank
    public static final String Utkarsh_CallBack_URL_API="Utkarsh_CallBack_URL_API";
    public static final String UPI_CC_CHARGES_WAIVER_PARTNER_IDS="UPI_CC_CHARGES_WAIVER_PARTNER_IDS";
	public static PaymentResponse validateApi(PaymentRequest request) {
		return null;
	}

	public static PaymentResponse isValidPaymentType(String paymentType, PaymentResponse pgResponse) {

		if (paymentType.equalsIgnoreCase("DC") || paymentType.equalsIgnoreCase("CC")
				|| paymentType.equalsIgnoreCase("other") || paymentType.equalsIgnoreCase("wallet")
				|| paymentType.equalsIgnoreCase("nb")) {
			pgResponse.setResult(true);
			pgResponse.setStatus("success");
		} else {
			pgResponse.setResult(false);
			pgResponse.setResponseCode("01");
			pgResponse.setStatus("Failed");
			pgResponse.setDescription("Invalid Payment Mode");
		}
		return pgResponse;
	}

	public static String getAdress() {
		InetAddress localhost = null;
		String systemipaddress = "";

		try {
			localhost = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		logger.info("System IP Address : " + (localhost.getHostAddress()).trim());
		try {
			URL url_name = new URL("http://bot.whatismyipaddress.com");
			BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));
			systemipaddress = sc.readLine().trim();
		} catch (Exception e) {
			systemipaddress = "Cannot Execute Properly";
		}
		return systemipaddress;
	}

	public static final synchronized void writeLogs1(String message) {
		String baseFolder = "/home/e-shiksa/upiPayment/log";
		String fileName = "paymentLogs.txt";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String currentDate = sdf.format(new Date());
			String logFolder = baseFolder + currentDate;
			File folder = new File(logFolder);
			if (!folder.exists() || !folder.isDirectory()) {
				folder.mkdirs();
			}
			String logFilePath = logFolder + File.separator + fileName;
			File file = new File(logFilePath);
			if (!file.exists()) {
				Files.createFile(Paths.get(logFilePath));
			}
			SimpleDateFormat sdfLogTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String logTime = sdfLogTimeFormat.format(new Date());
			String messageToWrite = logTime + ": " + message + "\n";
			Files.write(Paths.get(logFilePath), messageToWrite.getBytes(), StandardOpenOption.APPEND);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		} finally {

		}
	}

	public static final String parseRBLUPIRequest(String req) {
		String encReq = "";
		if (req.contains("<data>")) {
			req = req.trim();
			String[] data = req.split("<data>");
			if (data != null && data.length > 1) {
				encReq = data[1];
				encReq = encReq.replace("</data>", "");
			}
		}
		return encReq;
	}

	private static final String RBL_UPI_RESPONSE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><UPI_PUSH_Response xmlns=\"http://rssoftware.com/callbackadapter/domain/\">"
			+ "<statuscode>#status</statuscode>" + "<description>#desc</description>" + "</UPI_PUSH_Response>";

//	public static final String rblUPIResponse(RblResVo vo) {
//		String response = RBL_UPI_RESPONSE;
//		response = response.replaceAll("#status", String.valueOf(vo.getStatuscode()));
//		response = response.replaceAll("#desc", vo.getDescription());
//		return response;
//	}

	public static String jaxbObjectToXML(RblChnlPrtnrLoginReqVo employee) {
		String xmlContent = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(RblChnlPrtnrLoginReqVo.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(employee, sw);
			xmlContent = sw.toString();
			logger.info(xmlContent);
		} catch (JAXBException e) {
			new GlobalExceptionHandler().customException(e);
		}
		return xmlContent;
	}

//	public static String main() {
//		RblChnlPrtnrLoginReqVo chnlprtnrLoginReq = new RblChnlPrtnrLoginReqVo();
//		RestTemplate restTemplate = new RestTemplate();
//
//		chnlprtnrLoginReq.setUsername("EshikshaUser");
//		chnlprtnrLoginReq.setPassword("50D8DDE5C4B98B57C600982A65E645ED0C16B28B");
//		chnlprtnrLoginReq.setAgent("Esh2156520");
//
//		String requestStr = jaxbObjectToXML(chnlprtnrLoginReq);
//
//		try {
//			System.setProperty("javax.net.ssl.keyStore", "/home/e-shiksa/Desktop/getepay.keystore");
//			System.setProperty("javax.net.ssl.keyStorePassword", "Getepay@2019");
//			System.setProperty("javax.net.ssl.trustStore", "/home/e-shiksa/Desktop/getepay.keystore");
//			System.setProperty("javax.net.ssl.trustStorePassword", "Getepay@2019");
//			String username = "ESHIKSA";
//			String password = "Mbahety@12";
//			String auth = username + ":" + password;
//			byte[] plainCredsBytes = auth.getBytes();
//			byte[] base64CredsBytes = org.apache.commons.codec.binary.Base64.encodeBase64(plainCredsBytes);
//			String base64Creds = new String(base64CredsBytes);
//
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_XML);
//			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
//			headers.set("Authorization", "Basic " + base64Creds);
//
//			// String abc = "<?xml version=\"1.0\"
//			// encoding=\"UTF-8\"?><channelpartnerloginreq><username>EshikshaUser</username><password>50D8DDE5C4B98B57C600982A65E645ED0C16B28B</password><bcagent>Esh2156520</bcagent></channelpartnerloginreq>";
//			String abc = requestStr;
//			HttpEntity<String> request = new HttpEntity<>(abc, headers);
//			ResponseEntity<RblChnlPrtnrLoginResVo> ChnlPrtnrLoginResponse = restTemplate.postForEntity(
//					"https://apideveloper.rblbank.com/test/sb/rbl/api/v1/upi/payment?client_id=a4f98b64-e27d-4751-9ebc-bf7dba809fba&client_secret=W6mK3gQ7qK1cG7bT2qL7nH7hK8lP3vP2rE7rN8hT4nV5kN2yI5",
//					request, RblChnlPrtnrLoginResVo.class);
//			System.err.println(ChnlPrtnrLoginResponse);
//		} catch (Exception e) {
//			// TODO: handle exception
//			new GlobalExceptionHandler().customException(e);
//		}
//		return "";
//	}

	public static HostnameVerifier getHostnameVerifier(String hostName) {
		return new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				if (hostName.equals(hostname)) {
					return true;
				} else {
					HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
					return hv.verify(hostname, session);
				}
			}
		};
	}

//	public static String rblRequest(String urlStr, String request) {
//		FileInputStream is = null;
//		try {
//			KeyStore keystore = KeyStore.getInstance("jks");
//			char[] pwd = "Getepay@2019".toCharArray();
//			is = new FileInputStream(new File("/home/e-shiksa/Desktop/getepay.keystore"));
//			keystore.load(is, pwd);
//
//			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//			tmf.init(keystore);
//			TrustManager[] tm = tmf.getTrustManagers();
//
//			KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//			kmfactory.init(keystore, pwd);
//			KeyManager[] km = kmfactory.getKeyManagers();
//
//			SSLContext sslcontext = SSLContext.getInstance("TLS");
//			sslcontext.init(km, tm, null);
//
//			CloseableHttpClient httpClient = HttpClients.custom()
//					.setSSLHostnameVerifier(getHostnameVerifier("apideveloper.rblbank.com")).build();
//			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//			requestFactory.setHttpClient(httpClient);
//			RestTemplate restTemplate = new RestTemplate(requestFactory);
//
//			/*
//			 * SSLConnectionSocketFactory socketFactory = new
//			 * SSLConnectionSocketFactory(sslcontext); HttpClient httpClient =
//			 * HttpClients.custom().setSSLSocketFactory(socketFactory).build();
//			 * ClientHttpRequestFactory requestFactory = new
//			 * HttpComponentsClientHttpRequestFactory(httpClient);
//			 */
//
//			String username = "ESHIKSA";
//			String password = "Mbahety@12";
//			String auth = username + ":" + password;
//			byte[] plainCredsBytes = auth.getBytes();
//			byte[] base64CredsBytes = org.apache.commons.codec.binary.Base64.encodeBase64(plainCredsBytes);
//			String base64Creds = new String(base64CredsBytes);
//
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_XML);
//			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
//			headers.set("Authorization", "Basic " + base64Creds);
//
//			RblChnlPrtnrLoginReqVo chnlprtnrLoginReq = new RblChnlPrtnrLoginReqVo();
//			// RestTemplate restTemplate = new RestTemplate(requestFactory);
//
//			chnlprtnrLoginReq.setUsername("EshikshaUser");
//			chnlprtnrLoginReq.setPassword("50D8DDE5C4B98B57C600982A65E645ED0C16B28B");
//			chnlprtnrLoginReq.setAgent("Esh2156520");
//
//			String requestStr = jaxbObjectToXML(chnlprtnrLoginReq);
//
//			HttpEntity<String> httpRequest = new HttpEntity<>(requestStr, headers);
//
//			ResponseEntity<String> ChnlPrtnrLoginResponse = restTemplate.postForEntity(
//					"https://apideveloper.rblbank.com/test/sb/rbl/api/v1/upi/payment?client_id=a4f98b64-e27d-4751-9ebc-bf7dba809fba&client_secret=W6mK3gQ7qK1cG7bT2qL7nH7hK8lP3vP2rE7rN8hT4nV5kN2yI5",
//					request, String.class);
//			logger.info(ChnlPrtnrLoginResponse);
//
//			return ChnlPrtnrLoginResponse.getBody();
//		} catch (Exception e) {
//			new GlobalExceptionHandler().customException(e);
//		} finally {
//			try {
//				is.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				new GlobalExceptionHandler().customException(e);
//			}
//		}
//		return null;
//	}

//	public static String rblRequest1(String urlStr, String request) {
//		try {
//			System.setProperty("javax.net.ssl.keyStore", "/home/e-shiksa/Desktop/getepay.keystore");
//			System.setProperty("javax.net.ssl.keyStorePassword", "Getepay@2019");
//
//			System.setProperty("javax.net.ssl.trustStore", "/home/e-shiksa/Desktop/getepay.keystore");
//			System.setProperty("javax.net.ssl.trustStorePassword", "Getepay@2019");
//			String username = "ESHIKSA";
//			String password = "Mbahety@12";
//			String auth = username + ":" + password;
//			byte[] plainCredsBytes = auth.getBytes();
//			byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
//			String base64Creds = new String(base64CredsBytes);
//
//			org.apache.http.ssl.SSLContextBuilder builder = null;
//			try {
//				KeyStore ks = KeyStore.getInstance("JKS");
//				ks.load(new FileInputStream("/home/e-shiksa/Desktop/getepay.keystore"), "Getepay@2019".toCharArray());
//				builder = new org.apache.http.ssl.SSLContextBuilder();
//				builder.loadTrustMaterial(new File("/home/e-shiksa/Desktop/getepay.keystore"),
//						"Getepay@2019".toCharArray());
//			} catch (Exception e) {
//				new GlobalExceptionHandler().customException(e);
//			}
//
//			javax.net.ssl.SSLContext sslContext = builder.build();
//			SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
//
//			HttpClient client = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
//
////			HttpRequest request1 = HttpRequest.newBuilder()
////			          .uri(URI.create(uri))
////			          .build();
//			HttpPost requests = new HttpPost(
//					"https://apideveloper.rblbank.com/test/sb/rbl/api/v1/upi/payment?client_id=a4f98b64-e27d-4751-9ebc-bf7dba809fba&client_secret=W6mK3gQ7qK1cG7bT2qL7nH7hK8lP3vP2rE7rN8hT4nV5kN2yI5");
//			CloseableHttpClient httpClient = HttpClients.createDefault();
//			CloseableHttpResponse response = httpClient.execute(requests);
//
////			
////			URL url = new URL(urlStr);
////			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
////			con.setRequestMethod("POST");
////			con.setRequestProperty("Content-Type", "application/xml; utf-8");
////			con.setRequestProperty("Accept", "application/xml");
////			con.setRequestProperty("Authorization", "Basic " + base64Creds);
////			con.setDoOutput(true);
////			
////			try (OutputStream os = con.getOutputStream()) {
////				byte[] input = request.getBytes("utf-8");
////				os.write(input, 0, input.length);
////			}
////			String responseString = null;
////			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
////				StringBuilder response = new StringBuilder();
////				String responseLine = null;
////				while ((responseLine = br.readLine()) != null) {
////					response.append(responseLine.trim());
////				}
////				responseString = response.toString();
////				logger.info("response=>" + responseString);
////				return responseString;
////			}
//		} catch (Exception e) {
//			logger.info("Error in getting responseVo " + e.getMessage());
//			new GlobalExceptionHandler().customException(e);
//		}
//		// return main();
//		return null;
//
//	}

	public static RblChnlPrtnrLoginResVo rblRequest2(Map<String, PropertiesVo> propMap) {
		try {

			String username = "ESHIKSA";
			String password = "Mbahety@12";
			String auth = username + ":" + password;
			byte[] plainCredsBytes = auth.getBytes();
			byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
			String base64Creds = new String(base64CredsBytes);

			String getepaySSLPath = propMap.get(PGUtility.GETEPAY_SSL_PATH_KEY).getPropertyValue();
			String getepaySSLPass = propMap.get(PGUtility.GETEPAY_SSL_PASS_KEY).getPropertyValue();

			String rblUser = propMap.get(PGUtility.RBL_USERNAME_KEY).getPropertyValue();
			String rblPass = propMap.get(PGUtility.RBL_PASS_KEY).getPropertyValue();
			String rblAgent = propMap.get(PGUtility.RBL_AGENT_KEY).getPropertyValue();
			String rblUpiURl = propMap.get(PGUtility.RBL_UPI_URL).getPropertyValue();

			RblChnlPrtnrLoginReqVo chnlprtnrLoginReq = new RblChnlPrtnrLoginReqVo();
			chnlprtnrLoginReq.setUsername(rblUser);
			chnlprtnrLoginReq.setPassword(rblPass);
			chnlprtnrLoginReq.setAgent(rblAgent);

			String requestStr = PGUtility.jaxbObjectToXML(chnlprtnrLoginReq);

			System.setProperty("javax.net.ssl.keyStore", getepaySSLPath);
			System.setProperty("javax.net.ssl.keyStorePassword", getepaySSLPass);

			URL url = new URL(rblUpiURl);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);

			con.setRequestProperty("Content-Type", "application/xml; utf-8");
			con.setRequestProperty("Accept", "application/xml");
			con.setRequestProperty("Authorization", "Basic " + base64Creds);

			try (OutputStream os = con.getOutputStream()) {
				byte[] input = requestStr.getBytes("utf-8");
				os.write(input, 0, input.length);
			}
			String responseString = null;
			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				responseString = response.toString();
				logger.info("response=>" + responseString);
				RblChnlPrtnrLoginResVo responseVo = new RblChnlPrtnrLoginResVo();
				return responseVo;
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}


	public static final UserCardDetails parseCardDetails(PaymentRequest pgRequest, Merchant merchant,
			MerchantSetting merchantSetting) throws Exception {
		if (pgRequest.getPaymentMode() != null && (pgRequest.getPaymentMode().equalsIgnoreCase("cc")
				|| pgRequest.getPaymentMode().equalsIgnoreCase("dc"))) {
			String carddata = pgRequest.getCarddata();
			String[] carddata_enc = carddata.split("\\|");
			UserCardDetails cardDetails = new UserCardDetails();
			if (carddata_enc != null && carddata_enc.length > 0) {
				String cardno = carddata_enc[0];
				String cardholdername = carddata_enc[1];
				String cardtype = carddata_enc[2].toUpperCase();
				String cardassociate = carddata_enc[3];

				SecureCardData obj1 = new SecureCardData();
				String carddatadec = obj1.decryptData(cardno, merchant.getMerchantPrivateKey());
				if (carddatadec.contains("|")) {
					try {
						String card_data_array[] = carddatadec.split("\\|");
						cardno = card_data_array[0];
						String cardCvv = card_data_array[1];
						String expiryYear = card_data_array[2];
						String expiryMonth = card_data_array[3];
						cardDetails.setCardNo(cardno);
						cardDetails.setCardProvider(cardassociate);
						cardDetails.setCardType(cardtype);
						cardDetails.setCustomerName(cardholdername);
						cardDetails.setCvv(cardCvv);
						cardDetails.setExpiryMonth(expiryMonth);
						cardDetails.setExpiryYear(expiryYear);
						return cardDetails;
					} catch (Exception e) {
						return null;
					}
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("upi")) {
			String carddata = pgRequest.getCarddata();
			// String[] carddata_enc = carddata.split("\\|");
			UserCardDetails cardDetails = new UserCardDetails();
			// if(carddata_enc != null && carddata_enc.length > 0) {
			String cardno = carddata;
			// String cardtype = carddata_enc[2].toUpperCase();
			// String cardassociate = carddata_enc[3];
			// String cardholdername = carddata_enc[1];

			SecureCardData obj1 = new SecureCardData();
			String carddatadec = obj1.decryptData(cardno, merchant.getMerchantPrivateKey());
			cardDetails.setCardNo(carddatadec);
			return cardDetails;
			/*
			 * if (carddatadec.contains("|")) { try { //String card_data_array[] =
			 * carddatadec.split("\\|"); //cardno = card_data_array[0]; //String cardCvv =
			 * card_data_array[1]; //String expiryYear = card_data_array[2]; //String
			 * expiryMonth = card_data_array[3]; cardDetails.setCardNo(cardno);
			 * //cardDetails.setCardProvider(cardassociate);
			 * //cardDetails.setCardType(cardtype);
			 * //cardDetails.setCustomerName(cardholdername); //cardDetails.setCvv(cardCvv);
			 * //cardDetails.setExpiryMonth(expiryMonth); //=
			 * cardDetails.setExpiryYear(expiryYear); return cardDetails; } catch (Exception
			 * e) { return null; } } else { return null; }
			 */
			// }
		} else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("nb")) {
			UserCardDetails cardDetails = new UserCardDetails();
			cardDetails.setCustomerName(pgRequest.getName());
			return cardDetails;
		} else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("wallet")) {
			UserCardDetails cardDetails = new UserCardDetails();
			cardDetails.setCustomerName(pgRequest.getName());
			return cardDetails;
		} else {
			return null;
		}
		// return null;
	}

	public static String maskCard2(String cardNo) {
		if (cardNo != null && cardNo.length() >= 16) {
			String maskedCardNo = "";
			for (int i = 0; i < cardNo.length(); i++) {
				if (i >= 4 && i < cardNo.length() - 4) {
					maskedCardNo = maskedCardNo + "X";
				} else {
					maskedCardNo = maskedCardNo + cardNo.charAt(i);
				}
			}
			return maskedCardNo;
		} else {
			return "";
		}
	}
	
	
	public static String maskCard(String cardNo) {
		if (cardNo != null && cardNo.length() >= 16) {
			String maskedCardNo = "";
			for (int i = 0; i < cardNo.length(); i++) {
				if (i >= 6 && i < cardNo.length() - 4) {
					maskedCardNo = maskedCardNo + "X";
				} else {
					maskedCardNo = maskedCardNo + cardNo.charAt(i);
				}
			}
			return maskedCardNo;
		} else {
			return "";
		}
	}

	public static final String getBin(PaymentRequest pgRequest, Merchant merchant) throws Exception {
		String bin = "";
		String paymentMode = pgRequest.getPaymentMode();
		String carddata = pgRequest.getCarddata();
		if (!paymentMode.equalsIgnoreCase("neft") && !paymentMode.equalsIgnoreCase("nb")) {
			SecureCardData secureCardData = new SecureCardData();
			String[] data = carddata.split("\\|");
			try {
				String decryptBaseData = secureCardData.decryptData(data[0], merchant.getMerchantPrivateKey());
				String[] cardDataArray = decryptBaseData.split("\\|");
				if (cardDataArray != null && cardDataArray.length == 4) {
					String cardNo = cardDataArray[0];
					if (cardNo != null && cardNo.length() >= 16) {
						bin = cardNo.substring(0, 6);
					}
				}
			} catch (Exception e) {
				new GlobalExceptionHandler().customException(e);
			}
		}
		return bin;
	}

	public static final TransactionLog parseDetailsAfterPgDetails(TransactionLog transactionLog,
			PaymentRequest pgRequest, Merchant merchant, MerchantSetting merchantSetting,
			UserCardDetails cardDetails) throws Exception {

		String paymentMode = pgRequest.getPaymentMode();
		String carddata = pgRequest.getCarddata();
		if (!paymentMode.equalsIgnoreCase("neft") && !paymentMode.equalsIgnoreCase("nb") && !paymentMode.equalsIgnoreCase("challan")) {
			SecureCardData secureCardData = new SecureCardData();
			String[] data = carddata.split("\\|");
			String carddataassoc = "";
			try {
				String decryptBaseData = secureCardData.decryptData(data[0], merchant.getMerchantPrivateKey());
				// KMS kms = new KMS(kmsProperties.getPropertyValue());
				String[] cardDataArray = decryptBaseData.split("\\|");
				if (cardDataArray != null && cardDataArray.length == 4) {
					String cdStr = cardDataArray[0] + "|" + cardDataArray[2] + "|" + cardDataArray[3];
					String cardNo = cardDataArray[0];

					logger.info("Masked Card=>" + maskCard(cardNo));
					String maskCardNo = maskCard(cardNo);
					transactionLog.setUdf9(maskCardNo);
					// transactionLog.setCarddata(kms.encrypt(cdStr));
					transactionLog.setCarddata("");
				} else {
					transactionLog.setCarddata("");
				}
			} catch (Exception e) {
				new GlobalExceptionHandler().customException(e);
				transactionLog.setCarddata("");
			}
		}
		transactionLog.setBankId(pgRequest.getBankid());
		transactionLog.setChannelType("Ecom");

		if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("neft")) {
			transactionLog.setCustomername(pgRequest.getName());
		}
		else if(pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("challan")) {
			transactionLog.setCustomername(pgRequest.getName());
		}
		
		else {
			transactionLog.setCustomername(cardDetails.getCustomerName());
		}

		transactionLog.setDate(pgRequest.getDate());
		transactionLog.setMerchantId(merchant.getMid());
		transactionLog.setMerchantName(merchant.getMerchantName());
		transactionLog.setMerchanttxnid(pgRequest.getMerchantTxnId());
		transactionLog.setMobile(pgRequest.getMobile());
		transactionLog.setOd(pgRequest.getOd());
		transactionLog.setProcessor(merchantSetting.getProcessor());
		transactionLog.setResponseCode("02");
		transactionLog.setTxnStatus("PENDING");
		transactionLog.setTotalrefundAmt(BigDecimal.ZERO);
		transactionLog.setRefundStatus("");
		transactionLog.setMerchantSettingId(merchantSetting.getMerchantSettingId());

		if (paymentMode.equalsIgnoreCase("wallet")) {
			transactionLog.setPaymentMode("Wallet");
		} else if (paymentMode.equalsIgnoreCase("upi")) {
			transactionLog.setPaymentMode("UPI");
		} else if (paymentMode.equalsIgnoreCase("nb")) {
			transactionLog.setPaymentMode("NB");
		} else if (paymentMode.equalsIgnoreCase("neft")) {
			transactionLog.setPaymentMode("NEFT");
			
			
		}
		else if (paymentMode.equalsIgnoreCase("challan")) {
			transactionLog.setPaymentMode("CHALLAN");
		}
		else {
			transactionLog.setPaymentMode(cardDetails.getCardType());
		}
		// transactionLog.setRu(pgRequest.getRu());
		transactionLog.setStage("Transaction is in process");
		transactionLog.setTxncurr(pgRequest.getTxncurr().toUpperCase());

		String requestType = pgRequest.getRequestType();
		if (requestType == null || requestType.equals("") || !requestType.equals("PAYMENT_PAGES")) {
			transactionLog.setAmt(pgRequest.getAmt());
			transactionLog.setUdf1(pgRequest.getUdf1());
			transactionLog.setUdf2(pgRequest.getUdf2());
			transactionLog.setUdf3(pgRequest.getUdf3());
			transactionLog.setUdf4(pgRequest.getUdf4());
			transactionLog.setUdf5(pgRequest.getUdf5());
			transactionLog.setRu(pgRequest.getRu());

		}
		if (pgRequest.getProductDetails() != null) {
			transactionLog.setProductType(pgRequest.getProductDetails());
		}

		return transactionLog;
	}

	public static final RequestProductsVo parseRequestProducts(String request) {
		try {
//			String productsString = new String(Base64.decodeBase64(request));
//			if(productsString.isEmpty()) {
//				return null;
//			}
			logger.info("xml request ==> " + request);
			JAXBContext jaxbContext = JAXBContext.newInstance(RequestProductsVo.class);
//			logger.info("jaxbContext ==> " + jaxbContext);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//			logger.info("jaxbUnmarshaller ==> " + jaxbUnmarshaller);
			RequestProductsVo products = (RequestProductsVo) jaxbUnmarshaller
					.unmarshal(new StringReader(request.trim()));
			return products;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	private static final String AU_UPI_HEAD = "ver=#var ts=#ts orgId=#orgId msgId=#msgId";
	private static final String AU_Txn = "id=#id note=#note refId=#refId refUrl=#refUrl ts=#ts type=#type";
	private static final String AU_TxnConfirmation = "note=#note orgStatus=#orgStatus orgErrCode=#orgErrCode type=#type orgTxnId=#orgTxnId #ref";
	private static final String AU_TxnConfig_Ref = "<Ref>type=#type seqNum=#seqNum addr=#addr regName=#regName settAmount=#settAmount orgAmount=#orgAmount "
			+ "settCurrency=#settCurrency approvalNum=#approvalNum respCode=#respCode reversalRespCode=#reversalRespCode</Ref>";
	private static final String AU_UPI_RESPONSE = "<upi:ReqTxnConfirmation xmlns:upi=><Head>#head</Head><Txn>#txn</Txn><TxnConfirmation>#configRef</TxnConfirmation></upi:ReqTxnConfirmation>";

//	public static String auResponse(UPICallbackModel auResVo, int status, IMPSEncKeys iMPSEncKeys) {
//		String upiHead = AU_UPI_HEAD;
//		String auTxn = AU_Txn;
//		String aUTxnConf = AU_TxnConfirmation;
//		String aUTxnConfRef = AU_TxnConfig_Ref;
//		// String response = AU_UPI_RESPONSE;
//		String response = "";
//		if (status == 1) {
//			response = "<upi:ReqTxnConfirmation xmlns:upi=\"http://npci.org/upi/schema/\"><TxnConfirmation><status>SUCCESS</status><code>00</code></TxnConfirmation></upi:ReqTxnConfirmation>";
//		} else {
//			response = "<upi:ReqTxnConfirmation xmlns:upi=\"http://npci.org/upi/schema/\"><TxnConfirmation><status>FAILED</status><code>99</code></TxnConfirmation></upi:ReqTxnConfirmation>";
//		}
//
//		Util util = new Util();
//		// IMPSEncKeys iMPSEncKeys = util.getAUKey();
//		try {
//			/*
//			 * upiHead = upiHead.replaceAll("#var", "\"" + auResVo.getVer() + "\""); upiHead
//			 * = upiHead.replaceAll("#ts", "\"" + auResVo.getTs() + "\""); upiHead =
//			 * upiHead.replaceAll("#orgId", "\"" + auResVo.getOrgId() + "\""); upiHead =
//			 * upiHead.replaceAll("#msgId", "\"" + auResVo.getMsgId() + "\"");
//			 * 
//			 * auTxn = auTxn.replaceAll("#id", "\"" + auResVo.getTxnId() + "\""); auTxn =
//			 * auTxn.replaceAll("#note", "\"" + auResVo.getTxnNote() + "\""); auTxn =
//			 * auTxn.replaceAll("#refId", "\"" + auResVo.getRefId() + "\""); auTxn =
//			 * auTxn.replaceAll("#refUrl", "\"" + auResVo.getRefURL() + "\""); auTxn =
//			 * auTxn.replaceAll("#ts", "\"" + auResVo.getTxnTs() + "\""); auTxn =
//			 * auTxn.replaceAll("#type", "\"" + auResVo.getTxnType() + "\"");
//			 * 
//			 * aUTxnConfRef = aUTxnConfRef.replaceAll("#type", "\"" + auResVo.getRefType() +
//			 * "\""); aUTxnConfRef = aUTxnConfRef.replaceAll("#seqNum", "\"" +
//			 * auResVo.getSeqNum() + "\""); aUTxnConfRef = aUTxnConfRef.replaceAll("#addr",
//			 * "\"" + auResVo.getMerchantVpa() + "\""); aUTxnConfRef =
//			 * aUTxnConfRef.replaceAll("#regName", "\"" + auResVo.getMerchantName() + "\"");
//			 * aUTxnConfRef = aUTxnConfRef.replaceAll("#settAmount", "\"" +
//			 * auResVo.getSettAmount() + "\""); aUTxnConfRef =
//			 * aUTxnConfRef.replaceAll("#orgAmount", "\"" + auResVo.getOrgAmount() + "\"");
//			 * aUTxnConfRef = aUTxnConfRef.replaceAll("#settCurrency", "\"" +
//			 * auResVo.gettCurrency() + "\""); aUTxnConfRef =
//			 * aUTxnConfRef.replaceAll("#approvalNum", "\"" + auResVo.getApprovalNum() +
//			 * "\""); aUTxnConfRef = aUTxnConfRef.replaceAll("#respCode", "\"" +
//			 * auResVo.getRespCode() + "\""); aUTxnConfRef =
//			 * aUTxnConfRef.replaceAll("#reversalRespCode", "\"" +
//			 * auResVo.getReversalRespCode() + "\"");
//			 * 
//			 * aUTxnConf = aUTxnConf.replaceAll("#note", "\"" + auResVo.getTxnConfigNote() +
//			 * "\""); aUTxnConf = aUTxnConf.replaceAll("#orgStatus", "\"" +
//			 * auResVo.getOrgStatus() + "\""); aUTxnConf =
//			 * aUTxnConf.replaceAll("#orgErrCode", "\"" + auResVo.getOrgErrCode() + "\"");
//			 * aUTxnConf = aUTxnConf.replaceAll("#type", "\"" + auResVo.getType() + "\"");
//			 * aUTxnConf = aUTxnConf.replaceAll("#orgTxnId", "\"" + auResVo.getOrgTxnId() +
//			 * "\""); aUTxnConf = aUTxnConf.replaceAll("#ref", aUTxnConfRef);
//			 * 
//			 * response = response.replaceAll("#head", upiHead); response =
//			 * response.replaceAll("#txn", auTxn); response =
//			 * response.replaceAll("#configRef", aUTxnConf);
//			 */
//			logger.info("Decrypted Response=>" + response);
//			String encReq = IMPSAES.encrypt(response, iMPSEncKeys);
//			logger.info("Encrypted Response=>" + encReq);
//			return encReq;
//		} catch (Exception e) {
//			new GlobalExceptionHandler().customException(e);
//			logger.info("Error in sending response for AU BANK ==> " + e.getMessage());
//		}
//		logger.info("Decrypted Response=>" + response);
//		try {
//			response = IMPSAES.encrypt(response, iMPSEncKeys);
//		} catch (Exception e) {
//			new GlobalExceptionHandler().customException(e);
//		}
//		return response;
//	}
}

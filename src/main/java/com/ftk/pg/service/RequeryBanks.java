package com.ftk.pg.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.enstage.mlehelper.beans.AES;
import com.enstage.mlehelper.beans.EncryptedResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fss.plugin.bob.iPayPipe;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.DmoOnboarding;
import com.ftk.pg.modal.MerchantSetting;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.requestvo.HdfcRequest;
import com.ftk.pg.requestvo.IciciCallbackStatusRequest;
import com.ftk.pg.requestvo.KotakRequeryRequest;
import com.ftk.pg.requestvo.SbiTransactionStatusRequest;
import com.ftk.pg.requestvo.AxisData;
import com.ftk.pg.requestvo.IciciHybridRequest;
import com.ftk.pg.requestvo.NorthAcrossPaymentStatusRequest;
import com.ftk.pg.requestvo.SaleStatusQueryApiRequest;
import com.ftk.pg.responsevo.Data;
import com.ftk.pg.responsevo.HdfcResponse;
import com.ftk.pg.responsevo.IciciCallbackStatusResponse;
import com.ftk.pg.responsevo.KotakRequeryResponse;
import com.ftk.pg.responsevo.NorthAcrossPaymentStatusResponse;
import com.ftk.pg.responsevo.SbiTransactionStatusResponse;
import com.ftk.pg.responsevo.VerifyOutput;
import com.ftk.pg.responsevo.SaleStatusQueryApiResponse;
import com.ftk.pg.util.AES256EncDnc;
import com.ftk.pg.util.CallDmo;
import com.ftk.pg.util.DMOUtils;
import com.ftk.pg.util.HdfcUtils;
import com.ftk.pg.util.ICICIBankUtils;
import com.ftk.pg.util.IciciCompositPayUtilSqs;
import com.ftk.pg.util.IciciUtils;
import com.ftk.pg.util.KotakBankUtil;
import com.ftk.pg.util.KotakRequeryApiCall;
import com.ftk.pg.util.KotakUtils;
import com.ftk.pg.util.NCrossUtils;
import com.ftk.pg.util.NorthAcrossUtil;
import com.ftk.pg.util.RequaryUtil;
import com.ftk.pg.util.SBIUtils;
import com.ftk.pg.util.SbiTransactionStatusUtil;
import com.ftk.pg.util.Utils;
import com.ftk.pg.vo.axis.AxisNbDoubleVerificationRequest;
import com.ftk.pg.vo.sbiNb.SBIPaymentRequest;
import com.ftk.pg.vo.sbiNb.SBIRequestWrapper;
import com.ftk.pg.vo.sbiNb.SBIVerificaltionResponse;
import com.ftk.pg.vo.sbiNb.SbiRequestHeader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mb.getepay.icici.lyra.LyraChargeResponse;
import com.mb.getepay.icici.lyra.action.Call;

public class RequeryBanks {

	private static Logger log = LogManager.getLogger(RequeryBanks.class);

//	public static TransactionLog bobRequary(TransactionLog transactionLog, MerchantSetting merchantSetting ) {
//		String responseCode = "01";
//		String txnStatus = "FAILED";
//		String setStage = "";
//		String processorcode = "";
//		try {
//			String resourcePath = merchantSetting.getSetting3();
//			String alias = merchantSetting.getSetting4();
//
//			String keystorePath = merchantSetting.getSetting3();
//			// String keystorePath = setting.getSetting1();
//
//			log.info("resource=> "  + resourcePath);
//			log.info("alias=> "  + alias);
//			log.info("keystorePath=> "  + keystorePath);
//
////			log.info("resource=>" + resourcePath);
////			log.info("alias=>" + alias);
////			log.info("keystorePath=>" + keystorePath);
//			// System.setProperty("sun.rmi.registry.registryFilter", "java.**;com.**");
//			// System.setProperty("PGPLUGIN_LOGPATH",
//			// "/media/shared/getepay-sqs-logs/ipaypipetrace/ipaypipetrace.log");
//			// System.setProperty("osgi.compatibility.bootdelegation", "true");
//
//			// Security.addProvider(new BouncyCastleProvider());
//			if (resourcePath == null || alias == null) {
//				return null;
//			}
//
//			log.info("BOB Action =========> 8 " );
////			log.info("BOB Action =========> 8");
//			String action = "8";
//			String trackId = String.valueOf(transactionLog.getTransactionId());
//			String currency = "356";
//			String language = "USA";
//
//			String transId = String.valueOf(transactionLog.getTransactionId());
//
//			log.info("Bob Trasnaction ID "  + transId);
////			log.info("Bob Trasnaction ID" + transId);
//			String cardType = "UPI_VPA";
//			if (transactionLog.getPaymentMode().equalsIgnoreCase("CC")) {
//				cardType = "C";
//			} else if (transactionLog.getPaymentMode().equalsIgnoreCase("DC")) {
//				cardType = "D";
//			}
//
//			String udf5 = "TrackID";
//
////		BigDecimal txnAmt = transactionLog.getAmt();
////		BigDecimal charges = transactionLog.getCommision();
////		if (charges == null) {
////			charges = BigDecimal.ZERO;
////		}
//
//			BigDecimal netAmt = RequaryUtil.requaryAmount(transactionLog);
//
//			log.info("Net Amount ==========> "  + netAmt);
////			log.info("Net Amount ==========>" + netAmt);
//			String netAmtStr = netAmt.setScale(2, RoundingMode.HALF_UP).toString();
//			trackId = trackId + "-" + System.currentTimeMillis();
//			iPayPipe pipe = new iPayPipe();
//			
//			pipe.setResourcePath(resourcePath);
//			pipe.setKeystorePath(keystorePath);
//			pipe.setAlias(alias);
//			pipe.setAction(action);
//			pipe.setCurrency(currency);
//			pipe.setLanguage(language);
//			pipe.setAmt(netAmtStr);
//			pipe.setTransId(trackId);
//			pipe.setType(cardType);
//			pipe.setTrackId(trackId);
//			pipe.setUdf5(udf5);
//			
//			
//			
////			log.info("iPayPipe Fields=>" + "action" + "::" + action);
////			log.info("iPayPipe Fields=>" + "transId" + "::" + transId);
////			log.info("iPayPipe Fields=>" + "trackId" + "::" + trackId);
////			log.info("iPayPipe Fields=>" + "udf5" + "::" + udf5);
////			log.info("iPayPipe Fields=>" + "amount" + "::" + netAmtStr);
//
////			log.info("iPayPipe Fields=>" + "action" + "::" + action);
////			log.info("iPayPipe Fields=>" + "transId" + "::" + transId);
////			log.info("iPayPipe Fields=>" + "trackId" + "::" + trackId);
////			log.info("iPayPipe Fields=>" + "udf5" + "::" + udf5);
////			log.info("iPayPipe Fields=>" + "amount" + "::" + netAmtStr);
////			log.info("I paypipe Object=========>" + pipe.toString());
//			pipe.performTransaction();
//
//			String responseResult = pipe.getResult();
//			String processorTransactionId = pipe.getPaymentId();
//			if (responseResult == null || responseResult.trim().equals("")) {
//				responseResult = pipe.getError();
//			}
//
//			if (responseResult != null
//					&& (responseResult.equalsIgnoreCase("SUCCESS") || responseResult.equalsIgnoreCase("CAPTURED"))) {
//				responseCode = "00";
//
//				txnStatus = "SUCCESS";
//				transactionLog.setResponseCode(responseCode);
//				transactionLog.setTxnStatus(txnStatus);
//				transactionLog.setStage("Transaction is marked as success in requery. ");
//				transactionLog.setProcessorCode(responseResult);
//				transactionLog.setOrderNumber(processorTransactionId);
//				//                                                    transactionLog.setBankErrorMsg("REVERSAL");
//				return transactionLog;
//
//			} else {
//				responseCode = "01";
//				// update transaction here as failed
//
//				txnStatus = "FAILED";
//				transactionLog.setResponseCode(responseCode);
//				transactionLog.setTxnStatus(txnStatus);
//				transactionLog.setStage("Transaction is marked as failed in requery");
//				transactionLog.setProcessorCode(responseResult);
//				transactionLog.setOrderNumber(processorTransactionId);
//				transactionLog.setBankErrorMsg(pipe.getError_text());
////				transactionLog.setBankErrorMsg("Transaction Timeout");
//
//				return transactionLog;
//
//			}
//
//		} catch (Exception e) {
//			CustomExceptionLoggerError.customExceptionPrintTrace(log, e);
//		}
//
//		return null;
//	}

//	public static TransactionLog bobRequary(TransactionLog transactionLog, MerchantSetting merchantSetting, Map<String, String> propMap) {
	public static TransactionLog bobRequary(TransactionLog transactionLog, Map<String, String> propMap) {
		String responseCode = "01";
		String txnStatus = "FAILED";
		try {
			log.info("before requery transactionLog   ==> " + transactionLog);
//			String resourcePath = merchantSetting.getSetting3();
//			String alias = merchantSetting.getSetting4();
			String alias = "ESHIKSA";

//			String keystorePath = merchantSetting.getSetting3();
			String resourcePath = propMap.get(Utils.BOB_REQUERY_RESOURCE_PATH);
			String rurl = "http://www.demomerchant.com/result.jsp";
			String errorUrl = "http://www.demomerchant.com/result.jsp";

			log.info("resource=>" + resourcePath);
			log.info("alias=>" + alias);
			log.info("keystorePath=>" + resourcePath);
			log.info("keystorePath=>" + resourcePath);
			log.info("resource=>" + resourcePath);
			if (resourcePath == null || alias == null) {
				log.info("inside resourcePath null");
				return null;
			}

			log.info("BOB Action =========> 8");
			String action = "8";
			String trackId = String.valueOf(transactionLog.getTransactionId());
			String currency = "356";
			String language = "USA";

			String transId = String.valueOf(transactionLog.getTransactionId());

			log.info("Bob TrasnactionID " + transId);
			String cardType = "UPI_VPA";
			if (transactionLog.getPaymentMode().equalsIgnoreCase("CC")) {
				cardType = "C";
				log.info("cardType : " + cardType);
			} else if (transactionLog.getPaymentMode().equalsIgnoreCase("DC")) {
				cardType = "D";
				log.info("cardType : " + cardType);
			}

			String udf5 = "PaymentID";

			BigDecimal netAmt = RequaryUtil.requaryAmount(transactionLog);

			log.info("Net Amount ==========>" + netAmt);
			String netAmtStr = netAmt.setScale(2, RoundingMode.HALF_UP).toString();
			iPayPipe pipe = new iPayPipe();
			pipe.setResourcePath(resourcePath);
			pipe.setKeystorePath(resourcePath);
			pipe.setAlias(alias);
			pipe.setAction(action);
			pipe.setCurrency(currency);
			pipe.setLanguage(language);
			pipe.setResponseURL(rurl);
			pipe.setErrorURL(errorUrl);
			pipe.setAmt(netAmtStr);
			pipe.setTransId(transactionLog.getOrderNumber());
			pipe.setType(cardType);
			pipe.setTrackId(String.valueOf(System.currentTimeMillis()));
			// pipe.setTrackId(trackId);
			pipe.setUdf5(udf5);
			pipe.setUdf1("Udf1");
			pipe.setUdf2("Udf2");
			pipe.setUdf3("Udf3");
			pipe.setUdf4("Udf4");
			pipe.setUdf6("Udf6");
			pipe.setUdf7("Udf7");

			log.info("iPayPipe Fields=>" + "action" + "::" + action);
			log.info("iPayPipe Fields=>" + "transId" + "::" + pipe.getTransId());
			log.info("iPayPipe Fields=>" + "trackId" + "::" + trackId);
			log.info("iPayPipe Fields=>" + "udf5" + "::" + udf5);
			log.info("iPayPipe Fields=>" + "amount" + "::" + netAmtStr);
			log.info("I paypipe Object=========>" + pipe.toString());
			// pipe.performVbVTransaction();
			pipe.performTransaction();

			String url = pipe.getWebAddress();

			log.info("Url Requeary ======================================>" + url);

			// String response=call(url);

			// log.info("Response=========================================>"+response);
			Object responseResult = pipe.getResult();
			String processorTransactionId = pipe.getPaymentId();
			// log.info("responseResult ===> " + responseResult);
			log.info("responseResult  ==> " + responseResult);
			if (responseResult == null || responseResult.equals("")) {
				responseResult = pipe.getError();
			}

			if (responseResult != null && (responseResult.equals("SUCCESS") || responseResult.equals("CAPTURED"))) {
				responseCode = "00";
				log.info("inside responseResult success or captured");
				txnStatus = "SUCCESS";
				transactionLog.setResponseCode(responseCode);
				transactionLog.setTxnStatus(txnStatus);
				transactionLog.setStage("Transaction is marked as success in requery. ");
//				transactionLog.setProcessorCode(responseResult);
//				transactionLog.setOrderNumber(processorTransactionId);
				return transactionLog;

			} else {
				responseCode = "01";
				log.info("inside responseResult failed");
				txnStatus = "FAILED";
				transactionLog.setResponseCode(responseCode);
				transactionLog.setTxnStatus(txnStatus);
				transactionLog.setStage("Transaction is marked as failed in requery. ");
//				transactionLog.setProcessorCode(responseResult);
				transactionLog.setOrderNumber(processorTransactionId);
				return transactionLog;
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info(e);
		}

		return null;
	}

	public static TransactionLog northAcross(TransactionLog logs, MerchantSetting merchantSet,
			Map<String, String> propertiesData) {

		log.info("INSIDE NORTHACROSS REQUERY  ====> ");
		try {
			String response = null;
			ObjectMapper objectMapper = new ObjectMapper();
			NCrossUtils nCrossUtils = new NCrossUtils();
			Map<String, String> mapUtils = new HashMap<String, String>();
			mapUtils = nCrossUtils.propertyData(merchantSet, propertiesData);
			Gson gson = new Gson();
			NorthAcrossPaymentStatusRequest paymentStatusRequest = new NorthAcrossPaymentStatusRequest();
			paymentStatusRequest.setApi_key(merchantSet.getSetting2());
			paymentStatusRequest.setOrder_id(logs.getTransactionId().toString());
			paymentStatusRequest.setBank_code(merchantSet.getSetting4());
			paymentStatusRequest.setHash("");

			String pipesepratedhash = NorthAcrossUtil.generateHash(gson.toJson(paymentStatusRequest),
					merchantSet.getSetting1());
			log.info("pipesepratedhash  ====> " + pipesepratedhash);
			paymentStatusRequest.setHash(pipesepratedhash);
			log.info("paymentStatusRequest  ====> " + paymentStatusRequest);
			if (merchantSet.getProcessor().equals("SHIVALIKNB")) {
				log.info("Inside SHIVALIKNB REQUERY  ====> ");
				response = NorthAcrossUtil.PostApiCallShiv(paymentStatusRequest, mapUtils.get("paymentStatusApiUrl"));
			} else {
				log.info("INSIDE IDFCNB REQUERY  ====> ");
				response = NorthAcrossUtil.PostApiCall(paymentStatusRequest, mapUtils.get("paymentStatusApiUrl"));
			}

			NorthAcrossPaymentStatusResponse paymentStatusResponse = objectMapper.readValue(response,
					NorthAcrossPaymentStatusResponse.class);
			log.info("Response NorthAcrossPaymentStatusResponse " + paymentStatusResponse);
//			log.info("Response NorthAcrossPaymentStatusResponse" + paymentStatusResponse);
			Data data = new Data();
			if (paymentStatusResponse != null) {
				try {
					data = paymentStatusResponse.getData().get(0);

					if (data != null && !data.equals("")) {
						Boolean hash1 = NorthAcrossUtil.ReturnHashCalculate(gson.toJson(data),
								merchantSet.getSetting1(), paymentStatusResponse.getHash());

						if (hash1) {
							if (!data.equals("") && data.getResponse_code().equals("0")) {
								logs.setTxnStatus("SUCCESS");
								logs.setResponseCode("00");
//								logs.setBankErrorMsg("Transaction Timeout");
								logs.setStage("Transaction is marked as Success in requery");
								logs.setProcessorCode(data.getTransaction_id());

							} else {
								logs.setTxnStatus("FAILED");
								logs.setResponseCode("01");
//								logs.setBankErrorMsg("Transaction Timeout");
								logs.setStage("Transaction is marked as failed in requery");
								logs.setProcessorCode(data.getTransaction_id());
							}
						}
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
			} else {
				try {
					logs.setTxnStatus("FAILED");
					logs.setResponseCode("01");
//					logs.setBankErrorMsg("Transaction Timeout");
					logs.setStage("Transaction is marked as failed in requery");
					logs.setProcessorCode(data.getTransaction_id());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return logs;
	}

	public static TransactionLog kotakBankRequery(TransactionLog transactionLog, MerchantSetting merchantSetting,
			Map<String, String> propMap) {
		try {

			String responseCode = "01";
			String txnStatus = "";
			SaleStatusQueryApiRequest salerequest = new SaleStatusQueryApiRequest();
			salerequest.setBankId(merchantSetting.getSetting4());
			salerequest.setMerchantId(merchantSetting.getMloginId());
			salerequest.setTerminalId(merchantSetting.getSetting1());
			salerequest.setOrderId(String.valueOf(transactionLog.getTransactionId()));
			salerequest.setAccessCode(merchantSetting.getSetting2());
			salerequest.setTxnType("Status");
			salerequest.setSecureHash("");

			SaleStatusQueryApiResponse saleStatusQueryApiResponse = KotakBankUtil.saleStatusApi(salerequest,
					merchantSetting, propMap);
			log.info("Kotak Bank saleStatusQueryApiResponse ====> " + saleStatusQueryApiResponse);
			if (saleStatusQueryApiResponse != null
					&& saleStatusQueryApiResponse.getResponseCode().equalsIgnoreCase("00")) {
				responseCode = "00";
				txnStatus = "SUCCESS";
				transactionLog.setResponseCode(responseCode);
				transactionLog.setTxnStatus(txnStatus);
				transactionLog.setStage("Transaction is marked as success in requery. ");
				transactionLog.setProcessorCode(saleStatusQueryApiResponse.getResponseCode());
				// transactionLog.setBankErrorMsg("REVERSAL");

			} else {
				try {
					responseCode = "01";
					// update transaction here as failed
					txnStatus = "FAILED";
					transactionLog.setResponseCode(responseCode);
					transactionLog.setTxnStatus(txnStatus);
					transactionLog.setStage("Transaction is marked as failed in requery");
					transactionLog.setProcessorCode(saleStatusQueryApiResponse.getResponseCode());
					transactionLog.setBankErrorMsg("Transaction Timeout");

				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}

			}
			log.info(saleStatusQueryApiResponse);
			return transactionLog;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final TransactionLog doAtomRequery(TransactionLog transactionLog, MerchantSetting setting,
			String requeryUrl) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

			Date tDate = sdf.parse(transactionLog.getDate());

			String atomDate = sdf1.format(tDate);

			BigDecimal txnAmt = transactionLog.getAmt();
			BigDecimal charges = transactionLog.getCommision();
			if (charges == null) {
				charges = BigDecimal.ZERO;
			}

			BigDecimal netAmt = txnAmt.add(charges);
			String netAmtStr = netAmt.setScale(2, RoundingMode.HALF_UP).toString();

			String strUrl = "https://payment.atomtech.in/paynetz/vfts?merchantid=" + setting.getMloginId()
					+ "&merchanttxnid=" + transactionLog.getTransactionId() + "&amt=" + netAmtStr + "&tdate="
					+ atomDate;
			log.info("Paynetz ATOM url=> " + strUrl);
			String response = "";

			URL url = new URL(strUrl);
			URLConnection urlCon = url.openConnection();
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			InputStream inputStream = httpCon.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line = "";
			while ((line = reader.readLine()) != null) {
				response = response + line;
			}
			String responseCode = "01";
			String txnStatus = "";
			Map<String, String> status = parseData(response);
			log.info("Paynetz ATOM status => " + status);
			if (status.containsKey("VERIFIED") && status.get("VERIFIED") != null
					&& status.get("VERIFIED").equalsIgnoreCase("success")) {
				responseCode = "00";
				txnStatus = "SUCCESS";
				transactionLog.setResponseCode(responseCode);
				transactionLog.setTxnStatus(txnStatus);
				transactionLog.setStage("Transaction is marked as success in requery. ");
				transactionLog.setProcessorCode(status.get("VERIFIED"));
				transactionLog.setOrderNumber(status.get("atomtxnId"));
				// transactionLog.setBankErrorMsg("REVERSAL");
			} else if (status.get("VERIFIED").equalsIgnoreCase("FAILED")) {
				responseCode = "01";
				txnStatus = "FAILED";
				transactionLog.setResponseCode(responseCode);
				transactionLog.setTxnStatus(txnStatus);
				transactionLog.setStage("Transaction is marked as failed in requery ");
				transactionLog.setProcessorCode(status.get("VERIFIED"));
				transactionLog.setOrderNumber(status.get("atomtxnId"));
//				transactionLog.setBankErrorMsg("Transaction Timeout");
//				log.info("PAYNETZ is Due=================>");
//				Utils.addRequaryInQueue(transactionLog.getTransactionId(), requeryUrl);
//				log.info("Requery ADD AGAIN IN QUEUE DUE TO PENDING OR INITIATE =================> "+transactionLog.getTransactionId());
			}
			return transactionLog;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static Map<String, String> parseData(String response) {
		Map<String, String> resultMap = new HashMap<String, String>();
		Node doc = convertStringToDocument(response);
		NamedNodeMap map = doc.getAttributes();
		for (int i = 0; i < map.getLength(); ++i) {
			Node attr = map.item(i);
			resultMap.put(attr.getNodeName(), attr.getNodeValue());
		}
		return resultMap;
	}

	private static Node convertStringToDocument(String xmlStr) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
			if (doc.hasChildNodes()) {
				NodeList childs = doc.getChildNodes();
				for (int i = 0; i < childs.getLength(); i++) {
					Node n = childs.item(i);
					if (n.getNodeName() != null && n.getNodeName().equalsIgnoreCase("VerifyOutput")) {
						return n;
					}
				}
			}
			return doc;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static TransactionLog lyraRequery(TransactionLog transactionLog, MerchantSetting merchantSetting,
			String baseUrl, String requeryUrl) {
		try {
			String responseCode = "01";
			String txnStatus = "FAILED";
			Gson gson = new Gson();
			Call call = new Call();

			String authoriztion = merchantSetting.getMloginId() + ":" + merchantSetting.getmPassword();
			authoriztion = Base64.getEncoder().encodeToString(authoriztion.getBytes());
//			String baseUrl = propMap.get(LyraUtil.LYRA_BASE_URL).getPropertyValue();

			call.setBaseUrl(baseUrl);
			call.setAuthoriztion(authoriztion);

			String uuid = transactionLog.getProcessorTxnId().trim();
			String lyraResponse = call.getACharge(uuid);

			LyraChargeResponse lyraChargeResponse = gson.fromJson(lyraResponse, LyraChargeResponse.class);
			log.info("Icici Lyra Response : " + lyraChargeResponse);

			if (lyraChargeResponse != null && (lyraChargeResponse.getStatus().equalsIgnoreCase("success")
					|| lyraChargeResponse.getStatus().equalsIgnoreCase("PAID"))) {

				log.info("Lyra Requery is Success=================> ");
				responseCode = "00";
				txnStatus = "SUCCESS";
				transactionLog.setResponseCode(responseCode);
				transactionLog.setTxnStatus(txnStatus);
				transactionLog.setStage("Transaction is marked as success in requery. ");
				transactionLog.setProcessorCode(lyraChargeResponse.getStatus());
				// transactionLog.setBankErrorMsg("REVERSAL");
//				transactionLog.setOrderNumber();
				return transactionLog;

//			} 
//				else if (lyraChargeResponse != null && lyraChargeResponse.getTransactions().size() > 0
//					&& lyraChargeResponse.getTransactions().get(0).getStatus().equalsIgnoreCase("DECLINED")) {
//				log.info("Lyra Requery is Failed=================> " );
//				responseCode = "01";
//				// update transaction here as failed
//				txnStatus = "FAILED";
//				transactionLog.setResponseCode(responseCode);
//				transactionLog.setTxnStatus(txnStatus);
//				transactionLog.setStage("Transaction is marked as failed in requery");
//				transactionLog.setProcessorCode(lyraChargeResponse.getStatus());
////				transactionLog.setOrderNumber();
//				transactionLog.setBankErrorMsg("Transaction Timeout");
//				return transactionLog;
			} else if (lyraChargeResponse != null && lyraChargeResponse.getTransactions().size() > 0
					&& lyraChargeResponse.getTransactions().get(0).getStatus().equalsIgnoreCase("FAILED")) {
				log.info("Lyra Requery is Failed=================> ");
				responseCode = "01";
				// update transaction here as failed
				txnStatus = "FAILED";
				transactionLog.setResponseCode(responseCode);
				transactionLog.setTxnStatus(txnStatus);
				transactionLog.setStage("Transaction is marked as failed in requery");
				transactionLog.setProcessorCode(lyraChargeResponse.getStatus());
//				transactionLog.setOrderNumber();
				transactionLog.setBankErrorMsg("Transaction Timeout");
				return transactionLog;
			}

			else {
//				txnStatus = lyraChargeResponse.getStatus();
//				transactionLog.setResponseCode(responseCode);
//				transactionLog.setTxnStatus(txnStatus);
//				transactionLog.setStage("Transaction is marked as failed in requery");
//				transactionLog.setProcessorCode(lyraChargeResponse.getStatus());
////				transactionLog.setOrderNumber();
////				transactionLog.setBankErrorMsg("Transaction Timeout");
				return transactionLog;
//				Utils.addRequaryInQueue(transactionLog.getTransactionId(), requeryUrl);
//				log.info("Requery ADD AGAIN IN QUEUE DUE TO PENDING OR INITIATE =================> "+transactionLog.getTransactionId());
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

//	private static void retryTransaction(TransactionLog transactionLog, MerchantSetting merchantSetting,
//			String baseUrl) {
//		
//		int maxAttempts = 3; // Max retry attempts
//        int retryIntervalSeconds = 10; // Interval between retries in seconds
//
//        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
//            try {
//                // Make API call to requery transaction
//                Gson gson = new Gson();
//                Call call = new Call();
//
//                String authorization = merchantSetting.getMloginId() + ":" + merchantSetting.getmPassword();
//                String encodedAuthorization = Base64.getEncoder().encodeToString(authorization.getBytes());
//
//                call.setBaseUrl(baseUrl);
//                call.setAuthoriztion(encodedAuthorization);
//
//                String uuid = transactionLog.getProcessorTxnId().trim();
//                String lyraResponse = call.getACharge(uuid);
//
//                LyraChargeResponse lyraChargeResponse = gson.fromJson(lyraResponse, LyraChargeResponse.class);
//                log.info("Retry attempt " + attempt + ", Response : " + lyraChargeResponse);
//
//                if (lyraChargeResponse != null) {
//                    String status = lyraChargeResponse.getStatus();
//                    if (status.equalsIgnoreCase("SUCCESS") || status.equalsIgnoreCase("PAID")) {
//                        transactionLog.setResponseCode("00");
//                        transactionLog.setTxnStatus("SUCCESS");
//                        transactionLog.setStage("Transaction is marked as success after retry.");
//                        transactionLog.setProcessorCode(status);
//                        return; // Exit retry loop on successful retry
//                    } else if (status.equalsIgnoreCase("DUE") || status.equalsIgnoreCase("PENDING")) {
//                        // Retry logic continues
//                        log.info("Transaction is still pending. Retrying...");
//                    } else {
//                        // Handle other statuses as failed
//                        transactionLog.setResponseCode("01");
//                        transactionLog.setTxnStatus("FAILED");
//                        transactionLog.setStage("Transaction failed after retry.");
//                        transactionLog.setProcessorCode(status);
//                        return; // Exit retry loop on failure
//                    }
//                } else {
//                    // Handle case where lyraChargeResponse is null
//                    log.error("Failed to get response from Lyra API during retry.");
//                }
//            } catch (Exception e) {
//                log.error("Exception occurred during retry attempt " + attempt, e);
//                CustomExceptionLoggerError.customExceptionPrintTrace(log, e);
//            }
//
//            // Wait before next retry
//            if (attempt < maxAttempts) {
//                try {
//                    TimeUnit.SECONDS.sleep(retryIntervalSeconds);
//                } catch (InterruptedException ie) {
//                    log.error("Retry sleep interrupted", ie);
//                    Thread.currentThread().interrupt();
//                }
//            }
//        }
//
//        // Log if all retry attempts failed
//        log.error("Retry attempts exhausted for transaction with ID: " + transactionLog.getProcessorTxnId());
//    }

	public static TransactionLog iciciBankRequery(TransactionLog transactionLog, DmoOnboarding dmoOnboarding,
			Map<String, String> propMap, String dmoMerchantIdOnline) {
		try {

			String responseCode = "01";
			String txnStatus = "FAILED";

			String apiKey = propMap.get(IciciUtils.DMO_API_KEY);
//			DmoOnboarding dmoOnboarding = new DmoOnboarding();
//			dmoOnboarding = dmoOnboardingDao.findByVpa(transactionLog.getUdf9());
//			log.info("DMO onboarded merchant => " + dmoOnboarding);
//			propMap.get(IciciUtils.DMO_PARENT_MERCHANTID);

			IciciCallbackStatusRequest request = new IciciCallbackStatusRequest();
			if (dmoOnboarding != null) {
				request.setMerchantId(String.valueOf(dmoOnboarding.getEazypayMerchantID()));
				request.setSubMerchantId(String.valueOf(dmoOnboarding.getEazypayMerchantID()));
				request.setTerminalId(String.valueOf(dmoOnboarding.getMccCode()));
				request.setMerchantTranId(transactionLog.getOrderNumber());
				request.setTransactionType("C");
			}
			log.info("Request Callback Status=================> " + request);

			Map<String, String> properties = new HashMap<String, String>();
			properties.put(IciciUtils.ICICI_CPAYPUBLICCER_KEY, propMap.get(IciciUtils.DMO_REFUND_CPAYPUBLICCER_KEY)); // enc
			properties.put(IciciUtils.GETEPAY_ICICI_PRIVATE_KEY, propMap.get(IciciUtils.DMO_REFUND_PRIVATE_KEY)); // dec

			Gson gson = new Gson();
			String requestString = gson.toJson(request);
			log.info("requestString : " + requestString);

			String requestId = "";
			IciciHybridRequest iciciHybridRequest = IciciCompositPayUtilSqs.hybridEncryption(properties, requestString,
					requestId);

			String encRequest = gson.toJson(iciciHybridRequest);
			log.info("encRequest : " + encRequest);

			CallDmo call = new CallDmo();

			String callbackstatusurl = propMap.get(IciciUtils.DMO_CALLBACK_STATUS_URL);
//			if(upiQrDetail.getMerchantGenre().equalsIgnoreCase("OFFLINE")) {
//			callbackstatusurl = callbackstatusurl.replace("{merchantId}", propMap.get(IciciUtils.DMO_PARENT_MERCHANTID));
//			}else {
			callbackstatusurl = callbackstatusurl.replace("{merchantId}", dmoMerchantIdOnline);
//			}
			log.info("DMo Merchant Onboarding url ========> " + callbackstatusurl);
			String responseString = DMOUtils.postApiDMO(callbackstatusurl, encRequest, apiKey);
			log.info("ICICI Requery responseString => " + responseString);
			IciciHybridRequest iciciHybridResponse = gson.fromJson(responseString, IciciHybridRequest.class);
			String decString = IciciCompositPayUtilSqs.hybridDeryption(properties, iciciHybridResponse);

			IciciCallbackStatusResponse iciciCallbackStatusResponse = gson.fromJson(decString,
					IciciCallbackStatusResponse.class);
			log.info("Dec String ======> " + decString);
			if (iciciCallbackStatusResponse.getStatus().equalsIgnoreCase("SUCCESS")) {
				responseCode = "00";
				txnStatus = "SUCCESS";
				transactionLog.setResponseCode(responseCode);
				transactionLog.setTxnStatus(txnStatus);
				transactionLog.setStage("Transaction is marked as success in requery. ");
//				//                                                    transactionLog.setBankErrorMsg("REVERSAL");

			} else if (iciciCallbackStatusResponse.getStatus().equalsIgnoreCase("FAILED")) {
				responseCode = "01";
				txnStatus = "FAILED";
				transactionLog.setResponseCode(responseCode);
				transactionLog.setTxnStatus(txnStatus);
				transactionLog.setStage("Transaction is marked as failed in requery ");
//				transactionLog.setBankErrorMsg("Transaction Timeout");
			}
			return transactionLog;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static TransactionLog doAxisNB(TransactionLog transactionLog, MerchantSetting merchantSetting,
			Map<String, String> propMap) {
		try {
			String responseCode = "01";
			String txnStatus = "FAILED";

			try {
				AxisNbDoubleVerificationRequest request = new AxisNbDoubleVerificationRequest();
				request.setAmt(String.valueOf(transactionLog.getAmt()));
				String txnDate = transactionLog.getDate().trim();
				try {
					txnDate = txnDate.split(Pattern.quote(" "))[0];
				} catch (Exception e) {
				}
				request.setDate(txnDate);
				request.setItc(transactionLog.getMerchanttxnid());
				request.setPayeeid(propMap.get(AES256EncDnc.AXIS_NB_PID));
				request.setPrn(String.valueOf(transactionLog.getTransactionId()));
				String checksumRequest = request.checksumpipeSeprated();
				log.info("Checksum Request================> " + checksumRequest);
				String checksumvalue = AES256EncDnc.getSHA256(checksumRequest);
				log.info("Checksum Caluclated====================> " + checksumvalue);
				request.setChksum(checksumvalue);
				String finalRequest = request.pipeSeprated();
				log.info("Final Request ========================> " + finalRequest);
				String encRequest = AES256EncDnc.encryptforpayment(finalRequest,
						propMap.get(AES256EncDnc.AXIS_DV_ENC_KEY));
				log.info("Enc Request============================> " + encRequest);
				AxisData axisdata = new AxisData();
				axisdata.setEncdata(encRequest);
				axisdata.setPayeeid(propMap.get(AES256EncDnc.AXIS_NB_PID));
				axisdata.setEnccat(propMap.get(AES256EncDnc.AXIS_NB_ENCCAT));
				axisdata.setMercat(propMap.get(AES256EncDnc.AXIS_NB_CATEGORY_ID));
				String response = AES256EncDnc.sendFormPostRequest(propMap.get(AES256EncDnc.AXIS_NB_VERIFICATION_URL),
						axisdata);
//					String response = null;
				log.info("Response========================================> " + response);
				String decryptedResponse = AES256EncDnc.decryptdoubleverification(response,
						propMap.get(AES256EncDnc.AXIS_DV_ENC_KEY));
				log.info("Decrypted Response=================================> " + decryptedResponse);
//				decryptedResponse = decryptedResponse.split("\\|")[0];
//				JAXBContext jaxbContext = JAXBContext.newInstance(DataSet.class);
//				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//				// jaxbUnmarshaller.setEventHandler(new DefaultValidationEventHandler());
//				DataSet dataSet = (DataSet) jaxbUnmarshaller.unmarshal(new StringReader(decryptedResponse));
//				List<Table1> table1 = dataSet.getTable1();
//				log.info("Table1 Data :  "  + table1);
//				Table1 table12 = new Table1();
//				for (Iterator iterator = table1.iterator(); iterator.hasNext();) {
//					table12 = (Table1) iterator.next();
//				}
//
//				if (table12.getPaymentStatus().equalsIgnoreCase("S")) {
//					responseCode = "00";
//					txnStatus = "SUCCESS";
//					transactionLog.setResponseCode(responseCode);
//					transactionLog.setTxnStatus(txnStatus);
//					transactionLog.setStage("Transaction is marked as success in requery. ");
//					//                                                    //                                                    transactionLog.setBankErrorMsg("REVERSAL");
//					return transactionLog;
//				} else {
//					responseCode = "01";
//					txnStatus = "FAILED";
//					transactionLog.setResponseCode(responseCode);
//					transactionLog.setTxnStatus(txnStatus);
//					transactionLog.setStage("Transaction is marked as failed in requery. ");
////					transactionLog.setBankErrorMsg("Transaction Timeout");
//					return transactionLog;
//				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

//	public static TransactionLog sbicardRequary(TransactionLog transactionLog, MerchantSetting merchantSetting) {
//		String responseCode = "01";
//		String txnStatus = "FAILED";
//		String setStage = "";
//		String processorcode = "";
//
//		try {
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		return null;
//	}
//
//	public static TransactionLog kotakBank(TransactionLog transactionLog, MerchantSetting merchantSetting,
//			Map<String, String> propertiesData) {
//
//		try {
//
//			String responseCode = "01";
//			String txnStatus = "";
//			SaleStatusQueryApiRequest salerequest = new SaleStatusQueryApiRequest();
//			salerequest.setBankId(merchantSetting.getSetting4());
//			salerequest.setMerchantId(merchantSetting.getMloginId());
//			salerequest.setTerminalId(merchantSetting.getSetting1());
//			salerequest.setOrderId(String.valueOf(transactionLog.getTransactionId()));
//			salerequest.setAccessCode(merchantSetting.getSetting2());
//			salerequest.setTxnType("Status");
//			salerequest.setSecureHash("");
//
//			SaleStatusQueryApiResponse saleStatusQueryApiResponse = KotakBankUtil.saleStatusApi(salerequest,
//					merchantSetting, propertiesData);
//
//			if (saleStatusQueryApiResponse != null
//					&& saleStatusQueryApiResponse.getResponseCode().equalsIgnoreCase("00")) {
//				responseCode = "00";
//				txnStatus = "SUCCESS";
//				transactionLog.setResponseCode(responseCode);
//				transactionLog.setTxnStatus(txnStatus);
//				transactionLog.setStage("Transaction is marked as success in requery. ");
//				transactionLog.setProcessorCode(saleStatusQueryApiResponse.getResponseCode());
//
//			} else {
//				try {
//					responseCode = "01";
//					// update transaction here as failed
//					txnStatus = "FAILED";
//					transactionLog.setResponseCode(responseCode);
//					transactionLog.setTxnStatus(txnStatus);
//					transactionLog.setStage("Transaction is marked as failed in requery");
//					transactionLog.setProcessorCode(saleStatusQueryApiResponse.getResponseCode());
//
//				} catch (Exception e) {
//					CustomExceptionLoggerError.customExceptionPrintTrace(log, e);
//				}
//
//			}
//			log.info(saleStatusQueryApiResponse);
//			return transactionLog;
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return null;
//	}

	public static TransactionLog sbinbRequary(TransactionLog transactionLog, MerchantSetting merchantSetting,
			Map<String, String> propertiesData, String requeryUrl) {
		try {
			String responseCode = "01";
			String txnStatus = "FAILED";
			SBIPaymentRequest sbirequest = new SBIPaymentRequest();
			BigDecimal amount = RequaryUtil.requaryAmount(transactionLog);
			sbirequest.setRef_no(String.valueOf(transactionLog.getTransactionId()));
			sbirequest.setAmount(String.valueOf(amount));
			sbirequest.setRedirect_url(propertiesData.get(SBIUtils.SBI_NB_RETURN_URL));
			sbirequest.setCrn("INR");
			sbirequest.setTransaction_category("INB");
			sbirequest.setCheckSum("");
			log.info("Checksum Request====> " + sbirequest.checksum());
			String pipeSepratedChecksum = SBIUtils.getSHA2Checksum(sbirequest.checksum());
			log.info("calculated Checksum====> " + pipeSepratedChecksum);
			log.info("pipeSepratedChecksum===> " + pipeSepratedChecksum);
			sbirequest.setCheckSum(pipeSepratedChecksum);

			String pipeSepratedfinal = sbirequest.checksumEncrypted();

			log.info(sbirequest.checksumEncrypted());

			log.info("final =>  " + pipeSepratedfinal);
			String encdata = SBIUtils.Encrypt(pipeSepratedfinal, propertiesData.get(SBIUtils.SBINB_KEYPATH));
			log.info("encryted Data=======> " + encdata);

			SBIRequestWrapper requestWrapper = new SBIRequestWrapper();
			requestWrapper.setEncdata(encdata);
			requestWrapper.setMerchant_code(merchantSetting.getSetting1());

			log.info("URL===> " + propertiesData.get(SBIUtils.SBI_NB_DOUBLE_VERIFICATION_API_URL));
			String response = SBIUtils.postapi2(propertiesData.get(SBIUtils.SBI_NB_DOUBLE_VERIFICATION_API_URL),
					requestWrapper);
			log.info("Response ====> " + response);
			String result = response.replaceAll("\n", "").replaceAll("\r", "");

			String decrypteddata = SBIUtils.Decrypt(result, propertiesData.get(SBIUtils.SBINB_KEYPATH));
			log.info("Decryptewd Data==> " + decrypteddata);
			SBIVerificaltionResponse sbiresponse = new SBIVerificaltionResponse(decrypteddata);

			if (sbiresponse != null && sbiresponse.getStatus().equals("Success")) {
				responseCode = "00";
				txnStatus = "SUCCESS";

				transactionLog.setResponseCode(responseCode);
				transactionLog.setTxnStatus(txnStatus);
				transactionLog.setStage("Transaction is marked as success in requery. ");
				transactionLog.setProcessorCode(sbiresponse.getBank_ref_no());
				// // transactionLog.setBankErrorMsg("REVERSAL");

			} else if (sbiresponse != null && sbiresponse.getStatus().equals("FAILED")) {
				responseCode = "01";
				txnStatus = sbiresponse.getStatus();
				transactionLog.setResponseCode(responseCode);
				transactionLog.setTxnStatus(txnStatus);
				transactionLog.setStage("Transaction is marked as failed in requery ");
				transactionLog.setProcessorCode(sbiresponse.getBank_ref_no());
//				transactionLog.setBankErrorMsg("Transaction Timeout");

			} else {
				log.info("SBINB Requery is Due=================>");
//				Utils.addRequaryInQueue(transactionLog.getTransactionId(), requeryUrl);
//				log.info("Requery ADD AGAIN IN QUEUE DUE TO PENDING OR INITIATE =================> "+transactionLog.getTransactionId());

			}
			return transactionLog;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

//	public static TransactionLog esafBankRequery(TransactionLog transactionLog, Map<String, String> propMap) {
//			log.info("Inside Esaf Bank Requery...");
//		
//		try {
//			String responseCode = "01";
//			String txnStatus = "FAILED";
//			Gson gson = new Gson();
//			
//			String esafBankResponse = EsafUtils.esafBankRequery(transactionLog,propMap);
//			MerchantRequaryResponse merchantRequaryResponse = gson.fromJson(esafBankResponse, MerchantRequaryResponse.class);
//			
//			log.info("Esaf Bank Requery Response : " + merchantRequaryResponse);
//			
//			if(merchantRequaryResponse != null && merchantRequaryResponse.getTxnStatus().equalsIgnoreCase("SUCCESS")) {
//				responseCode = "00";
//				txnStatus = "SUCCESS";
//				transactionLog.setTxnStatus(txnStatus);
//				transactionLog.setResponseCode(responseCode);
//				transactionLog.setStage("Transaction is marked as success in requery. ");
////				transactionLog.setProcessorCode(responseCode);
//				return transactionLog;
//			}else {
//				responseCode = "01";
//				txnStatus = "FAILED";
//				transactionLog.setResponseCode(responseCode);
//				transactionLog.setTxnStatus(txnStatus);
//				transactionLog.setStage("Transaction is marked as failed in requery ");
//			}
//			return transactionLog;
//			
//			
//			
//		}catch(Exception e) {
//			CustomExceptionLoggerError.customExceptionPrintTrace(log, e);
//		}
//		
//		
//		return null;
//	}
//	

	public static TransactionLog iciciNbRequery(TransactionLog transactionLog, MerchantSetting merchantSetting,
			Map<String, String> propMap, String requeryUrl) {
		log.info("<---------- Inside ICICI NB Requery ---------> ");

		try {
			String txnStatus = "FAILED";
			String url = propMap.get(ICICIBankUtils.ICICI_NB_VERIFICATION_URL_KEY);
			String apiUrl = propMap.get(ICICIBankUtils.ICICI_NB_REQUERY_URL);

			BigDecimal mAmount = RequaryUtil.requaryAmount(transactionLog);

			String dateTime = Utils.formatLocalDateTime(transactionLog.getCreatedDate());

			VerifyOutput response = null;
			if (transactionLog.getProcessorTxnId() != null) {

				url = url.replace("#login", merchantSetting.getMloginId());
				url = url.replace("#pid", merchantSetting.getSetting1());
				url = url.replace("#amt", mAmount.toString());
				url = url.replace("#crn", "INR");
				url = url.replace("#itc", transactionLog.getMerchanttxnid());
				url = url.replace("#md", "V");
				url = url.replace("#prn", String.valueOf(transactionLog.getTransactionId()));
				url = url.replace("#bid", String.valueOf(transactionLog.getProcessorTxnId()));
//				url = url.replace("#pmtDate", dateTime);

				log.info("redirect url => " + url);
				response = ICICIBankUtils.iciciNbVerification(url);
			} else {

				apiUrl = apiUrl.replace("#login", merchantSetting.getMloginId());
				apiUrl = apiUrl.replace("#pid", merchantSetting.getSetting1());
				apiUrl = apiUrl.replace("#amt", mAmount.toString());
				apiUrl = apiUrl.replace("#crn", "INR");
				apiUrl = apiUrl.replace("#itc", transactionLog.getMerchanttxnid());
				apiUrl = apiUrl.replace("#md", "V");
				apiUrl = apiUrl.replace("#prn", String.valueOf(transactionLog.getTransactionId()));
				url = url.replace("#bid", String.valueOf(transactionLog.getProcessorTxnId()));
				apiUrl = apiUrl.replace("#pmtDate", dateTime);
				log.info("redirect apiUrl => " + apiUrl);

				response = ICICIBankUtils.iciciNbVerification(apiUrl);
			}
			log.info("ICICINB response is " + response);

			if (response.getSTATUS().equalsIgnoreCase("SUCCESS")) {

				transactionLog.setResponseCode("00");
				transactionLog.setTxnStatus("SUCCESS");
				transactionLog.setStage("Transaction is marked as success in requery. ");
				// // transactionLog.setBankErrorMsg("REVERSAL");
			} else if (response.getSTATUS().equalsIgnoreCase("FAILED")) {
				txnStatus = "FAILED";
				transactionLog.setResponseCode("01");
				transactionLog.setTxnStatus(txnStatus);
				transactionLog.setStage("Transaction is marked as failed in requery ");
//				transactionLog.setBankErrorMsg("Transaction Timeout");
//				log.info("ICICINB Requery is Due OR Initiate =================>");
//				Utils.addRequaryInQueue(transactionLog.getTransactionId(), requeryUrl);
			}

			return transactionLog;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static TransactionLog kotakNBRequery(TransactionLog transactionLog, MerchantSetting merchantSetting,
			Map<String, String> propMap, String requeryUrl) {

		log.info("<=================Inside KOTAKNB Requery=====================> ");

		try {
			KotakRequeryApiCall kotakRequeryApiCall = new KotakRequeryApiCall();
//			kotakRequeryApiCall.setRefundApiUrl(propMap.get(KotakUtils.KOTAK_NB_REFUND_API_URL));
//			kotakRequeryApiCall.setApiKey(propMap.get(KotakUtils.KOTAK_NB_REFUND_API_KEY));
//			kotakRequeryApiCall.setCheckSumKey(propMap.get(KotakUtils.KOTAK_NB_REFUND_CHECKSUM_KEY));
//			kotakRequeryApiCall.setMerchantId(merchantSetting.getMloginId());
//			kotakRequeryApiCall.setGenerateTokenurl(propMap.get(KotakUtils.KOTAK_NB_REFUND_GENERATE_TOKEN_URL));
//			kotakRequeryApiCall.setClientId(propMap.get(KotakUtils.KOTAK_NB_REFUND_CLIENT_ID));
//			kotakRequeryApiCall.setClientSecret(propMap.get(KotakUtils.KOTAK_NB_REFUND_CLIENT_SECRET));
			kotakRequeryApiCall.setTxnDate(transactionLog.getCreatedDate());
			String refNo = kotakRequeryApiCall.getRefNo(kotakRequeryApiCall.getTxnDate(),
					transactionLog.getTransactionId());
			kotakRequeryApiCall.setMerchantReference(refNo);

			String generateTokenUrl = propMap.get(KotakUtils.KOTAK_NB_REFUND_GENERATE_TOKEN_URL);
			String client_id = propMap.get(KotakUtils.KOTAK_NB_REFUND_CLIENT_ID);
			String client_Secret = propMap.get(KotakUtils.KOTAK_NB_REFUND_CLIENT_SECRET);

			String token = KotakUtils.generateToken(generateTokenUrl, client_Secret, client_id);
			if (token == null) {
			}

			String dateAndTime = KotakUtils.dateAndTime(transactionLog.getDate());
			KotakRequeryRequest kotakRequeryRequest = new KotakRequeryRequest();

			kotakRequeryRequest.setMessageCode("0520");
			kotakRequeryRequest.setDateAndTime(dateAndTime);
			kotakRequeryRequest.setMerchantId(merchantSetting.getMloginId());
			kotakRequeryRequest.setMerchantReference(refNo);
			kotakRequeryRequest.setFuture1("");
			kotakRequeryRequest.setFuture2("");

			String msg = kotakRequeryRequest.getRequestToCalculateChecksum();

			String pipeSeprated = KotakUtils.getHMAC256Checksum(msg, propMap.get(KotakUtils.KOTAK_CHECKSUM_KEY));

			kotakRequeryRequest.setCheckSum(pipeSeprated);

			String finalRequest = kotakRequeryRequest.getRequestWithChecksum();

			String encryptRequest = KotakUtils.encrypt(finalRequest, propMap.get(KotakUtils.KOTAK_ENCRYPTION_KEY));

			String responseString = KotakUtils.postApi(propMap.get(KotakUtils.KOTAK_NB_REFUND_API_URL), encryptRequest,
					token);

			String decryptResponse = KotakUtils.decrypt(String.valueOf(responseString),
					propMap.get(KotakUtils.KOTAK_ENCRYPTION_KEY));
			KotakRequeryResponse response = new KotakRequeryResponse(decryptResponse);

//			KotakRequeryResponse response = kotakRequeryApiCall.kotakRequeryApi();

			if (response.getAuthStatus().equalsIgnoreCase("Y")) {
				transactionLog.setResponseCode("00");
				transactionLog.setTxnStatus("SUCCESS");
				transactionLog.setStage("Transaction is marked as success in requery. ");
				// // transactionLog.setBankErrorMsg("REVERSAL");
			} else {
				transactionLog.setResponseCode("01");
				transactionLog.setTxnStatus("FAILED");
				transactionLog.setStage("Transaction is marked as failed in requery. ");
//				transactionLog.setBankErrorMsg("Transaction Timeout");
			}
			return transactionLog;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public static TransactionLog sbiCardRequery(TransactionLog transactionLog, MerchantSetting merchantSetting,
			Map<String, String> propMap, String requeryUrl) {
		log.info("<========================Inside SBI Card Requery================================> ");
		try {
			AES aes = AES.init();
			Gson gson = new Gson();

			SbiRequestHeader header = new SbiRequestHeader();

			header.setXapikey(propMap.get(SbiTransactionStatusUtil.SBI_HEADER_XAPI_KEY));

			header.setPgInstanceId("72702415");
			header.setMerchantId("72702415");
			log.info("Header data =========> " + header);
			SbiTransactionStatusRequest saleauth = new SbiTransactionStatusRequest();

			BigDecimal mAmount = RequaryUtil.requaryAmount(transactionLog);
			log.info("Amount in Big Decimal =======================> " + mAmount);
			int amount = mAmount.multiply(new BigDecimal(100)).intValue();
			log.info("Amount in Integer =======================> " + amount);

			saleauth.setPgInstanceId(propMap.get(SbiTransactionStatusUtil.SBI_PG_INSTENCE_ID_AUTH_SALE));
			saleauth.setMerchantId(merchantSetting.getMloginId());
			saleauth.setAmount(String.valueOf(amount));
			saleauth.setCurrencyCode("356");
			saleauth.setMerchantReferenceNo(String.valueOf(transactionLog.getTransactionId()));
			String requestjson = gson.toJson(saleauth);
			log.info("SBI Request json for Requery ==================> " + saleauth
					+ " For transaction_id==================> " + transactionLog.getTransactionId());
			String encrequestjson = SbiTransactionStatusUtil.encrypt(requestjson, aes);

			log.info("SBI Cards Encrypted Requery Request ==================> " + encrequestjson
					+ " For transaction_id==================> " + transactionLog.getTransactionId());
			String saleStatusUrl = propMap.get(SbiTransactionStatusUtil.SBI_SALE_STATUS_URL);
			String response = SbiTransactionStatusUtil.postapi(saleStatusUrl, encrequestjson, header);

			log.info("SBI Cards Encrypted Requery Response ==================> " + response
					+ " For transaction_id==================> " + transactionLog.getTransactionId());

			EncryptedResponseData encryptedResponse = gson.fromJson(response, EncryptedResponseData.class);

			String decryptResponse = SbiTransactionStatusUtil.decypt(encryptedResponse, aes);

			if (decryptResponse == null || decryptResponse.isEmpty() || decryptResponse.equals("[]")) {
				log.info("SBI Cards Decrypted Requery Response ==================> " + decryptResponse
						+ " For transaction_id==================> " + transactionLog.getTransactionId());
//				transactionLog.setResponseCode("01");
//				transactionLog.setTxnStatus("FAILED");
//				transactionLog.setStage("Transaction is marked as failed in requery ");
//				transactionLog.setBankErrorMsg("Transaction Timeout");
				return transactionLog;

			} else {
				Type listType = new TypeToken<List<SbiTransactionStatusResponse>>() {
				}.getType();
				List<SbiTransactionStatusResponse> sbiResponses = gson.fromJson(decryptResponse, listType);

				SbiTransactionStatusResponse sbiResponse = sbiResponses.get(0);

//			SbiTransactionStatusResponse sbiResponse= gson.fromJson(decryptResponse,
//					SbiTransactionStatusResponse.class);

				log.info("SBI Cards Decrypted Requery Response ==================> " + sbiResponse
						+ " For transaction_id==================> " + transactionLog.getTransactionId());

				if (sbiResponse.getStatus().equalsIgnoreCase("50020")) {
//					log.info("SBI Cards Decrypted Requery Response in success");
					transactionLog.setResponseCode("00");
					transactionLog.setTxnStatus("SUCCESS");
					transactionLog.setStage("Transaction is marked as success in requery. ");

				} else if (sbiResponse.getStatus().equals("50021")) {
//					responseCode = "01";
//					txnStatus = "FAILED";
					transactionLog.setResponseCode("01");
					transactionLog.setTxnStatus("FAILED");
					transactionLog.setStage("Transaction is marked as failed in requery ");
//					transactionLog.setBankErrorMsg("Transaction Timeout");
				}
			}
			return transactionLog;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}

		return null;
	}

	public static TransactionLog hdfcCardsRequery(TransactionLog transactionLog, Map<String, String> propMap) {
		log.info("<===================HDFC BANK Requery  =================> ");
		try {

			BigDecimal mAmount = RequaryUtil.requaryAmount(transactionLog);

			HdfcRequest request = new HdfcRequest();
			request.setId(propMap.get(HdfcUtils.HDFC_CARDS_TRANPORTALID));
			request.setPassword("password1");
			request.setAction("8");
			request.setAmt(String.valueOf(mAmount));
			request.setTrackId(transactionLog.getTransactionId().toString());
			request.setMember(transactionLog.getCustomername());
			request.setTransid(transactionLog.getTransactionId().toString());
			request.setUdf5("TrackID");

			String xmlRequest = request.toXml();
			log.info("HDFC REQUEST  ==> " + xmlRequest);
			String apiUrl = propMap.get(HdfcUtils.HDFC_CARDS_REQUERY_URL);
			log.info("apiUrl  ==> " + apiUrl);
			HdfcResponse hdfcResponse = HdfcUtils.callApiUrl(apiUrl, xmlRequest);
			log.info("HDFC RESPONSE  ==> " + hdfcResponse);

			if (hdfcResponse.getResult().equalsIgnoreCase("SUCCESS")) {
				transactionLog.setResponseCode("00");
				transactionLog.setTxnStatus("SUCCESS");
				transactionLog.setStage("Transaction is marked as success in requery. ");
//          transactionLog.setBankErrorMsg("REVERSAL");
				return transactionLog;

			} else {

				transactionLog.setResponseCode("01");
				transactionLog.setTxnStatus("FAILED");
				transactionLog.setStage("Transaction is marked as failed in requery ");
//			transactionLog.setBankErrorMsg("Transaction Timeout");
				return transactionLog;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

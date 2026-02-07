package com.ftk.pg.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fss.plugin.bob.iPayPipe;
import com.ftk.pg.dto.PaymentRequest;
import com.ftk.pg.encryption.AuthEncryption;
import com.ftk.pg.encryption.ShoppingMallEncryptor;
import com.ftk.pg.encryption.TagitEncryption;
import com.ftk.pg.encryption.UcoEncryption;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.Bank;
import com.ftk.pg.modal.DmoOnboarding;
import com.ftk.pg.modal.KotakTransactionLog;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.modal.MerchantSetting;
import com.ftk.pg.modal.ProcessorBank;
import com.ftk.pg.modal.ProcessorBankHolder;
import com.ftk.pg.modal.ProcessorWallet;
import com.ftk.pg.modal.ProcessorWalletHolder;
import com.ftk.pg.modal.TransactionEssentials;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.modal.UpiQrDetail;
import com.ftk.pg.repo.BankRepo;
import com.ftk.pg.repo.DmoOnboardingRepo;
import com.ftk.pg.repo.KotakTransactionLogRepo;
import com.ftk.pg.repo.ProcessorBankHolderRepo;
import com.ftk.pg.repo.ProcessorBankRepo;
import com.ftk.pg.repo.ProcessorWalletHolderRepo;
import com.ftk.pg.repo.ProcessorWalletRepo;
import com.ftk.pg.repo.TransactionEssentialsRepo;
import com.ftk.pg.repo.TransactionLogRepo;
import com.ftk.pg.repo.UpiQrDetailRepo;
import com.ftk.pg.requestvo.Additional_Info;
import com.ftk.pg.requestvo.AuthenticationRequest;
import com.ftk.pg.requestvo.AxisRequest;
import com.ftk.pg.requestvo.BillDeskRequestHeader;
import com.ftk.pg.requestvo.CardDetailsVo;
import com.ftk.pg.requestvo.CashfreeRequestCustomer;
import com.ftk.pg.requestvo.CashfreeRequestOrder;
import com.ftk.pg.requestvo.CashfreeRequestOrderMeta;
import com.ftk.pg.requestvo.CashfreeRequestPayment;
import com.ftk.pg.requestvo.CashfreeRequestPaymentCard;
import com.ftk.pg.requestvo.CashfreeRequestPaymentCardWrapper;
import com.ftk.pg.requestvo.CashfreeRequestPaymentNetbanking;
import com.ftk.pg.requestvo.CashfreeRequestPaymentNetbankingWrapper;
import com.ftk.pg.requestvo.CashfreeRequestPaymentUpi;
import com.ftk.pg.requestvo.CashfreeRequestPaymentUpiWrapper;
import com.ftk.pg.requestvo.CreateTransactionRequest;
import com.ftk.pg.requestvo.CustDetails;
import com.ftk.pg.requestvo.Device;
import com.ftk.pg.requestvo.Extras;
import com.ftk.pg.requestvo.HeadDetails;
import com.ftk.pg.requestvo.IciciCollectResponse;
import com.ftk.pg.requestvo.IciciNetBankingRequest;
import com.ftk.pg.requestvo.MerchDetails;
import com.ftk.pg.requestvo.NorthAcrossPaymentRequest;
import com.ftk.pg.requestvo.NorthAcrossRequestWrapper;
import com.ftk.pg.requestvo.OtsTransaction;
import com.ftk.pg.requestvo.PayDetails;
import com.ftk.pg.requestvo.PayInstrument;
import com.ftk.pg.requestvo.PropertiesVo;
import com.ftk.pg.requestvo.RblChnlPrtnrLoginResVo;
import com.ftk.pg.requestvo.RedirectionRequestParameters;
import com.ftk.pg.requestvo.RupayEncryptedData;
import com.ftk.pg.requestvo.RupayRedirectData;
import com.ftk.pg.requestvo.ServerResponse;
import com.ftk.pg.requestvo.TokenHeader;
import com.ftk.pg.requestvo.UcoRequestVo;
import com.ftk.pg.responsevo.AuthenticationResponse;
import com.ftk.pg.responsevo.CashfreeResponseOrder;
import com.ftk.pg.responsevo.CashfreeResponsePayment;
import com.ftk.pg.responsevo.CreateTransactionResponse;
import com.ftk.pg.responsevo.Initiate2Response;
import com.ftk.pg.responsevo.ParqAuthenticationResponse;
import com.ftk.pg.responsevo.PaymentResponse;
import com.ftk.pg.responsevo.PvrqVersioningResponse;
import com.ftk.pg.responsevo.RupayCheckbinResponse;
import com.ftk.pg.util.AxisUtils;
import com.ftk.pg.util.BillDeskUtils;
import com.ftk.pg.util.Charge;
import com.ftk.pg.util.ComponentUtils;
import com.ftk.pg.util.EncryptDecryptUtil;
import com.ftk.pg.util.ICICIBankUtils;
import com.ftk.pg.util.IDBIVANUtils;
import com.ftk.pg.util.IciciCompositPay;
import com.ftk.pg.util.JoseHelper;
import com.ftk.pg.util.KotakUtil;
import com.ftk.pg.util.NCrossUtils;
import com.ftk.pg.util.NbblEncryptionUtil;
import com.ftk.pg.util.NbblUtil;
import com.ftk.pg.util.NorthAcrossUtil;
import com.ftk.pg.util.NorthAcrossUtil.SSLHelper;
import com.ftk.pg.vo.nbbl.NbblRequestWrapper;
import com.ftk.pg.vo.nbbl.Signature;
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
import com.ftk.pg.vo.sbiNb.SBIPaymentRequest;
import com.ftk.pg.vo.sbiNb.SBIRequestWrapper;
import com.ftk.pg.vo.sbiNb.SBITokenResponse;
import com.ftk.pg.vo.sbiNb.SbiRequestHeader;
import com.ftk.pg.vo.yesBankNB.YesBankNBRequest;
import com.ftk.pg.util.PGUtility;
import com.ftk.pg.util.PayubizUtil;
import com.ftk.pg.util.RemoteDbUtil;
import com.ftk.pg.util.SBICardsUtils;
import com.ftk.pg.util.SBIUtils;
import com.ftk.pg.util.SbiCardUtilCall;
import com.ftk.pg.util.SimulatorUtils;
import com.ftk.pg.util.UcoBankUtils;
import com.ftk.pg.util.UserCardDetails;
import com.ftk.pg.util.VerifyJWS;
import com.ftk.pg.util.YESBankUtils;
import com.google.gson.Gson;
import com.mb.getepay.icici.lyra.LyraCard;
import com.mb.getepay.icici.lyra.LyraCharge;
import com.mb.getepay.icici.lyra.LyraChargeResponse;
import com.mb.getepay.icici.lyra.LyraCustomer;
import com.mb.getepay.icici.lyra.LyraNetbanking;
import com.mb.getepay.icici.lyra.LyraTransactionResponse;
import com.mb.getepay.icici.lyra.LyraUpi;
import com.mb.getepay.icici.lyra.LyraUtil;
import com.mb.getepay.icici.lyra.LyraWallet;
import com.mb.getepay.icici.lyra.LyraWebhook;
import com.mb.getepay.icici.lyra.action.Call;
import com.paynetz.payment.PaynetzPayment;
import com.paynetz.pojo.Config;
import com.paynetz.pojo.PaynetzRequest;
import com.paynetz.pojo.PaynetzResponse;
import com.pgcomponent.security.SecureCardData;

import intech.vas.bean.VASBean;
import intech.vas.process.VanAcct;
import intech.vas.process.VanAcctService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PgService {

	private final PropertiesService propertiesService;

	private final TransactionLogRepo transactionLogRepo;

	private final MerchantCommisionService merchantCommisionService;

	private final ConvenienceChragesService convenienceChargesService;

	private final ProcessorWalletRepo processorWalletRepo;

	private final ProcessorBankRepo processorBankRepo;

	private final BankRepo bankRepo;

	private final TransactionEssentialsRepo transactionEssentialsRepo;

	private final UpiQrDetailRepo upiQrDetailRepo;

	private final DmoOnboardingRepo dmoOnboardingRepo;

	private final ProcessorBankHolderRepo processorBankHolderRepo;

	private final ProcessorWalletHolderRepo processorWalletHolderRepo;

	private final KotakTransactionLogRepo kotakTransactionLogRepo;

	Logger logger = LogManager.getLogger(PgService.class);

	public PaymentResponse auBankPayment(PaymentRequest pgRequest, Merchant merchant, MerchantSetting merchantSetting) {
		logger.info("<---------- Inside AU NB Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		Map<String, String> propMap = new HashMap<String, String>();
		List<PropertiesVo> PropertiesList = propertiesService.findByPropertykeyWithUpdatedCertsLike("AUNB_");
		for (PropertiesVo property : PropertiesList) {
			propMap.put(property.getPropertyKey(), property.getPropertyValue());
		}
		PaymentResponse response = new PaymentResponse();
		try {
			UserCardDetails cardDetails = PGUtility.parseCardDetails(pgRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}
			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();
			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();

			}

			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					cardDetails);

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				TransactionLog tLog = transactionLogRepo.save(transactionLog);
				transactionLog.setTransactionId(tLog.getTransactionId());
			}

			String merchantCode = propMap.get("AUNB_MERCHANT_CODE");
			String encryptionKey = propMap.get("AUNB_ENCRYPTION_KEY");
			String checksumKey = propMap.get("AUNB_CHECKSUM_KEY");

			logger.info("Merchant code=>" + merchantCode);

			String merchantRefNumber = "";
			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantRefNumber = pgRequest.getTransactionId().toString();
			} else {
				merchantRefNumber = transactionLog.getTransactionId().toString();
			}
			logger.info("Transaction id in AU=>" + merchantRefNumber);
			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);

			String transactionAmt = mAmount.setScale(2, RoundingMode.HALF_UP).toString();
			String txnCurrency = pgRequest.getTxncurr();

			String retURL = propMap.get("AUNB_RU_V2");
			String channel = propMap.get("AUNB_CHANNEL");

			String merURL = "https://pay.getepay.in";
			String customerAccNo = propMap.get("AUNB_ACCOUNTNO");

			String encoded = TagitEncryption.tagitEncrypt(checksumKey, "HmacSHA256", encryptionKey,
					"AES/ECB/PKCS5Padding", merchantRefNumber, transactionAmt, "INR", retURL, "WEB", merURL,
					merchantCode);

			String url = propMap.get("AUNB_URL");
			url = url + "?" + encoded;

			logger.info("Au redirect url=>" + url);

			response.setResponseCode("02");
			response.setProcessorCode("475");
			response.setDescription("3d secure verifcation is pending");
			response.setThreeDSecureUrl(url);
			response.setStatus("success");
			response.setResult(true);
			response.setPost(true);
			return response;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		}

	}

	public PaymentResponse paynetzPayment(PaymentRequest pgRequest, Merchant merchant,
			MerchantSetting merchantSetting) {

		logger.info("/*************** Welcome to API service Call   ***********/");
		PaymentResponse response = new PaymentResponse();
		BeanUtils.copyProperties(pgRequest, response);
		String paymentMode = pgRequest.getPaymentMode();
		String carddata = pgRequest.getCarddata();
		String[] carddata_enc = new String[4];
		String cardno = "";
		String cardtype = "";
		String carddatadec = "";
		String cardassociate = "";

		logger.info(cardassociate);
		String cardholdername = "";
		String cardCvv = "";
		String expiryYear = "";
		String expiryMonth = "";
		Long transactionId = null;
		String privateKey = "";
		String maskedCardNo = "";

		privateKey = merchant.getMerchantPrivateKey();
		logger.info("Private Key == " + privateKey);
		if (!paymentMode.equalsIgnoreCase("nb")) {
			carddata_enc = carddata.split("\\|");
			cardno = carddata_enc[0];
			if (!paymentMode.equalsIgnoreCase("wallet") && !paymentMode.equalsIgnoreCase("upi")) {
				cardtype = carddata_enc[2].toUpperCase();
				cardassociate = carddata_enc[3];
				cardholdername = carddata_enc[1];
			}
			try {
				SecureCardData obj1 = new SecureCardData();
				logger.info("Encrypt card data - " + cardno + " ------- " + pgRequest.getMerchantTxnId());
				carddatadec = obj1.decryptData(cardno, privateKey);
			} catch (Exception e) {
				response.setResponseCode("01");
				response.setDescription("Unable to decrypt Card-data");
				return response;
			}
		}
		String temp = "";
		if (carddatadec.contains("|")) {
			try {
				String card_data_array[] = carddatadec.split("\\|");
				cardno = card_data_array[0];
				cardCvv = card_data_array[1];
				expiryYear = card_data_array[2];
				expiryMonth = card_data_array[3];
				temp = card_data_array[3] + "" + card_data_array[2].substring(2, 4) + "" + card_data_array[1];

				maskedCardNo = PGUtility.maskCard(cardno);

			} catch (Exception e) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}
		} else {
			cardno = carddatadec;
		}
		String requestType = pgRequest.getRequestType();
		logger.info("Request Type ==> " + requestType);
		TransactionLog transactionLog = new TransactionLog();
		transactionLog.setUdf9(maskedCardNo);
		try {
			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				logger.info("finding transaction log ==> " + requestType);
				transactionId = pgRequest.getTransactionId();
				transactionLog = transactionLogRepo.findById(transactionId).get();
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			transactionLog = new TransactionLog();
		}
		try {
			if (!carddata.trim().equals("") || carddata != null) {
				SecureCardData secureCardData = new SecureCardData();
				String[] data = carddata.split("\\|");
				String decryptBaseData = secureCardData.decryptData(data[0], privateKey);
//				 PropertiesVo properties = propertiesService.findByPropertykeyWithUpdatedCerts(PGUtility.AWS_ARN_KEY);
//				KMS kms = new KMS(properties.getPropertyValue());
				String[] cardDataArray = decryptBaseData.split("\\|");
				if (cardDataArray != null && cardDataArray.length == 4) {
					String cdStr = cardDataArray[0] + "|" + cardDataArray[2] + "|" + cardDataArray[3];
					// transactionLog.setCarddata(kms.encrypt(cdStr));
					transactionLog.setCarddata("");
				} else {
					transactionLog.setCarddata("");
				}
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			logger.info("Unable to encrypt carddara, thus setting as blank..");
			transactionLog.setCarddata("");
		}

//		try {
		transactionLog.setUdf10(cardassociate);
		// transactionLog.setAmt(pgRequest.getAmt());
		transactionLog.setBankId(pgRequest.getBankid());
		if (pgRequest.getBankid() != null && !pgRequest.getBankid().equals("") && paymentMode.equalsIgnoreCase("nb")) {
			Bank bank = bankRepo.findById(Long.valueOf(pgRequest.getBankid())).get();
			transactionLog.setBankname(bank.getBankName());
		}
		// transactionLog.setCardExpCVV(temp);
		// transactionLog.setCardNo(cardno);
		transactionLog.setChannelType("Ecom");
		transactionLog.setCustomername(cardholdername);
		transactionLog.setDate(pgRequest.getDate());
		transactionLog.setMerchantId(merchant.getMid());
		transactionLog.setMerchantName(merchant.getMerchantName());
		transactionLog.setMerchanttxnid(pgRequest.getMerchantTxnId());
		transactionLog.setMobile(pgRequest.getMobile());
		transactionLog.setOd(pgRequest.getOd());
		transactionLog.setProcessor("PAYNETZ");
		transactionLog.setResponseCode("02");
		transactionLog.setTxnStatus("PENDING");
		transactionLog.setTotalrefundAmt(BigDecimal.ZERO);
		transactionLog.setRefundStatus("");
		transactionLog.setMerchantSettingId(merchantSetting.getMerchantSettingId());

		logger.info("setting payment modes ==>  " + paymentMode);
		if (paymentMode.equalsIgnoreCase("wallet") || paymentMode.equalsIgnoreCase("upi")) {
			transactionLog.setPaymentMode("Wallet/Other");
		} else if (paymentMode.equalsIgnoreCase("nb")) {
			transactionLog.setPaymentMode("NB");

		} else if (paymentMode.equalsIgnoreCase("CC") || paymentMode.equalsIgnoreCase("DC")) {
			transactionLog.setPaymentMode(cardtype);
		} else {
			transactionLog.setPaymentMode(cardtype);
		}
		// transactionLog.setRu(pgRequest.getRu());
		transactionLog.setStage("Transaction is in process");
		transactionLog.setTxncurr(pgRequest.getTxncurr().toUpperCase());
//		} catch (NumberFormatException e1) {
//			new GlobalExceptionHandler().customException(e1);
//		}
		logger.info("Request Type 2 ==> " + requestType);
		if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
			try {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
			} catch (Exception e) {
				new GlobalExceptionHandler().customException(e);
			}
		} else {
			logger.info("in else as req type is not payment pages ==> " + requestType);
			try {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
			} catch (Exception e) {
				new GlobalExceptionHandler().customException(e);
			}
			transactionLog.setAmt(pgRequest.getAmt());
			transactionLog.setUdf1(pgRequest.getUdf1());
			transactionLog.setUdf2(pgRequest.getUdf2());
			transactionLog.setUdf3(pgRequest.getUdf3());
			transactionLog.setUdf4(pgRequest.getUdf4());
			transactionLog.setUdf5(pgRequest.getUdf5());
			transactionLog.setRu(pgRequest.getRu());
			transactionLog.setTransactionId(transactionLog.getTransactionId());
			logger.info("Transaction Id in not payment pages type ==> " + transactionId);
		}
		transactionLog = transactionLogRepo.save(transactionLog);
		String walletBankId = "";
		String mdd = "";
		String mobile = "";
		String bankid = pgRequest.getBankid();
		ProcessorWallet processorWallet = null;
		if (paymentMode.equalsIgnoreCase("wallet")) {
			response.setPaymentMode("wallet");
			processorWallet = new ProcessorWallet();
			processorWallet = processorWalletRepo.findByWalletIdAndMId(processorWallet.getWalletId(),
					processorWallet.getmId());
			if (processorWallet != null) {
				mdd = "MW|SMSMW|" + processorWallet.getProcessorwalletId();
				walletBankId = merchantSetting.getSetting6();
				mobile = cardno;
				cardno = "4111111111111111";
			} else {
				logger.error("Error in wallet not configure for this merchant at time :: " + LocalDateTime.now());
				response.setResponseCode("01");
				response.setDescription("wallet not configure for this merchant");
				response.setStatus("Failed");
				return response;
			}
		} else if (paymentMode.equalsIgnoreCase("upi")) {
			mdd = "UP|SMSUPI|" + cardno;
			walletBankId = merchantSetting.getSetting7();
			cardno = "4111111111111111";
			response.setPaymentMode("UPI");
		} else if (paymentMode.equalsIgnoreCase("nb")) {
			logger.info("Payment Mode nb ==> " + paymentMode);
			// new code
			mdd = "NB";
			response.setPaymentMode(mdd);
			ProcessorBank processorBank = new ProcessorBank();
			processorBank.setmId(merchant.getMid());
			processorBank.setBankId(Long.parseLong(pgRequest.getBankid()));
			processorBank.setStatus(true);
			processorBank.setProcessor("PAYNETZ");
			processorBank = processorBankRepo.findByBankIdAndProcessorAndMIdAndStatus(processorBank.getBankId(),
					processorBank.getProcessor(), processorBank.getmId(), processorBank.isStatus());
			if (processorBank != null) {
				cardno = "4111111111111111";
				bankid = String.valueOf(processorBank.getProcessorBankId());
			} else {
				response.setResponseCode("01");
				response.setDescription("bank not configure for this merchant");
				response.setStatus("Failed");
				return response;
			}
		} else {
			mdd = "";
		}
		try {
			logger.info("Verified, now setting paynetzconfig ");
			logger.info("inside paynetz Integration");

			Config paynetzConfig = new Config();
			String loginId = merchantSetting.getMloginId();
			String pwd = merchantSetting.getmPassword();
			String prodid = merchantSetting.getSetting1();
			String reqHashKey = merchantSetting.getSetting2();

			paynetzConfig.setLoginId(loginId);
			paynetzConfig.setPass(pwd);
			paynetzConfig.setProdId(prodid);
			paynetzConfig.setRequestHashKey(reqHashKey);
			paynetzConfig.setUrl(merchantSetting.getSetting6());
			paynetzConfig.setSeamlessPayment(true);

			PaynetzRequest paynetzrequest = new PaynetzRequest();
			PaynetzPayment payment = new PaynetzPayment(paynetzConfig);

			// String mAmount =
			// String.valueOf(Math.round(Float.parseFloat(String.valueOf(pgRequest.getAmt()))));
			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);

			paynetzrequest.setAmt(mAmount.toString());
			paynetzrequest.setCustomerBillingAddress("");
			paynetzrequest.setCustomerEmailId("");
			paynetzrequest.setCustomerMobileNo(mobile);
			paynetzrequest.setCustomerName(cardholdername);
			SimpleDateFormat paynetzDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dates = paynetzDate.format(new Date());
			paynetzrequest.setDate(dates);
			byte[] encodedBytes = java.util.Base64.getEncoder().encode("502020000392".getBytes());

			logger.info("Received request type=>" + requestType);
			// paynetzrequest.setRu("https://pay.getepay.in/getePaymentPages/paynetzResponse/");
			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				paynetzrequest.setRu(pgRequest.getRu());
			} else {
				// paynetzrequest.setRu("https://pay.getepay.in/getePaymentPages/paynetzResponse/");
				paynetzrequest.setRu(merchantSetting.getSetting7());
			}

			// paynetzrequest.setRu(pgRequest.getRu());

			paynetzrequest.setTxnCurr(pgRequest.getTxncurr().toUpperCase());
			paynetzrequest.setTxnId(String.valueOf(transactionId));
			paynetzrequest.setClientCode(new String(encodedBytes));
			paynetzrequest.setTxnscAmt("0");
			paynetzrequest.setCustAcc("123456789");
			paynetzrequest.setCardNumber(cardno);
			paynetzrequest.setCvv(cardCvv);
			paynetzrequest.setExpiryMonth(expiryMonth);
			paynetzrequest.setExpiryYear(expiryYear);
			paynetzrequest.setMdd(mdd);

			if (paymentMode != null
					&& (paymentMode.equalsIgnoreCase("wallet") || paymentMode.equalsIgnoreCase("upi"))) {
				paynetzrequest.setBankid(walletBankId);
			} else if (paymentMode != null && paymentMode.equalsIgnoreCase("nb")) {
				paynetzrequest.setBankid(bankid);
			}

			if (cardtype.equalsIgnoreCase("CC")) {
				paynetzrequest.setCardType("CC");
				response.setPaymentMode("CC");
			} else if (cardtype.equalsIgnoreCase("DC")) {
				paynetzrequest.setCardType("DC");
				response.setPaymentMode("DC");
			} else {
				paynetzrequest.setCardType("NB");
				response.setPaymentMode("NB");
			}

			PaynetzResponse processPayment = payment.processPayment(paynetzrequest, privateKey);
			// PaynetzResponse processPayment = payment.processPayment(paynetzrequest);
			logger.info(processPayment.getRedirectUrl());

			logger.info("+++++++++++++++++++++" + String.valueOf(processPayment.getStatus()));
			logger.info(String.valueOf(processPayment.getMessage()));
			logger.info(String.valueOf(processPayment.getRedirectUrl()));
			if (processPayment.getStatus() == (PaynetzPayment.VALIDATION_SUCCESS)) {
				response.setResponseCode("02");
				response.setProcessorCode("475");
				response.setDescription("3d secure verifcation is pending");
				response.setThreeDSecureUrl(processPayment.getRedirectUrl());
				response.setStatus("success");
				response.setResult(true);
				try {
					logger.info(
							"Insert in DB after getting bank verification response : " + pgRequest.getMerchantTxnId());
					// update transaction as success here
				} catch (Exception e) {
					logger.error("Error in DB after getting bank verification response :: "
							+ pgRequest.getMerchantTxnId() + " :: " + LocalDateTime.now());
					logger.info("Error in DB after getting bank verification response :: " + e.getMessage());
				}
			} else {
				response.setResponseCode("01");
				response.setDescription("Failed");
				response.setStatus("failed");
				response.setResult(false);
			}
		} catch (Exception e) {
			response.setResponseCode("01");
			new GlobalExceptionHandler().customException(e);
		}
		logger.info("Error in verification response :: " + response);
		return response;

	}

	public PaymentResponse rblPayment(PaymentRequest pgRequest, Merchant merchant, MerchantSetting merchantSetting) {

		logger.info("<---------- Inside UPI Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		PaymentResponse response = new PaymentResponse();
		RestTemplate restTemplate = new RestTemplate();

		Map<String, PropertiesVo> propMap = new HashMap<>();

		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property);
			}

			logger.info("<---------- Inside Channel Partner --------->");

			try {

				RblChnlPrtnrLoginResVo enqResVo = PGUtility.rblRequest2(propMap);

				logger.info("Response=>" + enqResVo);

			} catch (Exception e) {
				new GlobalExceptionHandler().customException(e);
			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return response;

	}

	public PaymentResponse bobPayment(PaymentRequest pgRequest, Merchant merchant, MerchantSetting merchantSetting) {

		logger.info("<---------- Inside BOB Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		PaymentResponse response = new PaymentResponse();
		Map<String, PropertiesVo> propMap = new HashMap<>();
		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property);
			}
			logger.info("<---------- Inside BOB Channel Partner --------->");

			UserCardDetails cardDetails = PGUtility.parseCardDetails(pgRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}

			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();

			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();

			}

			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					cardDetails);
			if (cardDetails != null && cardDetails.getCardProvider() != null
					&& cardDetails.getCardProvider().equalsIgnoreCase("MASTERCARD")) {
				transactionLog.setUdf10("MASTER");
			} else if (cardDetails != null && cardDetails.getCardProvider() != null) {
				transactionLog.setUdf10(cardDetails.getCardProvider());
			}

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				transactionLogRepo.save(transactionLog);
			}

			iPayPipe pipe = new iPayPipe();

			String keystorePath = merchantSetting.getSetting1();
			if (keystorePath == null || keystorePath.trim().equals("")) {
				keystorePath = propMap.get(PGUtility.BOB_PROPERTIES_PATH_KEY).getPropertyValue();
			}
//			if (keystorePath != null && keystorePath.startsWith("/opt/certs")) {
//				keystorePath = keystorePath.replace("/opt/certs", "/media/shared/opt/certs");
//			} 
//			else if (keystorePath != null && keystorePath.startsWith("/media/shared")) {
//				keystorePath = keystorePath.replace("/media/shared", "/mnt/efs");
//			}
			logger.info("keystore path=>" + keystorePath);

			pipe.setKeystorePath(keystorePath);
			pipe.setResourcePath(keystorePath);

			String alias = merchantSetting.getSetting2();
			if (alias == null || alias.trim().equals("")) {
				alias = propMap.get(PGUtility.BOB_ALIAS_KEY).getPropertyValue();
			}
//			if (alias != null && alias.startsWith("/opt/certs")) {
//				alias = alias.replace("/opt/certs", "/media/shared/opt/certs");
//			} 
//			else if (alias != null && alias.startsWith("/media/shared")) {
//				alias = alias.replace("/media/shared", "/mnt/efs");
//			}
			logger.info("keystore alias=>" + alias);
			pipe.setAlias(alias);
			pipe.setAction("1");

			pipe.setCurrency("356");
			if (merchantSetting.getCurrency().equalsIgnoreCase("USD")) {
				pipe.setCurrency("840");
			}

			pipe.setLanguage("USA");

			String ru = propMap.get(PGUtility.BOB_RU_KEY_V2).getPropertyValue();
			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				ru = ru + pgRequest.getTransactionId().toString();
			} else {
				ru = ru + transactionLog.getTransactionId().toString();
			}
			logger.info("RU in BOB=>" + ru);
			logger.info("RU in BOB=>" + ru);
			// ru = ru + pgRequest.getTransactionId().toString();bb
			pipe.setResponseURL(ru);
			pipe.setErrorURL(ru);

			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);

			pipe.setAmt(mAmount.toString());

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				pipe.setTrackId(pgRequest.getTransactionId().toString());
			} else {
				pipe.setTrackId(transactionLog.getTransactionId().toString());
			}

			// Merchant Name
			pipe.setUdf6(merchant.getMerchantName());
			pipe.setUdf7(cardDetails.getCustomerName());
			if (transactionLog.getUdf3() != null && !transactionLog.getUdf3().equals("")) {
				// pipe.setUdf8(transactionLog.getUdf3());
			} else {
				// pipe.setUdf8("customer@getepay.com");
			}
			if (transactionLog.getUdf2() != null && !transactionLog.getUdf2().equals("")) {
				pipe.setUdf9(transactionLog.getUdf2());
			} else {
				pipe.setUdf9("9999999999");
			}
			if (transactionLog.getUdf4() != null && !transactionLog.getUdf4().equals("")) {
				pipe.setUdf10(transactionLog.getUdf4());
			} else {
				pipe.setUdf10("customer address");
			}
			pipe.setUdf11(mAmount.toString());
			pipe.setUdf12(transactionLog.getMerchanttxnid());

			TransactionEssentials transactionEssentials = transactionEssentialsRepo
					.findByTransactionId(transactionLog.getTransactionId());
			if (transactionEssentials != null) {
				logger.info("browser details =>" + transactionEssentials.getUdf31());
				pipe.setUdf31(transactionEssentials.getUdf31());
			}

			if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("upi")) {
				pipe.setCard(cardDetails.getCardNo());
				// pipe.setMember(cardDetails.getCustomerName());
				pipe.setSavedCard("Y");
				pipe.setType("UPI_VPA");
			} else {
				pipe.setCard(cardDetails.getCardNo());
				pipe.setCvv2(cardDetails.getCvv());
				pipe.setExpMonth(cardDetails.getExpiryMonth());
				pipe.setExpYear(cardDetails.getExpiryYear());
				pipe.setMember(cardDetails.getCustomerName());
				pipe.setSavedCard("Y");

				if (cardDetails.getCardType() != null && cardDetails.getCardType().equalsIgnoreCase("CC")) {
					pipe.setType("C");
				} else {
					pipe.setType("D");
					logger.info("Card Provider=>" + cardDetails.getCardProvider());

				}

			}

			// pipe.setType(type);

			pipe.performVbVTransaction();

			String url = pipe.getWebAddress();
			logger.info("Bob redirect url=>" + url);
			response.setResponseCode("02");
			response.setProcessorCode("475");
			response.setDescription("3d secure verifcation is pending");
			response.setThreeDSecureUrl(url);
			response.setStatus("success");
			response.setResult(true);
			// return response;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		}
		return response;

	}

	public PaymentResponse idfcPayment(PaymentRequest pgRequest, Merchant merchant, MerchantSetting merchantSetting) {

		logger.info("<---------- Inside IDFC Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		Map<String, String> challanMAp = new HashMap<>();
		PaymentResponse response = new PaymentResponse();

		try {

			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();

			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();
			}
			Map<String, PropertiesVo> propMap = new HashMap<>();
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property);
			}

			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					null);

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				transactionLog = transactionLogRepo.save(transactionLog);
				transactionLog.setTransactionId(transactionLog.getTransactionId());
			}

			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);
			int amount = mAmount.multiply(new BigDecimal(100)).intValue();

			if (pgRequest != null && pgRequest.getPaymentType().equalsIgnoreCase("NEFT")) {

				String url = propMap.get(PGUtility.NEFT_CHALLAN_URL).getPropertyValue();

				url = url + "?cId=" + transactionLog.getTransactionId();

				logger.info("neft redirect url=>" + url);
				response.setResponseCode("02");
				response.setProcessorCode("475");
				response.setDescription("3d secure verifcation is pending");
				response.setThreeDSecureUrl(url);
				response.setStatus("success");
				response.setResult(true);
				// return response;

			}

			else if (pgRequest != null && pgRequest.getPaymentType().equalsIgnoreCase("CHALLAN")) {

				String benfiString = "GETEPAY" + "GETEP" + transactionLog.getTransactionId();

				transactionLog.setUdf5(benfiString);
				transactionLogRepo.save(transactionLog);

				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
				String challanDate = transactionLog.getCreatedDate().format(dtf);

				String challanValidity = transactionLog.getUdf6();
				if (challanValidity == null || challanValidity.equals("")) {
					LocalDateTime validity = transactionLog.getCreatedDate().plus(Period.ofDays(2));
					challanValidity = validity.format(dtf);
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date d = sdf.parse(challanValidity);
						SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy");
						challanValidity = sdf2.format(d);
					} catch (ParseException e) {
						new GlobalExceptionHandler().customException(e);
					}

				}

				challanMAp.put("tId", String.valueOf(transactionLog.getTransactionId()));
				challanMAp.put("merchantTxnId", transactionLog.getMerchanttxnid());
				challanMAp.put("Benificiary Account Number", benfiString);
				challanMAp.put("Benificiary Ifsc Code",
						propMap.get(ComponentUtils.CHALLAN_IFSC_CODE).getPropertyValue());
				challanMAp.put("TotalAmount", String.valueOf(mAmount));
				challanMAp.put("Benificiary Name", merchant.getMerchantName());
				challanMAp.put("Beneficiary Reference Number", String.valueOf(transactionLog.getTransactionId()));
				challanMAp.put("Challan Date", challanDate);
				challanMAp.put("Challan Validity", challanValidity);
				challanMAp.put("Amount", String.valueOf(transactionLog.getAmt()));
				challanMAp.put("TotalconvienceCharges", String.valueOf(transactionLog.getTotalServiceCharge()));
				challanMAp.put("TotalcomissionChages", String.valueOf(transactionLog.getCommision()));
				challanMAp.put("processor", merchantSetting.getProcessor());

				response.setPost(true);
				response.setPostReqParam(challanMAp);
				response.setResponseCode("02");
				response.setProcessorCode("475");
				response.setDescription("3d secure verifcation is pending");
				response.setThreeDSecureUrl(propMap.get(ComponentUtils.CHALLAN_RU).getPropertyValue());
				response.setStatus("success");
				response.setResult(true);

			}
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		}
		return response;

	}

	public PaymentResponse iciciCollectPayment(PaymentRequest pgRequest, Merchant merchant,
			MerchantSetting merchantSetting) {

		logger.info("<---------- Inside ICICI Collect Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		PaymentResponse response = new PaymentResponse();
		Map<String, String> propMap = new HashMap<String, String>();
		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				if (property.getPropertyKey().equalsIgnoreCase("ICICI_COLLECTURL_KEY")) {
					String pvalue = property.getPropertyValue() + merchantSetting.getMloginId();
					propMap.put(property.getPropertyKey(), pvalue);
				} else {
					propMap.put(property.getPropertyKey(), property.getPropertyValue());
				}

			}
			logger.info("<---------- Inside ICICI Collect Payment --------->");

			UserCardDetails cardDetails = PGUtility.parseCardDetails(pgRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}

			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();

			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();

			}

			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					cardDetails);
			if (cardDetails != null && cardDetails.getCardProvider() != null
					&& cardDetails.getCardProvider().equalsIgnoreCase("MASTERCARD")) {
				transactionLog.setUdf10("MASTER");
			} else if (cardDetails != null && cardDetails.getCardProvider() != null) {
				transactionLog.setUdf10(cardDetails.getCardProvider());
			}

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				transactionLog = transactionLogRepo.save(transactionLog);
				transactionLog.setTransactionId(transactionLog.getTransactionId());
			}

			UpiQrDetail upiQrDetail = new UpiQrDetail();
			DmoOnboarding dmoOnboarding = new DmoOnboarding();

			try {
				upiQrDetail = upiQrDetailRepo.findByMidAndEnable(transactionLog.getMerchantId(), true);
				dmoOnboarding = dmoOnboardingRepo.findByVpa(upiQrDetail.getVpa());
			} catch (Exception e) {

				new GlobalExceptionHandler().customException(e);
				response.setResponseCode("01");
				response.setDescription("vpa is Null or merchant is Not Dmo Onboarded");
				response.setResult(false);
				return response;
			}

			IciciCollectResponse collectResponse = new IciciCollectResponse();
			TransactionEssentials txnEssential = transactionEssentialsRepo
					.findByTransactionId(transactionLog.getTransactionId());

			String updatedIciciCollectEnable = propMap.get(IciciCompositPay.UPDATED_ICICI_COLLECT_ENABLE);

			if (updatedIciciCollectEnable.equalsIgnoreCase("true")) {

				String merchantlist = propMap.get(IciciCompositPay.UPDATED_ICICI_COLLECT_MID_ENABLE);
				boolean isMidsPresent = false;
				if (merchantlist != null && !merchantlist.equalsIgnoreCase("")) {
					String[] midArrays = merchantlist.split(",");
					String mercId = String.valueOf(merchant.getMid());
					for (String mid : midArrays) {
						if (mid.equalsIgnoreCase(mercId)) {
							isMidsPresent = true;
							break;
						}
					}

				}

				if (isMidsPresent) {

					collectResponse = IciciCompositPay.iciciUpiCollectupdated(txnEssential, propMap, transactionLog,
							merchant, merchantSetting, cardDetails.getCardNo(), dmoOnboarding);

					if (collectResponse != null && collectResponse.getResponse() != null
							&& collectResponse.getResponse().equalsIgnoreCase("5009")) {
						logger.info("Collect Response when Response is 5009==============>");
						collectResponse = IciciCompositPay.iciciUpiCollectupdated(txnEssential, propMap, transactionLog,
								merchant, merchantSetting, cardDetails.getCardNo(), dmoOnboarding);
					}

				} else {
					collectResponse = IciciCompositPay.iciciUpiCollect(propMap, transactionLog, merchant,
							merchantSetting, cardDetails.getCardNo());

					if (collectResponse != null && collectResponse.getResponse() != null
							&& collectResponse.getResponse().equalsIgnoreCase("5009")) {
						logger.info("Collect Response when Response is 5009==============>");
						collectResponse = IciciCompositPay.iciciUpiCollect(propMap, transactionLog, merchant,
								merchantSetting, cardDetails.getCardNo());
					}
				}

			} else {

				collectResponse = IciciCompositPay.iciciUpiCollect(propMap, transactionLog, merchant, merchantSetting,
						cardDetails.getCardNo());

				if (collectResponse != null && collectResponse.getResponse() != null
						&& collectResponse.getResponse().equalsIgnoreCase("5009")) {
					logger.info("Collect Response when Response is 5009==============>");
					collectResponse = IciciCompositPay.iciciUpiCollect(propMap, transactionLog, merchant,
							merchantSetting, cardDetails.getCardNo());
				}
			}

			String url = propMap.get(PGUtility.ICICI_COLLECT_URL);
			url = url + "?tId=" + transactionLog.getTransactionId();

			response.setStatus("success");
			response.setResponseCode("02");
			response.setProcessorCode("475");
			response.setDescription("3d secure verifcation is pending");
			response.setThreeDSecureUrl(url);
			response.setResult(true);

			if (collectResponse != null && collectResponse.getResponse() != null && collectResponse.getSuccess() != null
					&& collectResponse.getSuccess().equals("true") && collectResponse.getResponse().equals("92")) {
				response.setStatus("success");
				response.setResponseCode("02");
				response.setProcessorCode("475");
				response.setDescription("3d secure verifcation is pending");
				response.setThreeDSecureUrl(url);
				response.setResult(true);
				transactionLog.setProcessorCode("475");
				transactionLog.setResponseCode("02");
				// transactionLogDao.update(transactionLog);
				transactionLog.setUdf7(collectResponse.getBankRRN());
				transactionLog.setOrderNumber(collectResponse.getBankRRN());
			} else {
				transactionLog.setTxnStatus("FAILED");
				transactionLog.setProcessorCode("01");
				transactionLog.setResponseCode("01");
				transactionLog.setStage("Transaction is failed");
				response.setResult(false);
				response.setStatus("failed");
				response.setResponseCode("01");
				response.setDescription("Unable to process your transaction");
			}

			transactionLogRepo.save(transactionLog);
			response.setTransactionId(transactionLog.getTransactionId());
			// return response;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		}
		return response;

	}

	public PaymentResponse payuPayment(PaymentRequest pgRequest, Merchant merchant, MerchantSetting merchantSetting) {

		logger.info("<---------- Inside Payu Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		PaymentResponse response = new PaymentResponse();
		Map<String, PropertiesVo> propMap = new HashMap<>();
		// Map<String, String> postReqParam = new HashMap<>();
		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property);
			}
			String url = propMap.get(PayubizUtil.PAYU_URL_KEY).getPropertyValue();
			UserCardDetails cardDetails = PGUtility.parseCardDetails(pgRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}

			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();

			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();

			}

			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					cardDetails);
			if (cardDetails != null && cardDetails.getCardProvider() != null
					&& cardDetails.getCardProvider().equalsIgnoreCase("MASTERCARD")) {
				transactionLog.setUdf10("MASTER");
			} else if (cardDetails != null && cardDetails.getCardProvider() != null) {
				transactionLog.setUdf10(cardDetails.getCardProvider());
			}

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				transactionLog = transactionLogRepo.save(transactionLog);
				transactionLog.setTransactionId(transactionLog.getTransactionId());
			}

			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);
			// set data for generate signature
			Map<String, String> payuParams = new HashMap<String, String>();
			payuParams.put("key", merchantSetting.getMloginId());
			// payuParams.put("SALT", merchantSetting.getmPassword());
			// payuParams.put("key", "pX5KpI");
			// payuParams.put("SALT", "DQjAS2Sq");
			payuParams.put("txnid", transactionLog.getTransactionId().toString());
			payuParams.put("amount", mAmount.toString());
			payuParams.put("productinfo", PayubizUtil.PAYU_PRODUCT_INFO);
			payuParams.put("firstname", pgRequest.getName());
			payuParams.put("email", pgRequest.getUdf3());
			payuParams.put("phone", pgRequest.getMobile());
			payuParams.put("udf1", pgRequest.getUdf1());
			payuParams.put("udf2", pgRequest.getUdf2());
			payuParams.put("udf3", pgRequest.getUdf3());
			payuParams.put("udf4", pgRequest.getUdf4());
			payuParams.put("udf5", pgRequest.getUdf5());

			String signature = PayubizUtil.calculateHash(payuParams, merchantSetting.getmPassword());
			if (signature == null || signature.trim().equals("")) {
				response.setResponseCode("01");
				response.setDescription("Invalid Signature");
				return response;
			}
			payuParams.put("hash", signature);
			payuParams.put("surl", propMap.get(PGUtility.PAYU_RU_KEY_V2).getPropertyValue());
			payuParams.put("curl", propMap.get(PGUtility.PAYU_RU_KEY_V2).getPropertyValue());
			payuParams.put("furl", propMap.get(PGUtility.PAYU_RU_KEY_V2).getPropertyValue());

			logger.info("cardDetails.getCardType() ==> " + cardDetails.getCardType());

			if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("CC")) {
				payuParams.put("pg", "CC");
				payuParams.put("bankcode", transactionLog.getUdf10());
				payuParams.put("enforce_paymethod", "creditcard");
				payuParams.put("ccnum", cardDetails.getCardNo());
				payuParams.put("ccname", cardDetails.getCustomerName());
				payuParams.put("ccvv", cardDetails.getCvv());
				payuParams.put("ccexpmon", cardDetails.getExpiryMonth());
				payuParams.put("ccexpyr", cardDetails.getExpiryYear());
			} else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("DC")) {
				payuParams.put("pg", "DC");
				payuParams.put("bankcode", transactionLog.getUdf10());
				payuParams.put("enforce_paymethod", "debitcard");
				payuParams.put("ccnum", cardDetails.getCardNo());
				payuParams.put("ccname", cardDetails.getCustomerName());
				payuParams.put("ccvv", cardDetails.getCvv());
				payuParams.put("ccexpmon", cardDetails.getExpiryMonth());
				payuParams.put("ccexpyr", cardDetails.getExpiryYear());
			} else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("NB")) {
				payuParams.put("pg", "NB");

				ProcessorBank processorBank = new ProcessorBank();
				processorBank.setmId(merchant.getMid());
				processorBank.setBankId(Long.parseLong(pgRequest.getBankid()));
				processorBank.setStatus(true);
				processorBank.setProcessor(merchantSetting.getProcessor());
				processorBank = processorBankRepo.findByBankIdAndProcessorAndMIdAndStatus(processorBank.getBankId(),
						processorBank.getProcessor(), processorBank.getmId(), processorBank.isStatus());
				if (processorBank != null) {
					payuParams.put("bankcode", processorBank.getProcessorBankId());
				} else {
					response.setResponseCode("01");
					response.setDescription("bank not configure for this merchant");
					response.setStatus("Failed");
					return response;
				}
				payuParams.put("enforce_paymethod", "netbanking");
			} else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("WALLET")) {
				payuParams.put("pg", "CASH");

				ProcessorWallet walletBank = new ProcessorWallet();
				walletBank.setmId(merchant.getMid());
				walletBank.setWalletId(Long.parseLong(pgRequest.getBankid()));
				walletBank.setStatus(true);
				walletBank.setProcessor(merchantSetting.getProcessor());
				walletBank = processorWalletRepo.findByWalletIdAndProcessorAndMIdAndStatus(walletBank.getWalletId(),
						walletBank.getProcessor(), walletBank.getmId(), walletBank.isStatus());

				if (walletBank != null) {
					payuParams.put("bankcode", walletBank.getProcessorwalletId());
				} else {
					response.setResponseCode("01");
					response.setDescription("wallet not configure for this merchant");
					response.setStatus("Failed");
					return response;
				}

				// payuParams.put("bankcode", processorBank.getProcessorBankId());
				payuParams.put("enforce_paymethod", "cashcard");
			} else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("UPI")) {
				payuParams.put("pg", "UPI");
				payuParams.put("bankcode", "UPI");
				payuParams.put("enforce_paymethod", "upi");
				payuParams.put("vpa", cardDetails.getCardNo());
			}

			// Set for nb and wallet
			// postReqParam.put("bankcode", "ICIB"); // provide by PAYU team

			payuParams.put("Consent_shared", "0");

			Set<String> payuParamKeys = payuParams.keySet();
			for (String key : payuParamKeys) {
				logger.info("key::" + key + "=>value::" + payuParams.get(key));
			}

			response.setPost(false);
			response.setPostReqParam(payuParams);
			response.setResponseCode("02");
			response.setProcessorCode("475");
			response.setDescription("3d secure verifcation is pending");
			response.setThreeDSecureUrl(url);
			response.setStatus("success");
			response.setResult(true);
			// return response;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		}
		return response;

	}

	public PaymentResponse cashfreePayment(PaymentRequest pgRequest, Merchant merchant,
			MerchantSetting merchantSetting) {

		logger.info("<---------- Inside Cashfree Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		PaymentResponse response = new PaymentResponse();
		Map<String, PropertiesVo> propMap = new HashMap<>();
		// Map<String, String> postReqParam = new HashMap<>();
		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property);
			}
			String url = propMap.get(RemoteDbUtil.GETEPAY_CASHFREE_ORDER_URL_KEY).getPropertyValue();
			UserCardDetails cardDetails = PGUtility.parseCardDetails(pgRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}

			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();

			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();

			}

			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					cardDetails);
			if (cardDetails != null && cardDetails.getCardProvider() != null
					&& cardDetails.getCardProvider().equalsIgnoreCase("MASTERCARD")) {
				transactionLog.setUdf10("MASTER");
			} else if (cardDetails != null && cardDetails.getCardProvider() != null) {
				transactionLog.setUdf10(cardDetails.getCardProvider());
			}

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				transactionLog = transactionLogRepo.save(transactionLog);
				transactionLog.setTransactionId(transactionLog.getTransactionId());
			}

			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);

			CashfreeRequestCustomer cashFreeCustomer = new CashfreeRequestCustomer();
			cashFreeCustomer.setCustomer_bank_account_number("");
			cashFreeCustomer.setCustomer_bank_code(0);
			cashFreeCustomer.setCustomer_bank_ifsc("");
			cashFreeCustomer.setCustomer_email("transaction@getepay.in");
			cashFreeCustomer.setCustomer_id("1");
			cashFreeCustomer.setCustomer_phone("9999999999");

			CashfreeRequestOrderMeta cashFreeOrderMeta = new CashfreeRequestOrderMeta();
			cashFreeOrderMeta.setNotify_url(propMap.get(RemoteDbUtil.GETEPAY_CASHFREE_CU_KEY_V2).getPropertyValue());
			cashFreeOrderMeta.setReturn_url(propMap.get(RemoteDbUtil.GETEPAY_CASHFREE_RU_KEY_V2).getPropertyValue());

			logger.info("cardDetails.getCardType() ==> " + cardDetails.getCardType());

			if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("CC")) {
				cashFreeOrderMeta.setPayment_methods("cc");
			} else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("DC")) {
				cashFreeOrderMeta.setPayment_methods("dc");
			} else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("NB")) {
				cashFreeOrderMeta.setPayment_methods("nb");
			} /*
				 * else if(pgRequest.getPaymentMode() != null &&
				 * pgRequest.getPaymentMode().equalsIgnoreCase("WALLET")) {
				 * 
				 * }
				 */ else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("UPI")) {
				cashFreeOrderMeta.setPayment_methods("upi");
			}

			Gson gson = new Gson();

			CashfreeRequestOrder cashFreeOrder = new CashfreeRequestOrder();
			cashFreeOrder.setCustomer_details(cashFreeCustomer);
			cashFreeOrder.setOrder_amount(mAmount.doubleValue());
			cashFreeOrder.setOrder_currency("INR");
			// cashFreeOrder.setOrder_expiry_time("");

			String orderId = String.valueOf(transactionLog.getTransactionId());

			if (orderId.length() < 3) {
				orderId = "0" + orderId;
			}

			cashFreeOrder.setOrder_id(orderId);
			cashFreeOrder.setOrder_meta(cashFreeOrderMeta);
			cashFreeOrder.setOrder_note("Getepay Transaction");

			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);

			String cashFreeRequest = gson.toJson(cashFreeOrder);

			logger.info("Cashfree request=>" + cashFreeRequest);

			StringEntity stringRequest = new StringEntity(cashFreeRequest);
			post.setEntity(stringRequest);
			post.setHeader("content-type", "application/json");
			post.setHeader("x-client-id", merchantSetting.getMloginId());
			post.setHeader("x-client-secret", merchantSetting.getmPassword());
			post.setHeader("x-api-version",
					propMap.get(RemoteDbUtil.GETEPAY_CASHFREE_APIVERSION_KEY).getPropertyValue());

			HttpResponse responseObject = httpClient.execute(post);
			HttpEntity entity = responseObject.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			logger.info("Cashfree create order response " + responseString);

			int responseCode = responseObject.getStatusLine().getStatusCode();

			if (responseCode == 200) {

				CashfreeResponseOrder orderResponse = gson.fromJson(responseString, CashfreeResponseOrder.class);

				transactionLog.setOrderNumber(orderResponse.getCf_order_id());
				transactionLog.setUdf10(orderResponse.getOrder_token());
				// transactionLog.setUdf10(orderResponse.getCf_order_id());
				transactionLog.setProcessorCode("475");
				transactionLog.setResponseCode("02");

				String paymentUrl = propMap.get(RemoteDbUtil.GETEPAY_CASHFREE_PAYMENT_URL_KEY).getPropertyValue();

				CashfreeRequestPayment paymentRequest = new CashfreeRequestPayment();
				// Map<String, Object> paramMap = new HashMap<String, Object>();
				// paramMap.put("order_token", orderResponse.getOrder_token());
				paymentRequest.setOrder_token(orderResponse.getOrder_token());

				if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("CC")) {

					CashfreeRequestPaymentCard cardObj = new CashfreeRequestPaymentCard();
					cardObj.setChannel("link");
					cardObj.setCard_alias("");
					cardObj.setCard_bank_name("");
					cardObj.setCard_cvv(cardDetails.getCvv());
					cardObj.setCard_expiry_mm(cardDetails.getExpiryMonth());
					cardObj.setCard_expiry_yy(cardDetails.getExpiryYear().substring(2));
					cardObj.setCard_holder_name(cardDetails.getCustomerName());
					cardObj.setCard_number(cardDetails.getCardNo());

					CashfreeRequestPaymentCardWrapper wrapper = new CashfreeRequestPaymentCardWrapper();
					wrapper.setCard(cardObj);
					paymentRequest.setPayment_method(wrapper);

				} else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("DC")) {
					CashfreeRequestPaymentCard cardObj = new CashfreeRequestPaymentCard();
					cardObj.setChannel("link");
					cardObj.setCard_alias("");
					cardObj.setCard_bank_name("");
					cardObj.setCard_cvv(cardDetails.getCvv());
					cardObj.setCard_expiry_mm(cardDetails.getExpiryMonth());
					cardObj.setCard_expiry_yy(cardDetails.getExpiryYear().substring(2));
					cardObj.setCard_holder_name(cardDetails.getCustomerName());
					cardObj.setCard_number(cardDetails.getCardNo());
					CashfreeRequestPaymentCardWrapper wrapper = new CashfreeRequestPaymentCardWrapper();
					wrapper.setCard(cardObj);
					paymentRequest.setPayment_method(wrapper);

				} else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("NB")) {

					ProcessorBank processorBank = new ProcessorBank();
					processorBank.setmId(merchant.getMid());
					processorBank.setBankId(Long.parseLong(pgRequest.getBankid()));
					processorBank.setStatus(true);
					processorBank.setProcessor(merchantSetting.getProcessor());
					processorBank = processorBankRepo.findByBankIdAndProcessorAndMIdAndStatus(processorBank.getBankId(),
							processorBank.getProcessor(), processorBank.getmId(), processorBank.isStatus());
					if (processorBank != null) {
						CashfreeRequestPaymentNetbanking bankObj = new CashfreeRequestPaymentNetbanking();
						bankObj.setChannel("link");
						bankObj.setNetbanking_bank_code(Integer.valueOf(processorBank.getProcessorBankId()));

						CashfreeRequestPaymentNetbankingWrapper wrapper = new CashfreeRequestPaymentNetbankingWrapper();
						wrapper.setNetbanking(bankObj);
						paymentRequest.setPayment_method(wrapper);
					} else {
						response.setResponseCode("01");
						response.setDescription("Selected bank is not configured for the merchant");
						response.setStatus("Failed");
						transactionLog.setTxnStatus("FAILED");
						transactionLog.setProcessorCode("02");
						transactionLog.setResponseCode("01");
						transactionLog.setStage("Transaction is failed");
						transactionLogRepo.save(transactionLog);
						return response;
					}
				} /*
					 * else if(pgRequest.getPaymentMode() != null &&
					 * pgRequest.getPaymentMode().equalsIgnoreCase("WALLET")) {
					 * 
					 * }
					 */ else if (pgRequest.getPaymentMode() != null
						&& pgRequest.getPaymentMode().equalsIgnoreCase("UPI")) {
					CashfreeRequestPaymentUpi upiObj = new CashfreeRequestPaymentUpi();
					upiObj.setChannel("collect");
					upiObj.setUpi_id(cardDetails.getCardNo());
					CashfreeRequestPaymentUpiWrapper wrapper = new CashfreeRequestPaymentUpiWrapper();
					wrapper.setUpi(upiObj);
					paymentRequest.setPayment_method(wrapper);
				}

				httpClient = HttpClientBuilder.create().build();
				post = new HttpPost(paymentUrl);

				String cashFreePaymentRequest = gson.toJson(paymentRequest);

				post.setEntity(new StringEntity(cashFreePaymentRequest));
				post.setHeader("content-type", "application/json");
				post.setHeader("x-api-version",
						propMap.get(RemoteDbUtil.GETEPAY_CASHFREE_APIVERSION_KEY).getPropertyValue());

				responseObject = httpClient.execute(post);
				entity = responseObject.getEntity();
				responseString = EntityUtils.toString(entity, "UTF-8");
				logger.info("Cashfree create payment response " + responseString);

				responseCode = responseObject.getStatusLine().getStatusCode();

				if (responseCode == 200) {

					CashfreeResponsePayment paymentResponse = gson.fromJson(responseString,
							CashfreeResponsePayment.class);

					String dUrl = paymentResponse.getData().getUrl();
					response.setResponseCode("02");
					response.setProcessorCode("475");
					response.setDescription("3d secure verifcation is pending");
					response.setThreeDSecureUrl(dUrl);
					response.setStatus("success");
					response.setResult(true);
				} else {
					logger.info("cashfree error code=>" + responseCode);
					transactionLog.setTxnStatus("FAILED");
					transactionLog.setProcessorCode(String.valueOf(responseCode));
					transactionLog.setResponseCode("01");
					transactionLog.setStage("Transaction is failed");
					response.setStatus("failed");
					response.setResponseCode("01");
					response.setDescription("Unable to process your transaction");
					// return response;
				}

			} else {
				logger.info("cashfree error code=>" + responseCode);
				transactionLog.setTxnStatus("FAILED");
				transactionLog.setProcessorCode(String.valueOf(responseCode));
				transactionLog.setResponseCode("01");
				transactionLog.setStage("Transaction is failed");
				response.setStatus("failed");
				response.setResponseCode("01");
				response.setDescription("Unable to process your transaction");
			}
			transactionLogRepo.save(transactionLog);
			// return response;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		}
		return response;

	}

	public PaymentResponse lyraPayment(PaymentRequest pgRequest, Merchant merchant, MerchantSetting merchantSetting) {

		logger.info("<---------- Inside Lyra Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		PaymentResponse response = new PaymentResponse();
		Map<String, PropertiesVo> propMap = new HashMap<>();
		// Map<String, String> postReqParam = new HashMap<>();
		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property);
			}

			String authoriztion = merchantSetting.getMloginId() + ":" + merchantSetting.getmPassword();
			authoriztion = Base64.getEncoder().encodeToString(authoriztion.getBytes());
			String baseUrl = propMap.get(LyraUtil.LYRA_BASE_URL).getPropertyValue();
			String baseUrlTxn = propMap.get(LyraUtil.LYRA_BASE_URL_TXN).getPropertyValue();
			String url = "";
			// String url = propMap.get(LyraUtil.LYRA_BASE_URL_KEY).getPropertyValue();
			UserCardDetails cardDetails = PGUtility.parseCardDetails(pgRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}

			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();

			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();

			}

			String bin = PGUtility.getBin(pgRequest, merchant);
			logger.info("bin => " + bin);
			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					cardDetails);
			if (cardDetails != null && cardDetails.getCardProvider() != null
					&& cardDetails.getCardProvider().equalsIgnoreCase("MASTERCARD")) {
				transactionLog.setUdf10("MASTER");
			} else if (cardDetails != null && cardDetails.getCardProvider() != null) {
				transactionLog.setUdf10(cardDetails.getCardProvider());
			}

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				transactionLog = transactionLogRepo.save(transactionLog);
//				transactionLog.setTransactionId(transactionLog.getTransactionId());
			}

			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);
			// set data for generate signature

			// String createChargeUrl =
			// propMap.get(LyraUtil.LYRA_CREATE_CHARGE_URL_KEY).getPropertyValue();
			// url = url + createChargeUrl;

			int amount = mAmount.multiply(new BigDecimal(100)).intValue();

			LyraCustomer customer = new LyraCustomer();
			customer.setAddress("");
			customer.setCity("Jaipur");
			customer.setCountry("India");
			customer.setEmailId("support@getepay.in");
			customer.setName("Getepay");
			customer.setPhone("9999999999");
			customer.setState("Rajasthan");
			customer.setZipCode("302039");

			LyraWebhook webhook = new LyraWebhook();
			webhook.setUrl(propMap.get(LyraUtil.LYRA_WEBHOOK_URL_KEY).getPropertyValue());

			LyraCharge charge = new LyraCharge();
			charge.setAmount(amount);
			charge.setCurrency(pgRequest.getTxncurr());
			charge.setCustomer(customer);
			charge.setOrderId(transactionLog.getTransactionId().toString());
			charge.setWebhook(webhook);
			charge.setOrderInfo("Transaction - " + transactionLog.getMerchanttxnid());
//			charge.setUrl(propMap.get(LyraUtil.LYRA_RETURN_URL_KEY).getPropertyValue() + "?tId="
//					+ transactionLog.getTransactionId());
			charge.setUrl(propMap.get("LYRA_RETURN_URL_KEY_V2").getPropertyValue() + "?tId="
					+ transactionLog.getTransactionId());

			Gson gson = new Gson();
			String request = gson.toJson(charge);
			logger.info("lyraCreateCharge request => " + request);

			Call call = new Call();
			call.setBaseUrl(baseUrl);
			call.setAuthoriztion(authoriztion);

			String lyraResponse = call.lyraCreateCharge(request);
			logger.info("lyraCreateCharge response => " + lyraResponse);
			LyraChargeResponse lyraChargeResponse = gson.fromJson(lyraResponse, LyraChargeResponse.class);
//		    LyraChargeResponse lyraChargeResponse = new LyraChargeResponse();
//		    lyraChargeResponse.setUuid("zY/dfD+dff51g1h1q1z+1d1fd2f5fdf13");
			logger.info("response : " + lyraChargeResponse);
			String uuid = "";
			if (lyraChargeResponse != null && lyraChargeResponse.getUuid() != null) {
				uuid = lyraChargeResponse.getUuid();
				transactionLog.setProcessorTxnId(uuid);
				transactionLogRepo.save(transactionLog);
			} else {
				response.setResponseCode("01");
				response.setDescription("Invalid Request");
				response.setStatus("Failed");
				return response;
			}

			Map<String, String> payuParams = new HashMap<>();

			logger.info("cardDetails.getCardType() ==> " + cardDetails.getCardType());

			if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("CC")) {
//				CardBean cardBean = cardBeanDao.getBeanDetailbyBeanValue(Integer.valueOf(bin));
//				if (cardBean != null && !cardBean.getTypeOfCatd().equalsIgnoreCase("C")) {
//					response.setResponseCode("01");
//					response.setDescription("Invalid card type : " + cardBean.getTypeOfCatd());
//					logger.info("CardType ==> " + cardBean.getTypeOfCatd());
//					return response;
//				}

				String extId = String.valueOf(transactionLog.getTransactionId());
				while (extId.length() < 12) {
					extId = "0" + extId;
				}

				LyraCard requestVo = new LyraCard();
				requestVo.setCardNumber(cardDetails.getCardNo());
				requestVo.setExpMonth(cardDetails.getExpiryMonth());
				requestVo.setExpYear(cardDetails.getExpiryYear());
				requestVo.setCardHolderName(cardDetails.getCustomerName());
				requestVo.setCvv(cardDetails.getCvv());
				requestVo.setSaveCard("false");
				requestVo.setExternalId(extId);

				gson = new Gson();
				String stringRequest = gson.toJson(requestVo);
				logger.info("LyraCard request CC : " + requestVo);

				call = new Call();
				call.setBaseUrlTxn(baseUrlTxn);
				call.setAuthoriztion(authoriztion);

				String responseString = call.submitCardDetails(stringRequest, uuid);

				LyraTransactionResponse lyraTransactionResponse = gson.fromJson(responseString,
						LyraTransactionResponse.class);
//			    LyraTransactionResponse lyraTransactionResponse = new LyraTransactionResponse();
//			    lyraTransactionResponse.setAuthenticationMode("H2HOTP");
//			    lyraTransactionResponse.setTransactionUuid("zY/dfD+dff51g1h1q1z+1d1fd2f5fdf13");
				logger.info("response : " + lyraTransactionResponse);

				response.setEnableHtml(false);
				if (lyraTransactionResponse != null && lyraTransactionResponse.getAuthenticationMode() != null
						&& lyraTransactionResponse.getAuthenticationMode().equalsIgnoreCase("IFRAME")) {
					response.setProcessor("Lyra");
					response.setType("IFRAME");
					response.setHtml(lyraTransactionResponse.getTransactionUuid());
				} else if (lyraTransactionResponse != null && lyraTransactionResponse.getAuthenticationMode() != null
						&& lyraTransactionResponse.getAuthenticationMode().equalsIgnoreCase("H2HOTP")) {
					response.setProcessor("Lyra");
					response.setType("H2HOTP");
					response.setHtml(lyraTransactionResponse.getTransactionUuid());
				} else if (lyraTransactionResponse != null && lyraTransactionResponse.getParams() != null) {
					url = lyraTransactionResponse.getUrl();
					payuParams.putAll(lyraTransactionResponse.getParams());
				} else {
					response.setResponseCode("01");
					response.setDescription("Invalid Request");
					response.setStatus("Failed");
					return response;
				}

			} else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("DC")) {
//				CardBean cardBean = cardBeanDao.getBeanDetailbyBeanValue(Integer.valueOf(bin));
//				if (cardBean != null && !cardBean.getTypeOfCatd().equalsIgnoreCase("D")) {
//					response.setResponseCode("01");
//					response.setDescription("Invalid card type : " + cardBean.getTypeOfCatd());
//					logger.info("CardType ==> " + cardBean.getTypeOfCatd());
//					return response;
//				}

				String extId = String.valueOf(transactionLog.getTransactionId());
				while (extId.length() < 12) {
					extId = "0" + extId;
				}

				LyraCard requestVo = new LyraCard();
				requestVo.setCardNumber(cardDetails.getCardNo());
				requestVo.setExpMonth(cardDetails.getExpiryMonth());
				requestVo.setExpYear(cardDetails.getExpiryYear());
				requestVo.setCardHolderName(cardDetails.getCustomerName());
				requestVo.setCvv(cardDetails.getCvv());
				requestVo.setSaveCard("false");
				requestVo.setExternalId(extId);

				gson = new Gson();
				String stringRequest = gson.toJson(requestVo);
				logger.info("LyraCard request DC : " + requestVo);

				call = new Call();
				call.setBaseUrlTxn(baseUrlTxn);
				call.setAuthoriztion(authoriztion);

				String responseString = call.submitCardDetails(stringRequest, uuid);

				LyraTransactionResponse lyraTransactionResponse = gson.fromJson(responseString,
						LyraTransactionResponse.class);
				logger.info("response : " + lyraTransactionResponse);

				response.setEnableHtml(false);
				if (lyraTransactionResponse != null && lyraTransactionResponse.getAuthenticationMode() != null
						&& lyraTransactionResponse.getAuthenticationMode().equalsIgnoreCase("IFRAME")) {
					response.setProcessor("Lyra");
					response.setType("IFRAME");
					response.setHtml(lyraTransactionResponse.getTransactionUuid());
				} else if (lyraTransactionResponse != null && lyraTransactionResponse.getAuthenticationMode() != null
						&& lyraTransactionResponse.getAuthenticationMode().equalsIgnoreCase("H2HOTP")) {
					response.setProcessor("Lyra");
					response.setType("H2HOTP");
					response.setHtml(lyraTransactionResponse.getTransactionUuid());
				} else if (lyraTransactionResponse != null && lyraTransactionResponse.getParams() != null) {
					url = lyraTransactionResponse.getUrl();
					payuParams.putAll(lyraTransactionResponse.getParams());
				} else {
					response.setResponseCode("01");
					response.setDescription("Invalid Request");
					response.setStatus("Failed");
					return response;
				}

			} else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("NB")) {
				ProcessorBankHolder processorBankHolder = new ProcessorBankHolder();
				processorBankHolder = processorBankHolderRepo.findByProcessorBankNameAndBankId(
						merchantSetting.getProcessor(), Long.valueOf(pgRequest.getBankid()));
				if (processorBankHolder != null) {
					LyraNetbanking netBankingDetailsRequestVo = new LyraNetbanking();
					netBankingDetailsRequestVo.setBankCode(processorBankHolder.getProcessorBankId());
					gson = new Gson();
					String stringRequest = gson.toJson(netBankingDetailsRequestVo);
					logger.info("request : " + stringRequest);

					call = new Call();
					call.setBaseUrlTxn(baseUrlTxn);
					call.setAuthoriztion(authoriztion);

					String responseString = call.submitNetBankingDetails(stringRequest, uuid);

					LyraTransactionResponse lyraTransactionResponse = gson.fromJson(responseString,
							LyraTransactionResponse.class);
					logger.info("response : " + lyraTransactionResponse);

					if (lyraTransactionResponse != null && lyraTransactionResponse.getParams() != null) {
						url = lyraTransactionResponse.getUrl();
						payuParams.putAll(lyraTransactionResponse.getParams());
					} else {
						response.setResponseCode("01");
						response.setDescription("Invalid Request");
						response.setStatus("Failed");
						return response;
					}

				} else {
					response.setResponseCode("01");
					response.setDescription("bank not configure for this merchant");
					response.setStatus("Failed");
					return response;
				}

			} else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("WALLET")) {

				ProcessorWalletHolder processorWalletHolder = processorWalletHolderRepo.findByProcessorAndWalletId(
						merchantSetting.getProcessor(), Long.valueOf(pgRequest.getBankid()));

				if (processorWalletHolder != null) {
					LyraWallet walletDetailsRequestVo = new LyraWallet();
					walletDetailsRequestVo.setWalletName(processorWalletHolder.getProcessorWalletId());

					gson = new Gson();
					String stringRequest = gson.toJson(walletDetailsRequestVo);
					logger.info("request : " + stringRequest);

					call = new Call();
					call.setBaseUrlTxn(baseUrlTxn);
					call.setAuthoriztion(authoriztion);

					String responseString = call.submitWalletDetails(stringRequest, uuid);

					LyraTransactionResponse lyraTransactionResponse = gson.fromJson(responseString,
							LyraTransactionResponse.class);
					logger.info("response : " + lyraTransactionResponse);

					if (lyraTransactionResponse != null && lyraTransactionResponse.getParams() != null) {
						url = lyraTransactionResponse.getUrl();
						payuParams.putAll(lyraTransactionResponse.getParams());
					} else {
						response.setResponseCode("01");
						response.setDescription("Invalid Request");
						response.setStatus("Failed");
						return response;
					}

				} else {
					response.setResponseCode("01");
					response.setDescription("wallet not configure for this merchant");
					response.setStatus("Failed");
					return response;
				}

			} else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("UPI")) {

				LyraUpi upiDetailsRequestVo = new LyraUpi();
				upiDetailsRequestVo.setVpa(cardDetails.getCardNo());
				gson = new Gson();
				String stringRequest = gson.toJson(upiDetailsRequestVo);
				logger.info("StringRequest : " + stringRequest);

				call = new Call();
				call.setBaseUrlTxn(baseUrlTxn);
				call.setAuthoriztion(authoriztion);

				String responseString = call.submitUpiDetails(stringRequest, uuid);

				LyraTransactionResponse lyraTransactionResponse = gson.fromJson(responseString,
						LyraTransactionResponse.class);
				logger.info("response : " + lyraTransactionResponse);

				if (lyraTransactionResponse != null && lyraTransactionResponse.getParams() != null) {
					url = lyraTransactionResponse.getUrl();
					payuParams.putAll(lyraTransactionResponse.getParams());
				} else {
					response.setResponseCode("01");
					response.setDescription("Invalid Request");
					response.setStatus("Failed");
					return response;
				}

			}

			Set<String> payuParamKeys = payuParams.keySet();
			for (String key : payuParamKeys) {
				logger.info("key::" + key + "=>value::" + payuParams.get(key));
			}

			response.setPost(false);
			response.setPostReqParam(payuParams);
			response.setResponseCode("02");
			response.setProcessorCode("475");
			response.setDescription("3d secure verifcation is pending");
			response.setThreeDSecureUrl(url);
			response.setStatus("success");
			response.setResult(true);
			// return response;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		}
		logger.info("pgresponse => " + response);
		return response;

	}

	public PaymentResponse kotakNb(PaymentRequest pgRequest, Merchant merchant, MerchantSetting merchantSetting) {

		logger.info("<---------- Inside kotakNb Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		PaymentResponse response = new PaymentResponse();
		Map<String, PropertiesVo> propMap = new HashMap<>();
		// Map<String, String> postReqParam = new HashMap<>();
		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property);
			}

			UserCardDetails cardDetails = PGUtility.parseCardDetails(pgRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}

			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();

			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();

			}

			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					cardDetails);
			if (cardDetails != null && cardDetails.getCardProvider() != null
					&& cardDetails.getCardProvider().equalsIgnoreCase("MASTERCARD")) {
				transactionLog.setUdf10("MASTER");
			} else if (cardDetails != null && cardDetails.getCardProvider() != null) {
				transactionLog.setUdf10(cardDetails.getCardProvider());
			}

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				transactionLog = transactionLogRepo.save(transactionLog);
				transactionLog.setTransactionId(transactionLog.getTransactionId());
			}

			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);
			// set data for generate signature

			// String createChargeUrl =
			// propMap.get(LyraUtil.LYRA_CREATE_CHARGE_URL_KEY).getPropertyValue();
			// url = url + createChargeUrl;

			// int amount = mAmount.multiply(new BigDecimal(100)).intValue();

			Map<String, String> payuParams = new HashMap<>();

			logger.info("cardDetails.getCardType() ==> " + cardDetails.getCardType());
			Gson gson = new Gson();
			String url = propMap.get(KotakUtil.KOTAK_URL_KEY).getPropertyValue();
			if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("NB")) {

				String kotakMessage = "0500|";

				LocalDateTime transactionDate = transactionLog.getCreatedDate();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHH");

				kotakMessage += formatter.format(transactionDate) + "|";
				kotakMessage += merchantSetting.getMloginId() + "|";
				// kotakMessage += merchantSetting.getMloginId() + "|";

				// Generating reference number
				String yearString = String.valueOf(transactionDate.getYear());
				String year = yearString.substring(2);
				String uniqueRefNo = year;

				String juleanDay = String.valueOf(transactionDate.getDayOfYear());
				while (juleanDay.length() < 3) {
					juleanDay = "0" + juleanDay;
				}
				uniqueRefNo += juleanDay;

				String hour = String.valueOf(transactionDate.getHour());

				if (hour.length() < 2) {
					hour = "0" + hour;
				}

				String min = String.valueOf(transactionDate.getMinute());

				if (min.length() < 2) {
					min = "0" + min;
				}

				uniqueRefNo += hour;
				uniqueRefNo += min;

				KotakTransactionLog kotakTransactionLog = new KotakTransactionLog();
				kotakTransactionLog.setTransactionId(transactionLog.getTransactionId());
				kotakTransactionLog = kotakTransactionLogRepo.save(kotakTransactionLog);

				String uniqueSequenceNoString = String.valueOf(kotakTransactionLog.getId());
				while (uniqueSequenceNoString.length() < 7) {
					uniqueSequenceNoString = "0" + uniqueSequenceNoString;
				}
				uniqueRefNo += uniqueSequenceNoString;

				kotakMessage += uniqueRefNo + "|";
				kotakMessage += mAmount.setScale(2, RoundingMode.HALF_UP).toString() + "|";
				kotakMessage += merchantSetting.getmPassword() + "|";

				kotakMessage += "" + "|";
				kotakMessage += "" + "|"; // For trading pass account number here.

				kotakMessage += merchant.getMccCode(); // For trading pass account number here.
				// kotakMessage += uniqueSequenceNoString;
				// Generate checksum

				String checksum = KotakUtil.getHMAC256Checksum(kotakMessage, propMap.get(KotakUtil.KOTAK_CHECKSUM_KEY)
						.getPropertyValue()/* merchantSetting.getSetting1() */);

				kotakMessage += "|" + checksum;
				logger.info("kotak dec=>" + kotakMessage);
				kotakMessage = KotakUtil.encrypt(kotakMessage, propMap.get(KotakUtil.KOTAK_ENCRYPTION_KEY)
						.getPropertyValue()/* merchantSetting.getSetting2() */);
				logger.info("kotak enc=>" + kotakMessage);
				kotakMessage = URLEncoder.encode(kotakMessage, "UTF-8");

				payuParams.put("merchantId", merchantSetting.getMloginId());
				payuParams.put("msg", kotakMessage);
			} else {
				response.setResponseCode("01");
				response.setDescription("bank not configure for this merchant");
				response.setStatus("Failed");
				return response;
			}

			Set<String> payuParamKeys = payuParams.keySet();
			for (String key : payuParamKeys) {
				logger.info("key::" + key + "=>value::" + payuParams.get(key));
			}

			response.setPost(false);
			response.setPostReqParam(payuParams);
			response.setResponseCode("02");
			response.setProcessorCode("475");
			response.setDescription("3d secure verifcation is pending");
			response.setThreeDSecureUrl(url);
			response.setStatus("success");
			response.setResult(true);
			// return response;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		}
		return response;

	}

	public PaymentResponse ucoFEBAPayment(PaymentRequest pgRequest, Merchant merchant,
			MerchantSetting merchantSetting) {

		logger.info("<---------- Inside UcoBank Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		PaymentResponse response = new PaymentResponse();
		Map<String, String> propMap = new HashMap<>();
		// Map<String, String> postReqParam = new HashMap<>();
		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

			UserCardDetails cardDetails = PGUtility.parseCardDetails(pgRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}

			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();

			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();

			}

			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					cardDetails);
			if (cardDetails != null && cardDetails.getCardProvider() != null
					&& cardDetails.getCardProvider().equalsIgnoreCase("MASTERCARD")) {
				transactionLog.setUdf10("MASTER");
			} else if (cardDetails != null && cardDetails.getCardProvider() != null) {
				transactionLog.setUdf10(cardDetails.getCardProvider());
			}

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				transactionLog = transactionLogRepo.save(transactionLog);
				transactionLog.setTransactionId(transactionLog.getTransactionId());
			}

			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);
			// set data for generate signature
			Map<String, String> ucoParams = new HashMap<String, String>();
			logger.info("cardDetails.getCardType() ==> " + cardDetails.getCardType());
			Gson gson = new Gson();
			String url = propMap.get(UcoBankUtils.UCO_NB_URL_KEY);

//			String secretKey = propMap.get(UcoBankUtils.UCO_NB_SECRET_KEY);
//			String salt = propMap.get(UcoBankUtils.UCO_NB_SALT);
			String encKey = propMap.get(UcoBankUtils.UCO_NB_ENCRYPTION_KEY);
			String ru = propMap.get(UcoBankUtils.UCO_NB_RURL);

			if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("NB")) {

				UcoRequestVo ucoRequestVo = new UcoRequestVo();
				ucoRequestVo.setTrancrn("INR");
				ucoRequestVo.setTxnamount(mAmount.toString());
				ucoRequestVo.setPid(propMap.get(UcoBankUtils.UCO_NB_PID));
				ucoRequestVo.setPrn(String.valueOf(transactionLog.getTransactionId()));
				ucoRequestVo.setItc(transactionLog.getMerchanttxnid());
//				ucoRequestVo.setShpngmallaccnum("");
				ucoRequestVo.setChecksum("");

				String qsString = ucoRequestVo.getStringtoCalculateCheckSum();
				logger.info("qsString => " + qsString);

				String checkSum = UcoBankUtils.getSHA256(qsString);
				ucoRequestVo.setChecksum(checkSum);
				String qs = ucoRequestVo.getFinalQString();
				logger.info("qs => " + qs);

				qs = UcoEncryption.encrypt(qs, encKey, "AES", 256);
				ru = UcoEncryption.encrypt(ru, encKey, "AES", 256);

				qs = URLEncoder.encode(qs, "UTF-8");
				ru = URLEncoder.encode(ru, "UTF-8");

				logger.info("redirect url => " + url);

				url = url.replace("#ru", ru);
				url = url.replace("#qs", qs);
				url = url.replace("#categoryId", propMap.get(UcoBankUtils.UCO_NB_CATEGORY_ID));
				logger.info("redirect url => " + url);

//				ucoParams.put("CATEGORY_ID", propMap.get(UcoBankUtils.UCO_NB_CATEGORY_ID));
//				ucoParams.put("RU", UcoBankUtils.encrypt(ru, secretKey, salt));
//				ucoParams.put("QS", UcoBankUtils.encrypt(qs, secretKey, salt));
			} else {
				response.setResponseCode("01");
				response.setDescription("bank not configure for this merchant");
				response.setStatus("Failed");
				return response;
			}

			Set<String> ucoParamsKeys = ucoParams.keySet();
			for (String key : ucoParamsKeys) {
				logger.info("key::" + key + "=>value::" + ucoParams.get(key));
			}

			response.setPost(false);
			response.setPostReqParam(ucoParams);
			response.setResponseCode("02");
			response.setProcessorCode("475");
			response.setDescription("3d secure verifcation is pending");
			response.setThreeDSecureUrl(url);
			response.setStatus("success");
			response.setResult(true);
			// return response;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		}
		return response;

	}

	public PaymentResponse kotakPayment(PaymentRequest pgRequest, Merchant merchant, MerchantSetting merchantSetting) {

		logger.info("<---------- Inside Kotak Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		PaymentResponse response = new PaymentResponse();
		Charge convienceCharge = new Charge();
		Map<String, String> propMap = new HashMap<>();
		// Map<String, String> postReqParam = new HashMap<>();
		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

			UserCardDetails cardDetails = PGUtility.parseCardDetails(pgRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}

			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();

			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();

			}

			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					cardDetails);
			if (cardDetails != null && cardDetails.getCardProvider() != null
					&& cardDetails.getCardProvider().equalsIgnoreCase("MASTERCARD")) {
				transactionLog.setUdf10("MASTER");
			} else if (cardDetails != null && cardDetails.getCardProvider() != null) {
				transactionLog.setUdf10(cardDetails.getCardProvider());
			}

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				transactionLog = transactionLogRepo.save(transactionLog);
				transactionLog.setTransactionId(transactionLog.getTransactionId());
			}

			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);
			// set data for generate signature

			// String createChargeUrl =
			// propMap.get(LyraUtil.LYRA_CREATE_CHARGE_URL_KEY).getPropertyValue();
			// url = url + createChargeUrl;

			int amount = mAmount.multiply(new BigDecimal(100)).intValue();

			KotakUtil kotakUtil = new KotakUtil();
			AuthenticationRequest request = new AuthenticationRequest();

			TransactionEssentials transactionEssentials = transactionEssentialsRepo
					.findByTransactionId(transactionLog.getTransactionId());

			request.setBankId(merchantSetting.getSetting4());
			request.setMerchantId(merchantSetting.getMloginId());
			request.setTerminalId(merchantSetting.getSetting1());
			request.setOrderId(String.valueOf(transactionLog.getTransactionId()));
			request.setMCC(merchantSetting.getSetting5());
			request.setAccessCode(merchantSetting.getSetting2());

			request.setCommand(propMap.get(KotakUtil.KOTAK_COMMAND_AUTHENTICATION_KEY));
			request.setCurrency(String.valueOf(RemoteDbUtil.getNumericCurrencyCode(transactionLog.getTxncurr())));
			request.setAmount(String.valueOf(amount));

			request.setOrderInfo("Kotak transaction for " + transactionLog.getTransactionId());

			request.setPaymentOption(pgRequest.getPaymentMode());

			String ipAddress = "";
			if (transactionEssentials.getUdf60() != null && !transactionEssentials.getUdf60().equals("")
					&& !transactionEssentials.getUdf60().equalsIgnoreCase("null")) {
				ipAddress = transactionEssentials.getUdf60();
			} else {
				ipAddress = "192.168.0.1";
			}

			request.setIpAddress(ipAddress);
//			TODO
			transactionEssentials.setUdf34("Windows, Chrome-89.0.4389.90");
			transactionEssentials.setUdf32(
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64)AppleWebKit/537.36 (KHTML, like Gecko)Chrome/89.0.4389.90 Safari/537.36"); // UserAgent

			request.setBrowserDetails(transactionEssentials.getUdf34());

			request.setAcceptHeader(
					"text/html,application/xhtml+xml,application/x ml;q=0.9,image/avif,image/webp,image/apng,* /*;q=0.8,application/signed- exchange;v=b3;q=0.9");
			request.setUserAgent(transactionEssentials.getUdf32());

			request.setAuthenticationResponseURL(propMap.get(KotakUtil.KOTAK_API_AUTHENTICATION_RESPONSE_URL_KEY) + "/"
					+ transactionLog.getTransactionId());

			request.setBrowserTZ(transactionEssentials.getUdf31().split("\\|")[4]);
			request.setBrowserScreenHeight(transactionEssentials.getUdf31().split("\\|")[2]);
			request.setBrowserScreenWidth(transactionEssentials.getUdf31().split("\\|")[3]);
			request.setBrowserJavaEnabled(transactionEssentials.getUdf31().split("\\|")[5]);
			request.setBrowserColorDepth(transactionEssentials.getUdf31().split("\\|")[1]);
			request.setBrowserJavascriptEnabled(transactionEssentials.getUdf31().split("\\|")[6]);

			request.setCardNumber(cardDetails.getCardNo());
			request.setExpiryDate(cardDetails.getExpiryMonth() + cardDetails.getExpiryYear());
			request.setCVV(cardDetails.getCvv());

			AuthenticationResponse authenticationResponse = KotakUtil.callAuthenticationApi2(request, merchantSetting,
					propMap);
			logger.info("authenticationResponse => " + authenticationResponse);
			if (authenticationResponse != null && authenticationResponse.getResponseCode() != null
					&& authenticationResponse.getResponseCode().equals("00")) {

				transactionLog.setProcessorTxnId(authenticationResponse.getPgId());
				transactionLogRepo.save(transactionLog);

				response.setPost(false);
				response.setResponseCode("02");
				response.setProcessorCode("475");
				response.setDescription("3d secure verifcation is pending");
				response.setStatus("success");
				response.setResult(false);

				if (authenticationResponse.getAuthenticationData() != null) {
					if (authenticationResponse.getAuthenticationData().getOTPRederingContent() != null) {
						response.setEnableHtml(true);
						response.setHtml(Base64.getEncoder().encodeToString(
								authenticationResponse.getAuthenticationData().getOTPRederingContent().getBytes()));
					}

					if (authenticationResponse.getAuthenticationData().getBEPG() != null
							&& authenticationResponse.getAuthenticationData().getBEPG().getBEPG_TxnCtxId() != null) {
						response.setProcessor("KOTAK BANK");
						response.setType("RUPAY");
						response.setResult(true);
						String dataBEPG = new Gson().toJson(authenticationResponse.getAuthenticationData().getBEPG());
						response.setHtml(Base64.getEncoder().encodeToString(dataBEPG.getBytes()));
					}
				}

			} else {
				response.setResponseCode("01");
				if (authenticationResponse != null) {
					response.setDescription(authenticationResponse.getResponseMessage());
					transactionLog.setBankErrorMsg(authenticationResponse.getResponseMessage());

				} else {
					response.setDescription("Error in Kotak authetication api call");
				}

				transactionLogRepo.save(transactionLog);
				response.setStatus("Failed");
				return response;
			}

			// return response;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		} finally {
		}
		return response;

	}

	public PaymentResponse iciciNb(PaymentRequest pgRequest, Merchant merchant, MerchantSetting merchantSetting) {

		logger.info("<---------- Inside ICICI NB Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		PaymentResponse response = new PaymentResponse();
		Map<String, String> propMap = new HashMap<>();
		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

			UserCardDetails cardDetails = PGUtility.parseCardDetails(pgRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}

			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();

			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();

			}

			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					cardDetails);
			if (cardDetails != null && cardDetails.getCardProvider() != null
					&& cardDetails.getCardProvider().equalsIgnoreCase("MASTERCARD")) {
				transactionLog.setUdf10("MASTER");
			} else if (cardDetails != null && cardDetails.getCardProvider() != null) {
				transactionLog.setUdf10(cardDetails.getCardProvider());
			}

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				transactionLog = transactionLogRepo.save(transactionLog);
				transactionLog.setTransactionId(transactionLog.getTransactionId());
			}

			TransactionEssentials txnEssential = transactionEssentialsRepo
					.findByTransactionId(transactionLog.getTransactionId());
			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);
			// set data for generate signature
			Map<String, String> ucoParams = new HashMap<String, String>();
			logger.info("cardDetails.getCardType() ==> " + cardDetails.getCardType());
			Gson gson = new Gson();
			String url = propMap.get(ICICIBankUtils.ICICI_NB_URL_KEY);

			String ru = propMap.get(ICICIBankUtils.ICICI_NB_RU_V2).replace("#ru",
					"" + transactionLog.getTransactionId());
			String cg = propMap.get(ICICIBankUtils.ICICI_NB_CG);
			if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("NB")) {

				IciciNetBankingRequest iciciNbRequest = new IciciNetBankingRequest();
				iciciNbRequest.setPRN(String.valueOf(transactionLog.getTransactionId()));
				iciciNbRequest.setITC(transactionLog.getMerchanttxnid());
				iciciNbRequest.setAMT(mAmount.toString());
				iciciNbRequest.setCRN("INR");
				iciciNbRequest.setRU(ru);
				iciciNbRequest.setCG(cg);

				if (txnEssential != null && txnEssential.getUdf57() != null
						&& txnEssential.getUdf57().contains("TPV")) {

					String[] values = txnEssential.getUdf57().split("\\|");

					iciciNbRequest.setACCNO(values[1]);

				} else {

					iciciNbRequest.setACCNO(propMap.get(ICICIBankUtils.ICICI_NB_ACC_NO));
				}

				// iciciNbRequest.setACCNO("xdfsddsdf6565");
				logger.info("iciciNb Request => " + iciciNbRequest);

				String es = iciciNbRequest.getFinalPipeData();
				logger.info("iciciNb es => " + es);

//				es = ShoppingMallEncryptor.encrypt(merchantSetting.getmPassword(), es);
				EncryptDecryptUtil util = new EncryptDecryptUtil(merchantSetting.getmPassword());
				es = util.encrypt(es);
				logger.info("iciciNb Request encrypted => " + es);

				url = url.replace("#es", es);
				url = url.replace("#spid", merchantSetting.getSetting2());
				url = url.replace("#login", merchantSetting.getMloginId());
				url = url.replace("#pid", merchantSetting.getSetting1());
				logger.info("redirect url => " + url);

			} else {
				response.setResponseCode("01");
				response.setDescription("bank not configure for this merchant");
				response.setStatus("Failed");
				return response;
			}

			Set<String> ucoParamsKeys = ucoParams.keySet();
			for (String key : ucoParamsKeys) {
				logger.info("key::" + key + "=>value::" + ucoParams.get(key));
			}

			response.setPost(false);
			response.setResponseCode("02");
			response.setProcessorCode("475");
			response.setDescription("3d secure verifcation is pending");
			response.setThreeDSecureUrl(url);
			response.setStatus("success");
			response.setResult(true);
			// return response;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		}
		return response;

	}

	public PaymentResponse northAkorssnb(PaymentRequest pgRequest, Merchant merchant, MerchantSetting merchantSetting) {

		logger.info("<---------- Inside NorthAcross NB Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		Charge convienceCharge = new Charge();
		NCrossUtils nCrossUtils = new NCrossUtils();
		PaymentResponse response = new PaymentResponse();
		Map<String, String> propMap = new HashMap<>();
		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

			UserCardDetails cardDetails = PGUtility.parseCardDetails(pgRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}

			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();

			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();

			}

			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					cardDetails);
			if (cardDetails != null && cardDetails.getCardProvider() != null
					&& cardDetails.getCardProvider().equalsIgnoreCase("MASTERCARD")) {
				transactionLog.setUdf10("MASTER");
			} else if (cardDetails != null && cardDetails.getCardProvider() != null) {
				transactionLog.setUdf10(cardDetails.getCardProvider());
			}

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);

				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				transactionLog = transactionLogRepo.save(transactionLog);
				transactionLog.setTransactionId(transactionLog.getTransactionId());
			}

			TransactionEssentials txnEssential = transactionEssentialsRepo
					.findByTransactionId(transactionLog.getTransactionId());
			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);
			// set data for generate signature
			Map<String, String> postParams = new HashMap<String, String>();
			logger.info("cardDetails.getCardType() ==> " + cardDetails.getCardType());

			Map<String, String> mapUtils = new HashMap<String, String>();
			mapUtils = nCrossUtils.propertyData(merchantSetting, propMap);

			logger.info("Secure Secret===>" + merchantSetting.getSetting1());

			String ReturnUrl = mapUtils.get("returnUrl").replace("#ru", "" + transactionLog.getTransactionId());
			logger.info("Ru==>" + ReturnUrl);
			if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("NB")) {
				logger.info(
						"Payement mode=====" + merchantSetting.getProcessor() + "==================================>");

				NorthAcrossPaymentRequest paymentRequest = new NorthAcrossPaymentRequest();
				paymentRequest.setApi_key(merchantSetting.getSetting2());
				paymentRequest.setAddress_line_1(merchant.getAddress());
				paymentRequest.setAddress_line_2("");
				paymentRequest.setAmount(mAmount.toString());
				paymentRequest.setCity(merchant.getCity());
				paymentRequest.setCountry("IND");
				paymentRequest.setCurrency(transactionLog.getTxncurr());
				paymentRequest.setDescription("Payment for Order " + String.valueOf(transactionLog.getTransactionId()));
				paymentRequest.setEmail(merchant.getEmail());
				paymentRequest.setMode(merchantSetting.getSetting3());
				paymentRequest.setName(merchant.getMerchantName());
				paymentRequest.setOrder_id(String.valueOf(transactionLog.getTransactionId()));
				paymentRequest.setPhone(merchant.getMobileNumber());
				paymentRequest.setReturn_url(ReturnUrl);
				paymentRequest.setReturn_url_failure(ReturnUrl);
				paymentRequest.setReturn_url_cancel(ReturnUrl);
				paymentRequest.setBank_code(merchantSetting.getSetting4());
				paymentRequest.setState(merchant.getState());
				paymentRequest.setUdf1("");
				paymentRequest.setUdf2("");
				paymentRequest.setUdf3("");
				paymentRequest.setUdf4("");
				paymentRequest.setUdf5("");
				paymentRequest.setUdf6("");
				paymentRequest.setUdf7("");
				paymentRequest.setUdf8("");
				paymentRequest.setUdf9("");

				if (txnEssential != null && txnEssential.getUdf57() != null
						&& txnEssential.getUdf57().contains("TPV")) {

					String[] values = txnEssential.getUdf57().split("\\|");

					paymentRequest.setUdf10(values[1]);

				} else {

					paymentRequest.setUdf10("");
				}

				paymentRequest.setZip_code(merchant.getPincode());
				paymentRequest.setHash("");
				Gson gson = new Gson();
				logger.info("Payment Request Before Hash Generation======>" + gson.toJson(paymentRequest));
				String pipesepratedhash = NorthAcrossUtil.generateHash(gson.toJson(paymentRequest),
						merchantSetting.getSetting1());
				paymentRequest.setHash(pipesepratedhash);

				logger.info("Hash data=====>" + pipesepratedhash);

				String request = gson.toJson(paymentRequest);
				logger.info("Payment Json Request====>" + request);
				String encData = NorthAcrossUtil.encrypt(request, merchantSetting.getSetting5());
				logger.info("Encrypted Data Request for ====>" + encData);
				NorthAcrossRequestWrapper wrapper = new NorthAcrossRequestWrapper();
				wrapper.setApi_key(merchantSetting.getSetting2());
				wrapper.setEncrypted_data(encData);
				transactionLogRepo.save(transactionLog);
				postParams.put("api_key", wrapper.getApi_key());
				postParams.put("encrypted_data", wrapper.getEncrypted_data());
				logger.info("Post Param====>" + postParams);
				response.setPost(false);
				response.setPostReqParam(postParams);
				response.setResponseCode("02");
				response.setProcessorCode("475");
				response.setDescription("3d secure verifcation is pending");
				response.setThreeDSecureUrl(mapUtils.get("paymentApiUrl"));

				response.setStatus("success");
				response.setResult(true);

			} else {
				response.setResponseCode("01");
				response.setDescription("bank not configure for this merchant");
				response.setStatus("Failed");
				return response;
			}

		} catch (

		Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		}
		return response;

	}

	public PaymentResponse sbinb(PaymentRequest pgRequest, Merchant merchant, MerchantSetting merchantSetting) {

		logger.info("<---------- Inside SBI NB Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		String url = null;
		PaymentResponse response = new PaymentResponse();
		Map<String, String> propMap = new HashMap<>();
		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

			UserCardDetails cardDetails = PGUtility.parseCardDetails(pgRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}

			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();

			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();

			}

			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					cardDetails);
			if (cardDetails != null && cardDetails.getCardProvider() != null
					&& cardDetails.getCardProvider().equalsIgnoreCase("MASTERCARD")) {
				transactionLog.setUdf10("MASTER");
			} else if (cardDetails != null && cardDetails.getCardProvider() != null) {
				transactionLog.setUdf10(cardDetails.getCardProvider());
			}

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				transactionLog = transactionLogRepo.save(transactionLog);
				transactionLog.setTransactionId(transactionLog.getTransactionId());
			}

			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);

			Map<String, String> postParams = new HashMap<String, String>();

			logger.info("cardDetails.getCardType() ==> " + cardDetails.getCardType());
			String returnUrl = propMap.get(SBIUtils.SBI_NB_RETURN_URL_V2).replace("#ru",
					"" + transactionLog.getTransactionId());
			if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("NB")) {

				SBIPaymentRequest payment = new SBIPaymentRequest();
				long ref = System.currentTimeMillis();
				payment.setRef_no(String.valueOf(transactionLog.getTransactionId()));
				payment.setAmount(mAmount.toString());
				payment.setRedirect_url(returnUrl);
				payment.setCrn("INR");
				payment.setTransaction_category("INB");
				payment.setCheckSum("");

				logger.info("Payment Request========>" + payment);
				logger.info("Request for Checksum " + payment.checksum());
				String pipeSepratedChecksum = SBIUtils.getSHA2Checksum(payment.checksum());
				logger.info("pipeSepratedChecksum===>" + pipeSepratedChecksum);
				payment.setCheckSum(pipeSepratedChecksum);
				logger.info("Payment Request========>" + payment);
				String pipeSepratedfinal = payment.checksumEncrypted();
				logger.info("Request to encrypt data=====>" + pipeSepratedfinal);
				String encdata = SBIUtils.Encrypt(pipeSepratedfinal, propMap.get(SBIUtils.SBINB_KEYPATH));
				logger.info("Encrypted SBI NB Data========================>" + encdata);

				SBIRequestWrapper requestWrapper = new SBIRequestWrapper();
				requestWrapper.setEncdata(encdata);
				requestWrapper.setMerchant_code(merchantSetting.getSetting1());
				logger.info("RequestWrapper Data==========================>" + requestWrapper);

				postParams.put("encdata", requestWrapper.getEncdata());
				postParams.put("merchant_code", requestWrapper.getMerchant_code());

				logger.info("Post Parameters=============>" + postParams);

				response.setPost(false);
				response.setPostReqParam(postParams);
				response.setResponseCode("02");
				response.setProcessorCode("475");
				response.setDescription("3d secure verifcation is pending");
				response.setThreeDSecureUrl(propMap.get(SBIUtils.SBI_NB_PAYMENT_API_URL));
				response.setStatus("success");
				response.setResult(true);
			} else {
				response.setResponseCode("01");
				response.setDescription("bank not configure for this merchant");
				response.setStatus("Failed");
				return response;
			}
			// return response;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		}
		return response;

	}

	public PaymentResponse sbicards(PaymentRequest pgRequest, Merchant merchant, MerchantSetting merchantSetting) {

		logger.info("<---------- Inside SBI Card Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		PaymentResponse response = new PaymentResponse();
		SBITokenResponse sbitokenResponse = new SBITokenResponse();
		String cardType = null;
		String enccardData = null;
		Charge convienceCharge = new Charge();
		Map<String, String> propMap = new HashMap<>();
		Map<String, String> cardMap = new HashMap<>();
		// Map<String, String> postReqParam = new HashMap<>();
		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

			UserCardDetails cardDetails = PGUtility.parseCardDetails(pgRequest, merchant, merchantSetting);
			CardDetailsVo carddetailsVo = new CardDetailsVo();
			BeanUtils.copyProperties(cardDetails, carddetailsVo);

			int value = Integer.parseInt(cardDetails.getCardNo().substring(0, 9));
			// CardBean cardBean = cardbeanDao.getBeanDetailbyBeanValue(value);

			if (cardDetails == null) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}
			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();

			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				new GlobalExceptionHandler().customException(e);
				transactionLog = new TransactionLog();

			}

			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					cardDetails);
			if (cardDetails != null && cardDetails.getCardProvider() != null
					&& cardDetails.getCardProvider().equalsIgnoreCase("MASTERCARD")) {
				transactionLog.setUdf10("MASTER");
				carddetailsVo.setCardProvider("MASTER");
			} else if (cardDetails != null && cardDetails.getCardProvider() != null) {
				transactionLog.setUdf10(cardDetails.getCardProvider());
			}

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				transactionLog = transactionLogRepo.save(transactionLog);
				transactionLog.setTransactionId(transactionLog.getTransactionId());
			}

			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);
			int amount = mAmount.multiply(new BigDecimal(100)).intValue();
			String signedResponse = null;

			TransactionEssentials transactionEssentials = transactionEssentialsRepo
					.findByTransactionId(transactionLog.getTransactionId());

			TokenHeader tokenheader = new TokenHeader();
			tokenheader.setClientId(propMap.get(SBICardsUtils.SBI_TOKEN_CLIENT_ID));
			tokenheader.setClientApiKey(propMap.get(SBICardsUtils.SBI_TOKEN_CLIENT_API_KEY));
			tokenheader.setClientApiUser(propMap.get(SBICardsUtils.SBI_TOKEN_CLIENT_API_USER));

			logger.info("Token Headers ===================>" + tokenheader
					+ " For transaction_id========================> " + transactionLog.getTransactionId());

			if (cardDetails != null && cardDetails.getCardProvider().equals("RUPAY")) {

				logger.info("Inside SBI Rupay Cards=======================>");
				cardType = "R";
				SbiRequestHeader header = new SbiRequestHeader();

//				header.setXapikey(merchantSetting.getSetting3());
//				header.setPgInstanceId(merchantSetting.getSetting1());
//				header.setMerchantId(merchantSetting.getSetting1());

				header.setXapikey(propMap.get(SBICardsUtils.SBI_HEADER_XAPI_KEY));
				header.setPgInstanceId(propMap.get(SBICardsUtils.SBI_RUPAY_HEADER_PG_INSTANCE_ID));
				header.setMerchantId(propMap.get(SBICardsUtils.SBI_RUPAY_HEADER_MERCHANT_ID));

				logger.info("SBI Rupay Headers======>" + header + " For transaction_id================================>"
						+ transactionLog.getTransactionId());

				Gson gson = new Gson();

				try {

					RupayCheckbinResponse rupaycheckbinResponse = SbiCardUtilCall.checkbin(value, header, propMap,
							merchant, merchantSetting, transactionLog, transactionEssentials);

					int[] aviAvailableAuthMode = Arrays.stream(rupaycheckbinResponse.getAvailableAuthMode().split(","))
							.mapToInt(Integer::parseInt).toArray();

					if (aviAvailableAuthMode.length > 0
							&& Arrays.stream(aviAvailableAuthMode).anyMatch(id -> id == 01)) {

						try {

							logger.info("Rupay Avilable Auth=================>" + aviAvailableAuthMode
									+ " For transaction_id================================>"
									+ transactionLog.getTransactionId());

							Initiate2Response initiateResponse = SbiCardUtilCall.initiate(carddetailsVo, header,
									propMap, merchant, amount, merchantSetting, transactionLog, sbitokenResponse,
									transactionEssentials);

							if (initiateResponse.getErrorcode() != null
									&& initiateResponse.getErrorcode().equals("0")) {

								RupayEncryptedData rupayencData = SbiCardUtilCall.encrptCardDetails(carddetailsVo,
										tokenheader, propMap, merchant, merchantSetting, transactionLog,
										transactionEssentials, cardType, amount, enccardData);

								transactionEssentials.setUdf39(rupayencData.getIv());
								transactionEssentials.setUdf41(rupayencData.getEncryptedData()); // rupay Encrypted Data
								transactionEssentials.setUdf40(initiateResponse.getRupayTransactionId());// rupay
																											// Transaction
																											// Id
																											//
																											//

								transactionEssentialsRepo.save(transactionEssentials);

								String reditectparamurl = initiateResponse.getRedirectURL();

								logger.info("SBI Initiate Redirect Url Response==================>" + reditectparamurl
										+ " For transaction_id======================>"
										+ transactionLog.getTransactionId());
								RupayRedirectData rupay = new RupayRedirectData(reditectparamurl);

								logger.info("SBI Initiate Redirect Url Response after Trim==================>" + rupay
										+ " For transaction_id=======================>"
										+ transactionLog.getTransactionId());

								String redirectionurl = propMap.get(SBICardsUtils.SBI_WIMBO_RUPAY_REDIRECTION_URL);
								logger.info("SBI Wimbo Url Rupay Redirection ======>" + redirectionurl
										+ " For transaction_id===============================>"
										+ transactionLog.getTransactionId());
								RedirectionRequestParameters redirectionrequest = new RedirectionRequestParameters();
								redirectionrequest.setAccuCardholderId(rupay.getAccuCardholderId());
								redirectionrequest.setAccuGuid(rupay.getAccuGuid());
								redirectionrequest.setAccuReturnURL(redirectionurl);
								redirectionrequest.setSession(initiateResponse.getSession());
								redirectionrequest.setAccuRequestId(initiateResponse.getAccuRequestId());

								Map<String, String> redirectparam = new HashMap<>();
								redirectparam.put("AccuCardholderId", redirectionrequest.getAccuCardholderId());
								redirectparam.put("AccuGuid", redirectionrequest.getAccuGuid());
								redirectparam.put("AccuReturnURL", redirectionrequest.getAccuReturnURL());
								redirectparam.put("session", redirectionrequest.getSession());
								redirectparam.put("AccuRequestId", redirectionrequest.getAccuRequestId());

								response.setPost(false);
								response.setResponseCode("02");
								response.setPostReqParam(redirectparam);
								response.setProcessorCode("475");
								response.setDescription("3d secure verifcation is pending");
								response.setStatus("success");
								response.setResult(true);
								response.setProcessor("SBI BANK");
								response.setType("REDIRECT");
								response.setThreeDSecureUrl(rupay.getRedirectURL());
//								response.setThreeDSecureUrl("https://qccert.npci.org.in/bepg/cert/iasAuthentication");

								return response;

							} else {
								if (initiateResponse.getErrormsg() != null
										&& !initiateResponse.getErrormsg().equals(" ")) {
									transactionLog.setBankErrorMsg(initiateResponse.getErrormsg());
									transactionLogRepo.save(transactionLog);
								}

								response.setResponseCode("01");
								response.setDescription("Invalid Details");
								return response;
							}
						} catch (Exception e) {

							new GlobalExceptionHandler().customException(e);
							response.setResponseCode("01");
							response.setDescription("Invalid Details");
							return response;
						}
					} else if (aviAvailableAuthMode.length > 0
							&& Arrays.stream(aviAvailableAuthMode).anyMatch(id -> id == 02)) {

						try {

							logger.info("The SBI Rupay Card is SEAMLESS==========>"
									+ " For transaction_id==================================>"
									+ transactionLog.getTransactionId());
							RupayEncryptedData rupayencData = SbiCardUtilCall.encrptCardDetails(carddetailsVo,
									tokenheader, propMap, merchant, merchantSetting, transactionLog,
									transactionEssentials, cardType, amount, enccardData);

							transactionEssentials.setUdf39(rupayencData.getIv());
							transactionEssentials.setUdf41(rupayencData.getEncryptedData());// rupay Encrypted Data
							transactionEssentialsRepo.save(transactionEssentials);

							String carddetailsuser = gson.toJson(carddetailsVo);
							cardMap.put("carddetailsuser", carddetailsuser);

							response.setPost(false);
							response.setResponseCode("02");
							response.setPostReqParam(cardMap);
							response.setProcessorCode("475");
							response.setDescription("3d secure verifcation is pending");
							response.setStatus("success");
							response.setResult(true);
							response.setProcessor("SBI BANK");
							response.setType("SEAMLESS");

							return response;

						} catch (Exception e) {

							new GlobalExceptionHandler().customException(e);
							response.setResponseCode("01");
							response.setDescription("Invalid Details");
							return response;

						}

					}

				} catch (Exception e) {

					new GlobalExceptionHandler().customException(e);
					response.setResponseCode("01");
					response.setDescription("Invalid Details");
					return response;
				}

			} else {

				if (carddetailsVo != null && carddetailsVo.getCardProvider().equalsIgnoreCase("VISA")) {

					logger.info("SBI Cards Visa ==============================>"
							+ " For transaction_id===============================>"
							+ transactionLog.getTransactionId());
					cardType = "V";

				} else if (carddetailsVo != null && carddetailsVo.getCardProvider().equalsIgnoreCase("MASTER")) {

					logger.info("SBI Cards Master ==============================>"
							+ " For transaction_id==============================>" + transactionLog.getTransactionId());
					cardType = "M";

				}

				sbitokenResponse = SbiCardUtilCall.generateAltIdToken(carddetailsVo, tokenheader, propMap, merchant,
						merchantSetting, transactionLog, transactionEssentials, cardType, amount, enccardData);
				if (sbitokenResponse != null && sbitokenResponse.getStatusCode().equalsIgnoreCase("TK0000")) {

					transactionEssentials.setUdf45(sbitokenResponse.getEncTokenInfo());
					transactionEssentials.setUdf44(sbitokenResponse.getTokenExpiryDate());
					transactionEssentials.setUdf43(sbitokenResponse.getVar2());
					transactionEssentials.setUdf42(sbitokenResponse.getIv());

				} else {
					if (sbitokenResponse != null && sbitokenResponse.getMsg() != null
							&& !sbitokenResponse.getMsg().equals(" ")) {
						transactionLog.setBankErrorMsg(sbitokenResponse.getMsg());
						transactionLogRepo.save(transactionLog);
					}
					response.setResponseCode("01");
					response.setDescription("Invalid Details");
					return response;
				}

				if (sbitokenResponse != null && sbitokenResponse.getStatusCode().equalsIgnoreCase("TK0000")) {
					Map<String, String> redirectparam = new HashMap<>();

					SbiRequestHeader header = new SbiRequestHeader();

					header.setXapikey(propMap.get(SBICardsUtils.SBI_HEADER_XAPI_KEY)); // todo bank
					header.setPgInstanceId(propMap.get(SBICardsUtils.SBI_ACQUIRERID));// aquirer bin
					header.setMerchantId(propMap.get(SBICardsUtils.SBI_ACQUIRERID));
					logger.info("SBI V/M Cards Headers======>" + header
							+ " For transaction_id==================================>"
							+ transactionLog.getTransactionId());

					PvrqVersioningResponse pvrqVersioningResponse = SbiCardUtilCall.pvrq(carddetailsVo, header,
							merchantSetting, merchant, transactionLog, propMap);

					transactionEssentials.setUdf47(pvrqVersioningResponse.getThreeDSServerTransID());
					transactionEssentials.setUdf48(pvrqVersioningResponse.getP_messageVersion());

					if (pvrqVersioningResponse != null && pvrqVersioningResponse.getErrorCode().contains("000")) {

						try {

							ParqAuthenticationResponse parqAuthenticationResponse = SbiCardUtilCall.pareq(carddetailsVo,
									amount, header, merchantSetting, merchant, transactionLog, pvrqVersioningResponse,
									propMap, transactionEssentials);

							if (parqAuthenticationResponse.getTransStatus() != null
									&& parqAuthenticationResponse.getTransStatus().equalsIgnoreCase("C")) {

								logger.info(" Inside Parq Respose===============> "
										+ parqAuthenticationResponse.getTransStatus()
										+ " For transaction_id=========================> "
										+ transactionLog.getTransactionId());

								transactionEssentials.setUdf46(parqAuthenticationResponse.getAuthenticationUrl());
								transactionEssentials.setUdf49(parqAuthenticationResponse.getAcsTransID());
								transactionEssentials.setUdf50(parqAuthenticationResponse.getDsTransID());

								transactionEssentialsRepo.save(transactionEssentials);

								if (parqAuthenticationResponse.getcReq() != null
										&& !parqAuthenticationResponse.getcReq().equalsIgnoreCase("")) {

									logger.info(" Inside Parq Respose===============> "
											+ parqAuthenticationResponse.getcReq() + ""
											+ parqAuthenticationResponse.getcReq()
											+ " For transaction_id=============================>"
											+ transactionLog.getTransactionId());
									redirectparam.put("creq", parqAuthenticationResponse.getcReq());
									response.setPost(false);
									response.setResponseCode("02");
									response.setPostReqParam(redirectparam);
									response.setProcessorCode("475");
									response.setDescription("3d secure verifcation is pending");
//									response.setProcessor("SBI BANK");
									response.setStatus("success");
									response.setResult(true);
									response.setThreeDSecureUrl(parqAuthenticationResponse.getAcsChallengeReqUrl());
									return response;
								}

								else {
									response.setResponseCode("01");
									response.setDescription("Invalid Details");
									return response;
								}

							} else if (parqAuthenticationResponse.getTransStatus() != null
									&& parqAuthenticationResponse.getTransStatus().equalsIgnoreCase("Y")) {
								Gson gson = new Gson();
								logger.info(
										" Inside Parq Respose===============>" + gson.toJson(parqAuthenticationResponse)
												+ "" + parqAuthenticationResponse.getTransStatus()
												+ " For transaction_id===========================>"
												+ transactionLog.getTransactionId());

								transactionEssentialsRepo.save(transactionEssentials);

								redirectparam.put("parqAuthenticationResponse",
										gson.toJson(parqAuthenticationResponse));
								response.setPost(false);
								response.setResponseCode("02");
								response.setPostReqParam(redirectparam);
								response.setProcessorCode("475");
								response.setDescription("3d secure verifcation is pending");
								response.setStatus("success");
//								response.setProcessor("SBI BANK");
								response.setResult(true);
								response.setThreeDSecureUrl(propMap.get(SBICardsUtils.SBI_CARD_RETURN_URL)
										.replace("#ru", String.valueOf(transactionLog.getTransactionId())));
								return response;

							}

							else if (parqAuthenticationResponse.getAcsChallengeReqFormData() != null
									&& !parqAuthenticationResponse.getAcsChallengeReqFormData().getPaReq()
											.equalsIgnoreCase("")) {

								logger.info(" Inside Parq Respose===============>"
										+ parqAuthenticationResponse.getAcsChallengeReqFormData().getPaReq() + ""
										+ parqAuthenticationResponse.getAcsChallengeReqFormData()
										+ " For transaction_id===========================>"
										+ transactionLog.getTransactionId());

								transactionEssentialsRepo.save(transactionEssentials);
								redirectparam.put("Pareq",
										parqAuthenticationResponse.getAcsChallengeReqFormData().getPaReq());
								response.setPost(false);
								response.setResponseCode("02");
								response.setPostReqParam(redirectparam);
								response.setProcessorCode("475");
								response.setDescription("3d secure verifcation is pending");
								response.setStatus("success");
//								response.setProcessor("SBI BANK");
								response.setResult(true);
								response.setThreeDSecureUrl(parqAuthenticationResponse.getAcsChallengeReqUrl());
								return response;

							}

							else {
								response.setResponseCode("01");
								response.setDescription("Invalid Details");
								return response;
							}
						} catch (Exception e) {

							new GlobalExceptionHandler().customException(e);
							response.setResponseCode("01");
							response.setDescription("Invalid Details");
							return response;
						}
					}

					else {

						response.setResponseCode("01");
						response.setDescription("Invalid Details");
						return response;
					}
				}

				else {

					response.setResponseCode("01");
					response.setDescription("Invalid Details");
					return response;
				}

			}

		} catch (Exception e) {
//				new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		} finally {
		}

		return null;

	}

	public PaymentResponse paynetznew(PaymentRequest pgRequest, Merchant merchant, MerchantSetting merchantSetting) {

		logger.info("<---------- Inside  Payment netz new --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		String url = null;
		PaymentResponse response = new PaymentResponse();
		Map<String, String> propMap = new HashMap<>();
		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

			UserCardDetails cardDetails = PGUtility.parseCardDetails(pgRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}

			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();

			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();

			}

			PropertiesVo properties = propertiesService.findByPropertykeyWithUpdatedCerts(PGUtility.AWS_ARN_KEY);

			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					cardDetails);
			if (cardDetails != null && cardDetails.getCardProvider() != null
					&& cardDetails.getCardProvider().equalsIgnoreCase("MASTERCARD")) {
				transactionLog.setUdf10("MASTER");
			} else if (cardDetails != null && cardDetails.getCardProvider() != null) {
				transactionLog.setUdf10(cardDetails.getCardProvider());
			}

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				transactionLog = transactionLogRepo.save(transactionLog);
				transactionLog.setTransactionId(transactionLog.getTransactionId());
			}

			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);

			Map<String, String> postParams = new HashMap<String, String>();
			String ReturnUrl = AipayService.PAYNETZ_RETURN_URL.replace("#ru", "" + transactionLog.getTransactionId());
			logger.info("cardDetails.getCardType() ==> " + cardDetails.getCardType());

			String encryptedData = "";
			String decryptedData = "";

			logger.info("############################ PayController Post #########################");

			String atomMid = "8952";
			String merchantId = atomMid;

			String merchTxnId = "563265656566";
			String merchantTxnId = merchTxnId;

			logger.info("MerchantId------: " + merchantId);
			logger.info("Amount----------: " + mAmount);
			logger.info("MerchantTxnId---: " + merchantTxnId);
			logger.info("ReturnURL-------: " + ReturnUrl);

			MerchDetails merchDetails = new MerchDetails();
			merchDetails.setMerchId(merchantId);
			merchDetails.setMerchTxnId(merchantTxnId);
			merchDetails.setUserId("");
			merchDetails.setPassword("Test@123");
			DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime date = LocalDateTime.now();
			String dateFormat = myFormat.format(date);
			merchDetails.setMerchTxnDate(dateFormat);

			PayDetails payDetails = new PayDetails();
			String amount = String.valueOf(mAmount);
			payDetails.setAmount(amount);
			payDetails.setCustAccNo("213232323");
			payDetails.setTxnCurrency("INR");

			payDetails.setProduct("NSE");
			CustDetails custDetails = new CustDetails();
			custDetails.setCustEmail("suraj.chavan@atomtech.in");
			custDetails.setCustMobile("1234567890");

			HeadDetails headDetails = new HeadDetails();
			headDetails.setApi("AUTH");
			headDetails.setVersion("OTSv1.1");
			headDetails.setPlatform("FLASH");

			Extras extras = new Extras();
			extras.setUdf1("");
			extras.setUdf2("");
			extras.setUdf3("");
			extras.setUdf4("");
			extras.setUdf5("");

			PayInstrument payInstrument = new PayInstrument();

			payInstrument.setMerchDetails(merchDetails);
			payInstrument.setPayDetails(payDetails);
			payInstrument.setCustDetails(custDetails);
			payInstrument.setHeadDetails(headDetails);
			payInstrument.setExtras(extras);

			OtsTransaction otsTxn = new OtsTransaction();
			otsTxn.setPayInstrument(payInstrument);

			Gson gson = new Gson();
			String json = gson.toJson(otsTxn);
			logger.info("OtsTransaction json data============>" + json);

			String serverResp = "";
			String decryptResponse = "";
			try {
				AuthEncryption authEncryption = new AuthEncryption();

				encryptedData = AuthEncryption.getAuthEncrypted(json, AuthEncryption.PAYMETZ_NEW_ENC_KEY);
				logger.info("EncryptedData------: " + encryptedData);

				serverResp = AipayService.getAtomTokenId(merchantId, encryptedData);
				logger.info("serverResp Result------: " + serverResp);
				logger.info("serverResp Length------: " + serverResp.length());
				logger.info("serverResp Condition---: " + serverResp.startsWith("merchId"));

				if ((serverResp != null) && (serverResp.startsWith("merchId"))) {
					decryptResponse = serverResp.split("\\&encData=")[1];
					logger.info("serrResp---: " + decryptResponse);

					decryptedData = AuthEncryption.getAuthDecrypted(decryptResponse,
							AuthEncryption.PAYNETZ_NEW_DEC_KEY);
					logger.info("DecryptedData------: " + decryptedData);

					ServerResponse serverResponse = new ServerResponse();
					serverResponse = (ServerResponse) gson.fromJson(decryptedData, ServerResponse.class);

					String atomTokenId = serverResponse.getAtomTokenId();
					logger.info("serverResponse-----: " + serverResponse);
					logger.info("TokenId------------: " + atomTokenId);
					postParams.put("TokenId", atomTokenId);
					postParams.put("amount", String.valueOf(mAmount));
					postParams.put("merchantId", atomTokenId);
					postParams.put("returnURL", ReturnUrl);

					logger.info("POST Param for Paynetz=====>" + postParams);
					response.setPost(false);
					response.setResponseCode("02");
					response.setPostReqParam(postParams);
					response.setProcessorCode("475");
					response.setDescription("3d secure verifcation is pending");
					response.setStatus("success");
					response.setProcessor("PAYNETZNEW");
					response.setResult(true);

					// return response;
				}

			} catch (Exception e) {
				new GlobalExceptionHandler().customException(e);
				response.setResponseCode("01");
				response.setDescription("Invalid Details");
				return response;
			}
			return response;

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		}

	}

	public PaymentResponse billDesk(PaymentRequest pgRequest, Merchant merchant, MerchantSetting merchantSetting) {

		logger.info("<---------- Inside BILL DESK NB Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		String url = null;
		PaymentResponse response = new PaymentResponse();
		Map<String, String> propMap = new HashMap<>();
		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property.getPropertyValue());
			}
			UserCardDetails cardDetails = PGUtility.parseCardDetails(pgRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}
			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();
			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();
			}
			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					cardDetails);
			if (cardDetails != null && cardDetails.getCardProvider() != null
					&& cardDetails.getCardProvider().equalsIgnoreCase("MASTERCARD")) {
				transactionLog.setUdf10("MASTER");
			} else if (cardDetails != null && cardDetails.getCardProvider() != null) {
				transactionLog.setUdf10(cardDetails.getCardProvider());
			}
			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				transactionLog = transactionLogRepo.save(transactionLog);
				transactionLog.setTransactionId(transactionLog.getTransactionId());
			}
			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);
			Map<String, String> postParams = new HashMap<String, String>();
			logger.info("cardDetails.getCardType() ==> " + cardDetails.getCardType());
			String ReturnUrl = propMap.get(BillDeskUtils.BILL_DESK_NB_RETURN_URL_V2).replace("#ru",
					"" + transactionLog.getTransactionId());
			if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("NB")) {

				String signingKeyFingerPrint = BillDeskUtils
						.getThumbprint(propMap.get(BillDeskUtils.BILL_DESK_GETEPAY_PUBLIC_SIGNING_KEY));
				logger.info("Signing Key Finger Print =======>" + signingKeyFingerPrint);

				String encryptionKeyFingerPrint = BillDeskUtils
						.getFingerprint(propMap.get(BillDeskUtils.BILL_DESK_ENC_KEY));
				logger.info("Encyption Key Finger Print =======>" + encryptionKeyFingerPrint);
				RSAPublicKey encryptionKey = (RSAPublicKey) VerifyJWS
						.getPublicKey(propMap.get(BillDeskUtils.BILL_DESK_ENC_KEY));

				RSAPrivateKey signingKey = (RSAPrivateKey) BillDeskUtils
						.signingPrivateKey(propMap.get(BillDeskUtils.BILL_DESK_GETEPAY_PRIVATE_SIGNING_KEY));
				ProcessorBank processorBank = new ProcessorBank();
				processorBank.setmId(merchant.getMid());
				processorBank.setBankId(Long.parseLong(pgRequest.getBankid()));
				processorBank.setStatus(true);
				processorBank.setProcessor("BILLDESK");
				processorBank = processorBankRepo.findByBankIdAndProcessorAndMIdAndStatus(processorBank.getBankId(),
						processorBank.getProcessor(), processorBank.getmId(), processorBank.isStatus());
				logger.info("Processor bank for billDesk========>" + processorBank);
				if (processorBank != null) {
					TransactionLog log = new TransactionLog();
					log.setProcessorTxnId(String.valueOf(System.currentTimeMillis()));
					CreateTransactionRequest createTrasnactionRequest = new CreateTransactionRequest();
					createTrasnactionRequest.setMercid(propMap.get(BillDeskUtils.BILL_DESK_MERC_ID));
					createTrasnactionRequest.setOrderid(String.valueOf(transactionLog.getTransactionId()));
					createTrasnactionRequest.setAmount(String.valueOf(mAmount));
					createTrasnactionRequest.setCurrency("356");
					createTrasnactionRequest.setBankid(processorBank.getProcessorBankId());
					createTrasnactionRequest.setItemcode("DIRECT");
					createTrasnactionRequest.setRu(ReturnUrl);
					createTrasnactionRequest.setPayment_method_type("netbanking");
					createTrasnactionRequest.setTxn_process_type("nb");
					Additional_Info additional_Info = new Additional_Info();
					additional_Info.setAdditional_info1("additional Info");
					createTrasnactionRequest.setAdditionalInfo(additional_Info);
					Device device = new Device();
					device.setInit_channel("internet");
					device.setIp("183.83.177.146");
					device.setUser_agent(
							"mozilla/5.0+(x11;+ubuntu;+linux+x86_64;+rv:72.0)+gecko/20100101+firefox/72.0");
					createTrasnactionRequest.setDevice(device);
					Gson gson = new Gson();
					String gsonRequestString = gson.toJson(createTrasnactionRequest);
					logger.info("Json BillDesk Request==========>" + gsonRequestString);

					String clientId = propMap.get(BillDeskUtils.BILL_DESK_CLIENT_ID);

					String encString = JoseHelper.encryptAndSign(gsonRequestString, encryptionKey, signingKey,
							signingKeyFingerPrint, encryptionKeyFingerPrint, clientId);

					logger.info("Encrypted jose String ======>" + encString);

					BillDeskRequestHeader header = new BillDeskRequestHeader();
					header.setBdTimeStramp(String.valueOf(System.currentTimeMillis()));
					header.setBdtraceId(BillDeskUtils.generateTraceId());

					logger.info("Bill Desk Header==========>" + header);

					String transactionUrl = propMap.get(BillDeskUtils.BILL_DESK_TRANSACTION_URL);
					String billDeskResponse = BillDeskUtils.postapi(transactionUrl, encString, header);

					logger.info("Bill Desk Response==========>" + billDeskResponse);
					RSAPrivateKey privateKey = (RSAPrivateKey) BillDeskUtils
							.decyptionPrivateKey(propMap.get(BillDeskUtils.BILL_DESK_GETEPAY_PRIVATE_DEC_KEY));
					String decResponse = JoseHelper.verifyAndDecrypt(billDeskResponse, encryptionKey, privateKey);

					logger.info("Bill Desk Dec Response==========>" + decResponse);
					ObjectMapper objectMapper = new ObjectMapper();
					CreateTransactionResponse billDeskresponse = objectMapper.readValue(decResponse,
							CreateTransactionResponse.class);

					logger.info("BillDesk Response Href Link =======>" + billDeskresponse.getLinks().get(0).getHref());
					logger.info("BillDesk Response Merchant Code =======>"
							+ billDeskresponse.getLinks().get(0).getParameters().getMerchant_code());
					logger.info("Bill Desk Response Encrypted Data=========>"
							+ billDeskresponse.getLinks().get(0).getParameters().getEncdata());
					postParams.put("merchant_code",
							billDeskresponse.getLinks().get(0).getParameters().getMerchant_code());
					postParams.put("encdata", billDeskresponse.getLinks().get(0).getParameters().getEncdata());
					response.setPost(false);
					response.setPostReqParam(postParams);
					response.setResponseCode("02");
					response.setProcessorCode("475");
					response.setDescription("3d secure verifcation is pending");
					response.setThreeDSecureUrl(billDeskresponse.getLinks().get(0).getHref());
					response.setStatus("success");
					response.setResult(true);
				}

				else {
					response.setResponseCode("01");
					response.setDescription("Proccessor Bank not Found");
					response.setStatus("Failed");
					return response;
				}

			} else {
				response.setResponseCode("01");
				response.setDescription("bank not configure for this merchant");
				response.setStatus("Failed");
				return response;
			}
			// return response;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		}
		return response;

	}

	public PaymentResponse idbichallan(PaymentRequest pgRequest, Merchant merchant, MerchantSetting merchantSetting) {

		logger.info("<---------- Inside IDBI CHALLAN NB Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		String url = null;
		PaymentResponse response = new PaymentResponse();
		Map<String, String> propMap = new HashMap<>();
		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();

			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();

			}

			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					null);

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				transactionLog = transactionLogRepo.save(transactionLog);
				transactionLog.setTransactionId(transactionLog.getTransactionId());
			}

			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);

			Map<String, String> postParams = new HashMap<String, String>();

			if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("CHALLAN")) {
				SSLHelper.disableCertificateValidation();
				VanAcctService service = new VanAcctService();
				VanAcct port = service.getVanAcct();
				VASBean bean = new VASBean();
				ArrayList<VASBean> list = new ArrayList<VASBean>();
				bean.setAccTitle(propMap.get(IDBIVANUtils.IDBI_ACC_TITLE));
				bean.setParentAccNumber(propMap.get(IDBIVANUtils.IDBI_PARENT_ACC_NUMBER));
				bean.setCustId(propMap.get(IDBIVANUtils.IDBI_CUSTOMER_ID));
				bean.setRemitterName(propMap.get(IDBIVANUtils.IDBI_REMITTER_NAME));
				String virtualAccNumber = "VGEP" + String.valueOf(System.currentTimeMillis()).substring(0, 12);
				bean.setVirtualAccNumber(virtualAccNumber);
				bean.setMode(propMap.get(IDBIVANUtils.IDBI_MODE));
				logger.info("Bean Request" + bean.toString());
				list.add(bean);
				logger.info("=============================================================");
				String vanresponse = port.masterDataInsertion(bean);
				logger.info("Port response ========>" + vanresponse);
				String[] responsearray = vanresponse.split("~");
				String responseVan = responsearray[0];
				logger.info("Response van======================>" + responseVan);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
				String challanDate = transactionLog.getCreatedDate().format(dtf);
				String challanValidity = transactionLog.getUdf6();
				if (challanValidity == null || challanValidity.equals("")) {
					LocalDateTime validity = transactionLog.getCreatedDate().plus(Period.ofDays(29));
					challanValidity = validity.format(dtf);
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date d = sdf.parse(challanValidity);
						SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy");
						challanValidity = sdf2.format(d);
					} catch (ParseException e) {
						new GlobalExceptionHandler().customException(e);
					}

				}
				if (responseVan != null) {

					BigDecimal challanAmount = transactionLog.getAmt();
					BigDecimal challanTotalCharges = new BigDecimal(transactionLog.getTotalServiceCharge())
							.add(transactionLog.getCommision());
					BigDecimal totalChallanAmount = BigDecimal.ZERO;

//						if(transactionLog.getServiceChargeType().equalsIgnoreCase("Excl") && transactionLog.getCommisionType().equalsIgnoreCase("Excl")) {
//							 totalChallanAmount=challanAmount.add(challanTotalCharges);
//						}
//						else {
//							totalChallanAmount=totalChallanAmount.add(challanAmount);
//							challanTotalCharges=BigDecimal.ZERO;
//						}

					transactionLog.setUdf5(responseVan);
					transactionLogRepo.save(transactionLog);
					postParams.put("tId", String.valueOf(transactionLog.getTransactionId()));
					postParams.put("merchantTxnId", transactionLog.getMerchanttxnid());
					postParams.put("Beneficiary Name", merchant.getMerchantName());
					postParams.put("Beneficiary ID", responseVan);

					postParams.put("Account No", propMap.get(IDBIVANUtils.IDBI_REMITTER_NAME));
					postParams.put("IFSC Code", propMap.get(IDBIVANUtils.IDBI_IFSC_CODE));
					postParams.put("Bank Name", propMap.get(IDBIVANUtils.IDBI_BANK_NAME));
					postParams.put("Branch Name", propMap.get(IDBIVANUtils.IDBI_BRANCH_NAME));

					postParams.put("Name", pgRequest.getUdf1());
					postParams.put("Mobile No", pgRequest.getUdf2());
					postParams.put("Email ID", pgRequest.getUdf3());
					postParams.put("Date", challanDate);
					postParams.put("challanValidity", challanValidity);
					postParams.put("Amount", String.valueOf(challanAmount));
					postParams.put("TotalconvienceCharges", String.valueOf(transactionLog.getTotalServiceCharge()));
					postParams.put("TotalcomissionChages", String.valueOf(transactionLog.getCommision()));
					postParams.put("TotalAmount", String.valueOf(mAmount));
					postParams.put("processor", merchantSetting.getProcessor());

					response.setPost(true);
					response.setPostReqParam(postParams);
					response.setResponseCode("02");
					response.setProcessorCode("475");
					response.setDescription("3d secure verifcation is pending");
					response.setThreeDSecureUrl(propMap.get(IDBIVANUtils.IDBI_CHALLAN_RU));
					response.setStatus("success");
					response.setResult(true);

				} else {
					response.setResponseCode("01");
					response.setDescription("Unable to create the Chlllan");

				}

			}
			// return response;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		}
		return response;

	}

	public PaymentResponse simulatorPayment(PaymentRequest pgRequest, Merchant merchant,
			MerchantSetting merchantSetting) {

		logger.info("<---------- Inside Simulator Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		PaymentResponse response = new PaymentResponse();
		Map<String, PropertiesVo> propMap = new HashMap<>();
		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property);
			}

			String url = "";
			UserCardDetails cardDetails = PGUtility.parseCardDetails(pgRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}

			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();

			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();

			}

			String bin = PGUtility.getBin(pgRequest, merchant);
			logger.info("bin => " + bin);
			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					cardDetails);
			if (cardDetails != null && cardDetails.getCardProvider() != null
					&& cardDetails.getCardProvider().equalsIgnoreCase("MASTERCARD")) {
				transactionLog.setUdf10("MASTER");
			} else if (cardDetails != null && cardDetails.getCardProvider() != null) {
				transactionLog.setUdf10(cardDetails.getCardProvider());
			}

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				transactionLog = transactionLogRepo.save(transactionLog);
				transactionLog.setTransactionId(transactionLog.getTransactionId());
			}

			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);

			int amount = mAmount.multiply(new BigDecimal(100)).intValue();

			String uuid = "" + System.currentTimeMillis();
			transactionLog.setProcessorTxnId(uuid);
			transactionLogRepo.save(transactionLog);

			logger.info("cardDetails.getCardType() ==> " + cardDetails.getCardType());

			Map<String, String> payuParams = new HashMap<>();
			Set<String> payuParamKeys = payuParams.keySet();

			response.setProcessor("SIMULATOR");

			if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("CC")) {
				response.setType("CC");
				String ru = propMap.get(SimulatorUtils.SIMULATOR_CC_RETURN_URL_V2).getPropertyValue();
				ru = ru.replace("#ru", String.valueOf(transactionLog.getTransactionId()));
				url = ru;
				payuParams = null;
			} else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("DC")) {
				response.setType("DC");
				String ru = propMap.get(SimulatorUtils.SIMULATOR_CC_RETURN_URL_V2).getPropertyValue();
				ru = ru.replace("#ru", String.valueOf(transactionLog.getTransactionId()));
				url = ru;
				payuParams = null;
			} else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("NB")) {
				response.setType("NB");
				// https://pay1.getepay.in/pgru/pgru/simulator/simulate-nb?tid=#txnId&bankCode=#bankCode
				String ru = propMap.get(SimulatorUtils.SIMULATOR_NB_RETURN_URL_V2).getPropertyValue();
				url = ru;
				payuParams.put("tid", String.valueOf(transactionLog.getTransactionId()));
				payuParams.put("bankCode", pgRequest.getBankid());
				for (String key : payuParamKeys) {
					logger.info("key::" + key + "=>value::" + payuParams.get(key));
				}
			} else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("WALLET")) {
				response.setType("Wallet");

			} else if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("UPI")) {
				response.setType("UPI");
				String ru = propMap.get(SimulatorUtils.SIMULATOR_CC_RETURN_URL_V2).getPropertyValue();
				ru = ru.replace("#ru", String.valueOf(transactionLog.getTransactionId()));
				url = ru;
				payuParams = null;
			}

			response.setPost(false);
			response.setPostReqParam(payuParams);
			response.setResponseCode("02");
			response.setProcessorCode("475");
			response.setDescription("3d secure verifcation is pending");
			response.setThreeDSecureUrl(url);
			response.setStatus("success");
			response.setResult(true);
			// return response;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		}
		logger.info("pgresponse => " + response);
		return response;

	}

	public PaymentResponse axisnb(PaymentRequest pgRequest, Merchant merchant, MerchantSetting merchantSetting) {

		logger.info("<---------- Inside Axis Bank Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		PaymentResponse response = new PaymentResponse();
		Map<String, String> propMap = new HashMap<>();
		// Map<String, String> postReqParam = new HashMap<>();
		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

			UserCardDetails cardDetails = PGUtility.parseCardDetails(pgRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}

			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();

			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();

			}

			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					cardDetails);
			if (cardDetails != null && cardDetails.getCardProvider() != null
					&& cardDetails.getCardProvider().equalsIgnoreCase("MASTERCARD")) {
				transactionLog.setUdf10("MASTER");
			} else if (cardDetails != null && cardDetails.getCardProvider() != null) {
				transactionLog.setUdf10(cardDetails.getCardProvider());
			}

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				transactionLog = transactionLogRepo.save(transactionLog);
				transactionLog.setTransactionId(transactionLog.getTransactionId());
			}

			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);
			// set data for generate signature
			Map<String, String> axisParams = new HashMap<String, String>();
			logger.info("cardDetails.getCardType() ==> " + cardDetails.getCardType());
			Gson gson = new Gson();
			String ru = propMap.get(AxisUtils.AXIS_NB_RETURN_URL_V2);
			ru = ru.replace("#ru", String.valueOf(transactionLog.getTransactionId()));
			String url = null;

			if (pgRequest.getPaymentMode() != null && pgRequest.getPaymentMode().equalsIgnoreCase("NB")) {

				// axis integration Code h

				AxisRequest axisrequest = new AxisRequest();
				axisrequest.setPRN(String.valueOf(transactionLog.getTransactionId()));
				axisrequest.setAMT(String.valueOf(mAmount));
				axisrequest.setCG(propMap.get(AxisUtils.AXIS_NB_CG));
				axisrequest.setCRN(propMap.get(AxisUtils.AXIS_NB_CRN));
				axisrequest.setITC(String.valueOf(transactionLog.getMerchanttxnid()));
				axisrequest.setMD(propMap.get(AxisUtils.AXIS_NB_MD));
				axisrequest.setPID(propMap.get(AxisUtils.AXIS_NB_PID));
				axisrequest.setRESPONSE(propMap.get(AxisUtils.AXIS_NB_RESPONSE));

				String checksumrequest = axisrequest.checksumpipeSeprated();
				logger.info("Checksum request============>" + checksumrequest);

				String checksumvalue = AxisUtils.getSHA256(checksumrequest);
				axisrequest.setChecksum(checksumvalue);
				logger.info("Checksum value=====================>" + checksumvalue);
				logger.info("qs Request============>" + axisrequest);

				String finalRequest = axisrequest.pipeSeprated();
				logger.info("final Request with pipeSeprated=========>" + finalRequest);

				// String salt, String iv, String encKey, String painString
				String qs = AxisUtils.encrypt(propMap.get(AxisUtils.AXIS_NB_SALT), propMap.get(AxisUtils.AXIS_NB_IV),
						propMap.get(AxisUtils.AXIS_NB_SECRET_KEY), finalRequest);

				logger.info("Encrypted qs=====================>" + qs);
				logger.info("Return Url========================>" + ru);

				axisParams.put("qs", qs);
				axisParams.put("RU", ru);
				axisParams.put("MERCHANT_ID", "");
				axisParams.put("CHECKSUM", "");

				url = propMap.get(AxisUtils.AXIS_NB_TRANSACTION_URL);
				url = url.replace("#category_ID", propMap.get(AxisUtils.AXIS_NB_CATEGORY_ID));

			} else {
				response.setResponseCode("01");
				response.setDescription("bank not configure for this merchant");
				response.setStatus("Failed");
				return response;
			}

			response.setPost(false);
			response.setPostReqParam(axisParams);
			response.setResponseCode("02");
			response.setProcessorCode("475");
			response.setDescription("3d secure verifcation is pending");
			response.setThreeDSecureUrl(url);
			response.setStatus("success");
			response.setResult(true);
			// return response;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		}
		return response;
	}

	public PaymentResponse yesnb(PaymentRequest pgRequest, Merchant merchant, MerchantSetting merchantSetting) {

		logger.info("<---------- Inside YES Bank Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		PaymentResponse response = new PaymentResponse();
		Map<String, String> propMap = new HashMap<>();
		// Map<String, String> postReqParam = new HashMap<>();
		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

			UserCardDetails cardDetails = PGUtility.parseCardDetails(pgRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}

			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();
			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();
			}

			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					cardDetails);
			if (cardDetails != null && cardDetails.getCardProvider() != null
					&& cardDetails.getCardProvider().equalsIgnoreCase("MASTERCARD")) {
				transactionLog.setUdf10("MASTER");
			} else if (cardDetails != null && cardDetails.getCardProvider() != null) {
				transactionLog.setUdf10(cardDetails.getCardProvider());
			}

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				try {
					transactionLogRepo.save(transactionLog);
				} catch (Exception e) {
					new GlobalExceptionHandler().customException(e);
				}
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
				transactionLog = transactionLogRepo.save(transactionLog);
				transactionLog.setTransactionId(transactionLog.getTransactionId());
			}

			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);
			String dateString = transactionLog.getDate();

			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date transactionDate = inputFormat.parse(dateString);

			SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String formattedDate = outputFormat.format(transactionDate);

			String returnUrl = propMap.get(YESBankUtils.YES_BANK_RETURN_URL);
			returnUrl = returnUrl.replace("#ru", String.valueOf(transactionLog.getTransactionId()));
			String url = propMap.get(YESBankUtils.YES_BANK_TRANSACTION_URL);
			String YES_BANK_NB_KEY = propMap.get(YESBankUtils.YES_BANK_NB_KEY);
			String YES_BANK_PID = propMap.get(YESBankUtils.YES_BANK_PID);

			YesBankNBRequest data = new YesBankNBRequest();
			data.setFldClientCode(merchantSetting.getMloginId());
			data.setFldMerchCode(merchantSetting.getmPassword());
			data.setFldTxnCurr(merchantSetting.getCurrency());
			data.setFldTxnAmt(String.valueOf(mAmount));
			data.setFldTxnScAmt("0");
			data.setFldMerchRefNbr(String.valueOf(transactionLog.getTransactionId()));
			data.setFldSucStatFlg("N");
			data.setFldFailStatFlg("N");
			data.setFldDatTimeTxn(formattedDate);
			data.setFldRef1("");
			data.setFldRef2("INSUB01");
			data.setFldRef3("");
			data.setFldRef4("");
			data.setFldRef5("");
			data.setFldRef6("");
			data.setFldRef7("");
			data.setFldRef8("");
			data.setFldRef9("");
			data.setFldRef10("");
			data.setFldRef11("");
			data.setFldDate1("");
			data.setFldDate2("");
			data.setRU(returnUrl);
			data.setFldClientAcctNo("");
			data.setB1("Submit");

			logger.info("YES BANK NB Checksum Request====>" + data.checksum());
			String sha2Checksum = YESBankUtils.CalculatedChecksum(data.checksum());
			logger.info("YES BANK NB calculated Checksum Request====>" + sha2Checksum);
			data.setCHECKSUM(sha2Checksum);
			String finaldata = data.finalChecksum();
			logger.info("YES BANK NB Request finaldata===>" + finaldata);
			String encData = YESBankUtils.encrypt(finaldata, YES_BANK_NB_KEY);
			logger.info("YES BANK NB finaldata===>" + finaldata);

			String appendString = "PID=" + YES_BANK_PID + "&encdata=" + encData;
			url = url + "?" + appendString;

			logger.info("YES BANK NB final Url==========>" + url);
			response.setPost(false);
			response.setResponseCode("02");
			response.setProcessorCode("475");
			response.setDescription("3d secure verifcation is pending");
			response.setThreeDSecureUrl(url);
			response.setStatus("success");
			response.setResult(true);
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		}

		return response;
	}

	public PaymentResponse nbbl(PaymentRequest pgRequest, Merchant merchant, MerchantSetting merchantSetting) {
		logger.info("<---------- Inside Cenrtal Bank Payment --------->");
		logger.info("PGRequested " + pgRequest);
		logger.info("merchant Detail " + merchant);
		logger.info("merchant Setting " + merchantSetting);
		PaymentResponse response = new PaymentResponse();
		Map<String, String> paynextPostparam = new HashMap<>();
		// Map<String, String> postReqParam = new HashMap<>();
		Map<String, String> propMap = new HashMap<>();
		// Map<String, String> postReqParam = new HashMap<>();
		try {
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property.getPropertyValue());
			}

			UserCardDetails cardDetails = PGUtility.parseCardDetails(pgRequest, merchant, merchantSetting);
			if (cardDetails == null) {
				response.setResponseCode("01");
				response.setDescription("Invalid Card data format");
				return response;
			}

			String requestType = pgRequest.getRequestType();
			TransactionLog transactionLog = new TransactionLog();
			try {
				if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
					Long transactionId = pgRequest.getTransactionId();
					transactionLog = transactionLogRepo.findById(transactionId).get();
				}
			} catch (Exception e) {
				transactionLog = new TransactionLog();
			}

			transactionLog = PGUtility.parseDetailsAfterPgDetails(transactionLog, pgRequest, merchant, merchantSetting,
					cardDetails);
			if (cardDetails != null && cardDetails.getCardProvider() != null
					&& cardDetails.getCardProvider().equalsIgnoreCase("MASTERCARD")) {
				transactionLog.setUdf10("MASTER");
			} else if (cardDetails != null && cardDetails.getCardProvider() != null) {
				transactionLog.setUdf10(cardDetails.getCardProvider());
			}

			if (requestType != null && !requestType.equals("") && requestType.equals("PAYMENT_PAGES")) {
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
			} else {
				// Merchant Commision
				merchantCommisionService.getMerchantCommission(transactionLog, pgRequest, merchant);
				convenienceChargesService.getConvienceCharges(transactionLog, pgRequest, merchant);
			}

			transactionLog = transactionLogRepo.save(transactionLog);

			TransactionEssentials transactionEssentials = transactionEssentialsRepo
					.findByTransactionId(transactionLog.getTransactionId());

			BigDecimal mAmount = pgRequest.getAmt().setScale(2, BigDecimal.ROUND_HALF_EVEN);

			NbblUtil.checkHeartBeatApi(transactionLog, propMap);
			
			LocalDateTime createdDate = transactionLog.getCreatedDate();
			ZonedDateTime zonedDateTime = createdDate.atZone(ZoneId.systemDefault());
			String timenow = NbblUtil.generateTimeStamp(zonedDateTime);
		    

			PaymentRequestNBBL paymentRequestnbbl = new PaymentRequestNBBL();

			String paId = propMap.get(NbblUtil.NBBL_PA_ID);
			String paName = propMap.get(NbblUtil.NBBL_PA_NAME);
			String refId = NbblUtil.generateRefId(paId, String.valueOf(transactionLog.getTransactionId()),createdDate);
			String messageId = NbblUtil.generateMessageId(String.valueOf(transactionLog.getTransactionId()),createdDate);
			
			Head head = new Head();
			head.setVer("1.0");
			head.setTs(timenow);
			head.setMsgID(messageId);
			head.setBankID("BAN01");
//			head.setBankAppId("BXY0123");
			head.setOrgID(paId);
			head.setCorrelationKey("");

			paymentRequestnbbl.setHead(head);
			paymentRequestnbbl.setTxn(Txn.builder().refID(refId).ts(timenow).expiry(300).initiationMode("REDIRECTION").build());

			Pa pa = new Pa();
			pa.setPaID(paId);
			pa.setPaName(paName);
//			pa.setCreds(Creds.builder().type("GSTN").value("27BBBBB0000A1Z6").build());
//			pa.setBeneficiary(Beneficiary.builder().bankId("BBA09").accNumber("6810353469612760").build());
			String credString = propMap.get("NBBL_CRED_DATA");
	        HashMap<String, Object> yourHashMap = new Gson().fromJson(credString, HashMap.class);
			pa.setCreds(yourHashMap);
			
			paymentRequestnbbl.setPa(pa);
			
			com.ftk.pg.vo.nbbl.reqTxnInit.Merchant merchantnbbl = new com.ftk.pg.vo.nbbl.reqTxnInit.Merchant();
			merchantnbbl.setMcc("5411");
			merchantnbbl.setMid("PGP17KRSHNMRT05");
			merchantnbbl.setmName("Shree Krishna Retail Pvt Ltd");
//			merchantnbbl.setBeneficiary(Beneficiary.builder().bankId("BHC01").accNumber("8027624710272780").build());

			ReturnUrl returnUrl = new ReturnUrl();
//			returnUrl.setSuccess("https://portal.getepay.in:8443/getePaymentPages/nbblResponse/refID");
			String ru = propMap.get(NbblUtil.NBBL_RETURN_URL);
			returnUrl.setSuccess(ru);
			merchantnbbl.setReturnUrl(returnUrl);

			paymentRequestnbbl.setMerchant(merchantnbbl);

			Payer payer = new Payer();

			Amount amount = new Amount();
			amount.setValue(mAmount.doubleValue());
			amount.setCurr("INR");
			
			AmountBreakUp amountBreakUp = new AmountBreakUp();
			List<Tag> tagListAmountBreakup = List.of(Tag.builder().name("Convenience Charges").value("12").build());
			amountBreakUp.setTag(tagListAmountBreakup);
			
			amount.setAmountBreakUp(amountBreakUp);
			payer.setAmount(amount);

			com.ftk.pg.vo.nbbl.reqTxnInit.Device device = new com.ftk.pg.vo.nbbl.reqTxnInit.Device();
			device.setMobile("919876543210");
			List<Tag> taglistdevice = List.of(
//					Tag.builder().name("GEOCODE").value("").build(),
//					Tag.builder().name("LOCATION").value("").build(), 
//					Tag.builder().name("APP").value("").build(), 
//					Tag.builder().name("BROWSER").value("").build(),
					Tag.builder().name("OS").value("IOS").build()
					);
			device.setTag(taglistdevice);
			payer.setDevice(device);

//			List<TPVDetail> tpvDetailList = List.of(
//					TPVDetail.builder().name("CUSTNAME").value("customer").build(),
//					TPVDetail.builder().name("ACNUM").value("5183209016332230").build(),
//					TPVDetail.builder().name("IFSC").value("ICIC0007452").build());
//			payer.setTPV(tpvDetailList);
//
//			List<Details> detailsList = List.of(Details.builder().name("Customer Name").value("Customer Name").build());
//			payer.setDetails(detailsList);

			paymentRequestnbbl.setPayer(payer);

			List<AdditionalInfo> additionalInfoList = List
					.of(AdditionalInfo.builder().name("SampleadditionalInfo").value("sampleAddInfoValue").build());
			paymentRequestnbbl.setAdditionalInfo(additionalInfoList);

			Gson gson = new Gson();
			String payload = gson.toJson(paymentRequestnbbl);
			
			logger.info("nbbl request payload : " + payload);

//			String upiPrivateKeyCrt = "/media/shared/nbbl/key_file/nbbl-hpy-key.pem";
//			String publicKeyCertificateFile = "/media/shared/nbbl/key_file/nbbl-hpy-cert.pem";
			String nbblPrivateKeyFile = propMap.get(NbblUtil.GTP_NBBL_PRIVATE_KEY_FILE);
			String nbblPublicKeyCert = propMap.get(NbblUtil.NBBL_PUBLIC_KEY_CERT);

			logger.info("nbblEncryption pvtKey : " + nbblPrivateKeyFile + " : publicCert : " + nbblPublicKeyCert);
			NbblEncryptionUtil nbblEncryptionUtil = new NbblEncryptionUtil(nbblPrivateKeyFile, nbblPublicKeyCert);
			
			String base64Signature = nbblEncryptionUtil.generateSignECDSA(payload, transactionLog);
			logger.info("nbbl Signature (Base64) === " + base64Signature);

			String protectedStringBase64 = nbblEncryptionUtil.generateProtectedInfoBase64(paId, transactionLog);
			logger.info("nbbl protectedInfo (Base64) === " + protectedStringBase64);


			NbblRequestWrapper nbblRequestWrapper = new NbblRequestWrapper();
			Signature signature = new Signature();
			signature.setProtectedValue(protectedStringBase64);
			signature.setSignature(base64Signature);
			nbblRequestWrapper.setSignature(signature);
			
//			paymentRequestnbbl.setSignature(signature);
			String payloadWithSignature = gson.toJson(paymentRequestnbbl);
			
			logger.info("payload with signature=>" + payloadWithSignature);
			
			String encryptPayload = nbblEncryptionUtil.encryptPayload(payloadWithSignature);
			
			nbblRequestWrapper.setPayload(encryptPayload);

			String finalRequestPayload = gson.toJson(nbblRequestWrapper);
			
			logger.info("nbbl encrypted request === " + finalRequestPayload);

			String trasnactionUrl = propMap.get(NbblUtil.NBBL_REQ_TXN_INIT_URL);
			trasnactionUrl = trasnactionUrl.replace("#referenceId", refId);
			logger.info("nbbl trasnactionUrl === " + trasnactionUrl);

			String apikey = propMap.get(NbblUtil.NBBL_REQ_TXN_INIT_API_KEY);
			String postapi = NbblUtil.postapi(finalRequestPayload, trasnactionUrl, apikey);
			logger.info("nbbl trasnaction api response === " + postapi);

			String nbblRu = "";
			logger.info("NBBL NB final Url==========>" + nbblRu);
			response.setPost(false);
			response.setResponseCode("02");
			response.setProcessorCode("475");
			response.setDescription("3d secure verifcation is pending");
			response.setThreeDSecureUrl(nbblRu);
			response.setStatus("success");
			response.setResult(true);

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
			response.setResponseCode("01");
			response.setDescription("Invalid Details");
			return response;
		}
		return null;
	}

}

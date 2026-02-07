package com.ftk.pg.util;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.responsevo.AuthenticationResponse;
import com.ftk.pg.responsevo.AuthorizationOnlyApiResponse;
import com.ftk.pg.responsevo.AutorizationResponse;
import com.ftk.pg.responsevo.CaptureApiResponse;
import com.ftk.pg.responsevo.DcEmiCancellationApiResponse;
import com.ftk.pg.responsevo.DebitCardEmiAuthenticationResponse;
import com.ftk.pg.responsevo.DebitCardEmiAuthorizationResponse;
import com.ftk.pg.responsevo.GetEmiPlanApiResponse;
import com.ftk.pg.responsevo.IntimateSiApiResponse;
import com.ftk.pg.responsevo.KotakResponseWrapper;
import com.ftk.pg.responsevo.MerchantCallBackPushApiResponse;
import com.ftk.pg.responsevo.RefundApiResponse;
import com.ftk.pg.responsevo.RefundStatusQueryApiResponse;
import com.ftk.pg.responsevo.ResendOtpResponse;
import com.ftk.pg.responsevo.SaleStatusQueryApiResponse;
import com.ftk.pg.responsevo.VerifyOtpApiResponse;
import com.ftk.pg.responsevo.VoidApiResponse;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class KotakCall {
	static Logger logger = LogManager.getLogger(KotakCall.class);
	String baseUrl;

	public KotakCall(String baseUrl) {
		this.baseUrl = baseUrl;

	}

	public KotakCall() {

	}

	public AuthenticationResponse authentication(String requestString, String encKey) {
		try {
			Gson gson = new Gson();

			String response = postApi(baseUrl, requestString, encKey);
			logger.info("kotak authentication response => " + response);

			AuthenticationResponse authResponse = gson.fromJson(response, AuthenticationResponse.class);
			return authResponse;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public String authentication(String requestString) {
		try {
			String url = baseUrl;
			logger.info("  url => " + url);
			String response = postApi(url, requestString);
			logger.info("kotak authentication response  => " + response);

			return response;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public AutorizationResponse authorization(String requestString, String encKey) {
		try {
			Gson gson = new Gson();
			String url = baseUrl;
			logger.info("  url => " + url);

			String response = postApi(url, requestString, encKey);
			logger.info(" Authorization response encData => " + response);

			AutorizationResponse autorizationResponse = gson.fromJson(response, AutorizationResponse.class);
			return autorizationResponse;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public VerifyOtpApiResponse verifyOtp(String requestString, String encKey) {
		try {
			Gson gson = new Gson();
			String url = baseUrl;
			logger.info(" VerifyOtp url => " + url);

			String response = postApi(url, requestString, encKey);
			logger.info("Response Verify Otp encData => " + response);
			VerifyOtpApiResponse verifyOtpResponse = gson.fromJson(response, VerifyOtpApiResponse.class);
			return verifyOtpResponse;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);

		}
		return null;
	}

	public ResendOtpResponse resendOtp(String requestString, String encKey) {
		try {
			Gson gson = new Gson();
			String url = baseUrl;
			logger.info("url => " + url);

			String response = postApi(url, requestString, encKey);
			logger.info("ResendOtpResponse encData => " + response);
			ResendOtpResponse resendOtpResponse = gson.fromJson(response, ResendOtpResponse.class);
			return resendOtpResponse;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public GetEmiPlanApiResponse getEmiPlanApi(String requestString, String encKey) {
		try {
			Gson gson = new Gson();
			String url = baseUrl;
			logger.info("url => " + url);

			String response = postApi(url, requestString, encKey);
			logger.info("GetEmiPlanApiResponse encData => " + response);

			GetEmiPlanApiResponse getEmiPlanResponse = gson.fromJson(response, GetEmiPlanApiResponse.class);
			return getEmiPlanResponse;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public SaleStatusQueryApiResponse saleStatusQueryApi(String requestString, String encKey) {
		try {
			Gson gson = new Gson();
			String url = baseUrl;
			logger.info("url => " + url);

			String response = postApi(url, requestString, encKey);
			logger.info("SaleStatusQueryApiResponse encData => " + response);

			SaleStatusQueryApiResponse saleStatusQueryApiResponse = gson.fromJson(response,
					SaleStatusQueryApiResponse.class);
			return saleStatusQueryApiResponse;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public RefundApiResponse refundApi(String requestString, String encKey) {
		try {
			Gson gson = new Gson();
			String url = baseUrl;
			logger.info("url => " + url);

			String response = postApi(url, requestString, encKey);
			logger.info("RefundApiResponse encData => " + response);

			RefundApiResponse refundApiResponse = gson.fromJson(response, RefundApiResponse.class);
			return refundApiResponse;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public RefundStatusQueryApiResponse refundStatusQueryApi(String requestString, String encKey) {
		try {
			Gson gson = new Gson();
			String url = baseUrl;
			logger.info("url => " + url);

			String response = postApi(url, requestString, encKey);
			logger.info("RefundStatusQueryApiResponse encData => " + response);

			RefundStatusQueryApiResponse refundstatusApiResponse = gson.fromJson(response,
					RefundStatusQueryApiResponse.class);
			return refundstatusApiResponse;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public CaptureApiResponse captureApi(String requestString, String encKey) {
		try {
			Gson gson = new Gson();
			String url = baseUrl;
			logger.info("url => " + url);

			String response = postApi(url, requestString, encKey);
			logger.info("CaptureApiResponse encData => " + response);
			CaptureApiResponse captureApiResponse = gson.fromJson(response, CaptureApiResponse.class);
			return captureApiResponse;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public MerchantCallBackPushApiResponse merchantCallBackPushApi(String requestString, String encKey) {
		try {
			Gson gson = new Gson();
			String url = baseUrl;

			String response = postApi(url, requestString, encKey);
			logger.info("MerchantCallBackPushApiResponse encData => " + response);

			MerchantCallBackPushApiResponse merchantCallBackPushApiResponse = gson.fromJson(response,
					MerchantCallBackPushApiResponse.class);
			return merchantCallBackPushApiResponse;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public VoidApiResponse voidApi(String requestString, String encKey) {
		try {
			Gson gson = new Gson();
			String url = baseUrl;
			logger.info("url => " + url);

			String response = postApi(url, requestString, encKey);
			logger.info("VoidApi response encData => " + response);

			VoidApiResponse voidApiResponse = gson.fromJson(response, VoidApiResponse.class);
			return voidApiResponse;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public DebitCardEmiAuthenticationResponse debitCardAuthenticationEmi(String requestString, String encKey) {
		try {
			Gson gson = new Gson();
			String url = baseUrl;
			logger.info("url => " + url);

			String response = postApi(url, requestString, encKey);
			logger.info("DebitCardEmiAuthenticationResponse encData => " + response);

			DebitCardEmiAuthenticationResponse cardEmiAuthenticationResponse = gson.fromJson(response,
					DebitCardEmiAuthenticationResponse.class);
			return cardEmiAuthenticationResponse;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public DcEmiCancellationApiResponse dcEmiCancellationApi(String requestString, String encKey) {
		try {
			Gson gson = new Gson();
			String url = baseUrl;
			logger.info("url => " + url);

			String response = postApi(url, requestString, encKey);
			logger.info("DcEmiCancellationApiResponse encData => " + response);

			DcEmiCancellationApiResponse dcEmiCancellationApiResponse = gson.fromJson(response,
					DcEmiCancellationApiResponse.class);
			return dcEmiCancellationApiResponse;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public AuthorizationOnlyApiResponse authorizationOnlyApi(String requestString, String encKey) {
		try {
			Gson gson = new Gson();
			String url = baseUrl;
			logger.info("url => " + url);

			String response = postApi(url, requestString, encKey);
			logger.info("AuthorizationOnlyApiResponse encData => " + response);

			AuthorizationOnlyApiResponse authorizationOnlyApiResponse = gson.fromJson(response,
					AuthorizationOnlyApiResponse.class);
			return authorizationOnlyApiResponse;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public IntimateSiApiResponse intimateSiApi(String requestString, String encKey) {
		try {
			Gson gson = new Gson();
			String url = baseUrl;
			logger.info("url => " + url);

			String response = postApi(url, requestString, encKey);
			logger.info("IntimateSi response encData => " + response);

			IntimateSiApiResponse intimateSiApiResponse = gson.fromJson(response, IntimateSiApiResponse.class);
			return intimateSiApiResponse;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public DebitCardEmiAuthorizationResponse debitCardAuthorizationEmi(String requestString, String encKey) {
		try {
			Gson gson = new Gson();
			String url = baseUrl;
			logger.info("url => " + url);

			String response = postApi(url, requestString, encKey);
			logger.info("response encData => " + response);

			DebitCardEmiAuthorizationResponse debitCardEmiAuthorizationResponse = gson.fromJson(response,
					DebitCardEmiAuthorizationResponse.class);
			return debitCardEmiAuthorizationResponse;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	public String postApi(String url, String requestString, String encKey) {
		Response response = null;
		try {
			OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
					.writeTimeout(10, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, requestString);
			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			response = client.newCall(request).execute();
			logger.info("kotak response => " + response);

			String responseString = response.body().string();
			logger.info("kotak responseString => " + responseString);

			String res = null;
			if (responseString != null) {
				Gson gson = new Gson();
				KotakResponseWrapper responseWrapper = gson.fromJson(responseString, KotakResponseWrapper.class);

				if (responseWrapper != null) {
					String encRes = responseWrapper.getEncData();
					String decString = KotakUtil.decryptCardRequest(encRes, encKey);

					res = decString;
				}

			}

			return res;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		} finally {
			if (response != null) {
				response.body().close();
			}
		}
		return null;

	}

	public String postApi(String url, String requestString) {
		Response response = null;
		try {
			OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
					.writeTimeout(10, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, requestString);
			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			response = client.newCall(request).execute();
			logger.info("kotak response => " + response);

			String responseString = response.body().string();
			logger.info("kotak responseString => " + responseString);

			return responseString;
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		} finally {
			if (response != null) {
				response.body().close();
			}
		}
		return null;

	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}


}
package com.ftk.pg.vo.iciciNb;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ICICINBUtils {

	static Logger logger = LogManager.getLogger(ICICINBUtils.class);
	
	public static VerifyOutput getApi(String url) {
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = RequestBody.create(mediaType, "");
			Request request = new Request.Builder().url(url).get().build();
			Response response = client.newCall(request).execute();

			String res = response.body().string();

			logger.info("Response ==========>" + res);
			VerifyOutput verifyOutput = xmltoObjectConverter(res);
			return verifyOutput;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	public static VerifyOutput xmltoObjectConverter(String response) {
		VerifyOutput verifyOutput = new VerifyOutput();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(response)));

			Element element = document.getDocumentElement();
			String BID = element.getAttribute("BID");
			String ITC = element.getAttribute("ITC");
			String AMT = element.getAttribute("AMT");
			String VERIFIED = element.getAttribute("VERIFIED");

			verifyOutput.setBID(BID);
			verifyOutput.setITC(ITC);
			verifyOutput.setAMT(AMT);
			verifyOutput.setVERIFIED(VERIFIED);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return verifyOutput;

	}

}

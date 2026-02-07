package com.ftk.pg.requestvo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ftk.pg.exception.GlobalExceptionHandler;

public class RupayRedirectData {
	static Logger logger = LogManager.getLogger(RupayRedirectData.class);

	private String accuCardholderId;
	private String accuGuid;
	private String accuHkey;
	private String redirectURL;

	public String getAccuCardholderId() {
		return accuCardholderId;
	}

	public void setAccuCardholderId(String accuCardholderId) {
		this.accuCardholderId = accuCardholderId;
	}

	public String getAccuGuid() {
		return accuGuid;
	}

	public void setAccuGuid(String accuGuid) {
		this.accuGuid = accuGuid;
	}

	public String getAccuHkey() {
		return accuHkey;
	}

	public void setAccuHkey(String accuHkey) {
		this.accuHkey = accuHkey;
	}
	
	

	

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}
	
	

	@Override
	public String toString() {
		return "RupayRedirectData [accuCardholderId=" + accuCardholderId + ", accuGuid=" + accuGuid + ", accuHkey="
				+ accuHkey + ", redirectURL=" + redirectURL + "]";
	}

	public RupayRedirectData(String urlString) {
		try {
			  String[] urlParts = urlString.split("\\?");
		        if (urlParts.length == 2) {
		            String baseUrl = urlParts[0];
		            this.redirectURL=baseUrl;
		            String queryParams = urlParts[1]; // This will give you the part after "?"

		            // Split the query parameters by "&"
		            String[] paramPairs = queryParams.split("&");
		            for (String paramPair : paramPairs) {
		                // Split each parameter by "="
		                String[] keyValue = paramPair.split("=");
		                if (keyValue.length == 2) {
		                    String paramName = keyValue[0];
		                    String paramValue = keyValue[1];

		                    if ("AccuCardholderId".equals(paramName)) {
		                        this.accuCardholderId = paramValue;
		                    } else if ("AccuGuid".equals(paramName)) {
		                        this.accuGuid = paramValue;
		                    } else if ("AccuHkey".equals(paramName)) {
		                        this.accuHkey = paramValue;
		                    } else if ("redirectURL".equals(paramName)) {
		                        this.redirectURL = paramValue;
		                    }
		                }
		            }
		        } else {
//		            System.out.println("Invalid URL format");
		            logger.info("Invalid URL format");
		        }
		    

			
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
	}

}

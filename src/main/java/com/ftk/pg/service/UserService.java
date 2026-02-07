package com.ftk.pg.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ftk.pg.dto.RequestWrapper;
import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.modal.TransactionLog;
import com.ftk.pg.modal.UpiQrDetail;
import com.ftk.pg.modal.User;
import com.ftk.pg.pi.modal.MerchantKeys;
import com.ftk.pg.pi.modal.VPAQrCode;
import com.ftk.pg.pi.repo.MerchantKeysRepo;
import com.ftk.pg.pi.repo.VPAQrCodeRepo;
import com.ftk.pg.repo.TransactionLogRepo;
import com.ftk.pg.repo.UpiQrDetailRepo;
import com.ftk.pg.repo.UserRepo;
import com.ftk.pg.requestvo.CustomerDetailsRequest;
import com.ftk.pg.requestvo.UserRequest;
import com.ftk.pg.responsevo.UserResponse;
import com.ftk.pg.util.EncryptionUtil;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	Logger logger = LogManager.getLogger(PgService.class);

	
	 private final PropertiesService propertiesService;

	Gson gson = new Gson();


	public final  UserRepo userRepo;

	
	public final UpiQrDetailRepo upiQrDetailRepo;

	
	private final MerchantKeysRepo merchantKeysRepo;

	public final VPAQrCodeRepo VPAQrCodeRepository;

	
	public final TransactionLogRepo transactionLogRepo;

	public ResponseEntity<ResponseWrapper<UserResponse>> fetchUserName(UserRequest userRequest,
			HttpServletRequest request) throws Exception {
		try {
			logger.info("Processing fetchUserName request for user: {}", userRequest.getUserName());

			ResponseWrapper<UserResponse> response = new ResponseWrapper<>();
			User user = userRepo.findByUsername(userRequest.getUserName());
			if (user == null) {
				logger.warn("User not found for username: {}", userRequest.getUserName());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}

			UpiQrDetail upiQrDetail = upiQrDetailRepo.findByMid(user.getMid());
			if (upiQrDetail == null) {
				logger.warn("UPI QR details not found for user with mid: {}", user.getMid());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}

			VPAQrCode VPAQrCode = VPAQrCodeRepository.findByMidAndVpa(upiQrDetail.getMid(), upiQrDetail.getVpa());
			if (VPAQrCode == null || VPAQrCode.getId() == null || VPAQrCode.getId() <= 0) {
				logger.warn("VPA QR Code not found or invalid for mid: {} and vpa: {}", upiQrDetail.getMid(),
						upiQrDetail.getVpa());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			MerchantKeys merchantKeys = merchantKeysRepo.findByMidAndTerminalId(VPAQrCode.getMid(), VPAQrCode.getVpa());
			if (merchantKeys == null || merchantKeys.getId() == null || merchantKeys.getId() <= 0) {
				response.setMessage("Encryption keys are not configured for the mid in request");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			UserResponse userResponse = new UserResponse();
			userResponse.setIv(merchantKeys.getIv());
			userResponse.setKey(merchantKeys.getKey());
			response.setData(userResponse);

			response.setMessage("UserName Fetch successfully");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("Exception while fetching user details for username: {}", userRequest.getUserName(), e);
			ResponseWrapper<UserResponse> errorResponse = new ResponseWrapper<>();
			errorResponse.setMessage("Internal Server Error: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	public ResponseEntity<ResponseWrapper<String>> updateCustomerDetails(RequestWrapper requestWrapper,String token)
			throws Exception {
		try {
			MerchantKeys merchantKeys = merchantKeysRepo.findByMidAndTerminalId(Long.valueOf(requestWrapper.getMid()),
					requestWrapper.getTerminalId());
//			GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(merchantKeys.getIv(), merchantKeys.getKey());
//			CustomerDetailsRequest customerDetailsRequest = EncryptionUtil
//					.decryptdata(String.valueOf(requestWrapper.getData()), CustomerDetailsRequest.class);
			CustomerDetailsRequest customerDetailsRequest = EncryptionUtil.decryptdata(String.valueOf(requestWrapper.getData()),
					token, CustomerDetailsRequest.class);

			TransactionLog transactionDetails = transactionLogRepo
					.findByMerchantIdAndMerchanttxnid(merchantKeys.getMid(), customerDetailsRequest.getTxnId());
			if (customerDetailsRequest.getCustomerName() != null
					&& customerDetailsRequest.getCustomerName().equals("")) {
				String encodedCustomerName = Base64.getEncoder()
						.encodeToString(customerDetailsRequest.getCustomerName().getBytes(StandardCharsets.UTF_8));
				transactionDetails.setUdf1(encodedCustomerName);
			}
			if (customerDetailsRequest.getCustomerMobNo() != null
					&& customerDetailsRequest.getCustomerMobNo().equals("")) {
				transactionDetails.setUdf2(customerDetailsRequest.getCustomerMobNo());
			}
			if (customerDetailsRequest.getEmail() != null && customerDetailsRequest.getEmail().equals("")) {
				transactionDetails.setUdf3(customerDetailsRequest.getEmail());
			}
			transactionLogRepo.save(transactionDetails);

			ResponseWrapper<String> response = new ResponseWrapper<>();
			response.setMessage("Customer details updated successfully");
			response.setData("Updated Successfully");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("Exception occurred while updating customer details", e);
			ResponseWrapper<String> errorResponse = new ResponseWrapper<>();
			errorResponse.setMessage("Internal Server Error: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

}

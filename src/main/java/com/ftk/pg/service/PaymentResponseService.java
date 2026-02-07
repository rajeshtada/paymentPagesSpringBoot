package com.ftk.pg.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftk.pg.pi.repo.MerchantKeysRepo;
import com.ftk.pg.repo.BankRepo;
import com.ftk.pg.repo.MerchantPartnerRepo;
import com.ftk.pg.repo.MerchantProductIdDetailsRepo;
import com.ftk.pg.repo.MerchantRepo;
import com.ftk.pg.repo.MerchantSettingRepo;
import com.ftk.pg.repo.ProcessorBankRepo;
import com.ftk.pg.repo.TransactionLogRepo;
import com.ftk.pg.repo.UIColorSchemeRepo;
import com.ftk.pg.repo.UpiQrDetailRepo;
import com.ftk.pg.repo.UserRepo;
import com.ftk.pg.repo.WalletRepo;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class PaymentResponseService {
	static Logger logger = LogManager.getLogger(PaymentProcessService.class);

//	private Logger logger = LoggerFactory.getLogger(PaymentResponseService.class);

//	@Autowired
//	private MerchantKeysRepo merchantKeysRepo;


	
	private final  ApiService apiService;

	
	private final MerchantRepo merchantRepo;

	
	private final UpiQrDetailRepo upiQrDetailRepo;

	
	private  final UIColorSchemeRepo uiColorSchemeRepo;

	
	private  final MerchantPartnerRepo merchantPartnerRepo;

	
	private final TransactionLogRepo transactionLogRepo;

	
	private  final EncryptionService encryptionService;

	
	private final MerchantProductIdDetailsRepo merchantProductIdDetailsRepo;

	
	private final UserRepo userRepo;

	
	private final BankRepo bankRepo;

	
	private final MerchantSettingRepo merchantSettingRepo;

	
	private final ProcessorBankRepo processorBankRepo;

	
	private final WalletRepo walletRepo;

//	@Autowired
//	private InvoiceRepo invoiceRepo;
//
//	@Autowired
//	private IntermediateTransactionRepo intermediateTransactionRepo;
//
//	@Autowired
//	private AdvancePropertiesRepo advancePropertiesRepo;

	
	private  final ValidationService validationService;

//	@Autowired
//	private MerchantCommisionService merchantCommisionService;

	
	private final  MerchantKeysRepo merchantKeysRepo;

//	public ResponseEntity<ResponseWrapper<String>> pgPayment(RequestWrapper requestWrapper) throws Exception {
//		ResponseEntity<ResponseWrapper<String>> response = null;
//		MerchantKeys merchantKeys = merchantKeysRepo.findByMidAndTerminalId(Long.parseLong(requestWrapper.getMid()),
//				requestWrapper.getTerminalId());
//		TokenRequest tokenRequest = EncryptionUtil.decryptdata(String.valueOf(requestWrapper.getData()), merchantKeys,
//				TokenRequest.class);
//	
//		
//		
//		ResponseEntity<ResponseWrapper<String>> payByVpaV2 = paymentValidationService.payByVpaV2(api_paymentRequest,
//				merchantKeys);
//		return payByVpaV2;
//	}

}

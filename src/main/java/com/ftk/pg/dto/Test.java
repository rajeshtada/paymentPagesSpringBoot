package com.ftk.pg.dto;

public class Test {

//	@RequestMapping(value = "/api/payByVpa", method = RequestMethod.POST)
//	public String payByVpa(@Valid @ModelAttribute API_PaymentRequest pgRequest, BindingResult result, Model model,
//			HttpSession session, HttpServletRequest request) {
//		session.invalidate();
//		HttpSession newSession = request.getSession(true);
//		logger.info("Request Generated Params v2: " + pgRequest);
//		String vpa = "";
//		Merchant merchant = new Merchant();
//		String signatureKey = "";
//		List<Bank> merchantBanks = new ArrayList<Bank>();
//		PaymentResponse pgresponse = new PaymentResponse();
//		Map<String, String> propMap = new HashMap<>();
//		List<Properties> PropertiesList = (List<Properties>) propertiesDao.findAll(Properties.class);
//		for (Properties property : PropertiesList) {
//			propMap.put(property.getPropertykey(), property.getPropertyValue());
//		}
//		logger.info("API Request Generated Params: " + pgRequest + " Timing ==>" + LocalDate.now());
//		if (pgRequest.getUdf6() != null) {
//			pgRequest.setUdf6(pgRequest.getUdf6().replaceAll(" ", "+"));
//		}
//		logger.info("API Request Generated Params: " + pgRequest + " Timing ==>" + LocalDate.now());
//		if (result.hasErrors()) {
//			FieldError error = result.getFieldError();
//			pgresponse.setDescription(error.getDefaultMessage());
//		} else {
//			try {
//				vpa = pgRequest.getMid();
//				User userDetails = apiService.getUserDetailByVpaId(pgRequest.getMid());
//				if (userDetails == null) {
//					logger.info("Invalid VPA");
//					pgresponse.setDescription("Invalid VPA");
//				} else {
//
//					merchant = apiService.findById(userDetails.getMid());
//					MerchantPartner merchantPartner = new MerchantPartner();
//					UIColorScheme uiColorScheme = new UIColorScheme();
//
//					// find by vpa color code
//					String encyptedlogoPath = null;
//
//					UpiQrDetail qrDetailgenre = apiService.getUpiQrDetailByVpa(vpa);
//
//					if (vpa != null && !vpa.equals("")) {
//						uiColorScheme = uiColorSchemeDao.findByVpa(vpa);
//					}
//					if (uiColorScheme == null || uiColorScheme.getId() <= 0) {
//						merchantPartner = merchantPartnerDao.findByMId(merchant.getMid());
//						logger.info("Merchant partner =========================>" + merchantPartner);
//						if (merchantPartner != null) {
//							uiColorScheme = uiColorSchemeDao.findByPartnerId(merchantPartner.getPartnerId());
//						}
//					}
//					if (uiColorScheme != null) {
//						logger.info("UI color Scheme =========================>" + uiColorScheme);
//						if (uiColorScheme.getLogoPath() != null && !uiColorScheme.getLogoPath().equals("")) {
//							encyptedlogoPath = AesEncryption.encrypt(uiColorScheme.getLogoPath());
//							logger.info("Encrypted logoPath==============================>" + encyptedlogoPath);
//						}
//					}
//
////					merchantPartner = merchantPartnerDao.findByMId(merchant.getMid());
////					logger.info("Merchant partner =========================>" + merchantPartner);
////					String encyptedlogoPath = null;
////					if (merchantPartner != null) {
////						uiColorScheme = uiColorSchemeDao.findByPartnerId(merchantPartner.getPartnerId());
////						if (uiColorScheme != null) {
////							logger.info("UI color Scheme =========================>" + uiColorScheme);
////							if (uiColorScheme.getLogoPath() != null && !uiColorScheme.getLogoPath().equals("")) {
////								encyptedlogoPath = AesEncryption.encrypt(uiColorScheme.getLogoPath());
////								logger.info("Encrypted logoPath==============================>" + encyptedlogoPath);
////							}
////						}
////					}
//
//					String merchantemailslist = propMap.get(Utilities.EMAIL_MERCHANTS);
//					if (merchantemailslist != null && !merchantemailslist.equalsIgnoreCase("")) {
//						String[] midArrays = merchantemailslist.split(",");
//						boolean isMidsPresent = false;
//						String mercId = String.valueOf(merchant.getMid());
//						for (String mid : midArrays) {
//							if (mid.equalsIgnoreCase(mercId)) {
//								isMidsPresent = true;
//								break;
//							}
//						}
//						if (isMidsPresent) {
//							newSession.setAttribute("merchantemail", merchant.getEmail());
//						} else {
//							newSession.setAttribute("merchantemail", null);
//						}
//					} else {
//						newSession.setAttribute("merchantemail", null);
//					}
//
//					String signature = SignatureGenerator
//							.signatureGeneration(
//									new String[] { pgRequest.getCurrency(), pgRequest.getLogin(),
//											pgRequest.getMerchantOrderNo(), pgRequest.getMid(),
//											pgRequest.getPaymentMode(), pgRequest.getProductType(),
//											pgRequest.getTxnAmount(), pgRequest.getUdf1(), pgRequest.getUdf2() },
//									pgRequest.getMid());
//
//					if (signature.equals(pgRequest.getSignature())) {
//						pgRequest.setMid(userDetails.getMid().toString());
//						pgRequest.setLogin(userDetails.getUsername());
//						String pgSignature = SignatureGenerator
//								.signatureGeneration(
//										new String[] { pgRequest.getCurrency(), pgRequest.getLogin(),
//												pgRequest.getMerchantOrderNo(), pgRequest.getMid(),
//												pgRequest.getPaymentMode(), pgRequest.getProductType(),
//												pgRequest.getTxnAmount(), pgRequest.getUdf1(), pgRequest.getUdf2() },
//										userDetails.getPassword());
//
//						pgRequest.setSignature(pgSignature);
//						Long mid = userDetails.getMid();
//
//						PaymentRequest paymentRequest = new PaymentRequest();
//						BeanUtils.copyProperties(pgRequest, paymentRequest);
//						paymentRequest.setAmt(pgRequest.getTxnAmount());
//						paymentRequest.setLogin(pgRequest.getLogin());
//						paymentRequest.setDate(pgRequest.getTxndatetime());
//						paymentRequest.setMerchantTxnId(pgRequest.getMerchantOrderNo());
//						paymentRequest.setPaymentMode(pgRequest.getPaymentMode());
//						paymentRequest.setProductType(pgRequest.getProductType());
//						paymentRequest.setRu(pgRequest.getRu());
//						paymentRequest.setTxncurr(pgRequest.getCurrency());
//						paymentRequest.setUdf1(pgRequest.getUdf1());
//						paymentRequest.setUdf2(pgRequest.getUdf2());
//						paymentRequest.setUdf3(pgRequest.getUdf3());
//						paymentRequest.setUdf4(pgRequest.getUdf4());
//						paymentRequest.setUdf5(pgRequest.getUdf5());
//						paymentRequest.setBankid(pgRequest.getBankId());
//
//						if (paymentRequest.getUdf5() != null && paymentRequest.getPaymentMode() != null
//								&& paymentRequest.getPaymentMode().contains("NEFT")) {
//							if (paymentRequest.getUdf5().contains("|")) {
//								String[] pr = paymentRequest.getUdf5().split("|");
//								if (pr != null && pr.length > 0) {
//									paymentRequest.setVan(pr[0]);
//								}
//							}
//						}
//
//						if (pgRequest.getTxnType() != null && pgRequest.getTxnType().equalsIgnoreCase("multi")) {
//							paymentRequest.setProductDetails(
//									new String(Base64.getDecoder().decode(pgRequest.getUdf6().replaceAll(" ", "+"))));
//						}
//
//						pgresponse = apiService.checkDetails(paymentRequest);
//
//						ProcessorIdAndPortalIdRequest processorIdAndPortalIdRequest = new ProcessorIdAndPortalIdRequest();
//						processorIdAndPortalIdRequest.setTransactionId(pgresponse.getMerchantTxnId());
//						processorIdAndPortalIdRequest.setProcessorId(String.valueOf(pgresponse.getTransactionId()));
//
//						callService.addprocessorIdInQueue(processorIdAndPortalIdRequest);
//
//						// multipart********
//						MerchantProductIdDetails merchantproductDetails = new MerchantProductIdDetails();
//
//						if (pgRequest != null && pgRequest.getTxnType().equalsIgnoreCase("multi")) {
//							RequestProductsVo products = parseRequestProducts(paymentRequest.getProductDetails());
//							logger.info("Inside Multi Condition==============================>");
//							logger.info("product Size ==============================>" + products.getProducts().size());
//							if (products.getProducts() != null && products.getProducts().size() == 1) {
//								String multiproductmids = propMap.get(Utilities.MULTIPRODUCTMIDS);
//								if (multiproductmids != null && !multiproductmids.equalsIgnoreCase("")) {
//									logger.info("multiproductmids Condition==============================>"
//											+ multiproductmids);
//									String[] midArrays = multiproductmids.split(",");
//									boolean isMidsPresent = false;
//									String mercId = String.valueOf(merchant.getMid());
//									for (String merchantId : midArrays) {
//										if (merchantId.equalsIgnoreCase(mercId)) {
//											isMidsPresent = true;
//											break;
//										} else {
//											isMidsPresent = false;
//										}
//									}
//									if (isMidsPresent) {
//										logger.info("Is Mids Present is true====>>>>>========>>>========>");
//
//										merchantproductDetails = merchantProductDetailsDao
//												.findMerchantProductByMidAndProductCode(merchant.getMid(),
//														products.getProducts().get(0).getCode());
//
//										logger.info("Merchant Product Detials===============>"
//												+ merchantproductDetails.getId());
//										if (merchantproductDetails != null) {
//											newSession.setAttribute("merchantproductDetails", merchantproductDetails);
//										}
//									}
//								}
//							} else {
//								newSession.setAttribute("merchantproductDetails", merchantproductDetails);
//							}
//						}
//						if (pgresponse != null
//								&& pgresponse.getDescription().equalsIgnoreCase("Merchant Txn Id already Exist")
//								&& pgresponse.getResponseCode().equalsIgnoreCase("01")) {
//							model.addAttribute("error", pgresponse);
//							return "error2";
//						}
//
//						logger.info("API Response Params: " + pgresponse);
//						if (pgresponse.isResult()) {
//
//							// return to payment page
//							merchant = apiService.findById(pgresponse.getmId());
//
//							User user = new User();
//							user.setUsername(pgRequest.getLogin());
//							user = apiService.findByUsername(user);
//							signatureKey = SignatureGenerator.signatureGeneration(
//									new String[] { pgRequest.getCurrency(), pgRequest.getLogin(),
//											pgRequest.getMerchantOrderNo(), pgRequest.getMid(),
//											pgRequest.getPaymentMode(), pgRequest.getProductType(),
//											pgRequest.getTxnAmount(), pgRequest.getUdf1(), pgRequest.getUdf2() },
//									user.getPassword());
//
//							/*
//							 * signatureKey = SignatureGenerator.signatureGeneration( new String[] {
//							 * pgRequest.getTxnAmount(), pgRequest.getLogin(),
//							 * pgRequest.getMerchantOrderNo(), pgRequest.getPaymentMode(),
//							 * pgRequest.getProductType(), pgRequest.getCurrency() },
//							 * merchant.getMerchantPrivateKey());
//							 */
//							logger.info("Signature key generated=>" + signatureKey + "::" + pgRequest.getSignature());
//							if (signatureKey.equals(pgRequest.getSignature())) {
//								String enabledPayModes = "";
//								if (merchant != null) {
//									enabledPayModes = pgRequest.getPaymentMode();
//								}
//								logger.info("Enabled Payment Modes=>" + enabledPayModes);
//								CommissionModel commissionModel = new CommissionModel();
//								if (enabledPayModes == null || enabledPayModes.equals("")
//										|| enabledPayModes.equals("ALL")) {
//									enabledPayModes = apiService.getEnabledPaymode(pgRequest.getLogin(),
//											pgRequest.getProductType());
//									commissionModel.setZeroCharges(new BigDecimal(pgRequest.getTxnAmount()));
//									merchantBanks = apiService.findAll(Bank.class);
//								} else {
//
//									String filteredPaymentModes = "";
//
//									String[] requestPaymentModeArray = enabledPayModes.split(",");
//
//									String configuredPaymentModes = apiService.getEnabledPaymode(pgRequest.getLogin(),
//											pgRequest.getProductType());
//									String[] configuredPaymentModesArray = configuredPaymentModes.split(",");
//									for (int i = 0; i < requestPaymentModeArray.length; i++) {
//										String rpm = requestPaymentModeArray[i];
//										for (int j = 0; j < configuredPaymentModesArray.length; j++) {
//											String pm = configuredPaymentModesArray[j];
//											if (rpm.equals(pm)) {
//												if (filteredPaymentModes != null && !filteredPaymentModes.equals("")) {
//													filteredPaymentModes = filteredPaymentModes + ",";
//												}
//												filteredPaymentModes = filteredPaymentModes + pm;
//											}
//										}
//									}
//									enabledPayModes = filteredPaymentModes;
//									commissionModel.setZeroCharges(new BigDecimal(pgRequest.getTxnAmount()));
//									/*
//									 * switch (enabledPayModes) { case "UPI": commissionModel =
//									 * merchantCommisionService.getCommisionAmountModel(pgresponse.getmId(), "upi",
//									 * Double.parseDouble(pgRequest.getTxnAmount()), pgRequest.getProductType(),
//									 * null); pgRequest.setTxnAmount( commissionModel.getTotal().setScale(2,
//									 * BigDecimal.ROUND_HALF_UP).toString()); break; case "CC": commissionModel =
//									 * merchantCommisionService.getCommisionAmountModel(pgresponse.getmId(), "CC",
//									 * Double.parseDouble(pgRequest.getTxnAmount()), pgRequest.getProductType(),
//									 * null); pgRequest.setTxnAmount( commissionModel.getTotal().setScale(2,
//									 * BigDecimal.ROUND_HALF_UP).toString()); break; case "NEFT": commissionModel =
//									 * merchantCommisionService.getCommisionAmountModel(pgresponse.getmId(), "NEFT",
//									 * Double.parseDouble(pgRequest.getTxnAmount()), pgRequest.getProductType(),
//									 * null); pgRequest.setTxnAmount( commissionModel.getTotal().setScale(2,
//									 * BigDecimal.ROUND_HALF_UP).toString()); break; default:
//									 * commissionModel.setZeroCharges(new BigDecimal(pgRequest.getTxnAmount()));
//									 * break; }
//									 */
//
//								}
//								MerchantSetting setting = new MerchantSetting();
//
//								if (enabledPayModes.toUpperCase().contains("NB")) {
//
//									if (propMap.get(Utilities.ENABLE_BANK_LIST).equalsIgnoreCase("true")) {
//
//										setting = merchanatCommisionDao.findByMidandPaymentModeandProcessor(
//												merchant.getMid(), "NB", "PAYNETZ");
//
//										if (setting != null) {
//											List<ProcessorBank> processorBankList = processorBankDao
//													.findByProcessorAndMid(setting);
//
//											if (processorBankList.size() > 0) {
//												Long[] bankIdArray = processorBankList.stream()
//														.map(ProcessorBank::getBankId).toArray(Long[]::new);
//
//												logger.info("bank Array Id=======================================>"
//														+ bankIdArray);
//
//												merchantBanks = bankDao.findByBankId(bankIdArray);
//
//												logger.info("Merchant Banks lists================================>"
//														+ merchantBanks);
//											}
//
//											else {
//												merchantBanks = apiService.findAll(Bank.class);
//											}
//
//										} else {
//											merchantBanks = apiService.findAll(Bank.class);
//										}
//
//									}
//
//									else {
//										merchantBanks = apiService.findAll(Bank.class);
//									}
//
//									// merchantBanks = apiService.findAll(Bank.class);
//
////									
//								}
//
//								List<Wallet> merchantWallets = new ArrayList<>();
//								if (enabledPayModes.toUpperCase().contains("WALLET")) {
//									merchantWallets = apiService.getWalletList();
//								}
//
//								pgRequest.setTransactionId(pgresponse.getTransactionId());
//								paymentRequest.setTransactionId(pgresponse.getTransactionId());
//								logger.info("vpa=>" + vpa);
//								if (enabledPayModes.toUpperCase().contains("DYNAMICQR")
//										|| enabledPayModes.toUpperCase().contains("UPI")) {
//									// merchantBanks = apiService.findAll(Bank.class);
////									logger.info("pgresponse =>" + pgresponse);
////									logger.info("paymentRequest =>" + paymentRequest);
//									UpiQrDetail qrDetail = apiService.getUpiQrDetailByVpa(vpa);
//									if (qrDetail != null && !qrDetail.equals("")) {
//										if (qrDetail.getVpa() != null && !qrDetail.getVpa().equals("")) {
//
//											String qr = qrDetail.getVpa();
//											CommissionModel cmodel = merchantCommisionService.getCommisionAmountModel(
//													mid, "DYNAMICQR", Double.parseDouble(pgRequest.getTxnAmount()),
//													"IPG", "");
//
//											ConvenienceModel conmodel = convenienceChargesService
//													.getConvenienceChargesModel(merchant.getMid(), "DYNAMICQR",
//															Double.parseDouble(pgRequest.getTxnAmount()), null);
//
//											DynamicQrModel qrModel = apiService.getDynamicQr(merchant, cmodel, conmodel,
//
//													paymentRequest, qr);
//
//											if (qrModel != null) {
//												String qrImage = qrModel.getQrImage();
//												String qrstring = qrModel.getQrString();
////												model.addAttribute("qrpath",
////														Base64.getEncoder().encodeToString(qrImage.getBytes("UTF-8")));
////												model.addAttribute("qrstring", qrstring);
//
//												newSession.setAttribute("qrpath",
//														Base64.getEncoder().encodeToString(qrImage.getBytes("UTF-8")));
//												newSession.setAttribute("qrstring", qrstring);
//
//											} else {
//												enabledPayModes = enabledPayModes.replace("DYNAMICQR", "").replace(",,",
//														",");
//											}
//
//										} else {
//											String qrImage = null;
//											String qrstring = null;
//											newSession.setAttribute("qrpath", qrImage);
//											newSession.setAttribute("qrstring", qrstring);
//											enabledPayModes = enabledPayModes.replace("DYNAMICQR", "").replace(",,",
//													",");
//										}
//									} else {
//										enabledPayModes = enabledPayModes.replace("DYNAMICQR", "").replace(",,", ",");
//									}
//								}
//
////								model.addAttribute("requestParams", paymentRequest);
////								model.addAttribute("commissionModel", commissionModel);
////								model.addAttribute("enabledPayModes", enabledPayModes.split(","));
////								model.addAttribute("merchant", merchant);
////								model.addAttribute("months", Utilities.getMonths());
////								model.addAttribute("years", Utilities.getYears());
////								model.addAttribute("banks", merchantBanks);
//
//								newSession.setAttribute("requestParams", paymentRequest);
//								newSession.setAttribute("commissionModel", commissionModel);
//								newSession.setAttribute("enabledPayModes", enabledPayModes);
//								newSession.setAttribute("merchant", merchant);
//								newSession.setAttribute("months", Utilities.getMonths());
//								newSession.setAttribute("years", Utilities.getYears());
//								newSession.setAttribute("banks", merchantBanks);
//								newSession.setAttribute("wallets", merchantWallets);
//								newSession.setAttribute("upiQrDetails", qrDetailgenre);
//
//								if (uiColorScheme != null) {
//									if (encyptedlogoPath != null) {
//										uiColorScheme.setLogoPath(encyptedlogoPath);
//									} else {
//										uiColorScheme.setLogoPath(null);
//									}
//
////									model.addAttribute("uiColorScheme", uiColorScheme);
//									newSession.setAttribute("uiColorScheme", uiColorScheme);
//
//								}
////								return "paypage";
//								String encryptedData = AesEncryption
//										.encrypt(String.valueOf(pgRequest.getTransactionId()));
//								return "redirect:/paynowv2/" + encryptedData;
//							} else {
//								pgresponse.setDescription("Signature Mismatch !!");
//								model.addAttribute("response", pgresponse);
//							}
//
//						} else {
//							model.addAttribute("response", pgresponse);
//						}
//					}
//				}
//			} catch (Exception e) {
//				new GlobalExceptionHandler().customException(e);
//			}
//
//		}
//		logger.info("FAILED  Request : " + pgRequest + " Timing ==>" + LocalDate.now());
//		logger.info("FAILED  Request : " + pgRequest + " Timing ==>" + LocalDate.now());
//		if (pgRequest.getRu() != null && (!pgRequest.getRu().trim().equals(""))) {
//			pgresponse.setRu(pgRequest.getRu());
//		} else {
//			pgresponse.setRu("");
//		}
//		if (merchant != null && merchant.getMid() != null && merchant.getMid() > 0) {
//			pgresponse.setmId(merchant.getMid());
//		} else {
//			pgresponse.setmId(0l);
//		}
//		pgresponse.setResponseCode("01");
//		pgresponse.setStatus("FAILED");
//		pgresponse.setMerchantTxnId(pgRequest.getMerchantOrderNo());
//		pgresponse.setAmt(pgRequest.getTxnAmount());
//
//		signatureKey = SignatureGenerator.signatureGeneration(new String[] { pgRequest.getLogin(),
//				pgRequest.getMerchantOrderNo(), pgRequest.getTxnAmount(), pgresponse.getStatus() },
//				pgRequest.getMerchantOrderNo());
//		pgresponse.setSignature(signatureKey);
//		logger.info("FAILED  RESPONSE : " + pgresponse + " Timing ==>" + LocalDate.now());
//		logger.info("FAILED  RESPONSE : " + pgresponse + " Timing ==>" + LocalDate.now());
//		model.addAttribute("response", pgresponse);
//		return "process";
//	}

}

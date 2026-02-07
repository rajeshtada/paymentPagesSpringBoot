package com.ftk.pg.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ftk.pg.dto.ChargesDto;
import com.ftk.pg.dto.RequestWrapper;
import com.ftk.pg.dto.ResponseBuilder;
import com.ftk.pg.dto.ResponseWrapper;
import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.CardBean;
import com.ftk.pg.modal.MerchantCommision;
import com.ftk.pg.pi.repo.MerchantKeysRepo;
import com.ftk.pg.repo.CardBeanRepo;
import com.ftk.pg.requestvo.PropertiesVo;
import com.ftk.pg.util.CommissionModel;
import com.ftk.pg.util.ConvenienceModel;
import com.ftk.pg.util.EncryptionUtil;
import com.ftk.pg.util.Utilities;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChargesService {

	static Logger logger = LogManager.getLogger(ChargesService.class);

	private final MerchantKeysRepo merchantKeysRepo;

	private final CardBeanRepo cardBeanRepo;

	private final ConvenienceChragesService convenienceChargesService;

	private final MerchantCommisionService merchantCommisionService;

	private final EncryptionService encryptionService;

	private final CardBeanService cardBeanService;

	private final PropertiesService propertiesService;

	public ResponseEntity<ResponseWrapper<String>> generateCharges(RequestWrapper requestWrapper, String token)
			throws Exception {
//		ResponseEntity<ResponseWrapper<String>> response = null;
		CommissionModel model = new CommissionModel();
		ConvenienceModel conveniencemodel = new ConvenienceModel();
		String subType = null;
		String conSubType = null;
		CardBean cardBean = null;

		try {
			ChargesDto chargesDto = EncryptionUtil.decryptdata(String.valueOf(requestWrapper.getData()), token,
					ChargesDto.class);
			logger.info("generateCharges dto ====>" + chargesDto);
			long mid = chargesDto.getMid();
			String cardNo = chargesDto.getCardNo();
			String paymentMode = chargesDto.getPaymentMode();
			String amount = chargesDto.getAmount();
			String bankId = chargesDto.getBankId();
			model.setPaymentMode(paymentMode);

			if (paymentMode != null && (paymentMode.equalsIgnoreCase("DC") || paymentMode.equalsIgnoreCase("CC"))) {
				if (cardNo != null && !cardNo.equals("")) {
//					cardBean = cardBeanService.getBeanDetailByValue(Integer.valueOf(cardNo));
					List<CardBean> beanDetailsByValue = cardBeanService.getBeanDetailsByValue(Integer.valueOf(cardNo));
					cardBean = beanDetailsByValue.get(0);
					if (cardBean != null && cardBean.getId() != null && cardBean.getId() > 0) {
						subType = cardBean.getCardType();
						String typeOfCard = cardBean.getTypeOfCard();
						if (cardBean.getCardType().equals("MASTERCARD")) {
							String cardTypeMaster = "MASTER";

							conSubType = cardTypeMaster + "_" + cardBean.getDomesticInternational();
							if (conSubType.equalsIgnoreCase("MASTER_I") || conSubType.equalsIgnoreCase("MASTER_C")) {
								subType = conSubType;
							}
						} else {
							conSubType = cardBean.getCardType() + "_" + cardBean.getDomesticInternational();
							if (conSubType.equalsIgnoreCase("VISA_I") || conSubType.equalsIgnoreCase("VISA_C")) {
								subType = conSubType;
							}
						}

						logger.info("Con SUB Type====>" + conSubType);
						if (paymentMode.equalsIgnoreCase("DC") && typeOfCard.equalsIgnoreCase("D")) {
							if (subType.equalsIgnoreCase("RUPAY")) {
								model.setAmountStr(amount);
								model.setChargesStr("0");
								model.setTotalStr(amount);
								try {
									conveniencemodel = convenienceChargesService.getConvenienceChargesModel(mid,
											paymentMode, Double.parseDouble(amount), conSubType);
									BigDecimal conBigDecimal = conveniencemodel.getCharges();
									model.setConvenience(conBigDecimal);
									model.setTotal(
											new BigDecimal(model.getTotalStr()).add(conveniencemodel.getCharges()));
								} catch (Exception e) {
									// TODO Auto-generated catch block
									new GlobalExceptionHandler().customException(e);
								}
								return ResponseBuilder.buildResponse(encryptionService.encryptResponse(model, token),
										"SUCCESS", HttpStatus.OK);
							} else if (subType.equalsIgnoreCase("VISA")) {
								subType = "VISA";
							} else if (subType.equalsIgnoreCase("MASTERCARD")) {
								subType = "MASTER";
							} else if (subType.equalsIgnoreCase("VISA_I")) {
								subType = "VISA_I";
							} else if (subType.equalsIgnoreCase("MASTER_I")) {
								subType = "MASTER_I";
							} else {
								model.setMessage("Invalid card details");
								return ResponseBuilder.buildResponse(encryptionService.encryptResponse(model, token),
										"SUCCESS", HttpStatus.OK);
							}
						} else if (paymentMode.equalsIgnoreCase("CC")) {
							if (typeOfCard.equalsIgnoreCase("C") || typeOfCard.equalsIgnoreCase("P")) {
								if (subType.equalsIgnoreCase("RUPAY")) {
									subType = "RUPAY";
								} else if (subType.equalsIgnoreCase("VISA")) {
									subType = "VISA";
								} else if (subType.equalsIgnoreCase("MASTERCARD")) {
									subType = "MASTER";
								} else if (subType.equalsIgnoreCase("VISA_I")) {
									subType = "VISA_I";
								} else if (subType.equalsIgnoreCase("MASTER_I")) {
									subType = "MASTER_I";
								} else {
									model.setMessage("Invalid card details");
									return ResponseBuilder.buildResponse(
											encryptionService.encryptResponse(model, token), "SUCCESS", HttpStatus.OK);
								}
							} else {
								model.setMessage("Invalid card details");
								return ResponseBuilder.buildResponse(encryptionService.encryptResponse(model, token),
										"SUCCESS", HttpStatus.OK);
							}
						} else {
							model.setMessage("Invalid card details");
							return ResponseBuilder.buildResponse(encryptionService.encryptResponse(model, token),
									"SUCCESS", HttpStatus.OK);
						}

					} else {
						if (cardNo.startsWith("4")) {
							subType = "VISA";
						} else {
							subType = "MASTER";
						}
					}
				} else {
					model.setAmountStr(amount);
					model.setChargesStr("0");
					model.setTotalStr(amount);
					model.setConvenienceStr("0");
//					return model;
					return ResponseBuilder.buildResponse(encryptionService.encryptResponse(model, token), "SUCCESS",
							HttpStatus.OK);
				}
			} else if (paymentMode != null && paymentMode.equalsIgnoreCase("NB")) {
				logger.info("Charges for NET banking ===========>");
				if (bankId == null || bankId.equals("")) {
					logger.info("If Bank Id is null=======>");
					model.setAmountStr(amount);
					model.setChargesStr("0");
					model.setTotalStr(amount);
					model.setConvenienceStr("0");
					return ResponseBuilder.buildResponse(encryptionService.encryptResponse(model, token), "SUCCESS",
							HttpStatus.OK);
				} else {
					subType = bankId;
					conSubType = bankId;
				}
			}
//			model = merchantCommisionService.getCommisionAmountModel(mid, paymentMode, Double.parseDouble(amount),
//					"IPG", subType);
			logger.info("generateCharges mid : " + mid + " paymentMode : " + paymentMode + " amount : " + amount
					+ " subType : " + subType + " conSubType : " + conSubType);

			model = merchantCommisionService.getCommisionAmountModel2(mid, paymentMode, Double.parseDouble(amount),
					"IPG", subType, conSubType);
			conveniencemodel = convenienceChargesService.getConvenienceChargesModel(mid, paymentMode,
					Double.parseDouble(amount), conSubType);
			BigDecimal conBigDecimal = conveniencemodel.getCharges();
			model.setConvenience(conBigDecimal);
			BigDecimal totalAmount = model.getTotal();
			totalAmount = totalAmount.add(conveniencemodel.getCharges());
			model.setTotal(totalAmount);
			model.setPaymentMode(paymentMode);

		} catch (Exception e) {
			logger.info("Card No. not valid");
			new GlobalExceptionHandler().customException(e);
		}
		return ResponseBuilder.buildResponse(encryptionService.encryptResponse(model, token), "SUCCESS", HttpStatus.OK);
	}

	public ResponseEntity<ResponseWrapper<String>> generateCharges2(RequestWrapper requestWrapper, String token)
			throws Exception {
//		ResponseEntity<ResponseWrapper<String>> response = null;
		CommissionModel model = new CommissionModel();
		ConvenienceModel conveniencemodel = new ConvenienceModel();
		String subType = null;
		String conSubType = null;
		CardBean cardBean = null;

		try {
			ChargesDto chargesDto = EncryptionUtil.decryptdata(String.valueOf(requestWrapper.getData()), token,
					ChargesDto.class);
			logger.info("generateCharges dto ====>" + chargesDto);
			long mid = chargesDto.getMid();
			String cardNo = chargesDto.getCardNo();
			String paymentMode = chargesDto.getPaymentMode();
			String amount = chargesDto.getAmount();
			String bankId = chargesDto.getBankId();
			model.setPaymentMode(paymentMode);

			if (paymentMode != null && (paymentMode.equalsIgnoreCase("DC") || paymentMode.equalsIgnoreCase("CC"))) {

				if (cardNo == null || cardNo.equals("")) {
					model.setAmountStr(amount);
					model.setChargesStr("0");
					model.setTotalStr(amount);
					model.setConvenienceStr("0");
					return ResponseBuilder.buildResponse(encryptionService.encryptResponse(model, token), "BAD_REQUEST",
							HttpStatus.BAD_REQUEST);
				}

				List<CardBean> beanDetailsByValue = cardBeanService.getBeanDetailsByValue(Integer.valueOf(cardNo));
				if (beanDetailsByValue == null || beanDetailsByValue.size() == 0) {
					if (cardNo.startsWith("4")) {
						subType = "VISA";
					} else {
						subType = "MASTER";
					}
				} else {
					cardBean = beanDetailsByValue.get(0);
					if (cardBean.getCardType().equals("MASTERCARD")) {
						cardBean.setCardType("MASTER");
					}
					subType = cardBean.getCardType();
					if (cardBean.getDomesticInternational().equalsIgnoreCase("I")
							|| cardBean.getDomesticInternational().equalsIgnoreCase("P")) {
						subType = subType + "_" + cardBean.getDomesticInternational();
					}

				}
			} else if (paymentMode != null && paymentMode.equalsIgnoreCase("NB")) {
				logger.info("Charges for NET banking ===========>");
				if (bankId == null || bankId.equals("")) {
					logger.info("If Bank Id is null=======>");
					model.setAmountStr(amount);
					model.setChargesStr("0");
					model.setTotalStr(amount);
					model.setConvenienceStr("0");
					return ResponseBuilder.buildResponse(encryptionService.encryptResponse(model, token), "BAD_REQUEST",
							HttpStatus.BAD_REQUEST);
				} else {
					subType = bankId;
					conSubType = bankId;
				}
			}
			
			Map<String, String> propMap = new HashMap<>();
			List<PropertiesVo> PropertiesList = propertiesService.findAllPropertyWithUpdatedCerts();
			for (PropertiesVo property : PropertiesList) {
				propMap.put(property.getPropertyKey(), property.getPropertyValue());
			}
			
			logger.info("generateCharges mid : " + mid + " paymentMode : " + paymentMode + " amount : " + amount
					+ " subType : " + subType + " conSubType : " + conSubType);

			MerchantCommision merchantCommisionData = merchantCommisionService.getMerchantCommisionData(mid,
					paymentMode, Double.parseDouble(amount), "IPG", subType, propMap);
			
			logger.info("merchantCommisionData : "+merchantCommisionData.getId());
			
			model = Utilities.getCommissionChargesModel(merchantCommisionData, Double.parseDouble(amount), propMap);
			
			conveniencemodel = convenienceChargesService.getConvenienceChargesModel(mid, paymentMode,
					Double.parseDouble(amount), subType);
			BigDecimal conBigDecimal = conveniencemodel.getCharges();
			model.setConvenience(conBigDecimal);
			BigDecimal totalAmount = model.getTotal();
			totalAmount = totalAmount.add(conveniencemodel.getCharges());
			model.setTotal(totalAmount);
			model.setPaymentMode(paymentMode);

		} catch (Exception e) {
			logger.info("Card No. not valid");
			new GlobalExceptionHandler().customException(e);
			return ResponseBuilder.buildResponse(encryptionService.encryptResponse(model, token), "INTERNAL_SERVER_ERROR",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseBuilder.buildResponse(encryptionService.encryptResponse(model, token), "SUCCESS", HttpStatus.OK);
	}

//	public ResponseEntity<ResponseWrapper<String>> generateCharges(RequestWrapper requestWrapper) throws Exception {
//		MerchantKeys merchantKeys = merchantKeysRepo.findByMidAndTerminalId(Long.valueOf(requestWrapper.getMid()),
//				requestWrapper.getTerminalId());
//		ChargesDto chargesDto = EncryptionUtil.decryptdata(requestWrapper.getData(), merchantKeys, ChargesDto.class);
//
//		CommissionModel model = new CommissionModel();
//		ConvenienceModel conveniencemodel = new ConvenienceModel();
//		String subType = null;
//		String ConSubType = null;
//		try {
//			CardBean cardBean = null;
//			if (chargesDto.getPaymentMode() != null && (chargesDto.getPaymentMode().equalsIgnoreCase("DC")
//					|| chargesDto.getPaymentMode().equalsIgnoreCase("CC"))) {
//				if (chargesDto.getCardNo() != null && !chargesDto.getCardNo().equals("")) {
//					cardBean = cardBeanRepo.getBeanDetailbyBeanValue(Integer.valueOf(chargesDto.getCardNo()));
////					cardBean = cardBeanService.getBeanDetailByValue(Integer.valueOf(chargesDto.getCardNo()));
//					// logger.info("cardbean => " + cardBean);
//					// subType = cardBean.getCardType();
//					if (cardBean != null && cardBean.getId() != null && cardBean.getId() > 0) {
//						subType = cardBean.getCardType();
//						String typeOfCard = cardBean.getTypeOfCatd();
//						if (cardBean.getCardType().equals("MASTERCARD")) {
//							String cardTypeMaster = "MASTER";
//
//							ConSubType = cardTypeMaster + "_" + cardBean.getDomesticInternational();
//							if (ConSubType.equalsIgnoreCase("VISA_I") || ConSubType.equalsIgnoreCase("MASTER_I")) {
//								subType = ConSubType;
//							}
//						} else {
//							ConSubType = cardBean.getCardType() + "_" + cardBean.getDomesticInternational();
//							if (ConSubType.equalsIgnoreCase("VISA_I") || ConSubType.equalsIgnoreCase("MASTER_I")) {
//								subType = ConSubType;
//							}
//						}
//
//						logger.info("Con SUB Type====>" + ConSubType);
//						if (chargesDto.getPaymentMode().equalsIgnoreCase("DC") && typeOfCard.equalsIgnoreCase("D")) {
//							if (subType.equalsIgnoreCase("RUPAY")) {
//								model.setAmountStr(chargesDto.getAmount());
//								model.setChargesStr("0");
//								model.setTotalStr(chargesDto.getAmount());
//								try {
//									conveniencemodel = convenienceChargesService.getConvenienceChargesModel(
//											Long.valueOf(requestWrapper.getMid()), chargesDto.getPaymentMode(),
//											Double.parseDouble(chargesDto.getAmount()), ConSubType);
//									BigDecimal conBigDecimal = conveniencemodel.getCharges();
//									model.setConvenience(conBigDecimal);
//									model.setTotal(
//											new BigDecimal(model.getTotalStr()).add(conveniencemodel.getCharges()));
//								} catch (Exception e) {
//									// TODO Auto-generated catch block
//									new GlobalExceptionHandler().customException(e);
//								}
//								EncryptedResponse encryptdata = EncryptionUtil.encryptdata(model.toString(),
//										merchantKeys);
//								ResponseWrapper<String> responseWrapper = new ResponseWrapper<>("SUCCESS",
//										encryptdata.toString());
//
//								return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
//							} else if (subType.equalsIgnoreCase("VISA")) {
//								subType = "VISA";
//							} else if (subType.equalsIgnoreCase("MASTERCARD")) {
//								subType = "MASTER";
//							} else if (subType.equalsIgnoreCase("VISA_I")) {
//								subType = "VISA_I";
//							} else if (subType.equalsIgnoreCase("MASTER_I")) {
//								subType = "MASTER_I";
//							} else {
//								model.setMessage("Invalid card details");
//								EncryptedResponse encryptdata = EncryptionUtil.encryptdata(model.toString(),
//										merchantKeys);
//								ResponseWrapper<String> responseWrapper = new ResponseWrapper<>("SUCCESS",
//										encryptdata.toString());
//
//								return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
//							}
//						} else if (chargesDto.getPaymentMode().equalsIgnoreCase("CC")) {
//							if (typeOfCard.equalsIgnoreCase("C") || typeOfCard.equalsIgnoreCase("P")) {
//								if (subType.equalsIgnoreCase("RUPAY")) {
//									subType = "RUPAY";
//								} else if (subType.equalsIgnoreCase("VISA")) {
//									subType = "VISA";
//								} else if (subType.equalsIgnoreCase("MASTERCARD")) {
//									subType = "MASTER";
//								} else if (subType.equalsIgnoreCase("VISA_I")) {
//									subType = "VISA_I";
//								} else if (subType.equalsIgnoreCase("MASTER_I")) {
//									subType = "MASTER_I";
//								} else {
//									model.setMessage("Invalid card details");
//									EncryptedResponse encryptdata = EncryptionUtil.encryptdata(model.toString(),
//											merchantKeys);
//									ResponseWrapper<String> responseWrapper = new ResponseWrapper<>("SUCCESS",
//											encryptdata.toString());
//
//									return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
//								}
//							} else {
//								model.setMessage("Invalid card details");
//								EncryptedResponse encryptdata = EncryptionUtil.encryptdata(model.toString(),
//										merchantKeys);
//								ResponseWrapper<String> responseWrapper = new ResponseWrapper<>("SUCCESS",
//										encryptdata.toString());
//
//								return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
//							}
//						} else {
//							model.setMessage("Invalid card details");
//							EncryptedResponse encryptdata = EncryptionUtil.encryptdata(model.toString(), merchantKeys);
//							ResponseWrapper<String> responseWrapper = new ResponseWrapper<>("SUCCESS",
//									encryptdata.toString());
//
//							return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
//						}
//
////						if (paymentMode.equalsIgnoreCase(cardBean.getTypeOfCatd() + "C")) {
////							if (subType.equalsIgnoreCase("RUPAY")) {
////								if (cardBean.getTypeOfCatd().equalsIgnoreCase("D")) {
////									model.setAmountStr(amount);
////									model.setChargesStr("0");
////									model.setTotalStr(amount);
////									return model;
////								} else if (cardBean.getTypeOfCatd().equalsIgnoreCase("C")) {
////									subType = "RUPAY";
////								}
////							} else if (subType.equalsIgnoreCase("VISA")) {
////								subType = "VISA";
////							} else if (subType.equalsIgnoreCase("MASTERCARD")) {
////								subType = "MASTER";
////							}
////						} else {
////							model.setMessage("Invalid card details");
////							return model;
////						}
//					} else {
//						if (chargesDto.getCardNo().startsWith("4")) {
//							subType = "VISA";
//						} else {
//							subType = "MASTER";
//						}
//					}
//				} else {
//					model.setAmountStr(chargesDto.getAmount());
//					model.setChargesStr("0");
//					model.setTotalStr(chargesDto.getAmount());
//					model.setConvenienceStr("0");
//					EncryptedResponse encryptdata = EncryptionUtil.encryptdata(model.toString(), merchantKeys);
//					ResponseWrapper<String> responseWrapper = new ResponseWrapper<>("SUCCESS", encryptdata.toString());
//
//					return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
//				}
//			} else if (chargesDto.getPaymentMode() != null && chargesDto.getPaymentMode().equalsIgnoreCase("NB")) {
//				logger.info("Charges for NET banking ===========>");
//				if (chargesDto.getBankId() == null || chargesDto.getBankId().equals("")) {
//					logger.info("If Bank Id is null=======>");
//					model.setAmountStr(chargesDto.getAmount());
//					model.setChargesStr("0");
//					model.setTotalStr(chargesDto.getAmount());
//					model.setConvenienceStr("0");
//					EncryptedResponse encryptdata = EncryptionUtil.encryptdata(model.toString(), merchantKeys);
//					ResponseWrapper<String> responseWrapper = new ResponseWrapper<>("SUCCESS", encryptdata.toString());
//
//					return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
//				} else {
//					subType = chargesDto.getBankId();
//					ConSubType = chargesDto.getBankId();
//				}
//			}
////			model = merchantCommisionService.getCommisionAmountModel(mid, paymentMode, Double.parseDouble(amount),
////					"IPG", subType);
//
//			model = merchantCommisionService.getCommisionAmountModel2(Long.valueOf(requestWrapper.getMid()),
//					chargesDto.getPaymentMode(), Double.parseDouble(chargesDto.getAmount()), "IPG", subType,
//					ConSubType);
//			conveniencemodel = convenienceChargesService.getConvenienceChargesModel(
//					Long.valueOf(requestWrapper.getMid()), chargesDto.getPaymentMode(),
//					Double.parseDouble(chargesDto.getAmount()), ConSubType);
//			BigDecimal conBigDecimal = conveniencemodel.getCharges();
//			model.setConvenience(conBigDecimal);
//			BigDecimal totalAmount = model.getTotal();
//			totalAmount = totalAmount.add(conveniencemodel.getCharges());
//			model.setTotal(totalAmount);
//
//		} catch (Exception e) {
//			logger.info("Card No. not valid");
//			new GlobalExceptionHandler().customException(e);
//		}
//		EncryptedResponse encryptdata = EncryptionUtil.encryptdata(model.toString(), merchantKeys);
//		ResponseWrapper<String> responseWrapper = new ResponseWrapper<>("SUCCESS", encryptdata.toString());
//
//		return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
//	}

}

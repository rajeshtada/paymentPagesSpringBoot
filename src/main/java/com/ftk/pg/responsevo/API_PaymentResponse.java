package com.ftk.pg.responsevo;

import java.util.List;

import com.ftk.pg.dto.MerchantDTO;
import com.ftk.pg.modal.Bank;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.modal.MerchantProductIdDetails;
import com.ftk.pg.modal.UIColorScheme;
import com.ftk.pg.modal.UpiQrDetail;
import com.ftk.pg.modal.Wallet;
import com.ftk.pg.requestvo.PaymentRequest;
import com.ftk.pg.util.CommissionModel;
import com.ftk.pg.util.ConvenienceModel;

import lombok.Data;

@Data
public class API_PaymentResponse {

	private String merchantchanrgesShow;
	private String merchantemail;
	private MerchantProductIdDetails merchantProductIdDetails;
	private String qrpath;
	private String qrstring;
	private PaymentRequest paymentRequest;
	private CommissionModel commissionModel;
	private String enabledPayModes;
	private MerchantDTO merchant;
	private List<String> months;
	private List<String> years;
	private List<Bank> banks;
	private List<Wallet> wallets;
	private UpiQrDetail upiQrDetail;
	private UIColorScheme uIColorScheme;
	private String transactionId;
	private String tpv;

}

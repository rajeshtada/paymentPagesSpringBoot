package com.ftk.pg.paymentPageApis;

public class CompleteTest {

	public static void main(String[] args) throws Exception {
		
		String mid = "76606";
		String environment="live";	
		
		GenerateInvoice generateInvoice = new GenerateInvoice(environment,mid);
		String token = generateInvoice.invoice();
		
		InitiateTest InitiateTest = new InitiateTest();
		InitiateTest.initiate(token);
		
//		PayTest  payTest = new  PayTest("CC");
		
		
		
	}

}

package com.ftk.pg.nbbl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.ftk.pg.util.NbblEncryptionUtil;

public class TestBlackHashProcess {

	public static void main(String[] args) {
		
		NbblEncryptionUtil nbblEncryptionUtil = new NbblEncryptionUtil("", "");

		String payload = "ver=1.0&mode=QR&orgId=PJS01&Tts=2024-10-12T01:41:00+05:30&rId=PJS014297296886SDFPH&expiry=5000&tdataEnc=UTP6Wzn4mG9ajmRbRwX0Fv1262XbB17qMF5FJy8IydnX2eDrfzksX7BugrJUia5kebCQpnT0UXEyUIPcwaW8PvfyhWOnoilWivnrlLRItU87YXP7RK76EL0BnHeZCJSOBs0KBHQhvJ3qJuUM+bUu14FRLq5hkRHLm/2Ny29HjH3hOg5g1peC69egeQBwzUYA1Qs0PZeHimLtG0O8xX0mFJdq3sf4ruP42+SwVRO+aB0wmCMSvm50hhN/XZPq42vivHI44CO6Q5edq2+3xN3gu5JDKTVbfdWxImEBr8wAE+UqYkgmSEy8ulk/11E7hD292spf3u3FImd4e07k1EhDd9Ac6u4JlDKL/gRf+na2DiwRPSr+YD850Y+xgDTc01a5j5XrkN9/wM5v8MKqcL8zzdLPAG6GBEhW2nOGGj7QqnEtSRUuMCKzgVIkc2mkh/Z3k9pUPlZInHYj7N6E2Cfs1LprTo3NO1OWgNghdWF94PD5j1D0RF3OO222TUGY2hnmWIsc5NQQuxFR7a1ZPMI076+KcFTv8OamyQjMISWCI4SPlN8d+KaO2sfNTLh73S92y+nci1DSnymRvtVywwdPma9bfaOgDm+zHeioL2NeOPKC+cj+wW8mFZ6Pspn1bYPKP/Zc+F9YvpJXsQE5Rla6AhpOfQAY+e2aBLujGCBkngoDTFMqza8T+qDiN6rueOXFQmkz5gtF9DwByyOdg2PGD064bKnP2V63uPJpr9Z5ioPz5kX9NyC9Tw/D2eQRrctosJKkMulE6+wvqBwcZLpqb2cafEsOQK1Tpqe/uS5H4139tdFUQ48Yyh1LNquqzilmO/SNZB19NoSRIIo8em74Vw==";
		
		byte[] digest = nbblEncryptionUtil.blake2b512(payload.getBytes(StandardCharsets.UTF_8));
		String base64Digest = Base64.getEncoder().encodeToString(digest);
		System.out.println("nbbl Signing String === " + base64Digest);
		
	}
}

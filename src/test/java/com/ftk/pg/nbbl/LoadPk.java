package com.ftk.pg.nbbl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

public class LoadPk {

//	static {
//		Security.addProvider(new BouncyCastleProvider());
//	}

	public static void main(String[] args) throws Exception {
		PrivateKey pk = getPrivateKeyCertificate("/media/shared/nbbl/key_file/ec_keys/nbbl-hpy-key.pem");
		System.out.println(pk);
		System.out.println(pk.getEncoded().length);
		
	}
	private static PrivateKey getPrivateKeyCertificate(String upiPrivateKeyCrt)
			throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, Exception {

		   if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
		        Security.addProvider(new BouncyCastleProvider());
		    }
		   

		FileReader fileReader = new FileReader(new File(upiPrivateKeyCrt), StandardCharsets.UTF_8);
		PemReader pemReader = new PemReader(fileReader);
		PemObject pemObject = pemReader.readPemObject();
		pemReader.close();

		byte[] keyBytes = pemObject.getContent();
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory kf = KeyFactory.getInstance("EC", "BC"); // For ECC keys
		return kf.generatePrivate(keySpec);
	}
	
}

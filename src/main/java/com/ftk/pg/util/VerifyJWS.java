package com.ftk.pg.util;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.crypto.bc.BouncyCastleProviderSingleton;


public class VerifyJWS {
  
  public static boolean verifyJWSSignature(String jwsString, RSAPublicKey verificationKey)
    throws Exception {

    JWSObject jwsObject = JWSObject.parse(jwsString);
    java.security.Provider bc = BouncyCastleProviderSingleton.getInstance();
    JWSVerifier verifier = new RSASSAVerifier(verificationKey);
    verifier.getJCAContext().setProvider(bc);
    return jwsObject.verify(verifier);
  }
  
  public static PublicKey getPublicKey(String encPath) throws Exception {

    FileInputStream in = new FileInputStream(encPath);
    byte[] keyBytes = new byte[in.available()];
    in.read(keyBytes);
    in.close();
    String certStr = new String(keyBytes, "UTF-8");
    certStr = certStr.replaceAll("BEGIN CERTIFICATE", "");
    certStr = certStr.replaceAll("END CERTIFICATE", "");
    certStr = certStr.replaceAll("-", "");
    certStr = certStr.trim();
    //remove start and end of certificate
    byte[] decodeVal = org.apache.commons.codec.binary.Base64.decodeBase64(certStr);
    ByteArrayInputStream instr = new ByteArrayInputStream(decodeVal);
    CertificateFactory cf = CertificateFactory.getInstance("X.509");
    X509Certificate certObj = (X509Certificate) cf.generateCertificate(instr);
    RSAPublicKey pubkey = (RSAPublicKey) certObj.getPublicKey();
    return pubkey;
  }

  
  public static PrivateKey getPrivateKey(String signingPath) throws Exception {
	  String privateKey = new String(Files.readAllBytes(Paths.get(signingPath)));

	    System.out.println("Original Private Key: " + privateKey);

	    privateKey = privateKey.replace("-----BEGIN PRIVATE KEY-----", "")
	                           .replace("-----END PRIVATE KEY-----", "")
	                           .replace("-----BEGIN CERTIFICATE-----","")
	                           .replace("-----END CERTIFICATE-----","")
	                           .replaceAll("\\s", ""); // Replace all whitespace characters

	    System.out.println("Cleaned Private Key: " + privateKey);

	    byte[] pkbytes = java.util.Base64.getDecoder().decode(privateKey.getBytes());
	    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkbytes);
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    PrivateKey pk = keyFactory.generatePrivate(keySpec);
	    return pk;
  }
  
  
  public static void main(String[] args) throws Exception {
	  String xyz=null;
    System.out.println(verifyJWSSignature("eyJhbGciOiJQUzI1NiIsImNsaWVudGlkIjoiU09OQVRBIiwieDV0IzI1NiI6IjltWGlpRkRlR3dkZEpxLVdDY3VuVF9lUXMyUVcxaEl1ZXlYM3I1MF9lOVUifQ.ZXlKaGJHY2lPaUpTVTBFdFQwRkZVQzB5TlRZaUxDSmpiR2xsYm5ScFpDSTZJbE5QVGtGVVFTSXNJbVZ1WXlJNklrRXhNamhIUTAwaUxDSjROWFFqTWpVMklqb2labVUyV1d4c2RTMUJjSFpEVjNRNWREQkRaVTkzT0RsMlFuVlVXVGxvYlZwSlZsaFlRbXRvVmtGaVNTSjkuQmp3RU9oQmpGN1BmeVRrNF9zZDRPZElLZktqRHVPUWFoRklocWhCYUFQLWJMcjJJVUZFVVRidkhoYmF2clBJLVJBTnVNQVIxcTJhTmhCbjFJaTFuZ25sZElkaDFIanF4Z3pJUEhlSkZodGlpOTFmTll6WEVJREc1OTY5bUhQYTVYYVc2d2VnMEhXYVJLQ3JSOUhUeTZCTUNaM2tFRUNxNnhHNFU1ZThHdFNCOUlqeDEyOW83emE4eUdPTDBYbXBPanNmU0t4RmpPa0wtWjQwV0NTWGRNcmJ4UU10dDE1YTBnQmdzb3Y3YUg2WnRhVloyS1NydTkxQnkzUC1UaU9BWWFYbzRncURnSEJCN3I0QnhfU0M4SE8yVXZTd1dqSFdZZlo2bk5JeXlHaDlhM0hpYXdzcUdyM1ZPUWtVV2h1WjZMTmtUNS1mSjFXTW82alZ6cHZvUkRRLmNwLV9wZ3RreGNiRlBpTjAuZEVpSW13LUdIbG45V0FQY3RJbG9qQTA2M20yLWEwXzFfdTVzRkdKb0hzQ085Si1WQjQxZFdWR1Q0Ukc4TW84bzJGUzVxLXpONFc1M3FGUUM2TlNVa3BDVHV0YXN1ekIwcG11cGZQSGlBSXRQLVRUSmRsUm1vczQ1QmVBVzlwdzFQVFlmSk9helJmMHNkR3M4VHg4ZThLRzNWZ1pab2Vza2xtX0lYMHhNajYwZlVJQ3gxenlxZ0N0dk83YzEzdUxWT1lOQ3BZOUpjczdfUlQ5S2NjcG1HdkVVMXp6WkVDYWhuazY3Z19YUWphMlhMbFozU0dsVG1aYU9zT0dUTTgxaFc3N2p2OFp0X3A4RG56NDVMNzljbWJSNGtYSUxTLUw2REhxcXFLTi05RnhVM2xSWkxLSkx6czZ5U0FYRGFHU0VjdDZaTFBjRy1qMDlHbnNSZ1J0ZlRLQ25HSGpqQWhJbWRfdzdWZE1Yc0txdGFhVGFSOGVCSXBjNnN5YTE1NEVLTmxIWlM4Mk8yOFVSMFJTUHoxaHI4NUttM0c4OThBMjkwbU1WaV9nd081NnR2MmZTZUdjZ29SemVQWTRPMWYyQUVHWmZVeHYzWThPeHlWaW8yMGdwYmkxN2FPYmpxNUVKdFY0eVlTbFZuWXZxSU54eEoxUW53cm4yQ2lKeU1tT2Y4Ql9UQ29obS52QmZ5bEthaEFMYm1LRzUyWFJJaEpR.X8sMCdoX-pOJ7lRdotHQhIyrV4O3_-Fd2bxtKcsRMqhGYEvNl4twNGSZAMcGKl9C6OL3Q4XDg_jFhmvJGKDGyhof5yt0fmlVJIbbD2xG7SXTc0n0HIDVK9VMQCU-9HTZnKW9RguGmoXb-Fk2EtXeTMTiVfDLKwauEx07w2KfJMR0KCgt7Ym9QhAaFpUd1pao59VcJ8961yHBbhp4t8AwV-bgaNwmw9yVmCzEk_sJ4Qch0Jt_XRysTd3d2VZkQhtayVDAPno_FL5XllV-51qNaSLEEutBuPEaqBlr2lKy7784_enPsiSurJAPBgUr7vBGMHONR6v6bbeHJbzLxjriog", (RSAPublicKey)getPublicKey(xyz)));
  }

}
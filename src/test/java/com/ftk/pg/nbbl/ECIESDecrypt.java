package com.ftk.pg.nbbl;
import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.IESParameterSpec;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class ECIESDecrypt {

//	static {
//	Security.addProvider(new BouncyCastleProvider());
//}
	
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        String privateKeyPem = "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgC35zlD3AyneiYSCgq3CFE86cHMDGQ78ySmRYL+fLed+hRANCAATrhj1m2N1kU4GEFG77QUIAUg0d4QDLm4M5OymszISSGVLs73ivTm+Nl1jupC/RLzaJPo1L+dv/Vy+s/3y6yGz8";
        String encryptedPayload = "aTMDynoLaMm2UlqwnYNGOHL92yLSde1e+mLmU0ZgRcWU4uCqReIQg9hFDLV5skmjtZXdKomROmQT/PMx01oOfwQhyetLG5PM06BhLfcqyFP6LGYPIuIumokJ2/mnlHpf8voUuHOI1D2aPcbvMDdU/J3jtL7D3Tjddd8cvqOOr6o3NB7UIafl+TyRXxcqQgzjBUDLBhpYEm85xP4TzOGVzSBpWy3Ghm+qmkxSYjx2fhnQ+ONAlv4mBBaFQGLKxK8Cm8Qr0zz7vmwC9rACW/Nqe7nsDiIf5P4imIkT915oxLF+MqfcOLgUkuxXH0pxTKxa19m/K+nkHhWqCtcU6BCUJXuYfuhf8yX0Gi7GxZcgy75XdGhWXjq2k1i1nnhJ1k80ozYHtmgDlt7h6pPzQS8HjFS/8uCpDsUxdIkWzwjT7bBS0fdqSbEVfZqIeS3nV9EmuD+RzW2DreOi9pNOxvSvJvoOWJZnlx8Aq/okTsFy3flbYDsvYjDahEnG19U6qMrTTKEM/p9pa1KrxcpyI+FEez1JhnZl+FwiTjxrzLLEJLL+r9EAnjKZUqSw2cMO/rxDN1GOjpnc283772TBVR4QF4JAZjvgAymYVrHAW/hJ7Wxu94QMdR51FFnAxnWqztDu3W9VmeOqHLQKeqbHxcH386SJlt13nofudq+Ex35+bK3z";

        // Decode private key
        byte[] pkcs8 = Base64.getDecoder().decode(privateKeyPem);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8);
        KeyFactory kf = KeyFactory.getInstance("EC", "BC");
        PrivateKey privateKey = kf.generatePrivate(keySpec);

        // Setup cipher with BC
        Cipher cipher = Cipher.getInstance("ECIESwithSHA512/NONE/NoPadding", "BC");
        IESParameterSpec iesParamSpec = new IESParameterSpec(null, null, 256);
        cipher.init(Cipher.DECRYPT_MODE, privateKey, iesParamSpec);

        // Decrypt
        byte[] cipherBytes = Base64.getDecoder().decode(encryptedPayload);
        byte[] decrypted = cipher.doFinal(cipherBytes);

        System.out.println("Decrypted: " + new String(decrypted));
    }
}
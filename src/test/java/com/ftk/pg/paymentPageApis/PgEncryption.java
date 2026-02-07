package com.ftk.pg.paymentPageApis;

import java.util.Base64;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PgEncryption {

	public String iv;
	public String ivKey;

	public PgEncryption(String iv, String key) {
		this.iv = iv;
		this.ivKey = key;
	}

	public static void main(String[] args) throws JsonMappingException, JsonProcessingException{
		
		
		String ivString = "hlnuyA9b4YxDq6oJSZFl8g==";
		String keyIvString = "JoYPd+qso9s7T+Ebj8pi4Wl8i+AHLv+5UNJxA3JkDgY=";
		PgEncryption encryption = new PgEncryption(ivString, keyIvString);
		
		String response = "5BDAAD0A7A2C711FD4A8431395563C18D742EE129A37313087FDCF33D0DCCFAAB82BA7FAD2BB1BA060E9A5BABA8F757D3EC0010BA1D84E129EB50ADA69E4857B7C8DC8838B6BDBA388FE8E87A64322463E9FE4DC0615AE904565AFC76A16AF6034400CAE1A8DF67268162159F678795CA76C58504CEB38C31C9BCFBD764622BF37F00E491CAA29720BD9B2F8949B40DE0BC53F43C17B512A3A0777C19F8C12174383DACE057D1C8DA331E860C9C846BDDC97F87B22C51C545B7EE395BDDCC80792245EE2BA1B9CBBEF2AA28290A6B9ABCF34160E4D7150F1E631B70EE2C4CFE774C3E2AA188B201AC117CE6EB24ED2CC0EFF18B76369D84F5BFAAE7A96DDC2BB04D6FFA80B86B5E591E4F235F9BAE0C6A80E53B9788925648FDFEFAEBBB8612CE28AC47AA42261F4D90D1B7974875EF8EB7C8CB680FFF374E18B94505392C9A3880F7A07666027868A6DFD8BE403EDF20B5A34B982A384B3B19E6D9602856D187E256A25D93B33B6CD587732F12F2601790A826A9456E150F5D7372B542C39C82B3FD7E0127CAD8CAC08DD4DF57691DBB16D7209522AB303E14956B4528AA71B2606B2C8BA8D7D613BC83F260DCDAC7446E7B239F55EF89A97AE5F3A1AA3BD82E4BC984FB72004A321EE0BE70C95FE819B564F0B8B1D0923BA21B5B186AFF2645EA2B0AA380A53FE796D848A66BBC24D84316A900419B88DB45AD88A627334750CADB20BB7CC006492CD325BC3E14B6FC1A3AD1EA36F743864AF25BD88BD75EFC14710C5646836E5056D76F2797042CD1B0BE59CDA5955F1B20DBE38109C50D04FA4D36CF29077E018E982F0595A01EC76937CF287B1C68EC2C8C19D0831160C39BB571C4D4E5E6CD394081C16D384C323C8C82D6493D3DC97C89565F23FC1CB65406411C0D97E4AF2CF10623104F17B3A95637F2854D9B5200C3F3C7C805E4BB687342043CA48041DD15BEE5958600AB37FCB5720F69B943DF0AB9B5671D3CB08AB34868A511D575D4E83BE07559806261BEEC227E073F97ACDD19D69AB3ED04C2958CCA4B93051472DDF338A8B1C7E09C742176F1E7B0955486EE61B78FFD893A665B2B70E66236E8A4DDD0C2B6329";
		String decrypt = encryption.decrypt(response);
		
		System.out.println(decrypt);
		ObjectMapper mapper = new ObjectMapper();
		String jsonInner = mapper.readValue(decrypt, String.class);
		Map<String, String> map = mapper.readValue(jsonInner, Map.class);
		
        String jsonOutput = mapper.writeValueAsString(map);

        System.out.println(jsonOutput);
        
	}
	public String encrypt(String string) {
		try {
			byte[] ivs = Base64.getDecoder().decode(iv);
			IvParameterSpec ivspec = new IvParameterSpec(ivs);
			SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(ivKey), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivspec);
			byte[] encrypted = cipher.doFinal(string.getBytes("UTF-8"));
			return toHexString(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String decrypt(String message) {
		try {
			byte[] msg = toByteArray(message);
			SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(ivKey), "AES");
			byte[] ivs = Base64.getDecoder().decode(iv);
			IvParameterSpec ivspec = new IvParameterSpec(ivs);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivspec);
			return new String(cipher.doFinal(msg));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String toHexString(byte[] array) {
		return DatatypeConverter.printHexBinary(array);
	}

	public static byte[] toByteArray(String s) {
		return DatatypeConverter.parseHexBinary(s);
	}

}

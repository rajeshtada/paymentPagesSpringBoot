package com.ftk.pg.service;

import com.ftk.pg.encryption.GcmPgEncryption;
import com.ftk.pg.encryption.GcmPgEncryptionPortal;
import com.ftk.pg.vo.generateInvoice.GenerateInvoiceResponseWrapper;
import com.ftk.pg.vo.generateInvoice.PgRequestWrapper;

public class GenerateInvoiceServiceTest {
	
	static String key = "lGed7VR7NcE1oSeCeUN0ng==";
	static String iv = "9TXpxfuXD6/4RJ0cuOCX0g==";
	
	public static void main(String[] args) throws Exception {

		String req = "I87u8K8xBaaRW7pM+dZ8FZdD17Jgikw+y0rfGs3vLQ9D7NDvriBjha5DVaMddaKqNEX1p8PAnxr3cKTpVSM5ikHF2K62/GGVyvfrlxj8+ANPzx2LrqdesqHYwCnU8ncmF8GP5wQnUijlQ+EIaP7ntM4UUcYspu/Oh+MInkJ3Juv6Dd8B+WdDIjl2vWhz1cUURgs7Zl9yZZYt4Ss/P9MCrNN5Dxf7sF/wtOWu8q68wxuSeGmgWoy/q041NdPyRFFMEZlL5O2QoUPaucQe3Fbp8ZR2Np2DK9kiXc131ihr07fXrxVe09s6WBC2fKVIYkKZw4dHzsF/4tZB+Uyy6gHUlUXt82FqxqJKjN0eWlL9PhoonvG7LgyaUPUMpGny0M6UfqVKxurPuMhwuq+z6zrEqtNaxaWkOlb1JXYjz4d3Fd6d5OIMX6eXwcFHspV4lhehIgyBI6Pd9A17O3ZrDPhazF3XypcAqwo=";
//		
//		GenerateInvoiceService service = new GenerateInvoiceService();
//		PgRequestWrapper requestWrapper = new PgRequestWrapper();
//		requestWrapper.setMid("44");
//		requestWrapper.setReq(req);
//		requestWrapper.setTerminalId("getepay.merchant202500@icici");
//		GenerateInvoiceResponseWrapper invoice = service.generateInvoice(requestWrapper);
//		System.out.println(invoice);
		
		GcmPgEncryption gcmPgEncryption = new GcmPgEncryption(iv, key);
//		GcmPgEncryptionPortal gcmPgEncryption = new GcmPgEncryptionPortal(iv,key);
//		String decryptedReq = gcmPgEncryption.decryptWithMKeys(req);
//		System.out.println(decryptedReq);
			
		
//		String respons = "1vgAit0STXh+o4AeVuL7Wycc8gEH7YTa9WoiyNPGSVW6fPtjDc4FVzSIwjqQUTEUrVNQkOxyOrjMEm/A9LZjLwkVGSD15LeYp8MPz91djSFD4GGhcz+R386k0Q5HTCCVNW2y61XhvIJtTh8ci9bhKiKsKjQ4GCyaYXULijjY/QLnQ0qTyvf0DU/tQdIoaFdLV08vt5sXmC/mvbkSmgEVW3eiK1BECLeJFy+jNKzEtO01AX/149FlZdk19U+KNJ7nEamFwO1yVmdHCLX6Z1+A3dNjHVFED1sNIbNDNT6ErhcloGDJBasX1McAbLNK7fYS8kg8EKjCMiJeQLdvs7FGMcQYJ9zXXPVCT2lSCgH9iklj5zJmlP58EhKlMAM7zL078AeJkehoCyTEdiyQ1ch6H4IHXdArnAWkU3UKfCwUGikU4FBF0V0mWwzrBERhQPhCfxD6s79giv2edtjWxanstglv25d4EX61VSCIpdAev5MBnytSzazWCr5nuMN2ZPduo+SD8ZS9xIztkHhYH3lA3JqHXDIyOx15KBvg7U7ySy3nBopjvNorHG6K9pgNUQ8s+e8rK2jJFTmT4mlhpqgvpWj0NtH62YxJn77m9wtUBPf7rN1IrnwxnzFD+/KzbhprUot9SACdGtk9FUmyaTUhFC+KL1SJqG546rJz10cS35NPSIerslTvKWgMTMnDt72EcsOoT3Co9Ea2YRosKNNPliuyipJYxGSxZesZ/tWC4EW/ZBzSGjQyt7COgScPFhf0Gd0=";
		String respons = "rvCfmTHzKGciJ6cWUITvkTHb0jxA+F3/VhW6nq29Cymu0kAUgVGYraXR6hqAFSVVigUdMeVeeQd1NGdBkx3/Yx4WPArzX3vCMRvSE4SQXEbM1UY2g53AxNXyqaiR9b0tqX0cCJMdLss+oLpDC/LXAJNQDJnfo7CUrSqMgsyqyRIVRBAdySFPPW6eMD8yC2JAEwCEcmKveVU/S4d+K5GmvPKtBLOgYMQZ1W48L3FTcXkyLZXPLFAWIwYU5EOBGfxZC+M820Wt8UIZ5U4UbaXSQqyI17a/mpz/leYxwXR+nMATuUyvwjPtjiyAgLzyGlOkwrJIxMiaTXZi7uLUDuSPkU6QnUwQZmhnRGl0SrwcgYGOBMiPH1w6jfCpYcjiO6rKSxGiSif/lbAA2RfpS01NS16gfJEYzNAtT1tjf2dfmMYhOpd9Ere6UNasTX1kJxGzmgGdpYIUvMZ/ul9X8FqTTZE5lWEJQhwq6rF0i876ev8ih6cpzfa8yAgG4bE42zd2OTw57mO/U+BoQfSmnFOZrZXoU5hTWAeO8/WMIe117TJuTWJD95+pTIOhYYq0ui7vJuxf3KdNVOS4EhyMp8qoza9oBMBNODgyT3O1iTZChx1GMkGfrhetG/4PcMs5sYSdmCaR1xbh/T1Uf8Nxag4WOEak5GkyGybqc4mtwC34kTO8U/002265c/VZLPkVTeMSoHCjParJ0IR+kYn1vPooEZJ5Pq9bIUxzqSqWTwUr6EuEFBm3VQQ0XmheE7N3hwXg7iluXQ==";
		//		System.out.println(encryptWithMKeys);
		String decResponse = gcmPgEncryption.decryptWithMKeys(respons);
		System.out.println(decResponse);
		
	}

}

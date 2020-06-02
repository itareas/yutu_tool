package com.yutu.utils.data.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * 身份证加密解密
 * @author sunyc
 *
 */
public class CardIDEncrypt {
	//密钥
	private final String key = "DreamCard";
	
	public String encode(String data) {
		//加密
		try {
			Key deskey = this.keyGenerator(key);
			Cipher cipher = Cipher.getInstance("DES");
			SecureRandom random = new SecureRandom();
			cipher.init(Cipher.ENCRYPT_MODE, deskey, random);
			byte[] message =  cipher.doFinal(data.getBytes());
			return Base64Utils.encode(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String decode(String data) {
		//解密
		try {
			Key deskey = this.keyGenerator(key);
			
			SecureRandom random = new SecureRandom();
			
			Cipher cipher = Cipher.getInstance("DES");
			
			cipher.init(Cipher.DECRYPT_MODE, deskey, random);
			
			byte[] message =  cipher.doFinal(Base64Utils.decode(data.toCharArray()));
			 
			return new String(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private SecretKey keyGenerator(String key) throws Exception {

        DESKeySpec desKey = new DESKeySpec(key.getBytes());

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        
        SecretKey securekey = keyFactory.generateSecret(desKey);
        
        return securekey;
    }
	
	static class Base64Utils {  
		  
        static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_=".toCharArray();  
        static private byte[] codes = new byte[256];  
        static {  
            for (int i = 0; i < 256; i++)  
                codes[i] = -1;  
            for (int i = 'A'; i <= 'Z'; i++)  
                codes[i] = (byte) (i - 'A');  
            for (int i = 'a'; i <= 'z'; i++)  
                codes[i] = (byte) (26 + i - 'a');  
            for (int i = '0'; i <= '9'; i++)  
                codes[i] = (byte) (52 + i - '0');  
            codes['-'] = 62;  
            codes['_'] = 63;  
        }  
        
        static public String encode(byte[] data) {  
            char[] out = new char[((data.length + 2) / 3) * 4];  
            for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {  
                boolean quad = false;  
                boolean trip = false;  
                int val = (0xFF & (int) data[i]);  
                val <<= 8;  
                if ((i + 1) < data.length) {  
                    val |= (0xFF & (int) data[i + 1]);  
                    trip = true;  
                }  
                val <<= 8;  
                if ((i + 2) < data.length) {  
                    val |= (0xFF & (int) data[i + 2]);  
                    quad = true;  
                }  
                out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];  
                val >>= 6;  
                out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];  
                val >>= 6;  
                out[index + 1] = alphabet[val & 0x3F];  
                val >>= 6;  
                out[index + 0] = alphabet[val & 0x3F];  
            }  
              
            return new String(out);  
        }  
        
        static public byte[] decode(char[] data) {  
            int len = ((data.length + 3) / 4) * 3;  
            if (data.length > 0 && data[data.length - 1] == '=')  
                --len;  
            if (data.length > 1 && data[data.length - 2] == '=')  
                --len;  
            byte[] out = new byte[len];  
            int shift = 0;  
            int accum = 0;  
            int index = 0;  
            for (int ix = 0; ix < data.length; ix++) {  
                int value = codes[data[ix] & 0xFF];  
                if (value >= 0) {  
                    accum <<= 6;  
                    shift += 6;  
                    accum |= value;  
                    if (shift >= 8) {  
                        shift -= 8;  
                        out[index++] = (byte) ((accum >> shift) & 0xff);  
                    }  
                }  
            }  
            if (index != out.length)  
                throw new Error("miscalculated data length!");  
            return out;  
        }  
    
	
	}}

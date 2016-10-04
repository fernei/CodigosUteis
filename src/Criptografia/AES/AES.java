/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Criptografia.AES;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.*;

/**
 *
 * @author fernando.m.souza
 */
public class AES {
    
    private static final String ALGO = "AES";
    private static final byte[] keyValue
            = new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't',
                'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};
    
    public static String encrypt(String Data) throws Exception {
        System.out.println("string length: " + (Data.getBytes()).length); //length = 16
        Key key = generateKey();
        Cipher chiper = Cipher.getInstance(ALGO);
        chiper.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = chiper.doFinal(Data.getBytes());
        System.out.println("output length: " + encVal.length); //length = 32
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }
    
    public static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher chiper = Cipher.getInstance(ALGO);
        chiper.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = chiper.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }
    
    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }
    
    public static void main(String[] args) throws Exception {
        System.err.println(encrypt("auto_soc_111"));
        System.out.println(decrypt("c0+2QANenQO/lpHacL09kw=="));
    }
    
}

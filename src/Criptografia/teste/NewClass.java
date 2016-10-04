/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Criptografia.teste;

import static Criptografia.teste.crip.getSecretKey;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author fernando.m.souza
 */
public class NewClass {

    public static final String ALGORITMO_AES = "AES";
    public static final String CHAVE = "oinovocoiraja450";

    private static BASE64Encoder enc = new BASE64Encoder();
    private static BASE64Decoder dec = new BASE64Decoder();

    public static String encrypt(String text) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, UnsupportedEncodingException, InvalidKeySpecException {
        SecretKey skey = getSecretKey(CHAVE, ALGORITMO_AES);
        Cipher cipher = Cipher.getInstance(ALGORITMO_AES);
        cipher.init(Cipher.ENCRYPT_MODE, skey);
        return enc.encode(cipher.doFinal(text.getBytes()));
    }

    public static String decrypt(String text) {
        StringBuffer ret = new StringBuffer();
        try {
            SecretKey skey = getSecretKey(CHAVE, ALGORITMO_AES);
            Cipher cipher = Cipher.getInstance(ALGORITMO_AES);
            cipher.init(Cipher.DECRYPT_MODE, skey);
            byte[] b = dec.decodeBuffer(text);
            ret.append(new String(cipher.doFinal(b)));
        } catch (Exception e) {
            return "Chave Incorreta";
        }
        return ret.toString();
    }

}

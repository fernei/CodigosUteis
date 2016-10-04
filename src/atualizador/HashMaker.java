/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atualizador;

/**
 *
 * @author fernando.m.souza
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashMaker {

    public static String geraHash(File f) throws NoSuchAlgorithmException, FileNotFoundException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        InputStream is = new FileInputStream(f);
        byte[] buffer = new byte[8192];
        int read = 0;
        String output = null;
        try {
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
            byte[] md5sum = digest.digest();
            BigInteger bigInt = new BigInteger(1, md5sum);
            output = bigInt.toString(16);
            System.out.println("MD5: " + output);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possivel processar o arquivo.", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException("Não foi possivel fechar o arquivo", e);
            }
        }
        return output;
    }
}

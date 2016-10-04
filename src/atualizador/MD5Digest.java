/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atualizador;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author fernando.m.souza
 */
public class MD5Digest {

    /**
     * @param args the command line arguments
     * @throws java.security.NoSuchAlgorithmException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {

        String original = "5065F3BB2402MW76XQ0DZLIV55";

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(original.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }

        System.out.println("original:" + original);
        System.out.println("digested(hex):" + sb.toString());

    }

}

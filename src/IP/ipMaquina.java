/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IP;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fernando.m.souza
 */
public class ipMaquina {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        try {

            System.out.println(InetAddress.getLocalHost().getHostName()); //NOME
            System.out.println(InetAddress.getLocalHost().getHostAddress()); //IP
            System.out.println(System.getProperty("user.name")); //Usuario Windowns

            System.out.println(System.getProperty("java.class.path"));

        } catch (UnknownHostException ex) {
            Logger.getLogger(ipMaquina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

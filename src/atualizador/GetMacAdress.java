/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atualizador;

import java.io.PrintStream;
import static java.lang.String.format;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.Format;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fernando.m.souza
 */
public class GetMacAdress {

    public static void main(String[] args) {

        try {
            InetAddress address = InetAddress.getLocalHost();
            NetworkInterface ni = NetworkInterface.getByInetAddress(address);
            byte[] mac = ni.getHardwareAddress();
//            for (int i = 0; i < mac.length; i++) {
//                System.out.println(mac[i]);
//            }
//
//            for (int i = 0; i < mac.length; i++) {
//                System.out.format("%02X%s", mac[i], (i < mac.length - 1) ? "" : "");
//
//            }

            String retorno = null;
            retorno = InetAddress.getLocalHost().getHostName();
            System.out.println(mac+retorno);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException ex) {
            Logger.getLogger(GetMacAdress.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

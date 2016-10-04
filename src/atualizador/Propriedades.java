/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atualizador;

import java.util.Properties;

/**
 *
 * @author fernando.m.souza
 */
public class Propriedades {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
       Properties p = System.getProperties();
       
        String[] split = p.toString().split(",");
        
        for (String string : split) {
//            if (string.contains("java.runtime.version")){
                System.out.println(string);
//            }
        }
        
//        System.out.println(p.toString());
        
    }
}

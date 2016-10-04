/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Criptografia.Criptografia;

/**
 *
 * @author fernando.m.souza
 */
public class main {

    public static void main(String[] args) {

        Criptografia criptografia = Criptografia.getInstancia("teste");
        System.err.println("Senha Criptografada: " + criptografia.getSenhaCriptografada());
        System.err.println("Senha origem: " + criptografia.getSenha("191F1FAD09FA4A304009FD1D16096109F47BFEDDC509F9F47BFEDDC509F341794B09F6081CCDB809F"));

    }
}

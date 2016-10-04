/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Criptografia.BCrypt;

/**
 *
 * @author fernando.m.souza
 */
public class testeBCrypt {

    public static void main(String[] args) {

        BCrypt teste = new BCrypt();
        
        System.out.println(teste.hashpw("FERNANDO", teste.gensalt()));
        System.err.println(teste.checkpw("FERNANDO", "$2a$10$DXStWdIpO275mTKO8JmfA.d8Mk3BVhzfJkWp1.wFJZWMMBPiQPAvm"));

//        
    }

}

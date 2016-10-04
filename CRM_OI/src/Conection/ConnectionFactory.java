/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conection;

import Model.Banco;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author otavio.c.ferreira
 */
public class ConnectionFactory {

    public Connection criaConexao() throws SQLException {
        Banco  banco = new Banco();
        return DriverManager.getConnection("jdbc:oracle:thin:@" + banco.getIp() + ":" + banco.getPorta() + ":" + banco.getBanco(), banco.getUsuario(), banco.getSenha());
    }
}

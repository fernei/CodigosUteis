/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

//import Controller.ConnectionFactory;
//import static Controller.LogTrace.trace;
import Conection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author otavio.c.ferreira
 */
public class Servidor {

    private int id;
    private String ip;
    
    public void pegaServidor() {
        try (Connection conn = new ConnectionFactory().criaConexao()) {
            String sql = "SELECT NUMERO, IP FROM TBL_SERVIDOR_SAC"
                    + " WHERE ATIVO = 'S'";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.executeQuery();

                try (ResultSet result = stmt.getResultSet()) {
                    while (result.next()) {
                        id = result.getInt("NUMERO");
                        ip = result.getString("IP");
                    }
                }
            }
        } catch (SQLException ex) {
//            trace.error(ex.getMessage());
        }
    }

    public void atualizaServidor() {
        try (Connection conn = new ConnectionFactory().criaConexao()) {
            String sql = "UPDATE TBL_SERVIDOR_SAC"
                    + " SET ATIVO = 'S'"
                    + " WHERE NUMERO <> ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            
            sql = "UPDATE TBL_SERVIDOR_SAC"
                + " SET ATIVO = 'N'"
                + " WHERE NUMERO = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
//            trace.error(ex.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }
}

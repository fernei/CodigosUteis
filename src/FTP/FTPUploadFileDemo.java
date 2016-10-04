/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author fernando.m.souza
 */
public class FTPUploadFileDemo {

    private static FTPClient ftpClient;

    public static void main(String[] args) {
        String server = "plutao.telemar";
        int port = 21;
        String user = "usrcorj";
        String pass = "usr341!tg";

        ftpClient = new FTPClient();

        Conect(server, port, user, pass);
        UploadFile("C:\\Users\\fernando.m.souza\\Desktop\\Teste.txt", "\\UsrCoRJ\\Automacoes\\Teste.txt");
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void UploadFile(String arquivo, String nomeArquivo) {
        InputStream inputStream = null;
        try {
            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File(arquivo);
            String firstRemoteFile = nomeArquivo;
            inputStream = new FileInputStream(firstLocalFile);
            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The first file is uploaded successfully.");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FTPUploadFileDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FTPUploadFileDemo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(FTPUploadFileDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void Conect(String server, int port, String user, String pass) {
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException ex) {
            Logger.getLogger(FTPUploadFileDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

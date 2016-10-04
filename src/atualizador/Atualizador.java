/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atualizador;

import static atualizador.HashMaker.geraHash;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;


/**
 *
 * @author fernando.m.souza
 */
public class Atualizador {

    private static final String MD5Name = "MD5.txt";
    private static final String SistemaFile = "teste.txt";
    private static final String Diretorio = "Automacoes/Atualizador";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        VerificarAtualizacao(MD5Name, SistemaFile, Diretorio);
    }

    private static void VerificarAtualizacao(String MD5FileName, String SistemaFileName, String DiretorioFTP) {
        try {

            FTPDownload(MD5FileName, DiretorioFTP); //Arquivo TXT com o MD5

            //Le o valor do arquivo
            FileReader arq = new FileReader(MD5FileName);

            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();

            String Md5FPT = linha;
            String Md5Local = geraHash(new File(SistemaFileName)); //Mudar o file name 
            if (!ValidaVersao(Md5FPT, Md5Local)) {
                System.out.println("Atualizando");
                FTPDownload(SistemaFileName, DiretorioFTP);
            }

        } catch (NoSuchAlgorithmException | FileNotFoundException ex) {
            Logger.getLogger(Atualizador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Atualizador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static boolean ValidaVersao(String Md5FPT, String Md5Local) throws NoSuchAlgorithmException, IOException {

        if (!Md5FPT.equals(Md5Local)) {
            System.out.println("Versão desatualizada");
            return false;
        } else {
            System.out.println("Versão Atualizada");
            return true;
        }
    }

    private static void FTPDownload(String fileName, String DiretorioFTP) throws IOException, FileNotFoundException {
        FTPClient client = new FTPClient();
        FileOutputStream fos = null;

        client.connect("plutao.telemar");
        client.login("usrcorj", "usr341!tg");

        String filename = fileName;
        fos = new FileOutputStream(filename);

        client.changeWorkingDirectory(DiretorioFTP);
        client.retrieveFile(filename, fos);
        fos.close();
        client.disconnect();
    }

}

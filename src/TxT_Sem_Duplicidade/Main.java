/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TxT_Sem_Duplicidade;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 *
 * @author fernando.m.souza
 */
public class Main {

    public String file;
    public static String origem;
    public static String destino;

    public Main(String file) {
        this.file = file;
    }

    public void lerTexto() throws IOException {
        // VARIÁVEL ONDE SERÁ GUARDADA A LINHA PERCORRIDA
        String line = "";
        // Usamos os BufferedReader para abrir o arquivo.
        BufferedReader bf = new BufferedReader(new FileReader(this.file));
        // Instanciamos uma collection para guardar o nosso conteúdo
        Collection lista = new ArrayList();
        // variáveis para contagem de linhas (só pra ficar mais bonitinho, hehehe)
        int linhaT = 0;
        int linhaF = 0;

        /* AQUI SERÁ ONDE VAMOS PREENCHER NOSSA LISTA */
        while ((line = bf.readLine()) != null) {
            System.out.println("PERCORRENDO LINHA: " + linhaT++);
            // COMANDO PRA PREENCHER A LISTA "line"
            lista.add(line);
        }

        /* AQUI SERÁ O TRATAMENTO  
         * DA LISTA A SER SALVA
         * SEM REPETIÇÕES....
         */
        // O MÉTODO "LinkedHashSet" SERVE PARA NÃO EMBARALHAR A LISTA 
        // QUE NO CASO O COMANDO "HashSet" faz...
        Collection lista2 = new LinkedHashSet(lista);
        // CRIA O NOVO ARQUIVO ONDE SERÁ ARMAZENADA A LISTA
        File arquivo = new File(destino);

        // ABRE O ARQUIVO DE DESTINO PARA A GRAVAÇÃO
        BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));
        // PERCORRE TODA A NOVA LISTA
        for (Object item : lista2) {
            linhaF++;
            /* ESCREVE A LISTA NO ARQUIVO...
             * OBSERVE O TYPECAST FEITO POIS O 
             * FOR ESTÁ PASSANDO UM "Object"
             */
            bw.write((String) item);
            bw.newLine();
        }

        bw.flush();
        bw.close();

        System.out.println("VARREDURA COMPLETA...");
        System.out.println("TOTAL DE LINHAS NOVO ARQUIVO: " + linhaF);
    }

    public static void main(String[] args) {
        origem = args[0];
        destino = args[1];

        Main l = new Main(origem);

        try {
            l.lerTexto();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println("CONCLUIDO...");
    }
}

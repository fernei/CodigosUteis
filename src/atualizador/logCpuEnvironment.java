/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atualizador;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fernando.m.souza
 */
public class logCpuEnvironment {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(logCpuEnvironment());
    }

    public static String logCpuEnvironment() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.println("General information: ");
        pw.println("   Operating System: " + System.getProperty("os.name")
                + " " + System.getProperty("sun.os.patch.level"));
        pw.println("   Java Version: "
                + System.getProperty("java.version") + " - "
                + System.getProperty("java.vendor"));
        final long max = Runtime.getRuntime().maxMemory();
        final int maxMega = (int) ((float) max / 1048576);
        pw.println("   Heap Space (Espaço de Pilha): " + maxMega + "MB");
        pw.println("   User name: " + System.getProperty("user.name"));
        pw.println("   Language: " + System.getProperty("user.language"));
        pw.println("   Country: " + System.getProperty("user.country"));

        try (FileWriter arq = new FileWriter("C:\\SOC_BSC\\MachineConfiguration.txt")) {
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.print(sw.toString());
        } catch (IOException ex) {
            Logger.getLogger(logCpuEnvironment.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sw.toString();
    }

}

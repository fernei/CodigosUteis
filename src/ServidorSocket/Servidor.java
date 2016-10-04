/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServidorSocket;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fernando.m.souza
 */
public class Servidor {

    public static void main(String[] args) {
        Socket cliente = null;
        try {
            ServerSocket servidor = new ServerSocket(8020);
            final FileWriter arq = new FileWriter("C:\\Automacoes\\Log.txt");

            final PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.println("Servidor On-line");
            arq.flush();

            while (true) {

                cliente = servidor.accept();
                boolean flagStop = true;

                gravarArq.println("Nova conexão com o cliente "
                        + cliente.getInetAddress().getHostAddress()
                );
                arq.flush();

//                int delay = 300000; // delay;
//                int interval = 300000; // intervalo
//                Timer timer = new Timer();
//
//                timer.scheduleAtFixedRate(new TimerTask() {
//                    public void run() {
//
//                        try {
//                            gravarArq.println(agora() + " ESTOU ON-LINE");
//                            arq.flush();
//                        } catch (IOException ex) {
//                            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                }, delay, interval);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(cliente.getInputStream()));

//                String inputLine;
                do {
                    if (in.ready()) {
                        flagStop = false;
                        gravarArq.println(agora() + " --> " + in.readLine());

                        cliente.close();
                    }
                } while (flagStop);

                arq.flush();
            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                cliente.close();
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static String agora() {
        Date d = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        fmt.setCalendar(cal);
        String dateFormatted = fmt.format(cal.getTime());
        return dateFormatted;
    }
}

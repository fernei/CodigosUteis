package ServidorSocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fernando.m.souza
 */
public class CapitalizeServer {

    public static void main(String[] args) throws Exception {
        System.out.println("Server is running.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(8020);
        try {
            while (true) {
                new Capitalizer(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }
    }

    private static class Capitalizer extends Thread {

        private Socket socket;
        private int clientNumber;

        public Capitalizer(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client# " + clientNumber + " at " + socket);
        }

        public void run() {
            try {

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
//                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                //welcome message para o client.
//                out.println("Hello, you are client #" + clientNumber + ".");
//                out.println("Enter a line with only a period to quit\n");
                // Pega a menssagem enviada pelo cliente
                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals(".")) {
                        break;
                    }
//                    out.println(input.toUpperCase());
                    log(input.toString());
                }
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client# " + clientNumber + " closed");
            }
        }

        private void log(String message) {
            System.out.println(message);
            final FileWriter arq;
            try {
                arq = new FileWriter("C:\\Automacoes\\Log.txt", true);
                BufferedWriter bw = new BufferedWriter(arq);
                bw.write(message + "\r\n");
                bw.flush();
            } catch (IOException ex) {
                Logger.getLogger(CapitalizeServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}

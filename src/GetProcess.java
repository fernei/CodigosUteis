/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fernando.m.souza
 */
import java.io.*;
import java.util.*;

public class GetProcess {

    public static List listRunningProcesses() {
        List<String> processes = new ArrayList<String>();
        try {
            String line;
            Process p = Runtime.getRuntime().exec("tasklist.exe /nh");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (!line.trim().equals("")) {
// keep only the process name
                    processes.add(line.substring(0, line.indexOf("  ")));
                }

            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
        return processes;
    }

    public static void main(String[] args) {
        List<String> processes = listRunningProcesses();
        String result = "";

        // display the result
        Iterator<String> it = processes.iterator();
        int i = 0;
        while (it.hasNext()) {
//          System.out.println(it.next());
            result += it.next() + ",";
            i++;
            if (i == 10) {
                result += "\n";
                i = 0;
            }
        }
        msgBox("Running processes :\n  " + result + "\n Total No of Processes: " + processes.size());

    }

    public static void msgBox(String msg) {
        javax.swing.JOptionPane.showConfirmDialog((java.awt.Component) null, msg, "WindowsUtils",
                javax.swing.JOptionPane.DEFAULT_OPTION);
    }
}

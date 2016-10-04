/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.TreeMap;

/**
 *
 * @author otavio.c.ferreira
 */
public class MainFrame {

    private String hostIP; //Endereço para conexão com mainframe
    private String canal; //Trilha de conexão com mainframe
    private int timeout = 15; //Tempo padrão de espera pela resposta do mainframe
    private boolean statusConexao;

    public TreeMap mapConfigIp = new TreeMap(); //Mapa de configurações dos servidores SAC (arquivo de configuração)
    public TreeMap mapConfigCh = new TreeMap(); //Mapa de configurações das trilhas por SAC (arquivo de configuração)

    public String getHostIP() {
        return hostIP;
    }

    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isStatusConexao() {
        return statusConexao;
    }

    public void setStatusConexao(boolean statusConexao) {
        this.statusConexao = statusConexao;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TelasSAC;

import static Conection.ConexaoSAC.host;
import static Conection.ConexaoSAC.mainFrame;

/**
 *
 * @author fernando.m.souza
 */
public class BDEB extends Tela {

    /* CONSULTA DE AGENDAMENTO DE OS/BD - CGBDEB0A*/
    public static final String BDEB = "24,6,BDEB";

    public static final String posicaoDataHoraCriouAgendamento = "24,6,19";
    
    /*ESTATISTICA DA FILA DE EXECUCAO OS/BD - CGBDEB0A*/
    public void acessa() {
        host.setStringAt(this.BDEB);
        host.sendEnterKeyWait(mainFrame.getTimeout());
    }

    public void setTipoConsulta(String tipoconsulta) {
        host.setStringAt(9, 18, tipoconsulta);
    }

    public void setNumeroOsBd(String numero) {
        host.setStringAt(11, 20, numero);
    }

    
}

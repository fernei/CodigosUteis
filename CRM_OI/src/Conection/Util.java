package Conection;

import static Logger.LogTrace.log;
import static Logger.LogTrace.trace;
import Model.MainFrame;
import Model.TerminalWrapper;
import static java.lang.StrictMath.log;

import javax.swing.JOptionPane;


public class Util {

    MainFrame mainframe;
    TerminalWrapper host;
    private int quantTentativas;
    boolean erro;

    public Util(TerminalWrapper host, MainFrame mainframe) {
        this.host = host;
        this.mainframe = mainframe;
        quantTentativas = 0;
    }

    public boolean isErro() {
        return erro;
    }

    public void setErro(boolean erro) {
        this.erro = erro;
    }

//    
//    public void atualizaResultadoCRM(String resultado) {
//        if (registroAtual.getResultadoCRM() == null) {
//            registroAtual.setResultadoCRM(resultado);
//        } else {
//            registroAtual.setResultadoCRM(registroAtual.getResultadoCRM() + " | " + resultado);
//        }
//    }
}

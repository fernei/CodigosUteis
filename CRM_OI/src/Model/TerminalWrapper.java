package Model;

//import static Controller.LogTrace.//trace;
import static Logger.LogTrace.trace;
import View.TelaLog;
import pw3270.*;

public class TerminalWrapper {

    private TelaLog tela = new TelaLog();
    private final terminal host;
    private int rc;
    private boolean isTrace;

    public TerminalWrapper() {
        rc = 0;
        host = new terminal();
        isTrace = false;
    }

    public int getRc() {
        return rc;
    }

    public void setIsTrace(boolean isTrace) {
        this.isTrace = isTrace;
    }

    public boolean getIsTrace() {
        return this.isTrace;
    }

    public String getVersion() {
        String rc = host.getVersion();
        if (isTrace) {
            String texto = "[RC=" + rc + "] {getVersion}";
            trace.info(texto);
        }
        return rc;
    }

    public String getRevision() {
        String rc = host.getRevision();
        if (isTrace) {
            String texto = "[RC=" + rc + "] {getRevision}";
            trace.info(texto);
        }
        return rc;
    }

    public int Connect(String string, int i) {
        rc = host.Connect(string, i);
        if (isTrace) {
            String texto = "[RC=" + rc + "] {Connect(" + string + "," + i + ")}";
            trace.info(texto);
        }
        return rc;
    }

    public int Disconnect() {
        rc = host.Disconnect();
        if (isTrace) {
            String texto = "[RC=" + rc + "] {Disconnect}";
            trace.info(texto);
        }
        return rc;
    }

    public int getConnectionState() {
        rc = host.getConnectionState();
        if (isTrace) {
            String texto = "[RC=" + rc + "] {getConnectionState}";
            trace.info(texto);
        }
        return rc;
    }

    public boolean isConnected() {
        boolean rc = host.isConnected();
        if (isTrace) {
            String texto = "[RC=" + rc + "] {isConnected}";
            trace.info(texto);
        }
        return rc;
    }

    public boolean checarConexao() {
        boolean rc = host.isConnected();
        sendPFKey(3);
        int result = waitForTerminalReady(10);
        if (result == 0) {
            rc = true;
            trace.info("[SUCESSO] A ferramenta está conectada no SAC. RC: " + rc);
        } else {
            rc = false;
            trace.info("[SUCESSO] A ferramenta está desconectada do SAC. RC: " + rc);
        }
        if (isTrace) {
            String texto = "[RC=" + rc + "] {isConnected}";
            trace.info(texto);
        }
        return rc;
    }

    public boolean isTerminalReady() {
        boolean rc = host.isTerminalReady();
        if (isTrace) {
            String texto = "[RC=" + rc + "] {isTerminalReady}";
            trace.info(texto);
        }
        return rc;
    }

    public String getEncoding() {
        String rc = host.getEncoding();
        if (isTrace) {
            String texto = "[RC.length()=" + rc.length() + "] {getEncoding}";
            trace.info(texto);
        }
        return rc;
    }

    public String getScreenContentAt(int i, int i1, int i2) {
        String rc = host.getScreenContentAt(i, i1, i2);
        if (isTrace) {
            String texto = "[RC=" + rc + "] {getScreenContentAt(" + i + "," + i1 + "," + i2 + ")}";
            trace.info(texto);
        }
        return rc;
    }

    public String getScreenContentAt(String string) {

        String[] parts = string.split(",");
        String rc = host.getScreenContentAt(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));

        if (isTrace) {
            String texto = "[RC=" + rc + "] {getScreenContentAt(" + parts[0] + "," + parts[1] + "," + parts[2] + ")}";
            trace.info(texto);
        }
        return rc;
    }

    public String getScreenContent() {
        String rc = host.getScreenContent();
        if (isTrace) {
            String texto = "[RC.length()=" + rc.length() + "] {getScreenContent}";
            trace.info(texto);
        }
        return rc;
    }

    public void printScreenContent() {
        System.out.println(host.getScreenContent());
        trace.info("[\n" + host.getScreenContent() + "\n]");

        tela.jTextArea_Log.setText(host.getScreenContent());
        tela.setVisible(true);
    }

    /**
     * @param i Linha
     * @param i1 Coluna
     * @param string Texto procurado
     * @return Retorna TRUE caso o texto seja encontrado na posição informada
     */
    public boolean queryStringAt(int i, int i1, String string) {
        boolean rc = host.queryStringAt(i, i1, string);
        if (isTrace) {
            String texto = "[RC=" + rc + "] {queryStringAt(" + i + "," + i1 + "," + string + ")}";
            trace.info(texto);
        }
        return rc;
    }

    /**
     * @param i Linha
     * @param string Texto procurado
     * @return Retorna TRUE caso o texto seja encontrado na linha informada
     */
    public boolean queryStringAt(int i, String string) {
        String content = host.getScreenContentAt(i, 1, 80);
        boolean rc = content.contains(string);
        if (isTrace) {
            String texto = "[RC=" + rc + "] {queryStringAt(" + i + "," + string + ")}";
            trace.info(texto);
        }
        return rc;
    }

    public int sendEnterKey() {
        rc = host.sendEnterKey();
        if (isTrace) {
            String texto = "[RC=" + rc + "] {sendEnterKey}";
            trace.info(texto);
        }
        return rc;
    }

    public int sendEnterKeyWait(int secs) {

        if (isTrace) {
            printScreenContent();
        }

        rc = host.sendEnterKey();

        if (isTrace) {
            String texto = "[RC=" + rc + "] {sendEnterKey}";
            trace.info(texto);
        }

        rc = host.waitForTerminalReady(secs);

        if (isTrace) {
            String texto = "[RC=" + rc + "] {waitForTerminalReady(" + secs + ")}";
            trace.info(texto);
            printScreenContent();
        }

        printScreenContent();

        return rc;
    }

    public int sendPFKey(int i) {
        rc = host.sendPFKey(i);
        if (isTrace) {
            String texto = "[RC=" + rc + "] {sendPFKey(" + i + ")}";
            trace.info(texto);
        }
        return rc;
    }

    public int sendPFKeyWait(int i, int secs) {
        if (isTrace) {
            printScreenContent();
        }

        rc = host.sendPFKey(i);

        if (isTrace) {
            String texto = "[RC=" + rc + "] {sendPFKey(" + i + ")}";
            trace.info(texto);
        }

        rc = host.waitForTerminalReady(secs);

        if (isTrace) {
            String texto = "[RC=" + rc + "] {waitForTerminalReady(" + secs + ")}";
            trace.info(texto);
            printScreenContent();
        }

        printScreenContent();
        return rc;
    }

    public int setStringAt(int i, int i1, String string) {
        rc = host.setStringAt(i, i1, string);
        if (isTrace) {
            String texto = "[RC=" + rc + "] {setStringAt(" + i + "," + i1 + "," + string + ")}";
            trace.info(texto);
        }

        printScreenContent();

        return rc;
    }

    public int setStringAt(String string) {

        String[] parts = string.split(",");

        rc = host.setStringAt(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2]);
        if (isTrace) {
            String texto = "[RC=" + rc + "] {setStringAt(" + Integer.parseInt(parts[0]) + "," + Integer.parseInt(parts[1]) + "," + parts[2] + ")}";
            trace.info(texto);
        }

        printScreenContent();

        return rc;
    }

    public int setStringAt(String string, String valor) {

        String[] parts = string.split(",");

        rc = host.setStringAt(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), valor);
        if (isTrace) {
            String texto = "[RC=" + rc + "] {setStringAt(" + Integer.parseInt(parts[0]) + "," + Integer.parseInt(parts[1]) + "," + valor + ")}";
            trace.info(texto);
        }

        printScreenContent();

        return rc;
    }
    
    public int setStringAtPassword(int i, int i1, String string) {
        rc = host.setStringAt(i, i1, string);
        if (isTrace) {
            String texto = "[RC=" + rc + "] {setStringAtPassword(" + i + "," + i1 + ",******)}";
            trace.info(texto);
        }
        return rc;
    }

    public int wait(int i) {
        rc = host.wait(i);
        if (isTrace) {
            String texto = "[RC=" + rc + "] {wait(" + i + ")}";
            trace.info(texto);
        }
        return rc;
    }

    public int waitForTerminalReady(int i) {
        rc = host.waitForTerminalReady(i);
        if (isTrace) {
            String texto = "[RC=" + rc + "] {waitForTerminalReady(" + i + ")}";
            trace.info(texto);
        }
        return rc;
    }

    public int waitForTerminalReady_NoError(int i) {
        rc = host.waitForTerminalReady(i);
        if (isTrace) {
            String texto = "[RC=" + rc + "] {waitForTerminalReady(" + i + ")}";
            trace.info(texto);
        }
        return rc;
    }

    public int waitForStringAt(int i, int i1, String string, int i2) {
        rc = host.waitForStringAt(i, i1, string, i2);
        if (isTrace) {
            String texto = "[RC=" + rc + "] {waitForStringAt(" + i + "," + i1 + "," + string + "," + i2 + ")}";
            trace.info(texto);
        }
        return rc;
    }

    public int Connect(String string) {
        rc = host.Connect(string);
        if (isTrace) {
            String texto = "[RC=" + rc + "] {Connect(" + string + ")}";
            trace.info(texto);
        }
        return rc;
    }

}

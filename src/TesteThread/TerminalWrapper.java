package TesteThread;

import pw3270.*;

public class TerminalWrapper {

    private final terminal host;
    private int rc;
    private boolean exibeTelaSAC;
    private int nivelLog;
//    private boolean isTrace;

    public TerminalWrapper() {
        rc = 0;
        host = new terminal();
//        isTrace = false;
    }

    public int getRc() {
        return rc;
    }

//    public void setIsTrace(boolean isTrace) {
//        this.isTrace = isTrace;
//    }
//
//    public boolean getIsTrace() {
//        return this.isTrace;
//    }
    public String getVersion() {
        String rc = host.getVersion();
        String texto = "[RC=" + rc + "] {getVersion}";
        //log.debug(texto);

        return rc;
    }

    public String getRevision() {
        String rc = host.getRevision();
        String texto = "[RC=" + rc + "] {getRevision}";
        //log.debug(texto);
        return rc;
    }

    public int Connect(String string, int i) {
        rc = host.Connect(string, i);
        String texto = "[RC=" + rc + "] {Connect(" + string + "," + i + ")}";
        //log.debug(texto);
        return rc;
    }

    public int Disconnect() {
        rc = host.Disconnect();

        String texto = "[RC=" + rc + "] {Disconnect}";
        //log.debug(texto);

        return rc;
    }

    public int getConnectionState() {
        rc = host.getConnectionState();

        String texto = "[RC=" + rc + "] {getConnectionState}";
        //log.debug(texto);

        return rc;
    }

    public boolean isConnected() {
        boolean rc = host.isConnected();

        String texto = "[RC=" + rc + "] {isConnected}";
        //log.debug(texto);

        return rc;
    }

    public boolean checarConexao() {
        boolean rc = host.isConnected();
        sendPFKey(3);
        int result = waitForTerminalReady(10);
        if (result == 0) {
            rc = true;
            //log.info("[SUCESSO] A ferramenta está conectada no SAC. RC: " + rc);
        } else {
            rc = false;
            //log.info("[SUCESSO] A ferramenta está desconectada do SAC. RC: " + rc);
        }

        String texto = "[RC=" + rc + "] {isConnected}";
        //log.debug(texto);

        return rc;
    }

    public boolean isTerminalReady() {
        boolean rc = host.isTerminalReady();

        String texto = "[RC=" + rc + "] {isTerminalReady}";
        //log.debug(texto);

        return rc;
    }

    public String getEncoding() {
        String rc = host.getEncoding();
        String texto = "[RC.length()=" + rc.length() + "] {getEncoding}";
        //log.debug(texto);
        return rc;
    }

    public String getScreenContentAt(int i, int i1, int i2) {
        String rc = host.getScreenContentAt(i, i1, i2);

        String texto = "[RC=" + rc + "] {getScreenContentAt(" + i + "," + i1 + "," + i2 + ")}";
        //log.debug(texto);

        return rc;
    }

    public String getScreenContentAt(String string) {

        String[] parts = string.split(",");
        String rc = host.getScreenContentAt(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));

        String texto = "[RC=" + rc + "] {getScreenContentAt(" + parts[0] + "," + parts[1] + "," + parts[2] + ")}";
        //log.debug(texto);

        return rc;
    }

    public String getScreenContent() {
        String rc = host.getScreenContent();

        String texto = "[RC.length()=" + rc.length() + "] {getScreenContent}";
        //log.debug(texto);

        return rc;
    }

    public void printScreenContent() {
        System.out.println(host.getScreenContent());
        //log.debug("[\n" + host.getScreenContent() + "\n]");

    }

    /**
     * @param i Linha
     * @param i1 Coluna
     * @param string Texto procurado
     * @return Retorna TRUE caso o texto seja encontrado na posição informada
     */
    public boolean queryStringAt(int i, int i1, String string) {
        boolean rc = host.queryStringAt(i, i1, string);

        String texto = "[RC=" + rc + "] {queryStringAt(" + i + "," + i1 + "," + string + ")}";
        //log.debug(texto);

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

        String texto = "[RC=" + rc + "] {queryStringAt(" + i + "," + string + ")}";
        //log.debug(texto);

        return rc;
    }

    public int sendEnterKey() {
        rc = host.sendEnterKey();

        String texto = "[RC=" + rc + "] {sendEnterKey}";
        //log.debug(texto);

        return rc;
    }

    public int sendEnterKeyWait(int secs) {

        printScreenContent();

        rc = host.sendEnterKey();

        String texto = "[RC=" + rc + "] {sendEnterKey}";
        //log.debug(texto);

        rc = host.waitForTerminalReady(secs);

        String texto2 = "[RC=" + rc + "] {waitForTerminalReady(" + secs + ")}";
        //log.debug(texto2);
        printScreenContent();

//        printScreenContent();
        return rc;
    }

    public int sendPFKey(int i) {
        rc = host.sendPFKey(i);

        String texto = "[RC=" + rc + "] {sendPFKey(" + i + ")}";
        //log.debug(texto);

        return rc;
    }

    public int sendPFKeyWait(int i, int secs) {

        printScreenContent();

        rc = host.sendPFKey(i);

        String texto = "[RC=" + rc + "] {sendPFKey(" + i + ")}";
        //log.debug(texto);

        rc = host.waitForTerminalReady(secs);

        String texto2 = "[RC=" + rc + "] {waitForTerminalReady(" + secs + ")}";
        //log.debug(texto2);
        printScreenContent();

//        printScreenContent();
        return rc;
    }

    public int setStringAt(int i, int i1, String string) {
        rc = host.setStringAt(i, i1, string);
//        if (isTrace) {
        String texto = "[RC=" + rc + "] {setStringAt(" + i + "," + i1 + "," + string + ")}";
        //log.debug(texto);
//        }

        printScreenContent();

        return rc;
    }

    public int setStringAt(String string) {

        String[] parts = string.split(",");

        rc = host.setStringAt(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2]);
//        if (isTrace) {
        String texto = "[RC=" + rc + "] {setStringAt(" + Integer.parseInt(parts[0]) + "," + Integer.parseInt(parts[1]) + "," + parts[2] + ")}";
        //log.debug(texto);
//        }

        printScreenContent();

        return rc;
    }

    public int setStringAt(String string, String valor) {

        String[] parts = string.split(",");

        rc = host.setStringAt(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()), valor);
//        if (isTrace) {
        String texto = "[RC=" + rc + "] {setStringAt(" + Integer.parseInt(parts[0].trim()) + "," + Integer.parseInt(parts[1].trim()) + "," + valor + ")}";
        //log.debug(texto);
//        }

        printScreenContent();

        return rc;
    }

    public int setStringAt(String string, Integer valor) {

        String[] parts = string.split(",");

        rc = host.setStringAt(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()), valor.toString());
//        if (isTrace) {
        String texto = "[RC=" + rc + "] {setStringAt(" + Integer.parseInt(parts[0].trim()) + "," + Integer.parseInt(parts[1].trim()) + "," + valor.toString() + ")}";
        //log.debug(texto);
//        }

        printScreenContent();

        return rc;
    }

    public int setStringAtPassword(int i, int i1, String string) {
        rc = host.setStringAt(i, i1, string);
//        if (isTrace) {
        String texto = "[RC=" + rc + "] {setStringAtPassword(" + i + "," + i1 + ",******)}";
        //log.debug(texto);
//        }
        return rc;
    }

    public int wait(int i) {
        rc = host.wait(i);
//        if (isTrace) {
        String texto = "[RC=" + rc + "] {wait(" + i + ")}";
        //log.debug(texto);
//        }
        return rc;
    }

    public int waitForTerminalReady(int i) {
        rc = host.waitForTerminalReady(i);
//        if (isTrace) {
        String texto = "[RC=" + rc + "] {waitForTerminalReady(" + i + ")}";
        //log.debug(texto);
//        }
        return rc;
    }

    public int waitForTerminalReady_NoError(int i) {
        rc = host.waitForTerminalReady(i);
//        if (isTrace) {
        String texto = "[RC=" + rc + "] {waitForTerminalReady(" + i + ")}";
        //log.debug(texto);
//        }
        return rc;
    }

    public int waitForStringAt(int i, int i1, String string, int i2) {
        rc = host.waitForStringAt(i, i1, string, i2);
//        if (isTrace) {
        String texto = "[RC=" + rc + "] {waitForStringAt(" + i + "," + i1 + "," + string + "," + i2 + ")}";
        //log.debug(texto);
//        }
        return rc;
    }

    public int Connect(String string) {
        rc = host.Connect(string);
//        if (isTrace) {
        String texto = "[RC=" + rc + "] {Connect(" + string + ")}";
        //log.debug(texto);
//        }
        return rc;
    }

    public boolean isExibeTelaSAC() {
        return exibeTelaSAC;
    }

    public void setExibeTelaSAC(boolean exibeTelaSAC) {
        this.exibeTelaSAC = exibeTelaSAC;
    }

    public int getNivelLog() {
        return nivelLog;
    }

    public void setNivelLog(int nivelLog) {
        this.nivelLog = nivelLog;
    }

}

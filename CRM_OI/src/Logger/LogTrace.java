package Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
import org.apache.log4j.lf5.Log4JLogRecord;

/**
 *
 * @author otavio.c.ferreira
 */
public class LogTrace {

    private final String strLogFile;
    private final String strTraceFile;
    public static final Logger log = Logger.getLogger("Log");
    public static final Logger trace = Logger.getLogger("Trace");

    public LogTrace(String tipo) {
        SimpleDateFormat dateFormatLogN = new SimpleDateFormat("yyyyMMdd_HHmmss");
        strLogFile = "log/LOG_ENRIQUECEDOR_R2_" + tipo + "_" + dateFormatLogN.format(new java.util.Date(System.currentTimeMillis())) + ".log";
        strTraceFile = "log/TRACE_ENRIQUECEDOR_R2_" + tipo + "_" + dateFormatLogN.format(new java.util.Date(System.currentTimeMillis())) + ".log";

        FileAppender appenderLog, appenderTrace;
        
        try {
            appenderLog = new FileAppender(new PatternLayout("[%d{dd/MM/yyyy HH:mm:ss}] %m%n"), strLogFile, true);
            appenderTrace = new FileAppender(new PatternLayout("[%d{dd/MM/yyyy HH:mm:ss.SSS}] [%p] {%C.%M() %L} [%t] - %m%n"), strTraceFile, true);

            appenderLog.setThreshold(Priority.INFO);
            appenderTrace.setThreshold(Priority.DEBUG);

            log.addAppender(appenderLog);
            trace.addAppender(appenderTrace);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getTraceFile() {
        return strTraceFile;
    }

    public String getLogFile() {
        return strLogFile;
    }

    public void removeFile(String fileName) {
        File f = new File(fileName);
        f.delete();
    }

    public void removeAppenders() {
        log.removeAllAppenders();
        trace.removeAllAppenders();
    }
}

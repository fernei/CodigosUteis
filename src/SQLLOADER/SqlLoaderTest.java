package SQLLOADER;

/**
 *
 * @author fernando.m.souza
 */
public class SqlLoaderTest {

    public static void main(String[] args) {
        try {
            String sqlldrCmd = "sqlldr IVA_SOC/soc123@\\\"\\(DESCRIPTION=\\(ADDRESS=\\(PROTOCOL=TCP\\)\\(HOST=10.121.1.237\\)\\(PORT=1521\\)\\)\\(CONNECT_DATA=\\(SERVICE_NAME=CLIDBDEV\\)\\)\\)\\\" data='C:\\teste\\Relatorio_Portas_DSL.txt' control='C:\\teste\\loader_portas_dsl.ctl' discard='C:\\teste\\teste.dis' bad='C:\\teste\\teste.bad' log='C:\\teste\\teste.log' parallel=true silent=feedback";

            System.out.println("SQLLDR Started ....... ");
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(sqlldrCmd);
            System.out.println("SQLLDR Ended ........  ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

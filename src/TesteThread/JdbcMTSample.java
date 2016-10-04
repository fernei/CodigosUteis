package TesteThread;

/**
 *
 * @author fernando.m.souza
 */
import JDBCMultThreading.*;
import java.sql.*;

public class JdbcMTSample extends Thread {

    // Set default number of threads to 10
    private static int NUM_OF_THREADS = 10;

    int m_myId;

    static int c_nextId = 1;
    static Connection s_conn = null;

    synchronized static int getNextId() {
        return c_nextId++;
    }

    public static void main(String args[]) {
        try {
            NUM_OF_THREADS = 2;

            // Create the threads
            Thread[] threadList = new Thread[NUM_OF_THREADS];

            // spawn threads
            for (int i = 0; i < NUM_OF_THREADS; i++) {
                threadList[i] = new JdbcMTSample();
                threadList[i].start();
            }

            // wait for all threads to end
            for (int i = 0; i < NUM_OF_THREADS; i++) {
                threadList[i].join();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JdbcMTSample() {
        super();
        // Assign an ID to the thread
        m_myId = getNextId();
    }

    public void run() {
        UsuarioSAC user;
        if (m_myId == 1) {
            System.err.println("Para o usuário TR580524");
            user = new UsuarioSAC();
            user.setUsuario("TR580524");
            user.setSenhaDescriptografada("eewwqq11");
        } else {
            System.err.println("Para o usuário TR544073");
            user = new UsuarioSAC();
            user.setUsuario("TR544073");
            user.setSenhaDescriptografada("ot4vio06");
        }

        ConexaoSAC sac = new ConexaoSAC();
        sac.conectarSAC(user);

    }

}

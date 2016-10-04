package JDBCMultThreading;

/**
 *
 * @author fernando.m.souza
 */
//JDBC and Multi-Threading
//
//The Oracle JDBC drivers provide full support for programs that use multiple threads. The following example program uses the default Oracle employee database emp. The program creates a number of threads. Each thread opens a connection and sends a query to the database for the contents of the emp table. The program then displays the thread and the employee name and employee ID associated with it.
//
//Execute the program by entering:
//
//java JdbcMTSample [number_of_threads]
//
//on the command line where number_of_threads is the number of threads that you want to create. If you do not specify the number of threads, then the program creates 10 by default.
import java.sql.*;
//import oracle.jdbc.driver.OracleStatement;

public class JdbcMTSample extends Thread {

    // Set default number of threads to 10
    private static int NUM_OF_THREADS = 10;

    int m_myId;

    static int c_nextId = 1;
    static Connection s_conn = null;

    synchronized static int getNextId() {
        return c_nextId++;
    }

    String sTempDb = "C:\\Users\\fernando.m.souza\\Desktop\\monitoramento.s3db";
    String sJdbc = "jdbc:sqlite";
    String sDbUrl = sJdbc + ":" + sTempDb;

    public static void main(String args[]) {
        try {
            // Load the JDBC driver //
//            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());;;

            // If NoOfThreads is specified, then read it
            if (args.length > 1) {
                System.out.println("Error: Invalid Syntax. ");
                System.out.println("java JdbcMTSample [NoOfThreads]");
                System.exit(0);
            } else if (args.length == 1) {
                NUM_OF_THREADS = Integer.parseInt(args[0]);
            }

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
        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;

        try {
            // Get the connection
//            conn = DriverManager.getConnection("jdbc:oracle:oci8:@", "scott", "tiger");
            conn = DriverManager.getConnection(sDbUrl);
            // Create a Statement
            stmt = conn.createStatement();

            // Execute the Query
//            rs = stmt.executeQuery("SELECT * FROM emp");
            rs = stmt.executeQuery("SELECT response from dummy");

            // Loop through the results
            while (rs.next()) {
                System.out.println("Thread " + m_myId
                        + " Employee Id : " + rs.getInt(1)
                        + " Name : " + rs.getString(1));
            }

            // Close all the resources
            rs.close();
            stmt.close();
            if (conn != null) {
                conn.close();
            }
            System.out.println("Thread " + m_myId + " is finished. ");
        } catch (Exception e) {
            System.out.println("Thread " + m_myId + " got Exception: " + e);
            e.printStackTrace();
            return;
        }
    }

}

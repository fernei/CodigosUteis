/* Documentação de POOL de conexao do C3P0
### *****************************
### **** Propriedades do C3P0 ***
### *****************************
# Número de conexõs que o pool tentará adiquirir durante a inicialização. Deve
ser um número entre minPoolSize e maxPoolSize.
c3p0.initialPoolSize=5
# Número mínimo de conexões que o pool irá manter.
c3p0.minPoolSize=5
# Número máximo de conexões que o pool irá manter.
c3p0.maxPoolSize=15
# Segundos que uma Conexão será mantida no pool sem ser usada, antes de ser
descartada. Zero significa que a conexão nunca expira.
c3p0.maxIdleTime=60
# O tamanho do cache do C3P0 para PreparedStatements. Se o valor de ambos, maxStatements
e maxStatementsPerConnection, é zero, o cache será desabilitado. Se
maxStatements é zero mas maxStatementsPerConnection é um valor diferente de
zero, o cache será habilitado, mas sem um limite global, apenas com um limite
por conexão. maxStatements controla o número total de Statements dos quais é
feito cache, para todas as conexões. Se setado, deve ser um valor relativamente
alto, já que cada Conexão do pool terá um determinado número de statements
colocado em cache. Como um exemplo, considere quantos PreparedStatements
distintos são frequentemente usados na sua aplicação e multiplique esse
número por maxPoolSize para chegar num valor apropriado. Apesar do parâmetro
maxStatements ser o padrão para o JDBC controlar o cache de statements, usuários
podem achar mais intuitivo o uso do parâmetro maxStatementsPerConnection.
c3p0.maxStatements=0
# O número de PreparedStatements que o c3p0 irá colocar em cache, para cada conexão
do pool. Se ambos maxStatements e maxStatementsPerConnection são zero, o cache
de consultas ficará inativo. Se maxStatementsPerConnection é zero, mas maxStatements
é um valor não nulo, o cache de consultas será habilitado, e um limite global
imposto, mas por outro lado, não existirá nenhum limite individual por conexão.
Se setado, maxStatementsPerConnection deveria ser um valor, aproximado, do número
de PreparedStatements, distintos, que são frequentemente usados na sua aplicação
mais dois ou três, para que as consultas menos comuns não tirem as mais comuns
do cache. Apesar de maxStatements ser o parâmetro padrão em JDBC para controlar
o cache de consultas, o usuário pode achar mais intuitivo usar o parâmetro
maxStatementsPerConnection.
c3p0.maxStatementsPerConnection=10
# Determina quantas conexões por vez o c3p0 tenta adquirir quando o pool não tem
conexões inativas para serem usadas.
c3p0.acquireIncrement=1
# Se idleConnectionTestPeriod é um número maior que zero, c3p0 irá testar todas
as conexões inativas, que estão no pool e não fizeram o check-out, de X em X
segundos, onde X é o valor de idleConnectionTestPeriod.
c3p0.idleConnectionTestPeriod=60
# O número de milisegundos que um cliente chamando getConnection() irá esperar
por uma Conexão, via check-in ou uma nova conexão adquirida quando o pool estiver
esgotado. Zero siginifica esperar indefinidademento. Setar qualquer valor positivo
causará um time-out com uma SQLException depois de passada a quantidade especificada
de milisegundos.
c3p0.checkoutTimeout=5000
# Tempo em milisegundos que o c3p0 irá esperar entre tentivas de aquisição.
c3p0.acquireRetryDelay=1000
# Define quantas vezes o c3p0 tentará adquirir uma nova Conexão da base de dados
antes de desistir. Se esse valor é menor ou igual a zero, c3p0 tentará adquirir
uma nova conexão indefinidamente.
c3p0.acquireRetryAttempts=5
# Se true, um pooled DataSource declarará a si mesmo quebrado e ficará permanentemente
fechado caso não se consiga uma Conexão do banco depois de tentar acquireRetryAttempts
vezes. Se falso, o fracasso para obter uma Conexão jogará uma exceção, porém
o DataSource permanecerá valido, e tentará adquirir novamente, seguindo uma nova
chamada para getConnection().
c3p0.breakAfterAcquireFailure=false
# Número de segundos que conexões acima do limite minPoolSize deverão permanecer
inativas no pool antes de serem fechadas. Destinado para aplicações que desejam
reduzir agressivamente o número de conexões abertas, diminuindo o pool novamente
para minPoolSize, se, seguindo um pico, o nível de load diminui e Conexões não
são mais requeridas. Se maxIdleTime está definido, maxIdleTimeExcessConnections
deverá ser um valor menor para que o parâmetro tenho efeito. Zero significa que
não existirá nenhuma imposição, Conexões em excesso não serão mais fechadas.
c3p0.maxIdleTimeExcessConnections=30
# c3p0 é muito assíncrono. Operações JDBC lentas geralmente são executadas por
helper threads que não detém travas de fechamento. Separar essas operações atravéz
de múltiplas threads pode melhorar significativamente a performace, permitindo
que várias operações sejam executadas ao mesmo tempo.
c3p0.numHelperThreads=3
# Se true, e se unreturnedConnectionTimeout está definido com um valor positivo,
então o pool capturará a stack trace (via uma exceção) de todos os checkouts
de Conexões, e o stack trace será impresso quando o checkout de Conexões der
timeout. Este paramêtro é destinado para debug de aplicações com leak de
Conexões, isto é, aplicações que ocasionalmente falham na liberação/fechamento
de Conexões, ocasionando o crescimento do pool, e eventualmente na sua exaustão
(quando o pool atinge maxPoolSize com todas as suas conexões em uso e perdidas).
Este paramêtro deveria ser setado apenas para debugar a aplicação, já que capturar
o stack trace deixa mais o lento o precesso de check-out de Conexões.
c3p0.debugUnreturnedConnectionStackTraces=false
# Segundos. Se setado, quando uma aplicação realiza o check-out e falha na realização
do check-in [i.e. close()] de um Conexão, dentro de período de tempo especificado,
o pool irá, sem cerimonias, destruir a conexão [i.e. destroy()]. Isto permite
que aplicações com ocasionais leaks de conexão sobrevivam, ao invéz de exaurir
o pool. E Isto é uma pena. Zero significa sem timeout, aplicações deveriam fechar
suas próprias Conexões. Obviamente, se um valor positivo é definido, este valor
deve ser maior que o maior valor que uma conexão deveria permanecer em uso. Caso
contrário, o pool irá ocasionalmente matar conexões ativas, o que é ruim. Isto
basicamente é uma péssima idéia, porém é uma funcionalidade pedida com frequência.
Consertem suas aplicações para que não vazem Conexões!!! Use esta funcionalidade
temporariamente em combinação com debugUnreturnedConnectionStackTraces para
descobrir onde as conexões esão vazando!
c3p0.unreturnedConnectionTimeout=0
*
*/
package Conexao_BD;

/**
 *
 * @author fernando.m.souza
 */
/*IMPORTS PARA TRABALHAR COM A API C3P0*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/*CLASSE DE CONEXAO COM O BANCO DE DADOS -  TRABALHA DE FORMA DE POOL DE CONEXAO*/
public class PoolConexao {

    /*CONSTANTES COM SEU ATRIBUTOS*/
    protected static String JDBC_CLASS = "jdbc:oracle:thin:@";
    protected static String CLASS_DRIVER = "";
    protected static String NAME_DB = "CLIDB";
    protected static String USER_DB = "IVA_SOC";
    protected static String PASSWORD_DB = "soc123";
    protected static String IP_SERVER = "10.121.241.96";
    protected static String PORT_SERVER = "1521";
    protected static int MAX_POOL_SIZE = 10;
    protected static int MIN_POOL_SIZE = 1;
    protected static int ACQUIRE_INCREMENT = 10;
    protected static int ACQUIRE_RETRY_ATTEMPTS = 30;
    protected static int MAX_ID_LE_TIME = 60;
//    protected static String URL_DB = "" + JDBC_CLASS + "://" + IP_SERVER + ":" + PORT_SERVER + "/" + NAME_DB + "";
    protected static String URL_DB = "" + JDBC_CLASS + IP_SERVER + ":" + PORT_SERVER + "/" + NAME_DB + "";

    /*CONSTANTES DE MANIPULACAO DA BASE DE DADOS*/
    protected static Connection con = null;
    protected static Statement stmt = null;
    protected static PreparedStatement pstm = null;
    protected static ResultSet rs = null;

    /*CLASSE QUE TRABALHA JUNTO COM O SGDB*/
    protected static ComboPooledDataSource ds;

    /*METODO CONSTRUTOR DA CLASSE*/
    protected PoolConexao() {
    }

    /*METODO MAIN - USADO PARA TESTAR A CONEXAO*/
    public static void main(String[] argv) {
        try {
            //CONFIGURAÇÃO DEFAULT 
            //DataSource ds = DataSources.unpooledDataSource("jdbc:mysql://localhost/dados","user","#####");
           
            /*INSTANCIA DA CLASSE QUE VAI TRABALHAR OS ATRIBUTOS E CONFIG. PARA CONEXAO COM O SGBD*/
            ds = new ComboPooledDataSource("TESTE");
            
            /*SETANDO AS CONFIRUÇÕES*/
            ds.setDataSourceName(NAME_DB);
            ds.setUser(USER_DB);
            ds.setPassword(PASSWORD_DB);
            ds.setDriverClass(CLASS_DRIVER);
            ds.setJdbcUrl(URL_DB);
            ds.setMinPoolSize(MIN_POOL_SIZE);//Minimo de conexões paradas(standby)   
            ds.setAcquireIncrement(ACQUIRE_INCREMENT);//   
            ds.setMaxPoolSize(MAX_POOL_SIZE);//Maximo de conexões aberta  
            ds.setAcquireRetryAttempts(ACQUIRE_RETRY_ATTEMPTS);  
            ds.setMaxIdleTime(MAX_ID_LE_TIME); 
            /*PEQUENO SELECT PARA VERIFICAR A CONEXAO*/
            try {
                //ABRE A CONEXAO COM O SGBD
                con = getConexao();
                //CRIA UM STATEMENT PARA EXECUTAR A QUERY
                stmt = con.createStatement();
                //EXECUTA A QUERY
                rs = stmt.executeQuery("SELECT 1");
                //MOSTRA OS RESULTADOS
                while (rs.next()) {
                    System.out.println(rs.getString(1));
                }
            } catch (Exception ex) {
                /*CASO HAJA UMA EXCESSAO PARA CONECTAR  AQUI SERA MOSTRADA*/
                System.out.println("ERRO AO CONECTAR NO SGDB\n" + ex.getMessage());
                ex.printStackTrace();
            } finally {
                /*FINALIZANDO O TRABALHO NA BASE DE DADOS */
                /*FECHA O RESULTSET*/
                resultSetClose(rs);
                /*FECHA O STATEMENT*/
                statementClose(stmt);
                /*FECHA A CONEXAO*/
                connectionClose(con);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //conecta na base de dados

    public static Connection getConexao() throws Exception {
//    	//CONFIGURAÇÃO DEFAULT 
//	DataSource ds = DataSources.unpooledDataSource("jdbc:mysql://localhost/dados_freedom","root","mysqls3rv3r");
	
//    	/*INSTANCIA DA CLASSE QUE VAI TRABALHAR OS ATRIBUTOS E CONFIG. PARA CONEXAO COM O SGBD*/
        ds = new ComboPooledDataSource();
        
        /*SETANDO AS CONFIRUÇÕES*/
        ds.setDataSourceName(NAME_DB);
        ds.setUser(USER_DB);
        ds.setPassword(PASSWORD_DB);
        ds.setDriverClass(CLASS_DRIVER);
        ds.setJdbcUrl(URL_DB);
        ds.setMinPoolSize(MIN_POOL_SIZE);//Minimo de conexões paradas(standby)   
        ds.setAcquireIncrement(ACQUIRE_INCREMENT);//   
        ds.setMaxPoolSize(MAX_POOL_SIZE);//Maximo de conexões aberta  
        ds.setAcquireRetryAttempts(ACQUIRE_RETRY_ATTEMPTS);
        ds.setMaxIdleTime(MAX_ID_LE_TIME);
//        DataSource ds = DataSources.unpooledDataSource("jdbc:mysql://localhost/dados_freedom", "root", "mysqls3rv3r");
        /*PEQUENO SELECT PARA VERIFICAR A CONEXAO*/
        try {
            //ABRE A CONEXAO COM O SGBD
            con = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return con;
    }

    /*METODO PARA FECHAR O RESULTSET*/
    static void resultSetClose(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*METODO PARA FECHAR O STATEMENT*/
    static void statementClose(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*METODO PARA FECHAR O PREPARED_STATEMENT*/
    static void preparedStatmentClose(PreparedStatement pstm) {
        try {
            if (pstm != null) {
                pstm.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*METODO PARA FECHAR A CONEXAO*/
    static void connectionClose(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

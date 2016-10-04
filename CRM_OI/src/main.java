
import Conection.ConexaoSAC;
import Logger.LogTrace;
import Model.Banco;
import Model.MainFrame;
import Dao.Servidor;
import Model.UsuarioSAC;
import TelasSAC.ACB;
import static Logger.LogTrace.trace;
import static Logger.LogTrace.log;
import TelasSAC.BDEB;
import TelasSAC.IG;
import org.apache.log4j.Logger;

/**
 *
 * @author fernando.m.souza
 */
public class main {

    public static LogTrace logTrace;
    public static Banco banco;
    public static MainFrame mainframe;
    public static Servidor servidor;

    public static void main(String[] args) {

        logTrace = new LogTrace("Log");
//        trace.error("teste");
//        log.error("testelog");
//        banco = new Banco();
//        mainframe = new MainFrame();
//        servidor = new Servidor();
//
        ConexaoSAC sac = new ConexaoSAC();

        UsuarioSAC usrSAC = new UsuarioSAC();
        usrSAC.setUsuario("TR580524");
        usrSAC.setSenha("eewwqq11");

        sac.conectarSAC(usrSAC);
        sac.acessaUfNoSAC("GO");

        ACB acb = new ACB();
        acb.acessa();
        acb.setTerminal("33883079");
        acb.sendF4();
        acb.getTelefonesParaContato();
        
        
//        BDEB bdeb = new BDEB();
//        bdeb.acessa();
//        bdeb.setTipoConsulta("BD");
//        bdeb.setNumeroOsBd("37279605");
//        bdeb.sendEnter();
//        IG ig = new IG();
//        ig.acessa();
//        ig.selecionaEmpreiteira();
//        ig.selecDetEmpreiteira(7);
//        ig.selecEstFilaExeReparos();
//        ig.selecTelBraTelRf();
//        ig.selecReparoDetalhar(8);
//        ig.sendF5(ig.observacaoAssinante);
//        ig.getObservacaoAssinante();
//        ig.sendF7();
//        ig.sendF9();
//        ig.getDadFacAdslNroPortaAdsl();
        System.exit(0);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TesteThread;

//import static Logger.LogTrace.log;
//import Model.UsuarioSAC;
//import TelasSAC.Tela;
/**
 *
 * @author fernando.m.souza
 */
public class ConexaoSAC {

    private String ufAtual;
    private int quantTentativas = 0;
    private static TerminalWrapper host;
//    private UsuarioSAC usuarioSAC;
    private UsuarioSAC usuarioSAC;

    public ConexaoSAC() {
        host = new TerminalWrapper();
    }

    public void acessaUfNoSAC(String ufAtual) {

        this.ufAtual = ufAtual;

        if (!"APLICACOES ON LINE DISPONIVEIS".equals(host.getScreenContentAt(3, 2, 30).trim())) {
            host.sendPFKeyWait(10, 5);
        }

        String numUF = pegaNumeroUF();

        if (numUF == null) {
//            //log.info("Não existe aplicação para a UF " + ufAtual);
            return;
        }

        host.setStringAt(19, 29, numUF);
        host.sendEnterKeyWait(5);

        if (host.queryStringAt(1, 1, "EMS1334E Dados da aplicacao nao disponivel. Limpe a tela.")) {
            host.sendPFKeyWait(3, 5);
            host.sendPFKeyWait(10, 5);
            numUF = pegaNumeroUF();
            host.setStringAt(19, 29, numUF);
            host.sendEnterKeyWait(5);

            if (!host.queryStringAt(4, 36, "FILIAL " + this.ufAtual.toUpperCase())) {

                if (host.queryStringAt(1, 63, "Terminal")
                        && host.queryStringAt(12, 11, "ACESSO AS APLICACOES ON LINE ")) {
//                servidor.atualizaServidor();
                    conectarSAC();
                    numUF = pegaNumeroUF();
                    host.setStringAt(19, 29, numUF);
                    host.sendEnterKeyWait(5);

                } else {
                    host.printScreenContent();
//                host.setStringAt(19, 29, "lf " + numUF);
                    if (host.queryStringAt(19, 2, "Informe NUMERO ou CODIGO")) {
                        host.setStringAt(19, 29, "LOGOFF");
                        host.sendEnterKeyWait(5);
                        conectarSAC();
                        numUF = pegaNumeroUF();
                        host.setStringAt(19, 29, numUF);
                        host.sendEnterKeyWait(5);
                    } else {
                        host.printScreenContent();
                        host.Disconnect();
                        //trace.error("Erro ao acessar Aplicação no SAC.");
                        host.printScreenContent();
                        conectarSAC();
                        numUF = pegaNumeroUF();
                        host.setStringAt(19, 29, numUF);
                        host.sendEnterKeyWait(5);

                    }
                }
            }
        }

        if (host.queryStringAt(1, 1, "COMTAM0001-Z No program active")) {
            //host.printScreenContent();
            host.Disconnect();
            //log.info("Erro ao acessar Aplicação no SAC.");
            conectarSAC();
            //acessaUfNoSAC();
        }
    }

    public void conectarSAC(String usuario, String senha) {

        UsuarioSAC user = new UsuarioSAC();
        user.setUsuario(usuario);
        user.setSenhaDescriptografada(senha);
        this.usuarioSAC = user;
        conectarSAC();
    }

    public boolean conectarSAC() {

        if (host.isConnected()) {
            host.Disconnect();
        }

        host.Connect("TCSNET.BRASILTELECOM.COM.BR");
        host.waitForTerminalReady(5);

        if (host.isConnected()) {
            host.printScreenContent();
//            //log.info("[SUCESSO] Conectado em " + mainFrame.getHostIP()); //Gravando Log
        } else {
//            //log.info("[ERRO] Não foi possível conectar em: " + mainFrame.getHostIP()); //Gravando Log
            return false;
        }

        ////////// Tela de Login
        if (!host.queryStringAt(16, 13, "Matricula")) {
            host.printScreenContent();
//            //log.info("[ERRO] Comando não reconhecido: " + mainFrame.getCanal());
            return false;
        }

        host.setStringAt(16, 35, usuarioSAC.getUsuario());
        host.setStringAtPassword(17, 35, usuarioSAC.getSenhaDescriptografada());
        host.sendEnterKeyWait(5);

        if (host.queryStringAt(23, 11, "Senha nao confere")) {
            String erro = "Senha não confere para o usuário: " + usuarioSAC.getUsuario();
            host.printScreenContent();
//            //log.info("[ERRO] " + erro);
            return false;
        }

        if (host.queryStringAt(23, 11, "Usuario revogado")) {
//            String erro = "Usuário revogado: " + parametro.getUsuario();
//            JOptionPane.showMessageDialog(null, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            host.printScreenContent();
//            //log.info("[ERRO] " + erro);
            return false;
        }

        if (!host.queryStringAt(3, 2, "APLICACOES ON LINE DISPONIVEIS")) {
            host.setStringAt(19, 29, "LOGOFF");
            host.sendEnterKeyWait(5);
//            host.setStringAt(16, 35, parametro.getUsuario());
//            host.setStringAtPassword(17, 35, parametro.getSenha());
            host.sendEnterKeyWait(5);
        }

        String msgErroConnSAC = host.getScreenContentAt(23, 2, 60).trim();

        if (msgErroConnSAC.contains("logged")) {
//            //log.info("[ERRO] Dados incorretos de login: " + msgErroConnSAC);
            return conectarSAC();
        }

        if (msgErroConnSAC.contains("user")) {
//            //log.info("[ERRO] Dados incorretos de login: " + msgErroConnSAC);
            return false;
        }

        //Mensagens de erro no Login SAC
        if (msgErroConnSAC.contains("Wrong password")) {
//            //log.info("[ERRO] Dados incorretos de login: " + msgErroConnSAC);
            return false;
        }

        if (msgErroConnSAC.contains("not authorized")) {
            //log.info("[ERRO] Dados incorretos de login: " + msgErroConnSAC); //Gravando Log
            return false;
        }

        return true;
    }

    private String pegaNumeroUF() {
        int linha = 7, quantPaginacao = 0;

        while (!host.getScreenContentAt(linha, 9, 1).trim().isEmpty()) {
            if (ufAtual.equals(host.getScreenContentAt(linha, 9, 2).trim())) {
                return host.getScreenContentAt(linha, 4, 3).trim();
            } else if (linha == 16) {
                if (quantTentativas < 2) {
                    if (quantPaginacao < 2) {
                        host.sendPFKeyWait(8, 5);
                        linha = 6;
                        quantPaginacao++;
                    } else {
                        quantTentativas++;
                        quantPaginacao = 0;
                        conectarSAC();
                        return pegaNumeroUF();
                    }
                } else {
                    return null;
                }
            }

            linha++;
        }

        return null;
    }

    public void Logoff() {
        if ("CG00000A".equals(host.getScreenContentAt(4, 72, 8))) {
            host.sendPFKeyWait(10, 5);
            host.setStringAt(19, 29, "LOGOFF");
            host.sendEnterKeyWait(5);
//        } else if (new Tela().retornaMenuPrincipal()) {
//            host.sendPFKeyWait(10, 5);
//            host.setStringAt(19, 29, "LOGOFF");
//            host.sendEnterKeyWait(5);
        }
    }

    void conectarSAC(UsuarioSAC user) {
        this.usuarioSAC = user;
        conectarSAC();
    }

}

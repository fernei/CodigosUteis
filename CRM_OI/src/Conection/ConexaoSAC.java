/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conection;

import static Logger.LogTrace.log;
import Model.MainFrame;
import Model.TerminalWrapper;
import Model.UsuarioSAC;
import TelasSAC.Tela;
import javax.swing.JOptionPane;

/**
 *
 * @author fernando.m.souza
 */
public class ConexaoSAC {

    private String ufAtual;
    private int quantTentativas = 0;
    private UsuarioSAC usuarioSAC;

    public static TerminalWrapper host;
    public static MainFrame mainFrame;

    public ConexaoSAC() {
        host = new TerminalWrapper();
        mainFrame = new MainFrame();
    }

    public void acessaUfNoSAC(String ufAtual) {

        this.ufAtual = ufAtual;

        if (!"APLICACOES ON LINE DISPONIVEIS".equals(host.getScreenContentAt(3, 2, 30).trim())) {
            host.sendPFKeyWait(10, this.mainFrame.getTimeout());
        }

        String numUF = pegaNumeroUF();

        if (numUF == null) {
            //trace.warn("Não existe aplicação para a UF " + ufAtual);
            return;
        }

        host.setStringAt(19, 29, numUF);
        host.sendEnterKeyWait(this.mainFrame.getTimeout());

        if (host.queryStringAt(1, 1, "EMS1334E Dados da aplicacao nao disponivel. Limpe a tela.")) {
            host.sendPFKeyWait(3, this.mainFrame.getTimeout());
            host.sendPFKeyWait(10, this.mainFrame.getTimeout());
            numUF = pegaNumeroUF();
            host.setStringAt(19, 29, numUF);
            host.sendEnterKeyWait(this.mainFrame.getTimeout());

            if (!host.queryStringAt(4, 36, "FILIAL " + this.ufAtual.toUpperCase())) {

                if (host.queryStringAt(1, 63, "Terminal")
                        && host.queryStringAt(12, 11, "ACESSO AS APLICACOES ON LINE ")) {
//                servidor.atualizaServidor();
                    conectarSAC(usuarioSAC);
                    numUF = pegaNumeroUF();
                    host.setStringAt(19, 29, numUF);
                    host.sendEnterKeyWait(this.mainFrame.getTimeout());

                } else {
                    host.printScreenContent();
//                host.setStringAt(19, 29, "lf " + numUF);
                    if (host.queryStringAt(19, 2, "Informe NUMERO ou CODIGO")) {
                        host.setStringAt(19, 29, "LOGOFF");
                        host.sendEnterKeyWait(this.mainFrame.getTimeout());
                        conectarSAC(usuarioSAC);
                        numUF = pegaNumeroUF();
                        host.setStringAt(19, 29, numUF);
                        host.sendEnterKeyWait(this.mainFrame.getTimeout());
                    } else {
                        host.printScreenContent();
                        host.Disconnect();
                        //trace.error("Erro ao acessar Aplicação no SAC.");
                        host.printScreenContent();
                        conectarSAC(usuarioSAC);
                        numUF = pegaNumeroUF();
                        host.setStringAt(19, 29, numUF);
                        host.sendEnterKeyWait(this.mainFrame.getTimeout());

                    }
                }
            }
        }

        if (host.queryStringAt(1, 1, "COMTAM0001-Z No program active")) {
            //host.printScreenContent();
            host.Disconnect();
            //trace.error("Erro ao acessar Aplicação no SAC.");
            conectarSAC(usuarioSAC);
            //acessaUfNoSAC();
        }
    }

    public boolean conectarSAC(String usuario, String senha) {

        UsuarioSAC user = new UsuarioSAC();
        user.setUsuario(usuario);
        user.setSenha(senha);
        this.usuarioSAC = user;
        return conectarSAC(usuarioSAC);
    }

    public boolean conectarSAC(UsuarioSAC usuarioSAC) {
        this.usuarioSAC = usuarioSAC;
        if (host.isConnected()) {
            host.Disconnect();
        }

        if ("".equals(this.mainFrame.getHostIP()) || this.mainFrame.getHostIP() == null) {
            this.mainFrame.setHostIP("10.32.102.62");
        } else {
//            servidor.atualizaServidor();
        }

        host.Connect(this.mainFrame.getHostIP());
        host.waitForTerminalReady(this.mainFrame.getTimeout());

        if (host.isConnected()) {
            host.printScreenContent();
//            log.info("[SUCESSO] Conectado em " + this.mainFrame.getHostIP()); //Gravando Log
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível conectar no servidor!\n" + this.mainFrame.getHostIP(), "Erro", JOptionPane.ERROR_MESSAGE);
            log.info("[ERRO] Não foi possível conectar em: " + this.mainFrame.getHostIP()); //Gravando Log
            return false;
        }

        ////////// Tela de Login
        if (!host.queryStringAt(16, 13, "Matricula")) {
            JOptionPane.showMessageDialog(null, "Comando não reconhecido!\n" + this.mainFrame.getCanal(), "Erro", JOptionPane.ERROR_MESSAGE);
            host.printScreenContent();
            log.info("[ERRO] Comando não reconhecido: " + this.mainFrame.getCanal()); //Gravando Log
            return false;
        }

        host.setStringAt(16, 35, usuarioSAC.getUsuario());
        host.setStringAtPassword(17, 35, usuarioSAC.getSenha());
        host.sendEnterKeyWait(this.mainFrame.getTimeout());

        if (host.queryStringAt(23, 11, "Senha nao confere")) {
//            String erro = "Senha não confere para o usuário: " + parametro.getUsuario();
//            JOptionPane.showMessageDialog(null, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            host.printScreenContent();
//            log.info("[ERRO] " + erro);
            return false;
        }

        if (host.queryStringAt(23, 11, "Usuario revogado")) {
//            String erro = "Usuário revogado: " + parametro.getUsuario();
//            JOptionPane.showMessageDialog(null, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            host.printScreenContent();
//            log.info("[ERRO] " + erro);
            return false;
        }

        if (!host.queryStringAt(3, 2, "APLICACOES ON LINE DISPONIVEIS")) {
            host.setStringAt(19, 29, "LOGOFF");
            host.sendEnterKeyWait(this.mainFrame.getTimeout());
//            host.setStringAt(16, 35, parametro.getUsuario());
//            host.setStringAtPassword(17, 35, parametro.getSenha());
            host.sendEnterKeyWait(this.mainFrame.getTimeout());
        }

        String msgErroConnSAC = host.getScreenContentAt(23, 2, 60).trim();

        if (msgErroConnSAC.contains("logged")) {
            JOptionPane.showMessageDialog(null, "Esse usuário já se encontra logado no SAC.\n\nFavor deslogar do SAC e clicar em OK para tentar novamente.", "Usuário Logado", JOptionPane.INFORMATION_MESSAGE);
            log.info("[ERRO] Dados incorretos de login: " + msgErroConnSAC); //Gravando Log
            return conectarSAC(usuarioSAC);
        }

        if (msgErroConnSAC.contains("user")) {
            JOptionPane.showMessageDialog(null, "Dados incorretos de login do SAC:\n" + msgErroConnSAC, "Erro", JOptionPane.ERROR_MESSAGE);
            log.info("[ERRO] Dados incorretos de login: " + msgErroConnSAC); //Gravando Log
            return false;
        }

        //Mensagens de erro no Login SAC
        if (msgErroConnSAC.contains("Wrong password")) {
            JOptionPane.showMessageDialog(null, "Dados incorretos de login do SAC:\n" + msgErroConnSAC, "Erro", JOptionPane.ERROR_MESSAGE);
            log.info("[ERRO] Dados incorretos de login: " + msgErroConnSAC); //Gravando Log
            return false;
        }

        if (msgErroConnSAC.contains("not authorized")) {
            JOptionPane.showMessageDialog(null, "Usuário não autorizado a acessa esta chave:\n" + msgErroConnSAC, "Erro", JOptionPane.ERROR_MESSAGE);
            log.info("[ERRO] Dados incorretos de login: " + msgErroConnSAC); //Gravando Log
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
                        host.sendPFKeyWait(8, this.mainFrame.getTimeout());
                        linha = 6;
                        quantPaginacao++;
                    } else {
                        quantTentativas++;
                        quantPaginacao = 0;
                        conectarSAC(usuarioSAC);
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
            host.sendPFKeyWait(10, this.mainFrame.getTimeout());
            host.setStringAt(19, 29, "LOGOFF");
            host.sendEnterKeyWait(this.mainFrame.getTimeout());
        } else if (new Tela().retornaMenuPrincipal()) {
            host.sendPFKeyWait(10, this.mainFrame.getTimeout());
            host.setStringAt(19, 29, "LOGOFF");
            host.sendEnterKeyWait(this.mainFrame.getTimeout());
        }
    }

//    public boolean retornaMenuPrincipal() {
//        if (host.isConnected()) {
//            if (host.queryStringAt(4, 72, "CG00000A")) {
//                //host.printScreenContent();
//                return true;
//            } else {
////                trace.info("Retornando à tela inicial da Funcionalidade");
//                int cont = 1;
//                while (!host.queryStringAt(4, 72, "CG00000A")) {
////                    if (host.queryStringAt(3, 2, "ACESSO AS APLICACOES ON LINE") && host.queryStringAt(19, 2, "Informe NUMERO ou CODIGO")) {
////                        host.setStringAt(19, 29, "LOGOFF");
////                        host.sendEnterKeyWait(this.mainFrame.getTimeout());
////                    }
//
//                    host.sendPFKey(3);
//                    host.waitForTerminalReady(this.mainFrame.getTimeout());
//                    if (!tela.popupGeraisSAC()) {
////                        util.setErro(true);
////                        log.error("[ERRO] PopUp não mapeado para o terminal.: " + registroAtual.getTerminal());
////                        trace.error("PopUp não mapeado para o terminal.: " + registroAtual.getTerminal());
//                    }
//
//                    if (host.queryStringAt(24, 2, "CMD")) {
//                        host.setStringAt(24, 6, "0");
//                        host.sendEnterKey();
//                        host.waitForTerminalReady(this.mainFrame.getTimeout());
//                        if (host.queryStringAt(15, "COMANDO NAO CADASTRADO")) {
//                            host.sendEnterKey();
//                            host.waitForTerminalReady(this.mainFrame.getTimeout());
//                        }
//
//                    }
//
//                    if (!tela.popupGeraisSAC()) {
////                        util.setErro(true);
////                        log.error("[ERRO] PopUp não mapeado para o terminal.: " + registroAtual.getTerminal());
////                        trace.error("PopUp não mapeado para o terminal.: " + registroAtual.getTerminal());
//                    }
//                    if (host.queryStringAt(1, 63, "Terminal") && host.queryStringAt(12, 11, "ACESSO AS APLICACOES ON LINE ")) {
////                        servidor.atualizaServidor();
//                        conectarSAC(usuarioSAC);
//                        acessaUfNoSAC();
//                        break;
//                    }
//
//                    if (!tela.popupGeraisSAC()) {
////                        util.setErro(true);;
////                        log.error("[ERRO] PopUp não mapeado para o terminal.: " + registroAtual.getTerminal());
////                        trace.error("PopUp não mapeado para o terminal.: " + registroAtual.getTerminal());
//                    };
//
//                    // Diferença pro navegaMenuPrincipal()
//                    if (!host.queryStringAt(4, 72, "CG00000A")) {
//                        if (host.queryStringAt(24, 2, "CMD")) {
//                            String telaAtual = host.getScreenContentAt(2, 70, 10).trim();
//
//                            if (!telaAtual.isEmpty()) {
//                                host.setStringAt(24, 6, telaAtual.substring(0, 1));
//                                host.sendEnterKey();
//                                host.waitForTerminalReady(this.mainFrame.getTimeout());
//                                //host.printScreenContent();
//                            }
//                        }
//                    }
//                    // Fim diferença
//
//                    cont++;
//
//                    if (cont > 10 || host.getScreenContent().trim().isEmpty()) {
//                        host.Disconnect();
//                        boolean conectado = conectarSAC(usuarioSAC);
//                        acessaUfNoSAC();
//                        return conectado;
//                    }
//                }
//
//                return true;
//            }
//        } else {
//            conectarSAC(usuarioSAC);
//        }
//
//        return false;
//    }
}

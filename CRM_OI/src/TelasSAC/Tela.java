/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TelasSAC;

import static Conection.ConexaoSAC.host;
import static Conection.ConexaoSAC.mainFrame;
import static Logger.LogTrace.log;
import static Logger.LogTrace.trace;
import Model.MainFrame;
import Model.RegistroBase;
import Model.TerminalWrapper;
import javax.swing.JOptionPane;

/**
 *
 * @author fernando.m.souza
 */
public class Tela {

    private final RegistroBase registroAtual;
    private int quantTentativas;

    public Tela() {
        this.quantTentativas = 0;
        this.registroAtual = null;
    }

    public boolean retornaMenuPrincipal() {
        if (host.isConnected()) {
            if (host.queryStringAt(4, 72, "CG00000A")) {
                //host.printScreenContent();
                return true;
            } else {
                trace.info("Retornando à tela inicial da Funcionalidade");
                int cont = 1;
                while (!host.queryStringAt(4, 72, "CG00000A")) {
//                    if (host.queryStringAt(3, 2, "ACESSO AS APLICACOES ON LINE") && host.queryStringAt(19, 2, "Informe NUMERO ou CODIGO")) {
//                        host.setStringAt(19, 29, "LOGOFF");
//                        host.sendEnterKeyWait(mainFrame.getTimeout());
//                    }

                    host.sendPFKey(3);
                    host.waitForTerminalReady(mainFrame.getTimeout());
                    if (!popupGeraisSAC()) {
//                        setErro(true);
//                        log.error("[ERRO] PopUp não mapeado para o terminal.: " + registroAtual.getTelefoneApurado() + " e Os nº.:" + registroAtual.getNumOS());
//                        trace.error("PopUp não mapeado para o terminal.: " + registroAtual.getTelefoneApurado() + " e Os nº.:" + registroAtual.getNumOS());
                    }

                    if (host.queryStringAt(24, 2, "CMD")) {
                        host.setStringAt(24, 6, "0");
                        host.sendEnterKey();
                        host.waitForTerminalReady(mainFrame.getTimeout());
                        if (host.queryStringAt(15, "COMANDO NAO CADASTRADO")) {
                            host.sendEnterKey();
                            host.waitForTerminalReady(mainFrame.getTimeout());
                        }
                    }

                    if (!popupGeraisSAC()) {
//                       setErro(true);
//                        log.error("[ERRO] PopUp não mapeado para o terminal.: " + registroAtual.getTelefoneApurado() + " e Os nº.:" + registroAtual.getNumOS());
//                        trace.error("PopUp não mapeado para o terminal.: " + registroAtual.getTelefoneApurado() + " e Os nº.:" + registroAtual.getNumOS());
                    }
                    if (host.queryStringAt(1, 63, "Terminal") && host.queryStringAt(12, 11, "ACESSO AS APLICACOES ON LINE ")) {
//                        servidor.atualizaServidor();
                        conectarSAC();
                        acessaUfNoSAC();
                        break;
                    }

                    if (popupGeraisSAC()) {
//                        setErro(true);
//                        log.error("[ERRO] PopUp não mapeado para o terminal.: " + registroAtual.getTelefoneApurado() + " e Os nº.:" + registroAtual.getNumOS());
//                        trace.error("PopUp não mapeado para o terminal.: " + registroAtual.getTelefoneApurado() + " e Os nº.:" + registroAtual.getNumOS());
                    };

                    // Diferença pro navegaMenuPrincipal()
                    if (!host.queryStringAt(4, 72, "CG00000A")) {
                        if (host.queryStringAt(24, 2, "CMD")) {
                            String telaAtual = host.getScreenContentAt(2, 70, 10).trim();

                            if (!telaAtual.isEmpty()) {
                                host.setStringAt(24, 6, telaAtual.substring(0, 1));
                                host.sendEnterKey();
                                host.waitForTerminalReady(mainFrame.getTimeout());
                                //host.printScreenContent();
                            }
                        }
                    }
                    // Fim diferença

                    cont++;

                    if (cont > 10 || host.getScreenContent().trim().isEmpty()) {
                        host.Disconnect();
                        boolean conectado = conectarSAC();
                        acessaUfNoSAC();
                        return conectado;
                    }
                }

                return true;
            }
        } else {
            return conectarSAC();
        }
    }

    public boolean popupGeraisSAC() {
        boolean ret;
        int countLoop = 0;
        do {
            ret = false;

//            if (!tempo.verificaTempoExecucao("3")) {
//                return false;
//            }
            //*** Popup SELEÇÃO DE DDD ***//
            if (host.queryStringAt(4, 71, "CGS2225A") || host.queryStringAt(4, 71, "CGS1225A")) {
                if (!registroAtual.getDdd().isEmpty()) {
                    int linha_ddd = 6;
                    while (!host.queryStringAt(linha_ddd, 35, registroAtual.getDdd()) && linha_ddd < 21) {
                        linha_ddd++;
                    }
                    if (host.queryStringAt(linha_ddd, 35, registroAtual.getDdd())) {
                        host.setStringAt(linha_ddd, 16, "X");
                        host.printScreenContent();
                        host.sendEnterKey();
                        host.waitForTerminalReady(mainFrame.getTimeout());
                        host.printScreenContent();
                    } else {
                        return false;
                    }
                    ret = true;
                } else if (!registroAtual.getLocalidade().isEmpty()) {
                    int linha_ddd = 6;
                    while (!host.queryStringAt(linha_ddd, 39, registroAtual.getLocalidade()) && linha_ddd < 21) {
                        linha_ddd++;
                    }
                    if (host.queryStringAt(linha_ddd, 39, registroAtual.getLocalidade())) {
                        host.setStringAt(linha_ddd, 16, "X");
                        host.printScreenContent();
                        host.sendEnterKey();
                        host.waitForTerminalReady(mainFrame.getTimeout());
                        host.printScreenContent();
                    } else {
                        return false;
                    }
                    ret = true;
                } else {
                    return false;
                }
            }
            // *** Popup ESTE TELEFONE É SIGILOSO - MATRICULA REGISTRADA ***
            if (host.queryStringAt(12, 58, "CGS0389")) {
                // JOptionPane.showMessageDialog(null, "Este telefone é sigiloso, caso prossiga com a transação a sua matrícula ficará registrada. Digite S(Sim) ou N(Não). " , "AVISO", JOptionPane.YES_NO_OPTION) ;
                host.setStringAt(23, 69, "S");
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                host.printScreenContent();
                ret = true;
            }
            // *** Popup E-BILLING ***
            if (host.queryStringAt(9, 41, "E-BILLING")) {
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
//                tela.atualizarTela(host.getScreenContent());
                ret = true;
            }

            if (host.queryStringAt(13, 71, "CG00020")) {
//                registroAtual.setFlagOCT("*** OI Conta Total ***");
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
//                tela.atualizarTela(host.getScreenContent());
                ret = true;
            }
            if (host.queryStringAt(16, "OI TOTAL") || host.queryStringAt(16, "Oi Total")) {
//                registroAtual.setFlagOCT("*** OI Total ***");
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
//                tela.atualizarTela(host.getScreenContent());
                ret = true;
            }
            // *** Popup ACL de ATENCAO *** - COBRANÇA APÓS PRIMEIRO ACESSO
            if (host.queryStringAt(15, 30, "PRIMEIRO ACESSO :")) {
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                host.printScreenContent();
                ret = true;
            }
            // *** Popup AA de ATENCAO *** - COBRANÇA APÓS PRIMEIRO ACESSO
            if (host.queryStringAt(15, 33, "PRIMEIRO ACESSO :")) {
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
//                tela.atualizarTela(host.getScreenContent());
                ret = true;
            }

            if (host.queryStringAt(15, 46, "CG00020")) {
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
//                tela.atualizarTela(host.getScreenContent());
//                retornaTelaInicialSAC();
                ret = true;
            }
            // popup PROBLEMAS  NO  SISTEMA  SAC
            if (host.queryStringAt(19, 51, "ERROR =")) {
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
//                atualizaResultadoCRM("Problemas no sistema SAC, contatar a equipe de sistemas!");
//                tela.atualizarTela(host.getScreenContent());
                ret = true;
            }
            if (host.queryStringAt(18, 52, "Documento não encontrado")) {
//                util.setErro(true);
//                log.error("[ERRO] {popupGeraisSAC} Documento nao encontrado!. " + registroAtual.getNumOS());
//                trace.error("Documento nao encontrado!. " + registroAtual.getNumOS());
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(19, 29, "TERMINAL/CONTRATO")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(15, 20, "EXISTE OI CONTA TOTAL")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(14, 35, "TERMINAL OI CONTA TOTAL")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(11, 36, "A T E N C A O ")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(11, "TERMINAL INFORMADO POSSUI GECO.")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(11, 56, "A T E N C A O")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(14, 10, "NRO-OS")) {
                int linha = 15;
                while (!host.getScreenContentAt(linha, 8, 1).trim().isEmpty()) {
                    if ("PENDENTE".equals(host.getScreenContentAt(linha, 71, 8).trim())) {
                        host.setStringAt(linha, 6, "X");
                        host.sendEnterKeyWait(mainFrame.getTimeout());
                        break;
                    } else if ((linha == 19)) {
                        host.sendPFKeyWait(8, mainFrame.getTimeout());
                    }

                    linha += 2;
                }
                ret = true;
            }

            if (host.queryStringAt(12, 36, "A  T  E  N  C  A  O")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(13, 36, "*** A L E R T A ***")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(15, 30, "PRIMEIRO ACESSO")) {
                host.sendPFKey(3);
                host.waitForTerminalReady(mainFrame.getTimeout());
                host.printScreenContent();
                ret = true;
            }

            if (host.queryStringAt(13, 30, "A T E N C A O")) {
//                host.setStringAt(15, 52, "S");
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                host.printScreenContent();
                ret = true;
            }

//            if (host.queryStringAt(13, 30, "A T E N C A O")) {
//                //host.printScreenContent();
//                host.sendPFKey(3);
//                host.waitForTerminalReady(mainFrame.getTimeout());
//                ret = true;
//            }
            if (host.queryStringAt(12, 68, "CG00200E")) {
                //host.printScreenContent();
                host.sendPFKey(3);
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            // BD COM CAUSA COMUM
            // DESEJA DETALHAR? _ (S/N)
            // TECLE < ENTER >.
            if (host.queryStringAt(12, 32, "BD COM CAUSA COMUM") || host.queryStringAt(14, 29, "DESEJA DETALHAR")) {
                //host.printScreenContent();
                host.setStringAt(14, 46, "S");
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            // GRAU DE PRIORIDADE
            // DEFEITO
            // INFO CALL CENTER
            if (host.queryStringAt(21, 5, "PF3 - VOLTAR") || host.queryStringAt(14, 4, "INFO CALL CENTER")) {
                //host.printScreenContent();
                host.sendPFKey(3);
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(15, 45, "ESTE TELEFONE EH SIGILOSO")) {
                //host.printScreenContent();
                host.setStringAt(23, 69, "S");
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(12, "'BD COM CAUSA COMUM'")) {
                //host.printScreenContent();
                host.setStringAt(14, 46, "N");
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(21, 33, "O cliente participa do Programa Relacionamento")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(8, 24, "OCORRENCIAS DE BLOQUEIO")) {
                //host.printScreenContent();
                host.sendPFKey(3);
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(20, 21, "ESTE CLIENTE PASSOU")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;;
            }

            if (host.queryStringAt(15, 20, "Codigo do Plano")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;;
            }

            if (host.queryStringAt(14, 35, "A T E N C A O")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;;
            }

            //*** Tratamento do popup de NA ULTIMA HORA FORAM REGISTRADOS BD ***//
            if (host.queryStringAt(13, 35, "NA ULTIMA HORA FORAM REGISTRADOS BD")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;;
            }

            if (host.queryStringAt(10, 35, "A T E N C A O") && host.queryStringAt(13, 13, "NAO ESTA MAIS")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                return false;
            }

            //*** Tratamento do popup MENSAGEM - Existem servicos nesta ordem para serem executados
            if (host.queryStringAt(11, 15, "Existem servicos nesta ordem para serem executados")) {
                //host.printScreenContent();
                JOptionPane.showMessageDialog(null, "Existem serviços nesta ordem para serem executados.\nA OS está em Interoperação, favor informar técnico!", "Atenção", JOptionPane.WARNING_MESSAGE);
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                log.info("[ERRO] {clsUtil.popupGerais} Existem servicos nesta ordem para serem executados.");
                return false;
            }

            if (host.queryStringAt(14, 11, "MORE")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                return false;
            }

            if (host.queryStringAt(18, 52, "Documento não encontrado")) {
//                util.setErro(true);
                log.error("[ERRO] {popupGeraisSAC} Documento nao encontrado!. " + registroAtual.getNumOS());
                trace.error("Documento nao encontrado!. " + registroAtual.getNumOS());
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            // EXISTE SOLICITACAO DE REPARO                                
            // ABERTA PARA ESTE TELEFONE
            if (host.queryStringAt(17, 28, "A T E N C A O") || host.queryStringAt(19, 21, "EXISTE")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(17, 28, "A T E N C A O !")) {
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(9, 58, "A T E N C A O")) {
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(10, 34, "A T E N C A O")) {
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(10, 34, "A T E N C A O !")) { // SERVIÇO TRAMITANDO SEM FACILIDADE DESIGNADA.
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(7, 38, "A T E N C A O")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(7, 38, "ATENCAO")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(2, 33, "A T E N C A O")) {
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(13, 8, "NAO ENCONTROU FACILIDADE PARA O TERMINAL")) {
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(1, 2, "CONTRATO INVALIDO.")) {
                retornaMenuPrincipal();
                return false;
            }

            //*** Tratamento do popup *** S V O I *** //
            if (host.queryStringAt(13, 36, "*** S V O I ***")) {
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(13, 40, "*** S V O I ***")) {
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(17, 7, "PROG")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(13, 36, "Este Telefone ja esta")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
//                JOptionPane.showMessageDialog(null, "Este Telefone ja esta sendo detalhado por outra pessoa!", "Atenção", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (host.queryStringAt(13, 36, "Tipo de Utilizacao")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                return false;
            }

            if (host.queryStringAt(17, "PF3-Desiste  PF4-Detalhes  PF7-Bloq")) {
                //host.printScreenContent();
                host.sendPFKey(3);
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            // *** Este telefone encontra-se na Gerencia de Pendencia. ***
            if (host.queryStringAt(8, 35, "MENSAGEM") || host.queryStringAt(11, 15, "Este telefone encontra-se na Gerencia de Pendencia")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
//                JOptionPane.showMessageDialog(null, "Este telefone encontra-se na Gerência de Pendência!", "Atenção", JOptionPane.WARNING_MESSAGE);
                log.info("[ERRO] Este telefone encontra-se na Gerência de Pendência.");
                retornaMenuPrincipal();
                return false;
            }

            if (host.queryStringAt(9, 35, "RETORNO CLICK")) {
                host.printScreenContent();
                host.sendPFKey(4);
                host.waitForTerminalReady(mainFrame.getTimeout());
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            //*** Tratamento de popups de Alerta que exigem apenas <ENTER> ***//
            // POPUPS ERRADOS DE TELA
            if (host.queryStringAt(16, 42, "Usuario nao autorizado")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                return false;
            }

            if (host.queryStringAt(16, 40, "COMANDO NAO CADASTRADO")) {
                //host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                return false;
            }

            // TELEFONE COM TARIFACAO LOCAL EM MINUTOS.
            // CENTRAL COM BLOQUEIO ATENDIDO PELO 7IP
            // EWT ATIVADO .....
            if (host.queryStringAt(17, 30, "*** A L E R T A ***")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(17, 30, "A T E N C A O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(13, 40, "A T E N C A O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(15, 41, "Tecle <ENTER>")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(10, 42, "A T E N C A O")) {
                host.printScreenContent();
                host.sendPFKey(7);
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(8, 35, "Nao e permitido incluir aviso")) {
                host.printScreenContent();
//                JOptionPane.showMessageDialog(null, "AVISO", "Não é permitido incluir aviso para uma Ordem em processo de Interoperação.", JOptionPane.WARNING_MESSAGE);
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                return false;
            }

            //*** Tratamento de popups de Alerta que exigem apenas <ENTER> ***//
            if (host.queryStringAt(2, 27, "A T E N C A O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }
            // Este telefone pertence a area 'ASA'.
            if (host.queryStringAt(16, 46, "A T E N C A O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(17, 30, "A T E N C A O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(14, 34, "A T E N C A O !")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(8, 31, "MENSAGEM") && host.queryStringAt(11, 13, "EXISTEM ORDEM DE")) {
                host.printScreenContent();
                host.sendPFKey(4);
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            // Existe OI VELOX (ADSL) Projetado para esse telefone.
            if (host.queryStringAt(17, 28, "A T E N C A O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }
            // Existe OI VELOX (ADSL) Projetado para esse telefone.
            if (host.queryStringAt(17, 28, "A T E N C A O !")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.getScreenContentAt(17, 22, 25).trim().equals("A T E N C A O !")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }
            //*** Tratamento de popups de alerta que exigem apenas <PF3>  ***//
            if (host.queryStringAt(12, 20, "CLIENTE COM ATENDIMENTO ESPECIALIZADO") && host.queryStringAt(22, 21, "PF3")) {
                host.printScreenContent();
                host.sendPFKey(3);
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(10, 26, "A T E N C A O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(8, 38, "ATENCAO") || host.queryStringAt(10, 21, "A LIBERACAO do tom de discar  do  terminal")) {
                host.printScreenContent();
                host.setStringAt(16, 44, "N");
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }
            //O BLOQUEIO do tom de discar do terminal
            if (host.queryStringAt(8, 38, "ATENCAO") || host.queryStringAt(10, 21, "O BLOQUEIO")) {
                host.printScreenContent();
                host.setStringAt(16, 44, "N");
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            //*** Tratamento de popups de alerta EXISTEM ORDEM DE 'SERVICO' E 'APOIO' ABERTAS.  ***//
            if (host.queryStringAt(16, 13, "PF4= 'OS'")) {
                host.printScreenContent();
                host.sendPFKey(4);
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }
            //*** Tratamento de popups de Alerta que exigem apenas <ENTER> ***//
            if (host.queryStringAt(21, 32, "TECLE <ENTER...>")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }
            //*** Tratamento de popup ESTE TELEFONE NAO FIGURA EM LISTA TELEFONICA ***//
            if (host.queryStringAt(21, 31, "TECLE <ENTER...>")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }
            //*** Tratamento de popup PROCESSO DE MIGRACAO DE CAMPANHA VELOX. ***//
            if (host.queryStringAt(17, 30, "** A L E R T A **")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(17, 30, "*** A L E R T A ***")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            //*** Tratamento de popups de Alerta que exigem apenas <ENTER> ***//
            if (host.queryStringAt(15, 31, "** A L E R T A **")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }
            //*** Tratamento de popup ATENCAO - CLIENTE BRONZE ***//
            if (host.queryStringAt(15, 29, "** A L E R T A **")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }
            //*** Tratamento de popup ATENCAO - CLIENTE OURO ***//
            if (host.queryStringAt(15, 31, "** A L E R T A **")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }
            //*** Tratamento de popup ATENCAO - ESTE TELEFONE NAO FIGURA EM LISTA TELEFONICA ***//
            if (host.queryStringAt(15, 35, "ATENCAO")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(17, 32, "*** A L E R T A ***")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            //*** Tratamento de popups de Alerta que exigem apenas <ENTER> ***//
            // Este telefone pertence a area 'ASA' com agendamento.
            if (host.queryStringAt(21, 29, "<Enter>")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }
            //*** Tratamento de popup ENTREVISTA DA ATENDENTE COM O USUARIO ***//
            if (host.queryStringAt(19, 65, "Tecle enter")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }
            //*** Tratamento de popup de INSAPOIO ***//
            if (host.queryStringAt(19, 42, "ENTER")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            //*** Tratamento de popups de alerta que exigem apenas <PF7>  ***//
            // Popup de cliente ANATEL
            if (host.queryStringAt(19, 63, "TECLE <PF7...>")) {
                host.printScreenContent();
                host.sendPFKey(7);
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }
            //*** Tratamento de popup de alerta da existencia de Velox ***//
            if (host.queryStringAt(19, 21, "Existe OI VELOX (ADSL)")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            //*** Tratamento de popup de serviços associados ao terminal ***//
            if (host.queryStringAt(8, 29, "Lista dos Servicos existentes")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }
            //*** Tratamento do popup de Avisos por telefone ***///
            if (host.queryStringAt(5, 38, "AVISOS POR TELEFONE")) {
                host.printScreenContent();
                host.sendPFKey(3);
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }
            //*** Tratamento do popup de terminal com plano controle ***///
            if (host.queryStringAt(12, 29, "TERMINAL PLANO CONTROLE")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(17, 25, "A T E N C A O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(22, 31, "Tecle <ENTER")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }
            //*** Tratamento do popup de CLIENTE EMPRESARIAL ***///
            if (host.queryStringAt(22, 15, "TECLE <ENTER...>")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }
            //*** Tratamento do popup ESTE CLIENTE TEM CONSULTOR ***///
            if (host.queryStringAt(23, 15, "TECLE <ENTER...>")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(12, 19, "Esta O.S. ja foi selecionada")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(8, 17, "S E G M E N T O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(8, 12, "S E G M E N T O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(11, 29, "A T E N C A O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(10, 41, "A T E N C A O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(17, 41, "ALERTA")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(17, 37, "Observacao")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(17, 37, "ATENCAO")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(17, 37, "Observacao Atendente")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }
            // Parcelamento Nao Pago
            if (host.queryStringAt(10, 29, "Parcelamento Nao Pago")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(16, 25, "*** ALERTA ***")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(15, 29, "A T E N C A O")) {
                host.printScreenContent();
                if (host.queryStringAt(18, 12, "Existe outro Usuario utilizando este Registro.")) {
                    host.sendEnterKey();
                    host.waitForTerminalReady(mainFrame.getTimeout());
                    JOptionPane.showMessageDialog(null, "Existe outro Usuario utilizando este Registro!", "Atenção", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(16, 44, "A T E N C A O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(12, 19, "CLIENTE COM ATENDIMENTO ESPECIALIZADO")) {
                host.printScreenContent();
                host.sendPFKey(3);
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(10, 13, "LOCALIDADE")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(8, 29, "MENSAGEM")) {
                host.printScreenContent();
                if (host.queryStringAt(10, 15, "USUARIO NAO AUTORIZADO NESTE POSTO")) {
                    host.printScreenContent();
                    JOptionPane.showMessageDialog(null, host.getScreenContentAt(10, 15, 38), "Erro", JOptionPane.ERROR_MESSAGE);
                    host.sendEnterKey();
                    host.waitForTerminalReady(mainFrame.getTimeout());
                    return false;
                }
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(3, 27, "EQUIPAMENTOS DO TERMINAL DVI")) {
                host.printScreenContent();
                host.sendPFKey(3);
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(16, 30, "*** A L E R T A ***")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(4, 71, "CGS0375A")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(9, 36, "ATENCAO")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(17, 23, "A T E N C A O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(12, 16, "Logradouro")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(17, 31, "A L E R T A")) {
                host.printScreenContent();
                //O  NUMERO _______ FOI PORTADO PARA OUTRA OPERADORA.PARA VER DADOS DO FICTICIO
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(5, 48, "A T E N C A O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            // Abertura de Reparo de WLL
            if (host.queryStringAt(9, 41, "Abertura de Reparo de WLL")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(17, 41, "ALERTA")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(14, 39, "A T E N C A O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(4, 37, "COBRANCA")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(12, 25, "DESEJA CONTINUAR")) {
                host.printScreenContent();
                host.setStringAt(12, 43, "S");
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(5, 27, "ENTREVISTA")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(10, 35, "A T E N C A O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(11, 31, "A T E N C A O")) {
                host.setStringAt(14, 51, "S");
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(11, 36, "A T E N C A O")) {
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(9, 58, "A T E N C A O")) {
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());

                ret = true;
            }

            if (host.queryStringAt(10, 33, "A T E N C A O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }
            // Ordem esta Aprazada  //// Tela I.A
            if (host.queryStringAt(12, 29, "Ordem esta Aprazada")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(9, 8, "Telefone")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(12, 15, "Rede Interna")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(6, 56, "A T E N C A O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(12, 48, "A T E N C A O")) {
                host.printScreenContent();
                if (host.queryStringAt(16, 42, "Usuario nao autorizado")) {
                    host.sendEnterKey();
                    host.waitForTerminalReady(mainFrame.getTimeout());
                    return false;
                }
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(19, 66, "<TODOS>")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            // Voce ira providenciar A LIBERACAO  do  tom de discar manualmente?
            if (host.queryStringAt(16, 31, "manualmente?")) {
                host.printScreenContent();
                host.setStringAt(16, 44, "N");
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(1, 2, "Nao existem dados para os criterios informados")) {
                host.printScreenContent();
                //JOptionPane.showMessageDialog(null, "Não existem dados para os critérios informados.", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (host.queryStringAt(12, 36, "A V I S O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                if (host.queryStringAt(16, 13, "Deseja reservar esta ordem para uma futura execucao ?")) {
                    host.sendEnterKey();
                    host.waitForTerminalReady(mainFrame.getTimeout());
                }
                ret = true;
            }

            if (host.queryStringAt(11, 11, "EXISTEM ORDEM DE 'SERVICO' E 'APOIO' ABERTAS")) {
                host.printScreenContent();
                host.sendPFKey(4); //Seleciona OS
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(9, 37, "ATENCAO")) {
                host.printScreenContent();
                JOptionPane.showMessageDialog(null, "Popup não mapeado.\n\nPrintar a tela e enviar para a equipe de desenvolvimento.", "Erro", JOptionPane.ERROR_MESSAGE);
                host.printScreenContent();
                return false;
            }

            if (host.queryStringAt(10, 31, "A T E N C A O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(12, 16, "ESTA OS SERA AUTOMATICAMENTE FECHADA PELO SISTEMA")) {
                host.printScreenContent();
                JOptionPane.showMessageDialog(null, "Esta OS será automaticamente fechada pelo sistema", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (host.getScreenContentAt(10, 38, 20).replace(" ", "").equals("ATENCAO")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(10, 38, "A T E N C A O")) {
                host.printScreenContent();
                host.sendEnterKey();
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.getScreenContentAt(8, 20, 32).replace(" ", "").equals("MENSAGEM")) {
                host.printScreenContent();
                if (host.getScreenContentAt(11, 10, 60).trim().equals("Existem servicos nesta ordem para serem executados")) {
                    JOptionPane.showMessageDialog(null, "ATENCAO! Existem servicos nesta ordem para serem executados", "Atenção", JOptionPane.ERROR_MESSAGE);
                    return false;
                } else if (host.queryStringAt(12, 13, "Nao existe O.S. aberta para o telefone informado.")) {
                    host.sendEnterKey();
                    host.waitForTerminalReady(mainFrame.getTimeout());
                    JOptionPane.showMessageDialog(null, "ATENCAO! Nao existe O.S. aberta para o telefone informado. ", "Atenção", JOptionPane.ERROR_MESSAGE);
                    return false;
                } else {
                    host.sendEnterKey();
                    host.waitForTerminalReady(mainFrame.getTimeout());
                    ret = true;
                }
            }

            if (host.queryStringAt(5, 38, "AVISOS")) {
                host.printScreenContent();
                host.sendPFKey(3);
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(7, 11, "Descricao do erro")) {
                while ("IFF0MENU".equals(host.getScreenContentAt(7, 73, 8))) {
                    host.sendEnterKey();
                    host.waitForTerminalReady(mainFrame.getTimeout());
                }

                return false;
            }

            if (host.queryStringAt(5, 39, "AVISO")) {
                host.printScreenContent();
                host.sendPFKey(3);
                host.waitForTerminalReady(mainFrame.getTimeout());
                ret = true;
            }

            if (host.queryStringAt(7, 17, "Selecione a localidade desejada")) {
                int linha = 11;
                while (!host.getScreenContentAt(linha, 17, 20).trim().isEmpty()) {
                    if (("0" + registroAtual.getDdd()).equals(host.getScreenContentAt(linha, 20, 3).trim())) {
                        host.setStringAt(linha, 17, "X");
                        host.sendEnterKeyWait(mainFrame.getTimeout());
                        break;
                    } else if ((linha == 20) || host.getScreenContentAt(linha + 1, 20, 20).trim().isEmpty()) {
                        host.setStringAt(11, 17, "X");
                        host.sendEnterKeyWait(mainFrame.getTimeout());
                        break;
                    }

                    linha++;
                }
                ret = true;
            }

            if (host.queryStringAt(7, 24, "Selecione a localidade desejada")) {
                int linha = 11;
                while (!host.getScreenContentAt(linha, 24, 20).trim().isEmpty()) {
                    if (("0" + registroAtual.getDdd()).equals(host.getScreenContentAt(linha, 28, 3).trim())) {
                        host.setStringAt(linha, 24, "X");
                        host.sendEnterKeyWait(mainFrame.getTimeout());
                        break;
                    } else if ((linha == 24) || host.getScreenContentAt(linha + 1, 24, 20).trim().isEmpty()) {
                        host.setStringAt(11, 24, "X");
                        host.sendEnterKeyWait(mainFrame.getTimeout());
                        break;
                    }

                    linha++;
                }
                ret = true;
            }

            if (host.queryStringAt(4, 71, "CGS2225A")) {
                int linha = 6;

                trace.info("Procurando o DDD: 00" + registroAtual.getDdd());
                boolean achouDDD = false;
                while (!host.getScreenContentAt(linha, 21, 1).trim().isEmpty()) {
                    if (("00" + registroAtual.getDdd()).equals(host.getScreenContentAt(linha, 33, 4).trim())) {
                        host.setStringAt(linha, 16, "X");
                        host.sendEnterKeyWait(mainFrame.getTimeout());
                        achouDDD = true;
                        break;
                    } else if ((linha >= 20)) {
                        host.sendPFKeyWait(3, mainFrame.getTimeout());
                        break;
                    } else {
                        linha += 2;
                    }

                }
                if (!achouDDD) {
                    host.sendPFKeyWait(3, mainFrame.getTimeout());
                    break;
                }
                ret = true;
            }

            if (host.queryStringAt(12, 28, "RELACAO DE 'OS' ABERTAS")) {
                int linha = 15;
                boolean acabouOS = false;
                boolean achouOS = false;
                String relacaoOS = "";
                do {
                    if (host.getScreenContentAt(linha, 8, 70).trim().isEmpty()) {
                        acabouOS = true;
                    } else {
                        relacaoOS += "\n" + host.getScreenContentAt(linha, 8, 9).trim() + " " + host.getScreenContentAt(linha, 37, 19).trim();
                        relacaoOS += " (" + host.getScreenContentAt(linha, 71, 8).trim() + ")";
                    }
                    if (host.queryStringAt(linha, 8, registroAtual.getNumOS())) {
                        achouOS = true;
                        host.setStringAt(linha, 6, "X");
//                        tela.atualizarTela(host.getScreenContent());
                    }
                    linha++;
                } while (linha < 20 && !acabouOS);
                registroAtual.mapaAuxiliarInfo.put("relacaoOS", relacaoOS);
                if (achouOS) {
                    host.sendEnterKey();
                    host.waitForTerminalReady(mainFrame.getTimeout());
                    host.printScreenContent();
                    ret = true;
//                    tela.atualizarTela(host.getScreenContent());
                } else {
                    log.error("[ERRO] Nao encontrou a OS " + registroAtual.getNumOS() + "no popup relacao de OS abertas");
                    trace.error("Nao encontrou a OS " + registroAtual.getNumOS() + "no popup relacao de OS abertas");
                    registroAtual.setResultadoCRM("Não encontrou a OS " + registroAtual.getNumOS() + " na relação de OS abertas!");
//                JOptionPane.showMessageDialog(null, "Não encontrou a OS " + registroAtual.getNumOS() + " na relação de OS abertas!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }

            countLoop++;

        } while (ret && countLoop < 15);

        if (ret && countLoop >= 15) {
            log.error("[ERRO] Loop infinito no popupGeraisSAC. Mais de 15 tentativas.");
            trace.error("Loop infinito no popupGeraisSAC. Mais de 15 tentativas.");
//            JOptionPane.showMessageDialog(null, "Detectado erro no mapeamento de telas (SAC).\n\nPrint a tela e envie para o suporte!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

    public void acessaUfNoSAC() {
        if (!"APLICACOES ON LINE DISPONIVEIS".equals(host.getScreenContentAt(3, 2, 30).trim())) {
            host.sendPFKeyWait(10, mainFrame.getTimeout());
        }

        String numUF = pegaNumeroUF();

        if (numUF == null) {
//            trace.warn("Não existe aplicação para a UF " + ufAtual);
            return;
        }

        host.setStringAt(19, 29, numUF);
        host.sendEnterKeyWait(mainFrame.getTimeout());

        if (host.queryStringAt(1, 1, "EMS1334E Dados da aplicacao nao disponivel. Limpe a tela.")) {
            host.sendPFKeyWait(3, mainFrame.getTimeout());
            host.sendPFKeyWait(10, mainFrame.getTimeout());
            numUF = pegaNumeroUF();
            host.setStringAt(19, 29, numUF);
            host.sendEnterKeyWait(mainFrame.getTimeout());
            if (host.queryStringAt(1, 63, "Terminal") && host.queryStringAt(12, 11, "ACESSO AS APLICACOES ON LINE ")) {
//                servidor.atualizaServidor();
                conectarSAC();
                numUF = pegaNumeroUF();
                host.setStringAt(19, 29, numUF);
                host.sendEnterKeyWait(mainFrame.getTimeout());

            } else {
                host.printScreenContent();
                //host.setStringAt(19, 29, "lf " + numUF);
                if (host.queryStringAt(19, 2, "Informe NUMERO ou CODIGO")) {
                    host.setStringAt(19, 29, "LOGOFF");
                    host.sendEnterKeyWait(mainFrame.getTimeout());
                    conectarSAC();
                    numUF = pegaNumeroUF();
                    host.setStringAt(19, 29, numUF);
                    host.sendEnterKeyWait(mainFrame.getTimeout());
                } else {
                    host.printScreenContent();
                    host.Disconnect();
                    trace.error("Erro ao acessar Aplicação no SAC.");
                    host.printScreenContent();
                    conectarSAC();
                    numUF = pegaNumeroUF();
                    host.setStringAt(19, 29, numUF);
                    host.sendEnterKeyWait(mainFrame.getTimeout());
                }
            }

            //acessaUfNoSAC();
        }

        if (host.queryStringAt(1, 1, "COMTAM0001-Z No program active")) {
            //host.printScreenContent();
            host.Disconnect();
            trace.error("Erro ao acessar Aplicação no SAC.");
            conectarSAC();
            //acessaUfNoSAC();
        }
    }
    //Funcao de conexao com o SAC

    public boolean conectarSAC() {
        if (host.isConnected()) {
            host.Disconnect();
        }

        if ("".equals(mainFrame.getHostIP()) || mainFrame.getHostIP() == null) {
            mainFrame.setHostIP("10.32.102.62");
        } else {
//            servidor.atualizaServidor();
        }

        host.Connect(mainFrame.getHostIP());
        host.waitForTerminalReady(mainFrame.getTimeout());

        if (host.isConnected()) {
            log.info("[SUCESSO] Conectado em " + mainFrame.getHostIP()); //Gravando Log
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível conectar no servidor!\n" + mainFrame.getHostIP(), "Erro", JOptionPane.ERROR_MESSAGE);
            log.info("[ERRO] Não foi possível conectar em: " + mainFrame.getHostIP()); //Gravando Log
            return false;
        }

        ////////// Tela de Login
        if (!host.queryStringAt(16, 13, "Matricula")) {
//            JOptionPane.showMessageDialog(null, "Comando não reconhecido!\n" + mainframe.getCanal(), "Erro", JOptionPane.ERROR_MESSAGE);
            host.printScreenContent();
            log.info("[ERRO] Comando não reconhecido: " + mainFrame.getCanal()); //Gravando Log
            return false;
        }
        //        host.setStringAt(16, 35, parametro.getUsuario());
        //        host.setStringAtPassword(17, 35, parametro.getSenha());
        host.setStringAt(16, 35, "TR580524");
        host.setStringAtPassword(17, 35, "eewwqq11");
        host.sendEnterKeyWait(mainFrame.getTimeout());

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

        if (host.queryStringAt(3, 2, "APLICACOES ON LINE DISPONIVEIS")) {
            host.setStringAt(19, 29, "LOGOFF");
            host.sendEnterKeyWait(mainFrame.getTimeout());
//            host.setStringAt(16, 35, parametro.getUsuario());
//            host.setStringAtPassword(17, 35, parametro.getSenha());
            host.sendEnterKeyWait(mainFrame.getTimeout());
        }

        String msgErroConnSAC = host.getScreenContentAt(23, 2, 60).trim();

        if (msgErroConnSAC.contains("logged")) {
            JOptionPane.showMessageDialog(null, "Esse usuário já se encontra logado no SAC.\n\nFavor deslogar do SAC e clicar em OK para tentar novamente.", "Usuário Logado", JOptionPane.INFORMATION_MESSAGE);
            log.info("[ERRO] Dados incorretos de login: " + msgErroConnSAC); //Gravando Log
            return conectarSAC();
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
            if (registroAtual.getUf().equals(host.getScreenContentAt(linha, 9, 2).trim())) {
                return host.getScreenContentAt(linha, 4, 3).trim();
            } else if (linha == 16) {
                if (quantTentativas < 2) {
                    if (quantPaginacao < 2) {
                        host.sendPFKeyWait(8, mainFrame.getTimeout());
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

    /**
     * Envia o comando F3 para a tela
     */
    public void sendF3() {
        host.sendPFKey(3);
        host.sendEnterKeyWait(mainFrame.getTimeout());
    }

    /**
     * Envia o comando F5 para a tela
     */
    public void sendF4() {
        host.sendPFKey(4);
        host.sendEnterKeyWait(mainFrame.getTimeout());
    }

    /**
     * Envia o comando F5 para a tela
     */
    public void sendF5() {
        host.sendPFKey(5);
        host.sendEnterKeyWait(mainFrame.getTimeout());
    }

    /**
     * Envia o comando F7 para a tela.
     */
    public void sendF7() {
        host.sendPFKey(7);
        host.sendEnterKeyWait(mainFrame.getTimeout());
    }

    /**
     * Envia o comando F8 para a tela.
     */
    public void sendF8() {
        host.sendPFKey(8);
        host.sendEnterKeyWait(mainFrame.getTimeout());
    }

    /**
     * Envia o comando F9 para a tela.
     */
    public void sendF9() {
        host.sendPFKey(9);
        host.sendEnterKeyWait(mainFrame.getTimeout());
    }

    /**
     * Envia o comando ENTER
     */
    public void sendEnter() {
        host.sendEnterKeyWait(mainFrame.getTimeout());
    }
}

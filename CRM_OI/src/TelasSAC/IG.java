/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TelasSAC;

import static Conection.ConexaoSAC.host;
import static Conection.ConexaoSAC.mainFrame;

/**
 *
 * @author fernando.m.souza
 */
public class IG extends Tela {

    /* Acesso a tela TOTAL DE DOCUMENTOS ABERTOS */
    public static final String IG = "24,6,IG";
    public static final String empreiteira = "14,39,x";

    /* ESTATISTICA DA FILA DE EXECUCAO OS/BD - CGIG001B */
    public static final String estFilExeReparos = "18,39,x";

    /*TELEGOIAS BRASIL TEL - CGS1310A*/
    public static final String telBraTelDe = "7,14,x";
    public static final String telBraTelEv = "7,27,x";
    public static final String telBraTelQf = "7,40,x";
    public static final String telBraTelQr = "7,53,x";
    public static final String telBraTelRf = "7,66,x";
    public static final String telBraTelTb = "7,79,x";

    /* ESTATISTICA DA FILA DE EXECUCAO OS/BD - CGS0371A*/
    public static final String posicaoExeFilaExeNumeBD = "4,40,8";
    public static final String posicaoExeFilaExeLocalidade = "5,22,3";
    public static final String posicaoExeFilaExeTerminal = "5,27,8";
    public static final String posicaoExeFilaExeDataHoraSolicitacao = "9,24,13";
    public static final String posicaoExeFilaExeDataHoraPromessa = "10,24,3";
    public static final String posicaoExeFilaExeOrigem = "13,9,10";
    public static final String posicaoExeFilaExeReparoSolicitado = "14,20,17";

    /*PopUp Inicia Tela */
    public static final String observacaoAssinante = "13,55,x";

    /*DADOS DE FACILIDADE - CGS2799A*/
    public static final String posicaoDadFacPontaA = "7,20,61";
    public static final String posicaoDadFacLocal = "12,3,3";
    public static final String posicaoDadFacEstacao = "12,10,3";
    public static final String posicaoDadFacCabo = "12,16,1";
    public static final String posicaoDadFacParp = "12,22,3";
    public static final String posicaoDadFacAd = "12,27,2";
    public static final String posicaoDadFacAsteristico = "12,31,2";
    public static final String posicaoDadFacPars = "12,34,3";
    public static final String posicaoDadFacCaixa = "12,39,2";
    public static final String posicaoDadFacPtCan = "12,50,1";

    /*DADOS DE FACILIDADES PORTA ADSL - CGS1155A*/
    public static final String posicaoDadFacAdslNroPortaAdsl = "9,60,8";

    /*ESTATISTICA DA FILA DE EXECUCAO OS/BD - CGS1310B*/
    public void acessa() {
        host.setStringAt(this.IG);
        host.sendEnterKeyWait(mainFrame.getTimeout());
    }

    public void selecionaEmpreiteira() {
        host.setStringAt(this.empreiteira);
        host.sendEnterKeyWait(mainFrame.getTimeout());
    }

    /* DETALHAMENTO POR EMPREITEIRAS - CGIG001A */
    public void selecDetEmpreiteira(int Linha) {
        host.setStringAt(Linha, 24, "X");
        host.sendEnterKeyWait(mainFrame.getTimeout());
    }

    /* ESTATISTICA DA FILA DE EXECUCAO OS/BD - CGIG001B */
    public void selecEstFilaExeReparos() {
        host.setStringAt(estFilExeReparos);
        host.sendEnterKeyWait(mainFrame.getTimeout());
    }

    /*TELEGOIAS BRASIL TEL - CGS1310A*/
    public void selecTelBraTelRf() {
        host.setStringAt(telBraTelRf);
        host.sendEnterKeyWait(mainFrame.getTimeout());
    }

    /*ESTATISTICA DA FILA DE EXECUCAO OS/BD - CGS1310B*/
    public void selecReparoDetalhar(int Linha) {
        host.setStringAt(Linha, 3, "X");
        host.sendEnterKeyWait(mainFrame.getTimeout());
    }

    /* ESTATISTICA DA FILA DE EXECUCAO OS/BD - CGS0371A */
    public String getEstFilaExeNumBD() {
        return host.getScreenContentAt(this.posicaoExeFilaExeNumeBD);
    }

    public String getExeFilaExeLocalidade() {
        return host.getScreenContentAt(this.posicaoExeFilaExeLocalidade);
    }

    public String getExeFilaExeTerminal() {
        return host.getScreenContentAt(this.posicaoExeFilaExeTerminal);
    }

    public String getExeFilaExeDataHoraSolicitacao() {
        return host.getScreenContentAt(this.posicaoExeFilaExeDataHoraSolicitacao);
    }

    public String getExeFilaExeDataHoraPromessa() {
        return host.getScreenContentAt(this.posicaoExeFilaExeDataHoraPromessa);
    }

    public String getExeFilaExeOrigem() {
        return host.getScreenContentAt(this.posicaoExeFilaExeOrigem);
    }

    public String getExeFilaExeReparoSolicitado() {
        return host.getScreenContentAt(this.posicaoExeFilaExeReparoSolicitado);
    }

    public String getObservacaoAssinante() {
        StringBuffer sb = new StringBuffer();
        int i = 6;
        do {
            sb.append(host.getScreenContentAt(i, 8, 71).trim());
            i++;
            System.out.println(host.getScreenContentAt(i, 8, 71).trim());
        } while (!"".equals(host.getScreenContentAt(i, 8, 71).trim()));

        sendF3();

        return sb.toString();
    }

    /**
     * Comando F5 especifico para a tela devido a possibilidade de seleção.
     *
     * @param OBSERVACAO ASSINANTE = observacaoAssinante
     * @param OBSERVACAO UNIDADE = observacaoUnidade
     * @param ENTREVISTA = entrevista
     */
    public void sendF5(String labelOpcao) {
        host.sendPFKey(5);
        host.sendEnterKeyWait(mainFrame.getTimeout());
        host.setStringAt(labelOpcao);
        host.sendEnterKeyWait(mainFrame.getTimeout());
    }

    /*DADOS DE FACILIDADE - CGS2799A*/
    public String getDadFacPontaA() {
        return host.getScreenContentAt(this.posicaoDadFacPontaA);
    }

    public String getDadFacLocal() {
        return host.getScreenContentAt(this.posicaoDadFacLocal);
    }

    public String getDadFacEstacao() {
        return host.getScreenContentAt(this.posicaoDadFacEstacao);
    }

    public String getDadFacCabo() {
        return host.getScreenContentAt(this.posicaoDadFacCabo);
    }

    public String getDadFacParp() {
        return host.getScreenContentAt(this.posicaoDadFacParp);
    }

    public String getDadFacAd() {
        return host.getScreenContentAt(this.posicaoDadFacAd);
    }

    public String getDadFacAsteristico() {
        return host.getScreenContentAt(this.posicaoDadFacAsteristico);
    }

    public String getDadFacPars() {
        return host.getScreenContentAt(this.posicaoDadFacPars);
    }

    public String getDadFacCaixa() {
        return host.getScreenContentAt(this.posicaoDadFacCaixa);
    }

    public String getDadFacPtCan() {
        return host.getScreenContentAt(this.posicaoDadFacPtCan);
    }

    /*DADOS DE FACILIDADES PORTA ADSL - CGS1155A*/
    public String getDadFacAdslNroPortaAdsl() {
        return host.getScreenContentAt(this.posicaoDadFacAdslNroPortaAdsl);
    }
}

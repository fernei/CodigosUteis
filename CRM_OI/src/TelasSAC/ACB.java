/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TelasSAC;

import static Conection.ConexaoSAC.host;
import static Conection.ConexaoSAC.mainFrame;
import Model.TelefonesContatoACB;

/**
 *
 * @author fernando.m.souza
 */
public class ACB extends Tela {

    /*IDENTIFICAÇÃO DO CLIENTE*/
    public static final String ACB = "24,6,ACB";
    public static final String localidade = "6,24,4";
    public static final String cat = "6,42,3";

    public static final String posicaoTerminal = "6,13";

    /* TELEFONES PARA CONTATO - F4 */
    public static final String posicaoTipoResidencial = "14,16,11";
    public static final String posicaoNomeResidencial = "15,22,20";
    public static final String posicaoDddResidencial = "15,48,4";
    public static final String posicaoFoneResidencial = "15,59,9";
    public static final String posicaoRamalResidencial = "15,75,4";

    public static final String posicaoTipoComercial = "17,16,11";
    public static final String posicaoNomeComercial = "18,22,20";
    public static final String posicaoDddComercial = "18,48,4";
    public static final String posicaoFoneComercial = "18,59,9";
    public static final String posicaoRamalComercial = "18,75,4";

    public static final String posicaoTipoCelular = "20,16,11";
    public static final String posicaoNomeCelular = "21,22,20";
    public static final String posicaoDddCelular = "21,48,4";
    public static final String posicaoFoneCelular = "21,59,9";
    public static final String posicaoRamalCelular = "21,75,4";

    /* DADOS DE FACILIDADE - F8 */
    public static final String dfacF8 = "F8";
    public static final String dfacEstacao = "12,8,6";
    public static final String dadFacCabo = "12,16,5";
    public static final String dfacSecaoServico = "12,27,3"; //Campo AD

    /**
     * Digita o comando ACB na linha do CMD do SAC, efetuando assim a abertura
     * da tela ACB
     * <p>
     * Nome Tela.: IDENTIFICACAO DO CLIENTE
     * <p>
     * Codigo Tela.: CGACB00A
     * <p>
     * Sigla Tela.:A.C.B
     *
     * @since 1.0
     */
    public void acessa() {
        host.setStringAt(this.ACB);
        host.sendEnterKeyWait(mainFrame.getTimeout());
    }

    /**
     * Retorna o valor para a Localidade.
     * <p>
     * Posição 6 / 24 - 4
     * <p>
     * Nome.: IDENTIFICACAO DO CLIENTE
     * <p>
     * Codigo.: CGACB00A
     * <p>
     * Sigla.:A.C.B
     *
     * @since 1.0
     */
    public String getLocalidade() {
        return host.getScreenContentAt(this.localidade);
    }

    public void setTerminal(String terminal) {
        host.setStringAt(this.posicaoTerminal, terminal);
        sendEnter();
    }

    public void getTelefonesParaContato() {
//pag 26
        int i = 14;
        do {
            TelefonesContatoACB tcAcb = new TelefonesContatoACB();
            tcAcb.setTipo(host.getScreenContentAt(ACB));
            tcAcb.setNome("");
            tcAcb.setDdd("");
            tcAcb.setFone("");
            tcAcb.setRamal("");
            i++;
            System.out.println(host.getScreenContentAt(i, 8, 71).trim());
        } while (!"".equals(host.getScreenContentAt(i, 8, 71).trim()));

        sendF3();

    }

}

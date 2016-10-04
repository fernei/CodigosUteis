/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.TreeMap;

/**
 *
 * @author fernando.m.souza
 */
public class RegistroBase {

    private int IdRegistro;
    private String pa;
    private String id;
    private String uf;
    private String ddd;
    private String terminal;
    private String tipo;
    private String logradouro;
    private String numero;
    private String situacao;
    private String solucao;
    private String dtAbertura;
    private String dtEntradaAnalise;
    private String dtSaidaAnalise;
    private String dtEntradaCampo;
    private String dtSaidaCampo;
    private String dtFechamentoTotal;
    private String canal;
    private String solicitante;
    private String osFechada;
    private String fechadaSVOI;
    private String osSTC;
    private String tratadoPor;
    private String obsFechamento;
    private String canalSolicitante;
    private String loginSolicitante;

    private String resultadoCRM;
    private String localidade;
    private String cat;
    private String tec;
    private String estacao;
    private String cabo;
    private String secaoServico;
    private String caixa;
    private String sistema;
    private String tipoCrm;
    private String numOS;
    public TreeMap mapaAuxiliarInfo = new TreeMap(); //incluso no RESET

    public String getNumOS() {
        return numOS;
    }

    public void setNumOS(String numOS) {
        this.numOS = numOS;
    }

    public TreeMap getMapaAuxiliarInfo() {
        return mapaAuxiliarInfo;
    }

    public void setMapaAuxiliarInfo(TreeMap mapaAuxiliarInfo) {
        this.mapaAuxiliarInfo = mapaAuxiliarInfo;
    }
    
    public int getIdRegistro() {
        return IdRegistro;
    }

    public void setIdRegistro(int IdRegistro) {
        this.IdRegistro = IdRegistro;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public String getDtAbertura() {
        return dtAbertura;
    }

    public void setDtAbertura(String dtAbertura) {
        this.dtAbertura = dtAbertura;
    }

    public String getDtEntradaAnalise() {
        return dtEntradaAnalise;
    }

    public void setDtEntradaAnalise(String dtEntradaAnalise) {
        this.dtEntradaAnalise = dtEntradaAnalise;
    }

    public String getDtSaidaAnalise() {
        return dtSaidaAnalise;
    }

    public void setDtSaidaAnalise(String dtSaidaAnalise) {
        this.dtSaidaAnalise = dtSaidaAnalise;
    }

    public String getDtEntradaCampo() {
        return dtEntradaCampo;
    }

    public void setDtEntradaCampo(String dtEntradaCampo) {
        this.dtEntradaCampo = dtEntradaCampo;
    }

    public String getDtSaidaCampo() {
        return dtSaidaCampo;
    }

    public void setDtSaidaCampo(String dtSaidaCampo) {
        this.dtSaidaCampo = dtSaidaCampo;
    }

    public String getDtFechamentoTotal() {
        return dtFechamentoTotal;
    }

    public void setDtFechamentoTotal(String dtFechamentoTotal) {
        this.dtFechamentoTotal = dtFechamentoTotal;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getOsFechada() {
        return osFechada;
    }

    public void setOsFechada(String osFechada) {
        this.osFechada = osFechada;
    }

    public String getFechadaSVOI() {
        return fechadaSVOI;
    }

    public void setFechadaSVOI(String fechadaSVOI) {
        this.fechadaSVOI = fechadaSVOI;
    }

    public String getOsSTC() {
        return osSTC;
    }

    public void setOsSTC(String osSTC) {
        this.osSTC = osSTC;
    }

    public String getTratadoPor() {
        return tratadoPor;
    }

    public void setTratadoPor(String tratadoPor) {
        this.tratadoPor = tratadoPor;
    }

    public String getObsFechamento() {
        return obsFechamento;
    }

    public void setObsFechamento(String obsFechamento) {
        this.obsFechamento = obsFechamento;
    }

    public String getCanalSolicitante() {
        return canalSolicitante;
    }

    public void setCanalSolicitante(String canalSolicitante) {
        this.canalSolicitante = canalSolicitante;
    }

    public String getLoginSolicitante() {
        return loginSolicitante;
    }

    public void setLoginSolicitante(String loginSolicitante) {
        this.loginSolicitante = loginSolicitante;
    }

    public String getResultadoCRM() {
        return resultadoCRM;
    }

    public void setResultadoCRM(String resultadoCRM) {
        this.resultadoCRM = resultadoCRM;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getTec() {
        return tec;
    }

    public void setTec(String tec) {
        this.tec = tec;
    }

    public String getEstacao() {
        return estacao;
    }

    public void setEstacao(String estacao) {
        this.estacao = estacao;
    }

    public String getCabo() {
        return cabo;
    }

    public void setCabo(String cabo) {
        this.cabo = cabo;
    }

    public String getSecaoServico() {
        return secaoServico;
    }

    public void setSecaoServico(String secaoServico) {
        this.secaoServico = secaoServico;
    }

    public String getCaixa() {
        return caixa;
    }

    public void setCaixa(String caixa) {
        this.caixa = caixa;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public String getTipoCrm() {
        return tipoCrm;
    }

    public void setTipoCrm(String tipoCrm) {
        this.tipoCrm = tipoCrm;
    }

    
}

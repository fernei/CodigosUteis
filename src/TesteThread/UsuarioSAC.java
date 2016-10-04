/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TesteThread;

import java.io.Serializable;
import java.util.Date;



/**
 * Usuário SAC que contêm permissões para acessar o sistema SAC.
 *
 * @author fernando.m.souza
 */
public class UsuarioSAC {

    private String usuario;
    private String senha;
    private String senhaDescriptografada;
    private String arqSessaoExtra;
    private int maxSegundosUso;
    private String disponivel;
    private String emUso;
    
    private Date dataAtualizacao;
    private String ultimaAcao;
    private Date dataCriacao;
    private Date dataInicio;
    private Date dataFim;
    private long duracaoSegundos;
    

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getArqSessaoExtra() {
        return arqSessaoExtra;
    }

    public void setArqSessaoExtra(String arqSessaoExtra) {
        this.arqSessaoExtra = arqSessaoExtra;
    }

    public int getMaxSegundosUso() {
        return maxSegundosUso;
    }

    public void setMaxSegundosUso(int maxSegundosUso) {
        this.maxSegundosUso = maxSegundosUso;
    }

    public String getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(String disponivel) {
        this.disponivel = disponivel;
    }

    public String getEmUso() {
        return emUso;
    }

    public void setEmUso(String emUso) {
        this.emUso = emUso;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public String getUltimaAcao() {
        return ultimaAcao;
    }

    public void setUltimaAcao(String ultimaAcao) {
        this.ultimaAcao = ultimaAcao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public long getDuracaoSegundos() {
        return duracaoSegundos;
    }

    public void setDuracaoSegundos(long duracaoSegundos) {
        this.duracaoSegundos = duracaoSegundos;
    }

    

    public String getSenhaDescriptografada() {
        return senhaDescriptografada;
    }

    public void setSenhaDescriptografada(String senhaDescriptografada) {
        this.senhaDescriptografada = senhaDescriptografada;
    }

}

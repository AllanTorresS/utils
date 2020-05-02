package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa de dispositivos
 */
public class FiltroPesquisaDispositivoMotoristaVo extends BaseFiltroPaginado {

    private String nome;
    private String cpf;
    private EnumVo classificacao;
    private EntidadeVo unidade;
    private EntidadeVo grupo;
    private EntidadeVo empresaAgregada;
    private EnumVo statusBloqueio;
    private EnumVo statusHabilitacao;
    private EntidadeVo frota;
    private boolean semAllowMe;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public EnumVo getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(EnumVo classificacao) {
        this.classificacao = classificacao;
    }

    public EntidadeVo getUnidade() {
        return unidade;
    }

    public void setUnidade(EntidadeVo unidade) {
        this.unidade = unidade;
    }

    public EntidadeVo getGrupo() {
        return grupo;
    }

    public void setGrupo(EntidadeVo grupo) {
        this.grupo = grupo;
    }

    public EntidadeVo getEmpresaAgregada() {
        return empresaAgregada;
    }

    public void setEmpresaAgregada(EntidadeVo empresaAgregada) {
        this.empresaAgregada = empresaAgregada;
    }

    public EnumVo getStatusBloqueio() {
        return statusBloqueio;
    }

    public void setStatusBloqueio(EnumVo statusBloqueio) {
        this.statusBloqueio = statusBloqueio;
    }

    public EnumVo getStatusHabilitacao() {
        return statusHabilitacao;
    }

    public void setStatusHabilitacao(EnumVo statusHabilitacao) {
        this.statusHabilitacao = statusHabilitacao;
    }

    public boolean getSemAllowMe() {
        return semAllowMe;
    }

    public void setSemAllowMe(boolean semAllowMe) {
        this.semAllowMe = semAllowMe;
    }

    public EntidadeVo getFrota() {
        return frota;
    }

    public void setFrota(EntidadeVo frota) {
        this.frota = frota;
    }
}

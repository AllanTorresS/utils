package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa de UsuarioMotorista
 */
public class FiltroPesquisaUsuarioMotoristaVo extends BaseFiltroPaginado {

    private String nome;
    private String cpf;
    private EnumVo classificacao;
    private EntidadeVo unidade;
    private EntidadeVo grupo;
    private EntidadeVo empresaAgregada;
    private EntidadeVo frota;
    private EnumVo statusNaFrota;

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

    public EntidadeVo getFrota() {
        return frota;
    }

    public void setFrota(EntidadeVo frota) {
        this.frota = frota;
    }

    public EnumVo getStatusNaFrota() {
        return statusNaFrota;
    }

    public void setStatusNaFrota(EnumVo statusNaFrota) {
        this.statusNaFrota = statusNaFrota;
    }
}

package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa de motoristas
 */
public class FiltroPesquisaMotoristaVo extends BaseFiltroPaginado {

    private String nome;
    private String cpf;
    private EnumVo vencimentoCNH;
    private EnumVo status;
    private EnumVo classificacao;
    private EntidadeVo unidade;
    private EntidadeVo grupo;
    private EntidadeVo empresaAgregada;
    private EntidadeVo frota;

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

    public EnumVo getVencimentoCNH() {
        return vencimentoCNH;
    }

    public void setVencimentoCNH(EnumVo vencimentoCNH) {
        this.vencimentoCNH = vencimentoCNH;
    }

    public EnumVo getStatus() {
        return status;
    }

    public void setStatus(EnumVo status) {
        this.status = status;
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

    public EnumVo getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(EnumVo classificacao) {
        this.classificacao = classificacao;
    }

    public EntidadeVo getFrota() {
        return frota;
    }

    public void setFrota(EntidadeVo frota) {
        this.frota = frota;
    }
}

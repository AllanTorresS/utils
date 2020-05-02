package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa de comandas
 */
public class FiltroPesquisaComandaVo extends BaseFiltroPaginado {

    private String nome;
    private EntidadeVo pontoVenda;
    private EntidadeVo unidade;
    private EnumVo habilitado;
    private EnumVo bloqueado;
    private EntidadeVo frota;
    private Boolean postoInterno;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EnumVo getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(EnumVo habilitado) {
        this.habilitado = habilitado;
    }

    public EnumVo getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(EnumVo bloqueado) {
        this.bloqueado = bloqueado;
    }

    public EntidadeVo getUnidade() {
        return unidade;
    }

    public void setUnidade(EntidadeVo unidade) {
        this.unidade = unidade;
    }

    public EntidadeVo getFrota() {
        return frota;
    }

    public void setFrota(EntidadeVo frota) {
        this.frota = frota;
    }

    public EntidadeVo getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(EntidadeVo pontoVenda) {
        this.pontoVenda = pontoVenda;
    }

    public Boolean getPostoInterno() {
        return postoInterno;
    }

    public void setPostoInterno(Boolean postoInterno) {
        this.postoInterno = postoInterno;
    }
}

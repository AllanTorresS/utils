package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa de unidades e empresas agregadas.
 */
public class FiltroPesquisaUnidadeEmpresaAgregadaVo extends BaseFiltroPaginado {
    private EntidadeVo empresaAgregada;
    private EntidadeVo unidade;
    private EntidadeVo frota;
    private String cidade;
    private EnumVo uf;
    private EnumVo tipoEntidade;
    private EnumVo status;

    public EntidadeVo getEmpresaAgregada() {
        return empresaAgregada;
    }

    public void setEmpresaAgregada(EntidadeVo empresaAgregada) {
        this.empresaAgregada = empresaAgregada;
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public EnumVo getUf() {
        return uf;
    }

    public void setUf(EnumVo uf) {
        this.uf = uf;
    }

    public EnumVo getTipoEntidade() {
        return tipoEntidade;
    }

    public void setTipoEntidade(EnumVo tipoEntidade) {
        this.tipoEntidade = tipoEntidade;
    }

    public EnumVo getStatus() {
        return status;
    }

    public void setStatus(EnumVo status) {
        this.status = status;
    }
}

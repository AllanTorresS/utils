package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import javax.validation.constraints.NotNull;

/**
 * Filtro para pesquisa de postos credenciados.
 */
public class FiltroPesquisaPostoCredenciadoVo extends BaseFiltroPaginado {
    @NotNull
    private EntidadeVo frota;
    private EntidadeVo pontoVenda;
    private String cidade;
    private EnumVo uf;
    private EnumVo statusBloqueio;

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

    public EnumVo getStatusBloqueio() {
        return statusBloqueio;
    }

    public void setStatusBloqueio(EnumVo statusBloqueio) {
        this.statusBloqueio = statusBloqueio;
    }
}
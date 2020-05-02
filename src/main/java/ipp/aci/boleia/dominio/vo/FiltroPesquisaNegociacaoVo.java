package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;


/**
 * Filtro para pesquisa de negociacao
 */
public class FiltroPesquisaNegociacaoVo extends BaseFiltroPaginado {

    private EntidadeVo frota;
    private EntidadeVo pontoDeVenda;
    private EnumVo ufPontoDeVenda;
    private String municipioPontoDeVenda;

    public EntidadeVo getFrota() {
        return frota;
    }

    public void setFrota(EntidadeVo frota) {
        this.frota = frota;
    }

    public EntidadeVo getPontoDeVenda() {
        return pontoDeVenda;
    }

    public void setPontoDeVenda(EntidadeVo pontoDeVenda) {
        this.pontoDeVenda = pontoDeVenda;
    }

    public EnumVo getUfPontoDeVenda() {
        return ufPontoDeVenda;
    }

    public void setUfPontoDeVenda(EnumVo ufPontoDeVenda) {
        this.ufPontoDeVenda = ufPontoDeVenda;
    }

    public String getMunicipioPontoDeVenda() {
        return municipioPontoDeVenda;
    }

    public void setMunicipioPontoDeVenda(String municipioPontoDeVenda) {
        this.municipioPontoDeVenda = municipioPontoDeVenda;
    }
}

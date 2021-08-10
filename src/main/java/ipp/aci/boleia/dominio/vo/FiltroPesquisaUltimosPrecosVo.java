package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.FiltroBasePeriodoPaginado;

/**
 * Filtro para pesquisa de histórico de preços
 */
public class FiltroPesquisaUltimosPrecosVo extends FiltroBasePeriodoPaginado {

    private EntidadeVo frota;
    private EntidadeVo pontoDeVenda;
    private EntidadeVo tipoCombustivel;

    /**
     * Construtor padrão
     */
    public FiltroPesquisaUltimosPrecosVo() {
    }

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

    public EntidadeVo getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(EntidadeVo tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }
}

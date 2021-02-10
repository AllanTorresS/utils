package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import ipp.aci.boleia.dominio.pesquisa.comum.FiltroBasePeriodoPaginado;

import java.util.List;

/**
 * Filtro de relatorio de abastecimento de Agenciador de Frete
 */
public class FiltroRelatorioTransacaoVo extends FiltroBasePeriodoPaginado {

    private List<Long> idsPontoVenda;

    public FiltroRelatorioTransacaoVo() {
        //Construtor default
    }

    public List<Long> getIdsPontoVenda() {
        return idsPontoVenda;
    }

    public void setIdsPontoVenda(List<Long> idsPontoVenda) {
        this.idsPontoVenda = idsPontoVenda;
    }
}

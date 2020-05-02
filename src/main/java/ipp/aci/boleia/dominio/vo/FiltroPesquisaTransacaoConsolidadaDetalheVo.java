package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa de transação consolidada detalhe
 */
public class FiltroPesquisaTransacaoConsolidadaDetalheVo extends BaseFiltroPaginado {

    private Long idConsolidado;

    public Long getIdConsolidado() {
        return idConsolidado;
    }

    public void setIdConsolidado(Long idConsolidado) {
        this.idConsolidado = idConsolidado;
    }
}

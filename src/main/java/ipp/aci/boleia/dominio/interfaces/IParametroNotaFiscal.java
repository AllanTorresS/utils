package ipp.aci.boleia.dominio.interfaces;

import ipp.aci.boleia.dominio.Frota;

public interface IParametroNotaFiscal {

    Long getId();

    void setId(Long id);

    Integer getLocalDestino();

    void setLocalDestino(Integer localDestino);

    Integer getNfTipoAgrupamento();

    void setNfTipoAgrupamento(Integer nfTipoAgrupamento);

    String getDadosAdicionais();

    void setDadosAdicionais(String dadosAdicionais);

    Boolean getSepararPorCombustivelProdutoServico();

    void setSepararPorCombustivelProdutoServico(Boolean separarPorCombustivelProdutoServico);

    Long getUnidadeLocalDestinoPadrao();

    void setUnidadeLocalDestinoPadrao(Long unidadeLocalDestinoPadrao);

    /**
     * O local destino configurado é no ato do abastecimento?
     * @return true se positivo
     */
    boolean isDestinoNotaFiscalNoLocalDoAbastecimento();

}

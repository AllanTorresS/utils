package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.util.UtilitarioFormatacao;

import java.math.BigDecimal;

/**
 * Classe com informações referentes à um ajuste de cobrança.
 */
public class AjusteCobrancaVo {

    private String dataAjuste;
    private String descricaoAjuste;
    private String valorAjuste;

    /**
     * Construtor default
     */
    public AjusteCobrancaVo() {
    }

    /**
     * Constrói o VO com os dados necessário.
     *
     * @param dataAjuste      Data em que o ajuste foi realizado.
     * @param descricaoAjuste Descrição do ajuste realizado.
     * @param valorAjuste     Valor do ajuste.
     */
    public AjusteCobrancaVo(String dataAjuste, String descricaoAjuste, BigDecimal valorAjuste) {
        this.dataAjuste = dataAjuste;
        this.descricaoAjuste = descricaoAjuste;

        valorAjuste = valorAjuste.setScale(2);
        this.valorAjuste = UtilitarioFormatacao.formatarDecimal(valorAjuste);
    }

    public String getDataAjuste() {
        return dataAjuste;
    }

    public void setDataAjuste(String dataAjuste) {
        this.dataAjuste = dataAjuste;
    }

    public String getDescricaoAjuste() {
        return descricaoAjuste;
    }

    public void setDescricaoAjuste(String descricaoAjuste) {
        this.descricaoAjuste = descricaoAjuste;
    }

    public String getValorAjuste() {
        return valorAjuste;
    }

    public void setValorAjuste(String valorAjuste) {
        this.valorAjuste = valorAjuste;
    }
}

package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.tarifador.TaxaTarifador;

import java.math.BigDecimal;

/**
 * Contrato para implementacao de repositorios de entidades TaxaTarifador
 */
public interface ITaxaTarifadorDados extends IRepositorioBoleiaDados<TaxaTarifador> {

    /**
     * Obtém a taxa que deve ser aplicada para um valor/litragem de cobrança e frota
     * @param valorReais o valor total usado para escolher a faixa
     * @param valorLitragem a litragem usada para escolher a faixa
     * @param idFrota o id da frota associada à cobrança
     * @return a taxa vigente para a frota e total da cobrança
     */
    TaxaTarifador obterTaxaTarifadorVigente(BigDecimal valorReais, BigDecimal valorLitragem, Long idFrota);
}

package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Cobranca;
import ipp.aci.boleia.dominio.CobrancaConectcar;
import ipp.aci.boleia.dominio.vo.BoletoVo;

/**
 * Contrato para implementacao de reposiórios de Boleto do JDE.
 *
 */
public interface IBoletoDados {

    /**
     * Método que cria um boleto para uma cobrança já faturada
     * 
     * @param cobranca Cobrança para qual será gerado boleto.
     * @return Dados do boleto da cobrança
     */
    BoletoVo recuperar(Cobranca cobranca);

    /**
     * Método que cria um boleto para uma cobrança já faturada
     * 
     * @param cobranca Cobrança para qual será gerado boleto.
     * @return Dados do boleto da cobrança
     */
    BoletoVo recuperar(CobrancaConectcar cobranca);

}
package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.CicloRepasse;
import ipp.aci.boleia.dominio.Cobranca;
import ipp.aci.boleia.dominio.PedidoCreditoFrota;

import java.util.List;

/**
 * Contrato para implementacao de reposiórios de Fatura do JDE.
 * 
 */
public interface IFaturaDados {

    /**
     * Método que inclui uma fatura nova no JDE.
     * 
     * @param cobranca Cobrança para qual será gerada a fatura.
     * @param cobrancaAnterior A cobranca anterior à atual, para compensação de ajustes feitos no ciclo anterior
     * @return Cobrança atualizada
     */
    Cobranca incluir(Cobranca cobranca, Cobranca cobrancaAnterior);


    /**
     * Método que inclui crédito a uma frota no JDE.
     *
     * @param pedido de inclusão de crédito pago pela frota.
     * @return pedido atualizado
     */
    PedidoCreditoFrota incluir(PedidoCreditoFrota pedido);

    /**
     * Método que consulta a cobrança gerada no JDE e atualiza a
     * entidade de Cobrança com o status encontrado no registro da integração.
     *
     * @param cobranca Cobrança que será consultada.
     * @return Cobrança atualizada
     */
    Cobranca consultarCobranca(Cobranca cobranca);

    /**
     * Método que inclui ciclos de repasse no JDE.
     *
     * @param ciclosRepasse os ciclos de repasse em questão
     * @return ciclo de repasse atualizado
     */
    List<CicloRepasse> incluir(List<CicloRepasse> ciclosRepasse);

    /**
     * Método que consulta o ciclo de repasse gerado no JDE e atualiza a
     * entidade de CicloCobranca com o status encontrado no registro da integração.
     *
     * @param cicloRepasse CicloRepasse que será consultada.
     * @return CicloRepasse atualizado
     */
    CicloRepasse consultarCicloRepasse(CicloRepasse cicloRepasse);
}